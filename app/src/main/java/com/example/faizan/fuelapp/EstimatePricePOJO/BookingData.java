package com.example.faizan.fuelapp.EstimatePricePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by faizan on 2/15/2018.
 */

public class BookingData {


    @SerializedName("driverId")
    @Expose
    private String driverId;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("icon")
    @Expose
    private String icon;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
