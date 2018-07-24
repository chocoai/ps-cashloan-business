package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.ProfitLevel;
import com.adpanshi.cashloan.business.cl.service.ProfitLevelService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 分润等级Controller
*
* @author
* @version 1.0.0
* @date 2017-02-18 16:58:10
*
*
*
*
*/
@Scope("prototype")
@Controller
public class ProfitLevelController extends BaseController {

   @Resource
   private ProfitLevelService profitLevelService;

   /**
    * 查询列表
    * @throws Exception
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/profitLevel/find.htm", method = RequestMethod.POST)
   public void find() throws Exception {
       List<ProfitLevel> list = profitLevelService.find();
       Map<String,Object> map = new HashMap<>();
       map.put("list", list);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, map);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 修改分润等级
    * @throws Exception
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/profitLevel/update.htm", method = RequestMethod.POST)
   public void update(
           @RequestParam(value="id") long id,
           @RequestParam(value="rate") double rate
           ) throws Exception {
       int msg = profitLevelService.update(id,rate);
       Map<String,Object> result = new HashMap<String,Object>();
       if (msg>0) {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "修改成功");
       }else {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "修改成功");
       }
       ServletUtils.writeToResponse(response,result);
   }

}
