package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.dispatch.run.bo.DispatchRunResponseBo;
import com.adpanshi.cashloan.dispatch.run.domain.DispatchRunDomain;
import com.adpanshi.cashloan.dispatch.run.enums.StatusCode;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zsw on 2018/6/22.
 *
 */
@Api(value = "cashloan", description = "盘石APP中心")
@RestController
@RequestMapping("/cashloan/dispatch")
public class CashloanDispatchController {

    private static final Logger logger = LoggerFactory.getLogger(CashloanDispatchController.class);

//    @Resource
//    private DispatchRunDomain dispatchRunDomain;

    @ApiOperation("数据查询")
    @RequestMapping(value = "/dataQuery/getOperator", method = RequestMethod.GET)
    public DispatchRunResponseBo dataQuery(@ApiParam("用户ID 字段名:user_id") String user_id)  {

        logger.info("调用获取用户运营商接口,参数:user_id={}", user_id);
        try {
            //入参校验
            if(StringUtils.isBlank(user_id) || StringUtils.isBlank(user_id)){
                return DispatchRunResponseBo.error(StatusCode.PARAMS_WRONG);
            }
//            OperatorBo operatorBo = dispatchRunDomain.getOperatorByUserId(Integer.valueOf(user_id));
            return DispatchRunResponseBo.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return DispatchRunResponseBo.error(StatusCode.OTHER_ERROR,e.getMessage());
        }
    }
}
