package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.cl.service.UserEmerContactsService;
import com.adpanshi.cashloan.business.cl.service.UserEquipmentInfoService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.ErrorStringUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.rule.domain.UserEmerContacts;
import com.adpanshi.cashloan.business.rule.domain.UserEquipmentInfo;
import com.adpanshi.cashloan.business.rule.mapper.UserEmerContactsMapper;
import com.adpanshi.cashloan.business.rule.mapper.ZhimaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 紧急联系人表ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:24:05
 *
 *
 * 
 *
 */
 
@Service("userEmerContactsService")
public class UserEmerContactsServiceImpl extends BaseServiceImpl<UserEmerContacts, Long> implements UserEmerContactsService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserEmerContactsServiceImpl.class);
    
    @Resource
    private ZhimaMapper zhimaMapper;
   
    @Resource
    private UserEmerContactsMapper userEmerContactsMapper;
    
    @Resource
    private UserAuthService userAuthService;

    @Resource
    private UserEquipmentInfoService userEquipmentInfoService;

	@Override
	public BaseMapper<UserEmerContacts, Long> getMapper() {
		return userEmerContactsMapper;
	}
	
	@Override
	public List<UserEmerContacts> getUserEmerContactsByUserId(Map<String,Object> paramMap) {
		return userEmerContactsMapper.listSelective(paramMap);
	}
	
	@Override
	public Map<String, Object> saveOrUpdate(String name, String phone, String relation, String type, String userId,
			String operatingSystem, String systemVersions, String phoneType, String phoneBrand, String phoneMark,
			String versionName, String versionCode, String mac) {
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("userId", userId);
		List<UserEmerContacts> contacts=getUserEmerContactsByUserId(result);
		result.clear();
		String[] names=name.split(",");
		String[] phones=phone.split(",");
		String[] relations=relation.split(",");
		String[] types=type.split(",");
		if(names.length != 2 || phones.length != 2 || relations.length != 2 || types.length != 2){
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "参数有误，请联系客服");
			return result;
		}
		int count = 0;
		for (int i = 0; i < names.length; i++) {
			boolean flag = true;
			//更新数据
			for (UserEmerContacts contract : contacts) {
				if(contract.getType().equals(types[i])){
					UserEmerContacts info = contract;
					//begin pantheon 20170615 替换姓名中的表情为'*'
					String nameTemp = ErrorStringUtil.replaceErrorStringToStar(names[i]);
					String  phoneTemp = StringUtil.isNull(phones[i].replaceAll("[^0-9]", ""));
					//end
					
					info.setName(nameTemp);
					info.setPhone(phoneTemp);
					info.setRelation(relations[i]);
					info.setType(types[i]);
					int c = updateById(info);
					count =  count + c;
					flag = false;
				}
			}
			//新增数据
			if(flag){
				UserEmerContacts info=new UserEmerContacts();
				//begin pantheon 20170615 替换姓名中的表情为'*'
				String nameTemp = ErrorStringUtil.replaceErrorStringToStar(names[i]);
				String  phoneTemp = StringUtil.isNull(phones[i].replaceAll("[^0-9]", ""));
				//end
				info.setName(nameTemp);
				info.setPhone(phoneTemp);
				info.setRelation(relations[i]);
				info.setType(types[i]);
				info.setUserId(Long.parseLong(userId));
				int c = insert(info);
				count =  count + c;
			}
		}
		if(count < 2){
			throw new RuntimeException("紧急联系人保存失败，UserEmerContactsServiceImpl.saveOrUpdate");
		}

		//保存用户手机设备信息
		UserEquipmentInfo userEquipmentInfo = new UserEquipmentInfo();
		userEquipmentInfo.setUserId(Long.parseLong(userId));
		userEquipmentInfo.setOperatingSystem(operatingSystem);
		userEquipmentInfo.setSystemVersions(systemVersions);
		userEquipmentInfo.setPhoneType(phoneType);
		phoneBrand = ErrorStringUtil.replaceErrorStringToStar(phoneBrand);
		userEquipmentInfo.setPhoneBrand(phoneBrand);
		userEquipmentInfo.setPhoneMark(phoneMark);
		userEquipmentInfo.setVersionName(versionName);
		userEquipmentInfo.setVersionCode(versionCode);
		userEquipmentInfo.setMac(mac);
		userEquipmentInfoService.saveOrUpdate(userEquipmentInfo);
		
//		String zhimaFlag = Global.getValue("zhima_flag");
//		String zhimaScore = Global.getValue("zhimaScore");
//
		Map<String,Object> paramMap=new HashMap<String,Object>();//保存认证状态,直接设置成功
//
//		if("on".equals(zhimaFlag)){
//			Map<String,Object> paramMapZm=new HashMap<String,Object>();
//			paramMapZm.put("userId", userId);
//			Zhima zm=zhimaMapper.findSelective(paramMapZm);
//
//			if(zm == null){
//		        Zhima zhima = new Zhima();
//		        zhima.setBind("20");
//		        zhima.setBindTime(new Date(System.currentTimeMillis()));
//		        zhima.setUserId(Long.parseLong(userId));
//		        zhima.setScore(Double.parseDouble(zhimaScore));
//		        zhima.setOpenId("");
//		        zhimaMapper.save(zhima);
//			}
//			paramMap.put("zhimaState", "30");
//			paramMap.put("zhimaStateTime", new Date());
//		}
			
		paramMap.put("contactState", "30");
		paramMap.put("userId", userId);
		paramMap.put("contact_state_time", new Date());
		userAuthService.updateByUserId(paramMap);

		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "保存成功");
		
		
		
		return result;
	}
}
