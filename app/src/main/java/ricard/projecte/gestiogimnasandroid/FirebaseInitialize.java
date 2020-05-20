package ricard.projecte.gestiogimnasandroid;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

public class FirebaseInitialize {

   /* private FirebaseOptions options;
    private FirebaseApp fire;

    public FirebaseInitialize() throws IOException {
        initFirebase();
    }

    //Metode que s'executa abans que cualsevol altre metode o classe degut a la anotacio PostConstruct.
    //Establim el arxiu Json on esta la clau d'acces i la seva inicialitzacio.
    @PostConstruct
    public void initFirebase() throws IOException {
        FileInputStream refreshToken = null;
        try {
            refreshToken = new FileInputStream("Credentials/gestiogimnas-key.json");
            options = new FirebaseOptions.Builder()
                    .setCredentials(fromStream(refreshToken))
                    .setDatabaseUrl("https://gestiogimnas.firebaseio.com")
                    .build();
            fire = FirebaseApp.initializeApp(options);

        } catch (FileNotFoundException ex) {
            getLogger(FirebaseInitialize.class.getName()).log(SEVERE, null, ex);
        } catch (IOException ex) {
            getLogger(FirebaseInitialize.class.getName()).log(SEVERE, null, ex);
        }
    }

    public FirebaseApp AppPrincipal() {
        return fire;
    }*/
}
