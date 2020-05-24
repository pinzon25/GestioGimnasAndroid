package ricard.projecte.gestiogimnasandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_dades_usuari);

        actCpPob=findViewById(R.id.BtActualitzapoblaCp);
        actIb = findViewById(R.id.BtActualitzaIban);
        actCont = findViewById(R.id.BtActualitzaContrasenya);

        cp = findViewById(R.id.TfModificaContrasenya);
        pob=findViewById(R.id.TfPoblacioModifica);
        ib=findViewById(R.id.TfCompteIbanModifica);
        cont = findViewById(R.id.TfModificaContrasenya);
        rep = findViewById(R.id.TfRepeticioModifica);

        bundle = getIntent().getExtras();
        c = bundle.getParcelable("Client");


        actCpPob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    modificaPoblacio();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });





    }


    public void modificaPoblacio() throws InterruptedException, ExecutionException {
        String pb = "", codipostal = "";
        int codipost = 0;

        try {
            codipost = Integer.valueOf(cp.getText().toString());
        } catch (NumberFormatException ex) {
           // LbCp.setText("Format del codi postal erroni.");
        }

        pb = pob.getText().toString();
        codipostal = cp.getText().toString();

        if (!Modelo.comprobaPoblacio(pb)) {
            Toast.makeText(ModificaDadesUsuari.this, "Format de la poblacio erroni.", Toast.LENGTH_SHORT);
        } else if (!Modelo.comprobaCodiPostal(codipostal)) {
            Toast.makeText(ModificaDadesUsuari.this,"Format de codi postal erroni.", Toast.LENGTH_SHORT);
        } else {
         //db.collection("Clients").document(c.getNom());

            db.collection("Clients").document(c.getNom()).update("Codi Postal",codipost).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ModificaDadesUsuari.this, "Dades actualitzades.", Toast.LENGTH_SHORT);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModificaDadesUsuari.this, "No s'han pogut actualitzar les dades.", Toast.LENGTH_SHORT);
                        }
                    });


        }

        db.collection("Clients").document(c.getNom()).update("Poblacio",pb).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ModificaDadesUsuari.this, "Dades actualitzades.", Toast.LENGTH_SHORT);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ModificaDadesUsuari.this, "No s'han pogut actualitzar les dades.", Toast.LENGTH_SHORT);
                    }
                });

    }
}
