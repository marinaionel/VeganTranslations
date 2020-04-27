package com.example.vegantranslations.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.vegantranslations.data.local.AppDatabase;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.Purpose;
import com.example.vegantranslations.data.repository.DataLoader;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private AppDatabase appDatabase = AppDatabase.getAppDatabase(super.getApplication().getApplicationContext());
    private LiveData<List<NonVeganProduct>> nonVeganProducts;
    private LiveData<List<Purpose>> purposes;
    private MutableLiveData<String> productId = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        nonVeganProducts = appDatabase.nonVeganProductDao().getAll();
        purposes = Transformations.switchMap(productId, id -> appDatabase.purposeDao().getPurposesForProduct(id));
        DataLoader.getInstance(super.getApplication().getApplicationContext());
    }

    public LiveData<List<Purpose>> getPurposes() {
        return purposes;
    }

    public LiveData<List<NonVeganProduct>> getNonVeganProducts() {
        return nonVeganProducts;
    }

    public void setSelectedProduct(NonVeganProduct item) {
        productId.setValue(item.getId());
    }
}
