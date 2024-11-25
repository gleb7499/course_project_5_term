package com.example.lifeline.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.lifeline.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private ArrayList<Fragment> fragments;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.Frame);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_person) {
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, fragments.get(0)).commit();
                return true;
            } else if (item.getItemId() == R.id.navigation_plus) {
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, fragments.get(1)).commit();
                return true;
            }
            return false;
        });

        fragments = new ArrayList<>();
        fragments.add(new PersonFragment());
        fragments.add(new PlusFragment());

        getSupportFragmentManager().beginTransaction().replace(R.id.Frame, fragments.get(0)).commit();

    }
}
