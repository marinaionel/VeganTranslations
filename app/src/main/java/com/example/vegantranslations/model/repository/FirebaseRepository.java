package com.example.vegantranslations.model.repository;

import android.content.Context;
import android.util.Log;

import com.example.vegantranslations.model.local.db.AppDatabase;
import com.example.vegantranslations.model.local.db.entities.Alternative;
import com.example.vegantranslations.model.local.db.entities.Category;
import com.example.vegantranslations.model.local.db.entities.NonVeganProduct;
import com.example.vegantranslations.model.local.db.entities.ProductPurpose;
import com.example.vegantranslations.model.local.db.entities.ProductPurposeAlternative;
import com.example.vegantranslations.model.local.db.entities.Purpose;
import com.example.vegantranslations.model.network.ApiHandler;
import com.example.vegantranslations.model.network.IApiHandler;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.vegantranslations.model.repository.Collections.ALTERNATIVES;
import static com.example.vegantranslations.model.repository.Collections.CATEGORY;
import static com.example.vegantranslations.model.repository.Collections.NON_VEGAN_PRODUCTS;
import static com.example.vegantranslations.model.repository.Collections.PRODUCT_PURPOSE_ALTERNATIVE;
import static com.example.vegantranslations.model.repository.Collections.PURPOSE;
import static java.util.Objects.requireNonNull;

public class FirebaseRepository implements Repository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static AppDatabase appDatabase;
    private final String TAG = FirebaseRepository.class.getName();
    private Context context;
    private IApiHandler apiHandler;

    public FirebaseRepository(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getAppDatabase(context);
        apiHandler = new ApiHandler(context);
        populateLocalDatabaseFromFirebase();
    }

    @Override
    public void addAlternative(String name, String description) {
        Map<String, Object> document = new HashMap<>();
        document.put("name", name);
        document.put("description", description);
        db.collection(ALTERNATIVES).add(document);
        loadAlternatives();
    }

    private void populateLocalDatabaseFromFirebase() {
        loadCategories();
        loadPurposes();
        loadAlternatives();
    }

    private void loadCategories() {
        db.collection(CATEGORY)
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
        db.collection(NON_VEGAN_PRODUCTS).get().addOnCompleteListener(task -> {
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
        db.collection(PURPOSE).get().addOnCompleteListener(task -> {
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
        db.collection(ALTERNATIVES).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    String id = document.getId();
                    Alternative tmp = new Alternative(id, (String) document.get("name"), (String) document.get("description"));
                    if (document.get("image_url") == null) {
                        apiHandler.getImage(tmp.getName(), (name) -> {
                            db.collection(ALTERNATIVES).document(tmp.getId()).update("image_url", name);
                            tmp.setImageUrl(name);
                            appDatabase.alternativeDao().insertAll(tmp);
                            return null;
                        });
                    } else {
                        tmp.setImageUrl(document.getString("image_url"));
                        appDatabase.alternativeDao().insertAll(tmp);
                    }
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    private void loadProductPurposeAlternative() {
        db.collection(PRODUCT_PURPOSE_ALTERNATIVE).get().addOnCompleteListener(task -> {
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
