package ricard.projecte.gestiogimnasandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivityRutines extends AppCompatActivity {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Client c;
    public Exercici e;
    public ListView exercicis;
    TextView nomMuscul;
    public EditText Nrutina;
    public Button CancelarRutina, afegirExercici, esborrarExercici;
    public ArrayList<Exercici> llistaExercicisMuscul;
    public ArrayList<String> exercicisLlista;
    public String nomRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rutines);
        exercicis = findViewById(R.id.LlistaEx);
        nomMuscul = findViewById(R.id.LbNomMuscul);
        Nrutina = findViewById(R.id.TfNomRutina);
        CancelarRutina = findViewById(R.id.BtCancelarRutines);
        afegirExercici = findViewById(R.id.BtAfegirExercici);
        esborrarExercici = findViewById(R.id.BtEsborraExercici);
        nomRutina="";

        e = null;
        exercicisLlista = new ArrayList<>();
        llistaExercicisMuscul = new ArrayList<>();
        rebreDades();


        exercicis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    for(int j = 0; j <llistaExercicisMuscul.size();j++ ) {
                        if (adapterView.getItemAtPosition(i).toString().equals(llistaExercicisMuscul.get(i).getNom())) {

                            /*if(nomRutina.isEmpty()) {
                                Toast.makeText(DetailActivityRutines.this, "No has introduit un nom per a la rutina.", Toast.LENGTH_SHORT).show();
                            }else {*/
                                //nomRutina = Nrutina.getText().toString();
                                Log.d("Nom de la rutina introduit", nomRutina);
                                e = llistaExercicisMuscul.get(i);
                                Log.d("exercici clicat", e.getNom());   //Verifica correctament ques'hagi introduit un nom de rutina i el exercici clicat.
                                break;
                            //}
                        }
                    }
               // obteRutina(); //Dintre del onItemClick si que executa el  metode obteRutina().

            }
        });

        //obteRutina();//Al posarlo al OnCreate peta.

    }



    public void obteRutina(View view) {
        //Exercici ex = DetailActivityRutines.this.e;//Funciona be.
        //Log.d("obteRutina()-->Nom del exercici ex", ex.getNom());
        Log.d("obteRutina()-->Nom del exercici e", e.getNom());//Aqui tambe obte el exercici global.
        nomRutina = Nrutina.getText().toString();
    if(nomRutina.equals("")){
        Toast.makeText(DetailActivityRutines.this, "No has introduit un nom per a la rutina.", Toast.LENGTH_SHORT).show();
    }else {

        Map<String, Object> exN = new HashMap<>();
        exN.put("Nom", e.getNom());

        Map<String, Object> actv = new HashMap<>();
        actv.put("Benefici", e.getBenefici());
        actv.put("Nom", e.getNom());
        actv.put("Descripcio", e.getDescripcio());
        actv.put("Dificultat", e.getDificultat());


        db.collection("Clients").document(c.getNom()).collection("Rutines").document(nomRutina).collection("exercicis").document(e.getNom()).set(actv).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(DetailActivityRutines.this, "Dades guardades", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailActivityRutines.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT).show();
            }
        });


        //FUNCIONA
        /*db.collection("Clients").document(c.getNom()).collection("Rutines").document(nomRutina).set(actv).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(DetailActivityRutines.this, "Dades guardades", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailActivityRutines.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT).show();
            }
        });*/

        /*DocumentReference docRef = (DocumentReference) db.collection("Clients").document(client.getNom());
        docRef.update("Cuota", FieldValue.increment(totalsuplement));

        insAct.add(act);*/
    }
    }

    /*
    //Agafem tots els documents de la coleccio "Rutines".
        Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").document(c.getNom()).collection("Rutines").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String nom = "";
                //Exercici ex = DetailActivityRutines.this.e; //Aqui hem pogut obtenir la variable global d'exercici e.

                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                Log.d("obteRutina()-->1er querysnapshot.", "Correcte");

                for (DocumentSnapshot document : documents) {//Repassem tots els documents de la coleccio.
                    //Log.d("obteRutina()-->Nom de la rutina introduit", nomRutina);
                    nom = document.getString("Nom");
                    //Log.d("obteRutina()-->Nom del exercici e", e.getNom()); //D'aquesta manera tambe reconeix la variable.

                    //Si el nom de la rutina coincideix amb el de la base de dades, llavors es quan afegim aquell exercici a la rutina, sino, mostrem un toast.
                    //if (nomRutina.equals(nom)) {
                        String nomE = document.getString("Nom");
                        Map<String, Object> exRut = new HashMap<>(); //Creem un hashmap amb les dades del exercici a guardar.
                        exRut.put("Descripcio", e.getDescripcio());
                        exRut.put("Nom", e.getNom());
                        exRut.put("Dificultat", e.getDificultat());
                        exRut.put("Benefici", e.getBenefici());

                        //Guardem les dades del hashmap dintre del document nou.
                        db.collection("Clients").document(c.getNom()).collection("Rutines").document(nomRutina).collection("exercicis").document(nomE).set(exRut);/*.addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(DetailActivityRutines.this, "Exercici afegit a la rutina" + nomRutina, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DetailActivityRutines.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT).show();
                            }
                        });*/
    // }





    /*public void afegirExercici(View view){
    //obteRutina();
    }*/

    /*public void guardaRutina(final String nom, Exercici ex){
        String nomEx="";
        Map<String, Object> exRut = new HashMap<>();
        exRut.put("Descripcio", ex.getDescripcio());
        exRut.put("Nom", ex.getNom());
        exRut.put("Dificultat", ex.getDificultat());
        exRut.put("Benefici", ex.getBenefici());

        db.collection("Clients").document(c.getNom()).collection("Rutines").document(nom).collection("exercicis").document(ex.getNom()).set(exRut).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(DetailActivityRutines.this, "Exercici afegit a la rutina" + nom, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailActivityRutines.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

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
                     Exercici ex = new Exercici(nom, descripcio, benefici, dificultat);
                    Log.d("exercici obtingut de bbdd",ex.getNom()); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
                    llistaExercicisMuscul.add(ex);
                    exercicisLlista.add(ex.getNom());
                    Log.d("exerciciLlista conte",ex.getNom()); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
                    ArrayAdapter adaptador = new ArrayAdapter(DetailActivityRutines.this,android.R.layout.simple_list_item_1,exercicisLlista);
                    exercicis.setAdapter(adaptador);
                }
            }
        });
    }

    public void sortir(View view){
        finish();
    }

}
