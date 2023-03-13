package com.insoftbumdesku.driver.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;
import com.insoftbumdesku.driver.activity.MainActivity;
import com.insoftbumdesku.driver.constants.BaseApp;
import com.insoftbumdesku.driver.models.User;


public class MyLocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "com.insoftbumdesku.driver.utils.UPDATE_LOCATION";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null)
        {

            User user = BaseApp.getInstance(context).getLoginUser();
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null && user !=null)
                {
                    Location location = result.getLastLocation();
                    try {
                            MainActivity.getInstance().Updatelocationdata(location);


                    }catch (Exception ex)
                    {
                            BaseApp.getInstance(context).Updatelocationdata(location);

                    }
                }
            }
        }


    }
}
