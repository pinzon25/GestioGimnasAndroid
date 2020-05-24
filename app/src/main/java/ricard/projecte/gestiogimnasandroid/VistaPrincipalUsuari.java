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
         //cli = (Client) getIntent().getParcelableExtra("Client");
        modificarDades = findViewById(R.id.BtModificarDades);
        veureRutines = findViewById(R.id.BtRutinesFitness);
        veureActivitats = findViewById(R.id.BtActivitats);
        benvinguda = (TextView)findViewById(R.id.LbBenvingudaClient);

        //if(getIntent().getExtras() != null){
            cli = (Client)getIntent().getSerializableExtra("Client");

            benvinguda.setText("Benvingut: " + cli.getNom());
        //}




        modificarDades.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( VistaPrincipalUsuari.this,ModificaDadesUsuari.class );
                intent.putExtra("Client", cli);
                startActivity(intent);
            }
        } );

        veureRutines.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( VistaPrincipalUsuari.this,ModificaDadesUsuari.class );
                intent.putExtra("Client",cli);
                startActivity(intent);
            }
        } );

        veureActivitats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( VistaPrincipalUsuari.this,ModificaDadesUsuari.class );
                intent.putExtra("Client",cli);
                startActivity(intent);
            }
        } );

        //c= getIntent().getParcelableExtra("Client");


        //Log.d("Nom del client",cli.getNom()); //Comprobacio de que s'obtenen els noms dels clients que consten a la base de dades.





    }



}
