package com.example.vegantranslations.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.network.RequestQueueSingleton;

import org.json.JSONObject;

import java.util.List;

import static com.example.vegantranslations.data.network.ApiConstants.API_URL;
import static com.example.vegantranslations.data.network.ApiConstants.QUERY_STATIC_PARAMS;

public class ShowResultsViewModel extends AndroidViewModel {
    private RequestQueueSingleton requestQueueSingleton;
    private RequestQueue requestQueue;
    private MutableLiveData<List<Alternative>> alternatives;

    public ShowResultsViewModel(@NonNull Application application) {
        super(application);
        alternatives = new MutableLiveData<>();
        init();
        this.requestQueueSingleton = RequestQueueSingleton.getInstance(super.getApplication().getApplicationContext());
        requestQueue = requestQueueSingleton.getRequestQueue();
    }

    public MutableLiveData<List<Alternative>> getAlternatives() {
        return alternatives;
    }

    // call your Rest API in init method
    private void init() {
//        alternatives.setValue();
    }

    private void getImage(String query) {
        try {
            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL + query + QUERY_STATIC_PARAMS, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

