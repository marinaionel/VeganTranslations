package com.example.vegantranslations.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.vegantranslations.R;
import com.example.vegantranslations.data.model.db.Alternative;

import java.util.List;
import java.util.Objects;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    private List<Alternative> alternatives;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ResultsAdapter(List<Alternative> alternatives, Context context) {
        this.alternatives = alternatives;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(alternatives.get(position).getName());
        holder.description.setText(alternatives.get(position).getDescription());

        Glide.with(this.context)
                .load(alternatives.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImage());
    }

    @Override
    public int getItemCount() {
        return alternatives.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;

        public ImageView getImage() {
            return this.imageView;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rowTextTitle);
            description = itemView.findViewById(R.id.rowTextDescription);
            imageView = itemView.findViewById(R.id.rowImage);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (Objects.nonNull(onItemClickListener) && position != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(alternatives.get(position));
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Alternative alternative);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
