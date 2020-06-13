package com.example.vegantranslations.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.model.db.Category;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.ProductPurpose;
import com.example.vegantranslations.data.model.db.ProductPurposeAlternative;
import com.example.vegantranslations.data.model.db.Purpose;
import com.example.vegantranslations.data.network.RequestQueueSingleton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.vegantranslations.data.Collections.ALTERNATIVES;
import static com.example.vegantranslations.data.Collections.CATEGORY;
import static com.example.vegantranslations.data.Collections.NON_VEGAN_PRODUCTS;
import static com.example.vegantranslations.data.Collections.PRODUCT_PURPOSE_ALTERNATIVE;
import static com.example.vegantranslations.data.Collections.PURPOSE;
import static com.example.vegantranslations.data.network.ApiConstants.API_URL;
import static com.example.vegantranslations.data.network.ApiConstants.QUERY_STATIC_PARAMS;
import static java.util.Objects.requireNonNull;

public class FirestoreRepository implements Repository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static AppDatabase appDatabase;
    private final String TAG = FirestoreRepository.class.getName();
    private Context context;
    private final RequestQueueSingleton requestQueue;
    private IApiHandler apiHandler;

    public FirestoreRepository(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getAppDatabase(context);
        requestQueue = RequestQueueSingleton.getInstance(context);
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
