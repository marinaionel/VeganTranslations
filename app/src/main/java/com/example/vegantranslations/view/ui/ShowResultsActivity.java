package com.example.vegantranslations.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.vegantranslations.R;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.Purpose;
import com.example.vegantranslations.view.adapters.ResultsAdapter;
import com.example.vegantranslations.viewModel.ShowResultsViewModel;

import java.util.List;

public class ShowResultsActivity extends AppCompatActivity {
    private ShowResultsViewModel showResultsViewModel;
    private RecyclerView recyclerView;
    private ResultsAdapter resultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setTitle("Show Alternatives");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        showResultsViewModel = new ViewModelProvider(this).get(ShowResultsViewModel.class);

        showResultsViewModel.passIntentParams((NonVeganProduct) getIntent().getSerializableExtra("product"), (Purpose) getIntent().getSerializableExtra("purpose"));

        recyclerView = findViewById(R.id.resultsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Observer<? super List<Alternative>> alternativesUpdateObserver = (Observer<List<Alternative>>) alternatives -> {
            resultsAdapter = new ResultsAdapter(alternatives, getApplicationContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(resultsAdapter);
        };
        showResultsViewModel.getAlternatives().observe(this, alternativesUpdateObserver);
        resultsAdapter.setOnItemClickListener(alternative -> {
            Intent intent = new Intent(ShowResultsActivity.this, WebViewRecipesSearchActivity.class);
            intent.putExtra(getString(R.string.alternative), alternative);
            startActivity(intent);
        });
        recyclerView.setAdapter(resultsAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
