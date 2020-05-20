package ricard.projecte.gestiogimnasandroid;

public class Exercici {

    private String nom;
    private String descripcio;
    private String benefici;
    private String dificultat;

    public Exercici(String nom, String descripcio, String benefici, String dificultat) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.benefici = benefici;
        this.dificultat = dificultat;

    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio=descripcio;
    }

    public String getBenefici() {
        return benefici;
    }

    public void setBenefici(String descripcio) {
        this.benefici=descripcio;
    }

    public String getDificultat() {
        return dificultat;
    }

    public void setDificultat(String descripcio) {
        this.dificultat=descripcio;
    }

}
