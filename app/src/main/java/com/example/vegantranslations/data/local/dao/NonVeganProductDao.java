package com.example.vegantranslations.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vegantranslations.data.Collections;
import com.example.vegantranslations.data.model.db.NonVeganProduct;

import java.util.List;

@Dao
public interface NonVeganProductDao {
    @Query("select * from " + Collections.NON_VEGAN_PRODUCTS)
    LiveData<List<NonVeganProduct>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NonVeganProduct... nonVeganProducts);

    @Delete
    void delete(NonVeganProduct nonVeganProduct);

    @Query("DELETE FROM " + Collections.NON_VEGAN_PRODUCTS)
    void deleteAll();
}
