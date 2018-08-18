package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.common.utils.JSONUtils;
import com.adpanshi.cashloan.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.data.thirdparty.pancard.domain.PanCardDomain;
import com.adpanshi.cashloan.dispatch.run.bo.DispatchRunResponseBo;
import com.adpanshi.cashloan.dispatch.run.domain.DispatchRunDomain;
import com.adpanshi.cashloan.rule.domain.UserEquipmentInfo;
import com.adpanshi.cashloan.rule.mapper.UserEquipmentInfoMapper;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.cl.mapper.PanVerifyModelMapper;
import com.adpanshi.cashloan.cl.model.UserAuthModel;
import com.adpanshi.cashloan.cl.service.UserAuthService;
import com.adpanshi.cashloan.cl.service.VerifyPanService;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.util.HttpUtils;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: fenqidai_dev
 * @description: panNumber verify
 * @author: Mr.Wange
 * @create: 2018-07-05 16:00
 **/
@Service("verifyPanService")
public class VerifyPanServiceImpl implements VerifyPanService {
    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(VerifyPanServiceImpl.class);

    @Resource
    private PanVerifyModelMapper panVerifyModelMapper;
    @Resource
    private UserAuthService userAuthService;
    @Resource
    private PanCardDomain panCardDomain;
    @Resource
    private DispatchRunDomain dispatchRunDomain;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private UserEquipmentInfoMapper userEquipmentInfoMapper;
    /** 印度OLOAN pan卡 */
    private String nodeNumber = "india_oloan_panCard";

    /** 
    * @Description: 验证pan卡信息 
    * @Param: [paramMap]
    * @return: int 
    * @Author: Mr.Wange
    * @Date: 2018/7/24 
    */ 
    @Override
    public Map<String, Object> verifyPanNumber(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = null;
        //先从本地数据库查询
        Map request = panVerifyModelMapper.getPanInfo((String)paramMap.get("panNo"));
        Map<String,Object> map = new HashMap<>(16);
        map.putAll(paramMap);
        if( request == null){
            Long userId = (Long) paramMap.get("userId");
            //获取用户的全名/邮箱号
            ManagerUserModel managerUserModel = userBaseInfoMapper.getBaseModelByUserId(userId);
            //获取设备指纹
            Map<String,Object> userIdMap = new HashMap<>(4);
            userIdMap.put("userId", userId);
            UserEquipmentInfo userEquipmentInfo = userEquipmentInfoMapper.findSelective(userIdMap);
            String blackBox = "null";
            if (userEquipmentInfo!=null && userEquipmentInfo.getDeviceFinger()!=null){
                blackBox = userEquipmentInfo.getDeviceFinger();
            }
            Map nodeMap = new HashMap(4);
            nodeMap.put("panNo",paramMap.get("panNo"));
            Map sendMap = new HashMap(4);
            sendMap.put("THIRD_PARTY_REQUEST_PARAMJSON",nodeMap);
            DispatchRunResponseBo<String> dispatchRunResponseBo = dispatchRunDomain.startNode(
                    managerUserModel.getUserDataId(),nodeNumber,managerUserModel.getPhone(),
                    managerUserModel.getLoginNameEmail(),null,managerUserModel.getRealName(),
                    blackBox,sendMap);
            request = JSONUtils.toMap(dispatchRunResponseBo.getData());
            map.putAll(request);
        }else{
            logger.info(request.toString());
            map.put("success",request.get("id"));
            map.put("first_name",request.get("first_name"));
            map.put("last_name",request.get("last_name"));
        }
        map.put("createTime",new Date());
        map.put("panAuth","20");

        String panStatusValid = "VALID_PAN";
        String panStatus = "pan_status";
        if(panStatusValid.equals(request.get(panStatus))){
            String firstName = (String)request.get("first_name");
            String lastName = (String)request.get("last_name");
            //兼容只有firstName或lastName的用户
            if(StringUtil.isNotBlank(firstName) || StringUtil.isNotBlank(lastName)){
                if(StringUtil.isEmpty(firstName)){
                    firstName="";
                }else if(StringUtil.isEmpty(lastName)){
                    lastName="";
                }
                //组装正则表达式，首尾匹配
                String patternStr = "^"+firstName.toLowerCase()+".*"+lastName.toLowerCase()+"$";
                Pattern pattern = Pattern.compile(patternStr.replaceAll(" ",""));
                String name = paramMap.get("realName").toString().toLowerCase().trim().replaceAll(" ","");
                //验证正则表达式
                Matcher matcher = pattern.matcher(name);
                System.out.println(matcher.matches());
                boolean flg = false;
                if(matcher.matches()){
                    flg = true;
                }else{
                    //组装正则表达式，尾首匹配
                    patternStr = "^"+lastName.toLowerCase()+".*"+firstName.toLowerCase()+"$";
                    pattern = Pattern.compile(patternStr.replaceAll(" ",""));
                    //验证正则表达式
                    matcher = pattern.matcher(name);
                    if(matcher.matches()){
                        flg = true;
                    }
                }
                resultMap = new HashMap<>(5);
                if(flg){
                    resultMap.put("firstName",firstName);
                    resultMap.put("lastName",lastName);
                    Map<String,Object> returnMap = new HashMap<>(16);
                    returnMap.put("panState", UserAuthModel.STATE_VERIFIED);
                    returnMap.put("userId", paramMap.get("userId"));
                    userAuthService.updateByUserId(returnMap);
                    map.put("panAuth","30");
                    resultMap.put("panAuth","30");
                }else{
                    map.put("panAuth","10");
                    resultMap.put("panAuth","10");
                }
            }
        }
        panVerifyModelMapper.save(map);

        return resultMap;
    }

    @Override
    public List<Map> getUserInfoList() {
        return panVerifyModelMapper.getUserPanList();
    }
}
