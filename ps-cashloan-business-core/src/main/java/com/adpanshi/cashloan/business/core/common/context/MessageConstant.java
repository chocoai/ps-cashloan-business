package com.adpanshi.cashloan.business.core.common.context;

/**
 * @program: fenqidai_dev
 * @description: 公共信息参数
 * @author: Mr.Wange
 * @create: 2018-06-26 16:22
 **/
public class MessageConstant {
    public static final String USER_NOT_EXIST = "User information does not exist";
    public static final String USER_NAME_NOT_EQUAL = "User name error";
    /** pan卡认证失败 */
    public static final String PAN_NO_AUTH_FAIL = "PAN number authentication failure";
    /** 手机号码不匹配 */
    public static final String PHONE_NOT_EQUAL = "The mobile phone number does not match";
    /** 获取短信验证码过于频繁，请明日再试 */
    public static final String SMS_FREQUENTLY = "The verification code is obtained too frequently. Please try again tomorrow";
    /** 该用户为黑名单用户不予操作 */
    public static final String BLACKLISTED = "The user is not allowed to operate for blacklisted users";
    /** 请输入证件号 */
    public static final String ENTER_AADHAAR_CARD = "Enter Aadhaar Card";
    /** 城市不存在 */
    public static final String CITY_NOT_EXIST = "Cities don't exist";
    /** 证件号已存在 */
    public static final String AADHAAR_ALREADY_EXISTS = "Aadhaar already exists";
    /** 账户不存在 */
    public static final String ACCOUNT_NOT_EXIST = "The account does not exist";
    /** 手机号不存在 */
    public static final String PHONE_NOT_EXIST = "The phone number does not exist";
    /** 邮箱不存在 */
    public static final String EMAIL_NOT_EXIST = "The E-mail does not exist";
    /** 账号状态异常 */
    public static final String ACCOUNT_STATUS_ABNORMAL = "Account status abnormal";
    /** 账户状态失效 */
    public static final String ACCOUNT_STATUS_INVALID = "Account status invalid";
    /** 账户已注销 */
    public static final String ACCOUNT_CANCELLED = "Account cancelled";
    /** 用户名或密码错误 */
    public static final String USER_NAME_OR_PASSWORD_ERROR = "unknown user name or bad password";
    /** 系统异常 */
    public static final String SYSTEM_EXCEPTIONS = "system exceptions";
    /** 密码修改失败 */
    public static final String PASSWORD_FAILURE = "Modify your password failure";
    /** 验证码已失效 */
    public static final String VERIFICATION_CODE_EXPIRED = "Verification Code Expired";
    /** 验证码错误 */
    public static final String VERIFICATION_CODE_ERROR = "Verification Code Error";
    /** 验证码发送失败 */
    public static final String VERIFICATION_CODE_SENDING_FAILED = "Verification code sending failed";
    /** 请先获取验证码 */
    public static final String GET_VERIFICATION_CODE = "Get the verification code first";
    /** 该手机号码已被注册 */
    public static final String PHONE_REGISTERED = "The phone number has been registered";
    /** 该邮箱已被注册 */
    public static final String EMAIL_REGISTERED = "The E-mail has been registered";
    /** 该邮箱格式不正确 */
    public static final String EMAIL_ERROR = "The E-mail is improperly formatted";
    /** 申请失败 */
    public static final String APPLICATION_FAILED = "application failed";
    /** 申请成功 */
    public static final String APPLICATION_APPROVED = "application approved";
    /** 申请成功 */
    public static final String NOT_BORROW = "Can't borrow";
    /** 申请成功 */
    public static final String NOT_CERTIFICATION = "The certification is incomplete and cannot be borrowed";
    /** 已有未完成的借款 */
    public static final String AN_UNFINISHED_LOAN = "There's an unfinished loan";
    /** "申请失败,您在" + day + "天后才能再次借款" */
    public static final String DAYS_BORROW = "The application failed and you will not be able to borrow again until {0} days later";
    /** "借款期限不正确" */
    public static final String MATURITY_LOAN_ERR = "The maturity of the loan is not correct";
    /** 额度不足,无法借款! */
    public static final String QUOTA_INSUFFICIENT = "Your quota is insufficient and you can't borrow money";
    /** 借款期限为：{0}的借款模板不存在。 */
    public static final String LOAN_LIMIT_TEMPLATE = "借款期限为：{0}的借款模板不存在。";
    /** 请先进行个人信息认证 */
    public static final String FIRST_PERSONAL_INFORMATION = "Please conduct personal information certification first";
}
