package com.example.vegantranslations.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.vegantranslations.R;
import com.example.vegantranslations.model.local.db.entities.NonVeganProduct;
import com.example.vegantranslations.model.local.db.entities.Purpose;
import com.example.vegantranslations.viewModel.SearchAsGuestViewModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.util.ArrayList;

public class SearchAsGuestActivity extends AppCompatActivity {
    private SearchAsGuestViewModel searchAsGuestViewModel;
    private SearchableSpinner products;
    private SearchableSpinner purposes;
    private SubmitButton makeItVegan;
    private final String TAG = SearchAsGuestActivity.class.getName();
    private TextView logInAsAdministrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_as_guest);

        searchAsGuestViewModel = new ViewModelProvider(this).get(SearchAsGuestViewModel.class);

        products = findViewById(R.id.products);
        purposes = findViewById(R.id.purpose);

        products.setTitle("Non-vegan product");
        purposes.setTitle("Purpose");

        final ArrayAdapter<NonVeganProduct> productsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        final ArrayAdapter<Purpose> purposesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<>());

        searchAsGuestViewModel.getNonVeganProducts().observe(this, nonVeganProducts -> {
            productsAdapter.clear();
            productsAdapter.addAll(nonVeganProducts);
            productsAdapter.notifyDataSetChanged();
        });

        searchAsGuestViewModel.getPurposes().observe(this, purposes -> {
            purposesAdapter.clear();
            purposesAdapter.addAll(purposes);
            purposesAdapter.notifyDataSetChanged();
        });

        makeItVegan = findViewById(R.id.add_vegan_alternatives);
        makeItVegan.reset();
//        Log.d(TAG, String.valueOf(makeItVegan.getAnimation()));
        makeItVegan.setOnClickListener(v -> {
            Intent myIntent = new Intent(SearchAsGuestActivity.this, ShowResultsActivity.class);
            myIntent.putExtra("product", (NonVeganProduct) products.getSelectedItem());
            myIntent.putExtra("purpose", (Purpose) purposes.getSelectedItem());
            SearchAsGuestActivity.this.startActivityForResult(myIntent, 1);
            makeItVegan.doResult(true);
        });

        products.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchAsGuestViewModel.setSelectedProduct((NonVeganProduct) products.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        products.setAdapter(productsAdapter);
        purposes.setAdapter(purposesAdapter);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchAsGuestViewModel.signOut();
    }
}
