package com.example.vegantranslations.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vegantranslations.R;
import com.example.vegantranslations.service.model.db.NonVeganProduct;
import com.example.vegantranslations.viewModel.MainActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.vegantranslations.service.Collections.NON_VEGAN_PRODUCTS;

public class MainActivity extends AppCompatActivity {
    private AndroidViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

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
                                nonVeganProducts.add(new NonVeganProduct(document.getData().get("name").toString(), document.getId(), document.get("category_id").toString()));
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
        ArrayAdapter<NonVeganProduct> productsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nonVeganProducts);
        products.setAdapter(productsAdapter);

    }
}
