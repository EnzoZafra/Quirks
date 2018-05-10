package cmput301f17t12.quirks.Models;

import java.io.Serializable;

public class Geolocation implements Serializable {
    private double longitude;
    private double latitude;

    /**
     * Constructor for a Geolocation object
     * @param longitude
     * @param latitude
     */
    public Geolocation(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Get the longitude of the Geolocation
     * @return Geolocation longitude
     */
    public double getLongitude(){
        return longitude;
    }

    /**
     * Set the longitude of the Geolocation
     * @param longitude Geolocation longitude
     */
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    /**
     * Get the latitude of the Geolocation
     * @return Geolocation lattitude
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * Set the latitude of the Geolocation
     * @param latitude Geolocation latitude
     */
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

}
