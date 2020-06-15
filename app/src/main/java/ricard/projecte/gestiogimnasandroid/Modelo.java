package ricard.projecte.gestiogimnasandroid;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.bluetooth.BluetoothClass.Service.INFORMATION;
import static java.util.logging.Level.WARNING;
import static java.util.regex.Pattern.compile;

public class Modelo {

    //Comprobem que el nom nomes el formen lletres.
    public static boolean comprobaNom(String nom) {
        return Pattern.compile("[a-zA-Z]+").matcher(nom).matches();

    }

    //Comprobem que el cognom nomes el formen lletres.
    public static boolean comprobaCognom(String cognoms) {
        return Pattern.compile("[a-zA-Z]+[ ][a-zA-Z]+").matcher(cognoms).matches();
    }

    public static boolean comprobaDni(String dni) {
        return Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][A-Z]").matcher(dni).matches();

    }

    //Comprobem que el telefon estigui format per 9 numeros on el primer ha de estar entre 6 i 9.
    public static boolean comprobaTelefon(String telefon) {
        return Pattern.compile("[6-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]").matcher(telefon).matches();
    }

    //Comprobem que la contrassenya i la seva repeticio son iguals.
    public static boolean comprobaContrasenya(String contrasenya, String repeticio) {
        boolean igual = true;
        if (contrasenya.length() >= 1) {
            if (!repeticio.equals(contrasenya)) {
                igual = false;
            }
        }
        return igual;
    }

    //Establim les propietats de la finestra fent-la inmersiva,
    public static void amagaBarraNavegacio(Window w){
        int uiOptions = w.getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            //Log.i(Constants.TAG_DEF, "Turning immersive mode mode off. ");
        } else {
            //Log.i(Constants.TAG_DEF, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        w.getDecorView().setSystemUiVisibility(newUiOptions);
    }

    //Comprobem que el codi postal esta format per 5 numeros i on els dos primers son 0 i 8.
    public static boolean comprobaCodiPostal(String codiPostal) {
        int codip=0;
        codip = Integer.parseInt(codiPostal);
        if(codip>=01000 && codip<=52999){
            return true;
        }else{
            return false;
        }
    }

    //Comprobem que la poblacio nomes la formen lletres.
    public static boolean comprobaPoblacio(String poblacio) { //Nom del poble amb el nom mes llarg d'Espanya: "Colinas del Campo de Martín Moro Toledano".
        return Pattern.compile("[a-zA-Z]+").matcher(poblacio).matches();

    }

    //Comprobem el numero de compte IBAN.
    public static boolean comprobaCompteBancari(String compteBancari) {
        return Pattern.compile("[E][S][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]").matcher(compteBancari).matches();

    }
}
