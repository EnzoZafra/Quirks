package cmput301f17t12.quirks;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import cmput301f17t12.quirks.Activities.AddQuirkActivity;
import cmput301f17t12.quirks.Activities.EditQuirkActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.MainActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;
import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Models.Quirk;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by root on 11/13/17.
 */

@RunWith(AndroidJUnit4.class)
public class AddQuirkActivityTest {
    Quirk Quirktest;
    String quirky;
    @Rule
    //Login activity as starting view
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity loginActivity;


    @Before
    public void initQuirk(){
        //initialize the activity
        loginActivity = mActivityRule.getActivity();

    }

    //Test able to input Title
    @Test
    public void addQuirkTitleTest(){
        Intents.init();
        //go to AddQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onView(withId(R.id.add_quirk_button)).perform(click());

        //Title used to test
        String title = "Title";
        onView(withId(R.id.QuirkeditTextTitle)).perform(typeText("Title"), closeSoftKeyboard());
        onView(withId(R.id.QuirkeditTextTitle)).check(matches(withText(title)));
        intended(hasComponent(AddQuirkActivity.class.getName()), times(1));
        Intents.release();
    }
    //Test able to input Type
    @Test
    public void addQuirkTypeTest(){
        Intents.init();
        //go to AddQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onView(withId(R.id.add_quirk_button)).perform(click());

        //Type used to Test
        String type = "Type";
        onView(withId(R.id.QuirkeditTextType)).perform(typeText("Type"),closeSoftKeyboard());
        onView(withId(R.id.QuirkeditTextType)).check(matches(withText(type)));
        intended(hasComponent(AddQuirkActivity.class.getName()), times(1));
        Intents.release();
    }
    //Test able to input Reason
    @Test
    public void addQuirkReasonTest(){
        Intents.init();
        //go to AddQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onView(withId(R.id.add_quirk_button)).perform(click());

        //Reason to test
        String reason = "Reason";
        onView(withId(R.id.QuirkeditTextReason)).perform(typeText("Reason"),closeSoftKeyboard());
        onView(withId(R.id.QuirkeditTextReason)).check(matches(withText(reason)));
        intended(hasComponent(AddQuirkActivity.class.getName()), times(1));
        Intents.release();

    }

    //Test able to input Goal
    @Test
    public void addQuirkGoalTest(){
        Intents.init();
        //go to AddQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onView(withId(R.id.add_quirk_button)).perform(click());
        //Test Goal to test input
        String goal= "15";
        onView(withId(R.id.QuirkeditTextGoal)).perform(typeText(String.valueOf("15")));
        onView(withId(R.id.QuirkeditTextGoal)).check(matches(withText(goal)));
        intended(hasComponent(AddQuirkActivity.class.getName()), times(1));
        Intents.release();

    }

    //Test able to select occurence radio bttons
    @Test
    public void addQuirkOccTest(){
        Intents.init();
        //go to AddQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onView(withId(R.id.add_quirk_button)).perform(click());
        //Click Monday Radio Buton
        onView(withId(R.id.QuirkEditradioButtonMon)).perform(click());
        onView(withId(R.id.QuirkEditradioButtonMon)).check(matches(isChecked()));
        intended(hasComponent(AddQuirkActivity.class.getName()), times(1));
        Intents.release();

    }
//    https://stackoverflow.com/questions/31807182/android-espresso-issue-dependency-conflict
    @Test
    public void addQuirkDateTest(){
       Intents.init();
        //go to AddQuirkActivity
       onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
       onView(withId(R.id.loginBtn)).perform(click());
       onView(withId(R.id.action_quirklist)).perform(click());
       onView(withId(R.id.add_quirk_button)).perform(click());
        //IntentTest with the Date for Quirk StartDate
       int year  = 2017;
       int monthOfYear  = 11;
       int dayOfMonth =  25;
        onView( withId(R.id.textViewSelDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).
                perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.textViewSelDate)).check(matches(withText("11/25/2017")));
        intended(hasComponent(AddQuirkActivity.class.getName()), times(1));
        Intents.release();

    }
    //Test able to input save Quirk and view it to ensure proper data is stored
    //Then delete the Quirk for testing maintenance
    @Test
    public void addQuirkSaveButton(){
        Intents.init();
        //go to AddQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onView(withId(R.id.add_quirk_button)).perform(click());
        //Fill all fields then save
        onView(withId(R.id.QuirkEditradioButtonMon)).perform(click());
        onView(withId(R.id.QuirkeditTextReason)).perform(typeText("Reason"),closeSoftKeyboard());

        onView(withId(R.id.QuirkeditTextTitle)).perform(typeText("Title"), closeSoftKeyboard());
        onView(withId(R.id.QuirkeditTextType)).perform(typeText("Type"),closeSoftKeyboard());
        onView(withId(R.id.QuirkeditTextGoal)).perform(typeText(String.valueOf("15")));
        onView(withId(R.id.QuirkeditTextGoal)).perform(closeSoftKeyboard());
        int year  = 2017;
        int monthOfYear  = 11;
        int dayOfMonth =  25;
        onView( withId(R.id.textViewSelDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).
                perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.SaveBut)).perform(click());
        //Check that hte correct activities were visited
        intended(hasComponent(QuirksActivity.class.getName()));
        intended(hasComponent(AddQuirkActivity.class.getName()), times(1));
        //Go to View Quirk to ensure that data is properly saved then delete it
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(1).
                perform(click());
        intended(hasComponent(EditQuirkActivity.class.getName()));
        onView(withId(R.id.QuirkeditTextTitle)).check(matches(withText("Title")));
        onView(withId(R.id.QuirkeditTextType)).check(matches(withText("Type")));
        onView(withId(R.id.QuirkeditTextGoal)).check(matches(withText("15")));
        onView(withId(R.id.QuirkeditTextReason)).check(matches(withText("Reason")));
        onView(withId(R.id.textViewSelectStartingDateEdit)).check(matches(withText("11/25/2017 ")));
        onView(withId(R.id.QuirkEditradioButtonMon)).check(matches(isChecked()));
        //Delete this quirk that was created
        onView(withId(R.id.DeleteBut)).perform(click());
        Intents.release();

    }
    //Test Cancel button
    @Test
    public void addQuirkCancelButton(){
        Intents.init();
        //go to AddQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onView(withId(R.id.add_quirk_button)).perform(click());
        //Test Cancel, Ensure that it returns to QuirActivity (Second time after cancel)
        onView(withId(R.id.CancelBut)).perform(click());
        intended(hasComponent(AddQuirkActivity.class.getName()), times(1));
        intended(hasComponent(QuirksActivity.class.getName()), times(1));
        Intents.release();
    }



}
