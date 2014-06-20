package com.example.socialbook;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classe.Contact_metier;
import classe.ReseauSocial_metier;
import classe.SocialbookBDD;
import classe.Utilisateur_metier;

public class MainActivity extends Activity {

    Button _buttonInscription;
    EditText email;
    EditText password ;
    String str_email;
    String str_password ;
    Context context;
    ConnexionTask loaderTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        _buttonInscription = (Button)findViewById(R.id.btn_inscription);
        SocialbookBDD socialbookBDD = new SocialbookBDD(context);
        socialbookBDD.open();
        Utilisateur_metier utilisateur_metier= new Utilisateur_metier();
        utilisateur_metier = socialbookBDD.getUtilisateur();
        if(utilisateur_metier != null)
        {

            Intent i = new Intent(MainActivity.this, Connexion.class);
            i.putExtra("email", str_email);
            i.putExtra("password", str_password);
            this.startActivity(i);
            finish();
        }
        socialbookBDD.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayInscription(View _buttonInscription){
        Intent i = new Intent(this, Inscription.class);
        startActivity(i);
    }
    public void displayConnexion(View _buttonConnexion){
        email = (EditText)findViewById(R.id.email);
        str_email = email.getText().toString();
        password = (EditText)findViewById(R.id.password);
        str_password = password.getText().toString();
        // On déclare le pattern que l’on doit vérifier
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        // On déclare un matcher, qui comparera le pattern avec la
        // string passée en argument
        Matcher m = p.matcher(str_email);
        // Si l’adresse mail saisie ne correspond au format d’une
        // adresse mail on un affiche un message à l'utilisateur
        if (!m.matches()) {
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
        }
        loaderTask = new ConnexionTask();
        loaderTask.execute();

    }

    @Override
    public void onPause() {
        /*if(loaderTask!=null)
            loaderTask.dialog.dismiss();
        loaderTask.dialog=null;
        */
        super.onPause();
    }



    private class ConnexionTask extends AsyncTask<Void, Void, Void>{

        ProgressDialog dialog;
        private Handler mHandler = new Handler();
        @Override
        protected void onPreExecute(){
            dialog = new ProgressDialog(context);
            dialog.setMessage("Chargement des contacts");
            dialog.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setMax(100);
            dialog.setIndeterminate(true);
            dialog.show();
            super.onPreExecute();

        }


        @Override
        protected Void doInBackground(Void... voids) {
            new Thread(new Runnable() {
                public void run() {
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {

                            dialog.setProgress(3);

                        }
                    });

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet("http://172.16.56.12/Api/Utilisateur?login="+str_email+"&password="+str_password+"");
                    mHandler.post(new Runnable() {
                        public void run() {

                            dialog.setProgress(20);
                        }
                    });
            try {
                HttpResponse httpResponse=  httpClient.execute(getRequest);
                StatusLine statusLine = httpResponse.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode != 200)
                {
                    mHandler.post(new Runnable() {
                        public void run() {

                            dialog.setProgress(100);
                        }
                    });
                    Log.i("Utilisateur TEST :  ", "ERROR");


                }
                else {
                    mHandler.post(new Runnable() {
                        public void run() {

                            dialog.setProgress(30);
                        }
                    });
                    InputStream jsonStreamData = httpResponse.getEntity().getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStreamData));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    mHandler.post(new Runnable() {
                        public void run() {

                            dialog.setProgress(47);
                        }
                    });
                    String jsonData = builder.toString();
                    Log.i("Utilisateur TEST :  ", jsonData);

                    JSONObject json = new JSONObject(jsonData);
                    Utilisateur_metier utilisateur_metier = new Utilisateur_metier();
                    utilisateur_metier.setLogin(json.getString("LOGIN"));
                    utilisateur_metier.setPassword(json.getString("PASSWORD"));
                    utilisateur_metier.setIdUtilisateurServeur(json.getInt("ID_UTILISATEUR"));
                   SocialbookBDD socialbookBDD = new SocialbookBDD(context);
                     socialbookBDD.open();
                     socialbookBDD.insertUtilisateur(utilisateur_metier);
                     socialbookBDD.close();
                    JSONArray contactJson = json.getJSONArray("LesContacts");

                    mHandler.post(new Runnable() {
                        public void run() {

                            dialog.setProgress(60);
                        }
                    });
                    setJsonContact(contactJson, json.getInt("ID_UTILISATEUR"));


                    mHandler.post(new Runnable() {
                        public void run() {

                            dialog.setProgress(100);
                        }
                    });

                }

               Intent i = new Intent(MainActivity.this, Connexion.class);
                i.putExtra("email", str_email);
                i.putExtra("password", str_password);
                context.startActivity(i);
                finish();
            }
            catch (ClientProtocolException e)
            {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


                }
            }).start();

            return null;
        }


        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
        }

        public void setJsonContact(JSONArray contactJson, int idU) throws JSONException {

            JSONArray lesreseauxSociaux;
            if(contactJson.length()>0) {
                mHandler.post(new Runnable() {
                    public void run() {

                        dialog.setProgress(73);
                    }
                });
                SocialbookBDD socialbookBDD = new SocialbookBDD(context);
                socialbookBDD.open();
                for (int i = 0; i < contactJson.length(); i++) {
                    JSONObject contact = contactJson.getJSONObject(i);
                    Log.i("Tableau Json :  " + i + "", contactJson.getJSONObject(i).toString());
                    int  idcontact = contact.getInt("ID_CONTACT");
                    String prenom = contact.getString("PRENOM");
                    String nom = contact.getString("NOM");
                    //int idU = contact.getInt("ID_UTILISATEUR");
                    String ville = contact.getString("VILLE");
                    String adresse = contact.getString("ADRESSE");
                    int cp = contact.getInt("CP");
                    String tel = contact.getString("TELEPHONE");
                    String datenaissance = contact.getString("DATE_NAISSANCE");
                    double longitude = contact.getDouble("LONGITUDE");
                    double latitude = contact.getDouble("LATITUDE");

                    Contact_metier contact_metier = new Contact_metier(idU, nom, prenom, tel, adresse, cp, ville, datenaissance);
                    contact_metier.setId_contact_serveur(idcontact);
                    contact_metier.setLatitude(latitude);
                    contact_metier.setLongitude(longitude);
                    socialbookBDD.insertContact(contact_metier);
                    lesreseauxSociaux = contact.getJSONArray("LesReseauSociaux");
                    Log.i("Tableau ReseauxSociauxJson :  " + i + "", lesreseauxSociaux.toString());
                    mHandler.post(new Runnable() {
                        public void run() {

                            dialog.setProgress(80);
                        }
                    });
                    for (int j = 0; j < lesreseauxSociaux.length(); j++) {

                        JSONObject reseauSociaux = lesreseauxSociaux.getJSONObject(j);
                        String pseudo = reseauSociaux.getString("PSEUDO");
                        int idrs =reseauSociaux.getInt("ID_RESEAU_SOCIAL");
                        String typeRes = reseauSociaux.getString("TYPE_RESEAU_SOCIAL");
                        ReseauSocial_metier reseauSocial_metier =new ReseauSocial_metier (idrs,typeRes,pseudo);
                        reseauSocial_metier.setId_contact_reseau_social(idcontact);
                        Log.i("reeaux social metier :  " + i + "", reseauSocial_metier.getId_contact_reseau_social()+" .");
                        socialbookBDD.insertReseauxSocial(reseauSocial_metier);

                    }

                }
            }
            mHandler.post(new Runnable() {
                public void run() {

                    dialog.setProgress(95);
                }
            });
        }



    }

}
