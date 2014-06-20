package classe;

import java.util.Date;

/**
 * Created by ribardbastien on 29/04/2014.
 */
public class Utilisateur_metier {
    private int id;
    private String login;
    private String password;
    private int idUtilisateurServeur;

    public Utilisateur_metier(){}

    public Utilisateur_metier(int id, String login, String password){
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId_utilisateur() {
        return id;
    }

    public void setId_utilisateur(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdUtilisateurServeur() {
        return idUtilisateurServeur;
    }

    public void setIdUtilisateurServeur(int idUtilisateurServeur) {
        this.idUtilisateurServeur = idUtilisateurServeur;
    }
}
