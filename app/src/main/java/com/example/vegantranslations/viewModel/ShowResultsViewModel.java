package com.example.vegantranslations.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vegantranslations.data.repository.local.AppDatabase;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.Purpose;
import com.example.vegantranslations.data.network.RequestQueueSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.vegantranslations.data.network.ApiConstants.API_URL;
import static com.example.vegantranslations.data.network.ApiConstants.QUERY_STATIC_PARAMS;

public class ShowResultsViewModel extends AndroidViewModel {
    private AppDatabase appDatabase = AppDatabase.getAppDatabase(super.getApplication().getApplicationContext());
    private RequestQueueSingleton requestQueueSingleton;
    private RequestQueue requestQueue;
    private MutableLiveData<List<Alternative>> alternatives = new MutableLiveData<>();

    public ShowResultsViewModel(@NonNull Application application) {
        super(application);
        this.requestQueueSingleton = RequestQueueSingleton.getInstance(super.getApplication().getApplicationContext());
        requestQueue = requestQueueSingleton.getRequestQueue();
    }

    public MutableLiveData<List<Alternative>> getAlternatives() {
        return alternatives;
    }

    // call your Rest API in init method
    private void init() {
        List<Alternative> tmp = alternatives.getValue();
        for (Alternative a : Objects.requireNonNull(tmp)) {
            a.setImageUrl(getImage(a.getName()));
        }
        alternatives.setValue(tmp);
    }

    private String getImage(String query) {
        AtomicReference<String> result = new AtomicReference<>();
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL + query + QUERY_STATIC_PARAMS, null, response -> {
                JSONArray images_results = null;
                try {
                    images_results = response.getJSONArray("images_results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject o = null;
                try {
                    o = images_results.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    result.set(o.getString("thumbnail"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> {
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get();
    }

    public void passIntentParams(NonVeganProduct product, Purpose purpose) {
        alternatives.setValue(appDatabase.alternativeDao().getAlternativesForProductByPurpose(product.getId(), purpose.getId()).getValue());
        init();
    }
}

