package com.au.beero.beero.model;

import com.au.beero.beero.utility.Constants;

import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by thuc.phan on 8/15/2015.
 */
public class OpenTime implements Comparable<OpenTime> {
    private String day;
    private String openTime;
    private String closeTime;

    public OpenTime() {
    }

    public OpenTime(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        } else {
            try {
                setOpenTime(getHour(jsonObject.getString(Constants.SERVER_RES_KEY.RES_OPEN)));
            } catch (Exception e) {

            }
            try {
                setCloseTime(getHour(jsonObject.getString(Constants.SERVER_RES_KEY.RES_CLOSE)));
            } catch (Exception e) {

            }
        }
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        String[] weekDay = DateFormatSymbols.getInstance().getWeekdays();
        for (String someDay : weekDay) {
            if (someDay.toLowerCase().startsWith(day)) {
                this.day = someDay;
                break;
            }

        }
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public static String getHour(String hourString) {
        try {
            String patten = "hmm";
            if (hourString.length() == 4) {
                patten = "hhmm";
            }
            SimpleDateFormat sdfDate = new SimpleDateFormat(patten, Locale.ENGLISH);//dd/MM/yyyy
            Date strDate = sdfDate.parse(hourString);
            String result = new SimpleDateFormat("hh:mm a").format(strDate);
//            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public int compareTo(OpenTime openTime) {
        String[] weekDay = DateFormatSymbols.getInstance().getWeekdays();
        String day1 = getDay();
        int index1 = 0;
        int index2 = 0;
        String day2 = openTime.getDay();
        for (int i = 0; i < weekDay.length; i++) {
            String day = weekDay[i];
            if (day.equalsIgnoreCase(day1)) {
                index1 = i;
            }
            if (day.equalsIgnoreCase(day2)) {
                index2 = i;
            }
        }
        if (index2 > index1) {
            return 1;
        } else if (index1 == index2) {
            return 0;
        } else {
            return -1;
        }
    }
}
