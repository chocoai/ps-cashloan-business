package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.cl.domain.BorrowRepay;
import com.adpanshi.cashloan.cl.domain.CreditsUpgrade;
import com.adpanshi.cashloan.cl.extra.ExtraData;
import com.adpanshi.cashloan.cl.extra.credits.CreditsExtra;
import com.adpanshi.cashloan.cl.mapper.CreditsUpgradeMapper;
import com.adpanshi.cashloan.cl.service.CreditsUpgradeService;
import com.adpanshi.cashloan.core.common.enums.CreditsUpgradeEnum;
import com.adpanshi.cashloan.core.common.enums.CreditsUpgradeEnum.SEND_STATUS;
import com.adpanshi.cashloan.core.common.exception.BussinessException;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.DateUtil;
import com.adpanshi.cashloan.core.common.util.ReflectUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.creditrank.cr.domain.Credit;
import com.adpanshi.creditrank.cr.mapper.CreditMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-10-30 20:46:14
 * @history
 */
@Service("creditsUpgradeService")
public class CreditsUpgradeServiceImpl extends BaseServiceImpl<CreditsUpgrade,Long> implements CreditsUpgradeService{

	Logger logger = LoggerFactory.getLogger(CreditsUpgradeServiceImpl.class);
   
    @Resource
    private CreditsUpgradeMapper creditsUpgradeMapper;
    
    @Resource
    private CreditMapper creditMapper;

	@Override
	public BaseMapper<CreditsUpgrade, Long> getMapper() {
		return creditsUpgradeMapper;
	}

	/**
	 * <p> 额度提升记录作废</p>
	 * @param userId 
	 * @param remarks   额度表中的说明(什么原因做废的)
	 * @return int 
	 * */
	@Override
	public int updateCreditUpgradeExpired(Long userId,String remarks){
		int count=0;
		if(!isCreditsOpen()) {return count;}
		List<CreditsUpgrade> creditsList= creditsUpgradeMapper.queryByUserIdWithStatus(userId, Arrays.asList(CreditsUpgradeEnum.Status.NORMAL.getKey()),null);
		if(null==creditsList || creditsList.size()==0) {return 0;}
		Date curDate=new Date();
		String handleTime=DateUtil.dateToString(curDate, DateUtil.YYYY_MM_DD_HH_MM_SS);
		CreditsUpgrade updateEntity=new CreditsUpgrade();
		updateEntity.setGmtUpdateTime(curDate);
		for(CreditsUpgrade credits:creditsList){
			try {
				String result=getRemarks(remarks, handleTime, credits.getRemarks());
				updateEntity.setRemarks(result);
				updateEntity.setStatus(CreditsUpgradeEnum.Status.UN_NORMAL.getKey());
				updateEntity.setId(credits.getId());
				Map<String,Object> updateMap=ReflectUtil.clzToMap(updateEntity);
				count+=creditsUpgradeMapper.updateSelective(updateMap);
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		logger.info("---------------->userId={}的提额度记录更新为作废,完毕，受影响的行数为:{}条.",new Object[]{userId,count});
		return count;
	}
	
	@Override
	public int updateCreditUpgradeBySendStatus(Long userId, String remarks,SEND_STATUS sendStatus) {
		int count=0;
		if(!isCreditsOpen()) {return count;}
		List<Integer> sendStatusList=Arrays.asList(SEND_STATUS.WAIT_SEND.getKey(),SEND_STATUS.FAILURE_SEND.getKey());
		List<CreditsUpgrade> creditsList= creditsUpgradeMapper.queryByUserIdWithStatus(userId, Arrays.asList(CreditsUpgradeEnum.Status.NORMAL.getKey()),sendStatusList);
		if(null==creditsList || creditsList.size()==0) {return 0;}
		Date curDate=new Date();
		String handleTime=DateUtil.dateToString(curDate, DateUtil.YYYY_MM_DD_HH_MM_SS);
		CreditsUpgrade updateEntity=new CreditsUpgrade();
		updateEntity.setSendStatus(sendStatus.getKey());
		updateEntity.setGmtUpdateTime(curDate);
		for(CreditsUpgrade credits:creditsList){
			try {
				String result=getRemarks(remarks, handleTime, credits.getRemarks());
				updateEntity.setRemarks(result);
				updateEntity.setId(credits.getId());
				Map<String,Object> updateMap=ReflectUtil.clzToMap(updateEntity);
				count+=creditsUpgradeMapper.updateSelective(updateMap);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("",e);
			}
		}
		logger.info("---------------->userId={}的提额度记录更新为作废,完毕，受影响的行数为:{}条.准备更新用户信用额度...",new Object[]{userId,count});
		return 0;
	}
	
	@Override
	public List<CreditsUpgrade> queryCreditsByStatusWithGroupUserId(Integer borrowRepayState,Integer creditsUpgradeStatus,List<Integer> sendStatusList) {
		if(!isCreditsOpen()) {return null;}
		if(StringUtil.isEmpty(borrowRepayState,creditsUpgradeStatus,sendStatusList)){
			logger.error("---------------->缺少必要参数，不走正常路...");
			return null;
		}
		return creditsUpgradeMapper.queryCreditsByStatusWithGroupUserId(borrowRepayState, creditsUpgradeStatus,sendStatusList);
	}

	@Override
	public Double getCreditsByUserId(Long userId){
		Double tmpCredits=new Double(0);
		if(!isCreditsOpen()) {return tmpCredits;}
		if(null==userId) {return tmpCredits;}
		List<CreditsUpgrade> creditsList= creditsUpgradeMapper.queryCreditsUpgradeByUserId(userId, CreditsUpgradeEnum.Status.NORMAL.getKey());
		if(null==creditsList || creditsList.size()==0) {return tmpCredits;}
		try {
			return handleTmpCredits(creditsList);
		} catch (Exception e) {
			logger.error("",e);
		}
		return tmpCredits;
	}
	
	/**
	 * <p>获取用户的临时额度</p>
	 * @param creditsList
	 * @return Double
	 * */
	Double handleTmpCredits(List<CreditsUpgrade> creditsList)throws Exception{
		Double tmpCredits=new Double(0);
		if(!isCreditsOpen()) {return tmpCredits;}
		if(null==creditsList || creditsList.size()==0) {return tmpCredits;}
		//已使用临时额度总和.
		Double totalUsed=new Double(0);
		//用户获取赠送的临时额度总和
		Double totalTmpCredits=new Double(0);
		for(CreditsUpgrade creditsUpgrade:creditsList){
			boolean leveFlag=CreditsUpgradeEnum.checkCreditsLeve(creditsUpgrade.getCreditsLeve()); 
			boolean creditsFlag=CreditsUpgradeEnum.checkCredits(creditsUpgrade.getCredits());
			if(!leveFlag || !creditsFlag){
				logger.error("---------------->userId={}的用户临时额度糟遇非法篡改!");
				throw new  Exception("临时额度糟遇非法篡改!");
			}
			//已使用额度;
			Double used=creditsUpgrade.getUsed();
			//赠送的临时额度;
			Double credits=creditsUpgrade.getCredits();
			if(used.doubleValue()>credits.doubleValue()){
				logger.error("---------------->userId={}的用户临时额度糟遇非法篡改!");
				throw new  Exception("临时额度糟遇非法篡改!");
			}
			totalUsed+=+used.doubleValue();
			totalTmpCredits+=+credits.doubleValue();
		}
		tmpCredits=totalTmpCredits.doubleValue()-totalUsed;
		return tmpCredits;
	}

	@Override
	public List<CreditsUpgrade> queryCreditsInvalidByParams(Integer borrowRepayState, Integer creditsUpgradeStatus) {
		if(!isCreditsOpen()) {return null;}
		if(StringUtil.isNotEmptys(borrowRepayState,creditsUpgradeStatus)){
			return creditsUpgradeMapper.queryCreditsInvalidByParams(borrowRepayState, creditsUpgradeStatus);
		}
		return null;
	}
	
	@Override
	public int modifyTmpCredits(Long userId, Double amount,boolean repay) {
		int count=0;
//		if(!isCreditsOpen()) return count;
		// @change 去除开关判定，开关判定应该是在下单的时候 @author yecy 20171225
		if (null == amount || amount.equals(0d)) {
			logger.info("-------------->userId={}的用户，未曾使用临时额度借款、跳过后续业务逻辑...",new Object[]{userId});
			return 0;
		}
		List<CreditsUpgrade> creditsList= creditsUpgradeMapper.queryCreditsUpgradeByUserId(userId, CreditsUpgradeEnum.Status.NORMAL.getKey());
		logger.info("--------------***************>userId={}的用户，临时额度有{}条数据....",new Object[]{userId,creditsList.size()});
		//@change 放款（或还款）时，金额存在额度提升，但是未找到用户提额记录,直接抛出异常(用户成功放款后，额度提升不会过期) @author yecy 20171225
		if (CollectionUtils.isEmpty(creditsList)) {
			throw new BussinessException("用户userId:{}放款（或还款）时额度提升，但是未查询到额度提升记录",
					userId.toString());
		}
		return handlModifyTmpCredits(amount, repay, creditsList);
		
	}
	
	@Override
	public boolean isCreditUpgradeUser(Long userId) {
		if(!isCreditsOpen()) {return false;}
		List<CreditsUpgrade> creditsList= creditsUpgradeMapper.queryCreditsUpgradeByUserId(userId, CreditsUpgradeEnum.Status.NORMAL.getKey());
		return (null!=creditsList && creditsList.size()>0);
	}

	@Override
	public boolean isCreditUpgradeUserWithoutSwitch(Long userId) {
		List<CreditsUpgrade> creditsList= creditsUpgradeMapper.queryCreditsUpgradeByUserId(userId, CreditsUpgradeEnum.Status.NORMAL.getKey());
		return (null!=creditsList && creditsList.size()>0);
	}

	/**
	 * 放款后信用额度修改
	 *
	 * @param userId
	 * @param amount
	 * @change 业务修改，增加分期功能，信用额度功能变更 @author yecy 20171225
	 */
	@Override
	public int modifyCreditAfterLoan(Long userId, double amount) {

		Map<String, Object> creditMap = new HashMap<String, Object>();
		creditMap.put("consumerNo", userId.toString());
		Credit credit = creditMapper.findSelective(creditMap);
		if (credit != null) {
			// 此处不需要加入额度开关判定，开关判定应该是在下单的时候 @author yecy 20171225
			Double unused = credit.getUnuse();
			Double temp = BigDecimalUtil.sub(unused, amount);
			// 如果 用户信用额度<放款金额 那么这个用户肯定是有过提额、并且还使用了临时额度进行借款...
			Double usedAmount;
			double tmpCredits;//已使用临时额度
			if (temp < 0) {
				boolean isCreditUpgradeUser = isCreditUpgradeUserWithoutSwitch(userId);
				logger.info("【放款】临时额度提升修改-userId={}.amount={},isCreditUpgradeUser={}.", new Object[]{userId, amount, isCreditUpgradeUser});
				if (!isCreditUpgradeUser) {
					throw new BussinessException("【放款】用户userId:{}额度未提升，但是放款金额超出信用额度", userId.toString());
				}
				tmpCredits = Math.abs(temp);
				if (!CreditsUpgradeEnum.checkCredits(tmpCredits)) {
					throw new BussinessException("【放款】用户userId:{}额度提升金额，但是放款金额与临时额度提升金额不符", userId.toString());
				}
				usedAmount = unused;
			} else {
				tmpCredits = 0;
				usedAmount = amount;
			}
			logger.info("【放款】------------------->修改信用额度前[if]:userId={},used={},unsed={}.", new Object[]{userId, usedAmount, -usedAmount});
			//去除最低可借款额度判断，放款后的额度修改应该根据用户实际额度去修改，如果需要判断，应该在下单的时候判断（目前下单未判断） @author yecy 20171225
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", credit.getId());
			params.put("used", usedAmount);
			params.put("unuse", -usedAmount);
			int result = creditMapper.updateAmount(params);
			if (result != 1) {
				throw new BussinessException("【放款】更新额度失败，不能出现负值,userId:" + userId);
			}
			modifyTmpCredits(userId, tmpCredits, false);
			return result;
		} else {
			throw new BussinessException("【放款】更新额度失败，未找到用户额度信息，userId:" + userId);
		}
	}


	@Override
	public void modifyCreditAfterRepay(Long userId, Double borrowAmount) {
		Map<String, Object> creditMap = new HashMap<>();
		creditMap.put("consumerNo", userId.toString());
		Credit credit = creditMapper.findSelective(creditMap);
		if (credit != null) {
			Double used = credit.getUsed();
			Double temp = BigDecimalUtil.sub(used, borrowAmount);
			double tmpCredits;//已使用临时额度
			Double usedAmount;

			if (temp < 0) {
				boolean isCreditUpgradeUser = isCreditUpgradeUserWithoutSwitch(userId);
				logger.info("【还款】临时额度提升修改-userId={}.amount={},isCreditUpgradeUser={}.", new Object[]{userId, borrowAmount, isCreditUpgradeUser});
				if (!isCreditUpgradeUser) {
					throw new BussinessException("【还款】用户userId:{}额度未提升，但是还款金额超出信用额度", userId.toString());
				}
				tmpCredits = Math.abs(temp);
				if (!CreditsUpgradeEnum.checkCredits(tmpCredits)) {
					throw new BussinessException("【还款】用户userId:{}额度提升金额，但是还款金额与临时额度提升金额不符", userId.toString());
				}
				usedAmount = used;
			} else {
				tmpCredits = 0;
				usedAmount = borrowAmount;
			}
			logger.info("【还款】------------------->修改信用额度前[if]:userId={},used={},unsed={}.", new Object[]{userId,
					credit.getUsed(), credit.getUnuse()});
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", credit.getId());
			params.put("used", -usedAmount);
			params.put("unuse", usedAmount);
			int result = creditMapper.updateAmount(params);
			if (result != 1) {
				throw new BussinessException("【还款】更新额度失败，不能出现负值,userId:" + userId);
			}
			modifyTmpCredits(userId, tmpCredits, true);

		} else {
			throw new BussinessException("【还款】用户信用额度信息不存在" + userId);
		}

	}

	//-------------------------------------------------------> private method ------------------------------------------------------->
	
	/**
	 * <p>根据repay修改临时额度已使用额度</>
	 * @param amount
	 * @param repay (true:还款、false:放款)
	 * @param creditsList
	 * @return int
	 * */
	int handlModifyTmpCredits(Double amount,boolean repay,List<CreditsUpgrade> creditsList){
		//FIXME 分期第一期，不考虑提额业务，所以方法未修改，此处逻辑可能有问题 @author yecy 20171225
		int count=0;
		if(null==creditsList || creditsList.size()==0) {return count;}
		Date curDate=new Date();
		logger.info("---------->amount={},repay={}.creditsList.size={}",new Object[]{amount,repay,creditsList.size()});
		if(repay){
			//还款处理  ( 需要把已使用的临时额度都恢复至0;)
			for(CreditsUpgrade creditsUpgrade:creditsList){
				if(creditsUpgrade.getUsed().doubleValue()>0){
					CreditsUpgrade updateCredits=new CreditsUpgrade();
					updateCredits.setId(creditsUpgrade.getId());
					updateCredits.setGmtUpdateTime(curDate);
					String desc="[还款]已使用额度由原来的:"+creditsUpgrade.getUsed()+"修改成:"+amount;
					String handleTime=DateUtil.dateToString(curDate, DateUtil.YYYY_MM_DD_HH_MM_SS);
					String remarks=getRemarks(desc, handleTime, creditsUpgrade.getRemarks());
					updateCredits.setRemarks(remarks);
					updateCredits.setUsed(amount);
					Map<String,Object> paramMap=ReflectUtil.clzToMap(updateCredits);
					count+=creditsUpgradeMapper.updateSelective(paramMap);
				}
			}
			return count;
		}
		double lavecredits=amount.longValue();
		CreditsUpgrade updateCredits=null;
		String desc="";
		for(int i=0;i<creditsList.size();i++){
			CreditsUpgrade creditsUpgrade=creditsList.get(i);
			double credits=creditsUpgrade.getCredits().doubleValue();
			updateCredits=new CreditsUpgrade();
			boolean isUpdate=Boolean.FALSE;
			if(credits==lavecredits){
				desc="[放款]已使用额度由原来的:"+creditsUpgrade.getUsed()+"修改成:"+credits;
				updateCredits.setUsed(credits);
				isUpdate=true;
			}else if(credits>lavecredits && lavecredits>0){
				desc="[放款]已使用额度由原来的:"+creditsUpgrade.getUsed()+"修改成:"+lavecredits;
				updateCredits.setUsed(lavecredits);
				isUpdate=true;
			}else if(lavecredits>0 && credits<lavecredits){
				desc="[放款]已使用额度由原来的:"+creditsUpgrade.getUsed()+"修改成:"+credits;
				updateCredits.setUsed(credits);
				isUpdate=true;
			}
			if(isUpdate){
				lavecredits=lavecredits-credits;
				updateCredits.setId(creditsUpgrade.getId());
				updateCredits.setGmtUpdateTime(curDate);
				String handleTime=DateUtil.dateToString(curDate, DateUtil.YYYY_MM_DD_HH_MM_SS);
				String remarks=getRemarks(desc, handleTime, creditsUpgrade.getRemarks());
				updateCredits.setRemarks(remarks);
				Map<String,Object> paramMap=ReflectUtil.clzToMap(updateCredits);
				count+=creditsUpgradeMapper.updateSelective(paramMap);
			}
		}
		return count;
	}
	
	/**
	 * <p>根据给定数据得到remark</p>
	 * @param desc 描述信息
	 * @param handleTime 处理时间
	 * @param oldDataJson 待更新的JSON串
	 * @return 
	 * */
	String getRemarks(String desc,String handleTime,String oldDataJson){
		ExtraData extraData=new ExtraData();
		extraData.addData("desc", desc);
		extraData.addData("handleTime", handleTime);
		return getRemarksByAddWithUpdate(extraData, oldDataJson);
	}
	
	/**
	 * <p>根据给定数据得到remark</p>
	 * @param extraData
	 * @param oldDataJson
	 * @return 
	 * */
    String getRemarksByAddWithUpdate(ExtraData extraData,String oldDataJson){
		CreditsExtra extra=new CreditsExtra();
		if(StringUtil.isNotEmptys(oldDataJson)){
			extra.updateData(extraData, oldDataJson);
			return extra.toJSON();
		}
		extra.addData(extraData);
		return extra.toJSON();
	};
	
	/**
	 * <p>查询指定用户(复借用户)是否有历史逾期</p>
	 * @param userId 
	 * @return boolean true:是逾期用户(如果是新用户，不符合条件，也会把其看作是逾期用户)，false:不是逾期用户
	 * */
	boolean overdueAssert(Long userId,List<BorrowRepay> borrowRepayList) {
		Boolean flag=Boolean.TRUE;
		if(null==borrowRepayList || borrowRepayList.size()==0){
			logger.info("---------------->userId={}.的用户不是复借用户，不符合额度提升规则.");
			//如果还款计划中，未查询到用户的记录说明用户不是复借用户，直接不通过...
			return flag;
		}
		//查看用户是否有逾期记录...
		for(BorrowRepay repay:borrowRepayList){
			if(StringUtil.isNotEmptys(repay.getPenaltyDay()) && Integer.parseInt(repay.getPenaltyDay())>0){
				return flag;
			}
		}
		return Boolean.FALSE;
	}
	
     boolean isCreditsOpen(){
		if(!CreditsUpgradeEnum.isCreditsSwitchOn()){
			logger.info("---------------->提额开关未开启或不存在，跳过后续业务逻辑..");
			return false;
		}
		return true;
	}
}
