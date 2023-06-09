package com.antar.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;



public class ItemPesananModel extends RealmObject implements Serializable {

    @Expose
    @SerializedName("nama_item")
    private String nama_item;

    @Expose
    @SerializedName("jumlah_item")
    private String jumlah_item;

    @Expose
    @SerializedName("id_merchant")
    private String id_merchant;


    public String getNama_item() {
        return nama_item;
    }

    public void setNama_item(String nama_item) {
        this.nama_item = nama_item;
    }

    public String getJumlah_item() {
        return jumlah_item;
    }

    public void setJumlah_item(String jumlah_item) {
        this.jumlah_item = jumlah_item;
    }

    public String getId_merchant() {
        return id_merchant;
    }

    public void setId_merchant(String id_merchant) {
        this.id_merchant = id_merchant;
    }
}
