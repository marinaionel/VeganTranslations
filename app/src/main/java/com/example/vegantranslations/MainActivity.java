package com.example.vegantranslations;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vegantranslations.model.NonVeganProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.vegantranslations.repository.Collections.NON_VEGAN_PRODUCTS;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        final List<NonVeganProduct> nonVeganProducts = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(NON_VEGAN_PRODUCTS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                nonVeganProducts.add(new NonVeganProduct(document.getData(), document.getId()));
                            }
                            prepareListOfProducts(nonVeganProducts);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void prepareListOfProducts(List<NonVeganProduct> nonVeganProducts) {
        Log.d("asd", nonVeganProducts.size() + "");

        Spinner products = findViewById(R.id.products);
        ArrayAdapter<NonVeganProduct> productsAdapter = new ArrayAdapter<NonVeganProduct>(getApplicationContext(), android.R.layout.simple_spinner_item, nonVeganProducts);
        products.setAdapter(productsAdapter);

        products
    }
}
