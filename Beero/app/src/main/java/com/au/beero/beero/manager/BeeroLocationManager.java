package com.au.beero.beero.manager;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.au.beero.beero.R;
import com.framework.plistparser.xml.plist.PListXMLHandler;
import com.framework.plistparser.xml.plist.PListXMLParser;
import com.framework.plistparser.xml.plist.domain.Array;
import com.framework.plistparser.xml.plist.domain.Dict;
import com.framework.plistparser.xml.plist.domain.PList;
import com.framework.plistparser.xml.plist.domain.PListObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shintabmt@gmai.com on 8/12/2015.
 */
public class BeeroLocationManager {
    private static BeeroLocationManager sInstance = null;
    private Context mContext;
    private LocationManager mLocationManager;
    private Location mLocation;
    private boolean mIsEnableLocationServices = false;
    // The minimum distance to change Updates in meters
    private final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 20; // 10 meters

    // The minimum time between updates in milliseconds
    private final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


    private static final double LOCATIONMANAGER_DEFAULT_LOCATION_LATITUDE = -33.731628;
    private static final double LOCATIONMANAGER_DEFAULT_LOCATION_LONGITUDE = 151.216935;

    public static void initialize(Context context) {
        if (sInstance == null) {
            sInstance = new BeeroLocationManager();
            sInstance.mContext = context;
            sInstance.mLocation = sInstance.getLocation();
        }
    }

    public static BeeroLocationManager makeInstance() {
        return sInstance;
    }

    public Location getCurrentLocation() {
        if (mLocation == null) {
            mLocation = getLocation();
        }
        //TODO: for testing - remove later
        mLocation = new Location("");
        mLocation.setLatitude(LOCATIONMANAGER_DEFAULT_LOCATION_LATITUDE);
        mLocation.setLongitude(LOCATIONMANAGER_DEFAULT_LOCATION_LONGITUDE);
        return mLocation;
    }

    private Location getLocation() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }
        Location location = null;
        try {
            // getting GPS status
            boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
//				showSettingsAlert();
                this.mIsEnableLocationServices = false;
            } else {
                this.mIsEnableLocationServices = true;

                // First get location from Network Provider
                if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
                    Log.d("Network", "Network");
                    if (mLocationManager != null) {
                        location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mLocationManager != null) {
                            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            return location;
                        }
                    }
                }
            }
            mLocation = location;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    private List<Dict> getSupportArea() {
        PListXMLParser parser = new PListXMLParser();
        PListXMLHandler handler = new PListXMLHandler();
        parser.setHandler(handler);
        try {
            InputStream file = mContext.getResources().openRawResource(R.raw.supported_area);
            parser.parse(file);
            PList pList = ((PListXMLHandler) parser.getHandler()).getPlist();
            Array root = (Array) pList.getRootElement();
            List<Dict> mapList = new ArrayList<>();
            for (PListObject obj : root) {
                switch (obj.getType()) {
                    case DICT:
                        Dict dictionaryObj = (Dict) obj;
                        mapList.add(dictionaryObj);
                        break;
                    case STRING:
                        com.framework.plistparser.xml.plist.domain.String stringObj = (com.framework.plistparser.xml.plist.domain.String) obj;
                        break;

                    default:
                        break;
                }
            }
            return mapList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSupportArea() {
        return true;
//        if (mLocation == null) {
//            return false;
//        }
//        List<Dict> mapList = getSupportArea();
//        double lat = getLatitude();
//        double lng = getLongitude();
//        if (mapList != null) {
//            for (int i = 0; i < mapList.size(); i++) {
//                Dict dict = mapList.get(i);
//                double topLat = Double.valueOf(dict.getConfiguration("_TOP_LAT").getValue());
//                double leftLng = Double.valueOf(dict.getConfiguration("_LEFT_LNG").getValue());
//                double botLat = Double.valueOf(dict.getConfiguration("_BOTTOM_LAT").getValue());
//                double rightLng = Double.valueOf(dict.getConfiguration("_RIGHT_LNG").getValue());
//                if ((lat <= topLat) && (lat >= botLat) && (lng >= leftLng) && (lng <= rightLng))
//                    return true;
//            }
//        }
//        return false;
    }

    /*public boolean isSupportArea(Location location) {
        if (location == null) {
            return false;
        }
        List<Dict> mapList = getSupportArea();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        if (mapList != null) {
            for (int i = 0; i < mapList.size(); i++) {
                Dict dict = mapList.get(i);
                double topLat = Double.valueOf(dict.getConfiguration("_TOP_LAT").getValue());
                double leftLng = Double.valueOf(dict.getConfiguration("_LEFT_LNG").getValue());
                double botLat = Double.valueOf(dict.getConfiguration("_BOTTOM_LAT").getValue());
                double rightLng = Double.valueOf(dict.getConfiguration("_RIGHT_LNG").getValue());
                if ((lat <= topLat) && (lat >= botLat) && (lng >= leftLng) && (lng <= rightLng))
                    return true;
            }
        }
        return false;
    }*/

    public double getLatitude() {
        return LOCATIONMANAGER_DEFAULT_LOCATION_LATITUDE;
//        if (getCurrentLocation() == null) {
//            return LOCATIONMANAGER_DEFAULT_LOCATION_LATITUDE;
//        } else {
//            return getCurrentLocation().getLatitude();
//        }
    }

    public double getLongitude() {
        return LOCATIONMANAGER_DEFAULT_LOCATION_LONGITUDE;
//        if (getCurrentLocation() == null) {
//            return LOCATIONMANAGER_DEFAULT_LOCATION_LONGITUDE;
//        } else {
//            return getCurrentLocation().getLongitude();
//        }
    }


    public boolean isEnableLocationServices() {
//        return mIsEnableLocationServices;
        return true;
    }


    private final LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };
}
