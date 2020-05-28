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
    Activitat act;
    public Client client;
    public ArrayList<Activitat> dispAct;
    public ArrayList<Activitat> insAct;
    private BroadcastReceiver mReceiver;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean borrat;
    Button cancel, finalitzar, baixa;
    TextView nomActivitat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upordown);
        dispAct= new ArrayList<>();
        insAct = new ArrayList<>();
        nomActivitat = findViewById(R.id.LbActivitat);
        TextView sportsTitle = findViewById(R.id.titleDetail);
        ImageView sportsImage = findViewById(R.id.sportsImageDetail);
        cancel = findViewById(R.id.BtCancelaInscripcio);
        finalitzar = findViewById(R.id.BtRealitzaInscripcio);
        baixa = findViewById(R.id.BtBaixaActivitat);

        obteDades();

        /*finalitzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inscritaOno() ==false) {
                    inscriureActivitat();
                }else {
                    Toast.makeText(DetailActivity.this, "Ja estas inscrit a aquesta activitat!.", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*baixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(comprobaBorrades() == false) {
                     Toast.makeText(DetailActivity.this, "No estas inscrit a aquesta activitat!.", Toast.LENGTH_SHORT).show();
                }else {
                     baixaActivitat();
                }
            }

        });*/

    }
    public void inscriu(View view){
        if(inscritaOno() ==false) {
            inscriureActivitat();
        }else {
            Toast.makeText(DetailActivity.this, "Ja estas inscrit a aquesta activitat!.", Toast.LENGTH_SHORT).show();
        }
    }

    public void baixa(View view){
        if(comprobaBorrades() == false) {
            baixaActivitat();
        }else {
            Toast.makeText(DetailActivity.this, "No estas inscrit a aquesta activitat!.", Toast.LENGTH_SHORT).show();
        }
    }


    public void obteDades(){
        String nom="", nomAct="";
        Intent in = getIntent();
        nom = in.getStringExtra("NomActivitat");
        client = (Client) in.getSerializableExtra("Client");
        dispAct = (ArrayList<Activitat>) in.getSerializableExtra("Disponibles");
        insAct = (ArrayList<Activitat>) in.getSerializableExtra("Inscrites");
        Log.d("Metode activitatSeleccionada, nom obtingut des del ActivitatsUsuari.java",nom); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
        Log.d("Metode activitatSeleccionada, nom del client obtingut des del ActivitatsAdapter",client.getNom()); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
        nomActivitat.setText("Nom de la Activitat: "+ nom);

        for(int i=0; i <dispAct.size();i++){
            if(nom.equals(dispAct.get(i).getNom())){
               act = dispAct.get(i);
               Log.d("Metode activitatSeleccionada, nom de la activitat creada",act.getNom()); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
            }
        }
        mostraArray(dispAct);
        mostraArrayinscrites(insAct);
    }



    public void inscriureActivitat(){
        String nomac="";
        nomac = act.getNom();

        Log.d("Metode inscriureActivitat, variable global activitat, nom act",nomac);
        float totalsuplement=0;

                Map<String, Object> actv = new HashMap<>();
                actv.put("Id", act.getIdActivitat());
                actv.put("Nom", act.getNom());
                actv.put("Descripcio", act.getDescripcio());
                actv.put("Suplement", act.getSuplement());

                totalsuplement += act.getSuplement();


                db.collection("Clients").document(client.getNom()).collection("activitats").document(act.getNom()).set(actv).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(DetailActivity.this, "Dades guardades", Toast.LENGTH_SHORT).show();
                }
                }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DetailActivity.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT).show();
                }
                 });

                DocumentReference docRef = (DocumentReference) db.collection("Clients").document(client.getNom());
                docRef.update("Cuota", FieldValue.increment(totalsuplement));

                insAct.add(act);
    }

    public void baixaActivitat(){
        float totalsuplement=0;
        Activitat a = act;
        db.collection("Clients").document(client.getNom()).collection("activitats").document(act.getNom()).delete().addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(DetailActivity.this, "Dades guardades", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailActivity.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT).show();
            }
        });
        DocumentReference docRef = (DocumentReference) db.collection("Clients").document(client.getNom());
        docRef.update("Cuota", FieldValue.increment(-act.getSuplement()));
        insAct.remove(a);
        Log.d("Activitat Borrada",act.getNom());
    }

    public boolean inscritaOno(){
        boolean inscrita = false;
        for(int i = 0; i < insAct.size(); i++){
            if(act.getNom().equals(insAct.get(i).getNom())){
                inscrita = true;
                break;
            }
        }
        return inscrita;
    }

    public boolean comprobaBorrades(){
        boolean borrada=true;
        for(int i = 0; i < insAct.size(); i++){
            if(act.getNom().equals(insAct.get(i).getNom())){
                borrada=false;
                break;
            }
        }
        return borrada;
    }

    public void mostraArray(ArrayList<Activitat> a){
        for(int i=0; i <a.size();i++){
            Log.d("nom activitat disponible",a.get(i).getNom());
            Log.d("Descripcio activitat disponible",a.get(i).getDescripcio());
            Log.d("Id activitat disponible",a.get(i).getIdActivitat().toString());
            Log.d("Suplement activitat disponible",String.valueOf(a.get(i).getSuplement()));
        }

    }

    public void mostraArrayinscrites(ArrayList<Activitat> o){
        for(int x=0; x <o.size();x++){
            Log.d("nom activitat inscrita",o.get(x).getNom());
            Log.d("Descripcio activitat inscrita",o.get(x).getDescripcio());
            Log.d("Id activitat inscrita",o.get(x).getIdActivitat().toString());
            Log.d("Suplement activitat inscrita",String.valueOf(o.get(x).getSuplement()));
        }

    }

    /*public void obteActivitatsDisponibles(){

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
    }*/

    /*public void obteActivitatsInscrites() {

         Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").document(client.getNom()).collection("activitats").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
    }*/

}