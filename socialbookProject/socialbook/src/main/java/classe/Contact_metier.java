package classe;

import java.util.Date;

/**
 * Created by ribardbastien on 29/04/2014.
 */
public class Contact_metier {
    private int id_contact;
    private int id_utilisateur;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String cp;
    private String ville;
    private double longitude;
    private double latitude;
    private String date_naissance;
    private int id_contact_serveur;

    public Contact_metier(){}

    public Contact_metier(int id_contact, int id_utilisateur, String nom, String prenom, String telephone, String adresse, String cp, String ville, String date_naissance){
        this.id_contact = id_contact;
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.cp = cp;
        this.ville= ville;
        this.date_naissance = date_naissance;
        this.longitude=0;
        this.latitude=0;
    }
    public Contact_metier( int id_utilisateur, String nom, String prenom, String telephone, String adresse, String cp, String ville, String date_naissance){
        this.id_contact = id_contact;
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.cp = cp;
        this.ville= ville;
        this.date_naissance = date_naissance;
        this.longitude=0;
        this.latitude=0;
    }

    public int getId_contact() {
        return id_contact;
    }

    public void setId_contact(int id_contact) {
        this.id_contact = id_contact;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public void setId_contact_serveur(int id_contact_serveur){
        this.id_contact_serveur = id_contact_serveur;
    }

    public int getId_contact_serveur() {
        return this.id_contact_serveur;
    }

    public boolean equals(Contact_metier contact_metier)
    {
        boolean result = false;

      /*  if((contact_metier.getNom().equals(this.nom) &&
                contact_metier.getPrenom().equals(this.prenom) &&
                contact_metier.getTelephone().equals(this.telephone)) == true )
        {
            result = true;
        }
*/
        return  result;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
