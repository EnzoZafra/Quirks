package cmput301f17t12.quirks.Models;

import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Interfaces.Newsable;
import io.searchbox.annotations.JestId;

public class Quirk implements Newsable, Serializable {
    private EventList events;
    private String user;
    private String title;
    private String type;
    private String reason;
    private Date startDate;
    private ArrayList<Day> occDate;
    private int currValue;
    private int goalValue;

    @JestId
    private String qid;

    /**
     * Quirk constructor with required values only
     * @param title Title string of the Quirk
     * @param type Type string of the Quirk
     * @param startDate Starting date of the quirk
     * @param occDate Weekdays that Quirk occurs
     * @param goalValue Progress goal value of the Quirk
     * @param user User string
     * @param reason Reason string of the quirk
     */
    public Quirk(String title, String type, Date startDate, ArrayList<Day> occDate, int goalValue, String user, String reason) {
        this.events = new EventList();
        this.title = title;
        this.type = type;
        if (startDate == null) {
            this.startDate = new Date();
        } else {
            this.startDate = startDate;
        }
        this.occDate = occDate;
        this.goalValue = goalValue;
        this.currValue = 0;
        this.user = user;
        this.reason = reason;
    }

    /**
     *
     * @param events
     * @param title
     * @param type Type string of the Quirk
     * @param reason Reason string of the quirk
     * @param startDate Starting date of the quirk
     * @param occDate Weekdays that Quirk occurs
     * @param currValue Current progress value of the Quirk
     * @param goalValue Progress goal value of the Quirk
     * @param user
     */
    public Quirk(EventList events, String title, String type, String reason,
                 Date startDate, ArrayList<Day> occDate, int currValue, int goalValue, String user){
        this.events = events;
        this.title = title;
        this.type = type;
        this.reason = reason;
        this.startDate = startDate;
        this.occDate = occDate;
        this.currValue = currValue;
        this.goalValue = goalValue;
        this.user = user;
    }

    /**
     * Get Quirk ID
     * @return Quirk ID string
     */
    public String getId(){
        return qid;
    }

    /**
     * Set Quirk ID
     * @param qid Quirk ID string
     */
    public void setId(String qid){
        this.qid = qid;
    }

    /**
     * Add Event to the Quirk
     * @param event Event to add
     */
    public void addEvent(Event event){
        events.addEvent(event);
    }

    /**
     * Get Event at specified index
     * @param i Index
     * @return Event at specified index
     */
    public Event getEvent(int i){
        return events.getEvent(i);
    }

    /**
     * Get Quirk's EventList
     * @return EventList
     */
    public EventList getEventList(){
        return events;
    }

    /**
     * Remove event from the Quirk's EventList
     * @param event Event to remove
     */
    public void removeEvent(Event event){
        events.removeEvent(event);
    }

    /**
     * Get the Quirk title
     * @return Quirk title string
     */
    public String getTitle(){
        return title;
    }

    /**
     * Set the Quirk title
     * @param title Quirk title string
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Get Quirk type
     * @return Quirk type string
     */
    public String getType(){
        return type;
    }

    /**
     * Set Quirk type
     * @param type Quirk type string
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Get Quirk reason
     * @return Quirk reason string
     */
    public String getReason(){
        return reason;
    }

    /**
     * Set Quirk reason
     * @param reason Quirk reason string
     */
    public void setReason(String reason){
        this.reason = reason;
    }

    /**
     * Get Quirk startDate
     * @return Quirk startDate
     */
    public Date getDate(){
        return startDate;
    }

    /**
     * Set Quirk startDate
     * @param startdate Quirk startDate
     */
    public void setDate(Date startdate){
        this.startDate = startdate;
    }

    /**
     * Get Weekday Occurence of the Quirk
     * @return ArrayList of Occurrence Days
     */
    public ArrayList<Day> getOccDate(){
        return occDate;
    }

    /**
     * Set Weekday Occurence of the Quirk
     * @param occDate ArrayList of Occurrence days
     */
    public void setOccDate(ArrayList<Day> occDate){
        this.occDate = occDate;
    }

    /**
     * Get the Quirk progress goal value
     * @return Quirk goal value
     */
    public int getGoalValue(){
        return goalValue;
    }

    /**
     * Set the Quirk progress goal value
     * @param goalValue Quirk goal value
     */
    public void setGoalValue(int goalValue){
        this.goalValue = goalValue;
    }

    /**
     * Get the Quirk progress current value
     * @return Quirk current value
     */
    public int getCurrValue(){
        return currValue;
    }

    /**
     * Increment the progress current value by 1
     */
    public void incCurrValue(){
        if(currValue < goalValue) {
            this.currValue += 1;
        }
    }

    /**
     * Decrement the progress current value by 1
     */
    public void decCurrValue(){
        if(currValue > 0) {
            this.currValue -= 1;
        }
    }

    /**
     * Get Quirk user string
     * @return Quirk user string
     */
    public String getUser(){ return user; }

    /**
     * Set Quirk user string
     * @param user Quirk user string
     */
    public void setUser(String user){
        this.user = user;
    }

    /**
     * Check if two quirks are equal to each other
     * @param quirk Quirk to compare
     * @return Boolean value based on the equality of the two quirks
     */
    public boolean isEquals(Quirk quirk){
        return this.user.equals(quirk.user) && this.title.equals(quirk.title) && this.type.equals(quirk.type) && this.reason.equals(quirk.reason)
                && this.startDate.equals(quirk.startDate) && this.occDate.equals(quirk.occDate) && this.currValue == quirk.currValue
                && this.goalValue == quirk.goalValue && this.events.equals(quirk.events);
    }

    /**
     * Build news header string
     * @param extra Extra string to concatenate to user string
     * @return News header string
     */
    @Override
    public String buildNewsHeader() {
        return getUser() + " added a new Quirk!";
    }

    /**
     * Build TimeSpan for the Event
     * @return Timespan
     */
    @Override
    public String buildDate() {
        CharSequence relativeTimeSpan = DateUtils.getRelativeTimeSpanString(
                getDate().getTime(),
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE);
        return relativeTimeSpan.toString();
    }

    /**
     * Build the description for the event
     * @return Description string
     */
    @Override
    public String buildNewsDescription() {
        return getTitle();
    }

    /**
     * Return quirk title as string representation of the quirk
     */
    @Override
    public String toString() {
        return getTitle();
    }

    /**
     * Return the string used for the following feed
     * @return String header string
     */
    public String getFollowingString() {
        return getUser() + " - " + getTitle();
    }
}
