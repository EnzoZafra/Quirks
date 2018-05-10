package cmput301f17t12.quirks.Models;

import java.io.Serializable;

import cmput301f17t12.quirks.Enumerations.DropType;

public class Drop implements Serializable {

    private DropType dropType;
    private boolean isSelected;

    /**
     * Constructor for the drop object
     * @param dropType DropType enumeration of the object's dropType
     */
    public Drop(DropType dropType){
        this.dropType = dropType;
        this.isSelected = false;
    }

    /**
     * Get drop dropType level
     * @return DropType enum of the drop
     */
    public DropType getDropType(){
        return dropType;
    }

    /**
     * Toggle the isSelected value of the Drop
     * @param val boolean value
     */
    public void setSelected(boolean val) {
        this.isSelected = val;
    }

    /**
     * Returns isSelected
     * @return
     */
    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Drop))return false;
        Drop otherDrop = (Drop)other;

        return (this.dropType == otherDrop.dropType && this.isSelected == otherDrop.isSelected);
    }
}
