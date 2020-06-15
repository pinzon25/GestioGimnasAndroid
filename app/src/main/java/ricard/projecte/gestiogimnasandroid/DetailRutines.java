package ricard.projecte.gestiogimnasandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class DetailRutines extends AppCompatActivity {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Client c;
    public Exercici e;
    public ListView exercicis;
    TextView nomMuscul;
    public Spinner SpRutines;
    public EditText Nrutina;
    public Button CancelarRutina, afegirExercici, esborrarExercici, novaRutina, BtMostrar;
    public ArrayList<Exercici> llistaExercicisMuscul;
    public ArrayList<Exercici> llistaExercicisRutina;
    public ArrayList<String> exercicisLlista;
    public List<String> rutinesExistents;
    public String nomRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rutines);
        //Modelo.amagaBarraNavegacio(this.getWindow());
        BtMostrar = findViewById(R.id.BtMostrarRutina);
        SpRutines = findViewById(R.id.SpRutinesExistents);
        exercicis = findViewById(R.id.LlistaEx);

        nomMuscul = findViewById(R.id.LbNomMuscul);
        Nrutina = findViewById(R.id.TfNomRutina);
        CancelarRutina = findViewById(R.id.BtCancelarRutines);
        afegirExercici = findViewById(R.id.BtAfegirExercici);
        esborrarExercici = findViewById(R.id.BtEsborraExercici);
        novaRutina = findViewById(R.id.BtNovaRutina);
        nomRutina="";

        e = null;
        exercicisLlista = new ArrayList<>();
        rutinesExistents= new ArrayList<>();
        llistaExercicisMuscul = new ArrayList<>();
        llistaExercicisRutina = new ArrayList<>();
        rebreDades(); //Obtenim les dades que ens envia  el RecyclerMusculs.

        //Identifica el exercici clicat i l'emmagatzema en una variable de classe.
        exercicis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    for(int j = 0; j <llistaExercicisMuscul.size();j++ ) {
                        if (adapterView.getItemAtPosition(i).toString().equals(llistaExercicisMuscul.get(i).getNom())) {
                                Log.d("Nom de la rutina introduit", nomRutina);
                                e = llistaExercicisMuscul.get(i);
                                Log.d("exercici clicat", e.getNom());   //Verifica correctament ques'hagi introduit un nom de rutina i el exercici clicat.
                                break;
                        }
                    }
            }
        });


        obteRutinesExistents(); //Obtenim les rutines que te el client actual des de la base de dades.

        ArrayAdapter adapter = new ArrayAdapter(this, simple_spinner_item,rutinesExistents); //Creem el adaptador i el configurem per mostrar el contingunt del Spinner correctament.
        adapter.setDropDownViewResource(simple_spinner_item);
        SpRutines.setAdapter(adapter);


        //Configurem el boto de "Mostrar" per que amb un long click ens torni a mostrar els exercicis del grup muscular actual.
        BtMostrar.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                exercicisLlista.clear();
                obteExercicisMuscul(nomMuscul.getText().toString());
                afegirExercici.setEnabled(true);
                esborrarExercici.setEnabled(true);
                return true;
            }
        });

        //Configurem un click normal per que ens mostri els exercicis de la rutina introduida.
        BtMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (nomRutina.isEmpty()) {
                    Toast.makeText(DetailRutines.this, "No s'ha introduit un nom de rutina per mostrar.", Toast.LENGTH_SHORT).show();
                } else {*/
                    mostraRutina();
                    afegirExercici.setEnabled(false);
                    esborrarExercici.setEnabled(false);
                //}
            }
        });


        /*SpRutines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });*/
    }

    public void amagaBotons(View view){
        Modelo.amagaBarraNavegacio(DetailRutines.this.getWindow());
    }

    //Obte el client actual, el nom del grup muscular escollit i els seus exercicis de la classe MusculsAdapter.
    private void rebreDades(){
        String nom="";
        Intent in = getIntent();
        c  = (Client) in.getSerializableExtra("Client");
        nom = in.getStringExtra("NomMuscul");
        obteExercicisMuscul(nom);
        nomMuscul.setText(nom);
    }

    //Agafa el contingut del text view i el guarda com a rutina a la bbdd.
    public void creaNovaRutina(View view){
        nomRutina = Nrutina.getText().toString();
        if(nomRutina.isEmpty()) {
            Toast.makeText(DetailRutines.this, "No s'ha introduit un nom per a la rutina.", Toast.LENGTH_SHORT).show();
        }else{
            Map<String, Object> exN = new HashMap<>();
            exN.put("Nom", nomRutina);
            Task<Void> querySnapshotTask = db.collection("Clients").document(c.getNom()).collection("Rutines").document(nomRutina).set(exN);
            Nrutina.setText("");
            nomRutina = "";
            rutinesExistents.clear();
            obteRutinesExistents();
        }
    }

    //Elimina la rutina introduida de la base de dades i actualitza el contingut del Spinner.
    public void esborrarRutina(View view){
        nomRutina = Nrutina.getText().toString();
        Map<String, Object> exN = new HashMap<>();
        exN.put("Nom", nomRutina);

        if(nomRutina.isEmpty()){
            Toast.makeText(DetailRutines.this, "No s'ha introduit un nom per a la rutina.", Toast.LENGTH_SHORT).show();
        }
        else if(comprobaExistenciaRutina(nomRutina)==true) {
            db.collection("Clients").document(c.getNom()).collection("Rutines").document(nomRutina).delete();
            Toast.makeText(DetailRutines.this, "Rutina esborrada correctament.", Toast.LENGTH_SHORT).show();
            Nrutina.setText("");
            nomRutina="";
            rutinesExistents.clear();
            obteRutinesExistents();
        }
        else if(comprobaExistenciaRutina(nomRutina)==false){
            Toast.makeText(DetailRutines.this, "La rutina introduida no existeix.", Toast.LENGTH_SHORT).show();
        }
    }

    //Agafa el nom de rutina introduit i mostra els exercicis que la formen.
    private void mostraRutina(){
        nomRutina = Nrutina.getText().toString();
        if(nomRutina.isEmpty()){
            Toast.makeText(DetailRutines.this, "No s'ha introduit un nom per a la rutina.", Toast.LENGTH_SHORT).show();
        }else {
            exercicisLlista.clear();
            Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").document(c.getNom()).collection("Rutines").document(nomRutina).collection("exercicis").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    String nom = "", benefici = "", descripcio = "", dificultat = "";

                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot document : documents) {

                        nom = document.getString("Nom");
                        descripcio = document.getString("Descripcio");
                        benefici = document.getString("Benefici");
                        dificultat = document.getString("Dificultat");
                        Exercici ex = new Exercici(nom, descripcio, benefici, dificultat);

                        llistaExercicisRutina.add(ex);
                        exercicisLlista.add(ex.getNom());
                        ArrayAdapter adaptador = new ArrayAdapter(DetailRutines.this, android.R.layout.simple_list_item_1, exercicisLlista) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                                tv.setTextColor(Color.WHITE);
                                return view;
                            }
                        };

                        exercicis.setAdapter(adaptador);
                    }
                }
            });
        }
    }

    //Verifica si el nom de rutina introduit es correspon amb algun dels de les rutines existents.
    private boolean comprobaExistenciaRutina(String nom){
        boolean existeix = false;

        for(int i = 0; i<rutinesExistents.size();i++ ){
            if(nomRutina.equals(rutinesExistents.get(i))){
                existeix = true;
            }
        }
        return existeix;
    }

    //Guarda l'exercici a la rutina del client a la base de dades.
    public void guardaExerciciRutina(View view) {
      nomRutina = Nrutina.getText().toString();
    if(nomRutina.equals("")){
        Toast.makeText(DetailRutines.this, "No has escollit la rutina.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DetailRutines.this, "Dades guardades", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailRutines.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT).show();
            }
        });

      }
    nomRutina="";
    }

    //Esborra l'exercici clicat de la rutina introduida.
    public void esborraExerciciRutina(View view){
        nomRutina = Nrutina.getText().toString();
        if(nomRutina.isEmpty()){
            Toast.makeText(DetailRutines.this, "Introdueix el nom de la rutina.", Toast.LENGTH_SHORT).show();
        }else{
            db.collection("Clients").document(c.getNom()).collection("Rutines").document(nomRutina).collection("exercicis").document(e.getNom()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(DetailRutines.this, "Exercici esborrat correctament.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        nomRutina="";
    }

    //Obte els exercicis del muscul seleccionat.
    private void obteExercicisMuscul(String nomMuscul){
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

                    llistaExercicisMuscul.add(ex);
                    exercicisLlista.add(ex.getNom());
                    Log.d("exerciciLlista conte",ex.getNom()); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.

                    //Aquest apartat ens permet establir el color de font dels elements que es mostraran al ListView, en aquest cas, es el color Blanc.
                    ArrayAdapter adaptador = new ArrayAdapter(DetailRutines.this, android.R.layout.simple_list_item_1,exercicisLlista){
                        @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        View view = super.getView(position, convertView, parent);
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);
                        tv.setTextColor(Color.WHITE);
                        return view;
                    }};

                    exercicis.setAdapter(adaptador);
                }
            }
        });
    }

    //Llegeix les rutines existents del client de la base de dades.
    private void obteRutinesExistents(){
        Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").document(c.getNom()).collection("Rutines").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String nom = "";

                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    nom = document.getString("Nom");
                    rutinesExistents.add(nom);
                }
              }
            });
        }

    //Finalitza i torna a la activity anterior.
    public void sortir(View view){
        finish();
    }

}
