package com.example.vegantranslations.model.local.db.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vegantranslations.model.repository.Collections;
import com.example.vegantranslations.model.local.db.entities.Purpose;

import java.util.List;

@Dao
public interface PurposeDao {
    @Query("select * from " + Collections.PURPOSE)
    LiveData<List<Purpose>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(@NonNull Purpose... purposes);

    @Query("select purpose.* from " + Collections.PURPOSE + " left join product_purpose on purpose.id=product_purpose.purpose_id where product_id=:productId")
    LiveData<List<Purpose>> getPurposesForProduct(String productId);

    @Query("select * from purpose where name=:name COLLATE NOCASE limit 1")
    LiveData<Purpose> getPurposeByName(String name);

    @Delete
    void delete(Purpose nonVeganProduct);

    @Query("DELETE FROM " + Collections.PURPOSE)
    void deleteAll();
}
