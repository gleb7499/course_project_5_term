package com.example.lifeline.models;

public class Donations {
    private String donationID;
    private String userFirebaseID;
    private String donationDate;
    private String donationTypeID;
    private String quantity;

    {
        donationID = "";
        userFirebaseID = "";
        donationDate = "";
        donationTypeID = "";
        quantity = "";
    }

    public Donations(String donationID, String userFirebaseID, String donationDate, String donationTypeID, String quantity) {
        this.donationID = donationID;
        this.userFirebaseID = userFirebaseID;
        this.donationDate = donationDate;
        this.donationTypeID = donationTypeID;
        this.quantity = quantity;
    }

    public Donations() {
    }

    public String getDonationID() {
        return donationID;
    }

    public String getUserFirebaseID() {
        return userFirebaseID;
    }

    public void setUserFirebaseID(String userFirebaseID) {
        this.userFirebaseID = userFirebaseID;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }

    public String getDonationTypeID() {
        return donationTypeID;
    }

    public void setDonationTypeID(String donationTypeID) {
        this.donationTypeID = donationTypeID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
