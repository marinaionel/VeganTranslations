package com.example.vegantranslations.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.vegantranslations.model.local.db.AppDatabase;
import com.example.vegantranslations.model.local.db.entities.Alternative;
import com.example.vegantranslations.model.local.db.entities.NonVeganProduct;
import com.example.vegantranslations.model.local.db.entities.Purpose;

import java.util.List;
import java.util.Objects;

public class ShowResultsViewModel extends AndroidViewModel {
    private AppDatabase appDatabase = AppDatabase.getAppDatabase(super.getApplication().getApplicationContext());
    private LiveData<List<Alternative>> alternatives;
    private MutableLiveData<Pair<NonVeganProduct, Purpose>> pairMutableLiveData;
    private final String TAG = ShowResultsViewModel.class.getName();

    public ShowResultsViewModel(@NonNull Application application) {
        super(application);

        pairMutableLiveData = new MutableLiveData<>();

        alternatives = Transformations.switchMap(pairMutableLiveData, pair -> appDatabase.alternativeDao().getAlternativesForProductByPurpose(Objects.requireNonNull(pair.first).getId(), Objects.requireNonNull(pair.second).getId()));
    }

    public LiveData<List<Alternative>> getAlternatives() {
        return alternatives;
    }

    public void passIntentParams(NonVeganProduct product, Purpose purpose) {
        pairMutableLiveData.setValue(new Pair<>(product, purpose));
    }
}

