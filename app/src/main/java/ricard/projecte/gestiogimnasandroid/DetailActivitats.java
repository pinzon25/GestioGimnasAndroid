package ricard.projecte.gestiogimnasandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivitats extends AppCompatActivity {
    Activitat act;
    public Client client;
    public ArrayList<Activitat> dispAct;
    public ArrayList<Activitat> insAct;
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
        cancel = findViewById(R.id.BtCancelaInscripcio);
        finalitzar = findViewById(R.id.BtRealitzaInscripcio);
        baixa = findViewById(R.id.BtBaixaActivitat);

        obteDades();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //Es comproba si el client esta inscrit al'activitat seleccionada, si no ho esta s'inscriu el client, sino es mostra un missatge.
    public void inscriu(View view){
        if(inscritaOno() == false) {
            inscriureActivitat();
        }else {
            Toast.makeText(DetailActivitats.this, "Ja estas inscrit a aquesta activitat!.", Toast.LENGTH_SHORT).show();
        }
    }

    //Es comproba si el client s'ha donat de baixa a l'activitat, si s'ha donat de baixa mostra un missatge, pel contrari si no ho esta, el dona de baixa.
    public void baixa(View view){
        if(comprobaBorrades() == false) {
            baixaActivitat();
        }else {
            Toast.makeText(DetailActivitats.this, "No estas inscrit a aquesta activitat!.", Toast.LENGTH_SHORT).show();
        }
    }

    //Rep els ArrayList d'activitats disponibles i d'activitats inscrites aixi com el nom de l'activitat seleccionada.
    private void obteDades(){
        String nom="", nomAct="";
        Intent in = getIntent();
        nom = in.getStringExtra("NomActivitat");
        client = (Client) in.getSerializableExtra("Client");
        dispAct = (ArrayList<Activitat>) in.getSerializableExtra("Disponibles");
        insAct = (ArrayList<Activitat>) in.getSerializableExtra("Inscrites");
        nomActivitat.setText("Nom de la Activitat: "+ nom);

        for(int i=0; i <dispAct.size();i++){
            if(nom.equals(dispAct.get(i).getNom())){
               act = dispAct.get(i);
               Log.d("Metode activitatSeleccionada, nom de la activitat creada",act.getNom()); //Verifiquem el nom que ens arriba des de la activity ActivitatsUsuari.
            }
        }
    }

    //S'afegeix l'activitat a la col·leccio "Activitats" del client.
    private void inscriureActivitat(){
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


                db.collection("Clients").document(client.getNom()).collection("Activitats").document(act.getNom()).set(actv).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(DetailActivitats.this, "Dades guardades", Toast.LENGTH_SHORT).show();
                }
                }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DetailActivitats.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT).show();
                }
                 });

                DocumentReference docRef = (DocumentReference) db.collection("Clients").document(client.getNom());
                docRef.update("Cuota", FieldValue.increment(totalsuplement));

                insAct.add(act);
    }

    //S'esborra l'activitat de la col·leccio "Activitats" del client.
    private void baixaActivitat(){
        float totalsuplement=0;
        Activitat a = act;
        db.collection("Clients").document(client.getNom()).collection("Activitats").document(act.getNom()).delete().addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(DetailActivitats.this, "Dades guardades", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailActivitats.this, "No s'han pogut guardar les dades.", Toast.LENGTH_SHORT).show();
            }
        });
        DocumentReference docRef = (DocumentReference) db.collection("Clients").document(client.getNom());
        docRef.update("Cuota", FieldValue.increment(-act.getSuplement()));
        insAct.remove(a);
        Log.d("Activitat Borrada",act.getNom());
    }

    //Es comproba si l'activitat es troba entre les activitats a les que esta inscrit el client.
    private boolean inscritaOno(){
        boolean inscrita = false;
        for(int i = 0; i < insAct.size(); i++){
            if(act.getNom().equals(insAct.get(i).getNom())){
                inscrita = true;
                break;
            }
        }
        return inscrita;
    }

    //Es comproba si l'activitat es troba entre les activitats a les que s'ha donat de baixa el client.
    private boolean comprobaBorrades(){
        boolean borrada=true;
        for(int i = 0; i < insAct.size(); i++){
            if(act.getNom().equals(insAct.get(i).getNom())){
                borrada=false;
                break;
            }
        }
        return borrada;
    }

}
