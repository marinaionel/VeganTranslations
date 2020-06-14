package com.example.vegantranslations.view.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.MetricAffectingSpan;
import android.text.style.URLSpan;
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

        nameOfProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //remove formatting
                CharacterStyle[] toBeRemovedSpans = editable.getSpans(0, editable.length(), MetricAffectingSpan.class);
                for (CharacterStyle toBeRemovedSpan : toBeRemovedSpans)
                    editable.removeSpan(toBeRemovedSpan);
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //remove formatting
                CharacterStyle[] toBeRemovedSpans = editable.getSpans(0, editable.length(), MetricAffectingSpan.class);
                for (CharacterStyle toBeRemovedSpan : toBeRemovedSpans)
                    editable.removeSpan(toBeRemovedSpan);
            }
        });

        SubmitButton submitButton = findViewById(R.id.add_vegan_alternatives);
        submitButton.setOnClickListener(v -> addAlternativeViewModel.addAlternative(nameOfProduct.getText().toString(), description.getText().toString()));

        addAlternativeViewModel.getInfo().
                observe(this, (text) ->

                {
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