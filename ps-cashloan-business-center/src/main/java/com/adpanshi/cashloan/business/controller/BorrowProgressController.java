package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.cl.domain.BorrowMainProgress;
import com.adpanshi.cashloan.cl.model.BorrowProgressModel;
import com.adpanshi.cashloan.cl.service.BorrowMainProgressService;
import com.adpanshi.cashloan.cl.service.BorrowProgressService;
import com.adpanshi.cashloan.cl.service.ClBorrowService;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.core.domain.Borrow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 借款进度表Controller
*
* @author
* @version 1.0.0
* @date 2017-02-14 10:31:04
*
*
*
*
*/
@Scope("prototype")
@Controller
public class BorrowProgressController extends BaseController {
    final Logger logger = LoggerFactory.getLogger(BorrowProgressController.class);
   @Resource
   private BorrowProgressService borrowProgressService;
   @Resource
   private ClBorrowService clBorrowService;
   @Resource
   private BorrowMainProgressService borrowMainProgressService;

   /**
    * 借款进度查询
    * @param borrowId
    * @throws Exception
    */
   @RequestMapping(value = "/api/act/mine/borrow/findProgress.htm", method = RequestMethod.POST)
   public void findProgress(
           @RequestParam(value="borrowId") long borrowId) throws Exception {
       logger.info("借款订单详情:borrowId为"+borrowId);
       Borrow borrow = clBorrowService.findBorrowByMainId(borrowId);
       Map<String,Object> data = new HashMap<String,Object>();
       List<BorrowProgressModel> list = new ArrayList<BorrowProgressModel>();
       List<BorrowMainProgress> mainProgressList = new ArrayList<BorrowMainProgress>();
       if(borrow!=null){
           data = borrowProgressService.result(borrow);
           list = borrowProgressService.borrowProgress(borrow, "detail");
       }else{
           //获取主订单详情
           data = borrowMainProgressService.result(borrowId);
           //获取主订单借款记录
           mainProgressList = borrowMainProgressService.getProcessById(borrowId,borrow);
       }
       data.put("mainList", mainProgressList);
       data.put("list", list);
       if(list != null && !list.isEmpty()){
           data.put("isBorrow", true);
           logger.info("借款订单进度条数为"+list.size());
       }
       Map<String,Object> result = new HashMap<>();
       result.put(Constant.RESPONSE_DATA, data);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }
}
