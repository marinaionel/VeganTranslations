package com.example.vegantranslations.view.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vegantranslations.R;
import com.example.vegantranslations.viewModel.fragments.AddVeganAlternativeViewModel;

public class AddVeganAlternativeFragment extends Fragment {
    private AddVeganAlternativeViewModel addVeganAlternativeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addVeganAlternativeViewModel = new ViewModelProvider(this).get(AddVeganAlternativeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_alternatives, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        return root;
    }
}
