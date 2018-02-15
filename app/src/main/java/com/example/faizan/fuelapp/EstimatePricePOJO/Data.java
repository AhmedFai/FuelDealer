package com.example.faizan.fuelapp.EstimatePricePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by faizan on 2/15/2018.
 */

public class Data {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("bookingId")
    @Expose
    private String bookingId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("fuelTypeId")
    @Expose
    private String fuelTypeId;
    @SerializedName("fuelTypeName")
    @Expose
    private String fuelTypeName;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("fuelPrice")
    @Expose
    private Integer fuelPrice;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("bookingStatus")
    @Expose
    private String bookingStatus;
    @SerializedName("bookingData")
    @Expose
    private BookingData bookingData;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public String getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(String fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public String getFuelTypeName() {
        return fuelTypeName;
    }

    public void setFuelTypeName(String fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Integer getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(Integer fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public BookingData getBookingData() {
        return bookingData;
    }

    public void setBookingData(BookingData bookingData) {
        this.bookingData = bookingData;
    }

}
