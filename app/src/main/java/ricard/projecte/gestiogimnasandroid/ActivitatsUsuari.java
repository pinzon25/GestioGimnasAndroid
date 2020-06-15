package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivitatsUsuari extends AppCompatActivity {
 public FirebaseFirestore db = FirebaseFirestore.getInstance();
        Client client;
        Button BtCancelar;
        public ArrayList<Activitat> disponibles;
        public ArrayList<Activitat> inscrites;
        public ArrayList<RecyclerActivitats> disponiblesNoms;;
        RecyclerView Rinscrites;
        ActivitatsAdapter Aadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitats_usuari);
        inscrites  = new ArrayList<>();
        disponibles  = new ArrayList<>();
        client = (Client)getIntent().getSerializableExtra("ClientActivitats");
        obteActivitatsDisponibles();
        obteActivitatsInscrites();
        BtCancelar = findViewById(R.id.BtCancelar);
        disponiblesNoms=new ArrayList<>();

        Rinscrites = findViewById(R.id.RecyclerActivitats);

        Rinscrites.setLayoutManager(new LinearLayoutManager(this));
        Aadapter = new ActivitatsAdapter(this,disponiblesNoms, client, disponibles, inscrites);
        Rinscrites.setAdapter(Aadapter);

        initializeData();
        Modelo.amagaBarraNavegacio(this.getWindow());
    }

    @Override //Quan tornem a la activity actualitzem les Activitats disponibles i les inscrites.
    public void onResume(){
        super.onResume();
        this.obteActivitatsDisponibles();
        this.obteActivitatsInscrites();
        Modelo.amagaBarraNavegacio(this.getWindow());
    }

    //Finalitzem i tornem a láctivity anterior.
    public void sortir(View view){
        finish();
    }

    //Adhereix les imatges a la seva activitat corresponent.
    private void initializeData() {
        String[] sportsList = getResources()
                .getStringArray(R.array.sports_images); //va a buscar al fitxer sports_titles a buscar els sports en format string.
        String[] activitatsInfo = getResources()
                .getStringArray(R.array.activitats); //igual pero al fitxer sports_info.

        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);

        disponiblesNoms.clear();

        for(int i=0;i<sportsList.length;i++){
            disponiblesNoms.add(new RecyclerActivitats(sportsList[i],activitatsInfo[i],sportsImageResources.getResourceId(i,0))); //Afegim el atribut de tipus int necessari per formar correctament el constructor.
        }

        sportsImageResources.recycle();

        Aadapter.notifyDataSetChanged();
    }

    //Obte les activitats disponibles a la base de dades.
    private void obteActivitatsDisponibles(){
        Task<QuerySnapshot> querySnapshotTask = db.collection("Activitats").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
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

    //Obte les activitats a les que esta inscrit el client actual.
    private void obteActivitatsInscrites() {
        Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").document(client.getNom()).collection("activitats").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int id = 0;
                float suplement = 0;

                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    id = document.getLong("Id").intValue();
                    suplement = document.getLong("Suplement").floatValue();
                    Activitat act = new Activitat(id, document.getString("Nom"), document.getString("Descripcio"), suplement);
                    inscrites.add(act);
                }
            }


        });
    }

}
