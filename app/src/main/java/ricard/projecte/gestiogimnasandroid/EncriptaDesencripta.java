package ricard.projecte.gestiogimnasandroid;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncriptaDesencripta {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String Encriptar(String texto) throws UnsupportedEncodingException {

        return Base64.getEncoder().encodeToString(texto.getBytes("utf-8")); //devuelve un string codificado.
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String Desencriptar(String textoEncriptado) throws Exception {
        byte[] decode = Base64.getDecoder().decode(textoEncriptado.getBytes()); //decodifica el string codificado y lo devuelve.
        return new String(decode, "utf-8");
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
