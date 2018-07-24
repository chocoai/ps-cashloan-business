package com.adpanshi.cashloan.business.core.common.context;


/**
 * 公用常量类定义
 *
 * @version 1.0
 * @created 2014年9月23日 下午2:17:20
 */
public class Constant {

    /**
     * 角色session 暂用
     */
    public static final String ROLEID = "roleId";

    public static final String INSERT = "create";

    public static final String UPDATE = "update";

    public static final String RESPONSE_CODE = "code";

    public static final String RESPONSE_CODE_MSG = "msg";

    public static final String RESPONSE_DATA = "data";

    public static final String RESPONSE_CONTENT = "content";

    public static final String RESPONSE_DATA_PAGE = "page";

    /** 操作成功 */
    public static final String OPERATION_SUCCESS = "operate successfully";

    /** 操作失败 */
    public static final String OPERATION_FAIL = "operation failure";

    public static final String PARAMETER_CHECKING_FAIL = "参数校验失败";

    public static final String MYSQL_DATATYPE_INT = "bigint;int;decimal;integer;tinyint;double;decimal;float;bit;smallint;mediumint;";

    public static final int SIGN_FAIL = 99; // 验签失败

    public static final int SUCCEED_CODE_VALUE = 200; // 成功 插入 、删除 更新 修改

    public static final int FAIL_CODE_PARAM_INSUFFICIENT = 300;    //参数列表不完整

    public static final int FAIL_CODE_VALUE = 400; // 失败 插入 、删除 更新 修改

    public static final int FAIL_CODE_PWD = 401; // 交易密码错误

    public static final int PERM_CODE_VALUE = 403; // 无权限访问

    //wangxb增加
    public static final int BiZ_IN_BLACKLIST = 404; // 在黑名单中不予操作


    public static final int OTHER_CODE_VALUE = 500; // 其他异常
    
    /**无可用资源(次数用尽)*/
    public static final int NO_AVAILABLE=5050;
    
    /**无数据*/
    public static final int NO_DATA=666;
    
    /**认证失败*/
    public static final int AUTHENTICATION_FAILED_CODE=777;
    
    /**已认证 */
    public static final int AUTHENTICATION_VERIFIED_CODE=766;
    
    /**认证中*/
    public static final int AUTHENTICATION_ERTIFICATION_CODE=755;
    
    public static final int CLIENT_EXCEPTION_CODE_VALUE = 998; // 连接异常（除请求超时）

    public static final int TIMEOUT_CODE_VALUE = 999; // 请求超时


    /**
     * 参数校验不通过
     */
    public static final int PARAMETER_CHECKING_CODE = 700;

    /**
     * 系统升级-状态码
     */
    public static final int SYSTEM_UPGRADE_CODE = 8088;
    

    //----------------------->企业法人--------------------------------

    /***企业法人(sys_config)*/
    public static final String BUSINESS_ENTITY = "business_entity";

    /**
     * 企业法人身份证号(sys_config)
     */
    public static final String BUSINESS_ENTITY_IDCARD = "business_entity_idcard";

    /**
     * 企业邮箱(sys_config)
     */
    public static final String COMPANY_EMAIL = "company_email";

    /**
     * 企业名称(sys_config)
     */
    public static final String COMPANY_NAME = "company_name";

    /**
     * 企业法人联系电话(居间服务方-闪银联系人电话)
     */
    public static final String COMPANY_LINKMAN = "company_linkman";

    /**
     * 出借人手机号
     */
    public static final String LENDER_LINKMAN = "lender_linkman";

    /**
     * UTF-8
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * JSON
     */
    public static final String JSON = "json";

    /**
     * XML
     */
    public static final String XML = "xml";


    public static final String FAIL = "FAIL";

    public static final String SUCCESS = "SUCCESS";

    /**
     * 服务电话-sysConfig配置
     */
    public static final String SERVICE_CALL = "SERVICE_CALL";

    /**
     * 系统升级-sysConfig配置
     */
    public static final String SYSTEM_UPGRADE = "SYSTEM_UPGRADE";
    /**
     * 系统升级提示-sysConfig配置
     */
    public static final String SYSTEM_UPGRADE_MSG = "SYSTEM_UPGRADE_MSG";

    /**
     * userName常量配置
     */
    public static final String USERNAME_SYSTEM = "system";
}
