package com.adpanshi.cashloan.cl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.cl.domain.ClExamineDictDetail;
import com.adpanshi.cashloan.cl.domain.UserAuth;
import com.adpanshi.cashloan.cl.enums.ExamineDictDetailEnum;
import com.adpanshi.cashloan.cl.enums.PettyloanSceneEnum;
import com.adpanshi.cashloan.cl.enums.UserAuthEnum;
import com.adpanshi.cashloan.cl.enums.UserAuthEnum.WHAT_AUTH;
import com.adpanshi.cashloan.cl.mapper.PettyLoanSceneMapper;
import com.adpanshi.cashloan.cl.mapper.UserAuthMapper;
import com.adpanshi.cashloan.cl.model.UserAuthModel;
import com.adpanshi.cashloan.cl.service.UserAuthService;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.ReflectUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.core.model.BorrowModel;
import com.adpanshi.cashloan.core.model.UserAuthData;
import com.adpanshi.cashloan.core.model.UserAuthExtra;
import com.adpanshi.cashloan.core.umeng.beans.Extra;
import com.adpanshi.cashloan.rule.domain.UserEmerContacts;
import com.adpanshi.cashloan.rule.mapper.UserEmerContactsMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户认证信息表ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:18:17
 *
 */
 
@Service("userAuthService")
public class UserAuthServiceImpl extends BaseServiceImpl<UserAuth, Long> implements UserAuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);
   
    @Resource
    private UserAuthMapper userAuthMapper;

	@Resource
	private UserEmerContactsMapper userEmerContactsMapper;

	@Resource
	private UserBaseInfoMapper userBaseInfoMapper;
    
    @Resource
    private PettyLoanSceneMapper pettyLoanSceneMapper;
    
	@Override
	public BaseMapper<UserAuth, Long> getMapper() {
		return userAuthMapper;
	}

	/**
	 * 认证用户紧急联系人
	 * @param userId
	 */
	@Override
	public boolean authUserEmerContacts(long userId){
		//0.查询用户信息
		UserBaseInfo userBaseInfo =  userBaseInfoMapper.findByUserId(userId);
		if(userBaseInfo == null){
			logger.error("认证用户紧急联系人失败：无法查询到用户信息。UserAuthServiceImpl-authUserEmerContacts-userId:"+userId);
			return false;
		}

		//1.查询用户紧急联系人
		Map<String,Object> temp=new HashMap<>(16);
		temp.put("userId",userId);
		List<UserEmerContacts> userEmerContactsList = userEmerContactsMapper.listSelective(temp);
		if(userEmerContactsList == null || userEmerContactsList.size() == 0){
			logger.error("认证用户紧急联系人失败：无法查询到用户紧急联系人。UserAuthServiceImpl-authUserEmerContacts-userId:"+userId);
			return false;
		}

		return  true;
	}
	
	@Override
	public UserAuth getUserAuth(Map<String, Object> paramMap) {
		UserAuth userAuth = userAuthMapper.findSelective(paramMap);
		return userAuth;
	}

	/**
	 * 获取用户认证状态，关联同盾认证状态
	 * @param paramMap
	 * @return
	 */
	@Override
	public UserAuth getUserAuthNewVersion(Map<String, Object> paramMap) {
		UserAuth userAuth = userAuthMapper.findSelective(paramMap);

		return userAuth;
	}

	@Override
	public Integer updateByUserId(Map<String, Object> paramMap) {
		return userAuthMapper.updateByUserId(paramMap);
	}
	
	@Override
	public Page<UserAuthModel> listUserAuth(Map<String, Object> params,
                                            int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		if (null != params && params.size() > 0)
		{
			// 集合不为空则开始递归去除字符串的空格 zy
			for(Map.Entry<String, Object>  entry : params.entrySet())
			{
				if(null != params.get(entry.getKey()) && params.get(entry.getKey()) != "") {
					params.put(entry.getKey(), params.get(entry.getKey()).toString().trim());
				}
			}
		}
		List<UserAuthModel> list = userAuthMapper.listUserAuthModel(params);
		return (Page<UserAuthModel>) list;
	}

	@Override
	public UserAuth findSelective(long userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userAuthMapper.findSelective(map);
	}
	
	@Override
	public UserAuth findSelectiveWithVersion(long userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userAuthMapper.findSelectiveWithVersion(map);
	}

	@Override
	@Transactional(rollbackFor = {RuntimeException.class,Exception.class})
	public int updateLoanSceneStateRefusedWithLoanScene(Integer state, Long userId, String orderView) {
		if(StringUtil.isEmpty(state,userId,orderView)){
			logger.error("----------------->call updateLoanSceneStateRefusedWithLoanScene()缺少必要参数，跳过业务处理,state={},userId={},orderView={}.",new Object[]{state,userId,orderView});
			return 0;
		}
		if(!BorrowModel.STATE_REFUSED.equals(state.toString())){
			logger.error("----------------->状态不是审核拒绝的直接返回,请忽略、不做业务处理-state={},userId={},orderView={}.",new Object[]{state,userId,orderView});
			return 0;
		}
		//表结构没有设置好、导致要做更多的逻辑处理
		//orderView 对json串进行解释-是否包含code=88888888且code_type=28的原因、如果有则行处理，否则跳过。
		List<ClExamineDictDetail> examineDictDetails= JSONObject.parseArray(orderView, ClExamineDictDetail.class);
		if(null==examineDictDetails || examineDictDetails.size()==0){return 0;}
		//是否命中(是否包含code=88888888且code_type=28)
		boolean isHit=Boolean.FALSE;
		for(ClExamineDictDetail detail:examineDictDetails){
			if(StringUtil.isNotEmptys(detail.getCode()) && detail.getCode().equals(ExamineDictDetailEnum.CODE.TENEMENT_INCOME_CODE.getCode())){
				isHit=true;
				break;
			}
		}
		int count=0;
		if(isHit){
			count+=userAuthMapper.updateTenementIncomeStateWithTime(userId, UserAuthEnum.TENEMENT_INCOME_STATE.AUTH_FAIL.getCode());
			count+=pettyLoanSceneMapper.updateLoanSceneExpiryByUserIdWithSceneType(userId, Arrays.asList(PettyloanSceneEnum.SCENE_TYPE.HOUSES_DETAIL.getKey(),PettyloanSceneEnum.SCENE_TYPE.SELF_HOUSES.getKey()));
		}
		return count;
	}

	@Override
	public boolean houseIncomeIsAuth(Long userId) {
		Map<String,Object> map=new HashMap<>(16);
		map.put("userId", userId);
		map.put("tenementIncomeState", UserAuthEnum.TENEMENT_INCOME_STATE.AUTHENTICATED.getCode());
		return userAuthMapper.queryCount(map)==1;
	}

	@Override
	public UserAuthData getUserAuthByUserIdWithReset(Long userId) {
		if(StringUtil.isEmpty(userId)) {return null;}
		UserAuth userAuth=findSelective(userId);
		if(null==userAuth) {return null;}
		UserAuthData authConfigure= getAuthConfigure();
		if(null==authConfigure) {return null;}
		List<UserAuthExtra> optionals=authConfigure.getOptionals();
		List<UserAuthExtra> requireds=authConfigure.getRequireds();
		List<UserAuthExtra> hiddens=authConfigure.getHiddens();
		List<UserAuthExtra> chooses=authConfigure.getChooses();
		Map<String,Object> map=ReflectUtil.clzToMap(userAuth);
		Iterator<String> iterator= map.keySet().iterator();
		while(iterator.hasNext()){
			String key=iterator.next();
			Object value=map.get(key);
			for(UserAuthExtra optional:optionals){
				if(optional.getCode().equals(key)){optional.setStatus(StringUtil.isNotEmptys(value)?String.valueOf(value):null);}
			}
			for(UserAuthExtra required:requireds){
				if(required.getCode().equals(key)){required.setStatus(StringUtil.isNotEmptys(value)?String.valueOf(value):null);}
			}
			for(UserAuthExtra hidden:hiddens){
				if(hidden.getCode().equals(key)) {hidden.setStatus(StringUtil.isNotEmptys(value)?String.valueOf(value):null);}
			}
			for(UserAuthExtra choose:chooses){
				if(choose.getCode().equals(key)) {choose.setStatus(StringUtil.isNotEmptys(value)?String.valueOf(value):null);}
			}
		}
		sort(optionals);
		sort(requireds);
		sort(hiddens);
		sort(chooses);
		return authConfigure;
	}
	
	@Override
	public UserAuthData getAuthConfigure() {
		String authJSON=Global.getValue(UserAuthEnum.AUTH_CONFIGURE);
		if(StringUtil.isBlank(authJSON)) {return null;}
		UserAuthData authData=new UserAuthData();
		JSONObject object= JSONObject.parseObject(authJSON);
		//可选认证项
		List<UserAuthExtra> optionals=new ArrayList<>();
		Object optionalObj=object.get(UserAuthEnum.OPTIONALS);
		if(StringUtil.isNotEmptys(optionalObj)){
			String optionalStr= JSONObject.toJSONString(optionalObj);
			optionals= JSONObject.parseArray(optionalStr, UserAuthExtra.class);
		}
		//必选认证项
		Object requiredObj=object.get(UserAuthEnum.REQUIREDS);
		List<UserAuthExtra> requireds=new ArrayList<>();
		if(StringUtil.isNotEmptys(requiredObj)){
			String requiredStr= JSONObject.toJSONString(requiredObj);
			requireds= JSONObject.parseArray(requiredStr, UserAuthExtra.class);
		}
		//隐藏项
		Object hiddenObj=object.get(UserAuthEnum.HIDDEN);
		List<UserAuthExtra> hiddens=new ArrayList<>();
		if(StringUtil.isNotEmptys(hiddenObj)){
			String hiddenStr= JSONObject.toJSONString(hiddenObj);
			hiddens= JSONObject.parseArray(hiddenStr, UserAuthExtra.class);
		}
		//选择项(三选一)
		Object chooseObj=object.get(UserAuthEnum.CHOOSE);
		List<UserAuthExtra> chooses=new ArrayList<>();
		if(StringUtil.isNotEmptys(chooseObj)){
			String chooseStr= JSONObject.toJSONString(chooseObj);
			chooses= JSONObject.parseArray(chooseStr, UserAuthExtra.class);
		}
		authData.setOptional(optionals.size());
		authData.setOptionals(optionals);
		authData.setRequired(requireds.size());
		authData.setRequireds(requireds);
		authData.setHidden(hiddens.size());
		authData.setHiddens(hiddens);
		authData.setChoose(chooses.size());
		authData.setChooses(chooses);
		authData.setTotal(optionals.size()+requireds.size()+hiddens.size()+chooses.size());
		return authData;
	}
	
	private void sort(List<UserAuthExtra> auth){
		if(null==auth || auth.size()==0) {return;}
		Collections.sort(auth, new Comparator<UserAuthExtra>() {
			@Override
			public int compare(UserAuthExtra obj1, UserAuthExtra obj2) {
				int o1=obj1.getOrder();
				int o2=obj2.getOrder();
				return Integer.compare(o1,o2);
			}
		});
	}

	@Override
	public Map<String, Object> handleAuthDataJonSql() {
		UserAuthData userAuthData=getAuthConfigure();
		if(null==userAuthData) {return null;}
		List<UserAuthExtra> optionals= userAuthData.getOptionals();
		List<UserAuthExtra> requireds= userAuthData.getRequireds();
		List<UserAuthExtra> hiddens= userAuthData.getHiddens();
		List<UserAuthExtra> chooses= userAuthData.getChooses();
		Map<String,Object> map=new HashMap<>(16);
		List<String> totalAuthList=new ArrayList<>();
		List<String> requiredList=new ArrayList<>();
		List<String> hiddenList=new ArrayList<>();
		List<String> chooseList=new ArrayList<>();
		for(UserAuthExtra required:requireds){
			totalAuthList.add(StringUtil.getFieldName(required.getCode()));
			requiredList.add(StringUtil.getFieldName(required.getCode()));
		}
		for(UserAuthExtra optional: optionals){
			totalAuthList.add(StringUtil.getFieldName(optional.getCode()));
		}
		for(UserAuthExtra hidden: hiddens){
			totalAuthList.add(StringUtil.getFieldName(hidden.getCode()));
			hiddenList.add(StringUtil.getFieldName(hidden.getCode()));
		}
		for(UserAuthExtra choose:chooses){
			totalAuthList.add(StringUtil.getFieldName(choose.getCode()));
			chooseList.add(StringUtil.getFieldName(choose.getCode()));
		}
		map.put(UserAuthEnum.TOTAL_AUTH_SQL, totalAuthList);
		map.put(UserAuthEnum.REQUIRED_SQL, requiredList);
		map.put(UserAuthEnum.HIDDEN_SQL, hiddenList);
		map.put(UserAuthEnum.CHOOSE_SQL, chooseList);
		map.put(UserAuthEnum.TOTAL_AUTH_QUALIFIED, optionals.size()+requireds.size()+hiddens.size()+chooseList.size());
		map.put(UserAuthEnum.REQUIRED_QUALIFIED, requireds.size());
		return map;
	}

	@Override
	public Map<String, Object> getUserAuthWithConfigByUserId(Long userId) {
		if(StringUtil.isEmpty(userId)) {return null;}
		Map<String,Object> data=handleAuthDataJonSql();
		data.put("userId", userId);
		return userAuthMapper.getUserAuthWithConfigByUserId(data);
	}

	@Override
	public boolean isVerified(Long userId, WHAT_AUTH whatAuth) {
		if(StringUtil.isEmpty(userId,whatAuth)) {return false;}
		UserAuth userAuth=userAuthMapper.findSelective(new Extra("userId", userId));
		if(null==userAuth) {return false;}
		Object value=ReflectUtil.invokeGetMethod(UserAuth.class, userAuth, whatAuth.getCode());
		logger.info("userId={}的"+whatAuth.getName()+"的认证状态为:{}.",new Object[]{userId,value});
		return (StringUtil.isNotBlank(value)&&(value.toString().equals(UserAuthModel.STATE_VERIFIED) || value.toString().equals(UserAuthModel.STATE_ERTIFICATION)));
	}

	@Override
	public String getUserAuthState(Long userId, WHAT_AUTH whatAuth) {
		if(StringUtil.isEmpty(userId,whatAuth)) {return UserAuthModel.STATE_NOT_CERTIFIED;}
		UserAuth userAuth=userAuthMapper.findSelective(new Extra("userId", userId));
		if(null==userAuth) {return UserAuthModel.STATE_NOT_CERTIFIED;}
		Object value=ReflectUtil.invokeGetMethod(UserAuth.class, userAuth, whatAuth.getCode());
		return null!=value?value.toString():UserAuthModel.STATE_NOT_CERTIFIED;
	}
}