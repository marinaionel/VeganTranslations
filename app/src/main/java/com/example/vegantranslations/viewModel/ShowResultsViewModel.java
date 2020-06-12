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
    private LiveData<List<Alternative>> alternatives;
    private MutableLiveData<Pair<NonVeganProduct, Purpose>> pairMutableLiveData;
    private final String TAG = ShowResultsViewModel.class.getName();

    public ShowResultsViewModel(@NonNull Application application) {
        super(application);

        pairMutableLiveData = new MutableLiveData<>();

        alternatives = Transformations.switchMap(pairMutableLiveData, pair -> appDatabase.alternativeDao().getAlternativesForProductByPurpose(pair.first.getId(), pair.second.getId()));
    }

    public LiveData<List<Alternative>> getAlternatives() {
        return alternatives;
    }

    public void passIntentParams(NonVeganProduct product, Purpose purpose) {
        pairMutableLiveData.setValue(new Pair<>(product, purpose));
    }
}

