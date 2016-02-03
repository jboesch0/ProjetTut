package com.example.jerome.projettut.localisaion;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by couchot on 05/01/16.
 */
public class LocalisationGPS {
    static LocalisationGPS slocalisationGPS;
    Context mAppContext;
    LocationManager mLocationManager;
    Intent intent;

    public static final String KEY_POSITION = "CURRENT_POSITION";
    public static final String KEY_ALTITUDE = "CURRENT_ALTITUDE";

    private LocalisationGPS(Context appContext) {
        mAppContext = appContext;
        mLocationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public static LocalisationGPS get(Context c) {
        if (slocalisationGPS == null) {
            slocalisationGPS = new LocalisationGPS(c);
        }
        return slocalisationGPS;
    }


    public void startLocationUpdates() {
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        intent = new Intent(KEY_POSITION);
                        intent.putExtra(KEY_ALTITUDE, location.getAltitude());
                        mAppContext.sendBroadcast(intent);

                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } catch (SecurityException e) {
            Log.e("LocalisationGPS", "Erreur d'exception");
        }

    }


}
