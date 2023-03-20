package com.antar.driver.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.antar.driver.constants.Constants;


public class SettingPreference {

    private static final String KEY_AUTO_BID = "AUTO_BID";
    private static final String KEY_MAKSIMAL_BELANJA = "MAKSIMAL_BELANJA";
    private static final String KEY_KERJA = "KERJA";
    private static final String KEY_NOTIF = "NOTIF";
    private static final String CURRENCY = "Rp";
    private static final String ABOUTUS = "ABOUTUS";
    private static final String EMAIL = "EMAIL";
    private static final String PHONE = "PHONE";
    private static final String WEBSITE = "WEBSITE";
    private static final String CURRENCYTEXT = "CURRENCYTEXT";

    private static final String MPSTATUS = "MPSTATUS";
    private static final String MPACTIVE = "MPACTIVE";
    private static final String MOBILEPULSAUSERNAME = "MOBILEPULSAUSERNAME";
    private static final String MOBILEPULSAAPIKEY = "MOBILEPULSAAPIKEY";

    private static final String STATUSDRIVER = "statusdriver";

    private final SharedPreferences pref;

    private SharedPreferences.Editor editor;

    public SettingPreference(Context context) {
        pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void updateStatusdriver(String string) {
        editor = pref.edit();
        editor.putString(STATUSDRIVER, string);
        editor.apply();
    }

    public void updateAutoBid(String autoBid) {
        editor = pref.edit();
        editor.putString(KEY_AUTO_BID, autoBid);
        editor.apply();
    }

    public void updateMaksimalBelanja(String max) {
        editor = pref.edit();
        editor.putString(KEY_MAKSIMAL_BELANJA, max);
        editor.apply();
    }

    public void updateKerja(String kerja) {
        editor = pref.edit();
        editor.putString(KEY_KERJA, kerja);
        editor.apply();
    }

    public void updateCurrency(String kerja) {
        editor = pref.edit();
        editor.putString(CURRENCY, kerja);
        editor.apply();
    }

    public void updateNotif(String version) {
        editor = pref.edit();
        editor.putString(KEY_NOTIF, version);
        editor.apply();
    }

    public void updateabout(String string) {
        editor = pref.edit();
        editor.putString(ABOUTUS, string);
        editor.apply();
    }

    public void updateemail(String string) {
        editor = pref.edit();
        editor.putString(EMAIL, string);
        editor.apply();
    }

    public void updatephone(String string) {
        editor = pref.edit();
        editor.putString(PHONE, string);
        editor.apply();
    }

    public void updateweb(String string) {
        editor = pref.edit();
        editor.putString(WEBSITE, string);
        editor.apply();
    }


    public void updatecurrencytext(String string) {
        editor = pref.edit();
        editor.putString(CURRENCYTEXT, string);
        editor.apply();
    }

    public void updatempstatus(String string) {
        editor = pref.edit();
        editor.putString(MPSTATUS, string);
        editor.apply();
    }

    public void updatempactive(String string) {
        editor = pref.edit();
        editor.putString(MPACTIVE, string);
        editor.apply();
    }

    public void updateMobilepulsausername(String string) {
        editor = pref.edit();
        editor.putString(MOBILEPULSAUSERNAME, string);
        editor.apply();
    }

    public void updateMobilepulsaapikey(String string) {
        editor = pref.edit();
        editor.putString(MOBILEPULSAAPIKEY, string);
        editor.apply();
    }

    public String[] getSetting() {

        String[] settingan = new String[20];
        settingan[0] = pref.getString(KEY_AUTO_BID, "OFF");
        settingan[1] = pref.getString(KEY_MAKSIMAL_BELANJA, "100000");
        settingan[2] = pref.getString(KEY_KERJA, "ON");
        settingan[3] = pref.getString(KEY_NOTIF, "ON");
        settingan[4] = pref.getString(CURRENCY, "$");
        settingan[5] = pref.getString(ABOUTUS, "");
        settingan[6] = pref.getString(EMAIL, "");
        settingan[7] = pref.getString(PHONE, "");
        settingan[8] = pref.getString(WEBSITE, "");
        settingan[9] = pref.getString(MPSTATUS, "1");
        settingan[10] = pref.getString(MPACTIVE, "0");
        settingan[11] = pref.getString(MOBILEPULSAUSERNAME, "123");
        settingan[12] = pref.getString(MOBILEPULSAAPIKEY, "123");
        settingan[13] = pref.getString(CURRENCYTEXT, "USD");
        settingan[19] = pref.getString(STATUSDRIVER, "0");
        return settingan;
    }

    public void logout() {
        editor = pref.edit();
        editor.putString(KEY_AUTO_BID, "");
        editor.putString(KEY_MAKSIMAL_BELANJA, "");
        editor.putString(KEY_KERJA, "");
        editor.apply();
    }
}