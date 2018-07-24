package com.adpanshi.cashloan.business.cl.extra;

import com.adpanshi.cashloan.business.cl.enums.PettyloanSceneEnum;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.EnumUtil;
import com.adpanshi.cashloan.business.system.domain.SysDictDetail;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年4月8日下午4:59:39
 **/
public class HandleBorrowIntent {

	/**
     * <p>借款意图url穿透绑定</p>
     * @param dictDetails
     * @param userId
     * @return
     * */
   public static List<BorrowIntent> handlDictDetail(List<SysDictDetail> dictDetails,Long userId){
    	if(null==dictDetails||dictDetails.size()==0) return null;
    	List<BorrowIntent> borIntents=new ArrayList<>();
//    	PettyLoanScene loanScene=pettyLoanSceneMapper.getPettyLoanSceneLastByUserId(userId,EnumUtil.getKeysByClz(PettyloanSceneEnum.SCENE_TYPE.class));
    	for(SysDictDetail dict:dictDetails){
    		String url=Global.getValue(EnumUtil.getCodeByKey(PettyloanSceneEnum.SCENE_TYPE.class, Integer.parseInt(dict.getItemCode())));
    		String sceneType=dict.getItemCode();
    		/*if(dict.getItemCode().equals(PettyloanSceneEnum.SCENE_TYPE.HOUSES_DETAIL.getKey().toString())){
    			sceneType=(null!=loanScene?loanScene.getSceneType().toString():sceneType);
    		}*/
    		url=StringUtils.isNotBlank(url)?(url+"?userId="+userId+"&sceneType="+sceneType):"";
    		BorrowIntent borIntent=new BorrowIntent(dict.getId(), dict.getItemCode(), dict.getItemValue(),url);
    		borIntents.add(borIntent);
    	}
    	return borIntents;
    }
   
   /**
    * <p>数据字段转换BorrowIntent</p>
    * @param dictDetails
    * @return BorrowIntent
    * */
   public static BorrowIntent dictDetailTransformBorrowIntent(SysDictDetail dictDetail){
   	if(null==dictDetail) return null;
	return new BorrowIntent(dictDetail.getId(), dictDetail.getItemCode(), dictDetail.getItemValue());
   }
}
