package com.example.lifeline.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeline.R;
import com.example.lifeline.dashboard.HistoryActivity;
import com.example.lifeline.models.Info;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {

    private final Context context;
    private final List<Info> list;

    public InfoAdapter(Context context, List<Info> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InfoViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_info_item, parent, false));
    }

    private void setOnClickListenerForHistory(CardView cardView) {
        cardView.setOnClickListener(view1 -> context.startActivity(new Intent(context, HistoryActivity.class)));
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil((float) list.size() / InfoViewHolder.numberOfItemsInOneRow);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        int baseIndex = position * InfoViewHolder.numberOfItemsInOneRow;

        bindItem(holder.textView_1, holder.imageView_1, list.get(baseIndex));

        if (baseIndex + 1 < list.size()) {
            bindItem(holder.textView_2, holder.imageView_2, list.get(baseIndex + 1));
            if (isLastPosition(position)) {
                setOnClickListenerForHistory(holder.cardView_2);
            }
        } else {
            holder.cardView_2.setVisibility(View.GONE);
            if (isLastPosition(position)) {
                setOnClickListenerForHistory(holder.cardView_1);
            }
        }
    }

    private void bindItem(TextView textView, ImageView imageView, Info item) {
        textView.setText(item.getValue());
        imageView.setImageResource(item.getImage());
    }

    private boolean isLastPosition(int position) {
        return position + 1 == getItemCount();
    }


    public static final class InfoViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_1;
        private final TextView textView_2;
        private final ImageView imageView_1;
        private final ImageView imageView_2;
        private final CardView cardView_2;
        private final CardView cardView_1;
        private static final int numberOfItemsInOneRow = 2;

        public InfoViewHolder(View view) {
            super(view);

            textView_1 = view.findViewById(R.id.textView_1);
            textView_2 = view.findViewById(R.id.textView_2);
            imageView_1 = view.findViewById(R.id.imageView_1);
            imageView_2 = view.findViewById(R.id.imageView_2);
            cardView_1 = view.findViewById(R.id.cardView_1);
            cardView_2 = view.findViewById(R.id.cardView_2);
        }
    }
}
