package com.example.vegantranslations.data.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "alternatives")
public class Alternative {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String description;

    @Ignore
    public Alternative(@NonNull String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * No args constructor for use in serialization
     */
    public Alternative() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}