package com.example.vegantranslations.data.repository.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vegantranslations.data.repository.local.dao.AlternativeDao;
import com.example.vegantranslations.data.repository.local.dao.CategoryDao;
import com.example.vegantranslations.data.repository.local.dao.NonVeganProductDao;
import com.example.vegantranslations.data.repository.local.dao.PurposeDao;
import com.example.vegantranslations.data.model.db.Alternative;
import com.example.vegantranslations.data.model.db.Category;
import com.example.vegantranslations.data.model.db.NonVeganProduct;
import com.example.vegantranslations.data.model.db.ProductPurpose;
import com.example.vegantranslations.data.model.db.ProductPurposeAlternative;
import com.example.vegantranslations.data.model.db.Purpose;

@Database(entities = {NonVeganProduct.class, Category.class, Purpose.class, Alternative.class, ProductPurpose.class, ProductPurposeAlternative.class}, version = 4, exportSchema = false)
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