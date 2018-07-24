package com.adpanshi.cashloan.business.cl.domain.yincheng;

import java.io.Serializable;

/**
 * 用户id与银程用户id关联表实体
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-10-31 19:44:02

 *
 *
 */
 public class UserIdRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * cl_user_base_info表中的id
    */
    private Long userBaseId;

    /**
    * 银程金服返回的uid
    */
    private Long yinchengUid;


    /**
    * 获取cl_user_base_info表中的id
    *
    * @return cl_user_base_info表中的id
    */
    public Long getUserBaseId(){
        return userBaseId;
    }

    /**
    * 设置cl_user_base_info表中的id
    * 
    * @param userBaseId 要设置的cl_user_base_info表中的id
    */
    public void setUserBaseId(Long userBaseId){
        this.userBaseId = userBaseId;
    }

    /**
    * 获取银程金服返回的uid
    *
    * @return 银程金服返回的uid
    */
    public Long getYinchengUid(){
        return yinchengUid;
    }

    /**
    * 设置银程金服返回的uid
    * 
    * @param yinchengUid 要设置的银程金服返回的uid
    */
    public void setYinchengUid(Long yinchengUid){
        this.yinchengUid = yinchengUid;
    }

}
