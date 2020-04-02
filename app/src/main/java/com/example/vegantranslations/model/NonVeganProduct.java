package com.example.vegantranslations.model;

import java.util.Map;

public class NonVeganProduct {
    private String name;
    private String category;
    private String id;

    public NonVeganProduct(String name) {
        this.name = name;
    }

    public NonVeganProduct(Map<String, Object> fields, String id) {
        this.name = (String) fields.get("name");
        this.category = (String) fields.get("category");
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonVeganProduct that = (NonVeganProduct) o;
        return name.equals(that.name) && id.equals(that.id);
    }

    public String getEntityKey() {
        return id;
    }
}
