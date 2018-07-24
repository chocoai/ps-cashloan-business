package com.adpanshi.cashloan.business.core.common.web.controller;

import com.adpanshi.cashloan.business.system.service.SysAttachmentService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 附件读取
 * 
 * @version 1.0

 * @since 2017-02-15
 */
@Controller
@Scope("prototype")
public class ReadFileController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(ReadFileController.class);
	@Resource
	private SysAttachmentService sysAttachmentService;
	
	@RequestMapping(value = "/readFile.htm")
	public String readImg(HttpServletRequest request, HttpServletResponse response)  {
		sysAttachmentService.getImg(request,response);
		return null;
	}
    
}
