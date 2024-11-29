package com.example.lifeline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeline.R;
import com.example.lifeline.models.RecyclerViewModel;

import java.util.List;

public class RecyclerViewParamAdapter extends RecyclerView.Adapter<RecyclerViewParamAdapter.paramViewHolder> {

    private final Context context;
    private final List<RecyclerViewModel> list;

    public RecyclerViewParamAdapter(Context context, List<RecyclerViewModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public paramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_param_item, parent, false);
        return new paramViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil((double) list.size() / paramViewHolder.elementsNumber);
    }

    @Override
    public void onBindViewHolder(@NonNull paramViewHolder holder, int position) {
        holder.textView_1.setText(list.get(position * paramViewHolder.elementsNumber).getValue());
        holder.imageView_1.setImageResource(list.get(position * paramViewHolder.elementsNumber).getImage());

        if (position * paramViewHolder.elementsNumber + 1 < list.size()) {
            holder.textView_2.setText(list.get(position * paramViewHolder.elementsNumber + 1).getValue());
            holder.imageView_2.setImageResource(list.get(position * paramViewHolder.elementsNumber + 1).getImage());
        } else {
            holder.cardView_2.setVisibility(View.GONE);
        }
    }

    public static final class paramViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_1;
        private final TextView textView_2;
        private final ImageView imageView_1;
        private final ImageView imageView_2;
        private final CardView cardView_2;
        private static final int elementsNumber = 2;

        public paramViewHolder(View view) {
            super(view);

            textView_1 = view.findViewById(R.id.textView_1);
            textView_2 = view.findViewById(R.id.textView_2);
            imageView_1 = view.findViewById(R.id.imageView_1);
            imageView_2 = view.findViewById(R.id.imageView_2);
            cardView_2 = view.findViewById(R.id.cardView_2);
        }
    }
}
