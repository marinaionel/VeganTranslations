package com.example.vegantranslations.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vegantranslations.R;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.network.RequestQueueSingleton;
import com.example.vegantranslations.view.transformations.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.vegantranslations.data.network.ApiConstants.API_URL;
import static com.example.vegantranslations.data.network.ApiConstants.QUERY_STATIC_PARAMS;

public class AlternativesAdapter extends Adapter<AlternativesAdapter.ViewHolder> {
    private List<Alternative> alternatives;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private final String TAG = AlternativesAdapter.class.getName();

    public AlternativesAdapter(List<Alternative> alternatives, Context context) {
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
        Alternative alternative = alternatives.get(position);
        holder.title.setText(alternative.getName());
        holder.description.setText(alternative.getDescription());
        Picasso.get()
                .load(alternative.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fit()
                .transform(new CircleTransform())
                .centerCrop()
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
        private final String TAG = ViewHolder.class.getName();

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
