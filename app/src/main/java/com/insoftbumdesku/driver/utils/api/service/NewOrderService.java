package com.insoftbumdesku.driver.utils.api.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.insoftbumdesku.driver.activity.NewOrderActivity;
import com.insoftbumdesku.driver.utils.Log;

import androidx.annotation.Nullable;


public class NewOrderService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Log.e("ordercommand","");
            Intent toOrder = new Intent(this, NewOrderActivity.class);
            toOrder.putExtra("id_transaksi", intent.getStringExtra("id_transaksi"));
            toOrder.putExtra("icon", intent.getStringExtra("icon"));
            toOrder.putExtra("layanan", intent.getStringExtra("layanan"));
            toOrder.putExtra("layanandesc", intent.getStringExtra("layanandesc"));
            toOrder.putExtra("alamat_asal", intent.getStringExtra("alamat_asal"));
            toOrder.putExtra("alamat_tujuan", intent.getStringExtra("alamat_tujuan"));
            toOrder.putExtra("estimate_time", intent.getStringExtra("estimate_time"));
            toOrder.putExtra("harga", intent.getStringExtra("harga"));
            toOrder.putExtra("biaya", intent.getStringExtra("biaya"));
            toOrder.putExtra("distance", intent.getStringExtra("distance"));
            toOrder.putExtra("pakai_wallet", intent.getStringExtra("pakai_wallet"));
            toOrder.putExtra("token_merchant", intent.getStringExtra("token_merchant"));
            toOrder.putExtra("id_pelanggan", intent.getStringExtra("id_pelanggan"));
            toOrder.putExtra("id_trans_merchant", intent.getStringExtra("id_trans_merchant"));
            toOrder.putExtra("waktu_order", intent.getStringExtra("waktu_order"));
            toOrder.putExtra("reg_id", intent.getStringExtra("reg_id"));
            toOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toOrder);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
