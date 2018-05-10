package cmput301f17t12.quirks;

import android.app.UiAutomation;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiAutomatorInstrumentationTestRunner;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f17t12.quirks.Activities.EditEventActivity;
import cmput301f17t12.quirks.Activities.EventListActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.MainActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;
import cmput301f17t12.quirks.Models.EventList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.assertNoUnverifiedIntents;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by charleshoang on 2017-11-13.
 */
@RunWith(AndroidJUnit4.class)
public class EditEventActivityTest {

    String comment;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void initialize() {
        // Specify a valid comment
        comment = "Testing change";
        loginActivity = mActivityRule.getActivity();


    }

    //Test Cancel button
    //Go from login activity to Edit Event activvity
    @Test
    public void cancelButton(){

        Intents.init();

        //Login with user testing 123 and go to NewEvent ACtivity to have
        //quirks to log
        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());



        //go to edit event
        onView(withId(R.id.action_quirklist))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).
                onChildView(withId(R.id.quirk_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.el_eventslistview)).atPosition(0).
                onChildView(withId(R.id.el_eventview)).perform(click());
        //Cancel button in add Events
        onView(withId(R.id.cancel_button))
                .perform(click());
        intended(hasComponent(MainActivity.class.getName()), times(2));
        intended(hasComponent(QuirksActivity.class.getName()), times(1));
        intended(hasComponent(EventListActivity.class.getName()), times(1));
        intended(hasComponent(EditEventActivity.class.getName()), times(1));

        Intents.release();

    }

    //make changes to evvent and save

    @Test
    public void saveButton(){
        //Will need to go createa quirk then save
        Intents.init();

        //Login with user testing 123 and go to NewEvent ACtivity to have
        //quirks to log
        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());
        onView(withId(R.id.action_quirklist))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).
                onChildView(withId(R.id.quirk_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.el_eventslistview)).atPosition(0).
                onChildView(withId(R.id.el_eventview)).perform(click());

        //insert comment and save event
        onView(withId(R.id.comment_edittext))
                .perform(replaceText(comment), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .perform(click());
        onView(withId(R.id.action_quirklist))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).
                onChildView(withId(R.id.quirk_button)).perform(click());

        //click on view/edit/delete button for latest event

        onData(anything()).inAdapterView(withId(R.id.el_eventslistview)).atPosition(0).
                onChildView(withId(R.id.el_eventview)).perform(click());
        //Check that comment was updated
        onView(withId(R.id.comment_edittext)).check(matches(withText(comment)));
        Intents.release();

    }

    //Testing that the map is clickable
    @Test
    public void map(){
        loginActivity = mActivityRule.getActivity();
        SystemClock.sleep(1000);
        Intents.init();
        mActivityRule.launchActivity(new Intent());

        //Login with user testing 123 and go to NewEvent ACtivity to have
        //quirks to log
        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());
        onView(withId(R.id.action_quirklist))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).
                onChildView(withId(R.id.quirk_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.el_eventslistview)).atPosition(0).
                onChildView(withId(R.id.el_eventview)).perform(click());

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Google Maps"));

        onView(withContentDescription("Google Map")).perform(click());



        //Test that the text has changed to a lat and lon description after being tapped.
        onView(withId(R.id.event_tap_text))
                .check(matches(withText("Tap on the map")));
        Intents.release();

    }



}
