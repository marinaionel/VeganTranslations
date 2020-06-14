package com.example.vegantranslations.model.local.db.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vegantranslations.model.local.db.entities.NonVeganProduct;
import com.example.vegantranslations.model.local.db.entities.ProductPurpose;

import java.util.List;

@Dao
public interface NonVeganProductDao {
    @Query("select * from non_vegan_products")
    LiveData<List<NonVeganProduct>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(@NonNull NonVeganProduct... nonVeganProducts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductPurpose(@NonNull ProductPurpose... productPurposes);

    @Query("select * from non_vegan_products where id = :id")
    LiveData<NonVeganProduct> getProductById(String id);

    @Query("select * from non_vegan_products where name=:name COLLATE NOCASE limit 1")
    LiveData<NonVeganProduct> getNonVeganProductByName(String name);

    @Delete
    void delete(NonVeganProduct nonVeganProduct);

    @Query("DELETE FROM non_vegan_products")
    void deleteAll();
}
