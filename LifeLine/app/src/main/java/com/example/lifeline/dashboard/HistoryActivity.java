package com.example.lifeline.dashboard;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeline.R;
import com.example.lifeline.adapters.HistoryAdapter;
import com.example.lifeline.database.Database;
import com.example.lifeline.database.DatabaseManager;
import com.example.lifeline.interfaces.OnFragmentInteractionListener;
import com.example.lifeline.models.History;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HistoryActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private boolean isFirstForLayoutPerson = true;
    private RecyclerView recyclerViewHistory;
    private List<History> historyList;
    private TextView textViewHistoryEmpty;
    private Database database;
    private String userFirebaseID;

    @Override
    protected void onStart() {
        super.onStart();
        updateHistory();
    }

    private void updateHistory() {
        userFirebaseID = FirebaseAuth.getInstance().getUid();

        historyList.clear();
        historyList = database.getHistory(userFirebaseID);
        if (historyList.isEmpty()) {
            textViewHistoryEmpty.setVisibility(View.VISIBLE);
        } else {
            textViewHistoryEmpty.setVisibility(View.GONE);
        }

        HistoryAdapter historyAdapter = new HistoryAdapter(this, historyList);
        recyclerViewHistory.setAdapter(historyAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        database = DatabaseManager.getDatabase();

        AtomicInteger currentTopForRecyclerView = new AtomicInteger();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerViewHistory), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            // Сохраняем текущие отступы из XML
            if (isFirstForLayoutPerson) {
                currentTopForRecyclerView.set(mlp.topMargin);
                isFirstForLayoutPerson = false;
            }
            // Добавляем системные отступы к текущим отступам
            mlp.topMargin = currentTopForRecyclerView.get() + insets.top;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });

        textViewHistoryEmpty = findViewById(R.id.textViewHistoryEmpty);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);

        historyList = new ArrayList<>();

        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onFragmentInteraction(int resultCode) {
        if (resultCode == RESULT_OK) {
            updateHistory();
        }
    }
}