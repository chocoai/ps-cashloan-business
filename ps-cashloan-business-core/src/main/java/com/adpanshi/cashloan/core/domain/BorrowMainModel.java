package com.adpanshi.cashloan.core.domain;

/**
 * @description borrowMain 关联的虚拟字段
 *
 * @throws Exception
 * @author: nmnl
 * @date : 2017-12-30 16:52
 */
public class BorrowMainModel extends BorrowMain {

    private static final long serialVersionUID = 2L;

    //用户真实姓名
    private String realName;
    //用户身份证
    private String idNo;
    //黑名单状态
    private String backState;
    //拉黑原因
    private String blackReason;
    //用户注册客户端
    private String registerClient;
    //渠道名称
    private String channelName;
    //用户手机号码
    private String loginName;
    //td审核数据
    private String tdProposal;
    //审核数据.
    private String auditData;

    //当前信审人员Id
    private Long systemId;
    //当前信审人员名称
    private String systemName;
    //当前信审人员分配的订单
    private Integer systemCount;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getBackState() {
        return backState;
    }

    public void setBackState(String backState) {
        this.backState = backState;
    }

    public String getBlackReason() {
        return blackReason;
    }

    public void setBlackReason(String blackReason) {
        this.blackReason = blackReason;
    }

    public String getRegisterClient() {
        return registerClient;
    }

    public void setRegisterClient(String registerClient) {
        this.registerClient = registerClient;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getTdProposal() {
        return tdProposal;
    }

    public void setTdProposal(String tdProposal) {
        this.tdProposal = tdProposal;
    }

    public String getAuditData() {
        return auditData;
    }

    public void setAuditData(String auditData) {
        this.auditData = auditData;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getSystemCount() {
        return systemCount;
    }

    public void setSystemCount(Integer systemCount) {
        this.systemCount = systemCount;
    }
}
