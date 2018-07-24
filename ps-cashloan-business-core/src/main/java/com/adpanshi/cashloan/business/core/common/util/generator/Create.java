package com.adpanshi.cashloan.business.core.common.util.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Create {
	
	public static final Logger logger = LoggerFactory.getLogger(Create.class);
	public static void main(String[] args) {
		Create ot=new Create();
		ot.test();
	}
	
	public void test(){

		// 数据库连接信息
		String url = "jdbc:mysql://192.168.0.253:3306/xiaoedai_online_20171102_for_huangqin?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
		String MysqlUser = "root";
		String mysqlPassword = "yiqifu@123";
		
		// 数据库及数据表名称
		String database = "xiaoedai_online_20171102_for_huangqin";
		String table = "cl_yincheng_req_log";
		
		// 配置作者及Domain说明
		String classAuthor = "nmnl";
		String functionName = "请求银程保存同步表";
 
		// 公共包路径 (例如 BaseDao、 BaseService、 BaseServiceImpl)
		String commonName ="com.adpanshi.cashloan.com.adpanshi.cashloan.api.core.common";
		
		String packageName ="com.adpanshi.com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.cl";
		String moduleName = "com.adpanshi.cashloan.api.com.adpanshi.cashloan.api.cr.domain";

		//Mapper文件存储地址  默认在resources中
		String batisName = "config/config.config.config.config.config.mappers";
		String db="mysql";
		
		//类名前缀
		String classNamePrefix = "clYinchengReqLog";//cl_yincheng_req_log

		try {
			MybatisGenerate.generateCode(db,url, MysqlUser, mysqlPassword, database, table,commonName,packageName,batisName,moduleName,classAuthor,functionName,classNamePrefix);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

}
