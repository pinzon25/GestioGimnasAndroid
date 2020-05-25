package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.R.layout;
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
import java.util.List;

public class ActivitatsUsuari extends AppCompatActivity {
 public FirebaseFirestore db = FirebaseFirestore.getInstance();
        Client client;
        Spinner Sdisponibles, Sinscrites;
        Button BtBaixa, BtFinalitzar,BtCancelar;
        ArrayList<Activitat> disponibles = new ArrayList<>();
        ArrayList<String> disponiblesNoms = new ArrayList<>();
        RecyclerView Rinscrites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitats_usuari);

        client = (Client)getIntent().getSerializableExtra("ClientActivitats");

        Sdisponibles = findViewById(R.id.ObservableDisponibles);
        Sinscrites = findViewById(R.id.ObservableInscrites);
        BtBaixa = findViewById(R.id.BtBaixaActivitat);
        BtFinalitzar = findViewById(R.id.BtInscripcio);
        BtCancelar = findViewById(R.id.BtCancelar);

        obteActivitatsDisponibles();


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
                    disponiblesNoms.add(document.getString("Nom"));
                }

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, disponiblesNoms);
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
        });
    }

}
