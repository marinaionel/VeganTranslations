package com.example.vegantranslations.model.local.db.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vegantranslations.model.repository.Collections;
import com.example.vegantranslations.model.local.db.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("select * from " + Collections.CATEGORY)
    LiveData<List<Category>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(@NonNull Category... categories);

    @Delete
    void delete(Category category);

    @Query("DELETE FROM " + Collections.CATEGORY)
    void deleteAll();
}
