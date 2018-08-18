package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.rule.domain.UserEmerContacts;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 紧急联系人表Service
 *
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:24:05
 */
public interface UserEmerContactsService extends BaseService<UserEmerContacts, Long> {

    /**
     * 保存并更新联系人
     *
     * @param name
     * @param phone
     * @param relation
     * @param type
     * @param userId
     * @param operatingSystem
     * @param systemVersions
     * @param phoneType
     * @param phoneBrand
     * @param phoneMark
     * @param versionName
     * @param versionCode
     * @param mac
     * @return
     */
    Map<String, Object> saveOrUpdate(String name, String phone, String relation, String type, String userId,
                                     String operatingSystem, String systemVersions, String phoneType,
                                     String phoneBrand,  String phoneMark, String versionName, String versionCode,
                                     String mac, HttpServletRequest request);

}
