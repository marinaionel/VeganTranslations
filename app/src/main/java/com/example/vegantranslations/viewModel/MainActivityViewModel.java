package com.example.vegantranslations.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vegantranslations.service.model.db.NonVeganProduct;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<List<NonVeganProduct>> nonVeganProducts = new MutableLiveData<>();

    public LiveData<List<NonVeganProduct>> getNonVeganProducts() {
        return nonVeganProducts;
    }
}
