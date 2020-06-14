package com.example.vegantranslations.model.local.db.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vegantranslations.model.repository.Collections;
import com.example.vegantranslations.model.local.db.entities.Alternative;
import com.example.vegantranslations.model.local.db.entities.ProductPurposeAlternative;

import java.util.List;

@Dao
public interface AlternativeDao {
    @Query("select * from " + Collections.ALTERNATIVES)
    LiveData<List<Alternative>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(@NonNull Alternative... alternatives);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductPurposeAlternative(ProductPurposeAlternative... productPurposeAlternatives);

    @Query("select alternatives.id, alternatives.name, alternatives.description, alternatives.ImageUrl " +
            "from alternatives " +
            "left join product_purpose_alternative " +
            "on alternatives.id=product_purpose_alternative.alternative_id " +
            "where product_id=:productId and purpose_id=:purposeId")
    LiveData<List<Alternative>> getAlternativesForProductByPurpose(String productId, String purposeId);

    @Delete
    void delete(Alternative alternative);

    @Query("DELETE FROM " + Collections.ALTERNATIVES)
    void deleteAll();

    @Query("SELECT id, alternatives.description, alternatives.name " +
            "FROM alternative_fts " +
            "JOIN alternatives " +
            "ON docid = alternative_fts.rowid " +
            "WHERE alternatives.name LIKE :text or alternatives.description like :text " +
            "GROUP BY alternatives.id")
    LiveData<List<Alternative>> searchFor(String text);
}
