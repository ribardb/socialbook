package classe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ribardbastien on 29/04/2014.
 */
public class SocialbookBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "socialbook.db";

    /****************************************************************
    CONTACTS
     ***************************************************************/

    private static final String TABLE_CONTACTS = "table_contacts";
    private static final String COL_ID_CONTACT = "ID_CONTACT";
    private static final int NUM_COL_ID_CONTACT = 0;
    private static final String COL_ID_UTILISATEUR = "ID_UTILISATEUR";
    private static final int NUM_COL_ID_UTILISATEUR = 1;
    private static final String COL_NOM = "NOM";
    private static final int NUM_COL_NOM = 2;
    private static final String COL_PRENOM = "PRENOM";
    private static final int NUM_COL_PRENOM = 3;
    private static final String COL_TELEPHONE = "TELEPHONE";
    private static final int NUM_COL_TELEPHONE = 4;
    private static final String COL_ADRESSE = "ADRESSE";
    private static final int NUM_COL_ADRESSE = 5;
    private static final String COL_CP = "CP";
    private static final int NUM_COL_CP = 6;
    private static final String COL_VILLE = "VILLE";
    private static final int NUM_COL_VILLE = 7;
    private static final String COL_DATE_NAISSANCE = "DATE_NAISSANCE";
    private static final int NUM_COL_DATE_NAISSANCE = 8;
    private static final String COL_ID_CONTACT_SERVEUR = "ID_CONTACT_SERVEUR";
    private static final int NUM_COL_ID_CONTACT_SERVEUR = 9;
    private static final String COL_LONGITUDE = "LONGITUDE";
    private static final int NUM_COL_LONGITUDE = 10;
    private static final String COL_LATITUDE = "LATITUDE";
    private static final int NUM_COL_LATITUDE = 11;

    /****************************************************************
     UTILISATEURS
     ***************************************************************/

    private static final String TABLE_UTILISATEURS = "table_utilisateurs";
    private static final String COL_ID_USER = "ID";
    private static final int NUM_COL_ID_USER = 0;
    private static final String COL_LOGIN = "LOGIN";
    private static final int NUM_COL_LOGIN = 1;
    private static final String COL_PASSWORD = "PASSWORD";
    private static final int NUM_COL_PASSWORD = 2;
    private static final String COL_ID_UTILISATEUR_SERVEUR = "ID_UTILISATEUR_SERVEUR";
    private static final int NUM_COL_ID_UTILISATEUR_SERVEUR = 3;

    /****************************************************************
     RESEAUX SOCIAUX
     ***************************************************************/
    private static final String TABLE_RESEAUX_SOCIAUX = "table_reseaux_sociaux";
    private static final String COL_ID_RESEAU_SOCIAL = "ID_RESEAU_SOCIAL";
    private static final int NUM_COL_ID_RESEAU_SOCIAL = 0;
    private static final String COL_PSEUDO = "PSEUDO";
    private static final int NUM_COL_PSEUDO = 1;
    private static final String COL_TYPE_RESEAU_SOCIAL = "TYPE_RESEAU_SOCIAL";
    private static final int NUM_COL_TYPE_RESEAU_SOCIAL = 2;
    private static final String COL_ID_CONTACT_RESEAUX = COL_ID_CONTACT;
    private static final int NUM_COL_ID_CONTACT_RESEAUX = 3;


    /****************************************************************
     POSSEDE
     ***************************************************************/
    private static final String TABLE_POSSEDE = "table_Possede";
    private static final String COL_ID_RESEAU_SOCIAL_POSSEDE = "ID_RESEAU_SOCIAL";
    private static final int NUM_COL_ID_RESEAU_SOCIAL_POSSEDE = 0;
    private static final String COL_ID_CONTACT_POSSEDE = "PSEUDO";
    private static final int NUM_COL_ID_CONTACT_POSSEDE = 1;


    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public SocialbookBDD(Context context){
        //On créer la SocialbookBDD et sa table
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la SocialbookBDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la SocialbookBDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    /****************************************************************
     CONTACTS
     ***************************************************************/

    public long insertContact(Contact_metier contact){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ID_UTILISATEUR, contact.getId_utilisateur());
        values.put(COL_NOM, contact.getNom());
        values.put(COL_PRENOM, contact.getPrenom());
        values.put(COL_TELEPHONE, contact.getTelephone());
        values.put(COL_ADRESSE, contact.getAdresse());
        values.put(COL_CP, contact.getCp());
        values.put(COL_VILLE, contact.getVille());
        values.put(COL_DATE_NAISSANCE, contact.getDate_naissance());
        values.put(COL_ID_CONTACT_SERVEUR, contact.getId_contact_serveur());
        values.put(COL_LONGITUDE, contact.getLongitude());
        values.put(COL_LATITUDE, contact.getLatitude());
        //on insère l'objet dans la SocialbookBDD via le ContentValues
        Log.d("Ajout Contact", values.toString());

        List<Contact_metier> contact_metierList = new LinkedList<Contact_metier>();
        contact_metierList = getContactUtils(contact.getId_utilisateur());

        boolean trouve = false;
        if(contact_metierList.size()>0) {
            int i = 0;
            while (contact_metierList.size()>i && trouve==false)
            {
                trouve = (contact_metierList.get(i).equals(contact));
                i++;
            }
        }
            if(trouve==false) {
                return bdd.insert(TABLE_CONTACTS, null, values);
            }
            else
            {return 0;}

    }

    public int updateContact(int id_contact, Contact_metier contact){
        //La mise à jour d'un contact dans la SocialbookBDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle contact on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NOM, contact.getNom());
        values.put(COL_PRENOM, contact.getPrenom());
        values.put(COL_TELEPHONE, contact.getTelephone());
        values.put(COL_ADRESSE, contact.getAdresse());
        values.put(COL_CP, contact.getCp());
        values.put(COL_VILLE, contact.getVille());
        values.put(COL_DATE_NAISSANCE, String.valueOf(contact.getDate_naissance()));
        values.put(COL_LONGITUDE, contact.getLongitude());
        values.put(COL_LATITUDE, contact.getLatitude());
        return bdd.update(TABLE_CONTACTS, values, COL_ID_CONTACT + " = " +id_contact, null);
    }

    public int removeContactWithID(int id){
        //Suppression d'un contact de la SocialbookBDD grâce à l'ID
        return bdd.delete(TABLE_CONTACTS, COL_ID_CONTACT + " = " +id, null);
    }

    public List<Contact_metier> getContact(){
        //Récupère dans un Cursor les valeur correspondant à un contact contenu dans la SocialbookBDD (ici on sélectionne le contact grâce à son titre)
        Cursor c = bdd.query(TABLE_CONTACTS, new String[] {COL_ID_CONTACT, COL_ID_UTILISATEUR, COL_NOM, COL_PRENOM, COL_TELEPHONE, COL_ADRESSE, COL_CP, COL_VILLE, COL_DATE_NAISSANCE, COL_ID_CONTACT_SERVEUR, COL_LATITUDE, COL_LONGITUDE}
                , null, null, null, null, null);
        c.moveToFirst();
        List<Contact_metier> listContact = new LinkedList<Contact_metier>();
        while(!c.isAfterLast()){
            Contact_metier contact = new Contact_metier();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            contact.setId_contact(c.getInt(NUM_COL_ID_CONTACT));
            contact.setId_utilisateur(c.getInt(NUM_COL_ID_UTILISATEUR));
            contact.setNom(c.getString(NUM_COL_NOM));
            contact.setPrenom(c.getString(NUM_COL_PRENOM));
            contact.setTelephone(c.getString(NUM_COL_TELEPHONE));
            contact.setAdresse(c.getString(NUM_COL_ADRESSE));
            contact.setCp(c.getString(NUM_COL_CP));
            contact.setVille(c.getString(NUM_COL_VILLE));
            contact.setDate_naissance(c.getString(NUM_COL_DATE_NAISSANCE));
            contact.setId_contact_serveur(c.getInt(NUM_COL_ID_CONTACT_SERVEUR));
            contact.setLatitude(c.getDouble(NUM_COL_LATITUDE));
            contact.setLongitude(c.getDouble(NUM_COL_LONGITUDE));
            listContact.add(contact);
            c.moveToNext();
        }
        c.close();
        return listContact;
    }

    public List<Contact_metier> getContactUtils(int idu){
        //Récupère dans un Cursor les valeur correspondant à un contact contenu dans la SocialbookBDD (ici on sélectionne le contact grâce à son titre)
        Cursor c = bdd.query(TABLE_CONTACTS, new String[] {COL_ID_CONTACT, COL_ID_UTILISATEUR, COL_NOM, COL_PRENOM, COL_TELEPHONE, COL_ADRESSE, COL_CP, COL_VILLE, COL_DATE_NAISSANCE, COL_ID_CONTACT_SERVEUR,COL_LONGITUDE,COL_LATITUDE}
                , COL_ID_UTILISATEUR +" = " + idu, null, null, null, null);
        c.moveToFirst();
        List<Contact_metier> listContact = new LinkedList<Contact_metier>();
        while(!c.isAfterLast()){
            Contact_metier contact = new Contact_metier();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            contact.setId_contact(c.getInt(NUM_COL_ID_CONTACT));
            contact.setId_utilisateur(c.getInt(NUM_COL_ID_UTILISATEUR));
            contact.setNom(c.getString(NUM_COL_NOM));
            contact.setPrenom(c.getString(NUM_COL_PRENOM));
            contact.setTelephone(c.getString(NUM_COL_TELEPHONE));
            contact.setAdresse(c.getString(NUM_COL_ADRESSE));
            contact.setCp(c.getString(NUM_COL_CP));
            contact.setVille(c.getString(NUM_COL_VILLE));
            contact.setDate_naissance(c.getString(NUM_COL_DATE_NAISSANCE));
            contact.setId_contact_serveur(c.getInt(NUM_COL_ID_CONTACT_SERVEUR));
            contact.setLatitude(c.getDouble(NUM_COL_LATITUDE));
            contact.setLongitude(c.getDouble(NUM_COL_LONGITUDE));
            listContact.add(contact);
            c.moveToNext();
        }
        c.close();
        return listContact;
    }

    public Contact_metier getContactWithID(int id){
        //Récupère dans un Cursor les valeur correspondant à un contact contenu dans la SocialbookBDD (ici on sélectionne le contact grâce à son titre)
        Cursor c = bdd.query(TABLE_CONTACTS, new String[] {COL_ID_CONTACT, COL_ID_UTILISATEUR, COL_NOM, COL_PRENOM, COL_TELEPHONE, COL_ADRESSE, COL_CP, COL_VILLE, COL_DATE_NAISSANCE, COL_ID_CONTACT_SERVEUR, COL_LONGITUDE,COL_LATITUDE}
        , COL_ID_CONTACT +" = " + id, null, null, null, null);
        return cursorToContact(c);
    }

    //Cette méthode permet de convertir un cursor en un contact
    private Contact_metier cursorToContact(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un contact
        Contact_metier contact = new Contact_metier();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        contact.setId_contact(c.getInt(NUM_COL_ID_CONTACT));
        contact.setId_utilisateur(c.getInt(NUM_COL_ID_UTILISATEUR));
        contact.setNom(c.getString(NUM_COL_NOM));
        contact.setPrenom(c.getString(NUM_COL_PRENOM));
        contact.setTelephone(c.getString(NUM_COL_TELEPHONE));
        contact.setAdresse(c.getString(NUM_COL_ADRESSE));
        contact.setCp(c.getString(NUM_COL_CP));
        contact.setVille(c.getString(NUM_COL_VILLE));
        contact.setDate_naissance(c.getString(NUM_COL_DATE_NAISSANCE));
        contact.setId_contact_serveur(c.getInt(NUM_COL_ID_CONTACT_SERVEUR));
        contact.setLatitude(c.getDouble(NUM_COL_LATITUDE));
        contact.setLongitude(c.getDouble(NUM_COL_LONGITUDE));

        //On ferme le cursor
        c.close();

        //On retourne le contact
        return contact;
    }

    /****************************************************************
     UTILISATEURS
     ***************************************************************/

    public long insertUtilisateur(Utilisateur_metier utilisateur){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_LOGIN, utilisateur.getLogin());
        values.put(COL_PASSWORD, utilisateur.getPassword());
        values.put(COL_ID_UTILISATEUR_SERVEUR, utilisateur.getIdUtilisateurServeur());
        //on insère l'objet dans la SocialbookBDD via le ContentValues
       Utilisateur_metier utilisateur_metierList = getUtilisateur();
        if(utilisateur_metierList != null)
        {
            bdd.delete(TABLE_UTILISATEURS, null, null);
            bdd.delete(TABLE_RESEAUX_SOCIAUX, null, null);
            bdd.delete(TABLE_CONTACTS, null, null);
            bdd.delete(TABLE_POSSEDE, null, null);
        }
        return bdd.insert(TABLE_UTILISATEURS, null, values);
    }

    public int updateUtilisateur(int id_utilisateur, Utilisateur_metier utilisateur){
        //La mise à jour d'un livre dans la SocialbookBDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_LOGIN, utilisateur.getLogin());
        values.put(COL_PASSWORD, utilisateur.getPassword());
        return bdd.update(TABLE_UTILISATEURS, values, COL_ID_USER + " = " +id_utilisateur, null);
    }

    public int removeUtilisateurWithID(int id){
        //Suppression d'un livre de la SocialbookBDD grâce à l'ID
        return bdd.delete(TABLE_UTILISATEURS, COL_ID_USER + " = " +id, null);
    }

    public Utilisateur_metier getUtilisateur(){
        Log.e("test util :", "get Utils");
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la SocialbookBDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_UTILISATEURS, new String[] {COL_ID_USER, COL_LOGIN, COL_PASSWORD,COL_ID_UTILISATEUR_SERVEUR}
                , null, null, null, null, null);
        Log.e("test util :", c.toString());
       return cursorToUtilisateur(c);

    }

    public Utilisateur_metier getUtilisateurWithID(int id){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la SocialbookBDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_UTILISATEURS, new String[] {COL_ID_USER, COL_LOGIN, COL_PASSWORD}
                , COL_ID_USER +"="+ id, null, null, null, null);
        return cursorToUtilisateur(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Utilisateur_metier cursorToUtilisateur(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Utilisateur_metier utilisateur = new Utilisateur_metier();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        utilisateur.setId_utilisateur(c.getInt(NUM_COL_ID_USER));
        utilisateur.setLogin(c.getString(NUM_COL_LOGIN));
        utilisateur.setPassword(c.getString(NUM_COL_PASSWORD));
        utilisateur.setIdUtilisateurServeur(c.getInt(NUM_COL_ID_UTILISATEUR_SERVEUR));
        Log.i("Utilisateur id util :", ""+utilisateur.getId_utilisateur());

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return utilisateur;
    }

    /****************************************************************
     Reseau social
     ***************************************************************/

    public long insertReseauxSocial(ReseauSocial_metier reseauSocial_metier){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        values.put(COL_TYPE_RESEAU_SOCIAL, reseauSocial_metier.getType());
        values.put(COL_PSEUDO, reseauSocial_metier.getPseudo());
        values.put(COL_ID_CONTACT_RESEAUX, reseauSocial_metier.getId_contact_reseau_social());
        //on insère l'objet dans la SocialbookBDD via le ContentValues
        Log.d("Ajout Reseau social", values.toString());
        return bdd.insert(TABLE_RESEAUX_SOCIAUX, null, values);

    }

    public int updateReseauxSocial(int idr, ReseauSocial_metier reseauSocial_metier)
    {
        //La mise à jour d'un livre dans la SocialbookBDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_PSEUDO, reseauSocial_metier.getPseudo());
        return bdd.update(TABLE_RESEAUX_SOCIAUX, values, COL_ID_USER + " = " +idr, null);
    }

    public int removeReseauxSociauxWithID(int id){
        //Suppression d'un livre de la SocialbookBDD grâce à l'ID
        return bdd.delete(TABLE_RESEAUX_SOCIAUX, COL_ID_USER + " = " +id, null);
    }

    public List<ReseauSocial_metier> getReseauxSociaux(int idc){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la SocialbookBDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_RESEAUX_SOCIAUX, new String[] {COL_ID_RESEAU_SOCIAL, COL_PSEUDO, COL_TYPE_RESEAU_SOCIAL, COL_ID_CONTACT_RESEAUX}
                , COL_ID_CONTACT_RESEAUX +"="+ idc, null, null, null, null);
        c.moveToFirst();
        List<ReseauSocial_metier> listReseauxSociaux = new LinkedList<ReseauSocial_metier>();
        while(!c.isAfterLast()){
            ReseauSocial_metier reseauSocial_metier = new ReseauSocial_metier();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            reseauSocial_metier.setId_reseau_social(c.getInt(NUM_COL_ID_RESEAU_SOCIAL));
            reseauSocial_metier.setPseudo(c.getString(NUM_COL_PSEUDO));
            reseauSocial_metier.setType(c.getString(NUM_COL_TYPE_RESEAU_SOCIAL));
            reseauSocial_metier.setId_contact_reseau_social(c.getInt(NUM_COL_ID_CONTACT_RESEAUX));
            listReseauxSociaux.add(reseauSocial_metier);
            c.moveToNext();
        }
        c.close();
        return listReseauxSociaux;
    }

    public ReseauSocial_metier getReseauxSociauxWithID(int id){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la SocialbookBDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_RESEAUX_SOCIAUX, new String[] {COL_ID_RESEAU_SOCIAL, COL_PSEUDO, COL_TYPE_RESEAU_SOCIAL, COL_ID_CONTACT_RESEAUX}
                , COL_ID_RESEAU_SOCIAL +"="+ id, null, null, null, null);
        return cursorToReseauSociaux(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private ReseauSocial_metier cursorToReseauSociaux(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        ReseauSocial_metier reseauSocial_metier = new ReseauSocial_metier();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        reseauSocial_metier.setId_reseau_social(c.getInt(NUM_COL_ID_RESEAU_SOCIAL));
        reseauSocial_metier.setPseudo(c.getString(NUM_COL_PSEUDO));
        reseauSocial_metier.setType(c.getString(NUM_COL_TYPE_RESEAU_SOCIAL));
        reseauSocial_metier.setId_contact_reseau_social(c.getInt(NUM_COL_ID_CONTACT_RESEAUX));

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return reseauSocial_metier;
    }



}
