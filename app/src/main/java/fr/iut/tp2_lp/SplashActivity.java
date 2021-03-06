package fr.iut.tp2_lp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Activité lancée au démarrage pour afficher un écran customisé (au lieu d'un écran blanc)
 * C'est utile pour afficher une vue plus "rassurtante" à l'utilisateur le temps que l'application soit chargée
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}