package com.example.vegantranslations.view.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegantranslations.R;
import com.example.vegantranslations.viewModel.fragments.SearchAlternativesAsAdminViewModel;

public class SearchAlternativesAsAdminFragment extends Fragment {

    private SearchAlternativesAsAdminViewModel searchAlternativesAsAdminViewModel;
    private RecyclerView recyclerViewAlternatives;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_alternatives, container, false);
        searchAlternativesAsAdminViewModel = new ViewModelProvider(this).get(SearchAlternativesAsAdminViewModel.class);
        recyclerViewAlternatives = root.findViewById(R.id.recycler_view_alternatives);
        recyclerViewAlternatives.setHasFixedSize(true);
        recyclerViewAlternatives.setLayoutManager(new LinearLayoutManager(root.getContext()));
        return root;
    }
}