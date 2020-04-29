package com.example.vegantranslations.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.vegantranslations.data.repository.local.AppDatabase;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.model.db.Category;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.ProductPurpose;
import com.example.vegantranslations.data.model.db.ProductPurposeAlternative;
import com.example.vegantranslations.data.model.db.Purpose;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import static com.example.vegantranslations.data.Collections.ALTERNATIVES;
import static com.example.vegantranslations.data.Collections.CATEGORY;
import static com.example.vegantranslations.data.Collections.NON_VEGAN_PRODUCTS;
import static com.example.vegantranslations.data.Collections.PRODUCT_PURPOSE_ALTERNATIVE;
import static com.example.vegantranslations.data.Collections.PURPOSE;
import static java.util.Objects.requireNonNull;

public class DataLoader {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final String TAG = "Data Loader";
    private static DataLoader instance = null;
    private static AppDatabase appDatabase;

    private DataLoader() {
        populateLocalDatabaseFromFirebase();
    }

    public static DataLoader getInstance(Context context) {
        if (instance == null) {
            synchronized (DataLoader.class) {
                instance = new DataLoader();
            }
            appDatabase = AppDatabase.getAppDatabase(context);
        }
        return instance;
    }

    private void populateLocalDatabaseFromFirebase() {
        loadCategories();
        loadPurposes();
        loadAlternatives();
    }

    private void loadCategories() {
        firestore.collection(CATEGORY)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
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
                for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    String id = document.getId();
                    NonVeganProduct tmp = new NonVeganProduct((String) document.get("name"), document.getId(), ((DocumentReference) document.get("category_id")).getId());
                    List<DocumentReference> purposeReferences = (List<DocumentReference>) document.get("purposes");
                    for (DocumentReference i : requireNonNull(purposeReferences)) {
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
                for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
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

    private void loadAlternatives() {
        firestore.collection(ALTERNATIVES).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    String id = document.getId();
                    Alternative tmp = new Alternative(id, (String) document.get("name"), (String) document.get("description"));
                    appDatabase.alternativeDao().insertAll(tmp);
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    private void loadProductPurposeAlternative() {
        firestore.collection(PRODUCT_PURPOSE_ALTERNATIVE).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    String id = document.getId();
                    ProductPurposeAlternative tmp = new ProductPurposeAlternative();
                    tmp.setId(id);
                    tmp.setAlternative(((DocumentReference) document.get("alternative")).getId());
                    tmp.setProductId(((DocumentReference) document.get("product")).getId());
                    tmp.setPurposeId(((DocumentReference) document.get("purpose")).getId());
                    appDatabase.alternativeDao().insertProductPurposeAlternative(tmp);
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
}
