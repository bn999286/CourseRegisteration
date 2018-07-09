package com.example.group12.courseregisteration;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;


/**
 * Tests the UI of the project using espresso
 * written by Peter and Mao
 */
public class UItest {

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
     * Tests whether a new user can be successfully registered
     * Method invokes thread.sleep to hopefully get around the timing issues that plague
     * these tests
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void registerUser() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.regButton)).perform(click());
        onView(withId(R.id.etEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.password)).perform(typeText("fake12345"));
        onView(withId(R.id.etName)).perform(typeText("Bob Philmore"));
        onView(withId(R.id.etPhone)).perform(typeText("9025555555"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnSubmit)).perform(click());
        onView(withText("Test@gmail.ca")).inRoot(new ToastMatcher()).check(matches(withText("Test@gmail.ca")));
    }

    /**
     * Can a user sign in? This tests that!
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void signIn() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        //This is a clunky line that specifies the match text twice - there has got to be
        //a better way
        //This is a prime target for a refactor, if I can ever figure out how to do that
        onView(withText("Verification Success")).inRoot(new ToastMatcher()).check(matches(withText("Verification Success")));
    }

    /**
     * Reset password test. Re-sets the password back to its original form so the other test
     * methods begin with the same criteria
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void resetPassword() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonPassword)).perform(click());
        onView(withId(R.id.editText)).perform(typeText("fake12349"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.buttonSignOut)).perform(click());

        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12349"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonPassword)).perform(click());
        onView(withId(R.id.editText)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button)).perform(click());

        onView(withText("Password successfully changed")).inRoot(new ToastMatcher()).check(matches(withText("Password successfully changed")));
    }

    /**
     * View course test.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void viewCourse() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonOfferedCourse)).perform(click());
        Thread.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.viewer)).atPosition(0).perform(click());
        onView(withId(R.id.CourseID)).check(matches(withText("Chemistry 2110")));
    }

    /**
     * Tests that user can add course.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void addCourse() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonOfferedCourse)).perform(click());
        Thread.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.viewer)).atPosition(0).perform(click());
        onView(withId(R.id.buttonRegister)).perform(click());
        Thread.sleep(1000);
        onView(withText("Register Success!")).inRoot(new ToastMatcher()).check(matches(withText("Register Success!")));
    }

    /**
     * Tests that user can drop a course.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void dropCourse() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.buttonOfferedCourse)).perform(click());
        Thread.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.viewer)).atPosition(0).perform(click());
        onView(withId(R.id.buttonDrop)).perform(click());
        onView(withText("Drop Success!")).inRoot(new ToastMatcher()).check(matches(withText("Drop Success!")));
    }
}