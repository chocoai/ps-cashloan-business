package com.adpanshi.cashloan.business.controller;

import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zsw on 2018/6/23 0023.
 */
@Api(value = "user", description = "用户信息中心")
@RestController
@RequestMapping("/cashloan/dispatch")
public class UserDispatchController {

    private static final Logger logger = LoggerFactory.getLogger(UserDispatchController.class);

}
