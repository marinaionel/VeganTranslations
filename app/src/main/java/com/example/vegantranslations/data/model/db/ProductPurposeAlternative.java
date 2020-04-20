package com.example.vegantranslations.data.model.db;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_purpose_alternative")
public class ProductPurposeAlternative {
    @PrimaryKey
    @NonNull
    private String id;
    @Embedded
    private NonVeganProduct product;
    @Embedded
    private Purpose purpose;
    @Embedded
    private Alternative alternative;

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

    public NonVeganProduct getProduct() {
        return product;
    }

    public void setProduct(NonVeganProduct product) {
        this.product = product;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public Alternative getAlternative() {
        return alternative;
    }

    public void setAlternative(Alternative alternative) {
        this.alternative = alternative;
    }
}
