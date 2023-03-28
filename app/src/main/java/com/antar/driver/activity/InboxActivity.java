package com.antar.driver.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.antar.driver.R;
import com.antar.driver.constants.BaseApp;
import com.antar.driver.item.InboxItem;
import com.antar.driver.json.NotifRequestJson;
import com.antar.driver.json.NotifResponseJson;
import com.antar.driver.models.User;
import com.antar.driver.utils.LocaleHelper;
import com.antar.driver.utils.api.ServiceGenerator;
import com.antar.driver.utils.api.service.DriverService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxActivity extends AppCompatActivity {

    private RecyclerView rvinbox;
    private ImageView backbtn;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        rvinbox = findViewById(R.id.rv_inbox);
        backbtn = findViewById(R.id.back_btn);
        loading = findViewById(R.id.loading);
        rvinbox.setHasFixedSize(true);
        rvinbox.setLayoutManager(new LinearLayoutManager(this));
        getdata();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    private void getdata() {
        loading.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        NotifRequestJson param = new NotifRequestJson();
        param.setType("driver");
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getEmail() , loginUser.getPassword());
        userService.notif(param).enqueue(new Callback<NotifResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<NotifResponseJson> call, @NonNull Response<NotifResponseJson> response) {
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    InboxItem inboxItem = new InboxItem(InboxActivity.this, Objects.requireNonNull(response.body().getData()));
                    rvinbox.setAdapter(inboxItem);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotifResponseJson> call, @NonNull Throwable t) {
                loading.setVisibility(View.GONE);
            }
        });
    }
}


