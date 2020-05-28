package ricard.projecte.gestiogimnasandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    public Activitat act;
    public Client client=null;
    ArrayList<Activitat> disponibles;
    ArrayList<Activitat> inscrites;
    private BroadcastReceiver mReceiver;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button cancel, finalitzar, baixa;
    TextView nomActivitat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upordown);
        disponibles= new ArrayList<>();
        inscrites = new ArrayList<>();
        nomActivitat = findViewById(R.id.LbActivitat);
        TextView sportsTitle = findViewById(R.id.titleDetail);
        ImageView sportsImage = findViewById(R.id.sportsImageDetail);
        cancel = findViewById(R.id.BtCancelaInscripcio);
        finalitzar = findViewById(R.id.BtRealitzaInscripcio);
        baixa = findViewById(R.id.BtBaixaActivitat);


//        sportsTitle.setText(getIntent().getStringExtra("title"));

        //Glide.with(this).load(getIntent().getIntExtra("image_resource",0)).into(sportsImage);


        inscrites  = new ArrayList<>();
        disponibles  = new ArrayList<>();

         mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String nomClient = intent.getStringExtra("client");

                Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot document : documents) {

                            if (nomClient.equals(document.getString("Nom"))) {
                                client = new Client();
                                client.setNom(document.getString("Nom"));
                                client.setCognoms(document.getString("Cognoms"));
                                client.setDni(document.getString("Dni"));
                                client.setTelefon(document.getLong("Telefon"));
                                client.setContrassenya(document.getString("Contrasenya"));
                                client.setCodiPostal(document.getLong("Codi Postal").intValue());
                                client.setPoblacio(document.getString("Poblacio"));
                                client.setComptePagament(document.getString("Compte pagament"));
                                client.setJornadaAcces(document.getString("Jornada acces"));
                                client.setCuota(document.getLong("Cuota").floatValue());


                                obteActivitatsInscrites(client);


                            }

                        }
                    }
                });
            }
        };


        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("client_intent"));



        activitatseleccionada();

        obteActivitatsDisponibles();
        mostraArray(disponibles);

        finalitzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraArray(disponibles);
                mostraArrayinscrites(inscrites);
            }
        });

        //obteActivitatsInscrites();

    }

    public void mostraArray(ArrayList<Activitat> a){
        for(int i=0; i <a.size();i++){
            Log.d("nom activitat disponible",a.get(i).getNom());
            Log.d("Descripcio activitat disponible",a.get(i).getDescripcio());
            Log.d("Id activitat disponible",a.get(i).getIdActivitat().toString());
            Log.d("Suplement activitat disponible",String.valueOf(a.get(i).getSuplement()));
        }

    }

    public void mostraArrayinscrites(ArrayList<Activitat> a){
        for(int i=0; i <a.size();i++){
            Log.d("nom activitat inscrita",a.get(i).getNom());
            Log.d("Descripcio activitat inscrita",a.get(i).getDescripcio());
            Log.d("Id activitat inscrita",a.get(i).getIdActivitat().toString());
            Log.d("Suplement activitat inscrita",String.valueOf(a.get(i).getSuplement()));
        }

    }

    public void activitatseleccionada(){
        String nom="", nomAct="";
        Intent in = getIntent();
        nom = in.getStringExtra("NomActivitat");
        nomActivitat.setText("Nom de la Activitat: "+ nom);
        Log.d("Metode activitatSeleccionada, nom obtingut des del ActivitatsUsuari.java",nom); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
        for(int i=0; i <disponibles.size();i++){
            if(nom.equals(disponibles.get(i).getNom())){
                act = disponibles.get(i);
            }
        }
    }

    public void obteActivitatsDisponibles(){

        Task<QuerySnapshot> querySnapshotTask = db.collection("Activitats").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String nom="";
                Activitat act = null;
                int id = 0;
                float suplement = 0;

                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    id = document.getLong("Id").intValue();
                    suplement = document.getLong("Suplement").floatValue();
                    act = new Activitat(id, document.getString("Nom"), document.getString("Descripcio"), suplement);
                    disponibles.add(act);
                }
            }
        });
    }

    public void obteActivitatsInscrites(Client c) {

         Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").document(c.getNom()).collection("activitats").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String nom = "";
                Activitat act = null;
                int id = 0;
                float suplement = 0;

                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    id = document.getLong("Id").intValue();
                    suplement = document.getLong("Suplement").floatValue();
                    act = new Activitat(id, document.getString("Nom"), document.getString("Descripcio"), suplement);
                    inscrites.add(act);
                }
            }
        });
    }

    public void inscriureActivitat(){
        String nomac="";
        //nomac = act.getNom();

        Log.d("Metode inscriureActivitat, variable global activitat act",nomac);
        float totalsuplement=0;
        for(int i = 0; i < inscrites.size(); i++){
            if(act.equals(inscrites.get(i))){
                Toast.makeText(DetailActivity.this, "Ja estas inscrit a aquesta activitat!.", Toast.LENGTH_SHORT);
            }else{
                Map<String, Object> actv = new HashMap<>();
                actv.put("Id", act.getIdActivitat());
                actv.put("Nom", act.getNom());
                actv.put("Descripcio", act.getDescripcio());
                actv.put("Suplement", act.getSuplement());

                totalsuplement += act.getSuplement();

                db.collection("Clients").document(client.getNom()).collection("activitats").document(act.getNom()).set(actv).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(DetailActivity.this, "Dades guardades", Toast.LENGTH_SHORT);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailActivity.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT);
                    }
                });

                DocumentReference docRef = (DocumentReference) db.collection("Clients").document(client.getNom());
                docRef.update("Cuota", FieldValue.increment(totalsuplement));
            }
        }

    }

}
