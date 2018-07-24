package com.adpanshi.cashloan.business.cr.model.srule.config.builder;

import com.adpanshi.cashloan.business.cr.model.srule.config.rule.Rule;
import com.adpanshi.cashloan.business.cr.model.srule.config.rule.RuleBasic;

/**
 * Created by syq on 2016/12/18.
 */
@SuppressWarnings("rawtypes")
public interface Builder {
	

    /**
     * storing the rules which  produced from each thread
     */
	ThreadLocal<RuleBasic> threadLocalRules = new ThreadLocal<RuleBasic>();


    /**
     * return the com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.rule want to build
     *
     * @return
     * @throws Exception
     */
    Rule build() throws Exception;


}
