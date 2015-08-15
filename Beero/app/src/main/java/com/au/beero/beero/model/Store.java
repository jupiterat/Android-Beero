package com.au.beero.beero.model;

import com.au.beero.beero.utility.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thuc.phan on 8/12/2015.
 */
public class Store {
    private String id;
    private String lat;
    private String lng;
    private String name;
    private boolean isMember;
    private String address;
    private String phone;
    private boolean hasCatalog;
    private boolean hasBanner;
    private boolean hasMgr;
    private String mgrWelcome;
    private List<OpenTime> openHours;

    public Store() {
    }
    public Store(JSONObject jsonObject) {
        if (jsonObject == null){
            return;
        } else {
            try {
                setId(jsonObject.getString(Constants.SERVER_RES_KEY.RES_ID));
            }catch (Exception e){

            }
            try {
                setLat(jsonObject.getString(Constants.SERVER_RES_KEY.RES_LAT));
            }catch (Exception e){

            }
            try {
                setLng(jsonObject.getString(Constants.SERVER_RES_KEY.RES_LNG));
            }catch (Exception e){

            }
            try {
                setName(jsonObject.getString(Constants.SERVER_RES_KEY.RES_NAME));
            }catch (Exception e){

            }
            try {
                setIsMember(jsonObject.getBoolean(Constants.SERVER_RES_KEY.RES_IS_MEMBER));
            }catch (Exception e){

            }
            try {
                setAddress(jsonObject.getString(Constants.SERVER_RES_KEY.RES_ADDRESS));
            }catch (Exception e){

            }
            try {
                setPhone(jsonObject.getString(Constants.SERVER_RES_KEY.RES_PHONE));
            }catch (Exception e){

            }
            try {
                setHasCatalog(jsonObject.getBoolean(Constants.SERVER_RES_KEY.RES_HAS_CATALOG));
            }catch (Exception e){

            }
            try {
                setHasBanner(jsonObject.getBoolean(Constants.SERVER_RES_KEY.RES_HAS_BANNER_IMG));
            }catch (Exception e){

            }
            try {
                setHasMgr(jsonObject.getBoolean(Constants.SERVER_RES_KEY.RES_HAS_MGR_IMG));
            }catch (Exception e){

            }
            try {
                setMgrWelcome(jsonObject.getString(Constants.SERVER_RES_KEY.RES_MGR_WELCOME));
            }catch (Exception e){

            }
            try {
                JSONObject openHours = jsonObject.getJSONObject(Constants.SERVER_RES_KEY.RES_OPEN_HOURS);
                Iterator<?> keys = openHours.keys();
                List<OpenTime> openTimes = new ArrayList<>();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    try {
                        OpenTime openTime = null;
                        if (openHours.get(key) instanceof JSONObject) {
                            JSONObject object = (JSONObject) openHours.get(key);
                            openTime = new OpenTime(object);
                        } else {
                            openTime = new OpenTime();
                        }
                        openTime.setDay(key);
                        openTimes.add(openTime);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
                setOpenHours(openTimes);
//                setOpenHours(jsonObject.getString(Constants.SERVER_RES_KEY.RES_OPEN_HOURS));
            }catch (Exception e){

            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setIsMember(boolean isMember) {
        this.isMember = isMember;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isHasCatalog() {
        return hasCatalog;
    }

    public void setHasCatalog(boolean hasCatalog) {
        this.hasCatalog = hasCatalog;
    }

    public boolean isHasBanner() {
        return hasBanner;
    }

    public void setHasBanner(boolean hasBanner) {
        this.hasBanner = hasBanner;
    }

    public boolean isHasMgr() {
        return hasMgr;
    }

    public void setHasMgr(boolean hasMgr) {
        this.hasMgr = hasMgr;
    }

    public String getMgrWelcome() {
        return mgrWelcome;
    }

    public void setMgrWelcome(String mgrWelcome) {
        this.mgrWelcome = mgrWelcome;
    }

    public List<OpenTime> getOpenHours() {
        return openHours;
    }

    public void setOpenHours(List<OpenTime> openHours) {
        this.openHours = openHours;
    }
}
