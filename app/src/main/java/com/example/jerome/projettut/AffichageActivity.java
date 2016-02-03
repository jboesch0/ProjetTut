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
import android.widget.Toast;

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
    Double altitude;
    LocalisationGPS localisationGPS;

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
                Log.d("log3", "zzzz");
                altitude = intent.getDoubleExtra(LocalisationGPS.KEY_ALTITUDE, 0d);
                Log.d("altitude", altitude.toString());
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
        int poids = preferences.getInt("poids", 0);
        infoPoidsTw.setText(String.valueOf(poids));

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
