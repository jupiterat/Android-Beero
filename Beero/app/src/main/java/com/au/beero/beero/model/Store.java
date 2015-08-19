package com.au.beero.beero.model;

import com.au.beero.beero.utility.Constants;

import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
        if (jsonObject == null) {
            return;
        } else {
            try {
                setId(jsonObject.getString(Constants.SERVER_RES_KEY.RES_ID));
            } catch (Exception e) {

            }
            try {
                setLat(jsonObject.getString(Constants.SERVER_RES_KEY.RES_LAT));
            } catch (Exception e) {

            }
            try {
                setLng(jsonObject.getString(Constants.SERVER_RES_KEY.RES_LNG));
            } catch (Exception e) {

            }
            try {
                setName(jsonObject.getString(Constants.SERVER_RES_KEY.RES_NAME));
            } catch (Exception e) {

            }
            try {
                setIsMember(jsonObject.getBoolean(Constants.SERVER_RES_KEY.RES_IS_MEMBER));
            } catch (Exception e) {

            }
            try {
                setAddress(jsonObject.getString(Constants.SERVER_RES_KEY.RES_ADDRESS));
            } catch (Exception e) {

            }
            try {
                setPhone(jsonObject.getString(Constants.SERVER_RES_KEY.RES_PHONE));
            } catch (Exception e) {

            }
            try {
                setHasCatalog(jsonObject.getBoolean(Constants.SERVER_RES_KEY.RES_HAS_CATALOG));
            } catch (Exception e) {

            }
            try {
                setHasBanner(jsonObject.getBoolean(Constants.SERVER_RES_KEY.RES_HAS_BANNER_IMG));
            } catch (Exception e) {

            }
            try {
                setHasMgr(jsonObject.getBoolean(Constants.SERVER_RES_KEY.RES_HAS_MGR_IMG));
            } catch (Exception e) {

            }
            try {
                setMgrWelcome(jsonObject.getString(Constants.SERVER_RES_KEY.RES_MGR_WELCOME));
            } catch (Exception e) {

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
            } catch (Exception e) {

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

    private int getStoreState() {
        try {
            OpenTime openHoursToday = getOpenTimeToday();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdfDate = new SimpleDateFormat(Constants.STORE_DATE_TIME_FORMAT, Locale.ENGLISH);
            Date open = sdfDate.parse(openHoursToday.getOpenTime());
            Date close = sdfDate.parse(openHoursToday.getCloseTime());
            Date current = new Date();
            String currentFormat = sdfDate.format(current);
            current = sdfDate.parse(currentFormat);
            if (current.before(open)) {
//                return "Open at " + openHoursToday.getOpenTime();
                return Constants.STORE_STATE.WAITING;
            } else if (current.after(open) && current.before(close)) {
//                return "Till " + openHoursToday.getCloseTime();
                return Constants.STORE_STATE.OPENING;
            } else {
//                return "Closed";
                return Constants.STORE_STATE.CLOSED;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getBeautifiedLabelForOpenTimeToday() {
        int storeState = getStoreState();
        switch (storeState) {
            case Constants.STORE_STATE.WAITING:
                return "Open at " + getOpenTimeToday().getOpenTime();
            case Constants.STORE_STATE.OPENING:
                return "Till " + getOpenTimeToday().getCloseTime();
            /*case Constants.STORE_STATE.CLOSED:
                return "Closed";
                break;*/
            default:
                return "Closed";
        }
    }

    public long getRemainingTime() {
        int storeState = getStoreState();
//        int storeState = Constants.STORE_STATE.OPENING;
        if (storeState == Constants.STORE_STATE.OPENING) {
            try {
                OpenTime openHoursToday = getOpenTimeToday();
                SimpleDateFormat sdfDate = new SimpleDateFormat(Constants.STORE_DATE_TIME_FORMAT, Locale.getDefault());
                Date close = sdfDate.parse(openHoursToday.getCloseTime());
//                Date close = sdfDate.parse("11:00 AM");
                Date current = new Date();
                String currentFormat = sdfDate.format(current);
                current = sdfDate.parse(currentFormat);
                long duration = close.getTime() - current.getTime();
                if (duration < 0) {
                    return -1;
                }
                long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
                if (diffInHours > 0) {
                    return -1;
                } else {
                    long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                    return diffInMinutes;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    private OpenTime getOpenTimeToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String weekdays[] = new DateFormatSymbols(Locale.ENGLISH).getWeekdays();
        String todayName = weekdays[dayOfWeek];
        String openHoursToday = null;
        for (int i = 0; i < this.openHours.size(); i++) {
            OpenTime openTime = openHours.get(i);
            if (openTime.getDay().equalsIgnoreCase(todayName)) {
                return openTime;
//                openHoursToday = openTime.getOpenTime();
//                break;
            }
        }
        return null;
    }
}
