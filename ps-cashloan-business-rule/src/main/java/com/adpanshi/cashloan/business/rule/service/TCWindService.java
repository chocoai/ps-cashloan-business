package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.domain.BorrowMain;

/**
 * 天创大风策接口
 *
 * @author yecy
 * @date 2018/2/6 14:42
 */
public interface TCWindService {
    /**
     * @deprecated 计划永远赶不上变化(评估额度不是很准确)
     * 
     * 调用第三方评估用户信用额度
     *
     * @param userId 用户id
     */
    Double assessmentAmount_(Long userId);

    /**
     * 
     * @deprecated 请使用 {@link #review(Long userId,String ip,String address,String mobileType,String loanReason)}
     * 
     * <p>下单之后调用(走风控时调用)</p>
     * 调用大风策用户额度审批接口(如果接口调用失败，则返回建议审核)
     *
     * @param borrowMain 接口订单
     * @param mobileType 手机系统类型
     */
    String review(BorrowMain borrowMain, String mobileType);
    
    /**
     * <p>下单之前调用</p>
     * 调用大风策用户额度审批接口(如果接口调用失败，则返回建议审核)
     * @param userId
     * @param ip
     * @param address
     * @param mobileType 手机系统类型
     * @param loanReason  贷款原因
     * @return 额度
     */
    Double review(Long userId, String ip, String address, String mobileType, String loanReason);
    
    
}
