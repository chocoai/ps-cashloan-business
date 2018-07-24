package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.core.domain.BorrowMainModel;
import com.adpanshi.cashloan.business.rule.model.IndexModel;

import java.util.List;
import java.util.Map;

/**
 * @author yecy
 * @date 2017/12/18 9:26
 */
@RDBatisDao
public interface BorrowMainModelMapper {

    /**
     * 首页借款成功轮播信息
     *
     * @return
     */
    List<IndexModel> listIndexWithPhone();

    /**
     * 获取消贷同城推送失败的借款订单
     *
     * @param searchMap 搜索条件
     * @return
     */
    List<BorrowMainModel> listBorrowMainModelByFailLoanCity(Map<String, Object> searchMap);

}
