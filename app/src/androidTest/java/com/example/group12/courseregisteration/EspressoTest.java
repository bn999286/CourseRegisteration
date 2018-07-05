package com.example.group12.courseregisteration;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {


    @Rule
    public ActivityTestRule<Activity_Schedule_Mon> TestRuleMonday = new ActivityTestRule<>(Activity_Schedule_Mon.class);

    /**
     * Test to see if a button works on 'monday' page
     */
    @Test
    public void Monday() {
       onView(withText(R.string.button_offeredcourses)).perform(click());
       onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }

    /**
     * Test to see if a button works on 'tuesday' page
     */
    @Test
    public void Tuesday() {
        onView(withId(R.id.buttonTue)).perform(click());
        onView(withText(R.string.button_offeredcourses)).perform(click());
        onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }

    /**
     * Test to see if a button works on 'wednesday' page
     */
    @Test
    public void Wednesday() {
        onView(withId(R.id.buttonWed)).perform(click());
        onView(withText(R.string.button_offeredcourses)).perform(click());
        onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }

    /**
     * Test to see if a button works on 'Thursday' page
     */
    @Test
    public void Thursday() {
        onView(withId(R.id.buttonThu)).perform(click());
        onView(withText(R.string.button_offeredcourses)).perform(click());
        onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }
    /**
     * Test to see if a button works on 'friday' page
     */
    @Test
    public void Friday() {
        onView(withId(R.id.buttonFri)).perform(click());
        onView(withText(R.string.button_offeredcourses)).perform(click());
        onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }
}