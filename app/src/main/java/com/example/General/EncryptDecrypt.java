package com.example.General;

import android.provider.SyncStateContract;
import android.util.Base64;

import com.google.android.gms.common.internal.Constants;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {

    private static String cryptoPass = "25hui88fg";

    public static String encryptIt(String value) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] clearText = value.getBytes("UTF8");
            // Cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            String encrypedValue = Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT);
            //  Log.d(TAG, "Encrypted: " + value + " -> " + encrypedValue);
            return encrypedValue;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String decryptIt(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
       String ALGO = "DESede/ECB/PKCS7Padding";
        byte[] plainText = new byte[0];
        try {
   /*         DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encrypedPwdBytes = Base64.decode(value, Base64.DEFAULT);
            // cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypedValueBytes = (cipher.doFinal(encrypedPwdBytes));

            String decrypedValue = new String(decrypedValueBytes);
            //   Log.d(TAG, "Decrypted: " + value + " -> " + decrypedValue);
            return decrypedValue;*/

            byte[] message = Base64.decode(value.getBytes(), Base64.DEFAULT);

            Cipher decipher = Cipher.getInstance(ALGO);
            decipher.init(Cipher.DECRYPT_MODE, getSecreteKey("25hui88fg"));

          plainText = decipher.doFinal(message);

            return new String(plainText, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();



        }


        return new String(plainText, "UTF-8");
    }
    public static SecretKey getSecreteKey(String secretKey) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        return key;
    }
}
