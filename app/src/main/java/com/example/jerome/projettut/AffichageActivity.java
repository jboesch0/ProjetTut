package com.example.jerome.projettut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jerome.projettut.localisaion.LocalisationGPS;


/**
 * Created by Jerome on 03/02/2016.
 */
public class AffichageActivity extends AppCompatActivity {

    public BroadcastReceiver br;
    SharedPreferences preferences;
    TextView infoPoidsTw;
    TextView infoPuissanceTw;
    Button btnStart;
    double altitude;
    LocalisationGPS localisationGPS;
    double altitudePrecedente;
    double altitudeCourante;
    double deltaAltitude;
    float masse;
    static final double G = 9.81;
    static final double DT = 10.;
    static int compteur = 0;
    double puissance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);

        //Variables
        infoPoidsTw = (TextView)findViewById(R.id.infoPoidsTw);
        infoPuissanceTw = (TextView) findViewById(R.id.infoPuissanceTw);
        btnStart = (Button)findViewById(R.id.btnStart);
        localisationGPS = LocalisationGPS.get(getApplicationContext());


        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                altitude = intent.getDoubleExtra(LocalisationGPS.KEY_ALTITUDE, 0d);
                compteur += 1;
                Log.d("altitude", String.valueOf(altitude));

                if(altitudePrecedente == 0){
                    altitudePrecedente = altitude;
                    altitudeCourante = altitudePrecedente;
                }else {
                    altitudePrecedente = altitudeCourante;
                    altitudeCourante = altitude;
                }

                deltaAltitude = altitudeCourante - altitudePrecedente;

                if(deltaAltitude < 0){
                    deltaAltitude = 0.;
                }
                Log.d("Alt prec", String.valueOf(altitudePrecedente));
                Log.d("Alt cour", String.valueOf(altitudeCourante));
                Log.d("Delta", String.valueOf(deltaAltitude));

                if(compteur == 3){
                    puissance = masse * G * (deltaAltitude)/(DT);
                    compteur = 0;
                    Log.d("PUISSANCE", String.valueOf(puissance/1000));
                }
            }
        };



        //Listner
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localisationGPS.startLocationUpdates();
            }
        });


        //Récupération et affichage du poids
        preferences = getSharedPreferences(MainActivity.PREF_POIDS, 0);
        masse = preferences.getFloat("poids", 0);
        infoPoidsTw.setText(String.valueOf(masse));

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(LocalisationGPS.KEY_POSITION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
