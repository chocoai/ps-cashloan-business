package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.service.UserContactsService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.ErrorStringUtil;
import com.adpanshi.cashloan.business.core.common.util.ShardTableUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.rule.domain.UserContacts;
import com.adpanshi.cashloan.business.rule.mapper.UserContactsMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 用户资料--联系人ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-03-04 11:52:26
 *
 *
 * 
 *
 */
 
@Service("clUserContactsService")
public class UserContactsServiceImpl extends BaseServiceImpl<UserContacts, Long> implements UserContactsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserContactsServiceImpl.class);
   
    @Resource
    private UserContactsMapper userContactsMapper;

	@Override
	public BaseMapper<UserContacts, Long> getMapper() {
		return userContactsMapper;
	}

	@Override
	public boolean deleteAndSave(List<Map<String, Object>> infos,String userId) {
		int msg = 0;
		String name = null;
		String phone = null;
		boolean flag = false;
		if(CollectionUtils.isEmpty(infos)){
			return flag;
		}
		
		long userid = Long.parseLong(userId);
		
		// 分表
		String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userid, 30000);
		int countTable = userContactsMapper.countTable(tableName);
		if (countTable == 0) {
			userContactsMapper.createTable(tableName);
		}

		/**
		 * 去重后，新增
		 */

		Map<String, Object> borrowMap = new HashMap<>();
		borrowMap.put("userId", Long.parseLong(userId));
		List<UserContacts> userContactsList = userContactsMapper.listShardSelective(tableName,borrowMap);

		Map<String, Map<String, Object>> infosMap = new HashMap<>();
		for(Map<String, Object> map: infos){
			String phoneTmp = StringUtil.isNull(map.get("phone")).replaceAll("[^0-9]", "");
			if (StringUtils.isBlank(phoneTmp)){
				break;
			}
			infosMap.put(phoneTmp,map);
		}

		for(UserContacts uc : userContactsList){
			for(Map<String, Object> map: infos){
				String phoneTmp = StringUtil.isNull(map.get("phone")).replaceAll("[^0-9]", "");
				if (StringUtils.isBlank(phoneTmp)){
					break;
				}
				if (uc.getPhone().equals(phoneTmp)){
					infosMap.remove(phoneTmp);
				}
			}
		}

		List<Map<String, Object>> newInfos = new ArrayList<>();
		Set<String> stringSet = infosMap.keySet();
		Iterator<String> iterator = stringSet.iterator();
		while (iterator.hasNext()){
			String key = iterator.next();
			newInfos.add(infosMap.get(key));
		}

		if (CollectionUtils.isEmpty(newInfos)){
			return false;
		}

//		userContactsMapper.deleteShardByUserId(tableName, userid);
		for (Map<String, Object> map : newInfos) {
			logger.debug("保存用户userId："+userId+"通讯录，name："+StringUtil.isNull(map.get("name"))+"，phone："+StringUtil.isNull(map.get("phone")));
			name = StringUtil.isNull(map.get("name")).replaceAll("(null)", "").replace("()", "").replaceAll(" ", "");

			//begin pantheon 20170615 替换姓名中的表情为'*'
			name = ErrorStringUtil.replaceErrorStringToStar(name);
			//end

			phone = StringUtil.isNull(map.get("phone")).replaceAll("-", "").replaceAll(" ", "");
			logger.debug("保存用户userId："+userId+"通讯录，name___："+name+"，phone___："+phone);
			if(StringUtil.isNotBlank(name) && StringUtil.isNotBlank(phone) && phone.length() <= 20){
				try {
					UserContacts userContacts = new UserContacts();
					userContacts.setUserId(userid);
                    //@remarks:替换emojj字符. @date:20170904 @author:nmnl
                    String _name = name.replace("<","").replace(">",",");
                    userContacts.setName(_name.length() > 20?_name.substring(0,20):_name);
					userContacts.setPhone(phone);
					msg = userContactsMapper.saveShard(tableName, userContacts);
				} catch (Exception e) {
					logger.error("保存用户userId："+userId+"通讯录异常， name： " + name + "， phone：" + phone + "，" + e.getMessage(),e);
				}
			} else {
				logger.error("保存用户userId："+userId+"通讯录失败，name： " + name + "， phone：" + phone);
			}
		}
		if (msg>0) {
			flag = true;
		}
		return flag;
	}



}