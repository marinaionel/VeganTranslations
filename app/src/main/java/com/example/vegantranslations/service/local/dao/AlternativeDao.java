package com.example.vegantranslations.service.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vegantranslations.service.Collections;
import com.example.vegantranslations.service.model.db.Alternative;
import com.example.vegantranslations.service.model.db.NonVeganProduct;
import com.example.vegantranslations.service.model.db.Purpose;

import java.util.List;

@Dao
public interface AlternativeDao {
    @Query("select * from " + Collections.ALTERNATIVES)
    LiveData<List<Alternative>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Alternative... alternatives);

    @Query("select * from " + Collections.ALTERNATIVES+" where id=")
    LiveData<List<Alternative>> getAlternativesForProductByPurpose(NonVeganProduct nonVeganProduct, Purpose purpose);

    @Delete
    void delete(Alternative alternative);

    @Query("DELETE FROM " + Collections.ALTERNATIVES)
    void deleteAll();
}
