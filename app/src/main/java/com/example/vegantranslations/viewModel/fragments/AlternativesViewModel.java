package com.example.vegantranslations.viewModel.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.AppDatabase;

import java.util.List;

public class AlternativesViewModel extends AndroidViewModel {
    private LiveData<List<Alternative>> alternatives;
    private AppDatabase appDatabase;
    private MutableLiveData<String> query;

    public AlternativesViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(super.getApplication().getApplicationContext());
        query = new MutableLiveData<>();
        alternatives = Transformations.switchMap(query, query -> query.equals("%%") ? appDatabase.alternativeDao().getAll() : appDatabase.alternativeDao().searchFor(query));
        filter("");
    }

    public LiveData<List<Alternative>> getAlternatives() {
        return alternatives;
    }

    public void filter(String text) {
        query.setValue("%" + (text == null ? "" : text) + "%");
    }
}