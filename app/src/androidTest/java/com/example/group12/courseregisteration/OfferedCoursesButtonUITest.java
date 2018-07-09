package com.example.group12.courseregisteration;

import android.support.test.espresso.Espresso;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.typeText;
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
public class OfferedCoursesButtonUITest {


    private FirebaseAuth mAuth;
    /**
     * The Activity test rule.
     */
// Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<Activity_SignIn> activityTestRule =
            new ActivityTestRule<>(Activity_SignIn.class);

    /**
     * Init.
     *
     * Makes sure every activity starts with the same initial state: Logged out, and on the signin
     * screen
     */
    @Before
    public void init(){
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            onView(withId(R.id.buttonSignOut)).perform(click());
        }
    }

    /**
     * Test to see if a button works on 'monday' page
     */
    @Test
    public void Monday() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonSchedule)).perform(click());
        Thread.sleep(2000);
        onView(withText(R.string.button_offeredcourses)).perform(click());
        onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }

    /**
     * Test to see if a button works on 'tuesday' page
     */
    @Test
    public void Tuesday() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonSchedule)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonTue)).perform(click());
        onView(withText(R.string.button_offeredcourses)).perform(click());
        onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }

    /**
     * Test to see if a button works on 'wednesday' page
     */
    @Test
    public void Wednesday() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonSchedule)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonWed)).perform(click());
        onView(withText(R.string.button_offeredcourses)).perform(click());
        onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }

    /**
     * Test to see if a button works on 'Thursday' page
     */
    @Test
    public void Thursday() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonSchedule)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonThu)).perform(click());
        onView(withText(R.string.button_offeredcourses)).perform(click());
        onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }
    /**
     * Test to see if a button works on 'friday' page
     */
    @Test
    public void Friday() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonSchedule)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonFri)).perform(click());
        onView(withText(R.string.button_offeredcourses)).perform(click());
        onView(withText(R.string.button_offeredcourses)).check(matches(notNullValue()));
    }
}