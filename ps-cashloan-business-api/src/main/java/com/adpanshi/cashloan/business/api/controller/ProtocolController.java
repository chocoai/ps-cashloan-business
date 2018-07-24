package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.service.EnjoysignRecordService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.enjoysign.enums.StatusEnum;
import com.adpanshi.cashloan.business.system.domain.SysConfig;
import com.adpanshi.cashloan.business.system.service.SysConfigService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;


/**
* 协议Controller
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
public class ProtocolController  extends BaseController{
	
	@Resource
	private SysConfigService sysConfigService;
	
	@Resource
	private EnjoysignRecordService enjoysignRecordService;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	/**
	 * 获取协议清单
	 * @param search
	 * @throws Exception
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/protocol/list.htm", method = RequestMethod.GET)
	public void list(@RequestParam(value="borrowMainId",required=false)String borrowMainId) throws Exception {
		Map<String,Object>  data = new HashMap<>();
		
		List<Map<String,Object>> dataList= new ArrayList<Map<String,Object>>();
		List<SysConfig> list= sysConfigService.listByCode("protocol_");
		Boolean flag=Boolean.FALSE;
		String contractURL=null;
		if(!StringUtil.isEmpty(borrowMainId)){
			List<Integer> statusList=Arrays.asList(StatusEnum.SUCCESS.getKey(),StatusEnum.DOWNLOAD_FAIL.getKey(),StatusEnum.DOWNLOAD_SUCCESS.getKey());
			contractURL=enjoysignRecordService.getRemoteContractURLByOrderId(statusList, borrowMainId);
			if(!StringUtil.isEmpty(contractURL)){
				flag=true;
			}
		}
		logger.info("-----服务协议[借款协议]-borrowMinId={}、contractURL={}.",new Object[]{borrowMainId,contractURL});
		for(int i=0;i<list.size();i++){
			Map<String,Object> pro =new HashMap<>();
			String code=list.get(i).getCode();
			String value=list.get(i).getValue();
			if(flag && code.equals("protocol_borrow")){
				value=contractURL;
			}
			pro.put("code",code);
			pro.put("value",value);
			pro.put("name",list.get(i).getName());
			dataList.add(pro);
		}
		data.put("list", dataList);
		logger.info("-borrowMinId={}、---protocol_={}.",new Object[]{borrowMainId,JSONObject.toJSONString(dataList)});
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}

}
