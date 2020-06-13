package com.example.vegantranslations.data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreRepository implements Repository {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static AppDatabase appDatabase;
    private final String TAG = FirestoreRepository.class.getName();
    private Context context;
    private DataLoader dataLoader = DataLoader.getInstance(context);


    public FirestoreRepository(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    @Override
    public void addAlternative(String name, String description) {
        Map<String, Object> document = new HashMap<>();
        document.put("name", name);
        document.put("description", description);
        firestore.collection(Collections.ALTERNATIVES).add(document);
        dataLoader.loadAlternatives();
    }
}
