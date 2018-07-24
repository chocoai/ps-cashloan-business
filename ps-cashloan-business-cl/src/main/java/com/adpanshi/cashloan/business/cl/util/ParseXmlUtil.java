package com.adpanshi.cashloan.business.cl.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseXmlUtil {
	    public static Map<String,Object>  xmlElements(String xmlDoc) {
	        //创建一个新的字符串
	        StringReader read = new StringReader(xmlDoc);
	        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
	        InputSource source = new InputSource(read);
	        //创建一个新的SAXBuilder
	        SAXBuilder sb = new SAXBuilder();
	        try {
	            //通过输入源构造一个Document
	            Document doc = sb.build(source);
	            //取的根元素
	            Element root = doc.getRootElement();
	            Map<String,Object> result = new HashMap<String,Object>();
	            if(root.getChild("is_success").getValue().equals("T")){
	            	Element tradeNode = root.getChild("response").getChild("trade");
	            	@SuppressWarnings("rawtypes")
					List responseTradeList = tradeNode.getChildren();
	            	for(int i=0;i<responseTradeList.size();i++){
	 	               Element et = (Element) responseTradeList.get(i);//循环依次得到子元素
	 	               result.put(et.getName(), et.getValue());
	 	            }
	            }else{
	            	Element e = root.getChild("error");
	            	result.put(e.getName(), e.getValue());
	            }
	            return result;
	        } catch (JDOMException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO 自动生成 catch 块
	            e.printStackTrace();
	        }
	        return null;
	    }
	    public static void main(String[] args){
	        String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
	        "<Result xmlns=\"http://www.fiorano.com/fesb/activity/DBQueryOnInput2/Out\">"+
	           "<row resultcount=\"1\">"+
	              "<users_id>1001     </users_id>"+
	              "<users_name>wangwei   </users_name>"+
	              "<users_group>80        </users_group>"+
	              "<users_address>1001号   </users_address>"+
	           "</row>"+
	           "<row resultcount=\"1\">"+
	              "<users_id>1002     </users_id>"+
	              "<users_name>wangwei   </users_name>"+
	              "<users_group>80        </users_group>"+
	              "<users_address>1002号   </users_address>"+
	           "</row>"+
	        "</Result>";
	        ParseXmlUtil.xmlElements(xml);
	        System.out.println(xml+"------");
	    }
}
