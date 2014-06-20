package com.example.socialbook;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inscription extends Activity {

    ImageButton _buttonSubmit;
    EditText email;
    EditText password;
    EditText verification_password;
    String str_email;
    String str_password;
    String str_verification_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription_layout);
        _buttonSubmit = (ImageButton)findViewById(R.id.btn_submit);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inscription, menu);
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

    public void submitInscription(View _buttonSubmit){
        email = (EditText)findViewById(R.id.email);
        str_email = email.getText().toString();
        password = (EditText)findViewById(R.id.password);
        str_password = password.getText().toString();
        verification_password = (EditText)findViewById(R.id.verification_password);
        str_verification_password = verification_password.getText().toString();
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
            Toast.makeText(this, "l'email que vous avez saisi n'est pas valide",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (!str_password.equals(str_verification_password) ){
            Toast.makeText(this, "Les champs mot de passe et vérification ne sont pas identique",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(this, Connexion.class);
        i.putExtra("email", str_email);
        i.putExtra("password", str_password);
        this.startActivity(i);
        finish();
    }
}
