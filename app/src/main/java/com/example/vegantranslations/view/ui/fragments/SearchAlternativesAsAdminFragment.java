package com.example.vegantranslations.view.ui.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegantranslations.view.ui.AddAlternative;
import com.example.vegantranslations.R;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.view.adapters.AlternativesAdapter;
import com.example.vegantranslations.viewModel.fragments.SearchAlternativesAsAdminViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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

        SearchView searchAlternatives = root.findViewById(R.id.search_alternatives);
        searchAlternatives.setSubmitButtonEnabled(false);
        searchAlternatives.setIconified(true);
        searchAlternatives.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchAlternativesAsAdminViewModel.filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchAlternativesAsAdminViewModel.filter(s);
                return true;
            }
        });

        FloatingActionButton fabAddAlternative = root.findViewById(R.id.fab_add_alternative);
//        fabAddAlternative.setBackgroundDrawable(ContextCompat.getDrawable(root.getContext(), R.drawable.gradient));
        fabAddAlternative.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), AddAlternative.class);
            startActivity(intent);
        });
        return root;
    }

    private void onChanged(List<Alternative> alternatives) {
        alternativesAdapter = new AlternativesAdapter(alternatives, this.getActivity().getApplicationContext());
        alternativesAdapter.setOnItemClickListener(this::onItemClick);
        recyclerViewAlternatives.setAdapter(alternativesAdapter);
        alternativesAdapter.notifyDataSetChanged();
    }

    private void onItemClick(Alternative alternative) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=recipes+with+" + alternative.getName().replace("\\s+", "%20")));
        startActivity(intent);
    }
}