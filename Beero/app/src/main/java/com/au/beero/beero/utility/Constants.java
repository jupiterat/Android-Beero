/*
 * author : shintabmt@gmail.com
 */
package com.au.beero.beero.utility;

public class Constants {
    //
    public static final String SERVER_API_PATH = "http://beero.com.au/api/v1/";
    //
    public static final String DEVICE_TYPE = "android";
    public static final String IMAGE_PATH = "/Image/";
    public static final String LOGIN_PRE = "loginpref";
    public static final String EXTRA_USER = "user";
    public static final String EXTRA_IS_LOGGED_IN = "loggedin";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String STORE_DATE_TIME_FORMAT = "hh:mm a";
    public static final String FRAGMENT_INIT = "fragment_init";
    public static final long COUNT_DOWN_UNIT = 1000 * 60;
    //

    public static class SERVER_RES_KEY {
        public static final String RES_RESPONSE = "response";
        public static final String RES_STATUS = "status";
        public static final String RES_OK = "ok";
        public static final String RES_RESULTS = "results";
        public static final String RES_1ST_ITEM = "1";
        public static final String RES_WINNING_DEAL = "winning_deal";

        public static final String RES_BRAND_NAME = "brand_name";
        public static final String RES_QTY = "qty";
        public static final String RES_CONTAINER_TYPE = "container_type";
        public static final String RES_CONTAINER_SIZE = "container_size";
        public static final String RES_PRICE = "price";
        public static final String RES_DRIVING_DISTANCE = "driving_distance";
        public static final String RES_DRIVING_TIME = "driving_time";
        public static final String RES_IS_EXCLUSIVE = "is_exclusive";
        public static final String RES_STORE_DETAILS = "store_details";
        public static final String RES_ID = "id";
        public static final String RES_LAT = "lat";
        public static final String RES_LNG = "lng";
        public static final String RES_NAME = "name";
        public static final String RES_IS_MEMBER = "is_member";
        public static final String RES_ADDRESS = "address";
        public static final String RES_PHONE = "phone";
        public static final String RES_HAS_CATALOG = "has_catalog";
        public static final String RES_HAS_BANNER_IMG = "has_banner_img";
        public static final String RES_HAS_MGR_IMG = "has_mgr_img";
        public static final String RES_MGR_WELCOME = "mgr_welcome";
        public static final String RES_OPEN_HOURS = "open_hours";
        public static final String RES_MON = "mon";
        public static final String RES_OPEN = "open";
        public static final String RES_CLOSE = "close";
        public static final String RES_LOSING_DEALS = "losing_deals";
        public static final String RES_PRICE_PER_LITRE = "price_per_litre";
        public static final String RES_STORE_NAME = "store_name";
        public static final String RES_REASON = "reason";
        public static final String RES_INFO = "info";
        public static final String RES_POSITION = "position";
        public static final String RES_MESSAGE = "message";
    }


    public static class SERVER_PATH {
        //
        public static final String SEARCH = "search";
        public static final String BRANDS = "brands";
    }

    public static class SERVER_API_KEY {
    }

    public enum WEEKDAY {
        WEEKDAY_SUNDAY("sun"),
        WEEKDAY_MONDAY("mon"),
        WEEKDAY_TUESDAY("tue"),
        WEEKDAY_WEDNESDAY("wed"),
        WEEKDAY_THURSDAY("thu"),
        WEEKDAY_FRIDAY("fri"),
        WEEKDAY_SATURDAY("sat");
        private final String name;

        WEEKDAY(String name) {
            this.name = name;
        }

        private String getName() {
            return this.name;
        }
    }

    public static class STORE_STATE {
        public static final int WAITING = 0;
        public static final int OPENING = 1;
        public static final int CLOSED = 2;
    }
}
