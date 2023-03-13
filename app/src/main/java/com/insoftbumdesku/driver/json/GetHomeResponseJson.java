package com.insoftbumdesku.driver.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.insoftbumdesku.driver.models.TransaksiModel;
import com.insoftbumdesku.driver.models.User;

import java.util.ArrayList;
import java.util.List;



public class GetHomeResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("saldo")
    @Expose
    private String saldo;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("app_aboutus")
    @Expose
    private String aboutus;

    @SerializedName("app_email")
    @Expose
    private String email;

    @SerializedName("app_contact")
    @Expose
    private String phone;

    @SerializedName("app_website")
    @Expose
    private String website;

    @SerializedName("driver_status")
    @Expose
    private String driverstatus;

    @SerializedName("data_transaksi")
    @Expose
    private List<TransaksiModel> transaksi = new ArrayList<>();

    @SerializedName("data_driver")
    @Expose
    private List<User> datadriver = new ArrayList<>();

    @SerializedName("currency_text")
    @Expose
    private String currency_text;

    @SerializedName("mobilepulsa_username")
    @Expose
    private String mobilepulsausername;

    @SerializedName("mobilepulsa_api_key")
    @Expose
    private String mobilepulsaapikey;

    @SerializedName("mp_status")
    @Expose
    private String mpstatus;

    @SerializedName("mp_active")
    @Expose
    private String mpactive;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDriverstatus() {
        return driverstatus;
    }

    public void setDriverstatus(String driverstatus) {
        this.driverstatus = driverstatus;
    }

    public List<TransaksiModel> getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(List<TransaksiModel> transaksi) {
        this.transaksi = transaksi;
    }

    public void setDatadriver(List<User> datadriver) {
        this.datadriver = datadriver;
    }

    public List<User> getDatadriver() {
        return datadriver;
    }

    public String getCurrencytext() {
        return currency_text;
    }

    public void setCurrencytext(String currencytext) {
        this.currency_text = currencytext;
    }

    public String getMobilepulsausername() {
        return mobilepulsausername;
    }

    public void setMobilepulsausername(String mobilepulsausername) {
        this.mobilepulsausername = mobilepulsausername;
    }
    public String getMobilepulsaapikey() {
        return mobilepulsaapikey;
    }

    public void setMobilepulsaapikey(String mobilepulsaapikey) {
        this.mobilepulsaapikey = mobilepulsaapikey;
    }
    public String getMpstatus() {
        return mpstatus;
    }

    public void setMpstatus(String mpstatus) {
        this.mpstatus = mpstatus;
    }

    public String getMpactive() {
        return mpactive;
    }

    public void setMpactive(String mpactive) {
        this.mpactive = mpactive;
    }
}
