package com.example.vegantranslations.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vegantranslations.R;
import com.example.vegantranslations.service.model.db.NonVeganProduct;
import com.example.vegantranslations.service.model.db.Purpose;
import com.example.vegantranslations.viewModel.MainActivityViewModel;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.vegantranslations.service.Collections.NON_VEGAN_PRODUCTS;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private MaterialSpinner products;
    private MaterialSpinner purposes;
    private SubmitButton makeItVegan;

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
//        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
//        makeItVegan.setAnimation(myAnim);
        makeItVegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeItVegan.doResult(true);
//                v.startAnimation(myAnim);
            }
        });

    }
}
