package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.BorrowUserExamine;
import com.adpanshi.cashloan.business.cl.domain.UserExamine;
import com.adpanshi.cashloan.business.cl.mapper.BorrowUserExamineMapper;
import com.adpanshi.cashloan.business.cl.mapper.ClExamineDictDetailMapper;
import com.adpanshi.cashloan.business.cl.mapper.UserExamineMapper;
import com.adpanshi.cashloan.business.cl.model.BorrowUserExamineModel;
import com.adpanshi.cashloan.business.cl.service.UserExamineService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
import com.adpanshi.cashloan.business.system.domain.SysUser;
import com.adpanshi.cashloan.business.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("userExamineService")
public class UserExamineServiceImpl implements UserExamineService {
	private static final Logger logger = LoggerFactory.getLogger(UserExamineServiceImpl.class);

	@Resource
	private UserExamineMapper userExamineMapper;
	@Resource
	private BorrowUserExamineMapper borrowUserExamineMapper;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private ClBorrowMapper clBorrowMapper;
	@Resource
	ClExamineDictDetailMapper clExamineDictDetailMapper;

	/**
	 * 查询我的信审订单信息
	 */
	@Override
	public BorrowUserExamine listBorrowUserExamineInfo(Long BorrowId) {
		Map<String, Object>  map = new HashMap<String, Object>();
		map.put("BorrowId", BorrowId);
		return borrowUserExamineMapper.findSelective(map);
	}


	/**
	 * 人工复审订单分配给信审人员
	 * @author chengyinghao
	 * @param borrow
	 */
	@Override
	public void sysUserToExamineOrderRelation(BorrowMain borrow , String sign){
		String examine_on_off = Global.getValue("examine_on_off");
		logger.info("*************************人工复审订单分配给信审人员开关" + examine_on_off);
		if(examine_on_off != null && examine_on_off.equals("on")){
			try{
				logger.info("***************************程序开始执行借款订单分配信审人员处理********************************");
				//order_sign=0代表第一次借款，1为复借
				String order_sign = "0";
				if("repeatOrder".equals(sign)){
					order_sign = "1";
				}
				//获取状态为启用的信审人员信息
				Map<String,Object> userParam = new HashMap<String, Object>();
				userParam.put("status", "0");
				List<UserExamine> userExamine= userExamineMapper.listUserExamineInfo(userParam);
				if(userExamine == null || userExamine.isEmpty()){
					throw new NullPointerException("没有正常状态的信审人员无法分配订单");
				}
				//通过订单人员关联表`cl_borrow_user_examine`获取未完成任务的人员统计信息
				String status = "0";//0表示未处理的订单
				List<BorrowUserExamineModel> borrowUserExamineModel= borrowUserExamineMapper.listBorrowUserExamineModel(status);
				//初始化信审人员的任务数为0
				Map<Long,Long> borrowUserExaminemap = new HashMap<Long, Long>();
				for (UserExamine userExamine2 : userExamine) {
					borrowUserExaminemap.put(userExamine2.getUserId(), 0L);
				}
				//生成信审订单关联信息
				BorrowUserExamine borrowUserExamine = new BorrowUserExamine();
				borrowUserExamine.setBorrowId(borrow.getId());
				borrowUserExamine.setUserId(borrow.getUserId());
				borrowUserExamine.setOrderNo(borrow.getOrderNo());
				borrowUserExamine.setOrderSign(order_sign);
				borrowUserExamine.setOperationDate(DateUtil.getNow());
				borrowUserExamine.setStatus("0");
				borrowUserExamine.setCreateDate(DateUtil.getNow());
				SysUser sysUser;
				logger.info("*****************************已获取信审人员的任务数等待分配任务**********************************");
				if(borrowUserExamineModel != null && !borrowUserExamineModel.isEmpty()){
					logger.info("***************************部分信审人员，未完成任务分配流程开始执行*************************************");
					//添加信审人员的未处理的订单个数
					for (BorrowUserExamineModel borrowUserExamineModel2 : borrowUserExamineModel) {
						if(borrowUserExaminemap.containsKey(borrowUserExamineModel2.getSysUserId())){
							borrowUserExaminemap.put(borrowUserExamineModel2.getSysUserId(), borrowUserExamineModel2.getCont());
						}
					}
					//信审人数任务数按升序排序
					/**
					 *1)将map中的所有entry转化为一个ArrayList;
					 *2)调用Collections.sort()方法,对元素(Map entry)进行排序,Comparator中自定义了按照map 中value进行排序
					 */
					List<Map.Entry<Long, Long>> borrowInfoList = new ArrayList<Map.Entry<Long,Long>>(borrowUserExaminemap.entrySet());

					Collections.sort(borrowInfoList,new Comparator<Map.Entry<Long, Long>>() {
						@Override
						public int compare(Map.Entry<Long, Long> o1,Map.Entry<Long,Long> o2){
							return o1.getValue().compareTo(o2.getValue());
						}
					});
					//得到升序结果
					Map<Long,Long> sysUsermap = new LinkedHashMap<Long, Long>();
					for (Map.Entry<Long, Long> entry : borrowInfoList) {
						sysUsermap.put(entry.getKey(), entry.getValue());
					}
					//获取任务数最少的审核人
					Long sysUserId = sysUsermap.entrySet().iterator().next().getKey();
					//获取信审人员的姓名
					sysUser = sysUserMapper.findByPrimary(sysUserId);
					logger.info("*********************未完成分配订单号:"+borrow.getId() + "*******借款人:"+borrow.getUserId() +"*****订单分配信审人员:"+sysUserId);
					//登记关联表信息,订单状态为待处理
					borrowUserExamine.setSysUserId(sysUserId);
					borrowUserExamine.setSysUserName(sysUser.getName());
					borrowUserExamine.setAuditId(sysUserId);
					borrowUserExamine.setAuditName(sysUser.getName());
					borrowUserExamine.setOrderStatus(BorrowModel.STATE_NEED_REVIEW);
					borrowUserExamineMapper.save(borrowUserExamine);
				}else{
					//关联表为空时登记关联表信息,订单状态为待处理，所有信审人员都没有未完成的任务
					Long sysUserId = userExamine.get(0).getUserId();
					//获取信审人员的姓名
					sysUser = sysUserMapper.findByPrimary(sysUserId);

					logger.info("*********************已完成分配订单号:"+borrow.getId() + "*******借款人:"+borrow.getUserId() +"*****订单分配信审人员:"+sysUserId);
					//登记关联表信息,订单状态为待处理
					borrowUserExamine.setSysUserId(sysUserId);
					borrowUserExamine.setSysUserName(sysUser.getName());
					borrowUserExamine.setAuditId(sysUserId);
					borrowUserExamine.setAuditName(sysUser.getName());
					borrowUserExamine.setOrderStatus(BorrowModel.STATE_NEED_REVIEW);
					borrowUserExamineMapper.save(borrowUserExamine);
				}
			}catch(Exception e){
				logger.info("************************信审订单分配失败*******************"+e);
				//登记关联表信息，表状态：2：系统错误，订单分配失败
				//生成信审订单关联信息
				BorrowUserExamine borrowUserExamine = new BorrowUserExamine();
				borrowUserExamine.setBorrowId(borrow.getId());
				borrowUserExamine.setUserId(borrow.getUserId());
				borrowUserExamine.setOperationDate(DateUtil.getNow());
				borrowUserExamine.setStatus("2");
				borrowUserExamine.setCreateDate(DateUtil.getNow());
				borrowUserExamineMapper.save(borrowUserExamine);
			}
		}
	}

}
