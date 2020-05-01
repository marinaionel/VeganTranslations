package com.example.vegantranslations.viewModel.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchAlternativesAsAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchAlternativesAsAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}