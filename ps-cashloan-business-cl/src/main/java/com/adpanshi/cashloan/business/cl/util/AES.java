package com.adpanshi.cashloan.business.cl.util;

import com.adpanshi.cashloan.business.core.common.util.RandomNumUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class AES {
 
    static final String algorithmStr = "AES/ECB/PKCS5Padding";
 
    @SuppressWarnings("unused")
	private static final Object TAG = "AES";
 
    static private KeyGenerator keyGen;
 
    static private Cipher cipher;
 
    static boolean isInited = false;
       
      private static  void init() {
        try { 
                /**为指定算法生成一个 KeyGenerator 对象。
                *此类提供（对称）密钥生成器的功能。
                *密钥生成器是使用此类的某个 getInstance 类方法构造的。
                *KeyGenerator 对象可重复使用，也就是说，在生成密钥后，
                *可以重复使用同一 KeyGenerator 对象来生成进一步的密钥。
                *生成密钥的方式有两种：与算法无关的方式，以及特定于算法的方式。
                *两者之间的惟一不同是对象的初始化：
                *与算法无关的初始化
                *所有密钥生成器都具有密钥长度 和随机源 的概念。
                *此 KeyGenerator 类中有一个 init 方法，它可采用这两个通用概念的参数。
                *还有一个只带 keysize 参数的 init 方法，
                *它使用具有最高优先级的提供程序的 SecureRandom 实现作为随机源
                *（如果安装的提供程序都不提供 SecureRandom 实现，则使用系统提供的随机源）。
                *此 KeyGenerator 类还提供一个只带随机源参数的 inti 方法。
                *因为调用上述与算法无关的 init 方法时未指定其他参数，
                *所以由提供程序决定如何处理将与每个密钥相关的特定于算法的参数（如果有）。
                *特定于算法的初始化
                *在已经存在特定于算法的参数集的情况下，
                *有两个具有 AlgorithmParameterSpec 参数的 init 方法。
                *其中一个方法还有一个 SecureRandom 参数，
                *而另一个方法将已安装的高优先级提供程序的 SecureRandom 实现用作随机源
                *（或者作为系统提供的随机源，如果安装的提供程序都不提供 SecureRandom 实现）。
                *如果客户端没有显式地初始化 KeyGenerator（通过调用 init 方法），
                *每个提供程序必须提供（和记录）默认初始化。
                */
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化此密钥生成器，使其具有确定的密钥长度。
        keyGen.init(128); //128位的AES加密
        try {    
                // 生成一个实现指定转换的 Cipher 对象。
            cipher = Cipher.getInstance(algorithmStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        //标识已经初始化过了的字段
        isInited = true;
    }
 
    @SuppressWarnings("unused")
	private static byte[] genKey() {
        if (!isInited) {
            init();  
        }
        //首先 生成一个密钥(SecretKey),
        //然后,通过这个秘钥,返回基本编码格式的密钥，如果此密钥不支持编码，则返回 null。 
        return keyGen.generateKey().getEncoded();
    }
 
    @SuppressWarnings("unused")
	private static byte[] encrypt(byte[] content, byte[] keyBytes) {
        byte[] encryptedText = null;
        if (!isInited) { 
            init();
        }
        /**
        *类 SecretKeySpec
        *可以使用此类来根据一个字节数组构造一个 SecretKey，
        *而无须通过一个（基于 provider 的）SecretKeyFactory。
        *此类仅对能表示为一个字节数组并且没有任何与之相关联的钥参数的原始密钥有用 
        *构造方法根据给定的字节数组构造一个密钥。
        *此构造方法不检查给定的字节数组是否指定了一个算法的密钥。
        */
        Key key = new SecretKeySpec(keyBytes, "AES");
        try {
                // 用密钥初始化此 cipher。
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
                //按单部分操作加密或解密数据，或者结束一个多部分操作。(不知道神马意思)
            encryptedText = cipher.doFinal(content);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return encryptedText;
    }
 
    private static byte[] encrypt(String content, String password) {
        try {
            byte[] keyStr = getKey(password);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher = Cipher.getInstance(algorithmStr);//algorithmStr          
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);//   ʼ  
            byte[] result = cipher.doFinal(byteContent);
            return result; //     
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
  
    private static byte[] decrypt(byte[] content, String password) {
        try {
            byte[] keyStr = getKey(password);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            System.out.println(key.getEncoded()+"=================");
            Cipher cipher = Cipher.getInstance(algorithmStr);//algorithmStr           
            cipher.init(Cipher.DECRYPT_MODE, key);//   ʼ  
            byte[] result = cipher.doFinal(content);
            return result; //     
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
     
    private static byte[] getKey(String password) {
        byte[] rByte = null;
        if (password!=null) {
            rByte = password.getBytes();
        }else{
            rByte = new byte[24];
        }
        return rByte;
    }
 
    /**
     * 将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
 
    /**
     * 将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null; 
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
     
        //注意: 这里的password(秘钥必须是16位的)
   // private static  String keyBytes = RandomNumUtils.createRandomNumAndChar(16); 
    /**
    *加密
    */
    public static String encode(String content,String keyBytes){
            //加密之后的字节数组,转成16进制的字符串形式输出
        return parseByte2HexStr(encrypt(content, keyBytes));
    }
     
    /**
    *解密
    */
    public static String decode(String content,String keyBytes){
            //解密之前,先将输入的字符串按照16进制转成二进制的字节数组,作为待解密的内容输入
        byte[] b = decrypt(parseHexStr2Byte(content), keyBytes);
        return new String(b);
    }
     
    //测试用例
    public static void test1(){
    			String keyBytes = RandomNumUtils.createRandomNumAndChar(16);
    			String test = "0AA824E5042429504AC8620F501FEB8BEF0E98E170F5"
    					+ "38679A9A3642DA3918B8FE8D9488E06192482D44BFEEBD0D"
    					+ "D2B5C5FD4A4F747F1CB0971AB8C0009AC891CA789EB19C03A"
    					+ "55D82BC425FEC168D5ACCE2ED825E1667B4BE777C8A93DA2D"
    					+ "34ED272F7E91FF22C37D0B3AA8AA2BB65D31F366D460CA549"
    					+ "D61A239331E2FB2A9%206DE414D98FBD24AD2A9DE76443C07"
    					+ "E19F306AFDEDC8A2C6ACA8B3B8E9889A3E368E1ADA287DAF7"
    					+ "C7C08351069C0B1080075DED24A269F4613080AD4E918AD7B"
    					+ "5A741088C8A4029AD701944F9A78DA5BA6EABD64D885F08E6"
    					+ "54234F5F882C7B260C1C72E7A678CF321110A739382C01A69"
    					+ "D86EDD8AD0B3E9D26F3AE3C99ECDCC3%202A335773E6DE006B"
    					+ "D1F923997E0775C4483D84CBE7E7FCEEBEF38E0D5C86D94FEB"
    					+ "2C86BE57C8F78BF4B87A7460F5C4AAF35AA6C07977A0A96E8F"
    					+ "AE3B6D1B0A3EDC0FB9E7A81026ECDFC18B3A4058B8C962AA14"
    					+ "2EE4B8E22B95382F97A0C75EDEDA96A493ED95977A4935D97C"
    					+ "4D3203ABFD565DDC47CD234E4E4C54E3A5D7339A%201DD8232"
    					+ "7AC5CE9182A29808FEE76EF21DA17F5E1A12045F3F8DA3BDBE"
    					+ "8F188011A29086EC9AE6825D9A0D6C2077B8438D659480A355"
    					+ "64E6BCBEB9FA57FA38A1B7BC70AF23C3CBA1C9FB852F7514F6D"
    					+ "57A757B986AA6BD2B4CFBE843000D67CE98B834AEF562FF700C"
    					+ "24494507E524A2440D641AB183D83B2DC880FE4851D7977%204"
    					+ "2547856C2D38391D3B940D51CC31EC56ED82C44B13D91AC9F5A4"
    					+ "F031A3BE0F7A0D09D1E09E15CB5639E3484235B21094052E94DC4"
    					+ "1FDF0F8581A279483371469A4A834C7310BA8F5643CBDDCA6425D"
    					+ "0B4FFA1DCA6CAD26ADDE4E5E45AB1C8B8DDF4F8E352FA35A487C6"
    					+ "515EAC6AF04A368BD7391C49C248AB64C000DFBF0061%2027E4E9"
    					+ "F225224545F45F6C840E943F36B11CD22EB82B8CFFA6C1C634B59C"
    					+ "B2342C77571CDD1AB411DB7C48C4610ABE82AB87F0A3E994F95E35"
    					+ "3514DEC5041BEA8EE3855FCE9A1C4D1F34ED98FD1316C73B0EF9DA"
    					+ "3419D0B23F887FA317456FDCCB5C4A6543CB35F34F20A4A1C4DACC"
    					+ "2AB45494D808EF2470F9900FE5A07F7A2E%208174ACDD5DB6C43A0"
    					+ "E2A9C0D8243CF8ED988E85F7DD1EF246B10FEF0D203A44287EBD317"
    					+ "9AE62756B0B10204D78654A368C472CAB1E1B9FE69CC7456EA52442"
    					+ "192AE010F277480790DDA5AA4A9C5200B47395B2275FD8DD5ED49F1"
    					+ "261C0E7876850B5EB508F758E11A55264BCD757637B0ED1D900B28B"
    					+ "6529258B535DEC3AAEB%2061BEAB121CCBD627762DEA231192152F0"
    					+ "1F197A9047EEAC1216B2240A0856604C7661A87CD9563073CC8C8E4"
    					+ "184465AD1EDBD465C4B2DA0B8F383623B2047B9A7C5DB859DB631E3"
    					+ "98C90D30375AA5BCDFED4221539875D33C4C5E7CF3186AB1F3F3FC7"
    					+ "5ED836C8C17A0C1EA90F5D9C6D6A0A3EAA967BC4214B5764586C75C"
    					+ "0B5%206158CF0644D67F9FD36681E2995D389560D13211102E20326"
    					+ "69DBB31EF22F86BE3C06DA969A8CC3B1308A895AEB5DEE1F54EDAA8"
    					+ "16DE7950A464F80AB7D52D2FD1BFD9F8F673D8BD6BFCC3E57B03DC1"
    					+ "3907A39AD92E50228DE8A377084176CF3C0D3EF41B46B4A08144DD3"
    					+ "593FB2013BE57D7D0FA993403D78358098472AE9E5%20673AE330DA"
    					+ "E1F274233943E92171BBE077D75BCB512E85FA88D898FFC716FA2C70"
    					+ "75D2B6650FE5D2165B47CA1D7C525D203F3AF39C162CEDE7E3806311E7C"
    					+ "A05C2B2EAF852850DE63181C320D4D0D4E717A0C24C10AF74A0968EB13ABFA7E354E1BBAA1C7DC85BCA74"
    					+ "15E0714F73707057B3B0F885A548142BBEEF04D2C4F274%204691DBF03904F48429D9E442478F2CBF8F639"
    					+ "67CC49D3D52A79FFE91B6E4A4E99C71FBE856EC66C26E000683CDE65844E726199CF18F6A5E6A5D564A4FF"
    					+ "A37982B9EC5DD2B9CF705B9405C9B8954ABCDC54466565C1356F68F4E0C471FFC427429B07750D6C98CA08"
    					+ "7E3D98B84C2A0EAC30252D49253A97D51115968FAE9832A";
    			long start = System.currentTimeMillis();
    			 String pStr = encode(test,keyBytes);
    			long end = System.currentTimeMillis();	
       
        System.out.println("加密前："+test);
        System.out.println("加密后:" + (end-start));
         
        long start1 = System.currentTimeMillis();
        String postStr = decode(pStr,keyBytes);
		long end1 = System.currentTimeMillis();	
       
        System.out.println("解密后："+ postStr+"------"+(end1-start1));
    }
     
    public static void main(String[] args) {
        test1();
    }
  
}