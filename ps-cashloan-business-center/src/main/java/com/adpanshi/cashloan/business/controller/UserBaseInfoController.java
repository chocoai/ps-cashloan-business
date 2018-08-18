package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.model.UploadFileRes;
import com.adpanshi.cashloan.core.common.util.Base64;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.dispatch.run.bo.DispatchRunResponseBo;
import com.adpanshi.cashloan.dispatch.run.domain.DispatchRunDomain;
import com.adpanshi.cashloan.rule.domain.UserEquipmentInfo;
import com.adpanshi.cashloan.rule.service.EquifaxReportService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.cl.domain.UserAuth;
import com.adpanshi.cashloan.cl.model.UserAuthModel;
import com.adpanshi.cashloan.cl.service.*;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.context.MessageConstant;
import com.adpanshi.cashloan.core.common.util.*;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.domain.User;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.model.BorrowModel;
import com.adpanshi.cashloan.core.service.CloanUserService;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.rule.service.UserContactsService;
import com.adpanshi.cashloan.system.service.SysAttachmentService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;


/**
 * 用户详情表Controller
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:08:04 
 * Copyright 粉团网路 arc All Rights Reserved
 *
 *
 */
@Controller
@Scope("prototype")
public class UserBaseInfoController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(UserBaseInfoController.class);

	@Resource
	private UserBaseInfoService userBaseInfoService;
	@Resource
	private UserAuthService userAuthService;
	@Resource
	private UserContactsService userContactsService;
	@Resource
	private CloanUserService cloanUserService;
	@Resource
	private BorrowMainService borrowMainService;
	@Resource
	private SysAttachmentService sysAttachmentService;
	@Resource
	private VerifyPanService verifyPanService;
	@Resource
	private EquifaxReportService equifaxReportService;
	@Resource
	private DispatchRunDomain dispatchRunDomain;
	@Resource
	private UserEquipmentInfoService userEquipmentInfoService;

	/**
	 * @description 根据userId获取用户姓名
	 * @param userId

	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/api/act/mine/userInfo/getUserName.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserName(
			@RequestParam(value = "userId", required = true) String userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserBaseInfo info = userBaseInfoService.findSelective(paramMap);
		paramMap.clear();
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("phone", info.getPhone());
		temp.put("name", info.getRealName());
		return JsonUtil.newJson().addData(Constant.RESPONSE_DATA, temp).toJson().toJSONString();
	}
	/**
	 * @description 根据type获取相应的字典项
	 * @param type

	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/api/act/dict/list.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getDicts(@RequestParam(value = "type") String type) {
		Map<String, Object> dicList = new HashMap<>(16);
		if (type != null && type != "") {
			String[] types = type.split(",");
			for (int i = 0; i < types.length; i++) {
				dicList.put(StringUtil.clearUnderline(types[i]) + "List", userBaseInfoService.getDictsCache(types[i]));
			}
		}
		if (!dicList.isEmpty()) {
			return JsonUtil.newJson().addData(Constant.RESPONSE_DATA, dicList).toJson().toJSONString();
		} else {
			return JsonUtil.newFailJson().toJson().toJSONString();
		}
	}
	
	/**
	 * @description 人证识别
	 * @param frontImg 正面
	 * @param backImg 背面
	 * @param firstName 名字
	 * @param lastName 姓氏
	 * @param idNo 身份证号
	 * @param education 学历
	 * @param liveAddr 居住地址
	 * @param detailAddr 居住详细地址
	 * @param national 民族
	 * @param dateBirthday 生日
	 * @param startCard 证件有效期
	 * @param addrCard 证件地址
	 * @param branchIssued 签发机构
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/act/mine/userInfo/authentication.htm", method = RequestMethod.POST)
	public void authentication(
			@RequestParam(value = "frontImg", required = false) MultipartFile frontImg,
			@RequestParam(value = "backImg", required = false) MultipartFile backImg,
			@RequestParam(value = "livingImg", required = false) MultipartFile livingImg,
			@RequestParam(value = "education") String education,
			@RequestParam(value = "liveAddr") String liveAddr,
			@RequestParam(value = "detailAddr") String detailAddr,
			@RequestParam(value = "liveCoordinate") String liveCoordinate,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "middleName", required = false) String middleName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "idNo") String idNo,
			@RequestParam(value = "panNumber") String panNumber,
			@RequestParam(value = "pinCode") String pinCode,
			@RequestParam(value = "companyName",required=false) String companyName,
			@RequestParam(value = "companyPhone",required=false) String companyPhone,
			@RequestParam(value = "companyAddr",required=false) String companyAddr,
			@RequestParam(value = "workingYears",required=false) String workingYears,
			@RequestParam(value = "national",required=false) String national,
			@RequestParam(value = "sex",required=false) String sex,
			@RequestParam(value = "dateBirthday",required=false) String dateBirthday,
			@RequestParam(value = "startCard",required=false) String startCard,
			@RequestParam(value = "addrCard",required=false) String addrCard,
			@RequestParam(value = "branchIssued",required=false) String branchIssued,
			@RequestParam(value = "salary",required=false) String salary,
			HttpServletRequest request) throws Exception {

		//姓名
		String realName = null;
		if(StringUtil.isNotBlank(firstName)||StringUtil.isNotBlank(lastName)){
			realName = StringUtil.jointName(firstName,middleName,lastName);
		}
		//@remarks:去除所有string 前后空格.@date: 20170818 @author:nmnl
		Map<String, Object> resultMap = this.idCardSave(frontImg, backImg, livingImg, idNo.trim(),realName,
				education.trim(), liveAddr.trim(), detailAddr.trim(), liveCoordinate.trim(), national, sex,
				dateBirthday, startCard, addrCard, branchIssued, panNumber.trim(), pinCode.trim(), companyName,
				companyPhone, companyAddr, workingYears, salary
					);

		ServletUtils.writeToResponse(response, resultMap);
	}

	/**
	 * @description 获取用户信息
	 */
	@RequestMapping(value = "/api/act/mine/userInfo/qryUserInfo.htm", method = RequestMethod.POST)
	public void qryUserInfo() throws Exception {
		long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		//通过userId获取用户基本信息
		Map<String, Object> ubiMap = new HashMap<>(16);
		ubiMap.put("userId",userId);
		UserBaseInfo ubi = userBaseInfoService.findSelective(ubiMap);
		Map<String, Object> resultMap = new HashMap<>(16);
		Map<String, Object> data = new HashMap<>(16);
		data.put("frontImg",ubi.getFrontImg());
		data.put("backImg",ubi.getBackImg());
		data.put("livingImg",ubi.getLivingImg());
		data.put("education",ubi.getEducation());
		data.put("liveAddr",ubi.getLiveAddr());
		data.put("idNo",ubi.getIdNo());
		data.put("panNumber",ubi.getPanNumber());
		data.put("pinCode",ubi.getPinCode());
		data.put("companyName",ubi.getCompanyName());
		data.put("companyPhone",ubi.getCompanyPhone());
		data.put("companyAddr",ubi.getCompanyAddr());
		data.put("workingYears",ubi.getWorkingYears());
		data.put("sex",ubi.getSex());
		data.put("dateBirthday",ubi.getDateBirthday());
		data.put("firstName",ubi.getFirstName());
		data.put("middleName",ubi.getMiddleName());
		data.put("lastName",ubi.getLastName());
		data.put("salary",ubi.getSalary());

		resultMap.put(Constant.RESPONSE_DATA,data);
		resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, resultMap);
	}

	/**
	 * @description 更新用户信息
	 */
	@RequestMapping(value = "/api/act/mine/userInfo/updateUserInfo.htm", method = RequestMethod.POST)
	public void updateUserInfo(
			@RequestParam(value = "frontImg", required = false) MultipartFile frontImg,
			@RequestParam(value = "backImg", required = false) MultipartFile backImg,
			@RequestParam(value = "livingImg", required = false) MultipartFile livingImg,
			@RequestParam(value = "education", required = false) String education,
			@RequestParam(value = "liveAddr", required = false) String liveAddr,
			@RequestParam(value = "idNo", required = false) String idNo,
			@RequestParam(value = "panNumber", required = false) String panNumber,
			@RequestParam(value = "pinCode", required = false) String pinCode,
			@RequestParam(value = "companyName", required = false) String companyName,
			@RequestParam(value = "companyPhone", required = false) String companyPhone,
			@RequestParam(value = "companyAddr", required = false) String companyAddr,
			@RequestParam(value = "workingYears", required = false) String workingYears,
			@RequestParam(value="sex",required=false) String sex,
			@RequestParam(value="salary",required=false) String salary,
			@RequestParam(value="dateBirthday",required=false) String dateBirthday) throws Exception {

		logger.info(request.getParameterMap().toString());
		Map<String, Object> resultMap = this.idCardUpdate(frontImg, backImg, livingImg, idNo,
				education, liveAddr, liveAddr, sex, dateBirthday,  panNumber, pinCode, companyName,
				companyPhone, companyAddr, workingYears, salary
		);

		ServletUtils.writeToResponse(response, resultMap);
	}
	@RequestMapping(value = "/api/act/mine/userInfo/updateHeadImg.htm", method = RequestMethod.POST)
	public void updateHead(
			@RequestParam(value = "headImg") MultipartFile headImg
	) throws Exception {
		Map resultMap = new HashMap(16);
		long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		//通过userId获取用户基本信息
		Map<String, Object> ubiMap = new HashMap<>(16);
		ubiMap.put("userId",userId);
		UserBaseInfo userInfo = userBaseInfoService.findSelective(ubiMap);
		UploadFileRes res = sysAttachmentService.saveFile(headImg,userId,"head");
		String path = res.getResPath();
		if(StringUtil.isNotBlank(path)){
			userInfo.setHeadImg(path);
			userBaseInfoService.updateById(userInfo);
			Map headImgMap = new HashMap();
			headImgMap.put("headImg",userInfo.getHeadImg());
			resultMap.put(Constant.RESPONSE_DATA,headImgMap);
			resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}else{
			resultMap.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, res.getErrorMsg());
		}
		ServletUtils.writeToResponse(response, resultMap);
	}

	/**
	 * 保存用户基本信息
	 * @param frontImg
	 * @param backImg
	 * @param idNo
	 * @param education
	 * @param liveAddr
	 * @param detailAddr
	 * @param liveCoordinate
	 * @param national 民族
	 * @param dateBirthday 生日
	 * @param startCard 证件有效期
	 * @param addrCard 证件地址
	 * @param branchIssued 签发机构
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> idCardSave(MultipartFile frontImg, MultipartFile backImg, MultipartFile livingImg,
                                          String idNo, String realName, String education, String liveAddr,
                                          String detailAddr, String liveCoordinate, String national, String sex,
                                          String dateBirthday, String startCard, String addrCard, String branchIssued,
                                          String panNumber, String pinCode , String companyName, String companyPhone,
                                          String companyAddr, String workingYears, String salary)throws Exception {
		Map<String, Object> resultMap = new HashMap<>(16);
		if(!StringUtil.isAadhaarId(idNo)){
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_PARAM_INSUFFICIENT);
			resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.ENTER_AADHAAR_CARD);
			logger.error(resultMap.toString());
			return resultMap;
		}

		String regionalName = userBaseInfoService.getRegionalName(pinCode);
		if(StringUtil.isBlank(regionalName)){
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_PARAM_INSUFFICIENT);
			resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.CITY_NOT_EXIST);
			logger.error(resultMap.toString());
			return resultMap;
		}

		long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		Map<String, Object> ubiMap = new HashMap<>(16);
		ubiMap.put("idNo", idNo);
		//通过idNo获取用户基本信息
		UserBaseInfo ubi = userBaseInfoService.findSelective(ubiMap);
		//通过userid获取用户信息
		User user = cloanUserService.getById(userId);
		//获取认证信息
		UserAuth ua = userAuthService.findSelective(userId);

		if (ubi!=null&&ubi.getUserId()!=userId) {
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.AADHAAR_ALREADY_EXISTS);
			logger.error(resultMap.toString());
		}else if(ubi!=null&&StringUtil.isNotBlank(realName)&&!ubi.getRealName().toLowerCase().equals(realName.toLowerCase())){
			resultMap.put(Constant.RESPONSE_CODE, Constant.AUTHENTICATION_FAILED_CODE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.USER_NAME_NOT_EQUAL);
			logger.error(resultMap.toString());
		}else if (ubi!=null&& UserAuthModel.STATE_VERIFIED.equals(ua.getIdState())&&ubi.getUserId()==userId) {
			ubi.setEducation(education);
			ubi.setLiveAddr(liveAddr);
			ubi.setLiveDetailAddr(detailAddr);
			ubi.setLiveCoordinate(liveCoordinate);
			int count = userBaseInfoService.updateById(ubi);

			if(count>0){
				resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
				logger.info(resultMap.toString());
			} else {
				resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
				logger.error(resultMap.toString());
			}
		} else if( !UserAuthModel.STATE_ERTIFICATION.equals(ua.getIdState()) ){
			ubiMap.clear();
			ubiMap.put("userId", userId);
			ubi = userBaseInfoService.findSelective(ubiMap);
			if (user==null||ubi==null) {
				resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.USER_NOT_EXIST);
				logger.error(resultMap.toString());
			}else {
				if(StringUtil.isNotBlank(realName)&&!ubi.getRealName().toLowerCase().equals(realName.toLowerCase())){
					resultMap.put(Constant.RESPONSE_CODE, Constant.AUTHENTICATION_FAILED_CODE);
					resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.USER_NAME_NOT_EQUAL);
					logger.error(resultMap.toString());
					return resultMap;
				}
				//不是认证失败
				if (!UserAuthModel.STATE_FAIL.equals(ua.getIdState())){
					List<UploadFileRes> list = new LinkedList<>();
					saveMultipartFile(list, frontImg, userId+"");
					saveMultipartFile(list, backImg, userId+"");

					//保存详细信息
					String frontPath = list.get(0).getResPath();
					String backPath = list.get(1).getResPath();
					if(livingImg!=null){
						saveMultipartFile(list, livingImg, userId+"");
						String livingPath = list.get(2).getResPath();
						ubi.setLivingImg(livingPath);
					}
					ubi.setFrontImg(frontPath);
					ubi.setBackImg(backPath);
				}

				ubi.setIdNo(idNo);
				ubi.setEducation(education);
				ubi.setLiveAddr(liveAddr);
				ubi.setLiveDetailAddr(detailAddr);
				ubi.setLiveCoordinate(liveCoordinate);
				ubi.setAge(StringUtil.getAgeByDOB(dateBirthday));
				ubi.setConstellation(StringUtil.getConstellation(idNo));
				ubi.setCompanyName(companyName);
				ubi.setCompanyPhone(companyPhone);
				ubi.setCompanyAddr(companyAddr);
				ubi.setCompanyDetailAddr(companyAddr);
				ubi.setWorkingYears(workingYears);
				ubi.setSalary(salary);
				if(StringUtil.isNotEmptys(national)){
					ubi.setNational(national);
				}
				if(StringUtil.isNotEmptys(sex)){
					ubi.setSex(sex);
				}
				if(StringUtil.isNotEmptys(dateBirthday)){
					ubi.setDateBirthday(dateBirthday);
				}
				if(StringUtil.isNotEmptys(startCard)){
					ubi.setStartCard(startCard);
				}
				if(StringUtil.isNotEmptys(addrCard)){
					ubi.setAddrCard(addrCard);
				}
				if(StringUtil.isNotEmptys(branchIssued)){
					ubi.setBranchIssued(branchIssued);
				}

				if (!"dev".equals(Global.getValue("app_environment"))) {
					//查询pan卡信息
					Map<String, Object> panTem = new HashMap<>(16);
					panTem.put("realName", ubi.getRealName().trim());
					panTem.put("panNo", panNumber);
					panTem.put("userId", ubi.getUserId());
					Map<String, Object> validPan = verifyPanService.verifyPanNumber(panTem);
					if (StringUtil.isBlank(validPan)) {
						resultMap.put(Constant.RESPONSE_CODE, Constant.AUTHENTICATION_FAILED_CODE);
						resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.PAN_NO_AUTH_FAIL);
						logger.error(resultMap.toString());
						return resultMap;
					}else if("10".equals(validPan.get("panAuth"))){
						resultMap.put(Constant.RESPONSE_CODE, Constant.AUTHENTICATION_FAILED_CODE);
						resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.PAN_NO_AUTH_FAIL_NAME);
						logger.error(resultMap.toString());
						return resultMap;
					}else{
						ubi.setFirstName(String.valueOf(validPan.get("firstName")));
						ubi.setLastName(String.valueOf(validPan.get("lastName")));
					}

					//获取信用报告
					final Long userIdE = ubi.getUserId();
					final String panNumberE = panNumber;
					final String firstNameE = ubi.getFirstName();
					final String lastNameE = ubi.getLastName();
					new Thread() {
						@Override
						public void run() {
							equifaxReportService.getEquifaxReport(userIdE, panNumberE, firstNameE, lastNameE);
						}
					}.start();
				}

				//印度特有start
				ubi.setPanNumber(panNumber);
				ubi.setPinCode(pinCode);
				//印度特有end

				int count = userBaseInfoService.updateById(ubi);

				Map<String,Object> returnMap = new HashMap<>(16);
				if (count>0) {
					//调用黑名单规则，未认证
					if(UserAuthModel.STATE_NOT_CERTIFIED.equals(ubi.getState())){
						returnMap.put("idState", UserAuthModel.STATE_FAIL);
						returnMap.put("userId", userId);
						logger.info("idNo={}为黑名单用户不予通过，拉黑原因为{}",idNo,ubi.getBlackReason());
						resultMap.put(Constant.RESPONSE_CODE, Constant.BiZ_IN_BLACKLIST);
						resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.BLACKLISTED);
						logger.error(resultMap.toString());
					}else {
						returnMap.put("idState", UserAuthModel.STATE_VERIFIED);
						returnMap.put("userId", userId);
						resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
						resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
						logger.info(resultMap.toString());

						//获取用户的全名/邮箱号
						ManagerUserModel managerUserModel = userBaseInfoService.getBaseModelByUserId(userId);
						//获取设备指纹
						Map<String,Object> userIdMap = new HashMap<>();
						userIdMap.put("userId", userId);
						UserEquipmentInfo userEquipmentInfo = userEquipmentInfoService.findSelective(userId);
						String blackBox = "null";
						if (userEquipmentInfo!=null){
							blackBox = userEquipmentInfo.getBlackBox();
						}

						//调用节点同盾保镖解析设备指纹
						Map nodeMap = new HashMap<>();
						String deviceFinger = "null";
						nodeMap.put("url",Global.getValue("td_url"));
						nodeMap.put("partner_code",Global.getValue("td_partner_code"));
						nodeMap.put("partner_key",Global.getValue("td_partner_key"));
						nodeMap.put("app_name",Global.getValue("td_app_name"));
						nodeMap.put("biz_code",Global.getValue("td_biz_code"));
						nodeMap.put("india_account_mobile",managerUserModel.getPhone());
						nodeMap.put("india_id_number",managerUserModel.getIdNo());
						nodeMap.put("india_account_name",managerUserModel.getRealName());
						nodeMap.put("black_box",blackBox);
						Map map = new HashMap<>();
						map.put("THIRD_PARTY_REQUEST_PARAMJSON",nodeMap);
						//调用同盾信贷保镖节点
						DispatchRunResponseBo dispatchRunResponseBo = dispatchRunDomain.startNode(
								managerUserModel.getUserDataId(),"india_oloan_tongdun_creditBodyguard",managerUserModel.getPhone(),
								managerUserModel.getLoginNameEmail(),managerUserModel.getIdNo(),managerUserModel.getRealName(),
								blackBox,map);
						//解析返回的数据获取设备指纹信息
						if(dispatchRunResponseBo!=null && dispatchRunResponseBo.getRet_code() == 10000){
							String metaData = String.valueOf(dispatchRunResponseBo.getData());
							if(metaData!=null && metaData.contains("device_info")){
								Map<String,Object> metaDataMap = JSON.parseObject(metaData,Map.class);
								String resultData = String.valueOf(metaDataMap.get("result_desc"));
								Map<String,Object> resultDataMap = JSON.parseObject(resultData,Map.class);
								String infoanalysis = String.valueOf(resultDataMap.get("INFOANALYSIS"));
								Map<String,Object> infoanalysisMap = JSON.parseObject(infoanalysis,Map.class);
								String deviceInfo = String.valueOf(infoanalysisMap.get("device_info"));
								Map<String,Object> deviceInfoMap = JSON.parseObject(deviceInfo,Map.class);
								if(deviceInfoMap.get("deviceId")!=null){
									deviceFinger = deviceInfoMap.get("deviceId").toString();
									userEquipmentInfo.setDeviceFinger(deviceFinger);
									int num = userEquipmentInfoService.updateById(userEquipmentInfo);
									if(num > 0){
										logger.info("用户设备同盾设备指纹信息更新成功："+userId);
									}
								}
							}
						}

						Map rawDataMap = new HashMap();
						Enumeration enuma = request.getParameterNames();
						while (enuma.hasMoreElements()) {
							String paramName = (String) enuma.nextElement();

							String paramValue = request.getParameter(paramName);
							//形成键值对应的map
							rawDataMap.put(paramName, paramValue);
						}
						rawDataMap.put("livingImg",managerUserModel.getLivingImg());
						rawDataMap.put("firstName",managerUserModel.getFirstName());
						rawDataMap.put("lastName",managerUserModel.getLastName());
						rawDataMap.put("registerAddr",managerUserModel.getRegisterAddr());
						//调起节点
						dispatchRunDomain.startNode(
								managerUserModel.getUserDataId(),"india_oloan_user_baseInfo",managerUserModel.getPhone(),
								managerUserModel.getLoginNameEmail(),managerUserModel.getIdNo(),managerUserModel.getRealName(),
								deviceFinger,rawDataMap);
					}
				}else {
					logger.info("用户不存在[userid={}]",ubi.getUserId());
					resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
					resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
				}
				userAuthService.updateByUserId(returnMap);
			}
		}
		return resultMap;
	}

	/**
	 * 验证是否有未完成订单
	 * @param userId
	 * @return
	 */
	private int verifyBorrow(Long userId){
		List<BorrowMain> borrowMainList = borrowMainService.selectBorrowByUserId(userId,2,1);
		if(borrowMainList.size()>0){
			BorrowMain borrowMain = borrowMainList.get(0);
			Map<String, Object> borrowMap = new HashMap<>(16);
			borrowMap.put("orderNo",borrowMain.getOrderNo());
			borrowMap.put("amount",borrowMain.getAmount());
			borrowMap.put("stateStr",borrowMain.getStateStr());
			borrowMap.put("createTime",borrowMain.getCreateTime());
			borrowMap.put("loanTime",borrowMain.getLoanTime());
			borrowMap.put("repayTime",borrowMain.getRepayTime());
			borrowMap.put("id",borrowMain.getId());
			String state = borrowMain.getState();
			borrowMap.put("state",state);
			if(BorrowModel.STATE_FINISH.equals(state)||BorrowModel.STATE_REMISSION_FINISH.equals(state) || BorrowModel.STATE_REFUSED.equals(state)){
				return 1;
			}
		}else{
			return 1;
		}
		return 0;
	}

	/**
	 *
	 * @param frontImg
	 * @param backImg
	 * @param livingImg
	 * @param idNo
	 * @param education
	 * @param liveAddr
	 * @param detailAddr
	 * @param sex
	 * @param dateBirthday
	 * @param panNumber
	 * @param pinCode
	 * @param companyName
	 * @param companyPhone
	 * @param companyAddr
	 * @param workingYears
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> idCardUpdate(
            MultipartFile frontImg, MultipartFile backImg, MultipartFile livingImg, String idNo, String education,
            String liveAddr, String detailAddr, String sex, String dateBirthday, String panNumber, String pinCode ,
            String companyName, String companyPhone, String companyAddr, String workingYears, String salary
	)throws Exception {

		Map<String, Object> resultMap = new HashMap<>(16);

		long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		if(verifyBorrow(userId)!=1){
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_PARAM_INSUFFICIENT);
			resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.AN_UNFINISHED_LOAN_OPERATION);
			logger.error(resultMap.toString());
			return resultMap;
		}
		//通过userId获取用户基本信息
		Map<String, Object> ubiMap = new HashMap<>(16);
		ubiMap.put("userId",userId);
		UserBaseInfo userInfo = userBaseInfoService.findSelective(ubiMap);

		if(StringUtil.isNotBlank(idNo) && !StringUtil.isAadhaarId(idNo) && !idNo.equals(userInfo.getIdNo())){
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_PARAM_INSUFFICIENT);
			resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.ENTER_AADHAAR_CARD);
			logger.error(resultMap.toString());
			return resultMap;
		}
		if(StringUtil.isNotBlank(pinCode) && pinCode.equals(userInfo.getPinCode())){
			String regionalName = userBaseInfoService.getRegionalName(pinCode);
			if(StringUtil.isBlank(regionalName)){
				resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_PARAM_INSUFFICIENT);
				resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.CITY_NOT_EXIST);
				logger.error(resultMap.toString());
				return resultMap;
			}
			userInfo.setPinCode(pinCode);
		}
		//获取认证信息
		UserAuth ua = userAuthService.findSelective(userId);
		if(UserAuthModel.STATE_VERIFIED.equals(ua.getIdState())){
			if(frontImg!=null){
				List<UploadFileRes> list = new LinkedList<>();
				saveMultipartFile(list, frontImg, userId+"");
				userInfo.setFrontImg(list.get(0).getResPath());
			}
			if(backImg!=null){
				List<UploadFileRes> list = new LinkedList<>();
				saveMultipartFile(list, backImg, userId+"");
				userInfo.setBackImg(list.get(0).getResPath());
			}
			if(livingImg!=null){
				List<UploadFileRes> list = new LinkedList<>();
				saveMultipartFile(list, livingImg, userId+"");
				userInfo.setLivingImg(list.get(0).getResPath());
			}
			if(StringUtil.isNotBlank(panNumber) && !panNumber.equals(userInfo.getPanNumber())){
				//查询pan卡信息
				Map<String, Object> panTem = new HashMap<>(16);
				panTem.put("firstName",userInfo.getFirstName());
				panTem.put("lastName",userInfo.getLastName());
				panTem.put("panNo",panNumber);
				panTem.put("userId",userInfo.getUserId());
				Map<String, Object> validPan = verifyPanService.verifyPanNumber(panTem);
				if(StringUtil.isNotBlank(validPan)){
					resultMap.put(Constant.RESPONSE_CODE, Constant.AUTHENTICATION_FAILED_CODE);
					resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.PAN_NO_AUTH_FAIL);
					logger.error(resultMap.toString());
					return resultMap;
				}else if("10".equals(validPan.get("panAuth"))){
					resultMap.put(Constant.RESPONSE_CODE, Constant.AUTHENTICATION_FAILED_CODE);
					resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.PAN_NO_AUTH_FAIL_NAME);
					logger.error(resultMap.toString());
					return resultMap;
				}else{
					//获取信用报告
					final Long userIdE = userInfo.getUserId();
					final String panNumberE = panNumber;
					final String firstNameE = userInfo.getFirstName();
					final String lastNameE = userInfo.getLastName();
					new Thread() {
						@Override
						public void run() {
							equifaxReportService.getEquifaxReport(userIdE, panNumberE, firstNameE, lastNameE);
						}
					}.start();
					userInfo.setPanNumber(panNumber);
				}
			}
			if (StringUtil.isNotBlank(idNo)) {
				userInfo.setIdNo(idNo);
			}
			if (StringUtil.isNotBlank(education)) {
				userInfo.setEducation(education);
			}
			if (StringUtil.isNotBlank(liveAddr)) {
				userInfo.setLiveAddr(liveAddr);
			}
			if (StringUtil.isNotBlank(detailAddr)) {
				userInfo.setLiveDetailAddr(detailAddr);
			}
			if (StringUtil.isNotBlank(sex)) {
				userInfo.setSex(sex);
			}
			if (StringUtil.isNotBlank(dateBirthday)) {
				userInfo.setDateBirthday(dateBirthday);
				userInfo.setAge(StringUtil.getAgeByDOB(dateBirthday));
			}
			if (StringUtil.isNotBlank(companyName)) {
				userInfo.setCompanyName(companyName);
			}
			if (StringUtil.isNotBlank(companyPhone)) {
				userInfo.setCompanyPhone(companyPhone);
			}
			if (StringUtil.isNotBlank(companyAddr)) {
				userInfo.setCompanyAddr(companyAddr);
			}
			if (StringUtil.isNotBlank(workingYears)) {
				userInfo.setWorkingYears(workingYears);
			}
			if (StringUtil.isNotBlank(salary)) {
				userInfo.setSalary(salary);
			}
			int code = userBaseInfoService.updateById(userInfo);
			if (code > 0) {
				resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
				logger.info(resultMap.toString());

				//获取用户的全名/邮箱号
				ManagerUserModel managerUserModel = userBaseInfoService.getBaseModelByUserId(userId);
				//获取设备指纹
				Map<String,Object> userIdMap = new HashMap<>();
				userIdMap.put("userId", userId);
				UserEquipmentInfo userEquipmentInfo = userEquipmentInfoService.findSelective(userId);
				String blackBox = "null";
				if (userEquipmentInfo!=null){
					blackBox = userEquipmentInfo.getBlackBox();
				}

				//调用节点解析同盾信贷保镖数据
				Map nodeMap = new HashMap<>();
				String deviceFinger = "null";
				nodeMap.put("url",Global.getValue("td_url"));
				nodeMap.put("partner_code",Global.getValue("td_partner_code"));
				nodeMap.put("partner_key",Global.getValue("td_partner_key"));
				nodeMap.put("app_name",Global.getValue("td_app_name"));
				nodeMap.put("biz_code",Global.getValue("td_biz_code"));
				nodeMap.put("india_account_mobile",managerUserModel.getPhone());
				nodeMap.put("india_id_number",managerUserModel.getIdNo());
				nodeMap.put("india_account_name",managerUserModel.getRealName());
				nodeMap.put("black_box",blackBox);
				//调用同盾信贷保镖节点
				DispatchRunResponseBo dispatchRunResponseBo = dispatchRunDomain.startNode(
						managerUserModel.getUserDataId(),"india_oloan_tongdun_creditBodyguard",managerUserModel.getPhone(),
						managerUserModel.getLoginNameEmail(),managerUserModel.getIdNo(),managerUserModel.getRealName(),
						blackBox,nodeMap);
				//解析返回的数据获取设备指纹信息
				if(dispatchRunResponseBo!=null && dispatchRunResponseBo.getRet_code() == 10000){
					String metaData = String.valueOf(dispatchRunResponseBo.getData());
					if(metaData!=null && metaData.contains("device_info")){
						Map<String,Object> metaDataMap = JSON.parseObject(metaData,Map.class);
						String resultData = String.valueOf(metaDataMap.get("result_desc"));
						Map<String,Object> resultDataMap = JSON.parseObject(resultData,Map.class);
						String infoanalysis = String.valueOf(resultDataMap.get("INFOANALYSIS"));
						Map<String,Object> infoanalysisMap = JSON.parseObject(infoanalysis,Map.class);
						String deviceInfo = String.valueOf(infoanalysisMap.get("device_info"));
						Map<String,Object> deviceInfoMap = JSON.parseObject(deviceInfo,Map.class);
						if(deviceInfoMap.get("deviceId")!=null){
							deviceFinger = deviceInfoMap.get("deviceId").toString();
							userEquipmentInfo.setDeviceFinger(deviceFinger);
							int num = userEquipmentInfoService.updateById(userEquipmentInfo);
							if(num > 0){
								logger.info("用户设备同盾设备指纹信息更新成功："+userId);
							}
						}
					}
				}

				Map rawDataMap = new HashMap();
				Enumeration enuma = request.getParameterNames();
				while (enuma.hasMoreElements()) {
					String paramName = (String) enuma.nextElement();

					String paramValue = request.getParameter(paramName);
					//形成键值对应的map
					rawDataMap.put(paramName, paramValue);
				}
				//调起节点
				dispatchRunDomain.startNode(managerUserModel.getUserDataId(),"india_oloan_user_baseInfo",
						managerUserModel.getPhone(),managerUserModel.getLoginNameEmail(),managerUserModel.getIdNo(),
						managerUserModel.getRealName(),deviceFinger,rawDataMap);
			}
		}else{
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.FIRST_PERSONAL_INFORMATION);
			logger.info(resultMap.toString());
		}

		return resultMap;
	}

	private void saveMultipartFile(List<UploadFileRes> list, MultipartFile file, String userId) {
		if (file != null && !file.isEmpty()) {
			UploadFileRes model = save(file,userId);
			list.add(model);
		}
	}

	private UploadFileRes save(MultipartFile file, String userId) {
		UploadFileRes model = new UploadFileRes();
		model.setCreateTime(DateUtil.getNow());

		// 文件名称
		String picName = file.getOriginalFilename();

		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File newFile = (File) fi.getStoreLocation();
		logger.debug("图片----------"+newFile);
		// 文件格式
		String fileType = FileTypeUtil.getFileType(newFile);
		if (StringUtil.isBlank(fileType) || !FileTypeUtil.isImage(newFile, fileType)) {
			model.setErrorMsg("图片格式错误或内容不规范");
			logger.error(model.getErrorMsg());
			return model;
		}
		// 校验图片大小
		Long picSize = file.getSize();
		if (picSize.compareTo(20971520L) > 0) {
			model.setErrorMsg("文件超出20M大小限制");
			logger.error(model.getErrorMsg());
			return model;
		}
		// 保存文件
		//wwpwan 上线后修改
		String s="/";
		String dateStr = DateUtil.dateStr(new Date(),DateUtil.DATEFORMAT_STR_012);
		String fileName = userId+"_"+System.currentTimeMillis()+"_"+picName;
		String filePath = "/data/htdocs/image/"+ fileType + s + dateStr + s;

		File files = new File(filePath+fileName);
		if (!files.exists()) {
			try {
				files.mkdirs();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				model.setErrorMsg("文件目录不存在");
				return model;
			}
		}
		try {
			file.transferTo(files);
		} catch (IllegalStateException | IOException e) {
			logger.error(e.getMessage(), e);
		}

		// 转存文件
		model.setResPath(filePath+fileName);
		model.setFileName(picName);
		model.setFileFormat(fileType);
		model.setFileSize(new BigDecimal(picSize));
		return model;
	}

	/**
	 * @description 保存用户通讯录信息
	 * @param encodedInfo
	 * @param userId

	 * @return void
	 * @since 1.0.0
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/api/act/mine/userInfo/contacts.htm", method = RequestMethod.POST)
	public void contacts(
			@RequestParam(value = "info", required = true) String encodedInfo,
			@RequestParam(value = "userId", required = true) String userId) {
		String info = null;
		try {
			info = new String(Base64.decode(encodedInfo),"UTF-8");
			logger.info(info);
		} catch (UnsupportedEncodingException e) {
			logger.error("",e);
		}
		List<Map<String, Object>> infos = JsonUtil.parse(info, List.class);
		userContactsService.deleteAndSave(infos,userId);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, "保存成功");

		//获取用户的全名/邮箱号
		ManagerUserModel managerUserModel = userBaseInfoService.getBaseModelByUserId(Long.parseLong(userId));
		//获取设备指纹
		UserEquipmentInfo userEquipmentInfo = userEquipmentInfoService.findSelective(Long.parseLong(userId));
		String blackBox = "null";
		if (userEquipmentInfo!=null){
			blackBox = userEquipmentInfo.getDeviceFinger();
		}
		Map nodeMap = new HashMap(4);
		nodeMap.put("THIRD_PARTY_METADATA",infos);
		//调起节点
		dispatchRunDomain.startNode(
				managerUserModel.getUserDataId(),"india_oloan_app_contacts",managerUserModel.getPhone(),
				managerUserModel.getLoginNameEmail(),managerUserModel.getIdNo(),managerUserModel.getRealName(),
				blackBox,nodeMap);
		ServletUtils.writeToResponse(response, returnMap);
	}

	/**
	 * @description 获取用户验证状态
	 * @param userId
	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/api/act/mine/userAuth/getUserAuth.htm", method = RequestMethod.GET)
	public void getUserAuthInfo(
			@RequestParam(value = "userId", required = true) String userId) {
		UserAuth userAuth=userAuthService.findSelective(Long.parseLong(userId));
		logger.info("id为"+userId+"的用户验证状态信息为"+ JSONObject.toJSONString(userAuth));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_DATA, userAuth);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	@RequestMapping(value = "/api/act/mine/userInfo/saveCallRecord.htm", method = RequestMethod.POST)
	public void savecallRecord(@RequestParam(value = "info",required = true)String encodedInfo,
							   @RequestParam(value = "userId", required = true) String userId){
		String info = null;
		try {
			info = new String(Base64.decode(encodedInfo),"UTF-8");
			logger.info(info);
		} catch (UnsupportedEncodingException e) {
			logger.error("",e);
		}
		List<Map<String, String>> infos = JsonUtil.parse(info, List.class);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, "保存成功");

		//获取用户的全名/邮箱号
		ManagerUserModel managerUserModel = userBaseInfoService.getBaseModelByUserId(Long.parseLong(userId));
		//获取设备指纹
		UserEquipmentInfo userEquipmentInfo = userEquipmentInfoService.findSelective(Long.parseLong(userId));
		String blackBox = "null";
		if (userEquipmentInfo!=null){
			blackBox = userEquipmentInfo.getDeviceFinger();
		}
		Map nodeMap = new HashMap(4);
		nodeMap.put("THIRD_PARTY_METADATA",infos);
		//调起节点
		dispatchRunDomain.startNode(
				managerUserModel.getUserDataId(),"india_oloan_app_callRecord",managerUserModel.getPhone(),
				managerUserModel.getLoginNameEmail(),managerUserModel.getIdNo(),managerUserModel.getRealName(),
				blackBox,nodeMap);
		ServletUtils.writeToResponse(response, returnMap);

	}

}
