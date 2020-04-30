package com.example.vegantranslations.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.vegantranslations.data.repository.local.AppDatabase;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.Purpose;
import com.example.vegantranslations.data.repository.DataLoader;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class SearchAsGuestViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private LiveData<List<NonVeganProduct>> nonVeganProducts;
    private LiveData<List<Purpose>> purposes;
    private MutableLiveData<String> productId;
    private final String TAG = SearchAsGuestViewModel.class.getName();

    public SearchAsGuestViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(super.getApplication().getApplicationContext());
        nonVeganProducts = appDatabase.nonVeganProductDao().getAll();
        productId = new MutableLiveData<>();
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

    public boolean isLoggedOut() {
        Log.d(TAG, String.valueOf(firebaseAuth.getCurrentUser()));
        return firebaseAuth.getCurrentUser() == null;
    }

    public void signOut() {
        firebaseAuth.signOut();
    }
}
