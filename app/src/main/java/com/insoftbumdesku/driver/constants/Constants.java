package com.insoftbumdesku.driver.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;




public class Constants {

//    private static final String BASE_URL = "https://motest.m-ojek.com/";
    private static final String BASE_URL = "https://admin.bumdes-ku.com/";
    public static final String CONNECTION = BASE_URL + "api/";
    public static final String IMAGESFITUR = BASE_URL + "images/fitur/";
    public static final String IMAGESBANK = BASE_URL + "images/bank/";
    public static final String IMAGESDRIVER = BASE_URL + "images/fotodriver/";
    public static final String IMAGESUSER = BASE_URL + "images/pelanggan/";
    public static final String IMAGESMERCHANT = BASE_URL + "images/merchant/";
    public static String CURRENCY = "";

    public static long duration;
    public static Double LATITUDE;
    public static Double LONGITUDE;
    public static String LOCATION;

    public static String TOKEN = "token";

    public static String USERID = "uid";

    public static String PREF_NAME = "pref_name";

    public static int permission_camera_code = 786;
    public static int permission_write_data = 788;
    public static int permission_Read_data = 789;
    public static int permission_Recording_audio = 790;
    
    
//    berdassarkan semua yang ada dan dapat direncanakan harus bisa berjalan dengan apa adanya

    public static SimpleDateFormat df =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public static String versionname = "1.0";


}
