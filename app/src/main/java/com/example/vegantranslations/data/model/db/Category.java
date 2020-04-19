package com.example.vegantranslations.data.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "non_vegan_products_category")
public class Category {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;

    @Ignore
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * No args constructor for use in serialization
     */
    public Category() {
    }
}
