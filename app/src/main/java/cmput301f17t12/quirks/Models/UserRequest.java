package cmput301f17t12.quirks.Models;

import java.io.Serializable;

public class UserRequest extends Request implements Serializable {


    /**
     * UserRequest constructor
     * @param fromUser String
     */
    public UserRequest(String fromUser){
        super(fromUser);
    }


    /**
     * returns a string to tell you we got a Friend request
     * @return String
     */
    @Override
    public String getHeader() {
        return "Friend request!";
    }

    /**
     * returns a string to ask the current user to see if they want to add the user
     * @return String getFromUser()
     */
    @Override
    public String getDetails() {
        return "Would you like to add " + getFromUser() + "?";
    }
}
