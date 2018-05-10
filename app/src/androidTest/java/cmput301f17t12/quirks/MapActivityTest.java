package cmput301f17t12.quirks;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f17t12.quirks.Activities.EditEventActivity;
import cmput301f17t12.quirks.Activities.EventListActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.MainActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;


/**
 * Created by charleshoang on 2017-12-03.
 */


@RunWith(AndroidJUnit4.class)
public class MapActivityTest {
    String tapOriginalText;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void initialize() {
        // Specify a valid comment

        loginActivity = mActivityRule.getActivity();
        tapOriginalText = "Tap on the Map";

    }

    //Testing that the map is clickable
    @Test
    public void map(){
        loginActivity = mActivityRule.getActivity();
        SystemClock.sleep(1000);
        Intents.init();
        mActivityRule.launchActivity(new Intent());


        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());
        onView(withId(R.id.action_geomap))
                .perform(click());

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Google Maps"));

        onView(withContentDescription("Google Map")).perform(swipeRight());
        SystemClock.sleep(1000);
        //Test that the text has changed to a lat and lon description after being swipe.
        onView(withId(R.id.tap_text))
                .check(matches(not(withText(tapOriginalText))));
        Intents.release();

    }
    //Testing that the nearbyEvents
    @Test
    public void nearbyEvents(){
        loginActivity = mActivityRule.getActivity();
        SystemClock.sleep(1000);
        Intents.init();
        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());
        onView(withId(R.id.action_geomap))
                .perform(click());
        onView(withId(R.id.nearby_map_button));
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Google Maps"));

        onView(withContentDescription("Google Map")).perform(swipeDown());
        SystemClock.sleep(1000);
        //Test that the text has changed to a lat and lon description after being swipe.
        onView(withId(R.id.tap_text))
                .check(matches(not(withText(tapOriginalText))));
        Intents.release();

    }

    //Testing that the follow users events
    @Test
    public void followEvents(){
        loginActivity = mActivityRule.getActivity();
        SystemClock.sleep(1000);
        Intents.init();
        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());
        onView(withId(R.id.action_geomap))
                .perform(click());
        onView(withId(R.id.following_map_button));
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Google Maps"));

        onView(withContentDescription("Google Map")).perform(swipeUp());
        SystemClock.sleep(1000);
        //Test that the text has changed to a lat and lon description after being swipe.
        onView(withId(R.id.tap_text))
                .check(matches(not(withText(tapOriginalText))));
        Intents.release();

    }

    //Testing that the my Events
    @Test
    public void myEvents(){
        loginActivity = mActivityRule.getActivity();
        SystemClock.sleep(1000);
        Intents.init();
        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());
        onView(withId(R.id.action_geomap))
                .perform(click());
        onView(withId(R.id.my_events_map_button));
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Google Maps"));

        onView(withContentDescription("Google Map")).perform(swipeLeft());
        SystemClock.sleep(1000);
        //Test that the text has changed to a lat and lon description after being swiped.
        onView(withId(R.id.tap_text))
                .check(matches(not(withText(tapOriginalText))));
        Intents.release();

    }


}
