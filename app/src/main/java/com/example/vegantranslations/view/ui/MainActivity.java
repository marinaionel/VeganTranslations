package com.example.vegantranslations.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
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
import java.util.Arrays;
import java.util.List;

import static com.example.vegantranslations.service.Collections.NON_VEGAN_PRODUCTS;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private Spinner products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        products = findViewById(R.id.products);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        final ArrayAdapter<NonVeganProduct> productsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<NonVeganProduct>());
        products.setAdapter(productsAdapter);
        mainActivityViewModel.getNonVeganProducts().observe(this, new Observer<List<NonVeganProduct>>() {
            @Override
            public void onChanged(List<NonVeganProduct> nonVeganProducts) {
                Log.d("MainActivity", Arrays.toString(nonVeganProducts.toArray()));
                productsAdapter.clear();
                productsAdapter.addAll(nonVeganProducts);
                productsAdapter.notifyDataSetChanged();
            }
        });


    }
}
