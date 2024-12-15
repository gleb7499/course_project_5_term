package com.example.lifeline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeline.R;
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

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getDate());
//        holder.cardView.setOnClickListener(view -> ViewFragment);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final CardView cardView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
