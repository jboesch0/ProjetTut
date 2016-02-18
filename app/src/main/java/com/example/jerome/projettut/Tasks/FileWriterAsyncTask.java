package com.example.jerome.projettut.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.jerome.projettut.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
        int arraySize = params[0].size();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Resume-parcours";
        File dir = new File(path);
        dir.mkdirs();
        File file = new File(path + "/test.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String stringPoids = "Votre poids est de : " + poids + "\n";

            fos.write(stringPoids.getBytes());
            for (int i = 0; i < arraySize; i++){
                String str = "Puissance " + i + " = " + params[0].get(i)  + "\n";
                fos.write(str.getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
