package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.util.DateUtil;
import com.adpanshi.cashloan.core.common.util.RdPage;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.core.service.NoticesService;
import com.github.pagehelper.Page;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: fenqidai
 * @description: 消息Controller
 * @author: Mr.Wange
 * @create: 2018-07-23 09:32
 **/
@Scope("prototype")
@Controller
public class NoticesController extends BaseController {

    @Resource
    private NoticesService noticesService;

    @RequestMapping(value = "api/act/notices/list.htm")
    public void qryNoticesList(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "currentPage") Integer currentPage,
            @RequestParam(value = "pageSize") Integer pageSize
    ) throws Exception {
        Map<String, Object> result = new HashMap<>(16);
        Map paramMap = new HashMap(16);
        paramMap.put("type",type);
        long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
        paramMap.put("userId",userId);

        Page page = noticesService.queryNoticesList(paramMap,currentPage,pageSize);
        result.put(Constant.RESPONSE_DATA, page);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response,result);
    }

    @RequestMapping(value = "api/act/notices/noticesReadState.htm")
    public void queryNoticesReadState(
            @RequestParam(value = "time", required = false) String time
    ) throws Exception {
        Map<String, Object> result = new HashMap<>(16);
        Map paramMap = new HashMap(16);
        if(StringUtil.isNotBlank(time)) {
            Date date = DateUtil.parse(time, DateUtil.DATEFORMAT_STR_001);
            paramMap.put("time",date);
        }
        long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
        paramMap.put("userId",userId);
        int count = noticesService.queryNoticesReadState(paramMap);
        paramMap.put("isUnread",count>0);
        result.put(Constant.RESPONSE_DATA, paramMap);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response,result);
    }
}
