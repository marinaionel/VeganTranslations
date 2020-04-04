package com.example.vegantranslations.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "non_vegan_products_category")
public class Category {
    @PrimaryKey
    private String id;
    private String name;

    @Ignore
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * No args constructor for use in serialization
     */
    public Category() {
    }
}
