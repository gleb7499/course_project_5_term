package com.example.lifeline.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeline.R;
import com.example.lifeline.dashboard.ViewFragment;
import com.example.lifeline.dashboard.AddViewFragment;
import com.example.lifeline.models.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final Context context;
    private final List<History> list;

    public HistoryAdapter(Context context, List<History> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_history_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.textViewDate.setText(list.get(position).getDate());
        holder.textViewNumber.setText("#" + (position + 1));
        holder.itemView.setOnClickListener(view -> {
            AddViewFragment viewFragment = new ViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("date", list.get(position).getDate());
            viewFragment.setArguments(bundle);
            viewFragment.show(((FragmentActivity) context).getSupportFragmentManager(), "AddViewFragment");
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDate;
        private final TextView textViewNumber;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
        }
    }
}
