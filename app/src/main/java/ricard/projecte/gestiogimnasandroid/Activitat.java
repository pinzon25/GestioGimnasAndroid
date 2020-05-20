package ricard.projecte.gestiogimnasandroid;

import android.util.FloatProperty;

public class Activitat {

    private Integer idActivitat;
    private String nom;
    private String descripcio;
    private Float suplement;


    public Activitat(int idActivitat, String nom, String descripcio, float suplement){
        this.idActivitat = idActivitat;
        this.nom = nom;
        this.descripcio = descripcio;
        this.suplement = suplement;
    }

    public Integer getIdActivitat() {
        return idActivitat;
    }

    public void setIdActivitat(Integer idActivitat) {
        this.idActivitat = idActivitat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Float getSuplement() {
        return suplement;
    }

    public void setSuplement(Float suplement) {
        this.suplement = suplement;
    }
}
