package com.els.myapplication.utils;

import com.els.myapplication.Constant;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class RsaEncryptUtil {
    public static final String KEY_ALGORITHM = "RSA";

    public static final String PADDING = "RSA/NONE/PKCS1Padding";

    public static final String PROVIDER = "BC";

    private static final int MAX_ENCRYPT_BLOCK = 117;

    private static final int MAX_DECRYPT_BLOCK = 128;

    public static String encryptByPublicKey(String str) throws Exception {

        Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);
        Key publicKey = getPublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = str.getBytes("UTF-8");
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();

        return Base64Util.encode(encryptedData);
    }

    public static String decryptByPublicKey(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);
        Key publicKey = getPublicKey();
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] encryptedData = Base64Util.decode(str);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher
                        .doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "UTF-8");
    }

    private static Key getPublicKey() throws Exception {
        String key = Constant.PUBLIC_KEY;
        byte[] keyBytes;
        keyBytes = Base64Util.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

}
