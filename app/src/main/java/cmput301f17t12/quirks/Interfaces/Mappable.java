package cmput301f17t12.quirks.Interfaces;

import cmput301f17t12.quirks.Models.Geolocation;

public interface Mappable {

    Geolocation getGeolocation();
    void setGeolocation(Geolocation location);
}
