package com.example.vegantranslations.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vegantranslations.service.local.AppDatabase;
import com.example.vegantranslations.service.model.db.NonVeganProduct;
import com.example.vegantranslations.service.model.db.Purpose;
import com.example.vegantranslations.service.repository.DataLoader;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private AppDatabase appDatabase = AppDatabase.getAppDatabase(super.getApplication().getApplicationContext());
    LiveData<List<NonVeganProduct>> nonVeganProducts;
    LiveData<List<Purpose>> purposes;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        nonVeganProducts = appDatabase.nonVeganProductDao().getAll();
        purposes = appDatabase.purposeDao().getAll();
        DataLoader.getInstance(super.getApplication().getApplicationContext());
    }

    public LiveData<List<Purpose>> getPurposes() {
        return purposes;
    }

    public LiveData<List<NonVeganProduct>> getNonVeganProducts() {
        return nonVeganProducts;
    }
}
