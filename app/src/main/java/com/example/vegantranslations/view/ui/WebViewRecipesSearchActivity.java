package com.example.vegantranslations.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import com.example.vegantranslations.R;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.viewModel.MainActivityViewModel;
import com.example.vegantranslations.viewModel.WebViewRecipesSearchViewModel;

public class WebViewRecipesSearchActivity extends AppCompatActivity {
    private WebView webView;
    private final String query = "https://www.google.com/search?q=recipes+with+";
    private WebViewRecipesSearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_recipes_search);

        viewModel = new ViewModelProvider(this).get(WebViewRecipesSearchViewModel.class);

        webView = findViewById(R.id.webView);
        webView.canGoBack();
        webView.canGoForward();
        Alternative alternative = (Alternative) getIntent().getSerializableExtra(getString(R.string.alternative));
        webView.loadUrl(query + alternative.getName().replace("\\s{1,}", "+"));
    }
}