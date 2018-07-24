package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.BorrowAuditLog;
import com.adpanshi.cashloan.business.cl.extra.OrderByExtra;
import com.adpanshi.cashloan.business.cl.mapper.BorrowAuditLogMapper;
import com.adpanshi.cashloan.business.cl.mapper.ChannelMapper;
import com.adpanshi.cashloan.business.cl.model.TemplateInfoModel;
import com.adpanshi.cashloan.business.cl.service.BorrowMainService;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.domain.BorrowMainModel;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.core.model.StagingRecordModel;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
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
    public void updatePayState(Long borrowId,String state){
        updatePayState(borrowId, state, null, null);
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
	public Page<StagingRecordModel> pageByUserId(Long userId,int current,int pageSize) {
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
			if(count>0)record.setState(BorrowModel.STATE_DELAY);
			TemplateInfoModel template=new TemplateInfoModel().parseTemplateInfo(record.getAmount(), record
                    .getTemplateInfo());
			if(null!=template){
				record.setStagesAmount(template.getAmount());
				record.setByStages("分"+template.getStages()+"期");
				record.setTotalAmount(record.getAmount());
//				record.setTotalAmount(MathUtil.add(record.getAmount(),record.getFee(),record.getPenaltyAmout()));
			}
		}
		return (Page<StagingRecordModel>)stagingRecordList;
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
