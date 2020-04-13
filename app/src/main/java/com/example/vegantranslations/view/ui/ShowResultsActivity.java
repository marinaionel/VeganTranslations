package com.example.vegantranslations.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Window;

import com.example.vegantranslations.R;
import com.example.vegantranslations.viewModel.ShowResultsViewModel;

public class ShowResultsActivity extends AppCompatActivity {
    private ShowResultsViewModel showResultsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        showResultsViewModel = new ViewModelProvider(this).get(ShowResultsViewModel.class);
    }
}
