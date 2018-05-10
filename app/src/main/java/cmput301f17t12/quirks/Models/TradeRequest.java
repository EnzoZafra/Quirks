package cmput301f17t12.quirks.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class TradeRequest extends Request implements Serializable {

    private ArrayList<Drop> fromUserDrop;
    private ArrayList<Drop> myDrop;

    /**
     * TradeRequset constructor
     * @param userName User string
     * @param fromUserDrop ArrayList<Drop> Drop
     * @param myDrop ArrayList<Drop> Drop
     */
    public TradeRequest(String userName, ArrayList<Drop> fromUserDrop, ArrayList<Drop> myDrop) {
        super(userName);
        this.fromUserDrop = fromUserDrop;
        this.myDrop = myDrop;
    }


    /**
     * returns a string to tell you where the request is from
     * @return String getFromUser()
     */
    @Override
    public String getHeader() {
        return "Trade request from " + getFromUser();
    }

    /**
     * returns no details
     * @return null
     */
    @Override
    public String getDetails() {
        return null;
    }

    /**
     * Get the Arraylist of the other user's drops
     * @return ArrayList<Drop> fromUserDrop
     */
    public ArrayList<Drop> getFromUserDrop() {
        return fromUserDrop;
    }

    /**
     * returns the current user drops
     * @return ArrayList<Drop> myDrop
     */
    public ArrayList<Drop> getMyDrop() {
        return myDrop;
    }
}
