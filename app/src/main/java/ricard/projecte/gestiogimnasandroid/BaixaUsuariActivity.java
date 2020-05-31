package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BaixaUsuariActivity extends AppCompatActivity {
    public EditText dniBaixa,contrasenyaBaixa;
    public Button cancelarBaixa,aceptarBaixa;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baixa_usuari);

        dniBaixa = findViewById(R.id.TfDniAlta);
        contrasenyaBaixa = findViewById(R.id.TfContrasenya);
        cancelarBaixa = findViewById(R.id.BtCancelar);
        aceptarBaixa = findViewById(R.id.BtFinalitzar);



        aceptarBaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    comprobaCamps();
                    realitzaBaixa();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cancelarBaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean comprobaCamps() {
        boolean correcte = true;
        String dni = dniBaixa.getText().toString();
        String contrassenya = contrasenyaBaixa.getText().toString();

        if (dni.isEmpty() && !contrassenya.isEmpty()) {
            Toast.makeText(BaixaUsuariActivity.this, "Camp DNI buit!",
                    Toast.LENGTH_SHORT).show();
            correcte = false;
        }

        if (!dni.isEmpty() && contrassenya.isEmpty()) {
            Toast.makeText(BaixaUsuariActivity.this, "Camp Contrassenya buit!",
                    Toast.LENGTH_SHORT).show();
            correcte = false;
        }

        if (dni.isEmpty() && contrassenya.isEmpty()) {
            Toast.makeText(BaixaUsuariActivity.this, "No s'han introduit les dades!",
                    Toast.LENGTH_SHORT).show();
            correcte = false;
        }

        if (Modelo.comprobaDni(dni) == false) {
            Toast.makeText(BaixaUsuariActivity.this, "El dni no te el format correcte.",
                    Toast.LENGTH_SHORT).show();
            correcte = false;
        }

        return correcte;
    }

    //Introduim el dni i la contrassenya del client/usuari, si coincideixen amb algun document dels clients, n'agafem el nom i l'esborrem.
    public void realitzaBaixa() throws Exception {
    Client client;
        final String dnii;
        final String contrasenyaa;
        dnii = dniBaixa.getText().toString();
        contrasenyaa = EncriptaDesencripta.getMD5(contrasenyaBaixa.getText().toString());

        Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    String nom="";
                    Log.d("Nom del client",nom); //Comprobacio de que s'obtenen els noms dels clients que consten a la base de dades.
                    if (dnii.equals(document.getString("Dni")) && contrasenyaa.equals(document.getString("Contrasenya"))) {
                        nom = document.getString("Nom");

                        try {
                            db.collection("Clients").document(nom).delete();
                            Toast.makeText(BaixaUsuariActivity.this, "Baixa realitzada!",
                                    Toast.LENGTH_SHORT).show();
                        } catch (IllegalArgumentException ex) {
                            Toast.makeText(BaixaUsuariActivity.this, "L'usuari no figura a la base de dades.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        break;
                    }else{
                        Toast.makeText(BaixaUsuariActivity.this, "L'usuari no figura a la base de dades.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



}
