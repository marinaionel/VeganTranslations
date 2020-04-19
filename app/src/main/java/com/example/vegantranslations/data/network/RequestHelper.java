package com.example.vegantranslations.data.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vegantranslations.R;

import org.json.JSONObject;

public class RequestHelper {
    //info: https://serpapi.com/images-results
    private final String API_URL = "https://serpapi.com/search?q=";
    private final String QUERY_STATIC_PARAMS = "&tbm=isch&ijn=0";
    private RequestQueueSingleton requestQueueSingleton;
    private RequestQueue requestQueue;

    public RequestHelper(Context context) {
        this.requestQueueSingleton = RequestQueueSingleton.getInstance(context);
        requestQueue = requestQueueSingleton.getRequestQueue();
    }

    public void getImage(String query) {
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

}
