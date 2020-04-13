package com.example.vegantranslations.service.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vegantranslations.service.local.dao.CategoryDao;
import com.example.vegantranslations.service.local.dao.NonVeganProductDao;
import com.example.vegantranslations.service.local.dao.PurposeDao;
import com.example.vegantranslations.service.model.db.Category;
import com.example.vegantranslations.service.model.db.NonVeganProduct;
import com.example.vegantranslations.service.model.db.Purpose;

@Database(entities = {NonVeganProduct.class, Category.class, Purpose.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

//    private AppDatabase() {
//    }

    public abstract NonVeganProductDao nonVeganProductDao();

    public abstract CategoryDao categoryDao();

    public abstract PurposeDao purposeDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "vegan-translations-database")
                    .allowMainThreadQueries().enableMultiInstanceInvalidation().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
