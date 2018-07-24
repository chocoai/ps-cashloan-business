package com.adpanshi.cashloan.business.system.service;

import com.adpanshi.cashloan.business.core.common.exception.ServiceException;
import com.adpanshi.cashloan.business.system.domain.SysConfig;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
* User:    mcwang
* DateTime:2016-08-04 03:26:22
* details: 系统参数表,Service接口层
* source:  代码生成器
*/
public interface SysConfigService {

    /**
     * 系统参数表表,插入数据
     * @param sysConfig 系统参数表类
     * @return           返回页面map
     * @throws ServiceException 
     * @throws Exception
     */
    int insertSysConfig(SysConfig sysConfig);

    /**
     * 系统参数表表,修改数据
     * @param sysConfig 系统参数表类
     * @return           返回页面map
     * @throws ServiceException 
     * @throws Exception
     */
  	int updateSysConfig(SysConfig sysConfig);


    /**
     * 系统参数表表,查询数据
     * @param currentPage
	 * @param pageSize
	 * @param map
     * @return  返回页面map
     * @throws ServiceException 
     * @throws Exception
     */
  	Page<SysConfig> getSysConfigPageList(int currentPage, int pageSize, Map<String, Object> map);

   	 
   	 /**
   	  * 查询所有配置
   	  * @return ist<SysConfig>
   	  */
   	 List<SysConfig> findAll();

	/**
  	  * 根据code模糊查询
  	  * @param code
  	  * @return SysConfig列表
  	  * @throws Exception
  	  */
	List<SysConfig> listByCode(String code);

	/**
	 * 根据code查询
	 * @param code
	 * @return SysConfig
	 * */
	SysConfig selectByCode(String code);

}
