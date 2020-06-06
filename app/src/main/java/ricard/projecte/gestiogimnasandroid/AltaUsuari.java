package ricard.projecte.gestiogimnasandroid;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AltaUsuari extends AppCompatActivity {
    Client client;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button BtCancelar, BtAcceptar;
    EditText nom, cognoms, dni, telefon, contrasenya, repeticio, codipostal, poblacio, iban, cuota;
    RadioButton jornadamati, jornadatarda;
    String jornadaacces = "";
    float cuot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_usuari);

        nom = findViewById(R.id.TfNomAlta);
        cognoms = findViewById(R.id.TfCognomsAlta);
        dni = findViewById(R.id.TfDniAlta);
        telefon = findViewById(R.id.TfTelefonAlta);
        contrasenya = findViewById(R.id.TfContrasenya);
        repeticio = findViewById(R.id.TfRepeticioAlta);
        codipostal = findViewById(R.id.TfCodiPostalAlta);
        poblacio = findViewById(R.id.TfPoblacioAlta);
        iban = findViewById(R.id.TfIbanAlta);
        cuota = findViewById(R.id.TfCuotaAlta);

        jornadamati = findViewById(R.id.RbMatiAlta);
        jornadatarda = findViewById(R.id.RbTardaAlta);

        BtCancelar = findViewById(R.id.BtCancelar);
        BtAcceptar = findViewById(R.id.BtAcceptarAlta);


            jornadamati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    jornadaacces = "Mati";
                    cuot = (float) 30.50;
                    cuota.setText(String.valueOf(cuot));
                    jornadatarda.setChecked(false);
                }
            });


            jornadatarda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    jornadaacces = "Tarda";
                    cuot = (float) 40.50;
                    cuota.setText(String.valueOf(cuot));
                    jornadamati.setChecked(false);
                }
            });


        BtAcceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nom.getText().toString().isEmpty() && cognoms.getText().toString().isEmpty() && dni.getText().toString().isEmpty() && telefon.getText().toString().isEmpty() && contrasenya.getText().toString().isEmpty() && repeticio.getText().toString().isEmpty() && codipostal.getText().toString().isEmpty() && poblacio.getText().toString().isEmpty() && iban.getText().toString().isEmpty() && cuota.getText().toString().isEmpty()) {
                    Toast.makeText(AltaUsuari.this, "El formulari esta buit!", Toast.LENGTH_SHORT).show();
                } else {
                    //try {
                    try {
                        guardaUsuari(nom.getText().toString(), cognoms.getText().toString(), dni.getText().toString(), Long.parseLong(telefon.getText().toString()), contrasenya.getText().toString(), repeticio.getText().toString(), Integer.parseInt(codipostal.getText().toString()), poblacio.getText().toString(), iban.getText().toString(), jornadaacces, Float.parseFloat(cuota.getText().toString()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NumberFormatException es) {
                        Toast.makeText(AltaUsuari.this, "Tipus de dades erroni!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void guardaUsuari(String nomm, String cognomsi, String dnii, long telef, String contraseny, String rep, int codipost, String poblac, String ibann, String jornadaacces, float cuot) throws UnsupportedEncodingException {
        if (comprobacions(nomm, cognomsi, dnii, telef, contraseny, rep, codipost, poblac, ibann)) {
            creaUsuari(nomm, cognomsi, dnii, String.valueOf(telef), contraseny, codipost, poblac, ibann, jornadaacces, cuot);
            pujaUsuari();
       } else {
            Toast.makeText(AltaUsuari.this, "Una o varies comprobacions han retornat false.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean comprobacions(String n, String c, String d, long t, String con, String r, int cp,String pob, String ib){
        boolean verificat=false;

        if(Modelo.comprobaNom(n)) {
            verificat=true;
        }
        if(Modelo.comprobaCognom(c)){
            verificat=true;
        }
        if(Modelo.comprobaDni(d)) {
            verificat=true;
        }
        if(Modelo.comprobaTelefon(String.valueOf(t))){
            verificat=true;
        }
        if(Modelo.comprobaContrasenya(con,r)) {
            verificat=true;
        }
        if(Modelo.comprobaCodiPostal(String.valueOf(cp))) {
            verificat=true;
        }
        if(Modelo.comprobaPoblacio(pob)) {
            verificat=true;
        }
        if(Modelo.comprobaCompteBancari(ib)) {
            verificat=true;
        }
        return verificat;
    }

    private void pujaUsuari() {

        Map<String, Object> nomclient = new HashMap<>();
        nomclient.put("Nom", client.getNom());

        Map<String, Object> data = new HashMap<>();
        data.put("Nom", client.getNom());
        data.put("Cognoms", client.getCognoms());
        data.put("Dni", client.getDni());
        data.put("Telefon", client.getTelefon());
        data.put("Contrasenya", client.getContrassenya());
        data.put("Codi Postal", client.getCodiPostal());
        data.put("Poblacio", client.getPoblacio());
        data.put("Compte pagament", client.getComptePagament());
        data.put("Jornada acces", client.getJornadaAcces());
        data.put("Cuota", client.getCuota());

        db.collection("Clients").document(client.getNom()).set(data);

        db.collection("Clients").document(client.getNom()).set(data).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(AltaUsuari.this, "Dades guardades", Toast.LENGTH_SHORT);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AltaUsuari.this, "No s'ha pogut guardar les dades.", Toast.LENGTH_SHORT);
            }
        });

        inicialitzaCamps();
    }


    private void creaUsuari(String nom, String cognoms, String dni, String telefon, String contrasenya, int codi_postal, String poblacio, String comptePagament, String jornadaAcces, float cuota) throws UnsupportedEncodingException {
        String contra = "";
        long telf;
        telf = Long.parseLong(telefon);
        contra = EncriptaDesencripta.getMD5(contrasenya);
        client = new Client();
        client.setNom(nom);
        client.setCognoms(cognoms);
        client.setDni(dni);
        client.setTelefon(telf);
        client.setContrassenya(contra);
        client.setCodiPostal(codi_postal);
        client.setPoblacio(poblacio);
        client.setComptePagament(comptePagament);
        client.setJornadaAcces(jornadaAcces);
        client.setCuota(cuota);
    }



        public void inicialitzaCamps(){
            nom.setText("");
            cognoms.setText("");
            dni.setText("");
            telefon.setText("");
            contrasenya.setText("");
            repeticio.setText("");
            codipostal.setText("");
            poblacio.setText("");
            iban.setText("");
            cuota.setText("");

            jornadamati.setChecked(false);
            jornadatarda.setChecked(false);
            ocultaTeclat();
        }

        public void ocultaTeclat(){
            View view = this.getCurrentFocus();
            if(view != null){
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);

            }
        }

        public void sortir(View view){
        finish();
        }


    }

