package com.example.group12.courseregisteration;

import android.os.IBinder;
import android.support.test.espresso.Root;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * The type Toast matcher that matches toast.
 * Code taken from online tutorial
 */
public class ToastMatcher extends TypeSafeMatcher<Root> {

    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }

    /*
    *Goes into the view hierarchy and retrieves the toast popup, and then compares it
    * to comparison text
     */
    @Override
    public boolean matchesSafely(Root root) {
        int type = root.getWindowLayoutParams().get().type;
        if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
            IBinder windowToken = root.getDecorView().getWindowToken();
            IBinder appToken = root.getDecorView().getApplicationWindowToken();
            if (windowToken == appToken) {
                return true;
            }
        }
        return false;
    }


    /**
     * Static method for toast-matching comparisons
     *
     * @return the matcher
     */
    public static Matcher<Root> isToast() {
        return new ToastMatcher();
    }
}
