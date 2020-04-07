package com.example.vegantranslations.service.repository.extra;

import java.util.List;

public interface FirebaseDatabaseRepositoryCallback<T> {
    void onSuccess(List<T> result);

    void onError(Exception e);
}
