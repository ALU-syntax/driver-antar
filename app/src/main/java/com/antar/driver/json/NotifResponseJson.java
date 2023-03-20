package com.antar.driver.json;



import com.antar.driver.models.NotifModel;

import java.util.ArrayList;
import java.util.List;

public class NotifResponseJson {
    private String status;
    private List<NotifModel> data = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NotifModel> getData() {
        return data;
    }

    public void setData(List<NotifModel> data) {
        this.data = data;
    }
}
