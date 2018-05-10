package cmput301f17t12.quirks.Models;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

public class User implements Serializable {
    private String username;
    private Inventory inventory;
    private ArrayList<String> friends;
    private ArrayList<TradeRequest> tradeRequests;
    private ArrayList<UserRequest> userRequests;
    private QuirkList quirks;

    @JestId
    private String uid;


    /**
     * User constructor
     * @param username User string
     * @param inventory Inventory Inventory
     * @param friends ArrayList<String> String
     * @param userRequests ArrayList<UserRequest> UserRequest
     * @param tradeRequests ArrayLiost<TradeRequest>  TradeRequest
     * @param quirks QuirkList QuirkList
     */

    public User(String username, Inventory inventory, ArrayList<String> friends, ArrayList<UserRequest> userRequests, ArrayList<TradeRequest> tradeRequests, QuirkList quirks){
        this.username = username;
        this.inventory = inventory;
        this.friends = friends;
        this.quirks = quirks;
        this.tradeRequests = tradeRequests;
        this.userRequests = userRequests;
    }

    /**
     * Set the userID of the User
     * @param uid UserID string
     */
    public void setId(String uid){
        this.uid = uid;
    }

    /**
     * Get the userID of the User
     * @return UserID string
     */
    public String getId(){
        return uid;
    }

    /**
     * Get the username of the User
     * @return Username string
     */
    public String getUsername(){
        return username;
    }

    /**
     * Get the inventory of the User
     * @return Inventory object
     */
    public Inventory getInventory(){
        return inventory;
    }

    /**
     * Get the friends of the User
     * @return ArrayList<User> friends
     */
    public ArrayList<String> getFriends(){
        return friends;
    }

    /**
     * Get the available user requests for the User
     * @return ArrayList<UserRequest> requests
     */
    public ArrayList<UserRequest> getUserRequests(){return userRequests;}

    /**
     * Get the available trade requests for the User
     * @return ArrayList<TradeRequest> requests
     */
    public ArrayList<TradeRequest> getTradeRequests(){return tradeRequests;}


    /**
     * Checks if the user has any requests
     * @return boolean if the user has requests
     */
    public boolean hasRequests(){
        return (tradeRequests.size() > 0 || userRequests.size() > 0);
    }

    /**
     * Deletes a given user request from the user's list
     * @param request The request to be deleted
     */
    public void deleteUserRequest(UserRequest request){
        userRequests.remove(request);
    }

    /**
     * Deletes a given trade request from the user's list
     * @param request The request to be deleted
     */
    public void deleteTradeRequest(TradeRequest request){
        tradeRequests.remove(request);
    }

    /**
     * Adds a user request to the users list
     * @param request The request to be added
     */
    public void addUserRequest(UserRequest request){
        userRequests.add(request);
    }

    /**
     * Adds a trade request to the users list
     * @param request The request to be added
     */
    public void addTradeRequest(TradeRequest request){
        tradeRequests.add(request);
    }

    /**
     * Add a friend to the User's friend-list
     * @param friend The friend to be added
     */
    public void addFriend(String friend){
        friends.add(friend);
    }

    /**
     * Return true if the User's friend-list contains the specified friend
     * @param friend Friend to check
     * @return Boolean value based on result
     */
    public boolean hasFriend(String friend){
        return friends.contains(friend);
    }

    /**
     * Delete friend from User's friend-list
     * @param friend The friend to be deleted
     */
    public void deleteFriend(String friend){
        friends.remove(friend);
    }

    /**
     * Get Quirks associated with the User
     * @return QuirkList of all the User's Quirks
     */
    public QuirkList getQuirks() {
        return quirks;
    }

    /**
     * Add a quirk to the Users QuirkList
     * @param quirk Quirk to add
     */
    public void addQuirk(Quirk quirk){
        quirks.addQuirk(quirk);
    }

    /**
     * Remove a quirk from the Users QuirkList
     * @param quirk Quirk to remove
     */
    public void deleteQuirk(Quirk quirk){
        quirks.removeQuirk(quirk);
    }

    /**
     * Gives a given drop to the receiver User
     * and removes the given drop from this.User
     * @param myDrop Drop to be traded
     * @param receiver User that gets the drop.
     */
    public void trade(Drop myDrop, User receiver) {
        getInventory().removeDrop(myDrop);
        receiver.getInventory().addDrop(myDrop);
    }

    /**
     * Returns the User as a string (its username)
     * @return its Username
     */
    @Override
    public String toString() {
        return username;
    }
}
