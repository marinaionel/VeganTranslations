package com.example.vegantranslations.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vegantranslations.data.Collections;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.ProductPurposeAlternative;
import com.example.vegantranslations.data.model.db.Purpose;

import java.util.List;

@Dao
public interface AlternativeDao {
    @Query("select * from " + Collections.ALTERNATIVES)
    LiveData<List<Alternative>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Alternative... alternatives);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductPurposeAlternative(ProductPurposeAlternative... productPurposeAlternatives);

    @Query("select * from alternatives left join product_purpose_alternative on alternatives.id=product_purpose_alternative.alternative_id where product_id=:productId and purpose_id=:purposeId")
    LiveData<List<Alternative>> getAlternativesForProductByPurpose(String productId, String purposeId);

    @Delete
    void delete(Alternative alternative);

    @Query("DELETE FROM " + Collections.ALTERNATIVES)
    void deleteAll();
}
