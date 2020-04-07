package com.example.vegantranslations.service.model.db;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "purpose")
public class Purpose {
    private String id;
    private String name;

    @Ignore
    public Purpose(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * No args constructor for use in serialization
     */
    public Purpose() {
    }
}
