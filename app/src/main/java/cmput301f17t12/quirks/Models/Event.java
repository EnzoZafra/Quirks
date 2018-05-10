package cmput301f17t12.quirks.Models;

import android.net.Uri;
import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import cmput301f17t12.quirks.Interfaces.Mappable;
import cmput301f17t12.quirks.Interfaces.Newsable;
import io.searchbox.annotations.JestId;

public class Event implements Mappable, Newsable, Serializable {

    private String user;
    private String type;
    private String comment;
    private byte[] photoByte;
    private Date date;
    private Geolocation geolocation;

    @JestId
    private String eid;

    /**
     * Event constructor without without photobyte and geolocation
     * @param user User string
     * @param comment Comment string
     * @param date Event date
     */
    public Event(String user, String comment, Date date, String type){
        this.user = user;
        this.type = type;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Event constructor without geolocation
     * @param user User string
     * @param comment Comment string
     * @param photoByte Bytecode of the photo
     * @param date  Event date
     */
    public Event(String user, String comment, byte[] photoByte, Date date, String type) {
        this.user = user;
        this.type = type;
        this.comment = comment;
        if (comment == null) {
            this.comment = "";
        }
        this.photoByte = photoByte;
        if (photoByte == null) {
            this.photoByte = new byte[] {};
        }
        this.date = date;
    }

    /**
     * Event constructor with all parameters
     * @param user User string
     * @param comment Comment string
     * @param photoByte Bytecode of the photo
     * @param date  Event date
     * @param geolocation Location of the event
     */
    public Event(String user, String comment, byte[] photoByte, Date date, Geolocation geolocation, String type) {
        this.user = user;
        this.type = type;
        this.comment = comment;
        this.photoByte = photoByte;
        this.date = date;
        this.geolocation = geolocation;
    }

    /**
     * Get the event id
     * @return String event id
     */
    public String getId(){
        return eid;
    }

    /**
     * Set the event id
     * @param eid Event id string
     */
    public void setId(String eid){
        this.eid = eid;
    }

    /**
     * Get the Event user string
     * @return User string
     */
    public String getUser(){
        return user;
    }

    /**
     * Set the Event user string
     * @param user User string
     */
    public void setUser(String user){
        this.user = user;
    }

    /**
     * Get the Event's Comment
     * @return
     */
    public String getComment(){
        return comment;
    }

    /**
     * Set the Event's Comment
     * @param comment
     */
    public void setComment(String comment){
        this.comment = comment;
    }

    /**
     * Get the event's photo byte array
     * @return Photo byte array
     */
    public byte[] getPhotoByte(){
        return photoByte;
    }

    /**
     * Set the Event's photo byte array
     * @param photoByte Photo byte array
     */
    public void setPhotoByte(byte[] photoByte){
        this.photoByte = photoByte;
    }

    /**
     * Get the Event date
     * @return Event date
     */
    public Date getDate(){
        return date;
    }

    /**
     * Set the Event date
     * @param date Event date
     */
    public void setDate(Date date){
        this.date = date;
    }

    /**
     * Get the Event's quirk type
     * @return Event type
     */
    public String getType() { return type; }

    /**
     * Set the Event's quirk type
     * @param type
     */
    public void setType(String type) { this.type = type; }

    /**
     * Get the Event Geolocation
     * @return Event Location
     */
    public Geolocation getGeolocation(){
        return geolocation;
    }

    /**
     * Set the Event Geolocation
     * @param geolocation Event Location
     */
    public void setGeolocation(Geolocation geolocation){
        this.geolocation = geolocation;
    }

    /**
     * Delete Event GeoLocation
     */
    public void deleteGeolocation(){
        geolocation = null;
    }

    /**
     * Evaluate if two events are equal to each other
     * @param event Event to compare
     * @return Return Boolean value based on result of the comparison
     */
    public boolean isEquals(Event event){
        return this.user.equals(event.user) && this.comment.equals(event.comment) && Arrays.equals(this.photoByte, event.photoByte)
                && this.date.equals(event.date) && this.geolocation.equals(event.geolocation);
    }

    /**
     * Build news header string
     * @param extra Extra string to concatenate to user string
     * @return News header string
     */
    @Override
    public String buildNewsHeader() {
        return getUser() + " logged to " + getType() + "!";
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
        return getComment();
    }
}
