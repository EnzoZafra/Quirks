package cmput301f17t12.quirks.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class EventList implements Serializable {
    private ArrayList<Event> events = new ArrayList<Event>();

    /**
     * EventList Constructor
     */
    public EventList() {
        // Might be better to initialize eventList in here
    }

    /**
     * Add event to EventList
     * @param event Event to add
     */
    public void addEvent(Event event){
        events.add(event);
    }

    public void addAllEvents(EventList addEvents) { events.addAll(addEvents.getList()); }
    /**
     * Check if an event is within the EventList
     * @param event Event we want to find
     * @return Boolean value based on if the event is found or not
     */
    public boolean hasEvent(Event event){
        return events.contains(event);
    }

    /**
     * Clear and fill list with Events. This is used to copy the contents of an existing eventlist
     * @param eventList Eventlist we want to insert the values from
     */
    public void clearAndAddEvents(EventList eventList){
        events.clear();
        for(int i = 0; i<eventList.size(); i++){
            events.add(eventList.getEvent(i));
        }
    }

    /**
     * Get the event at a specific index
     * @param i Index
     * @return Event at specified index
     */
    public Event getEvent(int i){
        return events.get(i);
    }

    /**
     * Return the size of the EventList
     * @return EventList size
     */
    public Integer size() {
        return events.size();
    }

    /**
     * Remove event from Eventlist
     * @param event
     */
    public void removeEvent(Event event){
        events.remove(event);
    }

    /**
     * Get the full EventList
     * @return EventList ArrayList
     */
    public ArrayList<Event> getList(){
        return events;
    }

    /**
     * Return the EventList in string format. Used for testing.
     * @return String of EventList contents
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < events.size(); i++) {
            output.append(i).append(": ").append(events.get(i).getComment()).append(" ");
        }
        return output.toString();
    }
}
