package com.example.vegantranslations.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vegantranslations.R;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.Purpose;
import com.example.vegantranslations.viewModel.MainActivityViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private MaterialSpinner products;
    private MaterialSpinner purposes;
    private SubmitButton makeItVegan;
    private final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        products = findViewById(R.id.products);
        purposes = findViewById(R.id.purpose);

        final ArrayAdapter<NonVeganProduct> productsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<NonVeganProduct>());
        final ArrayAdapter<Purpose> purposeArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<Purpose>());

        products.setAdapter(productsAdapter);
        purposes.setAdapter(purposeArrayAdapter);

        mainActivityViewModel.getNonVeganProducts().observe(this, new Observer<List<NonVeganProduct>>() {
            @Override
            public void onChanged(List<NonVeganProduct> nonVeganProducts) {
//                Log.d("MainActivity", Arrays.toString(nonVeganProducts.toArray()));
                productsAdapter.clear();
                productsAdapter.addAll(nonVeganProducts);
                productsAdapter.notifyDataSetChanged();
            }
        });

        mainActivityViewModel.getPurposes().observe(this, new Observer<List<Purpose>>() {
            @Override
            public void onChanged(List<Purpose> purposes) {
                purposeArrayAdapter.clear();
                purposeArrayAdapter.addAll(purposes);
                purposeArrayAdapter.notifyDataSetChanged();
            }
        });

        makeItVegan = findViewById(R.id.makeItVegan);
        makeItVegan.reset();
//        Log.d(TAG, String.valueOf(makeItVegan.getAnimation()));
        makeItVegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ShowResultsActivity.class);
                myIntent.putExtra("product", products.getSelectedIndex());
                myIntent.putExtra("purpose", purposes.getSelectedIndex());
                MainActivity.this.startActivityForResult(myIntent, 1);
                makeItVegan.doResult(true);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                makeItVegan.reset();
            }
        }
    }
}
