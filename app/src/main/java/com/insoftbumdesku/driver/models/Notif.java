package com.insoftbumdesku.driver.models;

import java.io.Serializable;

import static com.insoftbumdesku.driver.json.fcm.FCMType.OTHER;


public class Notif implements Serializable{
    public int type = OTHER;
    public String title;
    public String message;
}
