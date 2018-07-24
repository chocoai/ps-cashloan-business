package com.adpanshi.cashloan.business.rule.extra.tongdun.api;

import cn.fraudmetrix.riskservice.RiskServiceClient;
import cn.fraudmetrix.riskservice.RuleDetailClient;
import cn.fraudmetrix.riskservice.RuleDetailResult;
import cn.fraudmetrix.riskservice.object.Environment;
import cn.fraudmetrix.riskservice.object.RiskResult;
import com.adpanshi.cashloan.business.rc.domain.TppBusiness;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方同盾 风险决策接口V1.1
 * https://oceanus.tongdun.cn/ruleengine/documentation/riskapi/api.htm
 *
 * Created by pantheon on 2017/6/22.
 */
public class ExtraTdRiskApi {
    public enum EnumMobileType{
        ANDROID,IOS,WEB
    }

    private static final Logger logger = LoggerFactory.getLogger(ExtraTdRiskApi.class);

    /**
     * 风险决策接口请求，同步返回

     * @since 2017-06-23 12:58
     *
     * @param requestParams
     * @param business
     * @param testState
     * @param enumMobileType
     * @return
     */
    public String request(Map<String, String> requestParams, TppBusiness business, boolean testState, EnumMobileType enumMobileType){
        JSONObject extend = JSONObject.parseObject(business.getExtend());
        if(extend == null){
            logger.error("ExtraTdRiskApi-request()-获取参数错误：business.getExtend()为空!");
            return null;
        }

        if(!"TongdunRisk".equals(business.getNid())){
            logger.error("ExtraTdRiskApi-request()-获取参数TppBusiness不正确-TppBusiness："+JSONObject.toJSONString(business));
            return null;
        }

        String partnerKey = extend.getString("partner_key");
        String partnerCode = extend.getString("partner_code");
        String appName = extend.getString("app_name");
        String secretkey = extend.getString("secret_key");
        String eventId = extend.getString("event_id");
        JSONObject names = JSONObject.parseObject(appName);
        JSONObject secretkeys = JSONObject.parseObject(secretkey);
        JSONObject eventIds = JSONObject.parseObject(eventId);
        switch (enumMobileType){
            case ANDROID:
                appName=names.getString("and");
                secretkey=secretkeys.getString("and");
                eventId=eventIds.getString("and");
                break;
            case IOS:
                appName=names.getString("ios");
                secretkey=secretkeys.getString("ios");
                eventId=eventIds.getString("ios");
                break;
            case WEB:
                appName=names.getString("web");
                secretkey=secretkeys.getString("web");
                eventId=eventIds.getString("web");
                break;
        }

        RiskServiceClient client = null;
        if(testState){
            client = RiskServiceClient.getInstance(partnerCode, Environment.SANDBOX, "UTF-8");
            System.out.println("测试环境-partnerCode："+partnerCode+"-Environment.SANDBOX："+Environment.SANDBOX.getApiUrl());
            logger.info("ExtraTdRiskApi-request()-测试环境");
        }else{
            client = RiskServiceClient.getInstance(partnerCode, Environment.PRODUCT,"UTF-8");
            System.out.println("生产环境");
            logger.info("ExtraTdRiskApi-request()-生产环境");
        }

        logger.info(" 20170831 请求数据: secretkey: " + secretkey +" eventId: " + eventId + " requestParams: " + requestParams.toString());
        RiskResult result = client.execute(secretkey, eventId, requestParams);
        //@remarks:查看代码时发现有system. 注释掉!  @date:20170803 @author: nmnl
        //System.out.println("测试环境-secretkey："+secretkey+"-partnerCode:"+partnerCode+"-eventId："+eventId+"-requestParams:"+JSONObject.toJSONString(requestParams));
        return JSONObject.toJSONString(result);
    }


    /**
     * 根据sequenceId反查同盾事件
     * @param business
     * @param testState
     * @param sequenceId
     * @return
     */
    public String report(TppBusiness business, boolean testState, String sequenceId){
        JSONObject extend = JSONObject.parseObject(business.getExtend());
        if(extend == null){
            logger.error("ExtraTdRiskApi-request()-获取参数错误：business.getExtend()为空!");
            return null;
        }

        if(!"TongdunRisk".equals(business.getNid())){
            logger.error("ExtraTdRiskApi-request()-获取参数TppBusiness不正确-TppBusiness："+JSONObject.toJSONString(business));
            return null;
        }

        String partnerKey = extend.getString("partner_key");
        String partnerCode = extend.getString("partner_code");
        String appName = extend.getString("app_name");
        String secretkey = extend.getString("secret_key");
        String eventId = extend.getString("event_id");
        JSONObject names = JSONObject.parseObject(appName);

        RuleDetailClient client = null;
        if(testState){
            client = RuleDetailClient.getInstance(partnerCode, Environment.SANDBOX);
        }else {
            client = RuleDetailClient.getInstance(partnerCode, Environment.PRODUCT);
        }

        RuleDetailResult result = client.execute(partnerKey, sequenceId);
        return  JSONObject.toJSONString(result);
//        if(result != null) {
//            List find = result.find(BlackListDetail.class);
//            Iterator var3 = find.iterator();
//
//            while(var3.hasNext()) {
//                BlackListDetail e = (BlackListDetail)var3.next();
//                List hits = e.getHits();
//
//                BlackListHit var7;
//                for(Iterator var6 = hits.iterator(); var6.hasNext(); var7 = (BlackListHit)var6.next()) {
//                    ;
//                }
//            }
//
//        }
    }

    public static void main(String[] args) {
        ExtraTdRiskApi api = new ExtraTdRiskApi();

        Map<String,String> result = new HashMap<String,String>();

        //1.新老用户
        int ext_regular_customer = 0 ;//是否是复借用户   新客户传入0  老客户传入1
        result.put("ext_regular_customer", ext_regular_customer+"");


        //2.设备指纹
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", "10");
        result.put("black_box", "eyJ0b2tlbklkIjoiSzlCZkpUR1lGVXVPQlhySVlYS1hxb2VIRXlwdWJTUXhGUzBZSG5SbHRqb1kzcG84emZwMFFYYVNGNHg4Z2pnRXFQc2ZkQnVZWTRYTWc5YmlaVnJQdkE9PSIsIm9zIjoiaU9TIiwicHJvZmlsZVRpbWUiOjI2OSwidmVyc2lvbiI6IjMuMC4yIn0=");

        //3.可选支持API实时返回设备或解析信息
        result.put("resp_detail_type", "device&geoip&attribution&credit_score&application_id");

        //4.refer_cust 	网页端请求来源 可选仅限网页(包括WAP、HTML5、微信公众号)js方式对接
        //5.可选 事件发生的真实时间 格式：2014-03-01 08:16:30
        result.put("event_occur_time", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));


        //6.借贷信息
//		result.put("apply_channel",null);//进件渠道
//		result.put("apply_province",null);//进件省份
//		result.put("apply_city",null);//进件城市
        result.put("loan_amount","1000");//贷款金额======
//		result.put("loan_purpose",null);//借款用途
        result.put("loan_term","14");//贷款期限
        result.put("loan_term_unit","DAY");//贷款期限
        result.put("loan_date", "2017-06-23");//贷款日期

//		result.put("is_cross_loan",null);//是否曾跨平台借款

//        result.put("ip_address",borrow.getIp());//ip


        //用户基本信息
//		result.put("work_time",null);//工作时间
//		result.put("monthly_income",null);//月均收入
//		result.put("annual_income",null);//年均收入
//        result.put("work_phone",info.getCompanyPhone());//借款人单位座机
//		result.put("applyer_type",null);//人员类别
//		result.put("org_code",null);//借款人组织机构代码
//		result.put("biz_license",null);//工商注册号

//		result.put("house_type",null);//房产类型
//		result.put("house_property",null);//房产情况

        result.put("account_name","皮晴晴");//字段借款人姓名
//		result.put("occupation",null);//职位
//		result.put("career",null);//职业
//		result.put("industry",null);//所属行业
//		result.put("company_type",null);//公司性质
//        result.put("organization",info.getCompanyName());//借款人工作单位
//        result.put("organization_address",info.getCompanyAddr());//借款人工作单位地址
//        result.put("marriage",info.getMarryState());//婚姻情况
//        result.put("diploma",info.getEducation());//学历
//		result.put("graduate_school",null);//借款人毕业院校
//        result.put("contact_address",info.getRegisterAddr());//通讯地址
//        result.put("registered_address",info.getIdAddr());//户籍地址
//        result.put("home_address",info.getLiveAddr());//家庭地址

//		result.put("account_phone",null);//借款人座机

        result.put("id_number","370404199006301915");//借款人身份证
        result.put("account_mobile","13333333333");//借款人手机





        //applicant_info.put("account_name","辛光迎");//字段借款人姓名
//        applicant_info.put("account_name","皮晴晴");//字段借款人姓名
//		applicant_info.put("occupation",null);//职位
//		applicant_info.put("career",null);//职业
//		applicant_info.put("industry",null);//所属行业
//		applicant_info.put("company_type",null);//公司性质
        //applicant_info.put("organization","杭州闪银");//借款人工作单位
//		applicant_info.put("marriage",info.getMarryState());//婚姻情况
        //applicant_info.put("diploma","初中及小学以下");//学历
//		applicant_info.put("graduate_school",null);//借款人毕业院校
        result.put("contact_address","浙江省杭州市西湖区万塘路靠近中国农业银行(科技城支行)");//通讯地址
        result.put("registered_address","浙江省杭州市西湖区文一路翠苑四区7栋314");//户籍地址
        result.put("home_address","浙江省杭州市西湖区文一路翠苑四区7栋314");//家庭地址

//		applicant_info.put("account_phone",null);//借款人座机

        //applicant_info.put("id_number","130582199309212342");//借款人身份证
//        applicant_info.put("id_number","370404199006301915");//借款人身份证
        //applicant_info.put("account_mobile","15967195213");//借款人手机
//        applicant_info.put("account_mobile","13333333333");//借款人手机

        //银行卡信息
        result.put("card_number","6230901807030310952");//借款人卡号

        //其他信息
        result.put("account_email","893939741@qq.com");//借款人邮箱
        result.put("qq_number","893939741");//借款人QQ

        //银行卡信息
        //applicant_info.put("card_number","6214623421000677142");//借款人卡号
//        applicant_info.put("card_number","6230901807030310952");//借款人卡号

        //其他信息
        //applicant_info.put("account_email","739022319@qq.com");//借款人邮箱
//        applicant_info.put("account_email","893939741@qq.com");//借款人邮箱
        //applicant_info.put("qq_number","739022319");//借款人QQ
//        applicant_info.put("qq_number","893939741");//借款人QQ

        //联系人信息

        result.put("contact1_name","张三");//字段第一联系人姓名
        result.put("contact1_mobile","+86 400-627-2273");//第一联系人手机号
        //result.put("contact1_id_number",null);//第一联系人身份证
        result.put("contact1_relation","朋友");//第一联系人社会关系


        result.put("contact2_name","爸");//字段第一联系人姓名
        result.put("contact2_mobile","15290605085");//第一联系人手机号
        //result.put("contact2_id_number",null);//第一联系人身份证
        result.put("contact2_relation","父亲");//第一联系人社会关系

//        result.put("loan",loan.toJSONString());
//        result.put("account_email","893939741@qq.com");
//        result.put("id_number","370404199006301915");
//        result.put("card_number","6230901807030310952");
//        result.put("account_name","皮晴晴");
//        result.put("account_mobile","13333333333");
//        result.put("qq_number","893939741");

        TppBusiness business = new TppBusiness();
        business.setId(6L);
        business.setTppId(3L);
        business.setName("同盾决策引擎");
        business.setNid("TongdunRisk");
        business.setState("10");
        business.setExtend("{\"partner_code\":\"hzsy\",\"partner_key\":\"a992726fd0d7477dae1a1f4e1b2355df\",\"app_name\":{\"ios\":\"hzsy_ios\",\"and\":\"hzsy_and\",\"web\":\"hzsy_web\"},\"secret_key\":{\"ios\":\"9439b5c6644d442e8a98c664d6dcde48\",\"and\":\"a5dffe7bfc08455ba2494382de2b3da1\",\"web\":\"a87d16509d854a6489084ba5801a0475\"},\"event_id\":{\"ios\":\"Loan_ios_20170414\",\"and\":\"Loan_android_20170414\",\"web\":\"Loan_web_20170414\"}}");
        business.setUrl("https://api.tongdun.cn/riskService/v1.1");
        business.setTestUrl("https://apitest.tongdun.cn/riskService/v1.1");
        business.setAddTime(new Date());

        boolean testState = true;
		String resultStr = api.request(result, business, true, ExtraTdRiskApi.EnumMobileType.IOS);
		System.out.println(resultStr);



//        String report = api.report(business, true,"1498459647150255T0B5651F97741943");//新用户
//        String report = api.report(business, true,"1498459674365380T0B5651FB8605965");//老用户
//
//        System.out.println(report);

    }


}
