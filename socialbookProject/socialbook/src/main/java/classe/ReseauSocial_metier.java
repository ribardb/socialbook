package classe;

/**
 * Created by Marine on 29/04/2014.
 */
public class ReseauSocial_metier {
    private int id_reseau_social;
    private String type;
    private String pseudo;
    private int id_contact_reseau_social;

    public ReseauSocial_metier(){}

    public ReseauSocial_metier(int id_reseau_social, String type, String pseudo)
    {
        this.id_reseau_social = id_reseau_social;
        this.type = type;
        this.pseudo = pseudo;
    }

    public int getId_reseau_social() {
        return id_reseau_social;
    }

    public void setId_reseau_social(int id_reseau_social) {
        this.id_reseau_social = id_reseau_social;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getId_contact_reseau_social() {
        return id_contact_reseau_social;
    }

    public void setId_contact_reseau_social(int id_contact_reseau_social) {
        this.id_contact_reseau_social = id_contact_reseau_social;
    }
}
