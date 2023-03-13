package com.insoftbumdesku.driver.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.insoftbumdesku.driver.R;
import com.insoftbumdesku.driver.constants.BaseApp;
import com.insoftbumdesku.driver.constants.Constants;
import com.insoftbumdesku.driver.json.UpdateRegionRequestJson;
import com.insoftbumdesku.driver.json.UpdateRegionResponseJson;
import com.insoftbumdesku.driver.models.User;
import com.insoftbumdesku.driver.utils.api.ServiceGenerator;
import com.insoftbumdesku.driver.utils.api.service.DriverService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class StnkActivity extends AppCompatActivity {
    private TextView textnotif;
    private LinearLayout stnkcontainer, kendaraancontainer;
    private ImageView fotostnk, fotokendaraan;
    private Button btnupdate;
    byte[] imageByteArraystnk, imageByteArraykendaraan;
    Bitmap decodedstnk, decodedkendaraan;
    private RelativeLayout rlnotif, rlprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stnk);
        stnkcontainer = findViewById(R.id.stnkcontainer);
        kendaraancontainer = findViewById(R.id.kendaraancontainer);
        fotostnk = findViewById(R.id.fotostnk);
        fotokendaraan = findViewById(R.id.fotokendaraan);
        btnupdate = findViewById(R.id.btnupdate);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);

        fotostnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImagestnk();
            }
        });

        fotokendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImagekendaraan();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageByteArraystnk == null) {
                    notif("STNK Tidak Boleh Kosong...!");
                } else if (imageByteArraykendaraan == null) {
                    notif("Foto Kendaraan Tidak Boleh Kosong..!");
                } else {
                    updateregion();
                }




            }
        });
    }


    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
    }

    public void progresshide() {
        rlprogress.setVisibility(GONE);
    }

    private void selectImagestnk() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 5);
        }
    }

    private void selectImagekendaraan() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 6);
        }
    }

    private boolean check_ReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constants.permission_Read_data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {
            if (data != null) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                fotostnk.setImageBitmap(rotatedBitmap);
                imageByteArraystnk = baos.toByteArray();
                decodedstnk = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
            }
        } else if (requestCode == 6) {
            if (data != null) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                fotokendaraan.setImageBitmap(rotatedBitmap);
                imageByteArraykendaraan = baos.toByteArray();
                decodedkendaraan = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
            }

        }
    }

    public String getStringImagestnk(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        imageByteArraystnk = baos.toByteArray();
        return Base64.encodeToString(imageByteArraystnk, Base64.DEFAULT);
    }

    public String getStringImagekendaraan (Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        imageByteArraykendaraan = baos.toByteArray();
        return Base64.encodeToString(imageByteArraykendaraan, Base64.DEFAULT);
    }

    public void notif(String text){
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);
         new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(GONE);
            }
        }, 3000);
    }

    private void updateregion() {
        progressshow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        DriverService driverService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());

        UpdateRegionRequestJson param = new UpdateRegionRequestJson();
        param.setId_user(loginUser.getId());
        if (imageByteArraystnk != null) {
            param.setStnk(getStringImagestnk(decodedstnk));
        }

        if (imageByteArraykendaraan != null) {
            param.setKendaraan(getStringImagekendaraan(decodedkendaraan));
        }

        driverService.updateregion(param).enqueue(new Callback<UpdateRegionResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<UpdateRegionResponseJson> call, @NonNull Response<UpdateRegionResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    String res = response.body().getResultcode();
                    if(res.equalsIgnoreCase("00")) {
                        finish();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateRegionResponseJson> call, @NonNull Throwable t) {
                finish();
            }
        });
    }

}