package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.ClExamineDictDetail;
import com.adpanshi.cashloan.business.cl.domain.UserAuth;
import com.adpanshi.cashloan.business.cl.enums.ExamineDictDetailEnum;
import com.adpanshi.cashloan.business.cl.enums.PettyloanSceneEnum;
import com.adpanshi.cashloan.business.cl.enums.UserAuthEnum;
import com.adpanshi.cashloan.business.cl.mapper.PettyLoanSceneMapper;
import com.adpanshi.cashloan.business.cl.mapper.UserAuthMapper;
import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.core.model.UserAuthData;
import com.adpanshi.cashloan.business.core.model.UserAuthExtra;
import com.adpanshi.cashloan.business.rule.mapper.UserEmerContactsMapper;
import com.alibaba.fastjson.JSONObject;
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
 *
 * 
 *
 */
 
@Service("userAuthService")
public class UserAuthServiceImpl extends BaseServiceImpl<UserAuth, Long> implements UserAuthService {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

	@Resource
	private UserAuthMapper userAuthMapper;

	//begin pantheon 20170602 用户紧急联系人认证
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
	 * 获取用户认证状态，关联同盾认证状态
	 *
	 * @param paramMap
	 * @return
	 */
	@Override
	public UserAuth getUserAuthNewVersion(Map<String, Object> paramMap) {
		UserAuth userAuth = userAuthMapper.findSelective(paramMap);

		//beign pantheon 20170615 芝麻认证状态 = 芝麻认证 + 同盾认证
		//20170706 M . 潘胜. 注释掉当前代码. （去除业务逻辑芝麻发与同盾同时认证）
//		if(userAuth == null){
//			logger.error("获取用户认证状态失败，无用户数据-UserAuthServiceImpl-getUserAuthNewVersion()-paramMap:"+ JSONObject.toJSONString(paramMap));
//			return null;
//		}
//
//		String tongdunState = StringUtils.isNotEmpty(userAuth.getTongdunState()) ? userAuth.getTongdunState() : UserAuthModel.STATE_NOT_CERTIFIED;
//		String zhimaState = StringUtils.isNotEmpty(userAuth.getZhimaState()) ? userAuth.getZhimaState() : UserAuthModel.STATE_NOT_CERTIFIED;
//
//		if(UserAuthModel.STATE_VERIFIED.equals(zhimaState) && UserAuthModel.STATE_VERIFIED.equals(tongdunState) ){
//			//两者状态都是通过，芝麻认证才通过
//			userAuth.setZhimaState(UserAuthModel.STATE_VERIFIED);
//			//logger.info("用户芝麻认证通过-UserAuthServiceImpl-getUserAuthNewVersion()-paramMap:"+ JSONObject.toJSONString(paramMap));
//		}else if(UserAuthModel.STATE_ERTIFICATION.equals(zhimaState) || UserAuthModel.STATE_ERTIFICATION.equals(tongdunState)){
//			//两者状态有一个是认证中，芝麻认证中
//			userAuth.setZhimaState(UserAuthModel.STATE_ERTIFICATION);
//			//logger.info("用户芝麻认证中-UserAuthServiceImpl-getUserAuthNewVersion()-paramMap:"+ JSONObject.toJSONString(paramMap));
//		}else if(UserAuthModel.STATE_FAIL.equals(zhimaState) || UserAuthModel.STATE_FAIL.equals(tongdunState)){
//			//两者状态有一个是失败，芝麻认证失败
//			userAuth.setZhimaState(UserAuthModel.STATE_FAIL);
//			//logger.info("用户芝麻认证失败-UserAuthServiceImpl-getUserAuthNewVersion()-paramMap:"+ JSONObject.toJSONString(paramMap));
//		}else{
//			userAuth.setZhimaState(UserAuthModel.STATE_NOT_CERTIFIED);
//			//logger.info("用户芝麻认证未完成-UserAuthServiceImpl-getUserAuthNewVersion()-paramMap:"+ JSONObject.toJSONString(paramMap));
//		}
		//end
		return userAuth;
	}

	@Override
	public Integer updateByUserId(Map<String, Object> paramMap) {
		return userAuthMapper.updateByUserId(paramMap);
	}

	@Override
	public UserAuth findSelective(long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userAuthMapper.findSelective(map);
	}

	@Override
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public int updateLoanSceneStateRefusedWithLoanScene(Integer state, Long userId, String orderView) {
		if (StringUtil.isEmpty(state, userId, orderView)) {
			logger.error("----------------->call updateLoanSceneStateRefusedWithLoanScene()缺少必要参数，跳过业务处理,state={},userId={},orderView={}.", new Object[]{state, userId, orderView});
			return 0;
		}
		if (!BorrowModel.STATE_REFUSED.equals(state.toString())) {
			logger.error("----------------->状态不是审核拒绝的直接返回,请忽略、不做业务处理-state={},userId={},orderView={}.", new Object[]{state, userId, orderView});
			return 0;
		}
		//表结构没有设置好、导致要做更多的逻辑处理
		//orderView 对json串进行解释-是否包含code=88888888且code_type=28的原因、如果有则行处理，否则跳过。
		List<ClExamineDictDetail> examineDictDetails = JSONObject.parseArray(orderView, ClExamineDictDetail.class);
		if (null == examineDictDetails || examineDictDetails.size() == 0) return 0;
		boolean isHit = Boolean.FALSE;//是否命中(是否包含code=88888888且code_type=28)
		for (ClExamineDictDetail detail : examineDictDetails) {
			if (StringUtil.isNotEmptys(detail.getCode()) && detail.getCode().equals(ExamineDictDetailEnum.CODE.TENEMENT_INCOME_CODE.getCode())) {
				isHit = true;
				break;
			}
		}
		int count = 0;
		if (isHit) {
			count += userAuthMapper.updateTenementIncomeStateWithTime(userId, UserAuthEnum.TENEMENT_INCOME_STATE.AUTH_FAIL.getCode());
			count += pettyLoanSceneMapper.updateLoanSceneExpiryByUserIdWithSceneType(userId, Arrays.asList(PettyloanSceneEnum.SCENE_TYPE.HOUSES_DETAIL.getKey(), PettyloanSceneEnum.SCENE_TYPE.SELF_HOUSES.getKey()));
		}
		return count;
	}


	@Override
	public UserAuthData getAuthConfigure() {
		String authJSON = Global.getValue(UserAuthEnum.AUTH_CONFIGURE);
		if (StringUtil.isBlank(authJSON)) return null;
		UserAuthData authData = new UserAuthData();
		JSONObject object = JSONObject.parseObject(authJSON);
		//可选认证项
		List<UserAuthExtra> optionals = new ArrayList<>();
		Object optionalObj = object.get(UserAuthEnum.OPTIONALS);
		if (StringUtil.isNotEmptys(optionalObj)) {
			String optionalStr = JSONObject.toJSONString(optionalObj);
			optionals = JSONObject.parseArray(optionalStr, UserAuthExtra.class);
		}
		//必选认证项
		Object requiredObj = object.get(UserAuthEnum.REQUIREDS);
		List<UserAuthExtra> requireds = new ArrayList<>();
		if (StringUtil.isNotEmptys(requiredObj)) {
			String requiredStr = JSONObject.toJSONString(requiredObj);
			requireds = JSONObject.parseArray(requiredStr, UserAuthExtra.class);
		}
		//隐藏项
		Object hiddenObj = object.get(UserAuthEnum.HIDDEN);
		List<UserAuthExtra> hiddens = new ArrayList<>();
		if (StringUtil.isNotEmptys(hiddenObj)) {
			String hiddenStr = JSONObject.toJSONString(hiddenObj);
			hiddens = JSONObject.parseArray(hiddenStr, UserAuthExtra.class);
		}
		//选择项(三选一)
		Object chooseObj = object.get(UserAuthEnum.CHOOSE);
		List<UserAuthExtra> chooses = new ArrayList<>();
		if (StringUtil.isNotEmptys(chooseObj)) {
			String chooseStr = JSONObject.toJSONString(chooseObj);
			chooses = JSONObject.parseArray(chooseStr, UserAuthExtra.class);
		}
		authData.setOptional(optionals.size());
		authData.setOptionals(optionals);
		authData.setRequired(requireds.size());
		authData.setRequireds(requireds);
		authData.setHidden(hiddens.size());
		authData.setHiddens(hiddens);
		authData.setChoose(chooses.size());
		authData.setChooses(chooses);
		authData.setTotal(optionals.size() + requireds.size() + hiddens.size() + chooses.size());
		return authData;
	}

	private void sort(List<UserAuthExtra> auth) {
		if (null == auth || auth.size() == 0) return;
		Collections.sort(auth, new Comparator<UserAuthExtra>() {
			@Override
			public int compare(UserAuthExtra obj1, UserAuthExtra obj2) {
				int o1 = obj1.getOrder();
				int o2 = obj2.getOrder();
				return Integer.compare(o1, o2);
			}
		});
	}

	@Override
	public Map<String, Object> handleAuthDataJonSql() {
		UserAuthData userAuthData = getAuthConfigure();
		if (null == userAuthData) return null;
		List<UserAuthExtra> optionals = userAuthData.getOptionals();
		List<UserAuthExtra> requireds = userAuthData.getRequireds();
		List<UserAuthExtra> hiddens = userAuthData.getHiddens();
		List<UserAuthExtra> chooses = userAuthData.getChooses();
		Map<String, Object> map = new HashMap<>();
		List<String> totalAuthList = new ArrayList<>();
		List<String> requiredList = new ArrayList<>();
		List<String> hiddenList = new ArrayList<>();
		List<String> chooseList = new ArrayList<>();
		for (UserAuthExtra required : requireds) {
			totalAuthList.add(StringUtil.getFieldName(required.getCode()));
			requiredList.add(StringUtil.getFieldName(required.getCode()));
		}
		for (UserAuthExtra optional : optionals) {
			totalAuthList.add(StringUtil.getFieldName(optional.getCode()));
		}
		for (UserAuthExtra hidden : hiddens) {
			totalAuthList.add(StringUtil.getFieldName(hidden.getCode()));
			hiddenList.add(StringUtil.getFieldName(hidden.getCode()));
		}
		for (UserAuthExtra choose : chooses) {
			totalAuthList.add(StringUtil.getFieldName(choose.getCode()));
			chooseList.add(StringUtil.getFieldName(choose.getCode()));
		}
		map.put(UserAuthEnum.TOTAL_AUTH_SQL, totalAuthList);
		map.put(UserAuthEnum.REQUIRED_SQL, requiredList);
		map.put(UserAuthEnum.HIDDEN_SQL, hiddenList);
		map.put(UserAuthEnum.CHOOSE_SQL, chooseList);
		map.put(UserAuthEnum.TOTAL_AUTH_QUALIFIED, optionals.size() + requireds.size() + hiddens.size() + chooseList.size());
		map.put(UserAuthEnum.REQUIRED_QUALIFIED, requireds.size());
		return map;
	}

	@Override
	public Map<String, Object> getUserAuthWithConfigByUserId(Long userId) {
		if (StringUtil.isEmpty(userId)) return null;
		Map<String, Object> data = handleAuthDataJonSql();
		data.put("userId", userId);
		return userAuthMapper.getUserAuthWithConfigByUserId(data);
	}
}