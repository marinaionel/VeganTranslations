package com.example.vegantranslations.data.fts;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import com.example.vegantranslations.data.model.db.Alternative;

@Fts4(contentEntity = Alternative.class)
@Entity(tableName = "alternative_fts")
public class AlternativeFts {
    @PrimaryKey
    @NonNull
    Long rowid;
    @NonNull
    private String name;
    @NonNull
    private String description;

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }
}
