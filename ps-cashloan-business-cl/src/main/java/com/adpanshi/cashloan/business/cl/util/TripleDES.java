package com.adpanshi.cashloan.business.cl.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

/**
 * 三重加密 3DES也作 Triple DES,
 *
 * @author stone
 * @date 2014-03-10 02:14:37
 */
public class TripleDES {
    // 算法名称
    private static final String KEY_ALGORITHM = "DESede";
    // 算法名称/加密模式/填充方式
    private static final String CIPHER_ALGORITHM_CBC = "DESede/CBC/PKCS5Padding";
    // DESedeKeySpec
    private static final String DESEDE_KEY_SPEC = "IB%Zy_CPMU3h5a4shi)tflQN&VEi9=K6M^]MHgy.'%tW>g^dCofY'm.8{/G1D1r+" +
            "(oqN)S7)'=hOi}f`)y[>8`vESypK2jP+e+f5]3JAD_^x%G~Y1%}>d;";
    //IV
    private static final String IV = "%r$ANRv?";


    public static void main(String[] args) throws Exception {
        String st = "这是一个加密测试abdcd";
//        String en = encrypt(st);
        String en = "oKVi0NQXKqh6/HL0OFulBALFXyKlaBdKEjHWIHSPIX3EwbxU+RzyhKnEraVf" +
                "+jKE4VEmTA4JoQa1uG04M6EY6jm9eJ8oUL6qs2gq/UIwCIBe6/dEY3UkcqcRrgo3IWegp4T0bkJ3VapoA5m0gNZkrwfwJ7LhuNjrENad279Ec8vdynrFuw6Az9AODovVrnHWbtXYz9ru7g+VOUp9530LBp35Vcw6trfTm3mUx0sti96oJ+I7vK9NvYsWYaUF3IOi0nMJS7HRU4uW/iTGhfNYfNV47bHApMsUlxZIH+pvisMIXzdQDpbk2OIxcmijut7Evo9ej8JXH7p1QIDVOqJTDeJDXOUwtQvUokF8CT+Yj0UigCJQdEcoAnwACxEeCDPQzFrzlL8w5uwy3PXdcSlDd8I5lXXuyN5wZmymqBvBO2dJaL3hExm0fHMfD8c6mTWAz7eXez9U6Z0s8hvd06A80PgSfv+W3FIOTUs2VmdkMsudlun/ABNb7Nbp9GJ+JM03rYV2r7ituQfqsA/p24Pgh7siAuHC7tejUClLJ7pH0RLeuPvKekYjW/btsxOsYmNI7ULxOYtG3f+IUnN+fYF57IR45XJsmNAMaRN/5xPftB3ZOqFRVkH9sNxpBASr4N26q5lm7tcRLppQNt44wT7jDUDtZCFLtLnH6JeD/DUpYE2a6aP3NVnrC7PCA21VZs7u7F5K50Gprk9k+5xvG1fOhJfDSomZ+91VgFnUky2cnmnEslQH155lpQ==";
        System.out.println("加密后：" + en);
        String de = decrypt(en);
        System.out.println("解密后：" + de);
    }

    /**
     * 加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String encrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encryptData = cipher.doFinal(str.getBytes());
        return new String(Base64.encodeBase64(encryptData), "UTF-8");
    }

    /**
     * 解密
     *
     * @param encrypt
     * @return
     * @throws Exception
     */
    public static String decrypt(String encrypt) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        byte[] decodeData = Base64.decodeBase64(encrypt.getBytes());
        return new String(cipher.doFinal(decodeData), "UTF-8");
    }

    private static Cipher getCipher(int model) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        DESedeKeySpec spec = new DESedeKeySpec(DESEDE_KEY_SPEC.getBytes());
        SecretKey secretKey = SecretKeyFactory.getInstance(KEY_ALGORITHM).generateSecret(spec);
        cipher.init(model, secretKey, new IvParameterSpec(IV.getBytes()));
        return cipher;
    }
}
