package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RutinesUsuari extends AppCompatActivity {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    Client clientRutina;
    RecyclerView Rmusculs;
    Button BtCancelarRutina;
    MusculsAdapter Madapter;
    ArrayList<RecyclerMusculs> Musculs;
    public ArrayList<String> NomsMusculs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutines_usuari);
        Musculs  = new ArrayList<>();
        NomsMusculs = new ArrayList<>();
        clientRutina = (Client)getIntent().getSerializableExtra("ClientRutines");
        obteMusculs();
        BtCancelarRutina = findViewById(R.id.BtCancelarRutines);
        Rmusculs = findViewById(R.id.RecyclerMusculs);
        Rmusculs.setLayoutManager(new LinearLayoutManager(this));
        Madapter = new MusculsAdapter(this,Musculs, clientRutina/*, NomsMusculs*/);
        Rmusculs.setAdapter(Madapter);

        initializeData();

    }

    private void initializeData() {

        String[] MusculList = getResources()
                .getStringArray(R.array.musculs_imatges); //va a buscar al fitxer sports_titles a buscar els sports en format string.
        String[] MusculsInfo = getResources()
                .getStringArray(R.array.musculs); //igual pero al fitxer sports_info.

        TypedArray MusculsImageResources = getResources().obtainTypedArray(R.array.musculs_imatges);

        for(int i=0;i<MusculList.length;i++){
            Musculs.add(new RecyclerMusculs(MusculList[i],MusculsInfo[i],MusculsImageResources.getResourceId(i,0))); //Afegim el atribut de tipus int necessari per formar correctament el constructor.
        }

        MusculsImageResources.recycle();

        Madapter.notifyDataSetChanged();
    }

    private void obteMusculs(){

        Task<QuerySnapshot> querySnapshotTask = db.collection("Exercicis").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String nom="";

                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    nom = document.getId();
                    NomsMusculs.add(nom);
                }
            }
        });
    }

    public void sortir(View view){
        finish();
    }

}
