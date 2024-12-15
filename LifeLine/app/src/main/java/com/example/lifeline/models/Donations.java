package com.example.lifeline.models;

public class Donations {
    private String userFirebaseID;
    private String donationDate;
    private String donationTypeID;
    private String quantity;

    public Donations(String userFirebaseID, String donationDate, String donationTypeID, String quantity) {
        this.userFirebaseID = userFirebaseID;
        this.donationDate = donationDate;
        this.donationTypeID = donationTypeID;
        this.quantity = quantity;
    }

    public Donations() {
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
