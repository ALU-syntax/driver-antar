package com.insoftbumdesku.driver.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.insoftbumdesku.driver.R;
import com.insoftbumdesku.driver.activity.interfaces.wilayahinterface;
import com.insoftbumdesku.driver.constants.BaseApp;
import com.insoftbumdesku.driver.item.WilayahItem;
import com.insoftbumdesku.driver.json.DaftarWilayahRequestJson;
import com.insoftbumdesku.driver.json.DaftarWilayahResponseJson;
import com.insoftbumdesku.driver.json.UpdateWilayahRequestJson;
import com.insoftbumdesku.driver.json.UpdateWilayahResponseJson;
import com.insoftbumdesku.driver.models.User;
import com.insoftbumdesku.driver.utils.api.ServiceGenerator;
import com.insoftbumdesku.driver.utils.api.service.DriverService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihWilayahActivity extends AppCompatActivity implements wilayahinterface {
    private RecyclerView rvwilayah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_wilayah);
        rvwilayah = findViewById(R.id.rvwilayah);
        fetchdata();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvwilayah.setLayoutManager(llm);

    }

    private void fetchdata() {
        User loginuser = BaseApp.getInstance(this).getLoginUser();
        DriverService driverservice = ServiceGenerator.createService(
                DriverService.class, loginuser.getNoTelepon(), loginuser.getPassword()
        );
        DaftarWilayahRequestJson param = new DaftarWilayahRequestJson();
        param.setWilayah(1);
        driverservice.daftarwilayah(param).enqueue(new Callback<DaftarWilayahResponseJson>() {
            @Override
            public void onResponse(Call<DaftarWilayahResponseJson> call, Response<DaftarWilayahResponseJson> response) {
                if(response.isSuccessful()) {
                    WilayahItem item = new WilayahItem(PilihWilayahActivity.this, response.body().getData(), PilihWilayahActivity.this);
                    item.notifyDataSetChanged();
                    rvwilayah.setAdapter(item);
                }
            }

            @Override
            public void onFailure(Call<DaftarWilayahResponseJson> call, Throwable t) {

            }
        });
    }

    @Override
    public void updatewilayah(int id) {
        User loginuser = BaseApp.getInstance(this).getLoginUser();
        DriverService driverservice = ServiceGenerator.createService(
                DriverService.class, loginuser.getNoTelepon(), loginuser.getPassword()
        );

        UpdateWilayahRequestJson param = new UpdateWilayahRequestJson();
        param.setIdcabang(id);
        param.setIduser(loginuser.getId());
        driverservice.updatewilayah(param).enqueue(new Callback<UpdateWilayahResponseJson>() {
            @Override
            public void onResponse(Call<UpdateWilayahResponseJson> call, Response<UpdateWilayahResponseJson> response) {
                if(response.isSuccessful()) {
                    String resultcode = response.body().getResultcode();
                    String pesan = response.body().getPesan();
                    if(resultcode.equalsIgnoreCase("00")) {
                        Intent intent = new Intent(PilihWilayahActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(PilihWilayahActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateWilayahResponseJson> call, Throwable t) {

            }
        });

    }
}