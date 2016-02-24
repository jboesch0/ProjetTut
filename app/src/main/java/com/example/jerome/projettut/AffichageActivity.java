package com.example.jerome.projettut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jerome.projettut.Tasks.FileWriterAsyncTask;
import com.example.jerome.projettut.localisaion.LocalisationGPS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Created by Jerome on 03/02/2016.
 */
public class AffichageActivity extends AppCompatActivity implements Serializable {

    public BroadcastReceiver br;
    SharedPreferences preferences;
    TextView infoPoidsTw;
    TextView infoPuissanceTw;
    Button btnStart;
    Button btnStop;
    double altitude;
    LocalisationGPS localisationGPS;
    double altitudePrecedente;
    double altitudeCourante;
    double deltaAltitude;
    Double total = 0.;
    Double moyenne = 0.;
    float masse;
    static final double G = 9.81;
    public static double DT = 3;
    Double puissance;
    ArrayList<Double> tabPuissances;
    Queue<Double> queue =new LinkedList<Double>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);

        //Variables
        tabPuissances = new ArrayList<>();
        infoPoidsTw = (TextView)findViewById(R.id.infoPoidsTw);
        infoPuissanceTw = (TextView) findViewById(R.id.infoPuissanceTw);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        localisationGPS = LocalisationGPS.get(getApplicationContext());

        //Récupération et affichage du poids
        preferences = getSharedPreferences(MainActivity.PREF_POIDS, 0);
        masse = preferences.getFloat("poids", 0);
        if (preferences.getInt("deltaT",0) != 0){
            DT = preferences.getInt("deltaT", 0);
        }
        infoPoidsTw.setText(String.valueOf(masse));


        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                altitude = intent.getDoubleExtra(LocalisationGPS.KEY_ALTITUDE, 0d);
                queue.add(altitude);
                altitudeCourante = altitude;
                if (altitudePrecedente == 0){
                    altitudePrecedente = altitude;
                }

                deltaAltitude = altitudeCourante - altitudePrecedente;

                if (queue.size() == 5){
                    for (Double d : queue){
                        total += d;
                    }
                    moyenne = total / queue.size();
                    deltaAltitude = altitudeCourante - moyenne;
                    queue.poll();
                }

                total = 0.;
                altitudePrecedente = moyenne;
                puissance = masse * G * (deltaAltitude/DT);
                if (puissance < 0){
                    puissance = 0.;
                }
                tabPuissances.add(puissance);
                infoPuissanceTw.setText(String.valueOf(puissance));

            }
        };


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //localisationGPS.stopLocationUpdate();
                Intent myIntent = new Intent(getApplicationContext(), SimpleXYPlotActivity.class);
                System.out.println(tabPuissances);
                myIntent.putExtra("ARR", tabPuissances);
                AffichageActivity.this.startActivity(myIntent);
                new FileWriterAsyncTask(getApplicationContext(), masse).execute(tabPuissances);
            }

        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localisationGPS.startLocationUpdates();

            }
        });

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
