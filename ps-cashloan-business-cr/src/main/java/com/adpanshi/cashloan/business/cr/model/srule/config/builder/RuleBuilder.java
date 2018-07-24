package com.adpanshi.cashloan.business.cr.model.srule.config.builder;

import com.adpanshi.cashloan.business.cr.model.srule.config.condition.ConditionItem;

/**
 * user interface , show the method to use
 * each type com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.rule must have only one builder ,means builder should be the single also
 * Created by syq on 2016/12/17.
 */
public interface RuleBuilder<T> {





    /**
     * com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.rule condition collect
     *
     * @return
     */
    ConditionItem newConditionItems();


    /**
     * pass three param which com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.rule must be needed
     *
     * @param id
     * @param column
     * @param conditionItem
     * @return
     */
    RuleConfigurer<T> newRule(long id, String column, ConditionItem conditionItem) throws IllegalAccessException, InstantiationException;


}
