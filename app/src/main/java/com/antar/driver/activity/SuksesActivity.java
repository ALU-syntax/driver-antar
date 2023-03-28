package com.antar.driver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.antar.driver.R;
import com.antar.driver.utils.LocaleHelper;

public class SuksesActivity extends AppCompatActivity {

    private Button btnoke;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukses);
        btnoke = findViewById(R.id.btn_oke);

        btnoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(SuksesActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}