package com.example.vegantranslations.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.vegantranslations.R;
import com.example.vegantranslations.view.ui.AddAlternative;
import com.example.vegantranslations.view.ui.MainActivity;
import com.example.vegantranslations.view.ui.SearchAsGuestActivity;
import com.example.vegantranslations.view.ui.ShowResultsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UiTests {
    @Before
    public void setUp() {
    }

    @Rule
    public IntentsTestRule<MainActivity> mainActivityIntentsTestRule = new IntentsTestRule<>(MainActivity.class);
    @Rule
    public ActivityTestRule<AddAlternative> addAlternativeActivityTestRule = new ActivityTestRule(AddAlternative.class);
    public Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void switchesActivitiesWhenClickOnContinueAsGuest() {
        Intent intent = new Intent(context, SearchAsGuestActivity.class);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(toPackage("com.example.vegantranslations.view")).respondWith(result);
        onView(withId(R.id.continue_as_guest)).perform(click());
    }

    @Test
    public void testSelectingAlternatives() {
        onView(withId(R.id.continue_as_guest)).perform(click());

        onView(withId(R.id.products)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.products)).check(matches(withSpinnerText(containsString("honey"))));

        onView(withId(R.id.purpose)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.purpose)).check(matches(withSpinnerText(containsString("general"))));

        Intent intent = new Intent(context, ShowResultsActivity.class);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(allOf(toPackage("com.example.vegantranslations.view"), hasExtraWithKey("product"), hasExtraWithKey("purpose"))).respondWith(result);
        onView(withId(R.id.show_vegan_alternatives)).perform(click());
    }

    @Test
    public void testAddToast() {
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.fab_add_alternative)).perform(click());
        onView(withId(R.id.name_of_the_product)).perform(replaceText("Lentils"));
        onView(withId(R.id.description_tv)).perform(replaceText("Lentils belong to the legume family. They resemble a tiny bean, grow in pods, and come in red, brown, black, and green varieties. They also contain high levels of protein and fiber."));
        onView(withId(R.id.add_vegan_alternatives)).perform(click());
        onView(withText(R.string.add_alternative_success)).inRoot(withDecorView(not(is(addAlternativeActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
