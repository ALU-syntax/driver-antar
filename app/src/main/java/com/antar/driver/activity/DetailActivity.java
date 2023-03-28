package com.antar.driver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.antar.driver.R;
import com.antar.driver.utils.LocaleHelper;

public class DetailActivity extends AppCompatActivity {

    private TextView judulnotif, tanggalnotif, kembali;
    private ImageView backbtn;
    private WebView isinotif;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        judulnotif = findViewById(R.id.judulnotif);
        isinotif = findViewById(R.id.isinotif);
        tanggalnotif = findViewById(R.id.tanggalnotif);
        kembali = findViewById(R.id.kembali);
        backbtn = findViewById(R.id.back_btn);

        String tjudulnotif = getIntent().getStringExtra("judulnotifintent");
        String tisinotif = getIntent().getStringExtra("isinotifintent");
        String ttanggalnotif = getIntent().getStringExtra("tanggalnotifintent");

        judulnotif.setText(tjudulnotif);
        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText = tisinotif;

        String text = "<html dir=" + "><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/NeoSans_Pro_Regular.ttf\")}body{font-family: MyFont;color: #000000;text-align:justify;line-height:1.2}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";
        isinotif.loadDataWithBaseURL(null, text, mimeType, encoding, null);
        tanggalnotif.setText(ttanggalnotif);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}