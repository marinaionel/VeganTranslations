package com.example.vegantranslations.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vegantranslations.R;
import com.example.vegantranslations.viewModel.AddAlternativeViewModel;
import com.unstoppable.submitbuttonview.SubmitButton;

public class AddAlternative extends AppCompatActivity {
    private AddAlternativeViewModel addAlternativeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alternative);

        addAlternativeViewModel = new ViewModelProvider(this).get(AddAlternativeViewModel.class);

        TextView nameOfProduct = findViewById(R.id.name_of_the_product);
        nameOfProduct.setText("");
        TextView description = findViewById(R.id.description_tv);
        description.setText("");

        SubmitButton submitButton = findViewById(R.id.add_vegan_alternatives);
        submitButton.setOnClickListener(v -> addAlternativeViewModel.addAlternative(nameOfProduct.getText().toString(), description.getText().toString()));

        addAlternativeViewModel.getInfo().observe(this, (text) -> {
            if (text.equals(getString(R.string.add_alternative_success))) {
                nameOfProduct.setText("");
                description.setText("");
            }
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            submitButton.reset();
            if (text.equals(getString(R.string.add_alternative_success))) {
                finish();
            }
        });
    }
}