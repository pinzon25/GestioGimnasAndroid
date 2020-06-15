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
    Button modificarDades, veureRutines, veureActivitats;
    TextView benvinguda;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal_usuari);
        modificarDades = findViewById(R.id.BtModificarDades);
        veureRutines = findViewById(R.id.BtRutinesFitness);
        veureActivitats = findViewById(R.id.BtActivitats);
        benvinguda = (TextView)findViewById(R.id.LbBenvingudaClient);
        Modelo.amagaBarraNavegacio(this.getWindow());
        cli = (Client)getIntent().getSerializableExtra("Client");
        try {
        benvinguda.setText("Benvingut: " + cli.getNom());
         }catch(NullPointerException ex){
        ex.getMessage();
        }
    }

    @Override //Quan tornem a la activity de Login, inicialitzem els camps.
    public void onResume(){
        super.onResume();
        Modelo.amagaBarraNavegacio(this.getWindow());
    }

    public void ModificaDadesClient(View view){
        Intent intent = new Intent( VistaPrincipalUsuari.this,ModificaDadesUsuari.class );
        intent.putExtra("ClientObjectiu", cli);
        startActivity(intent);
    }

    public void ActivitatsClient(View view){
        Intent intent = new Intent( VistaPrincipalUsuari.this,ActivitatsUsuari.class );
        intent.putExtra("ClientActivitats", cli);
        startActivity(intent);
    }

    public void RutinesClient(View view){
        Intent intent = new Intent( VistaPrincipalUsuari.this,RutinesUsuari.class );
        intent.putExtra("ClientRutines", cli);
        startActivity(intent);
    }

    public void sortir(View view){
        finish();
    }
}
