package com.insoftbumdesku.driver.activity;


import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.insoftbumdesku.driver.constants.BaseApp;
import com.insoftbumdesku.driver.R;
import com.insoftbumdesku.driver.constants.Constants;
import com.insoftbumdesku.driver.fragment.EnableLlocationFragment;
import com.insoftbumdesku.driver.models.User;

import java.util.Objects;


public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        final User user = BaseApp.getInstance(this).getLoginUser();
        removeNotif();
        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                if (user != null) {

                    if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        enable_location();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        enable_location();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        }, 5000);


    }

    public void GPSStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GpsStatus = Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!GpsStatus) {
            Toast.makeText(this, "Lokasi akurasi tinggi", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2);

        } else {


            GetCurrentlocation();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            GPSStatus();
        }
    }


    private void GetCurrentlocation() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            enable_location();
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(String.valueOf(Constants.LATITUDE), "" + location.getLatitude());
                            editor.putString(String.valueOf(Constants.LONGITUDE), "" + location.getLongitude());
                            editor.apply();
                            Constants.LATITUDE = location.getLatitude();
                            Constants.LONGITUDE = location.getLongitude();
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.from_right, R.anim.to_left);
                            finish();
                        } else {

                            if (sharedPreferences.getString(String.valueOf(Constants.LATITUDE), "").equals("") || sharedPreferences.getString(String.valueOf(Constants.LONGITUDE), "").equals("")) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(String.valueOf(Constants.LATITUDE), "-6.3042012");
                                editor.putString(String.valueOf(Constants.LONGITUDE), "107.307897");
                                editor.apply();
                                Constants.LATITUDE = Double.valueOf("-6.3042012");
                                Constants.LONGITUDE = Double.valueOf("107.307897");
                            }
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.from_right, R.anim.to_left);
                            finish();
                        }
                    }
                });
    }


    private void enable_location() {
        EnableLlocationFragment enable_llocationFragment = new EnableLlocationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.from_right, R.anim.to_left, R.anim.from_left, R.anim.to_right);
        getSupportFragmentManager().popBackStackImmediate();
        transaction.replace(R.id.splash, enable_llocationFragment).addToBackStack(null).commit();

    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }


}
