package com.insoftbumdesku.driver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.insoftbumdesku.driver.R;

public class WilayahActivity extends AppCompatActivity {
    private EditText etwilayah;
    private Button btnupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilayah);

        etwilayah = findViewById(R.id.etwilayah);
        btnupdate = findViewById(R.id.btnupdate);

        etwilayah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WilayahActivity.this, PilihWilayahActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }
}