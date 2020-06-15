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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
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

        //Modelo.amagaBarraNavegacio(this.getWindow());

        registrar.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent( MainActivity.this, AltaUsuari.class);
            startActivity(intent);
            }
        } );

        baixa.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent( MainActivity.this, BaixaUsuari.class);
            startActivity(intent);
            }
        } );

    }

    @Override //Quan tornem a la activity de Login, inicialitzem els camps.
    public void onResume(){
        super.onResume();
        this.inicialitzaCamps();
        //Modelo.amagaBarraNavegacio(this.getWindow());
    }

    public void login(View view) throws Exception {
        if(EtDni.getText() != null && EtContrasenya != null){
            obteClients();
        }
    }

    private void obteClients() throws Exception {

        Task<QuerySnapshot> querySnapshotTask = db.collection("Clients").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean trobat = true;
                String nom="";
                String dni="";
                String contrasenya="";
                String contencriptada="";
                dni = EtDni.getText().toString();
                contrasenya = EtContrasenya.getText().toString();
                contencriptada = EncriptaDesencripta.getMD5(contrasenya);

                if(dni.isEmpty() && contrasenya.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Hi ha camps buits.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(dni.isEmpty() | contrasenya.isEmpty()){
                    Toast.makeText(MainActivity.this, "Dades incompletes.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    boolean existeix = false;
                    for (DocumentSnapshot document : documents) {

                        if (dni.equals(document.getString("Dni")) && contencriptada.equals(document.getString("Contrasenya"))) {
                            nom = document.getString("Nom");

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
                            enviaClientNovaActivitat(client);
                            existeix=true;
                            break;
                        }else{
                            existeix=false;
                        }
                    }
                    if(existeix == false){
                        Toast.makeText(MainActivity.this, "L'usuari no existeix o no s'ha trobat.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }

    private void enviaClientNovaActivitat(Client c){
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

    private void inicialitzaCamps(){
        EtDni.setText("");
        EtContrasenya.setText("");
    }

}
