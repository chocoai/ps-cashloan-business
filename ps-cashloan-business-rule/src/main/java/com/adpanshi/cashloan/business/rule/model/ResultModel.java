package com.adpanshi.cashloan.business.rule.model;

import com.adpanshi.cashloan.business.core.common.enums.ManageExceptionEnum;

public class ResultModel {

	/**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String msg;

    /**
     * 返回内容
     */
    private Object content;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getContent() {
        return content;
    }

    public ResultModel(ManageExceptionEnum status) {
        this.code = status.Code();
        this.msg = status.Msg();
        this.content = "";
    }
    
    public ResultModel(ManageExceptionEnum status, Object content) {
        this.code = status.Code();
        this.msg = status.Msg();
        this.content = content;
    }

    public static ResultModel ok(Object content) {
        return new ResultModel(ManageExceptionEnum.SUCCEED_CODE_VALUE, content);
    }

    public static ResultModel ok() {
        return new ResultModel(ManageExceptionEnum.SUCCEED_CODE_VALUE);
    }

    public static ResultModel error(ManageExceptionEnum error) {
        return new ResultModel(error);
    }
}
