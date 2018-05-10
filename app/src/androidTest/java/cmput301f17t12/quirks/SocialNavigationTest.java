package cmput301f17t12.quirks;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.ActionBar;
import android.view.Menu;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import cmput301f17t12.quirks.Activities.BaseActivity;
import cmput301f17t12.quirks.Activities.FeedActivity;
import cmput301f17t12.quirks.Activities.FindFriendActivity;
import cmput301f17t12.quirks.Activities.FriendActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.MainActivity;
import cmput301f17t12.quirks.Activities.MapActivity;
import cmput301f17t12.quirks.Activities.NewEventActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;
import cmput301f17t12.quirks.Activities.RequestActivity;
import cmput301f17t12.quirks.Activities.SocialActivity;
import cmput301f17t12.quirks.Activities.TradeActivity;
import cmput301f17t12.quirks.Helpers.BottomNavigationViewHelper;
import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.TradeRequest;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.Models.UserRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by charleshoang on 2017-11-13.
 */


@RunWith(AndroidJUnit4.class)
public class SocialNavigationTest {

    @Rule
    //Login activity as starting view
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity loginActivity;
    @Before
    public void initUsername() {
        //initialize the activity
        loginActivity = mActivityRule.getActivity();


    }

    //Test to friend button will take to FriendActivity
    @Test
    public void actionFriendClick() {

        Intents.init();
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        //Click action Home

        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_friends))
                .perform(click());

        // Check that we are in main activity (Twice because once when login and another when
        // home button is clicked
        intended(hasComponent(FriendActivity.class.getName()),times(2));
        Intents.release();
    }






    //Test to FindFriend button will take to FindFriendActivity
    @Test
    public void actionFindFriendClick() {

        Intents.init();
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        //Click action Home

        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_findFriends))
                .perform(click());

        // Check that we are in main activity (Twice because once when login and another when
        // home button is clicked
        intended(hasComponent(FriendActivity.class.getName()),times(1));
        intended(hasComponent(FindFriendActivity.class.getName()),times(1));
        Intents.release();

    }
    //
    //Test to Requests button will take to RequestActivity
    @Test

    public void actionRequestTest() {

        Intents.init();
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        //Click action Home

        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_request))
                .perform(click());

        // Check that we are in main activity (Twice because once when login and another when
        // home button is clicked
        intended(hasComponent(FriendActivity.class.getName()),times(1));
        intended(hasComponent(RequestActivity.class.getName()),times(1));
        Intents.release();

    }

    //Test to Feed button will take to FeedActivity
    @Test

    public void actionFeedTest() {

        Intents.init();
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        //Click action Home

        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_feed))
                .perform(click());

        // Check that we are in main activity (Twice because once when login and another when
        // home button is clicked
        intended(hasComponent(FriendActivity.class.getName()),times(1));
        intended(hasComponent(FeedActivity.class.getName()),times(1));
        Intents.release();
    }
    //Test to Collectibles button will take to TradeACtivity
    @Test

    public void actionCollectiblesTest() {

        Intents.init();
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        //Click action Home

        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_trade))
                .perform(click());

        // Check that we are in main activity (Twice because once when login and another when
        // home button is clicked
        intended(hasComponent(FriendActivity.class.getName()),times(1));
        intended(hasComponent(TradeActivity.class.getName()),times(1));
        Intents.release();

    }
}
