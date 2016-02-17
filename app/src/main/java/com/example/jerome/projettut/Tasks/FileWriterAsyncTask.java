package com.example.jerome.projettut.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.jerome.projettut.MainActivity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Jerome on 17/02/2016.
 */
public class FileWriterAsyncTask extends AsyncTask<ArrayList<Double>, Void, Void> {

    SharedPreferences preferences;
    Context context;
    float poids;

    public FileWriterAsyncTask(Context context, float poids){
        this.context = context;
        this.poids = poids;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preferences = context.getSharedPreferences(MainActivity.PREF_POIDS, 0);
        poids = preferences.getFloat("poids", 0);
    }

    @Override
    protected Void doInBackground(ArrayList<Double>... params) {
        String name = "test";
        int arraySize = params[0].size();
        try {
            PrintWriter writer = new PrintWriter(name);
            writer.println("Votre poids est de: " + poids);
            for (int i = 0; i < arraySize; i++){
                writer.println("Puissance " + i + " est de : " + params[0].get(i));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
