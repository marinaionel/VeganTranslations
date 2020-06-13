package com.example.vegantranslations.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vegantranslations.R;
import com.example.vegantranslations.data.FirestoreRepository;
import com.example.vegantranslations.data.Repository;

public class AddAlternativeViewModel extends AndroidViewModel {
    private Repository repository = new FirestoreRepository(super.getApplication().getApplicationContext());
    private MutableLiveData<String> info = new MutableLiveData<>();

    public AddAlternativeViewModel(@NonNull Application application) {
        super(application);
    }

    public void addAlternative(String name, String description) {
        if (name == null || name.equals("")) {
            info.setValue(super.getApplication().getString(R.string.error_add_alternative_1));
            return;
        }
        if (name.length() < 2) {
            info.setValue(super.getApplication().getString(R.string.error_add_alternative_2));
            return;
        }
        if (name.length() > 50) {
            info.setValue(super.getApplication().getString(R.string.error_add_alternative_5));
            return;
        }
        if (description == null || description.equals("")) {
            info.setValue(super.getApplication().getString(R.string.error_add_alternative_3));
            return;
        }
        if (description.length() < 10) {
            info.setValue(super.getApplication().getString(R.string.error_add_alternative_4));
            return;
        }
        if (description.length() > 300) {
            info.setValue(super.getApplication().getString(R.string.error_add_alternative_6));
            return;
        }
        repository.addAlternative(name, description);
        info.setValue(super.getApplication().getString(R.string.add_alternative_success));
    }

    public MutableLiveData<String> getInfo() {
        return info;
    }
}
