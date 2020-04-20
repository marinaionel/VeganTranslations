package com.example.vegantranslations.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vegantranslations.data.Collections;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.ProductPurpose;
import com.example.vegantranslations.data.model.db.Purpose;

import java.util.List;

@Dao
public interface NonVeganProductDao {
    @Query("select * from " + Collections.NON_VEGAN_PRODUCTS)
    LiveData<List<NonVeganProduct>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NonVeganProduct... nonVeganProducts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductPurpose(ProductPurpose... productPurposes);

    @Query("select purpose.id, purpose.name from " + Collections.PURPOSE + " left join " + Collections.PRODUCT_PURPOSE + " on id=" + Collections.PRODUCT_PURPOSE + ".purpose_id where id=:id")
    LiveData<List<Purpose>> getProductPurposes(String id);

    @Query("select * from " + Collections.NON_VEGAN_PRODUCTS + " where id = :id")
    LiveData<NonVeganProduct> getProductById(String id);

    @Delete
    void delete(NonVeganProduct nonVeganProduct);

    @Query("DELETE FROM " + Collections.NON_VEGAN_PRODUCTS)
    void deleteAll();
}
