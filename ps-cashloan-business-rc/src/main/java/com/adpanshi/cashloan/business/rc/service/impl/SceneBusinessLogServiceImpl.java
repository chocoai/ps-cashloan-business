package com.adpanshi.cashloan.business.rc.service.impl;


import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rc.domain.SceneBusinessLog;
import com.adpanshi.cashloan.business.rc.domain.UserAuthCopy;
import com.adpanshi.cashloan.business.rc.mapper.SceneBusinessLogMapper;
import com.adpanshi.cashloan.business.rc.mapper.UserAuthCopyMapper;
import com.adpanshi.cashloan.business.rc.model.TppServiceInfoModel;
import com.adpanshi.cashloan.business.rc.service.SceneBusinessLogService;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 场景与第三方征信接口执行记录

 * @version 1.0
 * @date 2017年4月11日上午11:52:15
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 研发中心：rdc@fentuan123.com
 *
 */
@Service("sceneBusinessLogService")
@Transactional(rollbackFor=Exception.class)
public class SceneBusinessLogServiceImpl extends BaseServiceImpl<SceneBusinessLog, Long> implements SceneBusinessLogService {
	
	public static final Logger logger = LoggerFactory.getLogger(SceneBusinessLogServiceImpl.class);

	@Resource
	private SceneBusinessLogMapper sceneBusinessLogMapper;
	@Resource
	private UserAuthCopyMapper userAuthCopyMapper;
	
	@Override
	public BaseMapper<SceneBusinessLog, Long> getMapper() {
		return sceneBusinessLogMapper;
	}


	/**
	 * 按照get_way判断接口是否需要执行
	 * @param userId
	 * @param busId
	 * @param getWay
	 * @param period
	 * @return
	 */
	@Override
	public boolean needExcute(Long userId,Long busId,String getWay,String period) {
		if(TppServiceInfoModel.GET_WAY_EVERYTIMES.equals(getWay)){
			return true;
		}else if(TppServiceInfoModel.GET_WAY_CYCLE.equals(getWay)){
			if(StringUtil.isNotBlank(period)){
				int days = Integer.parseInt(period);//周期时间
				SceneBusinessLog log = sceneBusinessLogMapper.findLastExcute(userId,busId);
				//如果找不到记录，那么说明接口没有执行过，进行第一次执行，需要执行返回true
				if(log==null){
					return true;
				}

				//begin pantheon 20170614 时间天数差小于固定周期，都不需要重新走同盾贷前接口
				// 1.大于周期时间才执行场景条件
				DateTime beforeTime = new DateTime(log.getCreateTime());//最后一次执行的时间
				DateTime nowTime = new DateTime(new Date());//当前时间
				//时间相差天数
				int daysTemp1 = Days.daysBetween(beforeTime,nowTime).getDays();

				// 2.表cl_user_auth中同盾认证时间tongdun_state_time也算在内
				int daysTemp2 = Integer.MAX_VALUE;

				Map<String,Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				UserAuthCopy tempUserAuth =  userAuthCopyMapper.findSelectiveWithVersion(map);

				if(tempUserAuth!=null){
					if(tempUserAuth.getTongdunStateTime() != null){
						DateTime tongdunTime = new DateTime(tempUserAuth.getTongdunStateTime());
						daysTemp2 = Days.daysBetween(tongdunTime,nowTime).getDays();
					}
				}

				if(daysTemp1 < days || daysTemp2 < days){//任何一种情况的时间天数差小于固定周期，都不需要重新走同盾贷前接口
                    logger.info("SceneBusinessLogServiceImpl-neeExcute()-userId-"+userId+"-场景条件时间天数差小于固定周期，都不需要重新走接口(包括同盾，芝麻等)");
					return false;
				}else{
                    logger.info("SceneBusinessLogServiceImpl-neeExcute()-userId-"+userId+"-场景条件时间天数差大于固定周期，调用接口(包括同盾，芝麻等)-busId："+busId);
					return true;
				}
				//end
			}
		}else if(TppServiceInfoModel.GET_WAY_ONCE.equals(getWay)){
			SceneBusinessLog log = sceneBusinessLogMapper.findLastExcute(userId,busId);
			if(log==null){
				return true;
			}
		}
		return false;
	}


	
	public String getQcOrderNo(){
		Random random = new Random();
		String reqOrderNo = "";
 	   	for(int i=0;i<9;i++){
 		   int a = random.nextInt(10);
 		   reqOrderNo += a;
 	   	}
 	   	return reqOrderNo;
	}

	@Override
	public boolean haveNeedExcuteService(Long borrowId) {
		int count = sceneBusinessLogMapper.countUnFinishLog(borrowId);
		return count > 0 ? true : false;
	}
	
	

}
