package ricard.projecte.gestiogimnasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VistaPrincipalUsuari extends AppCompatActivity {
    Client cli;
    String nomClient="", cognomsClient="",dniClient="",contrasenyaClient="",pobClient="",ibClient="",jornadaClient="",cuotaClient="";
    long telfClient=0;
    int cpClient=0;
    Button modificarDades, veureRutines, veureActivitats;
    TextView benvinguda;
    Bundle bundle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal_usuari);
        modificarDades = findViewById(R.id.BtModificarDades);
        veureRutines = findViewById(R.id.BtRutinesFitness);
        veureActivitats = findViewById(R.id.BtActivitats);
        benvinguda = (TextView)findViewById(R.id.LbBenvingudaClient);

        //if(getIntent().getExtras() != null){
            cli = (Client)getIntent().getSerializableExtra("Client");
        try {
        benvinguda.setText("Benvingut: " + cli.getNom());
         }catch(NullPointerException ex){
        ex.getMessage();
        }

        //}

       /* modificarDades.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( VistaPrincipalUsuari.this,ModificaDadesUsuari.class );
                intent.putExtra("ClientObjectiu", cli);
                startActivity(intent);
            }
        } );*/

        veureRutines.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( VistaPrincipalUsuari.this,ModificaDadesUsuari.class );
                intent.putExtra("ClientRutines",cli);
                startActivity(intent);
            }
        } );

        veureActivitats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( VistaPrincipalUsuari.this,ModificaDadesUsuari.class );
                intent.putExtra("ClientActivitats",cli);
                startActivity(intent);
            }
        } );

    }

    public void enviaClient(View view){
        Intent intent = new Intent( VistaPrincipalUsuari.this,ModificaDadesUsuari.class );
        intent.putExtra("ClientObjectiu", cli);
        startActivity(intent);
    }


}
