package com.example.lifeline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifeline.authentication.AuthActivity;
import com.example.lifeline.hello.HelloActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.Contract;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcherForHelloActivity;
    private ActivityResultLauncher<Intent> launcherForAuthActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        launcherForHelloActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {});
        launcherForAuthActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {});

        if (isFirstRun()) {
            // Запустить приветственного активити
            startActivity(newActivity(HelloActivity.class));
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null || user.isAnonymous()) {
                // Запустить активити входа в систему
                startActivity(newActivity(AuthActivity.class));
            }
        }
        // Запустить основное активити только после успешной регистрации пользователя
        startActivity(newActivity(DashboardActivity.class));
        finish();
    }

    @NonNull
    @Contract("_ -> new")
    private Intent newActivity(Class<?> cls) {
        return new Intent(this, cls);
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