package com.example.vegantranslations.localstorage;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.vegantranslations.model.Category;
import com.example.vegantranslations.model.NonVeganProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.example.vegantranslations.repository.Collections.CATEGORY;
import static com.example.vegantranslations.repository.Collections.NON_VEGAN_PRODUCTS;

public class DataLoader {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Map<String, Category> categoryMap = new HashMap<>();
    private Map<String, NonVeganProduct> nonVeganProductMap = new HashMap<>();
    private final String TAG = "Data Loader";

    public void populateLocalDatabaseFromFirebase() {
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
                                categoryMap.put(id, tmp);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        firestore.collection(NON_VEGAN_PRODUCTS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        String id = document.getId();
                        NonVeganProduct tmp = new NonVeganProduct((String) document.get("name"), document.getId(), (String) document.get("category_id"));
                        nonVeganProductMap.put(id, tmp);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
