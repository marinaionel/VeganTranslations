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
import com.example.vegantranslations.viewModel.fragments.AddConnectionViewModel;

public class AddConnectionFragment extends Fragment {

    private AddConnectionViewModel addConnectionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addConnectionViewModel = new ViewModelProvider(this).get(AddConnectionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_connection, container, false);
        final TextView textView = root.findViewById(R.id.text_coming_soon_2);
        textView.setText(R.string.coming_soon);
        return root;
    }
}