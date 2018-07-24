package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.model.DailyData;
import com.adpanshi.cashloan.business.cl.model.DayPassApr;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 新日报统计接口
 * Created by cc on 2017/7/31.
 */
@RDBatisDao
public interface DailyDataMapper {

    List<DailyData> dayData(Map<String, Object> params);

    void saveDayData();

    List<DayPassApr> dayApr(Map<String, Object> params);

}
