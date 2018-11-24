package com.alessoft.LoginManager.Utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class AES {
    private static final byte[] theKey = "¯¯\\(°_o)//¯-i dont_know_what".getBytes(StandardCharsets.UTF_8);

    public static String encode(String text) {
        try {
            Key aesKey = new SecretKeySpec(theKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
   
    public static String decode(String text) {
        try {
            Key aesKey = new SecretKeySpec(theKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decode = decoder.decode(text);

            byte[] decryptedBytes = cipher.doFinal(decode);
            return new String(decryptedBytes, "UTF8");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return "";
    }

    public static boolean matches(String check, String encoded) {
        return encoded.equals(encode(check));
    }
}
