package com.antar.driver.activity;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.antar.driver.R;
import com.antar.driver.json.PrivacyRequestJson;
import com.antar.driver.json.PrivacyResponseJson;
import com.antar.driver.models.SettingsModel;
import com.antar.driver.utils.LocaleHelper;
import com.antar.driver.utils.NetworkUtils;
import com.antar.driver.utils.api.ServiceGenerator;
import com.antar.driver.utils.api.service.DriverService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyActivity extends AppCompatActivity {

    WebView webView;
    ImageView backbtn;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        webView = findViewById(R.id.webView);
        backbtn = findViewById(R.id.back_btn);
        webView.setBackgroundColor(Color.TRANSPARENT);
        if (NetworkUtils.isConnected(PrivacyActivity.this)) {
            get();
        }
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void get() {
        PrivacyRequestJson request = new PrivacyRequestJson();

        DriverService service = ServiceGenerator.createService(DriverService.class, "admin", "12345");
        service.privacy(request).enqueue(new Callback<PrivacyResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<PrivacyResponseJson> call, @NonNull Response<PrivacyResponseJson> response) {

                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                        SettingsModel model = response.body().getData().get(0);
                        setResult(model);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PrivacyResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setResult(SettingsModel getprivacy) {

        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText = getprivacy.getPrivacy();
        String text = "<html dir=" + "><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/NeoSans_Pro_Regular.ttf\")}body{font-family: MyFont;color: #000000;text-align:justify;line-height:1.2}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        webView.loadDataWithBaseURL(null, text, mimeType, encoding, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }
}

