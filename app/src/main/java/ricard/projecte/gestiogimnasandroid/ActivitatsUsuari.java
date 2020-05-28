package ricard.projecte.gestiogimnasandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.R.layout;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitatsUsuari extends AppCompatActivity {
 public FirebaseFirestore db = FirebaseFirestore.getInstance();
        Client client;
        Button BtCancelar;
        ArrayList<Activitat> disponibles = new ArrayList<>();
        ArrayList<Activitat> inscrites;
        ArrayList<Recycler> disponiblesNoms;;
       // String actSeleccionada="";
        RecyclerView Rinscrites;
        ActivitatsAdapter Aadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitats_usuari);
        /*Intent i = getIntent();
        actSeleccionada = i.getStringExtra("NomActivitat");*/
        client = (Client)getIntent().getSerializableExtra("ClientActivitats");
        Intent intent = new Intent("client_intent").putExtra("client",client.getNom());
        LocalBroadcastManager.getInstance(ActivitatsUsuari.this).sendBroadcast(intent);
        BtCancelar = findViewById(R.id.BtCancelar);
        disponiblesNoms=new ArrayList<>();
        inscrites  = new ArrayList<>();
        disponibles  = new ArrayList<>();
        Rinscrites = findViewById(R.id.RecyclerActivitats);

        Rinscrites.setLayoutManager(new LinearLayoutManager(this));
        Aadapter = new ActivitatsAdapter(this,disponiblesNoms);
        Rinscrites.setAdapter(Aadapter);

        initializeData();

    }

    public void sortir(View view){
        finish();
    }

    public void enviaClient(){
        Intent intent = new Intent("client_intent").putExtra("client",client.getNom());
        LocalBroadcastManager.getInstance(ActivitatsUsuari.this).sendBroadcast(intent);
    }

    private void initializeData() {

        String[] sportsList = getResources()
                .getStringArray(R.array.sports_images); //va a buscar al fitxer sports_titles a buscar els sports en format string.
        String[] sportsInfo = getResources()
                .getStringArray(R.array.activitats); //igual pero al fitxer sports_info.

        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);

        // Clear the existing data (to avoid duplication).
        disponiblesNoms.clear();

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





}
