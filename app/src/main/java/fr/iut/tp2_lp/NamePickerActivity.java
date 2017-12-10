package fr.iut.tp2_lp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Pattern;

public class NamePickerActivity extends AppCompatActivity {

    /**
     * Champ de saisie du nom
     */
    EditText mNameEditText;

    /**
     * Champ de saisie de l'email
     */
    EditText mEmailEditText;

    /**
     * Bouton de connexion
     */
    Button mSubmitButton;

    /**
     * Texte d'informations (bienvenue, les erreurs...)
     */
    TextView textIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namepicker);
        mNameEditText=(EditText) findViewById(R.id.nameText);
        mEmailEditText=(EditText) findViewById(R.id.emailText);
        textIntro=(TextView) findViewById(R.id.textIntro) ;
        autoGetEmail(); //Recuperation automatique de l'email associé au système (si il y en a une)
        //Ecoute de l'appui sur entrée lors de la saisie du mail
        mEmailEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Si on appuis sur entrée on lance la fonction de stockage
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return pickedGoStorage();
                }
                return false;
            }
        });
        mSubmitButton=(Button) findViewById(R.id.buttonPicker);
        mSubmitButton.setOnClickListener(new Button.OnClickListener () {
            @Override
            public void onClick(View v) {
                pickedGoStorage();
            }
        }); //Ecoute du bouton de connexion
    }

    /**
     * Cette methode va vérifier les informations saisies, et si otut est bon, va lancer l'activité principale (le chat)
     * @return
     */
    public boolean pickedGoStorage() {
        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        //On vérifie que les champs ne sont pas vides
        if(name.trim().length()<=0 || email.trim().length()<=0) {
            return false;
        }
        //On vérifie que l'email saisie a un format valide
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        if(!emailPattern.matcher(email).matches()) {
            //Si il n'est pas valide on affiche un message d'erreur dans le TextView d'informations
            textIntro.setTextColor(Color.RED);
            textIntro.setText(R.string.invalidEmail);
            return false;
        }
        //On enregistre les données validées dans le système
        UserStorage.setUserDatas(name,email);
        UserStorage.saveUserInfo(this);
        //On démarre l'activité principale
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    /**
     * Methode permettant de pre-remplir le champ email avec un éventuel email existant sur le système
     */
    public void autoGetEmail() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        try {
            Account[] accounts = AccountManager.get(this.getApplicationContext()).getAccounts(); //On récupère tous les comptes du système
            for(Account account : accounts) { //On les parcours
                if(emailPattern.matcher(account.name).matches()) { //Si on trouve une adresse email correcte
                    mEmailEditText.setText(account.name); //On rempli le champ email avec
                    break;
                }
            }
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }



}
