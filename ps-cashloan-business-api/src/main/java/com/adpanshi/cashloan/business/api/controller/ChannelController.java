package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.Channel;
import com.adpanshi.cashloan.business.cl.service.ChannelService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;


@Scope("prototype")
@Controller
public class ChannelController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
    @Autowired
    private ChannelService channelService;

    /**
     * 验证渠道商信息
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/borrow/channel/judge.htm", method = RequestMethod.GET)
    public void page(@RequestParam(value="code") String code) throws Exception {
        logger.info("渠道商code为"+code);
        Channel channel = channelService.findByCode(code);
        logger.info("渠道商信息为"+channel);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, channel);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response,result);
    }
}
