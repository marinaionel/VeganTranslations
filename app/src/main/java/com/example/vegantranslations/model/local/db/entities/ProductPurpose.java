package com.example.vegantranslations.model.local.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "product_purpose", primaryKeys = {"product_id", "purpose_id"})
public class ProductPurpose {
    @NonNull
    @ForeignKey(entity = NonVeganProduct.class, parentColumns = "product_id", childColumns = "id", onUpdate = CASCADE)
    @ColumnInfo(name = "product_id")
    private String productId;
    @NonNull
    @ForeignKey(entity = Purpose.class, parentColumns = "purpose_id", childColumns = "id", onUpdate = CASCADE)
    @ColumnInfo(name = "purpose_id")
    private String purposeId;

    @Ignore
    public ProductPurpose(@NonNull String productId, @NonNull String purposeId) {
        this.productId = productId;
        this.purposeId = purposeId;
    }

    /**
     * No args constructor for use in serialization
     */
    public ProductPurpose() {
    }

    @NonNull
    public String getProductId() {
        return productId;
    }

    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    @NonNull
    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(@NonNull String purposeId) {
        this.purposeId = purposeId;
    }
}
