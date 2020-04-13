package com.example.vegantranslations.service.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vegantranslations.service.Collections;
import com.example.vegantranslations.service.model.db.Purpose;

import java.util.List;

@Dao
public interface PurposeDao {
    @Query("select * from " + Collections.PURPOSE)
    LiveData<List<Purpose>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Purpose... purposes);

    @Delete
    void delete(Purpose nonVeganProduct);

    @Query("DELETE FROM " + Collections.PURPOSE)
    void deleteAll();
}