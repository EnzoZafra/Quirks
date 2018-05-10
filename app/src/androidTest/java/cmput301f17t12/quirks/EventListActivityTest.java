package cmput301f17t12.quirks;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f17t12.quirks.Activities.EditEventActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * Created by charleshoang on 2017-11-13.
 */
@RunWith(AndroidJUnit4.class)
public class EventListActivityTest {

    String comment;
    String username;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void initialize() {
        // Specify a valid comment
        comment = "testing comment";
        username = "intest3";
        loginActivity = mActivityRule.getActivity();
    }

    //Test the button to view/Edit Event to change activity
    @Test
    public void viewButton() {
        // Type username and go to quirk list then event list of first quirk
        Intents.init();

        //Navigate to EventListActivity
        onView(withId(R.id.loginUser))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.loginBtn))
                .perform(click());
        onView(withId(R.id.action_quirklist))
                .perform(click());
        //Test to ensure that the ViewButton is functional and leads to EditEventActivity
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).
                onChildView(withId(R.id.quirk_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.el_eventslistview)).atPosition(0).
                onChildView(withId(R.id.el_eventview)).perform(click());

        intended(hasComponent(EditEventActivity.class.getName()));


        Intents.release();
    }
}

