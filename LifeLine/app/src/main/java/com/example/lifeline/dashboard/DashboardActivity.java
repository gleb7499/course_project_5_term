package com.example.lifeline.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

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

    private void setMargins(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.topMargin = insets.top;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        setMargins(findViewById(R.id.Frame));

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new PersonFragment());
        fragments.add(new PlusFragment());

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

        getSupportFragmentManager().beginTransaction().replace(R.id.Frame, fragments.get(0)).commit();

    }
}
