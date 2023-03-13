package com.insoftbumdesku.driver.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoinRequestJson {
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
