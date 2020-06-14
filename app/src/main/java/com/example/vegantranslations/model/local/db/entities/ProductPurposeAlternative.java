package com.example.vegantranslations.model.local.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "product_purpose_alternative")
public class ProductPurposeAlternative {
    @PrimaryKey
    @NonNull
    private String id;
    @ForeignKey(entity = NonVeganProduct.class, parentColumns = "product_id", childColumns = "id", onUpdate = CASCADE)
    @ColumnInfo(name = "product_id")
    private String productId;
    @ForeignKey(entity = Purpose.class, parentColumns = "purpose_id", childColumns = "id", onUpdate = CASCADE)
    @ColumnInfo(name = "purpose_id")
    private String purposeId;
    @ForeignKey(entity = Alternative.class, parentColumns = "alternative_id", childColumns = "id", onUpdate = CASCADE)
    @ColumnInfo(name = "alternative_id")
    private String alternative;

    /**
     * No args constructor for use in serialization
     */
    public ProductPurposeAlternative() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }
}
