 package com.adpanshi.cashloan.cl.model;

import com.adpanshi.cashloan.cl.domain.UserAuth;

 /**
  * @author 8452
  */
 public class UserAuthModel extends UserAuth {
     /**
      *
      */
     private static final long serialVersionUID = 1L;


     /** 认证状态 - 未认证/未完善 */
     public static final String STATE_NOT_CERTIFIED = "10";

     /** 认证状态 - 认证中/完善中*/
     public static final String STATE_ERTIFICATION = "20";

     //begin pantheon 20170609 新增认证失败状态
     /** 认证状态 - 认证失败*/
     public static final String STATE_FAIL = "25";
     //end

     /** 认证状态 - 已认证/已完善*/
     public static final String STATE_VERIFIED = "30";

     /** 认证状态 - 已过期*/
     public static final String STATE_BE_OVERDUE = "40";


     /**
      * 登录名
      */
     private String loginName;

     /**
     * 真实姓名
     */
     private String realName;
     /**
      * 手机号码
      */
      private String phone;


     public String getLoginName() {
         return loginName;
     }
     public void setLoginName(String loginName) {
         this.loginName = loginName;
     }
     public String getRealName() {
         return realName;
     }
     public void setRealName(String realName) {
         this.realName = realName;
     }
     public String getPhone() {
         return phone;
     }
     public void setPhone(String phone) {
         this.phone = phone;
     }


 }
