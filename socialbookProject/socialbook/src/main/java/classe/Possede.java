package classe;

/**
 * Created by Marine on 29/04/2014.
 */
public class Possede {
    private int id_reseau_social;
    private int id_contact;

    public Possede(){}

    public Possede(int id_reseau_social, int id_contact)
    {
        this.id_reseau_social = id_reseau_social;
        this.id_contact = id_contact;
    }

    public int getId_reseau_social() {
        return id_reseau_social;
    }

    public void setId_reseau_social(int id_reseau_social) {
        this.id_reseau_social = id_reseau_social;
    }

    public int getId_contact() {
        return id_contact;
    }

    public void setId_contact(int id_contact) {
        this.id_contact = id_contact;
    }
}
