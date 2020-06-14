package com.example.vegantranslations;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.vegantranslations.model.local.db.AppDatabase;
import com.example.vegantranslations.model.repository.FirebaseRepository;
import com.example.vegantranslations.model.repository.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private Repository firestoreRepository;
    private AppDatabase appDatabase;

    @Before
    public void setUp() {
        firestoreRepository = new FirebaseRepository(appContext);
        appDatabase = AppDatabase.getAppDatabase(appContext);
    }

    @Test
    public void useAppContext() {
        assertEquals("com.example.vegantranslations", appContext.getPackageName());
    }

    @Test
    public void addAlternative(){
        String name="Oat milk";
        firestoreRepository.addAlternative(name, "Oat milk is a plant milk derived from whole oat (Avena spp.) grains by extracting the plant material with water. Oat milk has a creamy texture and oatmeal-like flavor, and is manufactured in various flavors, such as sweetened, unsweetened, vanilla or chocolate.");

    }
}
