package com.antar.driver.models;

import java.io.Serializable;

import static com.antar.driver.json.fcm.FCMType.OTHER;


public class Notif implements Serializable{
    public int type = OTHER;
    public String title;
    public String message;
}
