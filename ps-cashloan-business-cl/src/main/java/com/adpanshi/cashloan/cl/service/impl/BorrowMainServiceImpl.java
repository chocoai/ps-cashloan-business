package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.cl.domain.BorrowAuditLog;
import com.adpanshi.cashloan.cl.domain.Channel;
import com.adpanshi.cashloan.cl.extra.OrderByExtra;
import com.adpanshi.cashloan.cl.mapper.BorrowAuditLogMapper;
import com.adpanshi.cashloan.cl.mapper.BorrowRepayLogMapper;
import com.adpanshi.cashloan.cl.mapper.ChannelMapper;
import com.adpanshi.cashloan.cl.model.TemplateInfoModel;
import com.adpanshi.cashloan.cl.service.BorrowMainService;
import com.adpanshi.cashloan.core.common.exception.BussinessException;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.DateUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.domain.BorrowMainModel;
import com.adpanshi.cashloan.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.core.model.BorrowModel;
import com.adpanshi.cashloan.core.model.StagingRecordModel;
import com.adpanshi.cashloan.rule.mapper.ClBorrowMapper;
import com.adpanshi.cashloan.system.domain.SysUser;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 借款主信息表ServiceImpl
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-12-08 15:32:54
 */

@Service("borrowMainService")
public class BorrowMainServiceImpl extends BaseServiceImpl<BorrowMain, Long> implements BorrowMainService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowMainServiceImpl.class);

    @Resource
    private BorrowMainMapper borrowMainMapper;

    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;
    
    @Resource
    private ClBorrowMapper clBorrowMapper;

    @Resource
    private ChannelMapper channelMapper;

    @Resource
    private BorrowAuditLogMapper borrowAuditLogMapper;

    @Override
    public BaseMapper<BorrowMain, Long> getMapper() {
        return borrowMainMapper;
    }

    /**
     * 修改标的状态
     *
     * @param id
     * @param state
     */
    @Override
    public int modifyState(long id, String state) {
        return modifyStateAndRemark(id,state,null);
    }

    /**
     * 修改标的状态
     *
     * @param id
     * @param state
     * @param remark
     */
    @Override
    public int modifyStateAndRemark(long id, String state, String remark) {
        //审核步骤:
        BorrowAuditLog borrowAuditLog = new BorrowAuditLog();
        borrowAuditLog.setBorrowId(id);
        String strData = DateUtil.dateStr2(new Date());
        StringBuffer sb = new StringBuffer(strData);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("state", state);
        paramMap.put("id", id);
        /*平均分配:待人审状态-> 自动分配
        * @param borrowMainId
        * @date : 2018-1-04 15:58
        * @author: nmnl*/
        if (state.equals(BorrowModel.STATE_NEED_REVIEW)){
            Map<String, Object> ueMap = new HashMap<>();
            //减少查询范围:获取正在启用的信审人员,已经分配了多少订单
            ueMap.put("stateList",Arrays.asList(BorrowModel.STATE_NEED_REVIEW,BorrowModel.STATE_HANGUP));
            ueMap.put("startTime",strData+" 00:00:00");
            ueMap.put("endTime",strData+" 23:59:59");
            BorrowMainModel borrowMainModel = borrowMainMapper.selectBorrowSysCount(ueMap);
            if(StringUtil.isNotEmptys(borrowMainModel)){
                //当前订单自动分配给检索出来的信审人员.
                paramMap.put("sysUserId",borrowMainModel.getSystemId());
                paramMap.put("sysUserName",borrowMainModel.getSystemName());
                paramMap.put("sysCreateTime",new Date());
                sb.append(" 自动分配BorrowMainId: ").append(id).append(" 分配订单状态: ").append(state).append(" 分配人ID: ").append(borrowMainModel.getSystemId()).append(" 分配人名称: ").append(borrowMainModel.getSystemName());
            }
        }
        //没有remark字段.是除了22状态之外的.
        if(StringUtils.isNotEmpty(remark)){
            sb.append(" 审核: ").append(remark);
            paramMap.put("remark", sb.toString());
        }
        borrowAuditLog.setJsonData(sb.toString());
        borrowAuditLogMapper.update(borrowAuditLog);
        return borrowMainMapper.updateSelective(paramMap);
    }

    @Override
    public BorrowMain findLast(Long userId){
        if (userId == null){
            logger.error("获取用户最后一条失败记录失败，因为userId为空。");
            return null;
        }
        Map<String,Object> searchMap = new HashMap<>();
        searchMap.put("userId", userId);
        return borrowMainMapper.findLast(searchMap);
    }

    @Override
    public List<BorrowMain> findUserUnFinishedBorrow(Long userId) {
        return borrowMainMapper.findUserUnFinishedBorrow(userId);
    }

    @Override
	public void updatePayState(Long borrowId,String state){
        updatePayState(borrowId, state, null, null);
    }

    @Override
	public void updatePayState(Long borrowId, String state, Date loanTime, Date repayTime){
        Map<String, Object> map = new HashMap<>();
        map.put("id", borrowId);
        map.put("state", state);
        if (loanTime != null) {
            map.put("loanTime", loanTime);
        }
        if (repayTime != null) {
            map.put("repayTime", repayTime);
        }

        int result  =  borrowMainMapper.updatePayState(map);
        if(result < 1){
            throw new BussinessException("当前借款状态不允许修改");
        }
    }

	@Override
    public BorrowMain findByUserIdAndState(Map<String, Object> paramMap){
	    return borrowMainMapper.findByUserIdAndState(paramMap);
    }


    @Override
    public List<BorrowMain> selectBorrowWithAudit(long userId) {
        return borrowMainMapper.selectBorrowWithAudit(userId);
    }

	@Override
	public Page<StagingRecordModel> pageByUserId(Long userId, int current, int pageSize) {
		Map<String,Object> paramMap=OrderByExtra.getInstance().orderByDESC("id");
		paramMap.put("userId", userId);
		PageHelper.startPage(current, pageSize);
		List<StagingRecordModel> stagingRecordList=borrowMainMapper.pageByUserId(paramMap);
		for(int i=0;i<stagingRecordList.size();i++){
			StagingRecordModel record=stagingRecordList.get(i);
			if(StringUtil.isEmpty(record.getBorMainId(),record.getBorMainOrderNo(),record.getTemplateInfo())){
				stagingRecordList.remove(i);
				continue;
			}
			int count=clBorrowMapper.queryCount(userId, BorrowModel.STATE_DELAY, record.getBorMainId());
			if(count>0){record.setState(BorrowModel.STATE_DELAY);}
			TemplateInfoModel template=new TemplateInfoModel().parseTemplateInfo(record.getAmount(), record
                    .getTemplateInfo());
			if(null!=template){
				record.setStagesAmount(template.getAmount());
				record.setByStages("分"+template.getStages()+"期");
				record.setTotalAmount(record.getAmount());
			}
		}
		return (Page<StagingRecordModel>)stagingRecordList;
	}


    /**
     * @description 根据用户id查询借款记录
     *
     * @param userId
     * @param current
     * @param pageSize
     * @date : 2017-12-28 10:13
     * @author: nmnl
     * @throws Exception
     */
    @Override
    public Page<BorrowMain> selectBorrowByUserId(long userId, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        List<BorrowMain> borrowMains = borrowMainMapper.selectBorrowByUserId(userId);
        return (Page<BorrowMain>)borrowMains;
    }

    /**
     * @description 根据条件查询借款记录
     *
     * @param searchParams
     * @param current
     * @param pageSize
     * @date : 2017-12-28 10:13
     * @author: nmnl
     * @throws Exception
     */
    @Override
    public List<BorrowMainModel> selectBorrowList(Map<String, Object> searchParams,int current,int pageSize) {
        if(current !=0 && pageSize !=0) {
            PageHelper.startPage(current, pageSize);
        }
        List<BorrowMainModel> borrowMains = borrowMainMapper.selectBorrowList(searchParams);
        List<Channel> channelList = channelMapper.listChannel();
        //渠道名称
        for (BorrowMainModel borrowMainModel : borrowMains){
            for (Channel channel : channelList){
                Long channelId = borrowMainModel.getChannelId();
                if (null != channelId){
                    if (borrowMainModel.getChannelId().compareTo(channel.getId()) == 0){
                        borrowMainModel.setChannelName(channel.getName());
                    }
                }
            }
        }
        return borrowMains;
    }

    /** 
    * @Description: 查询还款信息 
    * @Param: [id] 
    * @return: java.util.Map 
    * @Author: Mr.Wange
    * @Date: 2018/7/24 
    */ 
    @Override
    public Map qryRepayLog(Long id){
        return borrowRepayLogMapper.qryRepayLog(id);
    }

    /**
     * @description 根据条件查询借款记录
     *
     * @param searchParams
     * @date : 2018-1-1 19:24
     * @author: nmnl
     * @throws Exception
     */
    @Override
    public List<BorrowMainModel> selectBorrowListByMap(Map<String, Object> searchParams) {
        return borrowMainMapper.selectBorrowList(searchParams);
    }

    /**
     * 分配:订单批量指派信审人员
     * @param sysUser
     * @param borrowMainIds
     * @param userId
     * @param userName
     * @date : 2018-1-03 19:41
     * @author: nmnl
     */
    @Override
    public Map<String, Object> orderAllotSysUsers(SysUser sysUser, Long[] borrowMainIds, Long userId, String userName) {
        Date dt = new Date();
        String strDate = DateUtil.dateToString(dt,DateUtil.yyyyMMddHHmmss);
        String jsonData = "[操作人ID:"+sysUser.getId()+",NAME:"+sysUser.getName()+",DATE:"+strDate+"]";
        Map<String,Object> outMap = new HashMap<>();
        List succList = new ArrayList();
        List failList = new ArrayList();
        for (Long borrowMainId : borrowMainIds) {
            //订单只有待审核的状态才能分配
            BorrowMain borrowMain = borrowMainMapper.findByPrimary(borrowMainId);
            String borrowState = borrowMain.getState();
            //只允许待审核的状态.重新分配
            if (BorrowModel.STATE_NEED_REVIEW.equals(borrowState) || BorrowModel.STATE_HANGUP.equals(borrowState)){
                BorrowAuditLog borrowAuditLog = new BorrowAuditLog();
                borrowAuditLog.setBorrowId(borrowMainId);
                borrowAuditLog.setJsonData(jsonData+",[OLD->ID:"+borrowMain.getSysUserId()+",NAME:"+borrowMain.getSysUserName()+",DATE:"+DateUtil.dateToString(borrowMain.getSysCreateTime(),DateUtil.yyyyMMddHHmmss)
                        +",NEW->ID:"+userId+",NAME:"+userName+",DATE:"+strDate+"]");
                borrowAuditLogMapper.update(borrowAuditLog);
                borrowMain.setSysUserId(userId);
                borrowMain.setSysUserName(userName);
                borrowMain.setSysCreateTime(dt);
                borrowMainMapper.update(borrowMain);
                succList.add(borrowMain);
            }else{
                failList.add(borrowMain);
            }
        }
        outMap.put("succList",succList);
        outMap.put("failList",failList);
        return outMap;
    }

	@Override
	public int getUnRepayCountByUserId(Long userId) {
		return borrowMainMapper.getUnRepayCountByUserId(userId);
	}

    @Override
    public List<BorrowMain> findWaitPayByOrderNos(Collection<String> orderNoList) {
        if (CollectionUtils.isEmpty(orderNoList)) {
            return new ArrayList<>();
        }
        return borrowMainMapper.findWaitPayByOrderNos(orderNoList);
    }

    @Override
    public List<BorrowMain> findByIds(Collection<Long> idList){
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>();
        }
        return borrowMainMapper.findByIds(idList);
    }
}
