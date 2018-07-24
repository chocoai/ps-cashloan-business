package com.adpanshi.cashloan.business.cr.model.srule.config.builder;

import com.adpanshi.cashloan.business.cr.model.srule.utils.RulePolicy;

import java.util.Map;

/**
 * Created by syq on 2016/12/18.
 */
public interface RuleConfigurer<H> extends Builder {

    /**
     * define the com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.rule policy , is match all or match one
     *
     * @param rulePolicy
     * @return
     */
    RuleConfigurer<H> rulePolicy(RulePolicy rulePolicy);


    /**
     * load the string data to range the match value ,help machine to recognize
     *
     * @param map
     * @return
     */
    RuleConfigurer<H> preLoad(Map<H, Integer> map);


    RuleConfigurer<H> name(String name);


}
