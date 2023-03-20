package com.antar.driver.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MobileTopUpRequestModel implements Serializable {

    @SerializedName("commands")
    String command;
    @SerializedName("username")
    String username;
    @SerializedName("sign")
    String sign;
    @SerializedName("status")
    String status;
    @SerializedName("ref_id")
    String orderId;
    @SerializedName("hp")
    String destinationPhoneNumber;
    @SerializedName("pulsa_code")
    String phoneCreditCode;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDestinationPhoneNumber() {
        return destinationPhoneNumber;
    }

    public void setDestinationPhoneNumber(String destinationPhoneNumber) {
        this.destinationPhoneNumber = destinationPhoneNumber;
    }

    public String getPhoneCreditCode() {
        return phoneCreditCode;
    }

    public void setPhoneCreditCode(String phoneCreditCode) {
        this.phoneCreditCode = phoneCreditCode;
    }
}
