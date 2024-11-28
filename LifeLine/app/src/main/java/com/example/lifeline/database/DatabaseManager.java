package com.example.lifeline.database;

import android.content.Context;

public class DatabaseManager {
    private static Database database;

    public static void setDatabase(Context context) {
        database = new Database(context);
    }

    public static Database getDatabase() {
        return database;
    }
}
