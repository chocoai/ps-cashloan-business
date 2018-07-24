package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.model.tianchuang.UsedBusiInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@RDBatisDao
public interface TCUsedBusiInfoMapper extends BaseMapper<UsedBusiInfo,Long> {

	public void batchInsert(@Param("usedBusiInfos") List<UsedBusiInfo> usedBusiInfos);

}
  