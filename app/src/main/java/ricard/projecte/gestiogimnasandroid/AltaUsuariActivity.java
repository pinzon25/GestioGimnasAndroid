package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.firestore.FirebaseFirestore;

public class AltaUsuariActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button BtCancelar, BtAcceptar;
    EditText nom,cognoms,dni,telefon,contrasenya,repeticio,codipostal,poblacio,iban,cuota;
    RadioButton jornadamati, jornadatarda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_usuari);

        BtCancelar= findViewById(R.id.BtCancelar);
        BtAcceptar = findViewById(R.id.BtAcceptarAlta);

        nom=findViewById(R.id.TfNomAlta);
        cognoms =findViewById(R.id.TfCognomsAlta);
        dni =findViewById(R.id.TfDniAlta);
        telefon =findViewById(R.id.TfTelefonAlta);
        contrasenya =findViewById(R.id.TfContrasenyaAlta);
        repeticio =findViewById(R.id.TfRepeticioAlta);
        codipostal =findViewById(R.id.TfCodiPostalAlta);
        poblacio =findViewById(R.id.TfPoblacioAlta);
        iban =findViewById(R.id.TfIbanAlta);
        cuota =findViewById(R.id.TfCuotaAlta);

        jornadamati = findViewById(R.id.RbMatiAlta);
        jornadatarda =findViewById(R.id.RbTardaAlta);


    }
}
