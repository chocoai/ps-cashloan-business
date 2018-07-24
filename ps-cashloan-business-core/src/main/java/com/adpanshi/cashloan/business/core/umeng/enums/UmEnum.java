package com.adpanshi.cashloan.business.core.umeng.enums;
/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月30日下午4:10:35
 **/
public class UmEnum {
	
	public static final String APP_PACKAGE="com.adpanshi.com.adpanshi.cashloan";//"app的包名"
	
	/**友盟主机地址*/
	public static final String UM_HOST_CODE="um_host";
	/**发送接口*/
	public static final String SEND_PATH_CODE="um_sendPath";
	
//	public static final String UPLOAD_PATH="/upload";//上传接口;
	
	public enum AndroidAppKey{
		/**应用唯一标识。友盟消息推送服务提供的appkey和友盟统计分析平台使用的同一套appkey*/
		KEY_CODE("um_android_appKey"),
		/**唯一标识*/
		MESSAGE_SECRET_CODE("um_android_messageSecret"),
		/**服务器密钥*/
		MASTER_SECRET_CODE("um_android_masterSecret");
		
		private String code;
		
		private  AndroidAppKey(String code){
			this.code=code;
		}
		public String getCode() {
			return code;
		}

	}

	public enum IOSAppKey{
		/**应用唯一标识。友盟消息推送服务提供的appkey和友盟统计分析平台使用的同一套appkey*/
		KEY_CODE("um_ios_appKey"),
		/**服务器密钥*/
		MASTER_SECRET_CODE("um_ios_masterSecret");
		
		private String code;
		
		private  IOSAppKey(String code){
			this.code=code;
		}

		public String getCode() {
			return code;
		}

	}
	
	public enum Type{
		/**
		 * 消息发送类型,其值可以为:
              unicast-单播
              listcast-列播(要求不超过500个device_token)
              filecast-文件播 (多个device_token可通过文件形式批量发送）
              broadcast-广播
              groupcast-组播(按照filter条件筛选特定用户群, 具体请参照filter参数)
              customizedcast(通过开发者自有的alias进行推送)包括两种case(alias:对单个或者多个alias进行推送,file_id:将alias存放到文件后,根据file_id来推送)
		 * **/
		
		/**单播*/
		UNICAST{public String getValue() {return "unicast";}},
		/**列*/
		LISTCAST{public String getValue() {return "listcast";}},
		/**文件播*/
		FILECAST{public String getValue() {return "filecast";}},
		/**广播*/
		BROADCAST{public String getValue() {return "broadcast";}},
		/**组播*/
		groupcast{public String getValue() {return "groupcast";}},
		/**通过开发者自有的alias进行推送*/
		customizedcast{public String getValue() {return "customizedcast";}};
		
		public abstract String getValue();
		
	}
	
	
	public enum DisplayType{
		
		/***消息类型值可以为:
		 * 		通知:notification
		 * 		消息:message
		 * */
		
		/**通知(消息送达到用户设备后，由友盟SDK接管处理并在通知栏上显示通知内容)*/
		NOTIFICATION{public String getValue(){return "notification";}},
		
		/**消息(消息送达到用户设备后，消息内容透传给应用自身进行解析处理)*/
		MESSAGE{public String getValue(){return "message";}};
		
		public abstract String getValue();
	}
	
	public enum AfterOpen{
		/**点击"通知"的后续行为，默认为打开app。
		 * after_open 值可以为:
               "go_app": 打开应用
               "go_url": 跳转到URL
               "go_activity": 打开特定的activity
               "go_custom": 用户自定义内容。
		 * */
		
		/**打开应用*/
		GO_APP{public String getValue() {return "go_app";}},
		/**跳转到URL*/
		GO_URL{public String getValue() {return "go_url";}},
		/**打开特定的activity*/
		GO_ACTIVITY{public String getValue() {return "go_activity";}},
		/**用户自定义内容*/
		GO_CUSTOM{public String getValue() {return "go_custom";}};
		
		public abstract String getValue();
	}
	
	public static void main(String[] args){
	}
}
