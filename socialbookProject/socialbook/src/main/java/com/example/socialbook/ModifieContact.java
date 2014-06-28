package com.example.socialbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import classe.Contact_metier;
import classe.GeocodeJSONParser;
import classe.ReseauSocial_metier;
import classe.SocialbookBDD;
import classe.Utilisateur_metier;


public class ModifieContact extends Activity {

    EditText nom;
    EditText prenom;
    EditText telephone;
    EditText adresse;
    EditText cp;
    EditText ville;
    EditText date_naissance;
    String str_nom;
    String str_prenom;
    String str_telephone;
    String str_adresse;
    String str_cp;
    String str_ville;
    String str_date_naissance;
    Contact_metier contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifie_contact);

        Bundle extra = getIntent().getExtras();
        int var_id = extra.getInt("id");

        SocialbookBDD socialbookBdd = new SocialbookBDD(this);
        socialbookBdd.open();
        contact = new Contact_metier();
        contact = socialbookBdd.getContactWithID(var_id);
        //List<ReseauSocial_metier> social_metierList = socialbookBdd.getReseauxSociaux(contact.getId_contact_serveur());
        nom = (EditText) findViewById(R.id.nom);
        if (contact.getNom().equals("")){
            nom.setHint("Nom");
        }
        else{
            nom.setText(contact.getNom());
        }
        prenom = (EditText) findViewById(R.id.prenom);
        if (contact.getPrenom().equals("")){
            prenom.setHint("Prenom");
        }
        else{
            prenom.setText(contact.getPrenom());
        }
        telephone = (EditText) findViewById(R.id.telephone);
        if (contact.getTelephone().equals("")){
            telephone.setHint("Telephone");
        }
        else{
            telephone.setText(contact.getTelephone());
        }
        adresse = (EditText) findViewById(R.id.adresse);
        if (contact.getAdresse().equals("")){
            adresse.setHint("Adresse");
        }
        else{
            adresse.setText(contact.getAdresse());
        }
        cp = (EditText) findViewById(R.id.cp);
        if (contact.getCp().equals("")){
            cp.setHint("CP");
        }
        else{
            cp.setText(contact.getCp());
        }
        ville = (EditText) findViewById(R.id.ville);
        if (contact.getVille().equals("")){
            ville.setHint("Ville");
        }
        else{
            ville.setText(contact.getVille());
        }
        date_naissance = (EditText) findViewById(R.id.date_naissance);
        if (contact.getDate_naissance().equals("")){
            date_naissance.setHint("Date de naissance");
        }
        else{
            date_naissance.setText(contact.getDate_naissance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.modifie_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Bundle extra = getIntent().getExtras();
        int var_id = extra.getInt("id");
        int id = item.getItemId();


            // On déclare le pattern que l’on doit vérifier
            //Pattern p = Pattern.compile(".+/.+\\./+");
            // On déclare un matcher, qui comparera le pattern avec la
            // string passée en argument
            //Matcher m = p.matcher(str_email);
            // Si l’adresse mail saisie ne correspond au format d’une
            // adresse mail on un affiche un message à l'utilisateur
        /*if (!m.matches()) {
            // Toast est une classe fournie par le SDK Android
            // pour afficher les messages (indications) à l'intention de
            // l'utilisateur. Ces messages ne possédent pas d'interaction avec l'utilisateur
            // Le premier argument représente le contexte, puis
            // le message et à la fin la durée d'affichage du Toast (constante
            // LENGTH_SHORT ou LENGTH_LONG). Sans oublier d'appeler la méthode
            //show pour afficher le Toast
            Toast.makeText(MainActivity.this, "l'email que vous avez saisi n'est pas valide",
                    Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (id == R.id.action_accept) {
            //Création d'une instance de ma classe LivresBDD
            SocialbookBDD socialbookBdd = new SocialbookBDD(this);
            //Création d'un livre

            nom = (EditText) findViewById(R.id.nom);
            str_nom = nom.getText().toString();
            prenom = (EditText)findViewById(R.id.prenom);
            str_prenom = prenom.getText().toString();
            telephone = (EditText)findViewById(R.id.telephone);
            str_telephone = telephone.getText().toString();
            adresse = (EditText)findViewById(R.id.adresse);
            str_adresse = adresse.getText().toString();
            cp = (EditText)findViewById(R.id.cp);
            str_cp = cp.getText().toString();
            ville = (EditText)findViewById(R.id.ville);
            str_ville = ville.getText().toString();
            date_naissance = (EditText)findViewById(R.id.date_naissance);
            str_date_naissance = date_naissance.getText().toString();
            Contact_metier contact = new Contact_metier(0,0, str_nom, str_prenom,str_telephone, str_adresse, str_cp, str_ville, str_date_naissance);
            //On ouvre la base de données pour écrire dedans
            GeocodeJSONParser geocodeJSONParser = new GeocodeJSONParser(this, str_adresse);
            contact.setLatitude(geocodeJSONParser.getLatitude());
            contact.setLongitude(geocodeJSONParser.getLongitude());
            Log.d("Latitude : ", "" + contact.getLatitude());
            Log.d("Longitude : ", "" + contact.getLongitude());
            socialbookBdd.open();
            Utilisateur_metier utilisateur_metier=socialbookBdd.getUtilisateur();
            contact.setId_utilisateur(utilisateur_metier.getIdUtilisateurServeur());
            //On insère le livre que l'on vient de créer
            socialbookBdd.updateContact(var_id,contact);

            socialbookBdd.close();

            Intent i = new Intent(ModifieContact.this, Contact.class);
        /*Toast.makeText(AjoutContact.this, "Un nouveau contact a été ajouté " + contact.getId_contact(),
                Toast.LENGTH_SHORT).show();*/
            i.putExtra("id", var_id);
            this.startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
