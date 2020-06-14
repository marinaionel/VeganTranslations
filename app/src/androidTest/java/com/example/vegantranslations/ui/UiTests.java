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
import static com.example.vegantranslations.ui.TestUtils.withRecyclerView;
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
    public Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void switchesActivitiesWhenClickOnContinueAsGuest() {
        Intent intent = new Intent(context, SearchAsGuestActivity.class);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(toPackage("com.example.vegantranslations.view")).respondWith(result);
        onView(withId(R.id.continue_as_guest)).perform(click());
    }

    @Test
    public void testSelectingAlternatives1() {
        onView(withId(R.id.continue_as_guest)).perform(click());

        onView(withId(R.id.products)).perform(click());
        onData(anything()).atPosition(5).perform(click());
        onView(withId(R.id.products)).check(matches(withSpinnerText(containsString("honey"))));

        onView(withId(R.id.purpose)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.purpose)).check(matches(withSpinnerText(containsString("general"))));

        Intent intent = new Intent(context, ShowResultsActivity.class);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(allOf(toPackage("com.example.vegantranslations.view"), hasExtraWithKey("product"), hasExtraWithKey("purpose"))).respondWith(result);
        onView(withId(R.id.show_vegan_alternatives)).perform(click());

        onView(withRecyclerView(R.id.resultsRecyclerView)
                .atPositionOnView(0, R.id.rowTextTitle))
                .check(matches(withText("Maple Syrup")));
        onView(withRecyclerView(R.id.resultsRecyclerView)
                .atPositionOnView(1, R.id.rowTextTitle))
                .check(matches(withText("Bee Free Honee")));
        onView(withRecyclerView(R.id.resultsRecyclerView)
                .atPositionOnView(2, R.id.rowTextTitle))
                .check(matches(withText("Agave Nectar")));
    }

    @Test
    public void testSelectingAlternatives2() {
        onView(withId(R.id.continue_as_guest)).perform(click());

        onView(withId(R.id.products)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.products)).check(matches(withSpinnerText(containsString("chicken eggs"))));

        onView(withId(R.id.purpose)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.purpose)).check(matches(withSpinnerText(containsString("omlette"))));

        Intent intent = new Intent(context, ShowResultsActivity.class);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(allOf(toPackage("com.example.vegantranslations.view"), hasExtraWithKey("product"), hasExtraWithKey("purpose"))).respondWith(result);
        onView(withId(R.id.show_vegan_alternatives)).perform(click());

        onView(withRecyclerView(R.id.resultsRecyclerView)
                .atPositionOnView(0, R.id.rowTextTitle))
                .check(matches(withText("JUST Egg")));
        onView(withRecyclerView(R.id.resultsRecyclerView)
                .atPositionOnView(1, R.id.rowTextTitle))
                .check(matches(withText("Tofu")));
    }

    @Test
    public void testSelectingAlternatives3() {
        onView(withId(R.id.continue_as_guest)).perform(click());

        onView(withId(R.id.products)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.products)).check(matches(withSpinnerText(containsString("chicken eggs"))));

        onView(withId(R.id.purpose)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.purpose)).check(matches(withSpinnerText(containsString("baking"))));

        Intent intent = new Intent(context, ShowResultsActivity.class);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(allOf(toPackage("com.example.vegantranslations.view"), hasExtraWithKey("product"), hasExtraWithKey("purpose"))).respondWith(result);
        onView(withId(R.id.show_vegan_alternatives)).perform(click());

        onView(withRecyclerView(R.id.resultsRecyclerView)
                .atPositionOnView(0, R.id.rowTextTitle))
                .check(matches(withText("Flax seeds")));
    }

    @Test
    public void testNoResults() {
        onView(withId(R.id.continue_as_guest)).perform(click());

        onView(withId(R.id.products)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.products)).check(matches(withSpinnerText(containsString("chicken eggs"))));

        onView(withId(R.id.purpose)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.purpose)).check(matches(withSpinnerText(containsString("frying"))));

        Intent intent = new Intent(context, ShowResultsActivity.class);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(allOf(toPackage("com.example.vegantranslations.view"), hasExtraWithKey("product"), hasExtraWithKey("purpose"))).respondWith(result);
        onView(withId(R.id.show_vegan_alternatives)).perform(click());

        onView(withId(Integer.parseInt("5"))).check(matches(withText(R.string.no_results)));
    }
}
