package com.kar.localoffers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.kar.localoffers.views.MainActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChangeSettingsTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "kick";
    }

    @Test
    public void clearUid_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.input_uid))
                .perform(clearText());
        onView(withId(R.id.btn_getoffers)).perform(click());
    }

    @Test
    public void changeUid_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.input_uid))
                .perform(typeTextIntoFocusedView(mStringToBetyped));
        onView(withId(R.id.btn_getoffers)).perform(click());
    }

    @Test
    public void changeAppid_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.input_appid))
                .perform(typeTextIntoFocusedView(mStringToBetyped));
        onView(withId(R.id.btn_getoffers)).perform(click());
    }

    @Test
    public void checkWithDefaultValues_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.btn_getoffers)).perform(click());
    }
}