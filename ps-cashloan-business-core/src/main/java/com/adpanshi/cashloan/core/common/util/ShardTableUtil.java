package com.adpanshi.cashloan.core.common.util;

import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.enums.TableDataEnum.TABLE_DATA;

import java.util.Calendar;
import java.util.Date;

/**
 * 工具类-分表
 * @version 1.0.0
 * @date 2017年6月5日 上午9:54:09
 * Copyright 粉团网路 金融创新事业部 cashloan  All Rights Reserved
 * @author 8452
 *
 */
public class ShardTableUtil {

	public static final String TONGDUN_REQ_LOG_TABLE ="cl_tongdun_req_log";
	/**
	 * 根据主键Id生成分表名称
	 * @param shardId 拆分id段
	 * @return
	 */
	public static String generateTableNameById(String tableName, long id, long shardId){
		long num = id/shardId + 1;
		return tableName + "_" + num;
	}
	
	/**
	 * @deprecated 此接口逻辑上不严禁、造成分表中最大一张分表始终使用不到(比如:分表序号从:1-256、而序号为256的表始终是为空的、
	 *             而原本序号为256表中的数据会往序号1的表中插入、这样就会对以后做统计等业务时增加难度)
	 * <p>如果是新业务、做分表时请调用此接口{@link #getTableNameByParam(Long id,TABLE_DATA tableData)}</p>
	 * @param id (比如:userId)
	 * @param tableData
	 * @return String 重构好的表名
	 * */
	/*public static String getTableNameById(Long id,TABLE_DATA tableData){
		String tableName=tableData.getCode();
		long count=id%tableData.getMax();
		if(count==0) count=1;
		return tableName+"_"+count;
	}*/
	
	/**
	 * @deprecated 此接口逻辑上不严禁、造成分表中最大一张分表始终使用不到(比如:分表序号从:1-256、而序号为256的表始终是为空的、
	 *             而原本序号为256表中的数据会往序号1的表中插入、这样就会对以后做统计等业务时增加难度)
	 * <p>如果是新业务、做分表时请调用此接口{@link #getTableNameByParam(Long id,Long maxId,TABLE_DATA tableData)}</p>
	 * @param id 前台传入的id(比如:userId)
	 * @param maxId 系统中已配置好的最大 maxId
	 * @param tableData 表的一些基本信息
	 * @return String 拼接好的表名
	 * */
	/*public static String getTableNameById(Long id,Long maxId,TABLE_DATA tableData){
		if(id.longValue()>=maxId.longValue()){
			return getTableNameById(id, tableData);
		}
		String tableName=tableData.getCode();
		return tableName;
	}*/
	
	/**
	 * <p>根据给定参数，查询对应的表名(注意:新的需求、需要分表时请使用此接口)</p>
	 * @param id (比如:userId)
	 * @param tableData
	 * @return String 重构好的表名
	 * */
	public static String getTableNameByParam(Long id,TABLE_DATA tableData){
		String tableName=tableData.getCode();
		long count=id%tableData.getMax();
		if(count==0) count=tableData.getMax();
		return tableName+"_"+count;
	}
	
	/**
	 * 根据给定参数，查询对应的表名(注意:新的需求、需要分表时请使用此接口)
	 * @param id 前台传入的id(比如:userId)
	 * @param maxId 系统中已配置好的最大 maxId
	 * @param tableData 表的一些基本信息
	 * @return String 拼接好的表名
	 * */
	public static String getTableNameByParam(Long id,Long maxId,TABLE_DATA tableData){
		if(id.longValue()>=maxId.longValue()){
			return getTableNameByParam(id, tableData);
		}
		String tableName=tableData.getCode();
		return tableName;
	}


	/**
	 * <p>根据给定参数，查询对应的表名(注意:cl_tongdun_req_log)</p>
	 * @param borrowId (比如:userId)
	 * @return String 重构好的表名
	 * */
	public static String getTongdunReqLogTableName(Long borrowId, Date createTime){
		String tableName=TONGDUN_REQ_LOG_TABLE;
		Calendar c=Calendar.getInstance();
		c.setTime(createTime);
		int shardBorrowId= Integer.valueOf(Global.getValue("td_shard_borrow_id"));
		if(borrowId>shardBorrowId){
			return tableName+"_"+c.get(Calendar.YEAR)+"_"+(c.get(Calendar.MONTH)+1);
		}else{
			return tableName;
		}
	}

	/**
	 * <p>根据给定参数，查询对应的表名(注意:cl_tongdun_resp_detail)</p>
	 * @param borrowId (比如:userId)
	 * @return String 重构好的表名
	 * */
	public static String getTongdunRespDetailTableName(Long borrowId, Date createTime){
		String tableName="cl_tongdun_resp_detail";
		Calendar c=Calendar.getInstance();
		c.setTime(createTime);
		int shardBorrowId= Integer.valueOf(Global.getValue("td_shard_borrow_id"));
		if(borrowId>shardBorrowId){
			return tableName+"_"+c.get(Calendar.YEAR)+"_"+ (c.get(Calendar.MONTH)+1);
		}else{
			return tableName;
		}
	}
	
}
