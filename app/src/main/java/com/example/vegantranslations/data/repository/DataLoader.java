package com.example.vegantranslations.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.vegantranslations.data.local.AppDatabase;
import com.example.vegantranslations.data.model.db.Category;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.Purpose;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.example.vegantranslations.data.Collections.CATEGORY;
import static com.example.vegantranslations.data.Collections.NON_VEGAN_PRODUCTS;
import static com.example.vegantranslations.data.Collections.PURPOSE;

public class DataLoader {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Map<String, Category> categoryMap = new HashMap<>();
    private Map<String, NonVeganProduct> nonVeganProductMap = new HashMap<>();
    private Map<String, Purpose> purposeMap = new HashMap<>();
    private final String TAG = "Data Loader";
    private static DataLoader instance = null;
    private static AppDatabase appDatabase;

    private DataLoader() {
        populateLocalDatabaseFromFirebase();
    }

    public static synchronized DataLoader getInstance(Context context) {
        if (instance == null) {
            instance = new DataLoader();
            appDatabase = AppDatabase.getAppDatabase(context);
        }
        return instance;
    }

    private void populateLocalDatabaseFromFirebase() {
        firestore.collection(CATEGORY)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String id = document.getId();
                                Category tmp = new Category(id, (String) document.get("name"));
                                appDatabase.categoryDao().insertAll(tmp);
                            }
                            loadProducts();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        loadPurposes();
    }

    private void loadProducts() {
        firestore.collection(NON_VEGAN_PRODUCTS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        String id = document.getId();
                        NonVeganProduct tmp = new NonVeganProduct((String) document.get("name"), document.getId(), (String) document.get("category_id"));
                        appDatabase.nonVeganProductDao().insertAll(tmp);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void loadPurposes() {
        firestore.collection(PURPOSE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        String id = document.getId();
                        Purpose tmp = new Purpose(document.getId(), (String) document.get("name"));
                        appDatabase.purposeDao().insertAll(tmp);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
