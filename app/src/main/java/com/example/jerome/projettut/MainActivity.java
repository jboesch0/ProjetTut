package com.example.jerome.projettut;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public EditText etPoids;
    public EditText etDeltaT;
    public Button btnEnregistrerPoids;
    public final static String PREF_POIDS = "PREF_POIDS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPoids = (EditText) findViewById(R.id.poidsEt);
        etDeltaT = (EditText) findViewById(R.id.deltaEt);
        btnEnregistrerPoids = (Button) findViewById(R.id.btnEnregistrerPoids);
        btnEnregistrerPoids.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEnregistrerPoids){
            SharedPreferences preferences = getSharedPreferences(PREF_POIDS, 0);
            SharedPreferences.Editor editor = preferences.edit();
            int poids = Integer.parseInt(etPoids.getText().toString());
            editor.putFloat("poids", poids);
            editor.putInt("deltaT", Integer.parseInt(etDeltaT.getText().toString()));
            editor.apply();
            startActivity(new Intent(MainActivity.this, AffichageActivity.class));
            finish();
        }
    }
}
