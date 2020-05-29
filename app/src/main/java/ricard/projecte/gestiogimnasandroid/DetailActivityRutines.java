package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetailActivityRutines extends AppCompatActivity {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Client c;
    Exercici e;
    public ListView exercicis;
    TextView nomMuscul;
    Button CancelarRutina, afegirExercici, esborrarExercici;
    public ArrayList<Exercici> llistaExercicisMuscul;
    public ArrayList<String> exercicisLlista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rutines);
        exercicis = findViewById(R.id.LlistaEx);
        nomMuscul = findViewById(R.id.LbNomMuscul);
        CancelarRutina = findViewById(R.id.BtCancelarRutines);
        afegirExercici = findViewById(R.id.BtAfegirExercici);
        esborrarExercici = findViewById(R.id.BtEsborraExercici);

        e = null;
        exercicisLlista = new ArrayList<>();
        llistaExercicisMuscul = new ArrayList<>();
        rebreDades();


    }

    public void rebreDades(){
        String nom="";
        Intent in = getIntent();
        c  = (Client) in.getSerializableExtra("Client");
        nom = in.getStringExtra("NomMuscul");
        obteExercicisMuscul(nom);
        nomMuscul.setText(nom);
    }
    public void obteExercicisMuscul(String nomMuscul){
        Task<QuerySnapshot> querySnapshotTask = db.collection("Exercicis").document(nomMuscul).collection("Exercici").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String nom = "", benefici="",descripcio="",dificultat="";

                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    nom = document.getString("Nom");


                    descripcio = document.getString("Descripcio");
                    benefici = document.getString("Benefici");
                    dificultat = document.getString("Dificultat");
                     e = new Exercici(nom, descripcio, benefici, dificultat);
                    Log.d("exercici obtingut de bbdd",e.getNom()); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
                    llistaExercicisMuscul.add(e);
                    exercicisLlista.add(e.getNom());
                    Log.d("exerciciLlista conte",e.getNom()); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
                    ArrayAdapter adaptador = new ArrayAdapter(DetailActivityRutines.this,android.R.layout.simple_list_item_1,exercicisLlista);
                    exercicis.setAdapter(adaptador);
                }
            }
        });
    }

    /*public void mostrarExercicis(){
        for(int i = 0; i <llistaExercicisMuscul.size();i++){
            exercicisLlista.add(llistaExercicisMuscul.get(i).getNom());
            Log.d("exerciciLlista conte",llistaExercicisMuscul.get(i).getNom()); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
        }
    }*/

}
