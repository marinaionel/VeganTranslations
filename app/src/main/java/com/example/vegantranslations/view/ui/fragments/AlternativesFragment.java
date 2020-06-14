package com.example.vegantranslations.view.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegantranslations.view.ui.AddAlternative;
import com.example.vegantranslations.R;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.view.adapters.AlternativesAdapter;
import com.example.vegantranslations.viewModel.fragments.AlternativesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AlternativesFragment extends Fragment {
    private AlternativesViewModel alternativesViewModel;
    private RecyclerView recyclerViewAlternatives;
    private View root;
    private AlternativesAdapter alternativesAdapter;
    private final String TAG = AlternativesFragment.class.getName();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_alternatives, container, false);
        alternativesViewModel = new ViewModelProvider(this).get(AlternativesViewModel.class);

        recyclerViewAlternatives = root.findViewById(R.id.recycler_view_alternatives);
        recyclerViewAlternatives.setHasFixedSize(true);
        recyclerViewAlternatives.setLayoutManager(new LinearLayoutManager(root.getContext()));

        alternativesViewModel.getAlternatives().observe(getViewLifecycleOwner(), this::onChanged);

        SearchView searchAlternatives = root.findViewById(R.id.search_alternatives);
        searchAlternatives.setSubmitButtonEnabled(false);
        searchAlternatives.setOnClickListener(v -> searchAlternatives.setIconified(false));
        searchAlternatives.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                alternativesViewModel.filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
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