package com.example.lifeline;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifeline.authentication.AuthActivity;
import com.example.lifeline.dashboard.DashboardActivity;
import com.example.lifeline.database.DatabaseManager;
import com.example.lifeline.hello.HelloActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcherForHelloActivity;
    private ActivityResultLauncher<Intent> launcherForAuthActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        DatabaseManager.setDatabase(this);

        // Запустить активити входа в систему
        launcherForHelloActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null || user.isAnonymous()) {
                    // Запустить активити входа в систему
                    launcherForAuthActivity.launch(new Intent(this, AuthActivity.class));
                } else {
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();
                }
            }
        });
        launcherForAuthActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                startActivity(new Intent(this, DashboardActivity.class));
                finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (isFirstRun()) {
            // Запустить приветственного активити
            launcherForHelloActivity.launch(new Intent(this, HelloActivity.class));
        } else if (user == null || user.isAnonymous()) {
            // Запустить активити входа в систему
            launcherForAuthActivity.launch(new Intent(this, AuthActivity.class));
        } else {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }

    private boolean isFirstRun() {
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            preferences.edit().putBoolean("isFirstRun", false).apply();
        }
        return isFirstRun;
    }
}