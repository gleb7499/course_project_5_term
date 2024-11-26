package com.example.lifeline.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeline.R;
import com.example.lifeline.adapters.RecyclerViewParamAdapter;

import java.util.ArrayList;
import java.util.List;

public class PersonFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView textViewName;

    private List<String> list;

    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.del_fragment_person, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        list = new ArrayList<>();
        list.add("0.45 л");

        RecyclerViewParamAdapter adapter = new RecyclerViewParamAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        textViewName = view.findViewById(R.id.textViewName);



        return view;
    }
}