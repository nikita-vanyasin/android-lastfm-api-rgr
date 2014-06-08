package nikita.rgr.lastfm;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nikita on 08.06.14.
 */
public class GeoInfo implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10000; // 10 km

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    private static GeoInfo instance;

    private Context context;
    private Location location;
    private LocationManager locationManager;

    public static GeoInfo getInstance() {

        return instance;
    }

    public static void createInstance(Context context) {
        instance = new GeoInfo(context);
    }

    private GeoInfo(Context context) {
        this.context = context;

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        if (isLocationInfoAvailable())
        {
            init();
        }
    }

    public double getLatitude()
    {
        return location.getLatitude();
    }

    public double getLongitude()
    {
        return location.getLongitude();
    }

    public Boolean isLocationInfoAvailable()
    {
        return isGpsEnabled() || isNetworkEnabled();
    }

    private boolean isNetworkEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean isGpsEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public String getCurrentCountryName()
    {
        Address address = getCurrentAddress();
        return address.getCountryName();
    }

    private Address getCurrentAddress()
    {
        final Locale locale = Locale.ENGLISH;
        try {
            Geocoder gcd = new Geocoder(context,locale);
            List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0)
            {
                return addresses.get(0);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error:", e);
        }

        return createFallbackAddress();
    }

    private void init()
    {
        requestLocationUpdates();

        if (isNetworkEnabled()) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (isGpsEnabled()) {
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
    }

    private void requestLocationUpdates() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            MIN_TIME_BW_UPDATES,
            MIN_DISTANCE_CHANGE_FOR_UPDATES,
            this
        );
    }

    @Override
    public void onLocationChanged(Location loc) {
        location = loc;
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private Address createFallbackAddress()
    {
        Address address = new Address(Locale.ENGLISH);
        address.setCountryName("russia");
        return address;
    }
}
