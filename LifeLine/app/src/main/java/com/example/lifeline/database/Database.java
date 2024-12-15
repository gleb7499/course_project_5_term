package com.example.lifeline.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lifeline.models.Donations;
import com.example.lifeline.models.Users;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final SQLiteDatabase db;
    private final DatabaseHelper dbHelper;

    public Database(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            db.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void setUser(Users user) {
        ContentValues values = new ContentValues();
        values.put("userFirebaseID", user.getUserFirebaseID());
        values.put("username", user.getUsername());
        values.put("bloodTypeID", user.getBloodTypeID());
        values.put("rhesusFactorID", user.getRhesusFactorID());
        db.insertOrThrow("Users", null, values);
    }

    public String getUsername(String userFirebaseID) {
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT username FROM Users WHERE userFirebaseID = '" + userFirebaseID + "'", null);
        cursor.moveToFirst();
        try {
            return cursor.getString(0);
        } catch (Exception e) {
            return "Гость";
        }
    }

    public int getBloodTypeID(String data) {
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT bloodTypeID FROM BloodTypes WHERE bloodType = '" + data + "'", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public int getRhesusFactorID(String rhesusFactor) {
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT rhesusFactorID FROM RhesusFactors WHERE rhesusFactor = '" + rhesusFactor + "'", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public String getDonationTypeID(String donationType) {
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT donationTypeID FROM DonationTypes WHERE donationType = '" + donationType + "'", null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String getTotalVolumeDonations(String userFirebaseID) {
        Cursor cursor = db.rawQuery("SELECT SUM(quantity) FROM Donations WHERE userFirebaseID = ?", new String[]{userFirebaseID});
        if (cursor.moveToFirst()) {
            return cursor.getFloat(0) / 1000 + " л";
        }
        cursor.close();
        return "---";
    }

    public String getTotalDeliveries(String userFirebaseID) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Donations WHERE userFirebaseID = ?", new String[]{userFirebaseID});
        if (cursor.moveToFirst()) {
            return cursor.getInt(0) + " раз";
        }
        cursor.close();
        return "-";
    }

    public List<String> getHistory(String userFirebaseID) {
        List<String> history = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT donationDate FROM Donations WHERE userFirebaseID = ?", new String[]{userFirebaseID});
        if (cursor.moveToFirst()) {
            do {
                history.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return history;
    }

    public boolean setDonation(Donations donation) {
        if (existsDonation(donation.getDonationDate(), donation.getUserFirebaseID())) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("userFirebaseID", donation.getUserFirebaseID());
        values.put("donationDate", donation.getDonationDate());
        values.put("donationTypeID", donation.getDonationTypeID());
        values.put("quantity", donation.getQuantity());
        db.insertOrThrow("Donations", null, values);
        return true;
    }

    private boolean existsDonation(String donationDate, String userFirebaseID) {
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Donations WHERE donationDate = '" + donationDate + "' AND userFirebaseID = '" + userFirebaseID + "'", null);
        return cursor.getCount() > 0;
    }
}
