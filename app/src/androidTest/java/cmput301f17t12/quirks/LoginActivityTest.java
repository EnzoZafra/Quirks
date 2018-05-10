package cmput301f17t12.quirks;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by charleshoang on 2017-11-13.
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    String username;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void initUsername() {
        // Specify a valid string.
        username = "intest2";
        loginActivity = mActivityRule.getActivity();
    }


    //Test to input a text into username box
    @Test
    public void usernameText() {
        // Type username
        onView(withId(R.id.loginUser))
                .perform(typeText(username), closeSoftKeyboard());

        // Check that the username is correct
        onView(withId(R.id.loginUser))
                .check(matches(withText(username)));
    }

    //Test to input a text into username box
    @Test
    public void emptyDialog() {
        //Click Login Button
        onView(withId(R.id.loginBtn))
                .perform(click());
        // Check that the username is empty dialog box is displayed
        onView(withText("Enter a Username.")).check(matches(isDisplayed()));

    }

    //Test that it logs in to Main Activity
    @Test

    public void startMainActivity(){
        //Need to initialize intents or you will get null exception
        Intents.init();
        // Type username
        onView(withId(R.id.loginUser))
                .perform(typeText(username), closeSoftKeyboard());

        //Click Login Button
        onView(withId(R.id.loginBtn))
                .perform(click());
        // Check that the intent is changed to main activity
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();

    }




}
