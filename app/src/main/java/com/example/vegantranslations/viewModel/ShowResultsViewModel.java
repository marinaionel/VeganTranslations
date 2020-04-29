package com.example.vegantranslations.viewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.example.vegantranslations.data.repository.local.AppDatabase;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.Purpose;
import com.example.vegantranslations.data.network.RequestQueueSingleton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.vegantranslations.data.network.ApiConstants.API_URL;
import static com.example.vegantranslations.data.network.ApiConstants.QUERY_STATIC_PARAMS;

public class ShowResultsViewModel extends AndroidViewModel {
    private AppDatabase appDatabase = AppDatabase.getAppDatabase(super.getApplication().getApplicationContext());
    private RequestQueueSingleton requestQueueSingleton;
    private RequestQueue requestQueue;
    private LiveData<List<Alternative>> alternatives;
    private MutableLiveData<Pair<NonVeganProduct, Purpose>> pairMutableLiveData;
    private LiveData<List<Alternative>> alternativesWithImages;
    private final String TAG = ShowResultsViewModel.class.getName();

    public ShowResultsViewModel(@NonNull Application application) {
        super(application);
        this.requestQueueSingleton = RequestQueueSingleton.getInstance(super.getApplication().getApplicationContext());
        requestQueue = requestQueueSingleton.getRequestQueue();

        pairMutableLiveData = new MutableLiveData<>();
        alternativesWithImages = new MutableLiveData<>();

        alternatives = Transformations.switchMap(pairMutableLiveData, pair -> appDatabase.alternativeDao().getAlternativesForProductByPurpose(pair.first.getId(), pair.second.getId()));
        alternativesWithImages = Transformations.map(alternatives, (List<Alternative> p) -> {
            p.forEach(p1 -> p1.setImageUrl(getImage(p1.getName())));
            return p;
        });
    }

    public LiveData<List<Alternative>> getAlternatives() {
        return alternativesWithImages;
    }

    private String getImage(String query) {
        query = query.replace("\\s+", "%20");
        AtomicReference<String> result = new AtomicReference<>();
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL + query + QUERY_STATIC_PARAMS, null, response -> {
                Log.d(TAG, response.toString());
                try {
                    String thumbnail = response.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(0).getString("thumbnail");
                    result.set("https:" + thumbnail);
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
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get();
    }

    public void passIntentParams(NonVeganProduct product, Purpose purpose) {
        pairMutableLiveData.setValue(new Pair<>(product, purpose));
    }
}

