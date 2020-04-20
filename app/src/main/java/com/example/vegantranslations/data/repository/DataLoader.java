package com.example.vegantranslations.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.vegantranslations.data.local.AppDatabase;
import com.example.vegantranslations.data.model.db.Category;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.ProductPurpose;
import com.example.vegantranslations.data.model.db.Purpose;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.vegantranslations.data.Collections.CATEGORY;
import static com.example.vegantranslations.data.Collections.NON_VEGAN_PRODUCTS;
import static com.example.vegantranslations.data.Collections.PRODUCT_PURPOSE_ALTERNATIVE;
import static com.example.vegantranslations.data.Collections.PURPOSE;

public class DataLoader {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
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
        loadCategories();
        loadPurposes();
    }

    private void loadCategories() {
        firestore.collection(CATEGORY)
                .get()
                .addOnCompleteListener(task -> {
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
                });
    }

    private void loadProducts() {
        firestore.collection(NON_VEGAN_PRODUCTS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    String id = document.getId();
                    NonVeganProduct tmp = new NonVeganProduct((String) document.get("name"), document.getId(), ((DocumentReference) document.get("category_id")).getId());
                    ArrayList<DocumentReference> purposeReferences = (ArrayList<DocumentReference>) document.get("purposes");
                    for (DocumentReference i : purposeReferences) {
                        ProductPurpose productPurpose = new ProductPurpose();
                        productPurpose.setProductId(tmp.getId());
                        productPurpose.setPurposeId(i.getId());
                        appDatabase.nonVeganProductDao().insertProductPurpose(productPurpose);
                    }
                    appDatabase.nonVeganProductDao().insertAll(tmp);
                }
                loadProductPurposeAlternative();
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    private void loadPurposes() {
        firestore.collection(PURPOSE).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    String id = document.getId();
                    Purpose tmp = new Purpose(id, (String) document.get("name"));
                    appDatabase.purposeDao().insertAll(tmp);
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    private void loadProductPurposeAlternative() {
        firestore.collection(PRODUCT_PURPOSE_ALTERNATIVE).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    String id = document.getId();
                    Purpose tmp = new Purpose(id, (String) document.get("name"));
                    appDatabase.purposeDao().insertAll(tmp);
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
}
