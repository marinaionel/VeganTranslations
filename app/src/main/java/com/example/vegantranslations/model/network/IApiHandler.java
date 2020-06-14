package com.example.vegantranslations.model.network;

import java.util.function.Function;

public interface IApiHandler {
    void getImage(String name, Function<String, Void> callback);
}
