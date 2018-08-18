package com.adpanshi.cashloan.business.user.domain;

import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: hujin3
 * @description: 数据封存
 * @author: Mr.Wange
 * @create: 2018-08-10 16:38
 **/
@Service("userDataPackageDomain")
public class UserDataPackageNativeDomain implements UserDataPackageDomain {
    @Resource
    private UserBaseInfoService userBaseInfoService;

    /**
     * 数据封存
     *
     * @param userId
     * @return
     */
    @Override
    public Integer packageSave(Long userId) {
        //用户详细信息
        UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(userId);
        if(userBaseInfo.getUserDataId()!=null) {
            Map map = new HashMap(16);
            map.put("id", userBaseInfo.getId());
            map.put("isSeal", "1");
            map.put("sealTime", new Date());
            boolean flg = userBaseInfoService.updateSelective(map);
            if (flg) {
                return 1;
            }
        }
        return 0;
    }
}
