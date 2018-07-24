package com.adpanshi.cashloan.business.cl.model.pay.lianlian.util;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ActiveSignUtil {

	private static Logger logger = Logger.getLogger(ActiveSignUtil.class);

	public static final String PARAM_EQUAL = "=";
	public static final String PARAM_AND = "&";
	public static final String SIGN_ALGORITHMS = "MD5WithRSA";

	private static ActiveSignUtil instance;

	public static ActiveSignUtil getInstance() {
		if (instance == null)
			return new ActiveSignUtil();
		return instance;
	}

	/**
	 * 对Object进行List<NameValuePair>转换后按key进行升序排序，以key=value&...形式返回*
	 * @param order
	 * @return
	 */
	public static String sortParam(Object order)
	{
		Map<String, String> map = bean2Parameters(order);
		logger.info(" 借款详情 : map PayOrder " + map);
		return sortParam(map);
	}

	/**
	 * 连连认证支付. 签名
	 * @param bean
	 * @date 20170718
	 * @return
	 */
	public static Map<String, String> bean2Parameters(Object bean)
	{
		if (bean == null)
		{
			return null;
		}

		Map<String, String> parameters = new HashMap<String, String>();
		if(null != parameters) {
			// 取得bean所有public 方法
			Method[] Methods = bean.getClass().getMethods();
			for (Method method : Methods)
			{
				if (method != null && method.getName().startsWith("get")
						&& !method.getName().startsWith("getClass"))
				{
					// 得到属性的类名
					String value = "";
					// 得到属性值
					try
					{
						String className = method.getReturnType().getSimpleName();
						if (className.equalsIgnoreCase("int"))
						{
							int val = 0;
							try
							{
								val = (Integer) method.invoke(bean);
							} catch (InvocationTargetException e)
							{
								logger.error("InvocationTargetException", e);
							}
							value = String.valueOf(val);
						} else if (className.equalsIgnoreCase("String"))
						{
							try
							{
								value = (String) method.invoke(bean);
							} catch (InvocationTargetException e)
							{
								logger.error("InvocationTargetException", e);
							}

						}
						if (value != null && value != "")
						{
							// 添加参数
							// 将方法名称转化为id，去除get，将方法首字母改为小写
							String param = method.getName().replaceFirst("get", "");
							if (param.length() > 0)
							{
								String first = String.valueOf(param.charAt(0)).toLowerCase();
								param = first + param.substring(1);
							}
							parameters.put(param, value);
						}
					} catch (IllegalArgumentException e)
					{
						logger.error("IllegalArgumentException", e);
					} catch (IllegalAccessException e)
					{
						logger.error("IllegalAccessException", e);
					}
				}
			}
		}
		return parameters;
	}

	/**
	 * 生成待签名串
	 * @date 20170718
	 * @param order
	 * @return
	 */
	public static String sortParam(Map<String, String> order)
	{
		if (null == order){
			return null;
		}
		Map<String, String> parameters = new TreeMap<String, String>(
				new Comparator<String>() {
					public int compare(String obj1, String obj2) {
						return obj1.compareToIgnoreCase(obj2);
					}
				});
		parameters.putAll(order);
		if(null != parameters) {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, String> entry : parameters.entrySet())
			{

				if (null != entry.getValue() && !"".equals(entry.getValue())
						&& !entry.getKey().equals("id_type")
						&& !entry.getKey().equals("id_no")
						&& !entry.getKey().equals("acct_name")
						&& !entry.getKey().equals("flag_modify")
						&& !entry.getKey().equals("user_id")
						&& !entry.getKey().equals("no_agree")
						&& !entry.getKey().equals("card_no")
						&& !entry.getKey().equals("test_mode"))
				{
					sb.append(entry.getKey());
					sb.append(PARAM_EQUAL);
					sb.append(entry.getValue());
					sb.append(PARAM_AND);
				}
			}

			String params = sb.toString();
			if (sb.toString().endsWith(PARAM_AND))
			{
				params = sb.substring(0, sb.length() - 1);
			}
			logger.info(" 连连认证支付: app 待签名串: " + params);
			return params;
		}

		return null;
	}


	public static String sign(String content, String privateKey) {
		String charset = "utf-8";
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					ActiveBase64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			Signature signature = Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(charset));

			byte[] signed = signature.sign();

			return ActiveBase64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String [] args){
		String content = "busi_partner=101001&dt_order=2017-07-18 13:44:33&money_order=0.01&name_goods=小额钱袋立即支付&no_order=1707181647286398&notify_url=http://com.adpanshi.com.adpanshi.cashloan.api.ngrok.bijianhuzhu.com:8000/com.adpanshi.com.adpanshi.cashloan.api/active/payment/asynNotify.htm&oid_partner=201704181001653368&risk_item={\"user_info_full_name\":\"王扬露\",\"user_info_identify_type\":\"1\",\"user_info_dt_register\":1499244264000,\"frms_ware_category\":\"2010\",\"user_info_identify_state\":\"1\",\"user_info_bind_phone\":\"15924180079\",\"user_info_id_no\":\"342423199405142726\",\"user_info_mercht_userno\":80522}&sign_type=RSA&valid_order=100";
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALRt20pu3oCzEH18/i2iEWc4G15f9ttDAXTKcrDcQCP5Jp9BlgU5k8UQLRBMEWe+aydw5x4cSOApVPF4UnCLsmYvfADrbgOAVVP1M+sgVg5Et3pMk0skHYo3jUIgaqXc6Fjn5JJVQVnvUUt+rtFpQc6WWY/M9FaHj2jgzDbA70nhAgMBAAECgYEAhsQzcnJDYY/eNk0BMaaHJzjiQGifYwC2eryoU+//PJ9huLxtSLPL6vp9Hloi+gFh2hDboELyL/TPTJlZwlSlx6n0s8cfPo4w+CeBj2LfrV2vjYAqKy3mAgSYz9cKbSbEKqSyqeJBztyvxdvtHMSz09XjExxlI1VBUphY4LY0qwECQQDkT70Fw3C/yVNzBbywa/eXc+XrMLD1T3DgzyFpEAjnGvog91bviyNq9pScs6oTqnstZJQLLMSbFh9mX/NP741RAkEAyk+Kg9QerR2gf7P3+PwqnadYkyckz9fE6Dak91U8aHenKY1nosvnu+KrJhkrKdi8gLCdhSpzImIu0vWnMVCPkQJAbiJA7ozO0NVPWgcEdJ5Ae2C/ImsEkfFWZDvGxCWmBcWvr0NhPoCB/1Efc0//1SjB7q279IAN/zn2v629c2v9YQJAVyLJMxxylfdzwG0AOeJKbJq+QKsUZNy3N5kJlmnE2e3nNrNr1qdiSP9I3eDrQVvTXM41Gjz9V+rX5g7eExRysQJAXxbp6uj5bECezN1H2rjAkA2b+e3BZloSk6hXjR1yYb3CnWRfo+DetQY5tDBb/OktwjU35+8T/qonSstkgIdhoA==";
		System.out.print(sign(content,privateKey));
	}
}
