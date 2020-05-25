package ricard.projecte.gestiogimnasandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class ModificaDadesUsuari extends AppCompatActivity {
    Bundle bundle;
    Client c;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button actCpPob,actIb,actCont;
    EditText cp, pob, ib, cont, rep;
    TextView etiquetaClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_dades_usuari);

        c = (Client)getIntent().getSerializableExtra("ClientObjectiu");

        etiquetaClient = findViewById(R.id.LbClientModificaDades);
        etiquetaClient.setText(c.getNom() + " " + c.getCognoms());
        actCpPob=findViewById(R.id.BtActualitzapoblaCp);
        actIb = findViewById(R.id.BtActualitzaIban);
        actCont = findViewById(R.id.BtActualitzaContrasenya);

        cp = findViewById(R.id.TfModificaContrasenya);
        pob=findViewById(R.id.TfPoblacioModifica);
        ib=findViewById(R.id.TfCompteIbanModifica);
        cont = findViewById(R.id.TfModificaContrasenya);
        rep = findViewById(R.id.TfRepeticioModifica);

    }

    public void modificaContrasenya(View view){
        String contrasenya = "", repeticio = "";
        contrasenya = cont.getText().toString();
        repeticio=rep.getText().toString();
        boolean correcte;
        if(Modelo.comprobaContrasenya(contrasenya,repeticio)==true){
            db.collection("Clients").document(c.getNom()).update("Contrasenya",EncriptaDesencripta.getMD5(contrasenya)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ModificaDadesUsuari.this, "Contrasenya actualitzada.", Toast.LENGTH_SHORT);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModificaDadesUsuari.this, "No s'han pogut actualitzar lea contrasenya.", Toast.LENGTH_SHORT);
                        }
                    });
        }
    }

    public void modificaPoblacio(View view) throws InterruptedException, ExecutionException {
        String pb = "", codipostal = "";
        int codipost = 0;

       /* if (!Modelo.comprobaPoblacio(pb)) {
            Toast.makeText(ModificaDadesUsuari.this, "Format de la poblacio erroni.", Toast.LENGTH_SHORT);
        } else if (!Modelo.comprobaCodiPostal(codipostal)) {
            Toast.makeText(ModificaDadesUsuari.this,"Format de codi postal erroni.", Toast.LENGTH_SHORT);
        } else {*/
        codipostal = cp.getText().toString();
        pb = pob.getText().toString();

        if (!pb.isEmpty()) {
            db.collection("Clients").document(c.getNom()).update("Poblacio", pb).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ModificaDadesUsuari.this, "Poblacio actualitzada.", Toast.LENGTH_SHORT);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModificaDadesUsuari.this, "No s'han pogut actualitzar la poblacio.", Toast.LENGTH_SHORT);
                        }
                    });
        }else{
            Toast.makeText(ModificaDadesUsuari.this, "No has introduit la poblacio.", Toast.LENGTH_SHORT);
        }

        //codipost = Integer.valueOf(cp.getText().toString());
        if (!codipostal.isEmpty()) {
            db.collection("Clients").document(c.getNom()).update("Codi Postal", Integer.valueOf(codipostal)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ModificaDadesUsuari.this, "Codi postal actualitzat.", Toast.LENGTH_SHORT);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModificaDadesUsuari.this, "No s'han pogut actualitzar el codi postal.", Toast.LENGTH_SHORT);
                        }
                    });
        } else {
            Toast.makeText(ModificaDadesUsuari.this, "No has introduit el codipostal.", Toast.LENGTH_SHORT);
        }
    }
}
