package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.cl.model.OpinionModel;
import com.adpanshi.cashloan.cl.service.OpinionService;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.util.RdPage;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import com.github.pagehelper.Page;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
* 意见反馈表Controller
*
* @author
* @version 1.0.0
* @date 2017-02-14 11:30:57
*
*/
@Controller
@Scope("prototype")
public class OpinionController extends BaseController {

   @Resource
   private OpinionService opinionService;

   /**
    * 保存意见
    * @param opinion
    * @throws Exception
    */
   @RequestMapping(value = "/api/act/mine/opinion/submit.htm", method = RequestMethod.POST)
   public void submit (@RequestParam(value="opinion",required=false) String opinion) throws Exception {
       long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
       Map<String,Object> resultMap = new HashMap<String,Object>();
       int result = opinionService.save(userId, opinion);
       if (result > 0) {
           resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           resultMap.put(Constant.RESPONSE_CODE_MSG, "提交成功");
       } else {
           resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           resultMap.put(Constant.RESPONSE_CODE_MSG, "提交失败");
       }
       ServletUtils.writeToResponse(response,resultMap);
   }

   /**
    * 获取意见
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @RequestMapping(value = "/api/act/mine/opinion/page.htm", method = RequestMethod.POST)
   public void page(
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
       Map<String,Object> paramMap = new HashMap<>(16);
       paramMap.put("userId", userId);
       Page<OpinionModel> page = opinionService.page(paramMap, current, pageSize);
       Map<String,Object> resultMap = new HashMap<>(16);
       Map<String,Object> data = new HashMap<>(16);
       data.put("list", page);
       resultMap.put(Constant.RESPONSE_DATA, data);
       resultMap.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       resultMap.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,resultMap);
   }
}
