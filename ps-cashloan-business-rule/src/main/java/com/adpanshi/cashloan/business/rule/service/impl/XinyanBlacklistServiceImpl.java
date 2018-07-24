package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.business.core.xinyan.XinyanUtil;
import com.adpanshi.cashloan.business.core.xinyan.beans.XinyanReqParams;
import com.adpanshi.cashloan.business.core.xinyan.beans.XinyanResultDetail;
import com.adpanshi.cashloan.business.core.xinyan.beans.XinyanReturnData;
import com.adpanshi.cashloan.business.core.xinyan.enums.XinyanRetunCodeEnum;
import com.adpanshi.cashloan.business.rule.domain.XinyanBlacklist;
import com.adpanshi.cashloan.business.rule.mapper.XinyanBlacklistMapper;
import com.adpanshi.cashloan.business.rule.service.XinyanBlacklistService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-10-21 16:33:33
 * @history
 */
@Service("xinyanBlacklistService")
public class XinyanBlacklistServiceImpl extends BaseServiceImpl<XinyanBlacklist,Long> implements XinyanBlacklistService{

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
    private XinyanBlacklistMapper xinyanBlacklistMapper;
	
	@Resource
	private UserBaseInfoService userBaseInfoService;
	
	@Override
	public BaseMapper<XinyanBlacklist, Long> getMapper() {
		return xinyanBlacklistMapper;
	}

	@Override
	public XinyanBlacklist getXinyanBlacklistByLasterIdNo(String idNo) {
		if(StringUtil.isBlank(idNo)){
			logger.info("---------------->Parameter cannot be empty , idNo={}。",new Object[]{idNo});
			return null;
		}
		return xinyanBlacklistMapper.getXinyanBlacklistByLasterIdNo(idNo);
	}

	@Override
	public int updateSelective(XinyanBlacklist xinyanBlacklist) {
		if(null==xinyanBlacklist){
			logger.info("---------------->Parameter cannot be empty。");
			return 0;
		}
		return xinyanBlacklistMapper.updateSelective(xinyanBlacklist);
	}

	@Override
	public XinyanBlacklist queryBlackByReqParams(XinyanReqParams reqParams) {
		XinyanBlacklist blacklist=new XinyanBlacklist();
		try {
			String result=XinyanUtil.queryBlackByReqParams(reqParams);
			if(StringUtil.isBlank(result)) return null;
			JSONObject obj= JSONObject.parseObject(result);
			if(null==obj) return null;
			String success=obj.getString("success");
			
			logger.info("------------------------>新颜:success:{}",new Object[]{success});
			
			if(StringUtil.isBlank(success) || !Boolean.parseBoolean(success)){
				logger.info("------------------>查询失败跳过后续业务逻辑，失败原因:{}.",new Object[]{JSONObject.toJSONString(obj)});
				return null;
			}
			String jsonData=obj.getString("data");
			if(StringUtil.isBlank(jsonData)){
				logger.info("------------------>响应结果data为空或null，跳过后续业务逻辑...");
				return null;
			}
			XinyanReturnData returnData= JSONObject.parseObject(jsonData, XinyanReturnData.class);
			if(null==returnData){
				logger.info("------------------>响应结果集为null，跳过后续业务逻辑...");
				return null;
			}
			
			logger.info("------------------------>新颜:开始copty:{}",new Object[]{JSONObject.toJSONString(returnData)});
			
			BeanUtils.copyProperties(returnData,blacklist);
			String resultDetailJson=returnData.getResult_detail();
			if(StringUtil.isNotEmptys(resultDetailJson)){
				XinyanResultDetail resultDetail=JSONObject.parseObject(resultDetailJson, XinyanResultDetail.class);
				if(null!=resultDetail){
					BeanUtils.copyProperties(resultDetail,blacklist);
				}
			}
		} catch (Exception e) {
			blacklist.setId_no(reqParams.getId_no());
			blacklist.setId_name(reqParams.getId_name());
			blacklist.setTrans_id(reqParams.getTrans_id());
			logger.info("",e);
		}
		return blacklist;
	}

	@Override
	public XinyanBlacklist queryBlackByIdNoWithIdName(String id_no, String id_name) {
		XinyanReqParams reqParams=new XinyanReqParams(id_no,id_name);
		return queryBlackByReqParams(reqParams);
	}

	@Override
	public XinyanBlacklist queryBlackByUserId(Long userId) {
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("userId", userId);
		UserBaseInfo baseInfo=userBaseInfoService.findSelective(paramMap);
		if(null==baseInfo) return null;
		return queryBlackByIdNoWithIdName(baseInfo.getIdNo(),baseInfo.getRealName());
	}

	@Override
	public int save(XinyanBlacklist xinyanBlacklist) {
		if(null==xinyanBlacklistMapper) return 0;
		return xinyanBlacklistMapper.save(xinyanBlacklist);
	}


	@Override
	public boolean whetherHit(XinyanBlacklist xinyanBlacklist, XinyanRetunCodeEnum code,int overDueDay) {
		if(null==xinyanBlacklist) {
			logger.info("---------------->参数不能为空...");
			return Boolean.FALSE;
		}
		if(null==code ){
			logger.info("---------------->规则不能为空...");
			return Boolean.FALSE;
		}
		if(code.getName().equals(xinyanBlacklist.getDesc()) && isHit(xinyanBlacklist.getMax_overdue_days(), overDueDay)){
			//已经命中
			return Boolean.TRUE;
		}
		return false;
	}
	
	/**
	 * <p>判断逾期天数是否命中</p>
	 * @param overdueDayStr 逾期天数区间值 （新颜给的区间值类似: 1-15 或者 >180 ）
	 * @param overdueDay 最大逾期天数
	 * @return boolean
	 * */
	public boolean isHit(String overdueDayStr,int overdueDay){
		if(StringUtil.isBlank(overdueDayStr)) return false;
		String numberStr= StringUtil.handleNonNumericWithReplace(overdueDayStr, ",");
		String[] numbers=numberStr.split(",");
		for(String number:numbers){
			if(number!=null&&!"".equals(number)) {
				if (Integer.parseInt(number) > overdueDay) {
					return true;
				}
			}
		}
		return false;
	}

}