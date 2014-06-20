package com.example.socialbook;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import classe.Contact_metier;
import classe.SocialbookBDD;
import classe.Utilisateur_metier;

public class Connexion extends ListActivity {

    //TextView _textView1;
    //TextView _textView2;
    Context context;
    ContactListAdapter adapter;
    List<Contact_metier> listAllContact;
    Button buttonSyncrho ;
    Utilisateur_metier utilisateur_metier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_layout);
        Bundle extra = getIntent().getExtras();
        context = this;
        buttonSyncrho = (Button)findViewById(R.id.buttonSyncrho);
        //Création d'une instance de ma classe LivresBDD
        SocialbookBDD socialbookBdd = new SocialbookBDD(context);
        socialbookBdd.open();
        utilisateur_metier= new Utilisateur_metier();
        utilisateur_metier = socialbookBdd.getUtilisateur();

//        Log.e("id utilisateur : ", ""+utilisateur_metier.getIdUtilisateurServeur());
        //Contact_metier contact = new Contact_metier(2,1, "jean", "paul","0634231223", "2rue de labas", 34021, "Pastresloin", 22041993);

        //On ouvre la base de données pour écrire dedans


        listAllContact = new LinkedList<Contact_metier>();
        listAllContact = socialbookBdd.getContactUtils(utilisateur_metier.getIdUtilisateurServeur());

        for (Contact_metier vContact : listAllContact) {
     //       Log.d("test contact nom", vContact.getId_utilisateur() + "" + vContact.getNom());

        }
        ArrayList<HashMap<String, String>> listItem = this.addListContact(listAllContact);

        adapter = new ContactListAdapter(this, listItem);
        setListAdapter(adapter);

        socialbookBdd.close();

        if(!adapter.isEmpty()){
            buttonSyncrho.setVisibility(View.INVISIBLE);
        }
        else{
            buttonSyncrho.setVisibility(View.VISIBLE);
        }

    }

    public void updateListContact(List<Contact_metier> newlist) {
        adapter.clear();
        ArrayList<HashMap<String, String>> listItem = this.addListContact(newlist);
        adapter.setData(listItem);
        adapter.notifyDataSetChanged();
    }

    public ArrayList<HashMap<String, String>> addListContact(List<Contact_metier> listAllContact) {
        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;

        for (int i = 0; i < listAllContact.size(); i++) {
            map = new HashMap<String, String>();
            //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
            map.put("nom", listAllContact.get(i).getNom());
            //on insère un élément description que l'on récupérera dans le textView description créé dans le fichier affichageitem.xml
            map.put("prenom", listAllContact.get(i).getPrenom());
            //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
            //map.put("img", String.valueOf(R.drawable.arrow_vista));

            //Test insertion id
            map.put("id", String.valueOf(listAllContact.get(i).getId_contact()));
            //enfin on ajoute cette hashMap dans la arrayList
            listItem.add(map);
        }
        return listItem;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        HashMap<String, String> item = (HashMap<String, String>) getListAdapter().getItem(position);
        //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, Contact.class);
        //i.putExtra("nom", item.get("nom"));
        //i.putExtra("prenom", item.get("prenom"));
        i.putExtra("id", item.get("id"));
        startActivity(i);
        //finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.connexion_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_adding) {
            Intent i = new Intent(this, AjoutContact.class);
            //i.putExtra("nom", item.get("nom"));
            //i.putExtra("prenom", item.get("prenom"));
            //i.putExtra("id", item.get("id"));
            startActivity(i);
            //finish();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void synchroniserContact(){
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{
                        ContactsContract.Contacts._ID
                },
                null,
                null, null
        );
        SocialbookBDD socialbookBdds = new SocialbookBDD(context);
        //On ouvre la base de données pour écrire dedans
        socialbookBdds.open();

        Contact_metier contactMetier;
        if (c.moveToFirst()) {

            listAllContact.clear();

            do {
                contactMetier = getContact(c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)));
              /*:*String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String num = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
*/
                // contactMetier = new Contact_metier(0,1,name,name,num,"adresse",30,"ville","dateNaissance");

                //contactMetier = new Contact_metier(1, "" + name, "", "" , "", 30000, "", "0");
                listAllContact.add(contactMetier);
                socialbookBdds.insertContact(contactMetier);

            } while (c.moveToNext());
            //updateListContact();

        }
        c.close();


        socialbookBdds.close();
        recreate();
        if(!adapter.isEmpty()){
            buttonSyncrho.setVisibility(View.GONE);
        }
        else{
            buttonSyncrho.setVisibility(View.VISIBLE);
        }

    }


    public void onSynchro(MenuItem item) {
        synchroniserContact();


    }

    public Contact_metier getContact(String id){
        Contact_metier cm = new Contact_metier();
        cm.setId_utilisateur(utilisateur_metier.getIdUtilisateurServeur());
        String givenName="";
        String structuredNameWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] structuredNameWhereParams = new String[]{id, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
        Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, structuredNameWhere, structuredNameWhereParams, null);
        if (cursor.moveToFirst())
        {
            String prenom = (cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)));
           String nom= cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
            if (nom == null){
                cm.setNom(" - ");
            }
            cm.setNom(nom);
            if (prenom == null){
                cm.setPrenom(" - ");
            }
            cm.setPrenom(prenom);
            cm.setAdresse((" "));
            cm.setVille(" ");
            cm.setDate_naissance(" ");

           // cm(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
        }
        cursor.close();


        String structuredPhoneWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] structuredPhoneWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
        cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, structuredPhoneWhere, structuredPhoneWhereParams, null);
        String tel = " - ";
        if (cursor.moveToFirst())
        {
             tel =(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            if(tel == null)
                tel = " - ";
        }

        cm.setTelephone(tel);
        cursor.close();



        String structuredCityWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] structuredCityWhereParams = new String[]{id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
        cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, structuredCityWhere, structuredCityWhereParams, null);
        if (cursor.moveToFirst())
        {
            cm.setVille(""+ cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
        }
        cursor.close();
        return cm;
    }

    public void synchroniser(View view) {
        synchroniserContact();
    }
}