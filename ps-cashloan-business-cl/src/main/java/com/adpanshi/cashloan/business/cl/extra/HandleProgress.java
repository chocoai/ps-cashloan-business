package com.adpanshi.cashloan.business.cl.extra;

import com.adpanshi.cashloan.business.cl.enums.AppStaginStateEnum;
import com.adpanshi.cashloan.business.cl.model.ProgressModel;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import org.springframework.beans.BeanUtils;

import java.util.*;

/***
 ** @category 分期进度默认...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月25日下午3:59:06
 **/
public class HandleProgress {
	
	private String state;
	
	List<ProgressModel> progress;
	
	/*
	 还款成功 1.提交成功 -> 2.审核通过 -> 3.打款成功 -> 4.还款成功
	      还款中 1.提交成功 -> 2.审核通过 -> 3.打款成功 -> 4.还款中 
	      已逾期 1.提交成功 -> 2.审核通过 -> 3.打款成功 -> 4.逾期中 
	      审核中	1.提交成功(stop) -> 2.审核中 -> 3.打款中 -> 4.还款中 
	审核失败(需要显示审核失败原因):1.提交成功 -> 2.审核失败(stop) -> 3.打款失败 -> 4.还失败
					（很遗憾.....）
	*/
	/*   	审核中(12、14、22、25),审核通过(12,20),放款中(26),审核失败(21、27),已完成(40),还款中(待还款)30,已逾期(50)
		  	审核失败(27)、审核中(22)、审核通过(26)、打款中(29)、打款失败(28)、打款成功(30)、还款中(35)、 还款成功(40)、逾期中(50)
	 */
	/**
	 * <p>根据给定进度及状态抽取进度</p>
	 * @param localProgressList
	 * @param state 主订单状态 
	 * @return 
	 * */
	public static HandleProgress magic(List<ProgressModel> localProgressList,String state){
		HandleProgress progressData=new HandleProgress();
		List<ProgressModel> result=new ArrayList<>();
		Map<String,ProgressModel> data=transformMap(localProgressList);
		ProgressModel progressModel= getProgress(data, BorrowModel.STATE_PRE);
		progressModel.setStageName("提交成功");
		String defaultState=null;
		result.add(progressModel);
		if(state.equals(BorrowModel.STATE_REPAY) || state.equals(BorrowModel.STATE_DELAY)){			
			ProgressModel auditPass= getProgress(data, BorrowModel.STATE_AUTO_PASS,BorrowModel.STATE_TEMPORARY_AUTO_PASS,BorrowModel.STATE_PASS);
			auditPass.setStageName("审核通过");
			result.add(auditPass);
			ProgressModel loansPass= getProgress(data, BorrowModel.STATE_REPAY);
			loansPass.setStageName("打款成功");
			result.add(loansPass);
			ProgressModel repaying= localProgressList.get(localProgressList.size()-1);
			if(repaying.hashCode()==loansPass.hashCode()){
				ProgressModel newProgress=new ProgressModel();
				BeanUtils.copyProperties(loansPass, newProgress);
				newProgress.setStageName(state.equals(BorrowModel.STATE_DELAY)?"逾期中":"还款中");
				result.add(newProgress);
			}else{
				repaying.setStageName(state.equals(BorrowModel.STATE_DELAY)?"逾期中":"还款中");
				result.add(repaying);
			}
			defaultState=state.equals(BorrowModel.STATE_DELAY)?AppStaginStateEnum.STATE.DELAY.getCode():AppStaginStateEnum.STATE.REPAYMENT.getCode();
		}else if(state.equals(BorrowModel.STATE_FINISH)){	
			ProgressModel auditPass= getProgress(data, BorrowModel.STATE_AUTO_PASS,BorrowModel.STATE_TEMPORARY_AUTO_PASS,BorrowModel.STATE_PASS);
			auditPass.setStageName("审核通过");
			result.add(auditPass);
			ProgressModel loansPass= getProgress(data, BorrowModel.STATE_REPAY);
			loansPass.setStageName("打款成功");
			result.add(loansPass);
			ProgressModel repayPass= getProgress(data, BorrowModel.STATE_FINISH);
			repayPass.setStageName("还款成功");
			result.add(repayPass);
			defaultState=AppStaginStateEnum.STATE.REPAYMENT_SUCCESS.getCode();
		}else {
			//打款失败
			ProgressModel progres=getProgress(data, BorrowModel.STATE_REPAY_FAIL);
			if(null!=progres){
				progres.setStageName("打款失败");
				result.add(progres);
				initDefault(result);
				progressData.setState(AppStaginStateEnum.STATE.LOAN_FAILED.getCode());
				progressData.setProgress(result);
				return progressData;
			}
			//审核中or审核失败or打款中
			progres=getProgress(data,BorrowModel.STATE_PASS,BorrowModel.STATE_AUTO_PASS,BorrowModel.STATE_WAIT_AGREE,BorrowModel.STATE_WAIT_REPAY);
			if(null!=progres){
				ProgressModel auditPass= getProgress(data, BorrowModel.STATE_PASS,BorrowModel.STATE_AUTO_PASS,BorrowModel.STATE_TEMPORARY_AUTO_PASS);
				auditPass.setStageName("审核通过");
				result.add(auditPass);
				if(progres.hashCode()==auditPass.hashCode()){
					ProgressModel newProgress=new ProgressModel();
					BeanUtils.copyProperties(progres, newProgress);
					newProgress.setStageName("打款中");
					result.add(newProgress);
				}else{
					progres.setStageName("打款中");
					result.add(progres);
				}
				initDefault(result);
				progressData.setState(AppStaginStateEnum.STATE.LOANS_IN.getCode());
				progressData.setProgress(result);
				return progressData;
			}
			//审核失败
			progres=getProgress(data,BorrowModel.STATE_REFUSED,BorrowModel.STATE_AUTO_REFUSED);
			if(null!=progres){
				progres.setStageName("审核失败");
				result.add(progres);
				initDefault(result);
				progressData.setState(AppStaginStateEnum.STATE.AUDIT_FAIL.getCode());
				progressData.setProgress(result);
				return progressData;
			}
			//审核中
			progres=getProgress(data,BorrowModel.STATE_HANGUP,BorrowModel.STATE_NEED_REVIEW,BorrowModel.STATE_TEMPORARY_NEED_REVIEW,BorrowModel.STATE_TEMPORARY_AUTO_PASS);
			if(null!=progres){
				progres.setStageName("审核中");
				result.add(progres);
				initDefault(result);
				progressData.setState(AppStaginStateEnum.STATE.UNDER_REVIEW.getCode());
				progressData.setProgress(result);
				return progressData;
			}
		}
		initDefault(result);
		progressData.setState(defaultState);
		progressData.setProgress(result);
		return progressData;
	}
	
	/**
	 * <p>考虑到app端不需要处理业务逻辑、在list.size< 4 时需要递归填充list</p>
	 * @param result
	 * */
	static void initDefault(List<ProgressModel> result){
		if(result.size()!=4){
			result.add(new ProgressModel());
			initDefault(result);
		}
	}
	
	/**
	 * <p>查询data中是否包含staes中，如果找到则直接返回</p>
	 * @param data
	 * @param states
	 * @return
	 * */
	static ProgressModel getProgress(Map<String,ProgressModel> data,String ...states){
		for(String state:states){
			if(data.containsKey(state)) return data.get(state);
		}
		return null;
	}
	
	/**
	 * <p>转map</p>
	 * @param progress
	 * @return 
	 * */
	static Map<String,ProgressModel> transformMap(List<ProgressModel> progress){
		Map<String,ProgressModel> data=new HashMap<>();
		for(ProgressModel pro:progress){
			data.put(pro.getState(), pro);
		}
		return data;
	}
	
    static void sort(List<ProgressModel> localProgress){
		if(null==localProgress || localProgress.size()==0) return;
		Collections.sort(localProgress, new Comparator<ProgressModel>() {
			@Override
			public int compare(ProgressModel obj1, ProgressModel obj2) {
				int o1=Integer.parseInt(obj1.getState());
				int o2=Integer.parseInt(obj2.getState());
				return Integer.compare(o1,o2);
			}
		});
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<ProgressModel> getProgress() {
		return progress;
	}

	public void setProgress(List<ProgressModel> progress) {
		this.progress = progress;
	}
}
