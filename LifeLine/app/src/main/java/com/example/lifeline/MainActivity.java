package com.example.lifeline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifeline.authentication.AuthActivity;
import com.example.lifeline.hello.HelloActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        if (isFirstRun()) {
            // Запустить приветственного активити
            newActivity(HelloActivity.class);
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null || user.isAnonymous()) {
                // Запустить активити входа в систему
                newActivity(AuthActivity.class);
            } else {
                // Запустить основное активити только после успешной регистрации пользователя
                newActivity(DashboardActivity.class);
            }
        }
    }

    private void newActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
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