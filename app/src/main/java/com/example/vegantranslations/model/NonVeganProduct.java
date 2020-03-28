package com.example.vegantranslations.model;

import java.util.Map;

public class NonVeganProduct {
    private String name;

    public NonVeganProduct(String name) {
        this.name = name;
    }

    public NonVeganProduct(Map<String, Object> fields) {
        this.name = (String) fields.get("name");
    }

    @Override
    public String toString() {
        return name;
    }
}
