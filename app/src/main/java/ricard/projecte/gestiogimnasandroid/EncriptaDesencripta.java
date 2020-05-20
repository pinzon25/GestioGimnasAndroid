package ricard.projecte.gestiogimnasandroid;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
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
}
