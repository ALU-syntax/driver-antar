package com.antar.driver.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.antar.driver.R;
import com.antar.driver.constants.BaseApp;
import com.antar.driver.json.LoginResponseJson;
import com.antar.driver.utils.LocaleHelper;
import com.antar.driver.utils.api.ServiceGenerator;
import com.antar.driver.utils.api.service.DriverService;
import com.antar.driver.json.EditKendaraanRequestJson;
import com.antar.driver.models.FirebaseToken;
import com.antar.driver.models.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditKendaraanActivity extends AppCompatActivity {

    ImageView backbtn;
    EditText brand, type, platnomor, warna;
    TextView notiftext;
    RelativeLayout rlnotif, rlprogress;
    String disableback;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editkendaraan);
        User driver = BaseApp.getInstance(this).getLoginUser();
        backbtn = findViewById(R.id.back_btn_verify);
        brand = findViewById(R.id.brand);
        type = findViewById(R.id.type);
        platnomor = findViewById(R.id.platnomor);
        warna = findViewById(R.id.color);
        notiftext = findViewById(R.id.textnotif2);
        rlnotif = findViewById(R.id.rlnotif2);
        rlprogress = findViewById(R.id.rlprogress);

        disableback = "false";
        brand.setText(driver.getMerek());
        type.setText(driver.getTipe());
        platnomor.setText(driver.getNomorkendaraan());
        warna.setText(driver.getWarna());

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void get() {
        progressshow();
        EditKendaraanRequestJson request = new EditKendaraanRequestJson();
        User loginuser = BaseApp.getInstance(EditKendaraanActivity.this).getLoginUser();
        request.setNoTelepon(loginuser.getNoTelepon());
        request.setId(loginuser.getId());
        request.setId_kendaraan(loginuser.getIdk());
        request.setMerek(brand.getText().toString());
        request.setTipe(type.getText().toString());
        request.setNo_kendaraan(platnomor.getText().toString());
        request.setWarna(warna.getText().toString());

        DriverService service = ServiceGenerator.createService(DriverService.class, request.getNoTelepon(), loginuser.getPassword());
        service.editKendaraan(request).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseJson> call, @NonNull Response<LoginResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {

                        User user = response.body().getData().get(0);
                        saveUser(user);
                        finish();


                    } else {
                        notif(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error");
            }
        });
    }

    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
    }

    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        notiftext.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(EditKendaraanActivity.this).setLoginUser(user);
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FirebaseToken response) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(FirebaseToken.class);
        realm.copyToRealm(response);
        realm.commitTransaction();
    }


}
