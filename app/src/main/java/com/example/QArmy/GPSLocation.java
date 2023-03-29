package com.example.QArmy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**

 The LocationFetcher class is responsible for fetching the user's location using GPS, WiFi, and mobile signals to pinpoint an accurate location.

 This class uses a combination of technologies to provide the most accurate location possible. It first attempts to use GPS signals to determine the location,

 then uses WiFi signals to supplement the GPS data, and finally uses mobile signals to further improve the location estimate.

 The class is designed to be easy to use, and provides a simple API for fetching the location. It abstracts away the complexities of using multiple

 technologies and provides a single location estimate that can be used by the application.
 @author yasminghaznavian
 */
public class GPSLocation implements LocationListener {
    /**
     * The Min time interval.
     */
    final long MIN_TIME_INTERVAL = 60 * 1000L;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private Location location;          //  location.getLatitude() & location.getLongitude() will give you what you need...
    private LocationManager locationManager;

    /**
     * Instantiates a new Gps location.
     *
     * @param context the context
     */
    public GPSLocation(Context context)  {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        requestForUpdates();
    }

    /**

     This method fetches the user's current location using a combination of GPS, WiFi, and mobile signals.
     @return a Location object containing the user's current location. If the location cannot be determined, the method returns null.
     @throws LocationPermissionException if the application does not have permission to access the user's location.
     @throws LocationProviderException if there is an error fetching the location from one or more of the location providers.
     */
    @SuppressLint("MissingPermission")
    private void requestForUpdates(){

        if(locationManager == null) {
            return;
        }

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, GPSLocation.this);
            if (locationManager != null) {
                Location tempLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (tempLocation != null && isBetterLocation(tempLocation,location))
                    location = tempLocation;
            }
        }
        if (isGPSEnabled) {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, GPSLocation.this, null);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, GPSLocation.this);
            if (locationManager != null) {
                Location tempLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (tempLocation != null  && isBetterLocation(tempLocation,location))
                    location = tempLocation;
            }
        }
        stopLocationUpdates();
    }

    /**
     * Stop location updates.
     */
    public void stopLocationUpdates(){
        if(locationManager!=null){
            locationManager.removeUpdates(GPSLocation.this);
        }
    }
    /**

     This function checks whether a new location reading is more accurate than the previous location reading. It takes into account the accuracy
     and age of the two location readings to determine which one is better.
     @param location The new location reading to compare.
     @param currentBestLocation The current best location reading to compare against.
     @return true if the new location is better than the current best location, false otherwise.
     @throws IllegalArgumentException if either location parameter is null.

     // implementation details
     The function takes in two parameters, a location object representing the new location reading and a currentBestLocation object representing the previous best location reading. It returns a boolean value indicating whether the new location reading is more accurate than the previous one.

     The function also throws an IllegalArgumentException if either the location or currentBestLocation parameter is null.

     Note that the implementation details of this function will depend on the specific requirements of your application, and may include considerations such as accuracy, age, and other factors.
     */
    private boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > MIN_TIME_INTERVAL;
        boolean isSignificantlyOlder = timeDelta < -MIN_TIME_INTERVAL;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate  && isFromSameProvider) {
            return true;
        }
        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    /**

     This function is called by the location manager when a new location reading is available. It updates the current location of the user
     and triggers any necessary actions in response to the new location.
     @param loc The new location reading.
     @throws IllegalArgumentException if the loc parameter is null.

     // implementation details
     The function is an override of the onLocationChanged method in the LocationListener interface, and is called by the location manager when a new location reading is available. It takes in a loc object representing the new location reading.

     The function updates the current location of the user and triggers any necessary actions in response to the new location. It also throws an IllegalArgumentException if the loc parameter is null.

     Note that the specific implementation of this function will depend on the requirements of your application and may include actions such as updating a map display, triggering a notification, or updating a database record.
     */
    @Override
    public void onLocationChanged(Location loc) {
        if (loc != null  && isBetterLocation(loc, location)) {
            location = loc;
        }
    }
    /**

     This function is called by the location manager when the status of a location provider changes. It provides information about the new status
     of the provider and any additional data that may be useful in responding to the change.
     @param provider The name of the location provider whose status has changed.
     @param status The new status of the location provider, as an integer value.
     @param extras A bundle of additional data that may be useful in responding to the change.
     @throws IllegalArgumentException if the provider parameter is null.
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }
}