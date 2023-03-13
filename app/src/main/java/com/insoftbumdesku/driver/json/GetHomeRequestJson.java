package com.insoftbumdesku.driver.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class GetHomeRequestJson {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("no_telepon")
    @Expose
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
