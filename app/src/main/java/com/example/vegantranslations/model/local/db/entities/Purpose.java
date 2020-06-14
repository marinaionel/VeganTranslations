package com.example.vegantranslations.model.local.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "purpose")
public class Purpose implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    @NonNull
    private String name;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
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
