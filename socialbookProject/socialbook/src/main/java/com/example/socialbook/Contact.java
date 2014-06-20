package com.example.socialbook;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import classe.Contact_metier;
import classe.ReseauSocial_metier;
import classe.SocialbookBDD;


public class Contact extends Activity {
    Contact_metier contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Bundle extra = getIntent().getExtras();
        int var_id = Integer.parseInt(extra.getString("id"));
        //String var_name = extra.getString("nom");
        //String var_firstname = extra.getString("prenom");

        //Création d'une instance de ma classe LivresBDD
        SocialbookBDD socialbookBdd = new SocialbookBDD(this);
        //On ouvre la base de données pour écrire dedans
        socialbookBdd.open();
        contact = new Contact_metier();
        contact = socialbookBdd.getContactWithID(var_id);
        List<ReseauSocial_metier> social_metierList = socialbookBdd.getReseauxSociaux(contact.getId_contact_serveur());
        TextView _textView_nom = (TextView) findViewById(R.id.nom);
        _textView_nom.setText(contact.getNom());
        TextView _textView_prenom = (TextView) findViewById(R.id.prenom);
        _textView_prenom.setText(contact.getPrenom());
        TextView _textView_numero = (TextView) findViewById(R.id.numero);
        _textView_numero.setText(contact.getTelephone());
        TextView _textView_adresse = (TextView) findViewById(R.id.adresse);
        _textView_adresse.setText(contact.getAdresse());
        TextView _textView_cp = (TextView) findViewById(R.id.cp);
        String cp = String.valueOf(contact.getCp());
        if (cp.equals("0")){
            _textView_cp.setText("");
        }
        else{
            _textView_cp.setText(cp);
        }

        TextView _textView_ville = (TextView) findViewById(R.id.ville);
        _textView_ville.setText(contact.getVille());

        TextView _textView_facebook = (TextView) findViewById(R.id.facebook);
        TextView _textView_twitter = (TextView) findViewById(R.id.twitter);

        if(social_metierList.size()>0) {
            for (int i = 0; social_metierList.size() > i; i++) {
                ReseauSocial_metier reseauSocial_metier = social_metierList.get(i);
                if (reseauSocial_metier.getType().contains("Facebook"))
                {

                    _textView_facebook.setText(reseauSocial_metier.getPseudo());

                }
                else if(reseauSocial_metier.getType().contains("Twitter"))
                {

                    _textView_twitter.setText(reseauSocial_metier.getPseudo());
                }
                Log.i("Reseaux Social :  " + i + "", reseauSocial_metier.getPseudo().toString());

            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Bundle extra = getIntent().getExtras();
        int var_id = Integer.parseInt(extra.getString("id"));
        int id = item.getItemId();
        if (id == R.id.action_deleting) {
            //Création d'une instance de ma classe LivresBDD
            SocialbookBDD socialbookBdd = new SocialbookBDD(this);
            //On ouvre la base de données pour écrire dedans
            socialbookBdd.open();
            socialbookBdd.removeContactWithID(var_id);
            socialbookBdd.close();
            Intent i = new Intent(this, Connexion.class);
            startActivity(i);
            finish();
            //return true;
        }
        else if(id == R.id.action_localiser) {
            if (contact.getLatitude()==0 && contact.getLongitude()==0)
            {
                Toast.makeText(getApplicationContext(), "Impossible de localiser ce contact", Toast.LENGTH_SHORT).show();

            }
            else
            {
                Intent i = new Intent(this, LocalisationContact.class);
                i.putExtra("id", var_id);
                startActivity(i);
            }
        }
        else {

        }
        return super.onOptionsItemSelected(item);
    }


    public void displayFacebook(View view) {

        String url = "http://www.facebook.fr";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void displayTwitter(View view) {
        String url = "http://www.twitter.fr";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
