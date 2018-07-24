package com.adpanshi.cashloan.business.cl.util;

import com.adpanshi.cashloan.business.core.common.util.RandomNumUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**  
 * 常用加密算法工具类  
 * @author cq  
 */    
@SuppressWarnings("restriction")
public class EncryptUtils {    
        
    /**  
     * 用MD5算法进行加密  
     * @param str 需要加密的字符串  
     * @return MD5加密后的结果  
     */    
    public static String encodeMD5String(String str) {    
        return encode(str, "MD5");    
    }
    
    /**  
     * 用MD5算法进行加密  加盐 
     * @param str 需要加密的字符串  salt 加盐
     * @return MD5加密后的结果  
     */    
    public static String encodeMD5String(String str,String salt) {    
        return encode(str+salt, "MD5");    
    }   
    
    /**  
     * 用SHA算法进行加密  
     * @param str 需要加密的字符串  
     * @return SHA加密后的结果  
     */    
    public static String encodeSHAString(String str) {    
        return encode(str, "SHA");    
    }    
    
    /**  
     * 用base64算法进行加密  
     * @param str 需要加密的字符串  
     * @return base64加密后的结果  
     */    
    public static String encodeBase64String(String str) {    
        BASE64Encoder encoder =  new BASE64Encoder();    
        return encoder.encode(str.getBytes());    
    }    
        
    /**  
     * 用base64算法进行解密  
     * @param str 需要解密的字符串  
     * @return base64解密后的结果  
     * @throws IOException   
     */    
    public static String decodeBase64String(String str) throws IOException {    
        BASE64Decoder encoder =  new BASE64Decoder();    
        return new String(encoder.decodeBuffer(str));    
    }    
        
    private static String encode(String str, String method) {    
        MessageDigest md = null;    
        String dstr = null;    
        try {    
            md = MessageDigest.getInstance(method);    
            md.update(str.getBytes());    
            dstr = new BigInteger(1, md.digest()).toString(16);    
        } catch (NoSuchAlgorithmException e) {    
            e.printStackTrace();    
        }    
        return dstr;    
    }   
    
  //rsa公钥加密
  	public static String rsaEncryptByPubKey(String content,String publicKey){
  		  	RSA rsa = RSA.create();
  	        String enStr = rsa.encodeByPublicKey(content, publicKey);
  	        return enStr;
  	}
  	//rsa私钥解密
  	public static String rsaDecryptByPriKey(String content,String privateKey){
  		RSA rsa = RSA.create();
  		String deStr = rsa.decodeByPrivateKey(content, privateKey);
  		return deStr;
  	}
  	//rsa私钥加密
  	public static String rsaEncryptByPriKey(String content,String privateKey){
  		RSA rsa = RSA.create();
  		String deStr = rsa.encodeByPrivateKey(content, privateKey);
  		return deStr;
  	}
  	//rsa公钥解密
  	public static String rsaDecryptByPubKey(String content,String publicKey){
  		RSA rsa = RSA.create();
  		String deStr = rsa.decodeByPublicKey(content, publicKey);
  		return deStr;
  	}
  	//aes加密
  	public static String aesEncrypt(String content,String keyBytes){
  	        String pStr = AES.encode(content,keyBytes);
  	        return pStr;
  	}
  	//aes解密
  	public static String aesDecrypt(String content,String keyBytes){
  		return AES.decode(content,keyBytes);
  	}
  	//rsa秘钥交换
  	public static void changeKey(){
  		
  	}
  	//生成固定位数的aes秘钥
  	public static String createAesKey(int count){
  		return RandomNumUtils.createRandomNumAndChar(count);
  	}
        
    public static void main(String[] args) throws IOException {   
    	System.out.println(encodeMD5String("1","1"));
        /*String user = "oneadmin";    
        System.out.println("原始字符串 " + user);    
        System.out.println("MD5加密 " + encodeMD5String(user));    
        System.out.println("SHA加密 " + encodeSHAString(user));    
        String base64Str = encodeBase64String(user);    
        System.out.println("Base64加密 " + base64Str);    
        System.out.println("Base64解密 " + decodeBase64String(base64Str));    
        System.out.println(encodeMD5String("1","ce06f"));*/
    	/*String rsaDncode = EncryptUtils.rsaDecryptByPubKey("713C4E2135836A137BE5864091AAB2CB9A43E795732AE46A699EA7D92862E52E62D16C2AB4FCFF3F50628303516C5BD472577DC885494360355A00EE3B42F1C05A94D5F9197C178B0533B16E62143482B351F826C97B012C4168A801F2EC97EDECAA836019295C07BC3779729E93CD8DE41DA04F76FAD7D0C37AD09FA8C4DF83",
    			"30819F300D06092A864886F70D010101050003818D0030818902818100A76BD44B28715C2BF4498470CE12A16E4F5252347D4F5F4A906BF7F778D1AC736056FE058EBC197CDD9D9A0855DE27E9CA0FC6F9CDA205B6B0F48B63EDAA42AD72F7F129B28F97A4531F01047962BA4E7EA3B4B274FC52F217D8127CE20CCA1E6985F1BA2F04737073DC2E395475E1AB0EAFCDB5146FC68BDF0317F3486D4FFF0203010001");
    	*///String rsaDecode = EncryptUtils.rsaDecode("1C1627565E97D860918CE2B6B92E7703D5C5448D58A17208089F8A0E0DE85BB5FBAC44F8E825933535BB3FF48133EE2C708A468B9BD2A769872334595C74DD65CE93BC4BD8BB2E84D921CEB1AF2C4C8516E085ED29F1F5B5229236B696EB3D3C495626844722AB0593D1E9EC858616F22A20A0F6A8EBA4146644DDE445354F46", "30820275020100300D06092A864886F70D01010105000482025F3082025B02010002818100B4733681F07608FDFAA7829B62A25E7EB5B0D708238C9CD222376405BBCDA73EB005011B4B047EC056042B107CF152CFD262EC462F8F4390291129EE15A05B0B646BAAA1158EBB3F89B8E01DF275910F417B55F5FD8E56B864208DAF78D18FFE55E924B78ED03076C87EE86A7529DDA69E38C366E85C3D12739B348F077A636702030100010281800FEE640F90C53B0201512CACBF468A8531514D8AC068B7A7ADB1AA38FF5DA6B8581DF8FD09E079B84D5FCCD585D260774CA1C32E672084DC8363AA8A048AC5BED36783FCC5F16FE186D1B6AD87313E631941D621571566FDED3047DD5A6D5FECACE000C7B7D61940CE82ED80649BD19695B65C0BE8B7135C143E54866626A801024100F7A9F47286CAA8246164F5EB6217A565C2EECD02F2AB868AEB25D92C8E0ADB560915DB1FBFA02A62C8C691CA528B1AAC53AA4832D9A4E5461471C1569E122601024100BA8618BF1064C77E52BF73A7BACEC3625D52F496C865C635406CE5FE422BEC0CB34420752EC3AA18F6F8AB6DB0FD866EDCE0583CF612928115B29321FE771967024039445143D22B362A0363470200929B2A26A2F540C3402A42211185602A10149B964A397AE3E2732A986661FBB88E75C2772E1EB7B35FCFAC1EBA170CA24C6C0102401A2AB9CA0418BF4BDF7BF053B587502DBA7DEAC64472D2ADA1CE252DD846A524DAF951BC5EC52055C43FFA7CB6F9BA244F6441BCBBA0BB60D4946AC3B819293D02404509687638B3992E372BD45C9449D4F750E78C10EA925C3F39DDD989293B062A4DF277CF64BFC67FB80981B594C3D0613DB8441E2CC347D006A74A0875A63421");
    	//String result = StringUtil.decode(rsaDecode);
    	System.out.println(encodeMD5String("123","85dec"));
    	//System.out.println(rsaDncode+"-----");
    }    
}    