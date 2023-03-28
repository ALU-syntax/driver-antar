package com.antar.driver.utils.api.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.antar.driver.constants.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.antar.driver.R;
import com.antar.driver.activity.ChatActivity;
import com.antar.driver.activity.MainActivity;
import com.antar.driver.activity.NewOrderActivity;
import com.antar.driver.activity.SplashActivity;
import com.antar.driver.constants.BaseApp;
import com.antar.driver.models.User;
import com.antar.driver.utils.SettingPreference;

import java.util.List;
import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;



@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessagingService extends FirebaseMessagingService {
    Intent intent;
    public static final String BROADCAST_ACTION = "com.insoftbumdesku.driver";
    public static final String BROADCAST_ORDER = "order";
    Intent intentOrder;
    long millisecond;

    SettingPreference sp;

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        intentOrder = new Intent(BROADCAST_ORDER);
        millisecond = 20000;
        sp = new SettingPreference(this);

    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (!remoteMessage.getData().isEmpty()) {
            messageHandler(remoteMessage);
        }
    }

    private void messageHandler(RemoteMessage remoteMessage){
        User user = BaseApp.getInstance(this).getLoginUser();
        SettingPreference sp = new SettingPreference(this);
        if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("1")) {
            if (user != null) {
                String resp = remoteMessage.getData().get("response");
                if (resp == null ) {
                    if (sp.getSetting()[1].equals("Unlimited")) {
                        if (sp.getSetting()[2].equals("ON") && sp.getSetting()[3].equals("OFF")) {

                           if (sp.getSetting()[0].equals("ON")) {
                               playSound();
                           }
                            notification(remoteMessage);
                        }
                    } else {
                        double uangbelanja = Double.parseDouble(sp.getSetting()[1]);
                        double harga = Double.parseDouble(Objects.requireNonNull(remoteMessage.getData().get("harga")));
                        if (uangbelanja > harga && sp.getSetting()[2].equals("ON") && sp.getSetting()[3].equals("OFF")) {
                            notification(remoteMessage);
                        }
                    }
                } else {
                    playSound();
                    intentCancel();
                }
            }
        } else if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("3")) {
            if (user != null) {
                otherHandler(remoteMessage);
            }
        } else if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("4")) {
                otherHandler2(remoteMessage);
        } else if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("2")) {
            if (user != null) {
                chat(remoteMessage);
            }
        }
    }

    private void intentCancel() {
        Intent toMain = new Intent(getBaseContext(), MainActivity.class);
        toMain.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(toMain);
    }

    private void otherHandler2(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), SplashActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.antar);
        mBuilder.setContentTitle(remoteMessage.getData().get("title"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String channelId = "customer";
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_MAX);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void otherHandler(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(remoteMessage.getData().get("title"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String channelId = "customer";
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_MAX);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void chat(RemoteMessage remoteMessage) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), ChatActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("senderid", remoteMessage.getData().get("receiverid"));
        intent1.putExtra("receiverid", remoteMessage.getData().get("senderid"));
        intent1.putExtra("name", remoteMessage.getData().get("name"));
        intent1.putExtra("tokenku", remoteMessage.getData().get("tokendriver"));
        intent1.putExtra("tokendriver", remoteMessage.getData().get("tokenuser"));
        intent1.putExtra("pic", remoteMessage.getData().get("pic"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("name"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(remoteMessage.getData().get("name"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String channelId = "customer";
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_MAX);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void notification(RemoteMessage remoteMessage) {
        Intent toOrder = new Intent(getBaseContext(), NewOrderActivity.class);
        toOrder.putExtra("id_transaksi", remoteMessage.getData().get("id_transaksi"));
        toOrder.putExtra("icon", remoteMessage.getData().get("icon"));
        toOrder.putExtra("layanan", remoteMessage.getData().get("layanan"));
        toOrder.putExtra("layanandesc", remoteMessage.getData().get("layanandesc"));
        toOrder.putExtra("alamat_asal", remoteMessage.getData().get("alamat_asal"));
        toOrder.putExtra("alamat_tujuan", remoteMessage.getData().get("alamat_tujuan"));
        toOrder.putExtra("alamat_tujuan2", remoteMessage.getData().get("alamat_tujuan2"));
        toOrder.putExtra("alamat_tujuan3", remoteMessage.getData().get("alamat_tujuan3"));
        toOrder.putExtra("alamat_tujuan4", remoteMessage.getData().get("alamat_tujuan4"));
        toOrder.putExtra("alamat_tujuan5", remoteMessage.getData().get("alamat_tujuan5"));
        toOrder.putExtra("estimasi_time", remoteMessage.getData().get("estimasi_time"));
        toOrder.putExtra("harga", remoteMessage.getData().get("harga"));
        toOrder.putExtra("biaya", remoteMessage.getData().get("biaya"));
        toOrder.putExtra("distance", remoteMessage.getData().get("distance"));
        toOrder.putExtra("pakai_wallet", remoteMessage.getData().get("pakai_wallet"));
        toOrder.putExtra("reg_id", remoteMessage.getData().get("reg_id_pelanggan"));
        toOrder.putExtra("order_fitur", remoteMessage.getData().get("order_fitur"));
        toOrder.putExtra("token_merchant", remoteMessage.getData().get("token_merchant"));
        toOrder.putExtra("id_pelanggan", remoteMessage.getData().get("id_pelanggan"));
        toOrder.putExtra("id_trans_merchant", remoteMessage.getData().get("id_trans_merchant"));
        toOrder.putExtra("waktu_order", remoteMessage.getData().get("waktu_order"));
        toOrder.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        boolean inForeground = isAppOnForeground(getApplicationContext());
        timerplay.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !inForeground) {
            orderintent(remoteMessage, toOrder);
        } else {
            toOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toOrder);
        }
    }

    @TargetApi(29)
    private void orderintent(RemoteMessage remoteMessage, Intent intent1) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_order");
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("HORE KAMU DAPAT PESANAN BARU");
        bigTextStyle.bigText("Cek Pesanannya "+remoteMessage.getData().get("layanan"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setOngoing(true);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("HORE KAMU DAPAT PESANAN BARU");
        mBuilder.setContentText("Cek Pesanannya "+remoteMessage.getData().get("layanan"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));



        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "notify_order";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Order Notification",
                    NotificationManager.IMPORTANCE_HIGH);

            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(100, mBuilder.build());
    }

    CountDownTimer timerplay = new CountDownTimer(20000, 1000) {
        @SuppressLint("SetTextI18n")
        public void onTick(long millisUntilFinished) {
            Constants.duration = millisUntilFinished;
        }


        public void onFinish() {
            sp.updateNotif("OFF");
            cancelIncomingCallNotification();
        }
    }.start();


    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private void playSound() {
        MediaPlayer BG = MediaPlayer.create(getBaseContext(), R.raw.notification);
        BG.setLooping(false);
        BG.setVolume(100, 100);
        BG.start();

        Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        Objects.requireNonNull(v).vibrate(3000);
    }


    
    public void cancelIncomingCallNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(100);
    }


}



