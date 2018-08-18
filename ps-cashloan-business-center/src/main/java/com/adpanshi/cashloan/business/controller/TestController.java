package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.cl.domain.OperatorReqLog;
import com.adpanshi.cashloan.cl.domain.UserAuth;
import com.adpanshi.cashloan.cl.mapper.OperatorReqLogMapper;
import com.adpanshi.cashloan.cl.mapper.PanVerifyModelMapper;
import com.adpanshi.cashloan.cl.service.OperatorReqLogService;
import com.adpanshi.cashloan.cl.service.UserAppsService;
import com.adpanshi.cashloan.cl.service.UserAuthService;
import com.adpanshi.cashloan.cl.service.UserEquipmentInfoService;
import com.adpanshi.cashloan.common.enums.OrganizationEnum;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.util.ShardTableUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.core.domain.User;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.mapper.UserMapper;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.data.user.bo.UserBaseDataBo;
import com.adpanshi.cashloan.data.user.bo.UserDataBo;
import com.adpanshi.cashloan.data.user.domain.UserDataDomain;
import com.adpanshi.cashloan.dispatch.business.constant.TaskParamConstant;
import com.adpanshi.cashloan.dispatch.run.domain.DispatchRunDomain;
import com.adpanshi.cashloan.rule.domain.UserApps;
import com.adpanshi.cashloan.rule.domain.UserContacts;
import com.adpanshi.cashloan.rule.domain.UserEmerContacts;
import com.adpanshi.cashloan.rule.domain.UserEquipmentInfo;
import com.adpanshi.cashloan.rule.domain.equifaxReport.EquifaxReport;
import com.adpanshi.cashloan.rule.mapper.EquifaxReportMapper;
import com.adpanshi.cashloan.rule.mapper.UserContactsMapper;
import com.adpanshi.cashloan.rule.mapper.UserEmerContactsMapper;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hujin3
 * @description: 测试
 * @author: Mr.Wange
 * @create: 2018-08-10 11:53
 **/
@Scope("prototype")
@Controller
public class TestController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private UserDataDomain userDataDomain;
    @Resource
    private UserAuthService userAuthService;
    @Resource
    private UserAppsService userAppsService;
    @Resource
    private OperatorReqLogService operatorReqLogService;
    @Resource
    private DispatchRunDomain dispatchRunDomain;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserContactsMapper userContactsMapper;
    @Resource
    private EquifaxReportMapper equifaxReportMapper;
    @Resource
    private PanVerifyModelMapper panVerifyModelMapper;
    @Resource
    private OperatorReqLogMapper operatorReqLogMapper;
    @Resource
    private UserEmerContactsMapper userEmerContactsMapper;
    @Resource
    private UserEquipmentInfoService userEquipmentInfoService;

    @RequestMapping(value = "/api/dataMigration.htm")
    public void test(final String key){
        Map<String,Object> result=new HashMap<>(3);
        String dataMigration = Global.getValue("dataMigration");
        if(dataMigration==null || !dataMigration.equals(key)){
            result.put(Constant.RESPONSE_CODE, 403);
            result.put(Constant.RESPONSE_CODE_MSG, "非法访问");
            ServletUtils.writeToResponse(response, result);
            return;
        }
        List<User> userList = userMapper.listSelective(null);
        int count = userList.size();
        int succeedCount = 0;
        int fairlyCount = 0;
        int leapfrog = 0;
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据迁移:待迁移总数="+count);
        for (int i=0;i<userList.size();i++){
            User user = userList.get(i);
            try {
                logger.info(">>>>>>>>>>>>>>>>>>>>>>数据迁移:第"+i+"个用户userId"+user.getId()+"迁移开始>>>>>>>>>>>>>>>>>>>>>>");
                //用户详细信息
                UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(user.getId());
                if (userBaseInfo==null||StringUtil.isNotBlank(userBaseInfo.getUserDataId())) {
                    leapfrog++;
                    logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:第"+i+"个用户userId"+user.getId()+"已迁移，跳过<<<<<<<<<<<<<<<<<<<<<<");
                    continue;
                }
                //用户磨合
                UserEquipmentInfo userEquipmentInfo = userEquipmentInfoService.findSelective(user.getId());
                //用户认证信息
                UserAuth userAuth = userAuthService.findSelective(user.getId());
                String blackBox = "null";
                if (userEquipmentInfo != null) {
                    blackBox = userEquipmentInfo.getDeviceFinger();
                }

                //初始化MongoDB用户数据start
                UserBaseDataBo userBaseDataBo = new UserBaseDataBo();
                userBaseDataBo.setEmail(user.getLoginNameEmail());
                userBaseDataBo.setMobile(userBaseInfo.getPhone());
                userBaseDataBo.setName(userBaseInfo.getRealName());
                userBaseDataBo.setDeviceFingerprint(blackBox);
                userBaseDataBo.setCountryType(OrganizationEnum.CountryType.INDIA);
                userBaseDataBo.setProductType(OrganizationEnum.ProductType.OLOAN);
                UserDataBo userDataBo = userDataDomain.create(userBaseDataBo);
                logger.info("数据迁移>>>>>>>>>>>>>>>>>>>>>>:新建MongoDB数据，userDataId=" + userDataBo.getFid());
                userBaseInfo.setUserDataId(userDataBo.getFid());
                Map map = new HashMap(4);
                map.put("userDataId", userDataBo.getFid());
                map.put("id", userBaseInfo.getId());
                //更新cl_user_base_info表的user_data_id字段
                userBaseInfoService.updateSelective(map);
                //初始化MongoDB用户数据end

                try {
                    Map<String, Object> userApps = new HashMap<>(4);
                    userApps.put("userId", user.getId());
                    userApps.put("state", 0);
                    List<UserApps> appList = userAppsService.listSelective(user.getId(), userApps);
                    //app应用节点
                    if (appList != null && appList.size() > 0) {
                        String appJsonData = JSONObject.toJSONString(appList);
                        Map nodeMap = new HashMap(3);
                        nodeMap.put(TaskParamConstant.THIRD_PARTY_METADATA, appJsonData);
                        //调起节点
                        logger.info(">>>>>>>>>>>>>>>>>>>>>>数据迁移:调用app应用节点开始>>>>>>>>>>>>>>>>>>>>>>");
                        dispatchRunDomain.startNode(
                                userBaseInfo.getUserDataId(), "india_oloan_app_application", userBaseInfo.getPhone(),
                                user.getLoginNameEmail(), userBaseInfo.getIdNo(), userBaseInfo.getRealName(),
                                blackBox, nodeMap);
                        logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:调用app应用节点完成<<<<<<<<<<<<<<<<<<<<<<");
                    }
                } catch (Exception e) {
                    logger.error("XXXXXXXXXXXXXXXXXXXXXXXXX数据迁移:调用app应用节点失败XXXXXXXXXXXXXXXXXXXXXXXXX", e);
                }


                //用户通讯录start
                try {
                    // 分表
                    String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", user.getId(), 30000);
                    int countTable = userContactsMapper.countTable(tableName);
                    if (countTable == 0) {
                        userContactsMapper.createTable(tableName);
                    }
                    Map<String, Object> params = new HashMap<>(3);
                    params.put("userId", user.getId());
                    List<UserContacts> userContactsList = userContactsMapper.listShardSelective(tableName, params);
                    if (userContactsList != null && userContactsList.size() > 0) {
                        String infos = JSONObject.toJSONString(userContactsList);
                        Map nodeMap = new HashMap(3);
                        nodeMap.put(TaskParamConstant.THIRD_PARTY_METADATA, infos);
                        logger.info(">>>>>>>>>>>>>>>>>>>>>>数据迁移:调用通讯录节点开始>>>>>>>>>>>>>>>>>>>>>>");
                        //调起节点
                        dispatchRunDomain.startNode(
                                userBaseInfo.getUserDataId(), "india_oloan_app_contacts", userBaseInfo.getPhone(),
                                user.getLoginNameEmail(), userBaseInfo.getIdNo(), userBaseInfo.getRealName(),
                                blackBox, nodeMap);
                        logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:调用通讯录节点完成<<<<<<<<<<<<<<<<<<<<<<");
                    }
                } catch (Exception e) {
                    logger.error("XXXXXXXXXXXXXXXXXXXXXXXXX数据迁移:调用通讯录节点失败XXXXXXXXXXXXXXXXXXXXXXXXX", e);
                }
                //用户通讯录end

                //sim卡信息start
                try {
                    //查询用户最后一个认证成功的时间
                    Map simParams = new HashMap(5);
                    simParams.put("userId", user.getId());
                    simParams.put("respCode", "200");
                    simParams.put("businessType", "10");
                    OperatorReqLog operatorReqLog = operatorReqLogService.findLastRecord(simParams);
                    if (operatorReqLog != null) {
                        String respParams = operatorReqLogMapper.findOrdersByTaskId(operatorReqLog.getOrderNo());
                        if (StringUtil.isNotBlank(respParams)) {
                            Map simNode = new HashMap(3);
                            simNode.put(TaskParamConstant.THIRD_PARTY_METADATA, respParams);
                            logger.info(">>>>>>>>>>>>>>>>>>>>>>数据迁移:调用SIM信息节点开始>>>>>>>>>>>>>>>>>>>>>>");
                            dispatchRunDomain.startNode(userBaseInfo.getUserDataId(), "india_oloan_moxie_sim",
                                    userBaseInfo.getPhone(), user.getLoginNameEmail(), userBaseInfo.getIdNo(),
                                    userBaseInfo.getRealName(), blackBox, simNode);
                            logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:调用SIM信息节点完成<<<<<<<<<<<<<<<<<<<<<<");
                        }
                    }
                } catch (Exception e) {
                    logger.error("XXXXXXXXXXXXXXXXXXXXXXXXX数据迁移:调用SIM信息节点失败XXXXXXXXXXXXXXXXXXXXXXXXX", e);
                }
                //sim卡信息end

                //sns卡信息start
                try {
                    //查询用户最后一个认证成功的时间
                    Map snsParams = new HashMap(5);
                    snsParams.put("userId", user.getId());
                    snsParams.put("respCode", "200");
                    snsParams.put("businessType", "20");
                    OperatorReqLog operatorSnsReqLog = operatorReqLogService.findLastRecord(snsParams);
                    if (operatorSnsReqLog != null) {
                        String respParams = operatorReqLogMapper.findOrdersByTaskId(operatorSnsReqLog.getOrderNo());
                        if (StringUtil.isNotBlank(respParams)) {
                            Map snsNode = new HashMap(3);
                            snsNode.put(TaskParamConstant.THIRD_PARTY_METADATA, respParams);
                            logger.info(">>>>>>>>>>>>>>>>>>>>>>数据迁移:调用SNS信息节点开始>>>>>>>>>>>>>>>>>>>>>>");
                            dispatchRunDomain.startNode(userBaseInfo.getUserDataId(), "india_oloan_moxie_sns",
                                    userBaseInfo.getPhone(), user.getLoginNameEmail(), userBaseInfo.getIdNo(),
                                    userBaseInfo.getRealName(), blackBox, snsNode);
                            logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:调用SNS信息节点完成<<<<<<<<<<<<<<<<<<<<<<");
                        }
                    }
                } catch (Exception e) {
                    logger.error("XXXXXXXXXXXXXXXXXXXXXXXXX数据迁移:调用SNS信息节点失败XXXXXXXXXXXXXXXXXXXXXXXXX", e);
                }
                //sns卡信息end

                //紧急联系人认证
                if("30".equals(userAuth.getContactState())){
                    try{
                        Map tem = new HashMap(3);
                        tem.put("userId",user.getId());
                        List<UserEmerContacts> emerList = userEmerContactsMapper.listSelective(tem);
                        if(emerList!=null && emerList.size()==2){
                            UserEmerContacts userEmer1 = emerList.get(0);
                            UserEmerContacts userEmer2 = emerList.get(1);
                            Map emerMap = new HashMap();
                            emerMap.put("userId",user.getId());
                            emerMap.put("phone",userEmer1.getPhone()+","+userEmer2.getPhone());
                            emerMap.put("relation",userEmer1.getRelation()+","+userEmer2.getRelation());
                            emerMap.put("name",userEmer1.getName()+","+userEmer2.getName());
                            emerMap.put("type",userEmer1.getType()+","+userEmer2.getType());
                            Map modeMap = new HashMap();
                            modeMap.put(TaskParamConstant.THIRD_PARTY_METADATA,emerMap);
                            logger.info(">>>>>>>>>>>>>>>>>>>>>>数据迁移:调用紧急联系人节点开始>>>>>>>>>>>>>>>>>>>>>>");
                            //调起节点
                            dispatchRunDomain.startNode(userBaseInfo.getUserDataId(),"india_oloan_emergency_contact",
                                    userBaseInfo.getPhone(),user.getLoginNameEmail(),userBaseInfo.getIdNo(),
                                    userBaseInfo.getRealName(),blackBox,modeMap);
                            logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:调用紧急联系人节点完成<<<<<<<<<<<<<<<<<<<<<<");
                        }
                    }catch (Exception e){
                        logger.error("XXXXXXXXXXXXXXXXXXXXXXXXX数据迁移:调用紧急联系人节点失败XXXXXXXXXXXXXXXXXXXXXXXXX", e);
                    }
                }

                if ("30".equals(userAuth.getIdState())) {
                    //信息认证节点
                    try {
                        Map userInfoDataMap = new HashMap(9);
                        userInfoDataMap.put("livingImg", userBaseInfo.getLivingImg());
                        userInfoDataMap.put("firstName", userBaseInfo.getFirstName());
                        userInfoDataMap.put("lastName", userBaseInfo.getLastName());
                        userInfoDataMap.put("firstName", userBaseInfo.getFirstName());
                        userInfoDataMap.put("lastName", userBaseInfo.getLastName());
                        userInfoDataMap.put("registerAddr", userBaseInfo.getRegisterAddr());
                        logger.info(">>>>>>>>>>>>>>>>>>>>>>数据迁移:调用用户信息节点开始>>>>>>>>>>>>>>>>>>>>>>");
                        //调起节点
                        dispatchRunDomain.startNode(
                                userBaseInfo.getUserDataId(), "india_oloan_user_baseInfo", userBaseInfo.getPhone(),
                                user.getLoginNameEmail(), userBaseInfo.getIdNo(), userBaseInfo.getRealName(),
                                blackBox, userInfoDataMap);
                        logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:调用用户信息节点完成<<<<<<<<<<<<<<<<<<<<<<");
                    } catch (Exception e) {
                        logger.error("XXXXXXXXXXXXXXXXXXXXXXXXX数据迁移:调用用户信息节点失败XXXXXXXXXXXXXXXXXXXXXXXXX", e);
                    }

                    //pan卡验证
                    try {
                        //先从本地数据库查询
                        Map panRequest = panVerifyModelMapper.getPanInfo(userBaseInfo.getPanNumber());
                        Map panMap = new HashMap(3);
                        panMap.put(TaskParamConstant.THIRD_PARTY_METADATA, JSONObject.toJSONString(panRequest));
                        logger.info(">>>>>>>>>>>>>>>>>>>>>>数据迁移:调用PAN卡信息节点开始>>>>>>>>>>>>>>>>>>>>>>");
                        dispatchRunDomain.startNode(
                                userBaseInfo.getUserDataId(), "india_oloan_panCard", userBaseInfo.getPhone(),
                                user.getLoginNameEmail(), null, userBaseInfo.getRealName(),
                                blackBox, panMap);
                        logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:调用PAN卡信息节点完成<<<<<<<<<<<<<<<<<<<<<<");
                    } catch (Exception e) {
                        logger.error("XXXXXXXXXXXXXXXXXXXXXXXXX数据迁移:调用PAN卡信息节点失败XXXXXXXXXXXXXXXXXXXXXXXXX", e);
                    }

                    //信用报告
                    try {
                        EquifaxReport report = equifaxReportMapper.getReport(user.getId());
                        if (report != null) {
                            String blobString = null;
                            try {
                                blobString = new String((byte[]) report.getReportContent(), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Map equifaxParams = new HashMap(3);
                            equifaxParams.put(TaskParamConstant.THIRD_PARTY_METADATA, blobString);
                            //调起节点
                            logger.info(">>>>>>>>>>>>>>>>>>>>>>数据迁移:调用信用报告信息节点开始>>>>>>>>>>>>>>>>>>>>>>");
                            dispatchRunDomain.startNode(
                                    userBaseInfo.getUserDataId(), "india_oloan_equifax", userBaseInfo.getPhone(),
                                    user.getLoginNameEmail(), null, userBaseInfo.getRealName(),
                                    blackBox, equifaxParams);
                            logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:调用信用报告信息节点完成<<<<<<<<<<<<<<<<<<<<<<");
                        }
                    } catch (Exception e) {
                        logger.error("XXXXXXXXXXXXXXXXXXXXXXXXX数据迁移:调用信用报告信息节点失败XXXXXXXXXXXXXXXXXXXXXXXXX", e);
                    }
                }
                succeedCount++;
                logger.info("<<<<<<<<<<<<<<<<<<<<<<数据迁移:第"+i+"个用户userId"+user.getId()+"迁移完成<<<<<<<<<<<<<<<<<<<<<<");
            } catch (Exception e) {
                fairlyCount++;
                logger.error("XXXXXXXXXXXXXXXXXXXXXXXXX数据迁移:第"+i+"个用户userId"+user.getId()+"迁移失败XXXXXXXXXXXXXXXXXXXXXXXXX", e);
            }
        }
        logger.info("数据迁移:成功"+succeedCount+"条，失败"+fairlyCount+"条，跳过"+leapfrog+"条<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        result.put(Constant.RESPONSE_CODE, 200);
        result.put(Constant.RESPONSE_CODE_MSG, "数据迁移成功");
        result.put(Constant.RESPONSE_DATA,"数据迁移:成功"+succeedCount+"条，失败"+fairlyCount+"条，跳过"+leapfrog+"条");
        ServletUtils.writeToResponse(response, result);
    }
}
