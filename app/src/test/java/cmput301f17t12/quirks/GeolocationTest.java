package cmput301f17t12.quirks;

import org.junit.Test;

import cmput301f17t12.quirks.Models.Geolocation;

import static org.junit.Assert.*;

public class GeolocationTest {

    // Test set latitude (lat,lon)
    @Test
    public void testSetLatitude() {
        Geolocation geoLoc = new Geolocation(123.321,1234.4321);
        geoLoc.setLatitude(2234.23);
        double actual = 2234.23;
        assertEquals(geoLoc.getLatitude(), actual,0);
    }

    // Test get longitude (lat,lon)
    @Test
    public void testSetLongitude() {
        Geolocation geoLoc = new Geolocation(123.321,1234.4321);
        geoLoc.setLongitude(123.123);
        double actual = 123.123;
        assertEquals(geoLoc.getLongitude(), actual,0);
    }
}

