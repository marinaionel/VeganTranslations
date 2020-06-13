package com.example.vegantranslations.data;

import java.util.function.Function;

public interface IApiHandler {
    void getImage(String name, Function<String, Void> function);
}
