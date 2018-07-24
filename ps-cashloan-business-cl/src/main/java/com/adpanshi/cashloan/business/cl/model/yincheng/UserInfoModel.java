package com.adpanshi.cashloan.business.cl.model.yincheng;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 借款人基础信息模型
 *
 * @author yecy
 */
public class UserInfoModel {

    private Long uid;//    用户id(如果这个填写就是修改否则添加)
    private String username;//   用户帐号
    private String name;//   用户名
    private String mobile;//   用户身手机号
    private String code;//   用户身份证
    @JSONField(name = "card_code")
    private String cardCode;//   银行区号
    @JSONField(name = "card_no")
    private String cardNo;//   银行卡号
    private Integer credit;//     芝麻积分
    private String personfontimage;//   身份证正面图片地址
    private String personbackimage;//   身份证反面图片地址
    private String education;//   用户学历
    private String mail;//   用户邮箱
    private Integer sex;//    用户性别 0为男 1为女
    private String qq;//   QQ
    private String address;//   用户地址
    private List<UserEmerContactModel> list;//   紧急联系人数组
    private Integer marriage; //婚姻状况 0未婚 1已婚 2离异 3丧偶 @remark 20171128 目前小额钱袋中该字段无值
    private String img; //借款人照片地址   @remark 目前该字段不传值
    private String company;   //	公司名
    private String tel;   //	公司电话
    private String department;   //	公司部门 @remark 20171128 目前小额钱袋中无该字段
    private String position;   //	公司职位 @remark 20171128 目前小额钱袋中无该字段
    @JSONField(name = "company_address")
    private String companyAddress;   //	工作地址
    @JSONField(name = "borrow_uid")
    private String borrowUid;   //	小额钱袋存储的借款人id


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getPersonfontimage() {
        return personfontimage;
    }

    public void setPersonfontimage(String personfontimage) {
        this.personfontimage = personfontimage;
    }

    public String getPersonbackimage() {
        return personbackimage;
    }

    public void setPersonbackimage(String personbackimage) {
        this.personbackimage = personbackimage;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<UserEmerContactModel> getList() {
        return list;
    }

    public void setList(List<UserEmerContactModel> list) {
        this.list = list;
    }

    public Integer getMarriage() {
        return marriage;
    }

    public void setMarriage(Integer marriage) {
        this.marriage = marriage;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getBorrowUid() {
        return borrowUid;
    }

    public void setBorrowUid(String borrowUid) {
        this.borrowUid = borrowUid;
    }
}
