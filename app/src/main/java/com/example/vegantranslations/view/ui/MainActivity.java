package com.example.vegantranslations.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.vegantranslations.R;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.Purpose;
import com.example.vegantranslations.view.ui.ui.login.AdministratorLoginActivity;
import com.example.vegantranslations.viewModel.MainActivityViewModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private SearchableSpinner products;
    private SearchableSpinner purposes;
    private SubmitButton makeItVegan;
    private final String TAG = MainActivity.class.getName();
    private TextView logInAsAdministrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        logInAsAdministrator = findViewById(R.id.logInAsAdministrator);
        logInAsAdministrator.setMovementMethod(LinkMovementMethod.getInstance());
        logInAsAdministrator.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdministratorLoginActivity.class);
            startActivity(intent);
        });

        products = findViewById(R.id.products);
        purposes = findViewById(R.id.purpose);

        products.setTitle("Non-vegan product");
        purposes.setTitle("Purpose");

        final ArrayAdapter<NonVeganProduct> productsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        final ArrayAdapter<Purpose> purposesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, new ArrayList<>());

        mainActivityViewModel.getNonVeganProducts().observe(this, nonVeganProducts -> {
            productsAdapter.clear();
            productsAdapter.addAll(nonVeganProducts);
            productsAdapter.notifyDataSetChanged();
        });

        mainActivityViewModel.getPurposes().observe(this, purposes -> {
            purposesAdapter.clear();
            purposesAdapter.addAll(purposes);
            purposesAdapter.notifyDataSetChanged();
        });

        products.setAdapter(productsAdapter);
        purposes.setAdapter(purposesAdapter);

        makeItVegan = findViewById(R.id.makeItVegan);
        makeItVegan.reset();
//        Log.d(TAG, String.valueOf(makeItVegan.getAnimation()));
        makeItVegan.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, ShowResultsActivity.class);
            myIntent.putExtra("product", (NonVeganProduct) products.getSelectedItem());
            myIntent.putExtra("purpose", (Purpose) purposes.getSelectedItem());
            MainActivity.this.startActivityForResult(myIntent, 1);
            makeItVegan.doResult(true);
        });

        products.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainActivityViewModel.selectedProduct((NonVeganProduct) products.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mainActivityViewModel.selectedProduct(null);
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
