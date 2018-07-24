package com.adpanshi.cashloan.business.system.service.impl;

import com.adpanshi.cashloan.business.core.common.exception.ServiceException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.system.domain.SysConfig;
import com.adpanshi.cashloan.business.system.mapper.SysConfigMapper;
import com.adpanshi.cashloan.business.system.service.SysConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* User:    mcwang
* DateTime:2016-08-04 03:26:22
* details: 系统参数表,Service实现层
* source:  代码生成器
*/
@Service(value = "sysConfigService")
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig,Long> implements SysConfigService {
    /**
	 * 系统参数表dao层
	 */
    @Resource
    private SysConfigMapper sysConfigMapper;
	
	/**
	*
	*继承关系
	*/
	@Override
	public BaseMapper<SysConfig, Long> getMapper() {
		return sysConfigMapper;
	}
	
	/**
	 * 系统参数表表,插入数据
	 * @param sysConfig 系统参数表类
	 * @return           返回页面map
	 * @throws ServiceException
	 */
	public int insertSysConfig(SysConfig sysConfig){
		return	sysConfigMapper.save(sysConfig);
	}

	/**
	* 系统参数表表,修改数据
	* @param sysConfig 系统参数表类
	* @return           返回页面map
	* @throws ServiceException
	*/
	public int updateSysConfig(SysConfig sysConfig) {
		return	sysConfigMapper.update(sysConfig);
	}
	/**
	 * 系统参数表表,查询数据
	 * @param currentPage
	 * @param pageSize
	 * @param paramMap
	 * @return           返回页面map
	 * @throws ServiceException
	 */
	public Page<SysConfig> getSysConfigPageList(int currentPage,int pageSize,Map<String, Object> paramMap) {
		PageHelper.startPage(currentPage, pageSize);
		return(Page<SysConfig>)sysConfigMapper.listSelective(paramMap);
	}

	@Override
	public List<SysConfig> findAll() {
		return sysConfigMapper.findAll();
	}

	/**
	 * 系统参数表表,根据code模糊查询数据
	 * @return    返回list
	 */
	@Override
	public List<SysConfig> listByCode(String code) {
		return sysConfigMapper.listByCode(code);
	}

	@Override
	public SysConfig selectByCode(String code) {
		return sysConfigMapper.selectByCode(code);
	}
	
}