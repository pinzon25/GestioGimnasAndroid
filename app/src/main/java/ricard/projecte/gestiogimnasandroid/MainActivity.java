package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Client client;
    Button entrar, baixa,registrar;
    EditText EtDni, EtContrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrar=findViewById(R.id.BtEntrar);
        baixa=findViewById(R.id.BtBaixaUsuari);
        registrar=findViewById(R.id.BtRegistre);

        EtDni = findViewById(R.id.EtDni);
        EtContrasenya = findViewById(R.id.EtContrasenya);


        registrar.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent( MainActivity.this, AltaUsuariActivity.class);
            startActivity(intent);
            }
        } );

        baixa.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent( MainActivity.this, BaixaUsuariActivity.class);
            startActivity(intent);
            }
        } );

        entrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    obteClients();
                    Intent intent = new Intent( MainActivity.this, VistaPrincipalUsuari.class);
                    intent.putExtra("Client",client);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } );




    }

    public void obteClients() throws Exception {

        final String dni;
        final String contrasenya;
        final String[] nomm = {""};
        dni = EtDni.getText().toString();
        contrasenya = EncriptaDesencripta.getMD5(EtContrasenya.getText().toString());

        Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    //String nom="";
                    //Log.d("Nom del client",nom); //Comprobacio de que s'obtenen els noms dels clients que consten a la base de dades.
                    if (dni.equals(document.getString("Dni")) && contrasenya.equals(document.getString("Contrasenya"))) {
                        //nom = document.getString("Nom");
                        client = new Client();
                        client.setNom(document.getString("Nom"));
                        client.setCognoms(document.getString("Cognoms"));
                        client.setDni(document.getString("Dni"));
                        client.setTelefon(document.getLong("Telefon"));
                        client.setContrassenya(document.getString("Contrasenya"));
                        client.setCodiPostal(document.getLong("Codi Postal").intValue());
                        client.setPoblacio(document.getString("Poblacio"));
                        client.setComptePagament(document.getString("Compte pagament"));
                        client.setJornadaAcces(document.getString("Jornada acces"));
                        client.setCuota(document.getLong("Cuota").floatValue());
                        break;
                    }

                }
            }
        });
    }
}
