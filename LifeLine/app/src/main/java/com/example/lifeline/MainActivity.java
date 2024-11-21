package com.example.lifeline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lifeline.Authantication.AuthActivity;
import com.example.lifeline.Hello.HelloActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser user;

    private void setMargin(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            layoutParams.leftMargin = insets.left;
            layoutParams.topMargin = insets.top;
            layoutParams.rightMargin = insets.right;
            layoutParams.bottomMargin = insets.bottom;
            v.setLayoutParams(layoutParams);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        FirebaseApp.initializeApp(this);

        Intent intent = new Intent(this, HelloActivity.class);
        startActivity(intent);
        finish();

//        if (isFirstRun()) {
//            // Запустить приветственного активити
//            Intent intent = new Intent(this, HelloActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            user = FirebaseAuth.getInstance().getCurrentUser();
//            if (user == null) {
//                // Запустить активити входа в систему
//                Intent intent = new Intent(this, AuthActivity.class);
//                startActivity(intent);
//                finish();
//            }
//            // Запустить основное активити
//            setContentView(R.layout.activity_main);
//        }
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