package ricard.projecte.gestiogimnasandroid;

public class Recycler {

    public String nom,descripcio;
    public final int imageId;

    public Recycler(String nom, String descripcio , int imageId) {
        this.nom = nom;
        this.descripcio=descripcio;
        this.imageId = imageId;
    }

    public String getNom() {
        return nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public int getImageId() {
        return imageId;
    }

}
