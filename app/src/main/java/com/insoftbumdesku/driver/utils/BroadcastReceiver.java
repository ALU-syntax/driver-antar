package com.insoftbumdesku.driver.utils;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.insoftbumdesku.driver.R;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        mp=MediaPlayer.create(context, R.raw.notification);
        mp.start();
        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
    }
}
