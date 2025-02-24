package com.example.lifeline.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeline.R;
import com.example.lifeline.adapters.InfoAdapter;
import com.example.lifeline.authentication.AuthActivity;
import com.example.lifeline.database.Database;
import com.example.lifeline.database.DatabaseManager;
import com.example.lifeline.interfaces.OnFragmentInteractionListener;
import com.example.lifeline.models.Info;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DashboardActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private RecyclerView recyclerView;
    private TextView textViewName;
    private List<Info> infos;
    private Button buttonAdd;
    private Button buttonLogOut;
    private Database database;
    private ActivityResultLauncher<Intent> launcherForAuthActivity;
    private String userFirebaseID;

    boolean isFirstForLayoutPerson = true;
    boolean isFirstForButtonAdd = true;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseManager.getDatabase().close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateInfo();
    }

    private void updateInfo() {
        userFirebaseID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textViewName.setText(database.getUsername(userFirebaseID));

        infos.clear();
        infos.add(new Info(database.getTotalVolumeDonations(userFirebaseID), R.drawable.donation));
        infos.add(new Info(database.getTotalDeliveries(userFirebaseID), R.drawable.score));
        infos.add(new Info("История", R.drawable.history));

        InfoAdapter adapter = new InfoAdapter(this, infos);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        database = DatabaseManager.getDatabase();

        launcherForAuthActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        });

        AtomicInteger currentTopForLayoutPerson = new AtomicInteger();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutPerson), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            // Сохраняем текущие отступы из XML
            if (isFirstForLayoutPerson) {
                currentTopForLayoutPerson.set(mlp.topMargin);
                isFirstForLayoutPerson = false;
            }
            // Добавляем системные отступы к текущим отступам
            mlp.topMargin = currentTopForLayoutPerson.get() + insets.top;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });

        buttonAdd = findViewById(R.id.floating_action_button);
        AtomicInteger currentBottomForButtonAdd = new AtomicInteger();
        ViewCompat.setOnApplyWindowInsetsListener(buttonAdd, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            // Сохраняем текущие отступы из XML
            if (isFirstForButtonAdd) {
                currentBottomForButtonAdd.set(mlp.bottomMargin);
                isFirstForButtonAdd = false;
            }
            // Добавляем системные отступы к текущим отступам
            mlp.bottomMargin = currentBottomForButtonAdd.get() + insets.bottom;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });
        buttonAdd.setOnClickListener(v -> {
            AddViewFragment addFragment = new AddFragment();
            addFragment.show(getSupportFragmentManager(), AddViewFragment.TAG);
        });

        buttonLogOut = findViewById(R.id.iconButtonLogOut);
        buttonLogOut.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Выйти из аккаунта?")
                    .setMessage("Ваши данные не будут удалены.")
                    .setNeutralButton("Отмена", (dialog, which) -> {
                    })
                    .setPositiveButton("Выйти", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        launcherForAuthActivity.launch(new Intent(this, AuthActivity.class));
                    })
                    .show();
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        infos = new ArrayList<>();

        textViewName = findViewById(R.id.textViewName);
    }

    @Override
    public void onFragmentInteraction(int resultCode) {
        if (resultCode == RESULT_OK) {
            updateInfo();
        }
    }
}
