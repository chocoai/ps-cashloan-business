package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.service.UserAppsService;
import com.adpanshi.cashloan.business.core.common.enums.TableDataEnum;
import com.adpanshi.cashloan.business.core.common.util.ShardTableUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.rule.domain.UserApps;
import com.adpanshi.cashloan.business.rule.mapper.UserAppsMapper;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-11 11:03:41
 * @history
 */
@Service("userAppsService")
public class UserAppsServiceImpl  implements UserAppsService{
	
	Logger logger =LoggerFactory.getLogger(getClass());

	@Resource
	UserAppsMapper userAppsMapper;
	
	@Override
	public int save(UserApps userApps) {
		if(null==userApps){
			logger.error("--------------->parameters userApps is not null .");
			return 0;
		}
		if(StringUtil.isEmpty(userApps.getUserId(),userApps.getPackageName(),userApps.getAppName())){
			logger.error("--------------->userId={}、appName={}、packageName={} parameters cannot be null.",new Object[]{userApps.getUserId(),userApps.getAppName(),userApps.getPackageName()});
			return 0;
		}
		//@1.先查询-记录是否存在
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("userId", userApps.getUserId());
		paramMap.put("packageName", userApps.getPackageName());
		paramMap.put("appName", userApps.getAppName());
		String tableName=ShardTableUtil.getTableNameByParam(userApps.getUserId(), TableDataEnum.TABLE_DATA.CL_USER_APPS);
		List<UserApps> list= userAppsMapper.listSelective(tableName,paramMap);
		if(null!=list&&list.size()>0){
			logger.info("---------------->userId={}、appName={}、packageName={}的记录在表{}中已存在，跳过后续逻辑.",new Object[]{userApps.getUserId(),userApps.getAppName(),userApps.getPackageName(),tableName});
			return -1;
		}
		logger.info("---------------->开始往{}中新增数据.",new Object[]{tableName});
		return userAppsMapper.save(tableName,userApps);
	}

	@Override
	public int updateSelective(UserApps userApps) {
		if(StringUtil.isEmpty(userApps.getUserId(),userApps.getId())){
			logger.error("--------------->parameters is not null . userId={},id={}.",new Object[]{userApps.getUserId(),userApps.getId()});
			return 0;
		}
		String tableName=ShardTableUtil.getTableNameByParam(userApps.getUserId(), TableDataEnum.TABLE_DATA.CL_USER_APPS);
		return userAppsMapper.updateSelective(tableName, userApps);
	}

	@Override
	public UserApps findByPrimary(Long userId, Long id) {
		if(StringUtil.isEmpty(userId,id)){
			logger.error("--------------->parameters is not null . userId={},id={}.",new Object[]{userId,id});
			return null;
		}
		String tableName=ShardTableUtil.getTableNameByParam(userId, TableDataEnum.TABLE_DATA.CL_USER_APPS);
		return userAppsMapper.findByPrimary(tableName, id);
	}

	@Override
	public List<UserApps> listSelective(Long userId, Map<String, Object> userApps) {
		if(StringUtil.isEmpty(userId,userApps)){
			logger.error("--------------->parameters is not null . userApps={},id={}.",new Object[]{userId,JSONObject.toJSONString(userApps)});
			return null;
		}
		String tableName=ShardTableUtil.getTableNameByParam(userId, TableDataEnum.TABLE_DATA.CL_USER_APPS);
		return userAppsMapper.listSelective(tableName, userApps);
	}

	@Override
	public int saveBatchHandle(List<UserApps> userAppsList) {
		if(null==userAppsList || userAppsList.size()==0){
			logger.error("------------------->未抓取到app应用信息...跳过业务逻辑.");
			return 0;
		}
		logger.info("------------------->抓取到用户userId={}的app信息有{}条.",new Object[]{userAppsList.get(0).getUserId(),userAppsList.size()});
		int success=0;
		String tableName=null;
		try {
			//@1.先把用户所有已存在的都查询出来、与当前抓取到的数据集进行比较、取差集..
			tableName=ShardTableUtil.getTableNameByParam(userAppsList.get(0).getUserId(), TableDataEnum.TABLE_DATA.CL_USER_APPS);
			Map<String,Object> paramMap=new HashMap<>();
			paramMap.put("userId", userAppsList.get(0).getUserId());
			paramMap.put("state", 0);
			List<UserApps> existUserAppsList= userAppsMapper.listSelective(tableName,paramMap);
			if(StringUtil.isEmpty(existUserAppsList)){
				truncateByData(userAppsList);
				success=userAppsMapper.insertBatch(tableName, userAppsList);
				logger.info("-------------->抓取到用户userId={}的app信息有{}条,入库的表{}，入库成功的有{}条.",new Object[]{userAppsList.get(0).getUserId(),userAppsList.size(),tableName,success});
				return success;
			}
			for(int i=0;i<userAppsList.size();i++){
				for(int k=0;k<existUserAppsList.size();k++){
					UserApps save=userAppsList.get(i);
					UserApps exist=existUserAppsList.get(k);
					if(save.getPackageName().equals(exist.getPackageName()) && save.getAppName().equals(exist.getAppName())){
						userAppsList.remove(i);
						i--;
						break;
					}
				}
			}
			if(userAppsList.size()>0){
				truncateByData(userAppsList);
				success=userAppsMapper.insertBatch(tableName, userAppsList);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		logger.info("------------------->抓取的app应用入库的表{}、成功入库{}条.",new Object[]{tableName,success});
		return success;
	}

	//--------------------------------->private method
	
	/**
	 * <p>待处理的数据集(处理insert时字符长度大于数据表指定长时进行截取)</p>
	 * @param userApps
	 * */
	private void truncateByData(List<UserApps> userApps){
		if(StringUtil.isEmpty(userApps)) return;
		for(UserApps app: userApps){
			String packageName=StringUtil.truncate(app.getPackageName(), 128,false);
			app.setPackageName(packageName);
		}
	}

}