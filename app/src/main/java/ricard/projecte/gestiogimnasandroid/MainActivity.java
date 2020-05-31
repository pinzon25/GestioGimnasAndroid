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
    //public Client client;
    Button entrar, baixa,registrar;
    EditText EtDni, EtContrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EtDni = findViewById(R.id.EtDni);
        EtContrasenya = findViewById(R.id.EtContrasenya);

        entrar=findViewById(R.id.BtEntrar);
        baixa=findViewById(R.id.BtBaixaUsuari);
        registrar=findViewById(R.id.BtRegistre);


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

    }

    public void login(View view) throws Exception {
        if(EtDni.getText() != null && EtContrasenya != null){
            obteClients();

        }else{
            Toast.makeText(MainActivity.this, "Verifiqui el format de les dades!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void obteClients() throws Exception {

        Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String nom="";
                String dni="";
                String contrasenya="";
                String contencriptada="";
                dni = EtDni.getText().toString();
                contrasenya = EtContrasenya.getText().toString();
                contencriptada = EncriptaDesencripta.getMD5(contrasenya);

                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    /*Log.d("dni introduit",dni);
                    Log.d("contrasenya introduida",contrasenya);
                    Log.d("contrasenya introduida encriptada",contencriptada);
                    Log.d("contrasenya bbdd",document.getString("Contrasenya"));*/

                    if (dni.equals(document.getString("Dni")) && contencriptada.equals(document.getString("Contrasenya"))) {
                        nom = document.getString("Nom");
                        //Log.d("Nom del client dintre del for de documents",nom); //Comprobacio de que s'obtenen els noms dels clients que consten a la base de dades.

                        Client client = new Client();
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
                        //mostraclients(client);//(DEBUG).
                        enviaClientNovaActivitat(client);

                    }
                }
            }
        });
    }

    //Metode per comprobar que tots els clients s'obtenen correctament.(Debug)
    public void mostraclients(Client c){
        Log.d("nom client",c.getNom());
        Log.d("cognom client",c.getCognoms());
        Log.d("dni client",c.getDni());
        Log.d("telefon client",String.valueOf(c.getTelefon()));
        Log.d("contrasenya client",c.getContrassenya());
        Log.d("codi postal client",String.valueOf(c.getCodiPostal()));
        Log.d("poblacio client",c.getPoblacio());
        Log.d("iban client",c.getComptePagament());
        Log.d("jornada client",c.getJornadaAcces());
        Log.d("cuota client",String.valueOf(c.getCuota()));
    }

    public void enviaClientNovaActivitat(Client c){
        try {
            Intent intent = new Intent(MainActivity.this, VistaPrincipalUsuari.class);
            intent.putExtra("Client", c);
            startActivity(intent);
            //inicialitzaCamps();
        }catch(NullPointerException ex){
            ex.printStackTrace();
            ex.getMessage();
        }
    }

    public void inicialitzaCamps(){
        EtDni.setText("");
        EtContrasenya.setText("");
    }

}
