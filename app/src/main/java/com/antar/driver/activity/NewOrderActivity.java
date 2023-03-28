package com.antar.driver.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.antar.driver.R;
import com.antar.driver.constants.BaseApp;
import com.antar.driver.constants.Constants;
import com.antar.driver.json.AcceptRequestJson;
import com.antar.driver.json.AcceptResponseJson;
import com.antar.driver.json.FcmKeyRequestJson;
import com.antar.driver.json.FcmKeyResponseJson;
import com.antar.driver.utils.api.FCMHelper;
import com.antar.driver.utils.api.ServiceGenerator;
import com.antar.driver.utils.api.service.DriverService;
import com.antar.driver.json.fcm.FCMMessage;
import com.antar.driver.models.OrderFCM;
import com.antar.driver.models.User;
import com.antar.driver.utils.SettingPreference;
import com.antar.driver.utils.Utility;
import com.antar.driver.utils.PicassoTrustAll;

import java.io.IOException;
import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NewOrderActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    private static final int REQUEST_PERMISSION_CALL = 992;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.layanan)
    TextView layanantext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.senddetail)
    LinearLayout sendDetailLayout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.produk)
    TextView produk;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sendername)
    TextView sendername;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receivername)
    TextView receivername;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.senderphone)
    Button senderphone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receiverphone)
    Button receiverphone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.layanandes)
    TextView layanandesctext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pickUpText)
    TextView pickuptext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText)
    TextView destinationtext;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText2)
    TextView destinationtext2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText3)
    TextView destinationtext3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText4)
    TextView destinationtext4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText5)
    TextView destinationtext5;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fitur)
    TextView estimatetext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.distance)
    TextView distancetext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cost)
    TextView costtext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.price)
    TextView pricetext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.totaltext)
    TextView totaltext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.image)
    ImageView icon;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.timer)
    TextView timer;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.time)
    TextView time;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.distancetext)
    TextView distancetextes;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.costtext)
    TextView costtextes;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cancel)
    Button cancel;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.order)
    Button order;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination)
    LinearLayout lldestination;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination2)
    LinearLayout lldestination2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination3)
    LinearLayout lldestination3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination4)
    LinearLayout lldestination4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination5)
    LinearLayout lldestination5;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldistance)
    LinearLayout lldistance;

    String waktuorder,iconfitur, layanan, layanandesc, alamatasal, alamattujuan, alamattujuan2, alamattujuan3, alamattujuan4, alamattujuan5, estimasitime, hargatotal, cost, distance, idtrans, regid, orderfitur,tokenmerchant,idpelanggan,idtransmerchant;
    String wallett;
    MediaPlayer BG;
    Vibrator v;
    SettingPreference sp;
    private String keyss;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        getkey();
        ButterKnife.bind(this);
        setScreenOnFlags();
        sp = new SettingPreference(this);
        sp.updateNotif("ON");
        Intent intent = getIntent();
        iconfitur = intent.getStringExtra("icon");
        layanan = intent.getStringExtra("layanan");
        layanandesc = intent.getStringExtra("layanandesc");
        alamatasal = intent.getStringExtra("alamat_asal");
        alamattujuan = intent.getStringExtra("alamat_tujuan");
        alamattujuan2 = intent.getStringExtra("alamat_tujuan2");
        alamattujuan3 = intent.getStringExtra("alamat_tujuan3");
        alamattujuan4 = intent.getStringExtra("alamat_tujuan4");
        alamattujuan5 = intent.getStringExtra("alamat_tujuan5");

        estimasitime = intent.getStringExtra("estimasi_time");
        hargatotal = intent.getStringExtra("harga");
        cost = intent.getStringExtra("biaya");
        distance = intent.getStringExtra("distance");
        idtrans = intent.getStringExtra("id_transaksi");
        regid = intent.getStringExtra("reg_id");
        wallett = intent.getStringExtra("pakai_wallet");
        orderfitur = intent.getStringExtra("order_fitur");
        tokenmerchant = intent.getStringExtra("token_merchant");
        idpelanggan = intent.getStringExtra("id_pelanggan");
        idtransmerchant = intent.getStringExtra("id_trans_merchant");
        waktuorder = intent.getStringExtra("waktu_order");

        if (orderfitur.equalsIgnoreCase("3")) {
            lldestination.setVisibility(View.GONE);
            lldistance.setVisibility(View.GONE);
        }
        if (orderfitur.equalsIgnoreCase("2")) {
            sendDetailLayout.setVisibility(View.VISIBLE);
            //getData();
        }
        if (orderfitur.equalsIgnoreCase("4")) {

            estimatetext.setText(estimasitime);
            time.setText("Merchant");
            distancetextes.setText("Biaya Pengiriman");
            costtextes.setText("Biaya Pesanan");
            Utility.currencyTXT(distancetext, distance, this);
            Utility.currencyTXT(costtext, cost, this);
        } else {

            estimatetext.setText(estimasitime);
            distancetext.setText(distance);
            costtext.setText(cost);
        }
        System.out.println("Order MASUK"+layanan);
        layanantext.setText(layanan);
        layanandesctext.setText(layanandesc);
        pickuptext.setText(alamatasal);
        destinationtext.setText(alamattujuan);

        lldestination2.setVisibility(View.GONE);
        lldestination3.setVisibility(View.GONE);
        lldestination4.setVisibility(View.GONE);
        lldestination5.setVisibility(View.GONE);


        if(alamattujuan2 != null){
            if(alamattujuan2.equalsIgnoreCase("")){
                lldestination2.setVisibility(View.GONE);
            }else{
                lldestination2.setVisibility(View.VISIBLE);
            }

        }

        if(alamattujuan3 != null){
            if(alamattujuan3.equalsIgnoreCase("")){
                lldestination3.setVisibility(View.GONE);
            }else{
                lldestination3.setVisibility(View.VISIBLE);
            }
        }
        if(alamattujuan4 != null){
            if(alamattujuan4.equalsIgnoreCase("")){
                lldestination4.setVisibility(View.GONE);
            }else{
                lldestination4.setVisibility(View.VISIBLE);
            }
        }
        if(alamattujuan5 != null){
            if(alamattujuan5.equalsIgnoreCase("")){
                lldestination5.setVisibility(View.GONE);
            }else{
                lldestination5.setVisibility(View.VISIBLE);
            }
        }





        destinationtext2.setText(alamattujuan2);
        destinationtext3.setText(alamattujuan3);
        destinationtext4.setText(alamattujuan4);
        destinationtext5.setText(alamattujuan5);



        Utility.currencyTXT(pricetext, hargatotal, this);
        if (wallett.equalsIgnoreCase("true")) {
            totaltext.setText("Total (SALDO)");
        } else {
            totaltext.setText("Total (TUNAI)");
        }


        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESFITUR + iconfitur)
                .placeholder(R.drawable.antar)
                .resize(100, 100)
                .into(icon);
        cancel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (BG != null && BG.isPlaying()) {
                    BG.stop();
                    v.cancel();
                }
                timerplay.cancel();
                Intent toOrder = new Intent(NewOrderActivity.this, MainActivity.class);
                toOrder.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(toOrder);

            }
        });

        if (new SettingPreference(this).getSetting()[0].equals("OFF")) {
            timerplay.start();
            playSound();
            removeNotif();
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getaccept(false);

                }
            });
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    removeNotif();
                    if (BG != null && BG.isPlaying()) {
                        BG.stop();
                        v.cancel();
                    }
                }
            }, 2000);
            getaccept(true);
        }


    }

    CountDownTimer timerplay = new CountDownTimer(20000, 1000) {

        @SuppressLint("SetTextI18n")
        public void onTick(long millisUntilFinished) {
            timer.setText("" + millisUntilFinished / 1000);
        }


        @SuppressLint("MissingPermission")
        public void onFinish() {
            if (BG != null && BG.isPlaying()) {
                BG.stop();
                v.cancel();
            }
            timer.setText("0");
            Intent toOrder = new Intent(NewOrderActivity.this, MainActivity.class);
            toOrder.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toOrder);
        }
    }.start();

    private void  getkey() {
        User loginUser = BaseApp.getInstance(NewOrderActivity.this).getLoginUser();
        DriverService serv = ServiceGenerator.createService(DriverService.class,
                loginUser.getEmail(), loginUser.getPassword());
        FcmKeyRequestJson param = new FcmKeyRequestJson();
        param.setFcm(1);
        serv.getfcmkey(param).enqueue(new Callback<FcmKeyResponseJson>() {
            @Override
            public void onResponse(Call<FcmKeyResponseJson> call, Response<FcmKeyResponseJson> response) {
                if(response.isSuccessful()){
                    String res = response.body().getResultcode();
                    if(res.equalsIgnoreCase("00")){
                        keyss = response.body().getKeydata();

                    }
                }
            }

            @Override
            public void onFailure(Call<FcmKeyResponseJson> call, Throwable t) {

            }
        });
    }

    @SuppressLint("MissingPermission")
    private void playSound() {
        v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 700};
        Objects.requireNonNull(v).vibrate(pattern, 0);

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            BG = MediaPlayer.create(getBaseContext(), notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BG.setLooping(true);
        BG.setVolume(100, 100);
        BG.start();
    }

    @Override
    public void onBackPressed() {
    }

    @SuppressLint("MissingPermission")
    private void getaccept(boolean isAutoBid) {
        if (!isAutoBid) {
            if (BG != null && BG.isPlaying()) {
                BG.stop();
                v.cancel();
            }
            timerplay.cancel();
        }

        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(this).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);
        userService.accept(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull final Response<AcceptResponseJson> response) {
                if (response.isSuccessful()) {
                    sp.updateNotif("OFF");
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("berhasil")) {
                        rlprogress.setVisibility(View.GONE);
                        Intent i = new Intent(NewOrderActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        OrderFCM orderfcm = new OrderFCM();
                        orderfcm.id_driver = loginUser.getId();
                        orderfcm.id_transaksi = idtrans;
                        orderfcm.response = "2";
                        if (orderfitur.equalsIgnoreCase("4")) {
                            orderfcm.desc = "pengemudi sedang ambil pesanan";
                            orderfcm.id_pelanggan = idpelanggan;
                            orderfcm.invoice = "INV-"+idtrans+idtransmerchant;
                            orderfcm.ordertime = waktuorder;
                            sendMessageToDriver(tokenmerchant, orderfcm);
                        } else {
                            orderfcm.desc = getString(R.string.notification_start);
                        }
                        sendMessageToDriver(regid, orderfcm);
                    } else {
                        sp.updateNotif("OFF");
                        rlprogress.setVisibility(View.GONE);
                        Intent i = new Intent(NewOrderActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Toast.makeText(NewOrderActivity.this, "Pesanan tidak lagi tersedia!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(NewOrderActivity.this, "Error Koneksi!", Toast.LENGTH_SHORT).show();
                rlprogress.setVisibility(View.GONE);
                sp.updateNotif("OFF");
                rlprogress.setVisibility(View.GONE);
                Intent i = new Intent(NewOrderActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });
    }

    private void sendMessageToDriver(final String regIDTujuan, final OrderFCM response) {

        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(response);

        FCMHelper.sendMessage(keyss, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                Log.e("REQUEST TO DRIVER", message.getData().toString());
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }

    private void setScreenOnFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            Objects.requireNonNull(keyguardManager).requestDismissKeyguard(this, null);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        }
    }
}