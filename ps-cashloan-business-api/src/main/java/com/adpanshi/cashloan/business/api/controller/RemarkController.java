package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.system.domain.SysConfig;
import com.adpanshi.cashloan.business.system.service.SysConfigService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* 备注Controller
* 

* @version 1.0.0
* @date 2017-02-22 13:57:14
*
*
* 客服中心：sly@fentuan123.com
*
*/
@Scope("prototype")
@Controller
public class RemarkController  extends BaseController{
	
	@Resource
	private SysConfigService sysConfigService;
	
	
	/**
	 * 获取备注清单
	 * @param search
	 * @throws Exception
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/remark/list.htm", method = RequestMethod.GET)
	public void list() throws Exception { 
		Map<String,Object>  data = new HashMap<>();
		
		List<Map<String,Object>> dataList= new ArrayList<Map<String,Object>>();
		List<SysConfig> list= sysConfigService.listByCode("remark_");
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> pro =new HashMap<>();
			pro.put("code",list.get(i).getCode());
			pro.put("value",list.get(i).getValue());
			pro.put("name",list.get(i).getName());
			dataList.add(pro);
		}
		data.put("list", dataList);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}

}
