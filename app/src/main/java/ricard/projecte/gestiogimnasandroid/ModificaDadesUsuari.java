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
    Client c;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button actCpPob,actIb,actCont;
    EditText cp, pob, ib, cont, rep;
    TextView etiquetaClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_dades_usuari);
        //Modelo.amagaBarraNavegacio(this.getWindow());
        c = (Client)getIntent().getSerializableExtra("ClientObjectiu");

        etiquetaClient = findViewById(R.id.LbClientModificaDades);
        etiquetaClient.setText(c.getNom() + " " + c.getCognoms());

        cont = findViewById(R.id.TfModificaContrasenya);
        rep = findViewById(R.id.TfRepeticioModifica);
        cp = findViewById(R.id.TfCodipostalModifica);
        pob=findViewById(R.id.TfPoblacioModifica);
        ib=findViewById(R.id.TfCompteIbanModifica);

        actCpPob=findViewById(R.id.BtActualitzapoblaCp);
        actIb = findViewById(R.id.BtActualitzaIban);
        actCont = findViewById(R.id.BtActualitzaContrasenya);

    }

    //Modifica la contrasenya del usuari.
    public void modificaContrasenya(View view){
        String contrasenya = "", repeticio = "", contencriptada="";
        contrasenya = cont.getText().toString();
        repeticio=rep.getText().toString();
        contencriptada = EncriptaDesencripta.getMD5(contrasenya);
        boolean correcte;

        if(contrasenya.isEmpty() | repeticio.isEmpty()){
            Toast.makeText(ModificaDadesUsuari.this, "Dades incompletes.", Toast.LENGTH_SHORT).show();
        }else if(!contrasenya.equals(repeticio)){
        Toast.makeText(ModificaDadesUsuari.this, "La contrasenya i la repeticio no coincideixen.", Toast.LENGTH_SHORT).show();
        }else if(Modelo.comprobaContrasenya(contrasenya,repeticio)==true){
            Log.d("contrasenya introduida ",contrasenya);
            Log.d("contrasenya encriptada",contencriptada);

            db.collection("Clients").document(c.getNom()).update("Contrasenya",contencriptada).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ModificaDadesUsuari.this, "Contrasenya actualitzada.", Toast.LENGTH_SHORT).show();
                    cont.setText("");
                    rep.setText("");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModificaDadesUsuari.this, "No s'han pogut actualitzar lea contrasenya.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //Modifica la poblacio del usuari.
    public void modificaPoblacio(View view) throws InterruptedException, ExecutionException {
        String pb = "";
        int codipost = 0;
        pb = pob.getText().toString();

        if(pb.isEmpty()){
            Toast.makeText(ModificaDadesUsuari.this, "Dades incompletes.", Toast.LENGTH_SHORT).show();
        }else if (Modelo.comprobaPoblacio(pb)==true) {
            db.collection("Clients").document(c.getNom()).update("Poblacio", pb).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ModificaDadesUsuari.this, "Poblacio actualitzada.", Toast.LENGTH_SHORT).show();
                    pob.setText("");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModificaDadesUsuari.this, "No s'han pogut actualitzar la poblacio.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else if(Modelo.comprobaPoblacio(pb)==false){
            Toast.makeText(ModificaDadesUsuari.this, "Dades amb format incorrecte.", Toast.LENGTH_SHORT).show();
        }

    }

    //Modifica el codi postal del usuari.
    public void modificaCodiPostal(View view) {
        String codipostal = "";
        codipostal = cp.getText().toString();
    try {
        if (codipostal.isEmpty()) {
            Toast.makeText(ModificaDadesUsuari.this, "Dades incompletes.", Toast.LENGTH_SHORT).show();
        } else if (Modelo.comprobaCodiPostal(codipostal) == true) {
            db.collection("Clients").document(c.getNom()).update("Codi Postal", Integer.valueOf(codipostal)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ModificaDadesUsuari.this, "Codi postal actualitzat.", Toast.LENGTH_SHORT).show();
                    cp.setText("");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModificaDadesUsuari.this, "No s'han pogut actualitzar el codi postal.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (Modelo.comprobaCodiPostal(codipostal) == false) {
            Toast.makeText(ModificaDadesUsuari.this, "Dades amb format incorrecte.", Toast.LENGTH_SHORT).show();
        }
    }catch(NumberFormatException ex){
        Toast.makeText(ModificaDadesUsuari.this, "Dades amb format incorrecte.", Toast.LENGTH_SHORT).show();
    }
        }

    //Modifica el compte de pagament del usuari.
    public void modificaIban(View view){
        String iban = "";
        iban = ib.getText().toString();

        if (Modelo.comprobaCompteBancari(iban)==true) {
            db.collection("Clients").document(c.getNom()).update("Compte pagament", iban).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ModificaDadesUsuari.this, "Iban actualitzat.", Toast.LENGTH_SHORT).show();
                    ib.setText("");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModificaDadesUsuari.this, "No s'han pogut actualitzar el Iban.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(ModificaDadesUsuari.this, "El iban no te el format correcte.", Toast.LENGTH_SHORT).show();
        }
    }

    //Finalitza i torna a l'activity anterior.
    public void sortir(View view){
        finish();
    }
}
