package cmput301f17t12.quirks.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class QuirkList implements Serializable {
    private ArrayList<Quirk> quirks = new ArrayList<Quirk>();

    public QuirkList(){

    }

    /**
     * Add Quirk object to the QuirkList
     * @param quirk Quirk to add
     */
    public void addQuirk(Quirk quirk){
        quirks.add(quirk);
    }

    public void addAllQuirks(QuirkList quirkList) {quirks.addAll(quirkList.getList()); }
    /**
     * Copy contents of inputted QuirkList. Called clearAndAdd because it clears the original
     * QuirkList and then adds all Quirks from the QuirkList that the class is copying from
     * @param newQuirks QuirkList that the class is copying
     */
    public void clearAndAddQuirks(QuirkList newQuirks){
        this.quirks.clear();

        for(int i = 0; i<newQuirks.size(); i++){
            this.quirks.add(newQuirks.getQuirk(i));
        }
    }

    /**
     * Returns true if the QuirkList contains the specified Quirk
     * @param quirk Quirk to check
     * @return Boolean value based on if the provided Quirk is in the QuirkList
     */
    public boolean hasQuirk(Quirk quirk){
        return quirks.contains(quirk);
    }

    /**
     * Returns the quirk at the specified position in this QuirkList.
     * @param i The Index
     * @return The Quirk at that index
     */
    public Quirk getQuirk(int i){
        return quirks.get(i);
    }

    /**
     * Removes the Quirk at the specified position in the QuirkList
     * @param quirk A Quirk
     */
    public void removeQuirk(Quirk quirk){
        quirks.remove(quirk);
    }

    /**
     * Returns the full QuirkList
     * @return The full QuirkList
     */
    public ArrayList<Quirk> getList(){
        return quirks;
    }

    /**
     * Returns the number of Quirks in the QuirkList
     * @return Number of Quirks in the QuirkList
     */
    public int size(){ return quirks.size(); }

    /**
     * Prints the contents of the QuirkList - Used for Testing
     * @return
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < quirks.size(); i++) {
            output.append(i).append(": ").append(quirks.get(i).getTitle()).append(" ");
        }
        return output.toString();
    }

}
