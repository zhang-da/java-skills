package com.da.learn.utils.codec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * DES 对称加密算法
 */
public class DESUtil {
    private static final String SECRET_KEY_TYPE = "DES";
    private static final String ECB_MOB = "DES/ECB/PKCS5Padding";
    private static final String CHARSET_NAME = "UTF-8";

    private static Key getKey(String password) throws Exception{
        byte[] DESKey = password.getBytes(CHARSET_NAME);
        DESKeySpec keySpec = new DESKeySpec(DESKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_KEY_TYPE);
        return keyFactory.generateSecret(keySpec);
    }

    public static String encode(String data, String password) throws Exception {
        Cipher enCipher = Cipher.getInstance(ECB_MOB);
        Key key = getKey(password);
        enCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] pasByte = enCipher.doFinal(data.getBytes(CHARSET_NAME));
        return Base64.getEncoder().encodeToString(pasByte);
    }

    public static String decode(String data, String password) throws Exception {
        Cipher deCipher = Cipher.getInstance(ECB_MOB);
        Key key = getKey(password);
        deCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] pasByte = deCipher.doFinal(Base64.getDecoder().decode(data.getBytes(CHARSET_NAME)));
        return new String(pasByte, CHARSET_NAME);
    }
}
