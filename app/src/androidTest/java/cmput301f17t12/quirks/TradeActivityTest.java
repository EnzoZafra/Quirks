package cmput301f17t12.quirks;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f17t12.quirks.Activities.EditEventActivity;
import cmput301f17t12.quirks.Activities.EventListActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.MainActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;
import cmput301f17t12.quirks.Activities.TradeActivity;

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;


/**
 * Created by charleshoang on 2017-12-03.
 */


@RunWith(AndroidJUnit4.class)
public class TradeActivityTest {


    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void initialize() {

        loginActivity = mActivityRule.getActivity();


    }
    //Select your item to trade
    @Test
    public void selectYourItem(){

        Intents.init();

        //Login with user testing 123 and go to NewEvent ACtivity to have
        //quirks to log
        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());

        //go to Trade
        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_trade))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.yourcollection)).atPosition(0).
         onChildView(withId(R.id.collectiblebox)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.yourcollection)).atPosition(0).
                onChildView(withId(R.id.collectiblebox)).check(matches(isChecked()));
        intended(hasComponent(TradeActivity.class.getName()), times(1));

        Intents.release();

    }

    //Filter for use and select user's item
    @Test
    public void Filter(){

        Intents.init();

        //Login with user testing 123 and go to NewEvent ACtivity to have
        //quirks to log
        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());

        //go to Trade
        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_trade))
                .perform(click());
        onView(withId(R.id.collectionspinner)).perform(click());


        onData(hasToString(startsWith("intest2")))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.theircollection)).atPosition(0).
                onChildView(withId(R.id.collectiblebox)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.theircollection)).atPosition(0).
                onChildView(withId(R.id.collectiblebox)).check(matches(isChecked()));
        intended(hasComponent(TradeActivity.class.getName()), times(1));

        Intents.release();

    }


    //Trade an item
    @Test
    public void TradeRequest(){

        Intents.init();

        //Login with user testing 123 and go to NewEvent ACtivity to have
        //quirks to log
        onView(withId(R.id.loginUser))
                .perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());

        //go to Trade
        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_trade))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.yourcollection)).atPosition(0).
                onChildView(withId(R.id.collectiblebox)).perform(click());

        //
        onView(withId(R.id.collectionspinner)).perform(click());

        onData(hasToString(startsWith("intest2")))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.theircollection)).atPosition(0).
                onChildView(withId(R.id.collectiblebox)).perform(click());

        onView(withId(R.id.tradebtn)).perform(click());

        onView(withText("Trade request sent!")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        intended(hasComponent(TradeActivity.class.getName()), times(1));

        Intents.release();

    }



}
