package com.example.lifeline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeline.R;

import java.util.List;

public class RecyclerViewParamAdapter extends RecyclerView.Adapter<RecyclerViewParamAdapter.paramViewHolder> {

    private final Context context;
    private final List<String> list;

    public RecyclerViewParamAdapter(Context context, List<String> list) {
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
        return list.size() / paramViewHolder.elementsNumber + 1;
    }

    @Override
    public void onBindViewHolder(@NonNull paramViewHolder holder, int position) {
        holder.textView_1.setText(list.get(position * paramViewHolder.elementsNumber));
        holder.imageView_1.setImageResource(R.drawable.donation);
    }

    public static final class paramViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_1;
        private final TextView textView_2;
        private final ImageView imageView_1;
        private final ImageView imageView_2;
        private static final int elementsNumber = 2;

        public paramViewHolder(View view) {
            super(view);

            textView_1 = view.findViewById(R.id.textView_1);
            textView_2 = view.findViewById(R.id.textView_2);
            imageView_1 = view.findViewById(R.id.imageView_1);
            imageView_2 = view.findViewById(R.id.imageView_2);
        }
    }
}
