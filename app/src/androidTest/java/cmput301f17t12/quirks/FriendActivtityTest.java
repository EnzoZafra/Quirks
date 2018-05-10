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
import cmput301f17t12.quirks.Activities.FriendActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.MainActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;


/**
 * Created by charleshoang on 2017-12-03.
 */


@RunWith(AndroidJUnit4.class)
public class FriendActivtityTest {

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

    //Friends delete button
    @Test
    public void deleteButton(){

        Intents.init();

        //Login with user testing 123 and go to NewEvent ACtivity to have
        //quirks to log
        onView(withId(R.id.loginUser))
                .perform(typeText("intest4"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());

        //Similar to @Before, to enusre friend exists in Friends List
        //Accept friend request for deletion
        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_request))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.listviewRequest)).atPosition(0).
                onChildView(withId(R.id.buttonAccept)).perform(click());

        //go to friend activity
        onView(withId(R.id.action_social))
                .perform(click());
        onView(withId(R.id.action_friends))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.friendlistView)).atPosition(0).
                onChildView(withId(R.id.delete_friend_but)).perform(click());

        intended(hasComponent(FriendActivity.class.getName()), times(3));

        Intents.release();

    }




}
