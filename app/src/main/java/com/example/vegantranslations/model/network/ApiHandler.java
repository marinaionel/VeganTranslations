package com.example.vegantranslations.model.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.example.vegantranslations.model.network.ApiConstants.API_URL;
import static com.example.vegantranslations.model.network.ApiConstants.QUERY_STATIC_PARAMS;

public class ApiHandler implements IApiHandler {
    private final String TAG = ApiHandler.class.getName();
    private final RequestQueueSingleton requestQueueSingleton;

    public ApiHandler(Context context) {
        requestQueueSingleton = RequestQueueSingleton.getInstance(context);
    }

    @Override
    public void getImage(String name, Function<String, Void> callback) {
        String query = name.replace("\\s+", "%20");
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL + query + QUERY_STATIC_PARAMS, null, response -> {
                Log.d(TAG, response.toString());
                try {
                    String thumbnail = "https:" + response.getJSONObject("data")
                            .getJSONObject("result").getJSONArray("items")
                            .getJSONObject(0)
                            .getString("thumbnail");

                    Log.d(TAG, thumbnail);
                    callback.apply(thumbnail);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.e(TAG, "Site Info Error: " + error.getMessage());
                callback.apply(null);
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
//                                  Simulate a browser client :)
                    headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
                    return headers;
                }
            };
            requestQueueSingleton.addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
