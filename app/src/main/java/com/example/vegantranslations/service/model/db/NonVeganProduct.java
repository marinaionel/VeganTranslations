package com.example.vegantranslations.service.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "non_vegan_products")
public class NonVeganProduct {
    private String name;
    @PrimaryKey
    private String id;
    @ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "id", onDelete = CASCADE)
    @ColumnInfo(name = "category_id")
    private String categoryId;

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * No args constructor for use in serialization
     */
    public NonVeganProduct() {
    }

    @Ignore
    public NonVeganProduct(String name, String id, String categoryId) {
        this.name = name;
        this.id = id;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonVeganProduct that = (NonVeganProduct) o;
        return name.equals(that.name) && id.equals(that.id);
    }

    public String getId() {
        return id;
    }
}
