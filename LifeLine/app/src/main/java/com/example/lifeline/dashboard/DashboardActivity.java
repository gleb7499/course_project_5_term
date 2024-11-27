package com.example.lifeline.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeline.R;
import com.example.lifeline.adapters.RecyclerViewParamAdapter;
import com.example.lifeline.authentication.AuthActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textViewName;

    private List<String> list;
    private Button buttonAdd;
    private Button buttonLogOut;
    private ActivityResultLauncher<Intent> launcherForAuthActivity;

    private void setMargins(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

            // Сохраняем текущие отступы из XML
            int currentTop = mlp.topMargin;
            int currentLeft = mlp.leftMargin;
            int currentBottom = mlp.bottomMargin;
            int currentRight = mlp.rightMargin;

            // Добавляем системные отступы к текущим отступам
            mlp.topMargin = currentTop + insets.top;
            mlp.leftMargin = currentLeft + insets.left;
            mlp.bottomMargin = currentBottom + insets.bottom;
            mlp.rightMargin = currentRight + insets.right;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getWindow().setNavigationBarContrastEnforced(false);
        }

        launcherForAuthActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        });

        setMargins(findViewById(R.id.layoutPerson));

        buttonAdd = findViewById(R.id.floating_action_button);
        setMargins(buttonAdd);
        buttonAdd.setOnClickListener(v -> {
            AddFragment addFragment = new AddFragment();
            addFragment.show(getSupportFragmentManager(), AddFragment.TAG);

        });

        buttonLogOut = findViewById(R.id.iconButtonLogOut);
        buttonLogOut.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Вы уверены?")
                    .setMessage("Вы действительно хотите выйти?\nВаши данные не будут удалены.")
                    .setNeutralButton("Отмена", (dialog, which) -> {
                    })
                    .setPositiveButton("Выйти", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        launcherForAuthActivity.launch(new Intent(this, AuthActivity.class));
                    })
                    .show();
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.VERTICAL, false));

        list = new ArrayList<>();
        list.add("0.45 л");

        RecyclerViewParamAdapter adapter = new RecyclerViewParamAdapter(DashboardActivity.this, list);
        recyclerView.setAdapter(adapter);

        textViewName = findViewById(R.id.textViewName);

//        ArrayList<Fragment> fragments = new ArrayList<>();
//        fragments.add(new PersonFragment());
//        fragments.add(new PlusFragment());

//        getSupportFragmentManager().beginTransaction().replace(R.id.Frame, fragments.get(0)).commit();

    }
}
