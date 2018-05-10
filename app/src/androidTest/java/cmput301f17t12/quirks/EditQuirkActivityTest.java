package cmput301f17t12.quirks;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f17t12.quirks.Activities.AddQuirkActivity;
import cmput301f17t12.quirks.Activities.EditEventActivity;
import cmput301f17t12.quirks.Activities.EditQuirkActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.MapActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by root on 11/13/17.
 */

//Note before testing make sure intentTesting has quirks inside its quirklist
@RunWith(AndroidJUnit4.class)
public class EditQuirkActivityTest {
    String title;
    String type, reason,goal;
    int year;
    int monthOfYear;
    int dayOfMonth;
    String month;
    String day;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void initQuirk()
    {
        loginActivity = mActivityRule.getActivity();

        //Variables to test changes
        title = "New Title";
        type = "New Type";
        reason = "New Reason";
        goal = "16";
        year = 2017;
        monthOfYear = 12;
        dayOfMonth = 01;

        //Dates for date string
        if (monthOfYear < 10){
            month = "0" + Integer.toString(monthOfYear);
        }
        else{
            month = Integer.toString(monthOfYear);
        }
        if (dayOfMonth < 10){
            day = "0" + Integer.toString(dayOfMonth);
        }
        else{
            day = Integer.toString(dayOfMonth);
        }
    }


    @Test
    //test that Title an be inputted and saved
    public void EditQuirkTitleTest(){
        Intents.init();
        //Navigate to EditQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).
                perform(click());

        //Test to change Title and check if it is saved properly
        onView(withId(R.id.QuirkeditTextTitle)).perform(replaceText(title), closeSoftKeyboard());
        onView(withId(R.id.SaveBut)).perform(click());
        intended(hasComponent(QuirksActivity.class.getName()));
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());
        onView(withId(R.id.QuirkeditTextTitle)).check(matches(withText(title)));
        intended(hasComponent(EditQuirkActivity.class.getName()),times(2));
        Intents.release();

    }

    //test that Type an be edited and saved
    @Test
    public void EditQuirkType(){
        Intents.init();
        //Navigate to EditQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());
        //Test to change Type and check if it is saved properly
        onView(withId(R.id.QuirkeditTextType)).perform(replaceText(type), closeSoftKeyboard());
        onView(withId(R.id.SaveBut)).perform(click());
        intended(hasComponent(QuirksActivity.class.getName()));
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());
        onView(withId(R.id.QuirkeditTextType)).check(matches(withText(type)));
        intended(hasComponent(EditQuirkActivity.class.getName()),times(2));
        Intents.release();
    }
    //test that Reason  can be edited and saved
    @Test
    public void EditQuirkReason(){
        Intents.init();
        //Navigate to EditQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());

        //Test to change Reason and check if it is saved properly
        onView(withId(R.id.QuirkeditTextReason)).perform(replaceText(reason), closeSoftKeyboard());
        onView(withId(R.id.SaveBut)).perform(click());
        intended(hasComponent(QuirksActivity.class.getName()),times(1));
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());
            onView(withId(R.id.QuirkeditTextReason)).check(matches(withText(reason)));
        intended(hasComponent(EditQuirkActivity.class.getName()),times(2));
        Intents.release();

    }
    //test that Date an be edited and saved
    @Test
    public void EditQuirkDate(){
        Intents.init();
        //Navigate to EditQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());
        //Test to change Starting Date and check if it is saved properly
        onView( withId(R.id.textViewSelectStartingDateEdit)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).
                perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.SaveBut)).perform(click());

        intended(hasComponent(QuirksActivity.class.getName()));
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());

        String compare = month + "/"
                + day + "/" + Integer.toString(year) + " ";
        onView(withId(R.id.textViewSelectStartingDateEdit)).check(matches(withText(compare)));
        Intents.release();
    }
    //test that Goal an be edited and saved
    @Test
    public void EditQuirkGoal(){
        Intents.init();
        //Navigate to EditQuirkActivity
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());
        //Test to change Goal and check if it is saved properly
        onView(withId(R.id.QuirkeditTextGoal)).perform(replaceText(String.valueOf(goal)));
        onView(withId(R.id.SaveBut)).perform(click());
        intended(hasComponent(QuirksActivity.class.getName()));
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());
        onView(withId(R.id.QuirkeditTextGoal)).check(matches(withText(goal)));
        intended(hasComponent(EditQuirkActivity.class.getName()),times(2));
        Intents.release();
    }

    //Test Cancel button
    @Test
    public void EditQuirkCancel(){
        Intents.init();
        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_quirklist)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(0).perform(click());
        onView(withId(R.id.CancelBut)).perform(click());
        intended(hasComponent(QuirksActivity.class.getName()),times(1));
        intended(hasComponent(EditQuirkActivity.class.getName()),times(1));
        Intents.release();
    }

    //Test delet Quirk
    @Test
    public void EditQuirkDelete(){
        Intents.init();

        onView(withId(R.id.loginUser)).perform(typeText("intest3"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());

        //Create a Quirk to be deleted
        onView(withId(R.id.action_quirklist)).perform(click());

        onView(withId(R.id.add_quirk_button)).perform(click());
        intended(hasComponent(AddQuirkActivity.class.getName()),times(1));
        onView(withId(R.id.QuirkEditradioButtonMon)).perform(click());
        onView(withId(R.id.QuirkeditTextReason)).perform(typeText("Reason"),closeSoftKeyboard());
        onView(withId(R.id.QuirkeditTextTitle)).perform(typeText("Title"), closeSoftKeyboard());
        onView(withId(R.id.QuirkeditTextType)).perform(typeText("Type"),closeSoftKeyboard());
        onView(withId(R.id.QuirkeditTextGoal)).perform(typeText(String.valueOf("15")));


        onView( withId(R.id.textViewSelDate)).perform(click());
        ;
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).
                perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.QuirkeditTextTitle)).perform(closeSoftKeyboard());

        onView(withId(R.id.SaveBut)).perform(click());

        //Delete the Quirk
        onView(withId(R.id.action_quirklist)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.quirk_listview)).atPosition(1).perform(click());
        onView(withId(R.id.DeleteBut)).perform(click());

        intended(hasComponent(QuirksActivity.class.getName()),times(2));
        intended(hasComponent(EditQuirkActivity.class.getName()),times(1));
        Intents.release();
    }







}
