package com.example.vegantranslations.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vegantranslations.data.local.AppDatabase;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.Purpose;
import com.example.vegantranslations.data.repository.DataLoader;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private AppDatabase appDatabase = AppDatabase.getAppDatabase(super.getApplication().getApplicationContext());
    LiveData<List<NonVeganProduct>> nonVeganProducts;
    MutableLiveData<List<Purpose>> purposes = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        nonVeganProducts = appDatabase.nonVeganProductDao().getAll();
        purposes.setValue(appDatabase.purposeDao().getAll().getValue());
        DataLoader.getInstance(super.getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Purpose>> getPurposes() {
        return purposes;
    }

    public LiveData<List<NonVeganProduct>> getNonVeganProducts() {
        return nonVeganProducts;
    }

    public void selectedProduct(NonVeganProduct item) {
        if (item != null)
            purposes.setValue(appDatabase.nonVeganProductDao().getProductPurposes(item.getId()).getValue());
        else
            purposes.setValue(appDatabase.purposeDao().getAll().getValue());
    }
}
