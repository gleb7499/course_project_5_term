package com.example.lifeline.models;

public class Users {
    private String userFirebaseID;
    private String username;
    private int bloodTypeID;
    private int rhesusFactorID;

    public Users() {
    }

    public String getUserFirebaseID() {
        return userFirebaseID;
    }

    public void setUserFirebaseID(String userFirebaseID) {
        this.userFirebaseID = userFirebaseID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBloodTypeID() {
        return bloodTypeID;
    }

    public void setBloodTypeID(int bloodTypeID) {
        this.bloodTypeID = bloodTypeID;
    }

    public int getRhesusFactorID() {
        return rhesusFactorID;
    }

    public void setRhesusFactorID(int rhesusFactorID) {
        this.rhesusFactorID = rhesusFactorID;
    }
}
