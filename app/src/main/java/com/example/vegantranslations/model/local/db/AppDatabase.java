package com.example.vegantranslations.model.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vegantranslations.model.local.db.dao.AlternativeDao;
import com.example.vegantranslations.model.local.db.dao.CategoryDao;
import com.example.vegantranslations.model.local.db.dao.NonVeganProductDao;
import com.example.vegantranslations.model.local.db.dao.PurposeDao;
import com.example.vegantranslations.model.local.db.fts.AlternativeFts;
import com.example.vegantranslations.model.local.db.entities.Alternative;
import com.example.vegantranslations.model.local.db.entities.Category;
import com.example.vegantranslations.model.local.db.entities.NonVeganProduct;
import com.example.vegantranslations.model.local.db.entities.ProductPurpose;
import com.example.vegantranslations.model.local.db.entities.ProductPurposeAlternative;
import com.example.vegantranslations.model.local.db.entities.Purpose;

@Database(entities = {NonVeganProduct.class, Category.class, Purpose.class, Alternative.class, AlternativeFts.class, ProductPurpose.class, ProductPurposeAlternative.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract NonVeganProductDao nonVeganProductDao();

    public abstract CategoryDao categoryDao();

    public abstract PurposeDao purposeDao();

    public abstract AlternativeDao alternativeDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "vegan-translations-database")
                        .allowMainThreadQueries().enableMultiInstanceInvalidation().fallbackToDestructiveMigration().build();
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
