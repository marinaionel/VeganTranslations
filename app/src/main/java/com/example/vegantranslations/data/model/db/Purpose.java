package com.example.vegantranslations.data.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "purpose")
public class Purpose {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public String toString() {
        return name;
    }
}
