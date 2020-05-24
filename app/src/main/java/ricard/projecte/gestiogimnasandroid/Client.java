package ricard.projecte.gestiogimnasandroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Client implements Serializable {

    private int idClient;
    private String nom;
    private String cognoms;
    private String dni;
    private String contrassenya;
    private long telefon;
    private String poblacio;
    private int codiPostal;
    private String jornadaAcces;
    private String comptePagament;
    private float cuota;

    public Client() {
        idClient = 0;
        nom = "";
        cognoms = "";
        dni = "";
        contrassenya = "";
        telefon = 0;
        codiPostal = 0;
        poblacio = null;
        comptePagament = "";
        jornadaAcces = "";
        cuota = 0;
    }

    public Client(int idClient, String nom, String cognoms, String dni, String contrassenya, long telefon, String poblacio, int codiPostal, String comptePagament, String jornadaAcces, float cuota) {
        this.idClient = idClient;
        this.nom = nom;
        this.cognoms = cognoms;
        this.dni = dni;
        this.contrassenya = contrassenya;
        this.telefon = telefon;
        this.codiPostal = codiPostal;
        this.poblacio = poblacio;
        this.comptePagament = comptePagament;
        this.jornadaAcces = jornadaAcces;
        this.cuota = cuota;
    }

    protected Client(Parcel in) {
        idClient = in.readInt();
        nom = in.readString();
        cognoms = in.readString();
        dni = in.readString();
        contrassenya = in.readString();
        telefon = in.readLong();
        poblacio = in.readString();
        codiPostal = in.readInt();
        jornadaAcces = in.readString();
        comptePagament = in.readString();
        cuota = in.readFloat();
    }



    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrassenya() {
        return contrassenya;
    }

    public void setContrassenya(String contrassenya) {
        this.contrassenya = contrassenya;
    }

    public long getTelefon() {
        return telefon;
    }

    public void setTelefon(long telefon) {
        this.telefon = telefon;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public int getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(int codiPostal) {
        this.codiPostal = codiPostal;
    }

    public String getJornadaAcces() {
        return jornadaAcces;
    }

    public void setJornadaAcces(String jornadaAcces) {
        this.jornadaAcces = jornadaAcces;
    }

    public String getComptePagament() {
        return comptePagament;
    }

    public void setComptePagament(String comptePagament) {
        this.comptePagament = comptePagament;
    }

    public float getCuota() {
        return cuota;
    }

    public void setCuota(float cuota) {
        this.cuota = cuota;
    }


}
