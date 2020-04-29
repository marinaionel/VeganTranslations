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
import java.util.Objects;

public class ShowResultsActivity extends AppCompatActivity {
    private ShowResultsViewModel showResultsViewModel;
    private RecyclerView recyclerView;
    private ResultsAdapter resultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setTitle("Show Alternatives");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        recyclerView = findViewById(R.id.resultsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showResultsViewModel = new ViewModelProvider(this).get(ShowResultsViewModel.class);

        showResultsViewModel.passIntentParams((NonVeganProduct) Objects.requireNonNull(getIntent().getSerializableExtra("product")), (Purpose) Objects.requireNonNull(getIntent().getSerializableExtra("purpose")));
        showResultsViewModel.getAlternatives().observe(this, this::onChanged);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void onChanged(List<Alternative> alternatives) {


        resultsAdapter = new ResultsAdapter(alternatives, getApplicationContext());
        resultsAdapter.setOnItemClickListener(this::onItemClick);
        recyclerView.setAdapter(resultsAdapter);
        resultsAdapter.notifyDataSetChanged();
    }

    private void onItemClick(Alternative alternative) {
        Intent intent = new Intent(ShowResultsActivity.this, WebViewRecipesSearchActivity.class);
        intent.putExtra(getString(R.string.alternative), alternative);
        startActivity(intent);
    }
}
