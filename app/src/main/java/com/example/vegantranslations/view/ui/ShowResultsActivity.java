package com.example.vegantranslations.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vegantranslations.R;
import com.example.vegantranslations.model.local.db.entities.Alternative;
import com.example.vegantranslations.model.local.db.entities.NonVeganProduct;
import com.example.vegantranslations.model.local.db.entities.Purpose;
import com.example.vegantranslations.view.adapters.AlternativesAdapter;
import com.example.vegantranslations.viewModel.ShowResultsViewModel;

import java.util.List;
import java.util.Objects;

public class ShowResultsActivity extends AppCompatActivity {
    private ShowResultsViewModel showResultsViewModel;
    private RecyclerView recyclerView;
    private AlternativesAdapter alternativesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (alternatives == null || alternatives.size() == 0) {
            LinearLayout linearLayout = findViewById(R.id.show_results_layout);
            linearLayout.removeAllViews();

            TextView textView = new TextView(getApplicationContext());
            textView.setPaddingRelative(10, 10, 0, 0);
            textView.setText(R.string.no_results);
            textView.setTextSize(35);
            textView.setId(Integer.parseInt("5"));
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            linearLayout.addView(textView);
        } else {
            alternativesAdapter = new AlternativesAdapter(alternatives, getApplicationContext());
            alternativesAdapter.setOnItemClickListener(this::onItemClick);
            recyclerView.setAdapter(alternativesAdapter);
            alternativesAdapter.notifyDataSetChanged();
        }
    }

    private void onItemClick(Alternative alternative) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=recipes+with+" + alternative.getName().replace("\\s+", "%20")));
        startActivity(intent);
    }
}
