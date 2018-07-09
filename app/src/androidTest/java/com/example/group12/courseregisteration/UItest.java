package com.example.group12.courseregisteration;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;


/**
 * The type Project tester.
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

    @Before
    public void init(){
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            onView(withId(R.id.buttonSignOut)).perform(click());
        }
    }

    /**
     * Create contact test success.
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

    @Test
    public void signIn() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("Test@gmail.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("fake12345"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSignIn)).perform(click());
        onView(withText("Verification Success")).inRoot(new ToastMatcher()).check(matches(withText("Verification Success")));
    }

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