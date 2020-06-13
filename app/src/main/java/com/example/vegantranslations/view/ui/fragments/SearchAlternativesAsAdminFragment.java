package com.example.vegantranslations.view.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegantranslations.AddAlternative;
import com.example.vegantranslations.R;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.view.adapters.AlternativesAdapter;
import com.example.vegantranslations.viewModel.fragments.SearchAlternativesAsAdminViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static androidx.core.view.MenuItemCompat.getActionView;

public class SearchAlternativesAsAdminFragment extends Fragment {
    private SearchAlternativesAsAdminViewModel searchAlternativesAsAdminViewModel;
    private RecyclerView recyclerViewAlternatives;
    private View root;
    private AlternativesAdapter alternativesAdapter;
    private final String TAG = SearchAlternativesAsAdminFragment.class.getName();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search_alternatives, container, false);
        searchAlternativesAsAdminViewModel = new ViewModelProvider(this).get(SearchAlternativesAsAdminViewModel.class);

        recyclerViewAlternatives = root.findViewById(R.id.recycler_view_alternatives);
        recyclerViewAlternatives.setHasFixedSize(true);
        recyclerViewAlternatives.setLayoutManager(new LinearLayoutManager(root.getContext()));

        searchAlternativesAsAdminViewModel.getAlternatives().observe(getViewLifecycleOwner(), this::onChanged);

        TextView searchAlternatives = root.findViewById(R.id.search_alternatives);
        searchAlternatives.setText("");
        searchAlternatives.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchAlternativesAsAdminViewModel.filter(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "1");
                searchAlternativesAsAdminViewModel.filter(s.toString());
            }
        });

        FloatingActionButton fabAddAlternative = root.findViewById(R.id.fab_add_alternative);
        fabAddAlternative.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), AddAlternative.class);
            startActivity(intent);
        });
        return root;
    }

    private void onChanged(List<Alternative> alternatives) {
        Log.d(TAG, "onchanged");
        alternativesAdapter = new AlternativesAdapter(alternatives, super.getContext());
//        alternativesAdapter.setOnItemClickListener(this::onItemClick);
        recyclerViewAlternatives.setAdapter(alternativesAdapter);
        alternativesAdapter.notifyDataSetChanged();
    }
}