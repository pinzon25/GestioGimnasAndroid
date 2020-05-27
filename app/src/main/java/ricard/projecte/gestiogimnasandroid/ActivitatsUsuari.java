package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.R.layout;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivitatsUsuari extends AppCompatActivity {
 public FirebaseFirestore db = FirebaseFirestore.getInstance();
        Client client;
        Spinner Sdisponibles, Sinscrites;
        Button BtBaixa, BtFinalitzar,BtCancelar;
        ArrayList<Activitat> disponibles = new ArrayList<>();
        ArrayList<Activitat> inscrites = new ArrayList<>();
         ArrayList<Recycler> disponiblesNoms;;
        RecyclerView Rinscrites;
        ActivitatsAdapter Aadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitats_usuari);

        client = (Client)getIntent().getSerializableExtra("ClientActivitats");
        obteActivitatsDisponibles();
        Sdisponibles = findViewById(R.id.ObservableDisponibles);
        Sinscrites = findViewById(R.id.ObservableInscrites);
        BtBaixa = findViewById(R.id.BtBaixaActivitat);
        BtFinalitzar = findViewById(R.id.BtInscripcio);
        BtCancelar = findViewById(R.id.BtCancelar);
        disponiblesNoms=new ArrayList<>();

        Rinscrites = findViewById(R.id.RecyclerActivitats);

        Rinscrites.setLayoutManager(new LinearLayoutManager(this));
        Aadapter = new ActivitatsAdapter(this,disponiblesNoms);
        //Aadapter = new ActivitatsAdapter(this,disponibles);
        Rinscrites.setAdapter(Aadapter);

        initializeData();


    }

    private void initializeData() {

        String[] sportsList = getResources()
                .getStringArray(R.array.sports_images); //va a buscar al fitxer sports_titles a buscar els sports en format string.
        String[] sportsInfo = getResources()
                .getStringArray(R.array.activitats); //igual pero al fitxer sports_info.

        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);

        // Clear the existing data (to avoid duplication).
        disponibles.clear();

        // Create the ArrayList of Sports objects with titles and
        // information about each sport.
        for(int i=0;i<sportsList.length;i++){
            //mSportsData.add(new Sport(sportsList[i],sportsInfo[i])); //afegim al arraylist nous esports amb el new i el seu constructor.
            disponiblesNoms.add(new Recycler(sportsList[i],sportsInfo[i],sportsImageResources.getResourceId(i,0))); //Afegim el atribut de tipus int necessari per formar correctament el constructor.
        }

        sportsImageResources.recycle();

        Aadapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializeData();
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

    public void obteActivitatsInscrites(){
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
                   // disponiblesNoms.add(document.getString("Nom"));
                }

            }
        });


        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, disponiblesNoms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sdisponibles.setAdapter(adapter);
        Sdisponibles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });*/
    }

}
