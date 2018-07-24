package com.adpanshi.cashloan.business.api.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Scope("prototype")
@Controller
@RequestMapping("/com/adpanshi/cashloan/api/deviceTokens")
public class AppDeviceTokensController {
	
	/*@Resource
	UserDeviceTokensService userDeviceTokensService;
	
	@RequestMapping("saveOrUpdateUserDeviceTokens")
	public void saveOrUpdateUserDeviceTokens(final HttpServletRequest request,HttpServletResponse response, final Long userId,final String deviceTokens) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				int count=userDeviceTokensService.saveOrUpdateUserDeviceTokens(userId, deviceTokens);
				Map<String,Object> map=new HashMap<>();
				if(count==-1){
					map.put("success", true);
					map.put("msg", "设备id已经存在，不需在次入库。");
				}else if(count>0){
					map.put("success", true);
					map.put("msg", "设备id已经成功入库。");
				}else{
					map.put("success", true);
					map.put("msg", "设备id入库失败。");
				}
				return map;
			}
		};
	}*/
}
