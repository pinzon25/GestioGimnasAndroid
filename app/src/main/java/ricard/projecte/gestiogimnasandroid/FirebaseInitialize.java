package ricard.projecte.gestiogimnasandroid;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.google.auth.oauth2.GoogleCredentials.fromStream;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

public class FirebaseInitialize {

    private FirebaseOptions options;
    //private FirebaseApp fire;

    public FirebaseInitialize() throws IOException {
        initFirebase();
    }

    //Metode que s'executa abans que cualsevol altre metode o classe degut a la anotacio PostConstruct.
    //Establim el arxiu Json on esta la clau d'acces i la seva inicialitzacio.

    public void initFirebase() throws IOException {
        FileInputStream refreshToken = null;
        try {
            refreshToken = new FileInputStream("google-services.json");
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://gestiogimnas.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options,"gestiogimnas");
        } catch (FileNotFoundException ex) {
            getLogger(FirebaseInitialize.class.getName()).log(SEVERE, null, ex);
        } catch (IOException ex) {
            getLogger(FirebaseInitialize.class.getName()).log(SEVERE, null, ex);
        }

    }

}
