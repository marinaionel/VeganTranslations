package com.example.vegantranslations.ui;

import android.content.Intent;

import com.example.vegantranslations.R;
import com.example.vegantranslations.view.ui.MainActivity;
import com.example.vegantranslations.view.ui.SearchAsGuestActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
    }

    @Test
    public void test1() {
        // Type text and then press the button.
        onView(withId(R.id.login))
                .perform();
        onView(withId(R.id.continue_as_guest)).perform(click());

//        Intent intent = new Intent(MainActivity., SearchAsGuestActivity.class);

    }
}
