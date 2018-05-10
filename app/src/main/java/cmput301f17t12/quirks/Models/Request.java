package cmput301f17t12.quirks.Models;

import java.io.Serializable;

public abstract class Request implements Serializable {
   private String fromUser;
    /**
     * Add fromUser string and set it to the current user
     * @param fromUser fromUser to add
     */
   public Request(String fromUser){
       this.fromUser = fromUser;
   }

    /**
     * Returns which user gave the request
     * @return returns the user that sent the request
     */

   public String getFromUser(){
       return this.fromUser;
   }

    /**
     * Abstract Method for the header to tell where the request is from
     *
     */
   public abstract String getHeader();


    /**
     * Abstract Method that getDetails of the trade
     *
     */
   public abstract String getDetails();
}
