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
    private final RequestQueueSingleton requestQueue;

    public AlternativesAdapter(List<Alternative> alternatives, Context context) {
        this.alternatives = alternatives;
        this.context = context;
        requestQueue = RequestQueueSingleton.getInstance(this.context);
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
        getImage(alternative.getName(), holder.getImage());
    }

    private void getImage(String query, ImageView image) {
        query = query.replace("\\s+", "%20");
        AtomicReference<String> result = new AtomicReference<>();
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL + query + QUERY_STATIC_PARAMS, null, response -> {
                Log.d(TAG, response.toString());
                try {
                    String thumbnail = "https:" + response.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(0).getString("thumbnail");
                    Picasso.get()
                            .load(thumbnail)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .fit()
                            .transform(new CircleTransform())
                            .centerCrop()
                            .into(image);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.e(TAG, "Site Info Error: " + error.getMessage());
                result.set(null);
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
//                  Simulate a browser client :)
                    headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
                    return headers;
                }
            };
            requestQueue.addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
