package ricard.projecte.gestiogimnasandroid;

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
/*
    //Mostra un missatge d'alerta que podrem personalitzar.
    public static void alerta(String frase) {
        Alert alert = new Alert(WARNING);
        alert.setTitle("Avís");
        alert.setHeaderText(null);
        alert.setContentText(frase);
        alert.showAndWait();
    }

    //Mostra un missatge de confirmacio que podrem personalitzar.
    public static void confirma(String frase) {
        Alert alert = new Alert(INFORMATION);
        alert.setTitle("Avís");
        alert.setHeaderText(null);
        alert.setContentText(frase);
        alert.showAndWait();
    }

    //Obte el llistat d'exercicis de la base de dades a partir del muscul que li introduim, despres els afegeix al array que nosaltres volguem passar-li.
    public static void obteExercicis(String muscul, ArrayList<Exercici> exercici) throws InterruptedException, ExecutionException {
        //Firestore db = FirestoreClient.getFirestore();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Exercici ex = null;
        String nom = "", descripcio = "", benefici = "", dificultat = "";
        ApiFuture<QuerySnapshot> query = db.collection("Exercicis").document(muscul).collection("Exercici").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            nom = document.getString("Nom");
            descripcio = document.getString("Descripcio");
            benefici = document.getString("Benefici");
            dificultat = document.getString("Dificultat");
            ex = new Exercici(nom, descripcio, benefici, dificultat);
            exercici.add(ex);
        }
    }

    public static void exercicisRutina(String nomRutina, ArrayList<Exercici> rutina, Client client) throws InterruptedException, ExecutionException {
        //Firestore db = FirestoreClient.getFirestore();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Exercici ex = null;
        String nom = "", descripcio = "", benefici = "", dificultat = "";
        ApiFuture<QuerySnapshot> query = db.collection("Clients").document(client.getNom()).collection("Rutines").document(nomRutina).collection("exercicis").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            nom = document.getString("Nom");
            descripcio = document.getString("Descripcio");
            benefici = document.getString("Benefici");
            dificultat = document.getString("Dificultat");
            ex = new Exercici(nom, descripcio, benefici, dificultat);
            rutina.add(ex);
        }
    }

    //Emmagatzema la rutina que hem creat a l'usuari corresponent amb el nom escollit dintre de la base de dades.
    public static void rutina(ArrayList<Exercici> llistaRutina, Client client, String nomRutina) throws InterruptedException, ExecutionException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Exercici exercici;
        for (int i = 0; i < llistaRutina.size(); i++) {
            exercici = llistaRutina.get(i);

            Map<String, Object> noms = new HashMap<>();
            noms.put("Nom", nomRutina);

            Map<String, Object> act = new HashMap<>();
            act.put("Benefici", exercici.getBenefici());
            act.put("Nom", exercici.getNom());
            act.put("Descripcio", exercici.getDescripcio());
            act.put("Dificultat", exercici.getDificultat());

            ApiFuture<WriteResult> res = db.collection("Clients").document(client.getNom()).collection("Rutines").document(nomRutina).create(noms);
            ApiFuture<WriteResult> result = db.collection("Clients").document(client.getNom()).collection("Rutines").document(nomRutina).collection("exercicis").document(exercici.getNom()).create(act);
            System.out.println("Update time : " + result.get().getUpdateTime());
        }
        Modelo.confirma("Rutina creada correctament!");
    }

    //Elimina la rutina seleccionada a l'usuari que esta loguejat.
    public static void eliminaRutina(Client client, ComboBox cb, String nomRutina) throws InterruptedException, ExecutionException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int numDocuments = 0, num = 0;
        CollectionReference ref = db.collection("Clients").document(client.getNom()).collection("Rutines").document().collection("exercicis");
        ApiFuture<WriteResult> writeResult2 = db.collection("Clients").document(client.getNom()).collection("Rutines").document(nomRutina).delete();
        Modelo.confirma("Rutina esborrada correctament!");
        System.out.println("Update time : " + writeResult2.get().getUpdateTime());
        volverEscenaAnterior();

    }
*/
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
    public static boolean comprobaContrasenya(String contrassenya, String repeticio) {
        boolean igual = true;
        if (contrassenya.length() >= 12) {
            if (!repeticio.equals(contrassenya)) {
                igual = false;
            }
        }
        return igual;
    }

    //Comprobem que el codi postal esta format per 5 numeros i on els dos primers son 0 i 8.
    public static boolean comprobaCodiPostal(String codiPostal) {
        return Pattern.compile("[0][8][0-9][0-9][0-9]").matcher(codiPostal).matches();
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
