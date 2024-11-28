package com.example.lifeline.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Lifeline.db";
    private static final int DATABASE_VERSION = 1;
    @SuppressLint("SdCardPath")
    private static final String DATABASE_PATH = "/data/user/0/com.example.lifeline/Database/";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_PATH + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BloodTypes (bloodTypeID INTEGER PRIMARY KEY AUTOINCREMENT, bloodType TEXT)");
        db.execSQL("CREATE TABLE RhesusFactors (rhesusFactorID INTEGER PRIMARY KEY AUTOINCREMENT, rhesusFactor TEXT)");
        db.execSQL("CREATE TABLE DonationTypes (donationTypeID INTEGER PRIMARY KEY AUTOINCREMENT, donationType TEXT)");
        db.execSQL("CREATE TABLE Users (userFirebaseID TEXT PRIMARY KEY, username TEXT, bloodTypeID INTEGER, rhesusFactorID INTEGER)");
        db.execSQL("CREATE TABLE Donations (donationID INTEGER PRIMARY KEY AUTOINCREMENT, userFirebaseID INTEGER, donationDate TEXT, donationTypeID INTEGER, quantity INTEGER)");

        db.execSQL("INSERT INTO BloodTypes (bloodType) VALUES ('I (0)'), ('II (A)'), ('III (B)'), ('IV (AB)')");
        db.execSQL("INSERT INTO RhesusFactors (rhesusFactor) VALUES ('+'), ('-')");
        db.execSQL("INSERT INTO DonationTypes (donationType) VALUES ('Цельная кровь'), ('Плазма'), ('Концентрат тромбоцитов')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}
