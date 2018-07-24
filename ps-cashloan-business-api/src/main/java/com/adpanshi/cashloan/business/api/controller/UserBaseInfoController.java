package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.BorrowUserExamine;
import com.adpanshi.cashloan.business.cl.domain.UserAuth;
import com.adpanshi.cashloan.business.cl.domain.UserMessages;
import com.adpanshi.cashloan.business.cl.domain.YoudunLog;
import com.adpanshi.cashloan.business.cl.model.UploadFileRes;
import com.adpanshi.cashloan.business.cl.model.UserAuthModel;
import com.adpanshi.cashloan.business.cl.service.*;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.context.MessageConstant;
import com.adpanshi.cashloan.business.core.common.util.*;
import com.adpanshi.cashloan.business.core.common.util.Base64;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.core.model.UserWorkInfoModel;
import com.adpanshi.cashloan.business.core.service.CloanUserService;
import com.adpanshi.cashloan.business.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.business.rule.service.EquifaxReportService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
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
import java.text.ParseException;
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
	private UserMessagesService userMessagesService;

	@Resource
	private CloanUserService cloanUserService;
	
	@Resource
	private UserCardCreditLogService userCardCreditLogService;

	@Resource
	private YoudunLogService youdunLogService;
	
	@Resource
	private UserExamineService userExamineService;
	@Resource
	private BorrowMainService borrowMainService;
	@Resource
	private BorrowMainProgressService borrowMainProgressService;
	@Resource
	private VerifyPanService verifyPanService;
	@Resource
	private EquifaxReportService equifaxReportService;

	/**
	 * @description 根据userId获取用户信息
	 * @param userId

	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/getUserInfo.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserInfo(
			@RequestParam(value = "userId", required = true) String userId) {
		//wwpwan 上线时修改回来
		String serverHost = Global.getValue("server_host");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserBaseInfo info = userBaseInfoService.findSelective(paramMap);
		if (null != info ) {
			//@remarks: 新增返回有盾ocr 认证成功的oderNo @date: 20170728 @author: nmnl
			paramMap.put("type",YoudunLog.TYPE_IDCARD);
			paramMap.put("state",YoudunLog.STATE_IDCARD);
			YoudunLog youdunLog = youdunLogService.findSelectiveOne(paramMap);
			if(null != youdunLog)
				info.setYouDunIdCardOrderNo(youdunLog.getOrderNo());
			info.setLivingImg(serverHost + "/readFile.htm?path=" + info.getLivingImg());
			info.setFrontImg(serverHost + "/readFile.htm?path=" + info.getFrontImg());
			info.setBackImg(serverHost + "/readFile.htm?path=" + info.getBackImg());
			info.setOcrImg(serverHost + "/readFile.htm?path=" + info.getOcrImg());
		}
		return JsonUtil.newJson().addData(Constant.RESPONSE_DATA, info).toJson().toJSONString();
	}

	/**
	 * 查询用户工作信息
	 * 
	 * @param userId
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/getWorkInfo.htm", method = RequestMethod.GET)
	public void getWorkInfo(
			@RequestParam(value = "userId", required = true) Long userId) {
		UserWorkInfoModel info = userBaseInfoService.getWorkInfo(userId);
		if (StringUtil.isNotBlank(info.getWorkingImg())) {
			info.setWorkImgState(UserWorkInfoModel.WORK_IMG_ADDED);
		} else {
			info.setWorkImgState(UserWorkInfoModel.WORK_IMG_NO_ADD);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, info);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 查询用户工作信息
	 * 
	 * @param userId
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/getWorkImg.htm", method = RequestMethod.GET)
	public void getWorkImg(@RequestParam(value = "userId", required = true) Long userId) {
		String serverHost = Global.getValue("server_host");

		UserWorkInfoModel info = userBaseInfoService.getWorkInfo(userId);
		String workImgPath = info.getWorkingImg();
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotBlank(workImgPath)) {
			String[] imgArray = workImgPath.split(";");

			for (int i = 0; i < imgArray.length; i++) {
				if (StringUtil.isNotBlank(imgArray[i])) {
					list.add(serverHost + "/readFile.htm?path=" + imgArray[i]);
				}
			} 
		}

		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("list", list);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, listMap);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * @description 根据userId获取用户姓名
	 * @param userId

	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/getUserName.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
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
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/dict/list.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
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
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/authentication.htm", method = RequestMethod.POST)
	public void authentication(
			@RequestParam(value = "frontImg", required = false) MultipartFile frontImg,
			@RequestParam(value = "backImg", required = false) MultipartFile backImg,
			@RequestParam(value = "livingImg", required = false) MultipartFile livingImg,
			@RequestParam(value = "education") String education,
			@RequestParam(value = "liveAddr") String liveAddr,
			@RequestParam(value = "detailAddr") String detailAddr,
			@RequestParam(value = "liveCoordinate") String liveCoordinate,
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "middleName", required = false) String middleName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "idNo") String idNo,
			@RequestParam(value = "panNumber") String panNumber,
			@RequestParam(value = "pinCode") String pinCode,
			@RequestParam(value = "companyName",required=false) String companyName,
			@RequestParam(value = "companyPhone",required=false) String companyPhone,
			@RequestParam(value = "companyAddr",required=false) String companyAddr,
			@RequestParam(value = "workingYears",required=false) String workingYears,
			@RequestParam(value="national",required=false) String national,				
			@RequestParam(value="sex",required=false) String sex,
			@RequestParam(value="dateBirthday",required=false) String dateBirthday,
			@RequestParam(value="startCard",required=false) String startCard,
			@RequestParam(value="addrCard",required=false) String addrCard,
			@RequestParam(value="branchIssued",required=false) String branchIssued,
			@RequestParam(value="salary",required=false) String salary,
			HttpServletRequest request) throws Exception {

		//姓名
		String realName = StringUtil.jointName(firstName,middleName,lastName);
		//@remarks:去除所有string 前后空格.@date: 20170818 @author:nmnl
		Map<String, Object> resultMap = this.idCardSave(frontImg, backImg, livingImg, idNo.trim(), realName,
				education.trim(), liveAddr.trim(), detailAddr.trim(), liveCoordinate.trim(), national, sex,
				dateBirthday, startCard, addrCard, branchIssued, panNumber.trim(), pinCode.trim(), companyName,
				companyPhone, companyAddr, workingYears, salary
					);

		ServletUtils.writeToResponse(response, resultMap);
	}

	/**
	 * @description 获取用户信息
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/qryUserInfo.htm", method = RequestMethod.POST)
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

		resultMap.put(Constant.RESPONSE_DATA,data);
		resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, resultMap);
	}

	/**
	 * @description 更新用户信息
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/updateUserInfo.htm", method = RequestMethod.POST)
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
			@RequestParam(value="dateBirthday",required=false) String dateBirthday) throws Exception {

		logger.info(request.getParameterMap().toString());
		Map<String, Object> resultMap = this.idCardUpdate(frontImg, backImg, livingImg, idNo,
				education, liveAddr, liveAddr, sex, dateBirthday,  panNumber, pinCode, companyName,
				companyPhone, companyAddr, workingYears
		);

		ServletUtils.writeToResponse(response, resultMap);
	}

	/**
	 * @description 活体识别 - wangxb
	 * @param livingImg 自拍
	 * @throws Exception
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/livingIdentifyAuth.htm", method = RequestMethod.POST)
	public void livingIdentifyAuth(
			@RequestParam(value = "livingImg", required = false) MultipartFile livingImg,
            @RequestParam(value = "youDunIdCardFlag", required = false) String youDunIdCardFlag)
			throws Exception {
//		String xiaoeNew = request.getHeader("xiaoe_new");    //大流程更改，为了兼容新老流程增加此标记
//		logger.info(" xiaoeNew 活体识别 新->" + xiaoeNew);
//		boolean flag = false;
//		if (!com.adpanshi.cashloan.com.adpanshi.cashloan.api.core.common.util.StringUtil.isEmpty(xiaoeNew) && "1".equals(xiaoeNew)){
//			flag = true;
//		}
//		Map<String, Object> result = new HashMap<String, Object>();
		logger.info(" youDunIdCardFlag 活体识别 新->" + youDunIdCardFlag);
		//begin pantheon 20170629 增加有盾活体
//		if(StringUtils.isNotEmpty(youDunIdCardFlag) && "1".equals(youDunIdCardFlag)){//有盾标记
		Map<String, Object> result = this.youDunLivingIdentify(livingImg);
		
		//活体通过22状态的自动分配订单开始
		if(result.get("borrowId") != null && !result.get("borrowId").equals("")){
			Long borrowId = Long.valueOf(String.valueOf(result.get("borrowId")));
			if(borrowId != null){
				BorrowMain borrowList =  borrowMainService.getById(borrowId);
				if(BorrowModel.STATE_NEED_REVIEW.equals(borrowList.getState())){
					//此订单是否在我的信审订单存在
					BorrowUserExamine borrowUserExamine= userExamineService.listBorrowUserExamineInfo(borrowId);
					if(borrowUserExamine == null){
						userExamineService.sysUserToExamineOrderRelation( borrowList , "");
					}
				}
			}
		}
		result.remove("borrowId");
		//活体通过22状态的自动分配订单结束
		
//		}else{
//			result = this.livingIdentify(livingImg,flag);
//		}
		//end
		ServletUtils.writeToResponse(response,result);
	}

	/**
	 * 有盾活体

	 * @since  20170629
	 * @param livingImg
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> youDunLivingIdentify(MultipartFile livingImg)throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("borrowId","");

		long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());

		User user = cloanUserService.getById(userId);

		Map<String, Object> ubiMap = new HashMap<String, Object>();
		ubiMap.put("userId", userId);
		UserBaseInfo ubi = userBaseInfoService.findSelective(ubiMap);
		logger.info("----------->OCR活体认证userId={}->扫描到你的face",new Object[]{userId}); 
		if (user==null||ubi==null) {
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "用户信息不存在!");
		}else if(!userCardCreditLogService.isCanCredit(userId)){
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "今日请求认证次数已超过限制,请明日再尝试");
		}else {
			List<UploadFileRes> list = new LinkedList<>();
			saveMultipartFile(list, livingImg, userId+"");
			//@remarks:索引越界.IndexOutOfBoundsException: Index: 0, Size: 0 @date:20170905 @author:nmnl
			if(list.size() > 0) {
				ubi.setLivingImg(list.get(0).getResPath());
				logger.info(userId + " 有盾活体上传完成 文件路径-> " + list.get(0).getResPath() + " 开始保存用户信息userId: " + userId);
			}else{
				resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, "对不起,请再次认证!");
				logger.error("UserBaseInfoContorller-youdunLivingIdentify()-有盾活体上传失败-userId: " + userId);
				return resultMap;
			}
			int count = userBaseInfoService.updateById(ubi);
			logger.info("----------->更新userId={}的userBaseInfo【人脸识别照片】受影响的行数:{}条.",new Object[]{userId,count});
			if (count>0) {
				//OCR活体识别成功后-需要更新活体识别认证状态
				Map<String,Object> paramMap=new HashMap<String, Object>();
				paramMap.put("userId", userId);
				paramMap.put("livingIdentifyState", 30);
				int flag=userAuthService.updateByUserId(paramMap);
				logger.info("修改userId={}的OCR活体认证状态受影响的行数:{}条.",new Object[]{userId,flag});
				//begin pantheon 20170627 更新借款订单为通过机审，如果成功放款，建议审核就人工复审
				//1.根据人查询借款订单
				List<BorrowMain> borrowList =  borrowMainService.selectBorrowWithAudit(userId);
				if(borrowList == null || borrowList.size() == 0){
					//无此订单，不继续操作
					resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
					resultMap.put(Constant.RESPONSE_CODE_MSG, "请下载新版app,重新发起借款!");
					logger.error("UserBaseInfoContorller-youdunLivingIdentify()-活体认证之前-无法查询到借款订单-userId:"+userId);
					return resultMap;
				}else {
					String youdunLiving_open = Global.getValue("youdunLiving_open");
					if (youdunLiving_open!=null&&youdunLiving_open.equals("on")) {
						if (BorrowModel.STATE_TEMPORARY_AUTO_PASS.equals(borrowList.get(0).getState())) {
							//@remark ：追加日志  @date： 20170707  @author：M
							logger.info(ubi.getPhone() + " 有盾活体-> 临时状态-自动审核 ");
							//自动审核通过，直接放款
							borrowMainService.modifyState(borrowList.get(0).getId(), BorrowModel.STATE_AUTO_PASS);
							borrowMainProgressService.savePressState(borrowList.get(0), BorrowModel.STATE_AUTO_PASS);
							// 放款(改为活体识别成功且规则功过才放款)
//							borrowFacade.borrowLoan(borrowList.get(0), new Date());
							
							//需要人工复审
							borrowMainService.modifyState(borrowList.get(0).getId(), BorrowModel.STATE_NEED_REVIEW);
							borrowMainProgressService.savePressState(borrowList.get(0), BorrowModel.STATE_NEED_REVIEW);
						} else if (BorrowModel.STATE_TEMPORARY_NEED_REVIEW.equals(borrowList.get(0).getState())) {
							//@remark ：追加日志  @date： 20170707  @author：M
							logger.info(ubi.getPhone() + " 有盾活体-> 临时状态-自动审核未决待人工复审 ");
							//需要人工复审
							borrowMainService.modifyState(borrowList.get(0).getId(), BorrowModel.STATE_NEED_REVIEW);
							borrowMainProgressService.savePressState(borrowList.get(0), BorrowModel.STATE_NEED_REVIEW);
						}
					} else {
						//@remark ：用户下单活体成功后.都更改为需人工复审  @date： 20170905  @author：nmnl
						if (BorrowModel.STATE_TEMPORARY_AUTO_PASS.equals(borrowList.get(0).getState()) ||
								BorrowModel.STATE_TEMPORARY_NEED_REVIEW.equals(borrowList.get(0).getState())) {
							//@remark ：追加日志  @date： 20170707  @author：M
							logger.info(ubi.getPhone() + " 有盾活体-> 临时状态-自动审核未决待人工复审borrowState: " + borrowList.get(0).getState());
							//需要人工复审
							borrowMainService.modifyState(borrowList.get(0).getId(), BorrowModel.STATE_NEED_REVIEW);
							borrowMainProgressService.savePressState(borrowList.get(0), BorrowModel.STATE_NEED_REVIEW);
						}
					}
					//返回订单信息，用于人工复审自动分配订单使用
					resultMap.put("borrowId",borrowList.get(0).getId());
				}
				//end
			}
			
			resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}
		return resultMap;
	}

	/**
	 * 保存用户基本信息，进行黑名单规则校-有盾ORC
	 * @param frontImg
	 * @param backImg
	 * @param idNo
	 * @param realName
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
		}else if(ubi!=null&&!ubi.getRealName().toLowerCase().equals(realName.toLowerCase())){
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
				if(!ubi.getRealName().toLowerCase().equals(realName.toLowerCase())){
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
					panTem.put("firstName", ubi.getFirstName());
					panTem.put("lastName", ubi.getLastName());
					panTem.put("panNo", panNumber);
					panTem.put("userId", ubi.getUserId());
					int validPan = verifyPanService.verifyPanNumber(panTem);
					if (validPan == 0) {
						resultMap.put(Constant.RESPONSE_CODE, Constant.AUTHENTICATION_FAILED_CODE);
						resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.PAN_NO_AUTH_FAIL);
						logger.error(resultMap.toString());
						return resultMap;
					}

					//获取信用报告
					final String userIdE = ubi.getUserId() + "";
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
			String companyName, String companyPhone, String companyAddr, String workingYears)throws Exception {

		Map<String, Object> resultMap = new HashMap<>(16);

		long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
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
				int validPan = verifyPanService.verifyPanNumber(panTem);
				if(validPan == 0){
					resultMap.put(Constant.RESPONSE_CODE, Constant.AUTHENTICATION_FAILED_CODE);
					resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.PAN_NO_AUTH_FAIL);
					logger.error(resultMap.toString());
					return resultMap;
				}else {
					//获取信用报告
					final String userIdE = userInfo.getUserId() + "";
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
			int code = userBaseInfoService.updateById(userInfo);
			if (code > 0) {
				resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
				logger.info(resultMap.toString());
			}
		}else{
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, MessageConstant.FIRST_PERSONAL_INFORMATION);
			logger.info(resultMap.toString());
		}

		return resultMap;
	}

	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/workInfo/saveOrUpdate.htm", method = RequestMethod.POST)
	public void workInfoSave(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "companyName", required = false) String companyName,
			@RequestParam(value = "companyPhone", required = false) String companyPhone,
			@RequestParam(value = "companyAddr", required = false) String companyAddr,
			@RequestParam(value = "companyDetailAddr", required = false) String companyDetailAddr,
			@RequestParam(value = "companyCoordinate", required = false) String companyCoordinate,
			@RequestParam(value = "workingYears", required = false) String workingYears) {

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", userId);
		UserBaseInfo info = userBaseInfoService.findSelective(userMap);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("companyName", companyName);
		paramMap.put("companyPhone", companyPhone);
		paramMap.put("companyAddr", companyAddr);
		paramMap.put("companyDetailAddr", companyDetailAddr);
		paramMap.put("companyCoordinate", companyCoordinate);
		paramMap.put("workingYears", workingYears);
		paramMap.put("id", info.getId());
		boolean flag = userBaseInfoService.updateSelective(paramMap);

//		if (flag) {
//			Map<String, Object> authMap = new HashMap<String, Object>();
//			authMap.put("userId", userId);
//			authMap.put("workInfoState", UserAuthModel.STATE_VERIFIED);
//			userAuthService.updateByUserId(authMap);
//			// 添加银程端用户信息更新操作 @author yecy 20171128
//			yinChengFacade.updateInvestor(Long.parseLong(userId));
//		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (flag) {
			resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "保存成功");
		} else {
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "系统错误，保存失败");
		}
		ServletUtils.writeToResponse(response, resultMap);
	}

	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/workInfo/workImgSave.htm", method = RequestMethod.POST)
	public void workImgSava(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "workImgFir", required = false) MultipartFile workImgFir,
			@RequestParam(value = "workImgSec", required = false) MultipartFile workImgSec,
			@RequestParam(value = "workImgThr", required = false) MultipartFile workImgThr) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", userId);
		UserBaseInfo info = userBaseInfoService.findSelective(userMap);

		// 判断是否已经添加过 ，如果已经添加过 则将原图片路径添加过
		String workImg = "";
		if (null != info && StringUtil.isNotBlank(info.getWorkingImg())) {
			workImg = StringUtil.isNull(info.getWorkingImg());
		}

		StringBuilder buffer = new StringBuilder(workImg);

		if (workImgFir != null) {
			List<UploadFileRes> list = new LinkedList<>();
			response.setContentType("text/html;charset=utf8");
			saveMultipartFile(list, workImgFir, userId);
			buffer.append(list.get(0).getResPath()).append(";");
		}

		if (workImgSec != null) {
			List<UploadFileRes> list = new LinkedList<>();
			response.setContentType("text/html;charset=utf8");
			saveMultipartFile(list, workImgSec, userId);
			buffer.append(list.get(0).getResPath()).append(";");
		}

		if (workImgThr != null) {
			List<UploadFileRes> list = new LinkedList<>();
			response.setContentType("text/html;charset=utf8");
			saveMultipartFile(list, workImgThr, userId);
			buffer.append(list.get(0).getResPath()).append(";");
		}
		boolean flag = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		logger.info(userId + " workImgSava -> workingImg " + buffer.toString());
		if(StringUtils.isNotBlank(buffer)){
			paramMap.put("workingImg", StringUtil.isNull(buffer));
			if (info != null) {
				paramMap.put("id", info.getId());
			}
			flag = userBaseInfoService.updateSelective(paramMap);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (flag) {
			resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "保存成功");
		} else {
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "系统错误，保存失败");
		}
		ServletUtils.writeToResponse(response, resultMap);
	}

	private void saveMultipartFile(List<UploadFileRes> list, MultipartFile file, String userId) {
		if (file != null && !file.isEmpty()) {
			UploadFileRes model = save(file,userId);
			list.add(model);
		}
	}

	private UploadFileRes save(MultipartFile file,String userId) {
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
//		String filePath = "D:\\data\\image\\"+ fileType + s + dateStr + s ;
//		String filePath = s+"data"+s+"image"+s + fileType + s + dateStr + s ;
//		String filePath = "/home/jenkinstest/workspaces/xiaoedai"+s+"image"+s + fileType + s + System.currentTimeMillis() + s + picName;
//		if(s.equals("\\")){//			filePath="D:"+filePath;
//		}
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

//		try {
//			String sftpConfigName = "img_sftp";
//			SftpUtil sftp = new SftpUtil(sftpConfigName);
//			InputStream input = file.getInputStream();
//			sftp.upFile(filePath,fileName,input,true,true);
//		} catch (JSchException e) {
//			logger.error(e.getMessage(), e);
//			com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.cr.model.setErrorMsg("sftp连接初始化失败");
//			return com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.cr.model;
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//		}

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
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/contacts.htm", method = RequestMethod.POST)
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
		//@remarks:用户保存通讯录.统一更改保存成功
		/*boolean flag = userContactsService.deleteAndSave(infos,userId);
		if (flag) {
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, "保存成功");
		}else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, "保存失败");
		}*/
		ServletUtils.writeToResponse(response, returnMap);
	}

	/**
	 * @description 保存用户短信信息
	 * @param encodedInfo
	 * @param userId

	 * @return void
	 * @throws ParseException
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userInfo/messages.htm", method = RequestMethod.POST)
	public void messages(
			@RequestParam(value = "info", required = true) String encodedInfo,
			@RequestParam(value = "userId", required = true) String userId)
			throws ParseException {
		String info = new String(Base64.decode(encodedInfo));
		List<Map<String, Object>> infos = JsonUtil.parse(info, List.class);
		for (Map<String, Object> map : infos) {
			UserMessages clUserMessages = new UserMessages();
			String _name = String.valueOf(map.get("name"));
            //@remarks:替换emojj字符. @date:20170904 @author:nmnl
            _name = _name.replace("<","").replace(">",",");
            clUserMessages.setName(_name.length() > 20?_name.substring(0,20):_name);
			//@remarks:长度校验. @date:20170907 @author:nmnl
			String phone = String.valueOf(map.get("phone")).trim();
			clUserMessages.setPhone(phone.length() > 20?phone.substring(0,20):phone);
			clUserMessages.setTime(new Date(
					Long.parseLong(map.get("time") + "")));
			clUserMessages.setType(map.get("type") + "");
			clUserMessages.setUserId(Long.parseLong(userId));
			userMessagesService.insert(clUserMessages);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, "保存成功");
		ServletUtils.writeToResponse(response, returnMap);
	}

	/**
	 * @description 获取验证状态
	 * @param userId

	 * @return void
	 * @since 1.0.0
	 */
//	@RequestMapping(value = "/com.adpanshi.com.adpanshi.cashloan.api/act/mine/userAuth/getUserAuth.htm", method = RequestMethod.GET)
//	public void getUserAuth(
//			@RequestParam(value = "userId", required = true) String userId) {
//		/*Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("userId", userId);
//		UserAuth userAuth = null;
//		//begin pantheon 20170615 新版用户的芝麻状态取决于芝麻状态和同盾状态
//		String newVersion = request.getHeader("xiaoe");
//		if (!StringUtil.isEmpty(newVersion) && "1".equals(newVersion)) {
//			userAuth = userAuthService.getUserAuthNewVersion(paramMap);
//		}else {
//			userAuth = userAuthService.getUserAuth(paramMap);
//		}*/
//		//end
//		UserAuthData userAuth=userAuthService.getUserAuthByUserIdWithReset(Long.parseLong(userId));
//		Map<String,Object> curUserAuth=userAuthService.getUserAuthWithConfigByUserId(Long.parseLong(userId));
//		userAuth.setVerified(curUserAuth);
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//		result.put(Constant.RESPONSE_DATA, userAuth);
//		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
//		ServletUtils.writeToResponse(response, result);
//	}

	/**
	 * @description 获取用户验证状态
	 * @param userId
	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userAuth/getUserAuth.htm", method = RequestMethod.GET)
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
	
	
	@RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/userAuth/getAuthImgLogo.htm", method = RequestMethod.GET)
	public void getAuthImgLogo() {
		
		String serverHost = Global.getValue("server_host");
		String path = request.getSession().getServletContext().getRealPath("/");
		StringBuilder buffer = new StringBuilder(path)
				.append(File.separatorChar).append("static")
				.append(File.separatorChar).append("images")
				.append(File.separator).append("authImgLogo-xiaoeqiandai.png");
		
		Map<String, Object> data = new HashMap<>();
		String filePath = StringUtil.isNull(buffer);
		data.put("authImgLogo", serverHost + "/readFile.htm?path=" + filePath);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
}
