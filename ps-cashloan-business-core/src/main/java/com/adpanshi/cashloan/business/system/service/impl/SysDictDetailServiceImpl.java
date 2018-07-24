package com.adpanshi.cashloan.business.system.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.system.domain.SysDictDetail;
import com.adpanshi.cashloan.business.system.mapper.SysDictDetailMapper;
import com.adpanshi.cashloan.business.system.service.SysDictDetailService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service
 *
 * @version 1.0
 * @since 2014-11-03
 */
@Service(value = "sysDictDetailServiceImpl")
public class SysDictDetailServiceImpl extends BaseServiceImpl<SysDictDetail, Long> implements
        SysDictDetailService {

    @Resource
    private SysDictDetailMapper sysDictDetailMapper;

    @Override
    public boolean deleteSysDictDetail(Long id) {
        return sysDictDetailMapper.deleteByPrimary(id) > 0;
    }

    @Override
    public Long getItemCountMap(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("parentId", id.longValue());
        ;
        return sysDictDetailMapper.getCount(param);
    }

    @Override
    public boolean addOrModify(SysDictDetail sysDictDetail) {
        int count = 0;
        if (null != sysDictDetail.getId() && sysDictDetail.getId().longValue() > 0L) {
            count = sysDictDetailMapper.update(sysDictDetail);
        } else {
            count = sysDictDetailMapper.save(sysDictDetail);
        }
        return count > 0;
    }

    @Override
    public Page<SysDictDetail> getDictDetailList(int currentPage, int pageSize, Long id) {
        PageHelper.startPage(currentPage, pageSize);
        Map<String, Object> param = new HashMap<>();
        param.put("parentId", id.longValue());
        return (Page<SysDictDetail>) sysDictDetailMapper.listSelective(param);
    }

    @Override
    public List<SysDictDetail> listByTypeCode(Map<String, Object> data) {
        return sysDictDetailMapper.listByTypeCode(data);
    }

    @Override
    public BaseMapper<SysDictDetail, Long> getMapper() {
        return sysDictDetailMapper;
    }

	@Override
	public SysDictDetail getDetailByTypeCodeWithItemCode(String typeCode, String itemCode) {
		if(StringUtil.isEmpty(typeCode,itemCode)) return null;
		return sysDictDetailMapper.getDetailByTypeCodeWithItemCode(typeCode, itemCode);
	}

	@Override
	public SysDictDetail getLastDictDetailByTypeCodeWithUserId(String typeCode, Long userId) {
		return sysDictDetailMapper.getLastDictDetailByTypeCodeWithUserId(typeCode, userId);
	}
    @Override
    public List<Map<String, Object>> queryAllDic(){
        return sysDictDetailMapper.queryAllDic();
    }
}