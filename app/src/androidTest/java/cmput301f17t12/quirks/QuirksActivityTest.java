package cmput301f17t12.quirks;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import cmput301f17t12.quirks.Activities.AddQuirkActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.MainActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;
import cmput301f17t12.quirks.Enumerations.Day;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by root on 11/13/17.
 */


@RunWith(AndroidJUnit4.class)
public class QuirksActivityTest {
    String test;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void initQuirk()
    {
        loginActivity = mActivityRule.getActivity();
    }

    //Test New Quirk Button
    @Test
    public void newQuirkButton(){
        Intents.init();
        //NavigateTo QuirkListActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest2"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onView(withId(R.id.add_quirk_button)).perform(click());
        intended(hasComponent(AddQuirkActivity.class.getName()), times(1));
        intended(hasComponent(QuirksActivity.class.getName()), times(1));
        Intents.release();

    }

    //Test Filter by Today is correct
    @Test
    public void QuirkActivityFilterToday(){
        Intents.init();
        //NavigateTo QuirkListActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest2"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());

        //Filter by Date
        onView(withId(R.id.filter_type)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Today\'s Habits"))).perform(click());
        onView(withId(R.id.applyFilterButton)).perform(click());

        //Click on Test Quirk and ensure proper date is also clicked for Occurence !
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("TestDay","DAY " + Integer.toString(day));

        switch (day) {
            case 1:
                onView(withId(R.id.QuirkEditradioButtonSun)).check(matches(isChecked()));
                break;
            case 2:
                onView(withId(R.id.QuirkEditradioButtonMon)).check(matches(isChecked()));
                break;
            case 3:
                onView(withId(R.id.QuirkEditradioButtonTue)).check(matches(isChecked()));
                break;
            case 4:
                onView(withId(R.id.QuirkEditradioButtonWed)).check(matches(isChecked()));
                break;
            case 5:
                onView(withId(R.id.QuirkEditradioButtonThur)).check(matches(isChecked()));
                break;
            case 6:
                onView(withId(R.id.QuirkEditradioButtonFri)).check(matches(isChecked()));
                break;
            case 7:
                onView(withId(R.id.QuirkEditradioButtonSat)).check(matches(isChecked()));
                break;
        }

        intended(hasComponent(QuirksActivity.class.getName()), times(1));
        Intents.release();
    }

}
