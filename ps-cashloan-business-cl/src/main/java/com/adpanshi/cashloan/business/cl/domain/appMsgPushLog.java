package com.adpanshi.cashloan.business.cl.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * @author huang qin
 * @create 2017-09-01 16:47:13
 * @history
 */
public class appMsgPushLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @Fields id:主键id
     */
    private Long id;

    /**
     * @Fields content:消息内容
     */
    private String content;

    /**
     * @Fields description:消息标题
     */
    private String description;

    /**
     * @Fields filePath:文件路径
     */
    private String filePath;

    /**
     * @Fields phones:推送电话
     */
    private String phones;

    /**
     * @Fields androidresult:结果描述
     */
    private String androidResult;

    /**
     * @Fields androidresultCode:结果状态码
     */
    private String androidResultCode;

    /**
     * @Fields androidresultData:结果数据
     */
    private String androidResultData;

    /**
     * @Fields IOSresult:结果描述
     */
    private String IOSResult;

    /**
     * @Fields IOSresultCode:结果状态码
     */
    private String IOSResultCode;

    /**
     * @Fields IOSresultData:结果数据
     */
    private String IOSResultData;

    /**
     * @Fields createTime:创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getAndroidResult() {
        return androidResult;
    }

    public void setAndroidResult(String androidResult) {
        this.androidResult = androidResult;
    }

    public String getAndroidResultCode() {
        return androidResultCode;
    }

    public void setAndroidResultCode(String androidResultCode) {
        this.androidResultCode = androidResultCode;
    }

    public String getAndroidResultData() {
        return androidResultData;
    }

    public void setAndroidResultData(String androidResultData) {
        this.androidResultData = androidResultData;
    }

    public String getIOSResult() {
        return IOSResult;
    }

    public void setIOSResult(String IOSResult) {
        this.IOSResult = IOSResult;
    }

    public String getIOSResultCode() {
        return IOSResultCode;
    }

    public void setIOSResultCode(String IOSResultCode) {
        this.IOSResultCode = IOSResultCode;
    }

    public String getIOSResultData() {
        return IOSResultData;
    }

    public void setIOSResultData(String IOSResultData) {
        this.IOSResultData = IOSResultData;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
