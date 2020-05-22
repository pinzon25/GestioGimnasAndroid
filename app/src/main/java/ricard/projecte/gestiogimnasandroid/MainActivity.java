package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Client client = null;
    ArrayList<Client> llistaClients = new ArrayList<>();
    FirebaseInitialize fire = new FirebaseInitialize();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button entrar, baixa,registrar;
    EditText EtDni, EtContrasenya;

    public MainActivity() throws IOException {
    }

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






    }
}
