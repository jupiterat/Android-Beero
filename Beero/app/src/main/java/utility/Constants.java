/*
 * author : shintabmt@gmail.com
 */
package utility;

public class Constants {
    //
    public static final String SERVER_API_PATH = "http://us-east1-stg-pdb-app-elb-189181948.us-east-1.elb.amazonaws.com/rest/v1/";
    //
    public static final String DEVICE_TYPE = "android";
    public static final String IMAGE_PATH = "/Image/";
    public static final String LOGINPRE = "loginpref";
    public static final String EXTRA_USER = "user";
    public static final String EXTRA_IS_LOGGED_IN = "loggedin";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    //BUNDLE KEY
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String SEARCH_PARAM_KEY = "search_param";
    public static final String RECENTLY_VIEW_TYPE = "recently_type";
    public static final String POPULAR_TYPE = "popular_type";
    public static final int RATING_REQUEST_CODE = 1111;
    public static final String PRODUCT_ID = "product_id";
    public static final  String FRAGMENT_INIT = "fragment_init";
    public static final int NOTIFICATION_CODE = 2222;
    public static final int PROFILE_CODE = 3333;
    public static final int FIND_FRIEND_CODE = 4444;
    public static final int BOOKMARK_CODE = 5555;
    public static final int SCAN_HISTORY_CODE = 6666;
    public static final int LEADERBOARD_CODE = 7777;
    public static final int POINT_CODE = 8888;
    public static final int SETTING_CODE = 9999;
    //

    public static final String AMAZON_STORE_NAME = "Amazon";
    public static class SERVER_RES_KEY {
        public static final String RES_RESPONSE = "response";
        public static final String RES_AUTH = "auth";
        public static final String RES_TOKEN = "token";
        public static final String RES_SECRET = "secret";
        public static final String RES_PHOTO = "photo";
        public static final String RES_SIZES = "sizes";
        public static final String RES_USER = "user";
        public static final String RES_ID = "id";
        public static final String RES_NAME = "name";
        public static final String RES_EMAIL = "email";
        public static final String RES_REVIEW_COUNT = "review_count";
        public static final String RES_TOTAL_POINT = "points_total";
        public static final String RES_PERCENT = "percentage";
        public static final String RES_CREATE_AT = "created_at";
        public static final String RES_RATE_SCORE = "rate_score";
        public static final String RES_TOP_EXPERT = "top_expert";
        public static final String RES_EXPERTISES_COUNT = "expertises_count";
        public static final String RES_IS_ELITE = "is_elite";
        public static final String RES_IS_NEW_USER = "is_new_user";
        public static final String RES_BASE_URL = "base_url";
        public static final String RES_URL = "url";

        public static final String RES_THUMB = "thumb";
        public static final String RES_SMALL = "small";
        public static final String RES_MEDIUM = "medium";
        public static final String RES_LARGE = "large";

        public static final String RES_ERROR = "error";
        public static final String RES_ERROR_DESCRIPTION = "error_description";
        public static final String RES_DESCRIPTION = "Description";

        public static final String RES_PARENT_ID = "parent_id";
        public static final String RES_UU_ID = "uuid";
        public static final String RES_SEARCH_PARAMS = "search_params";
        public static final String RES_PHOTO_URL = "photo_url";
        public static final String RES_ICON_URL = "icon_url";
        public static final String RES_HAS_CURATED = "has_curated_photo";
        public static final String RES_SORT_ORDER = "sort_order";
        public static final String RES_IS_LEAF = "is_leaf";
        public static final String RES_IS_HIDDEN = "is_hidden";
        public static final String RES_NUMBER_PRODUCTS = "number_of_products";
        public static final String RES_PERMALINK = "permalink";


        public static final String RES_CODE = "code";
        public static final String RES_CODE_NUMERIC = "code_numeric";
        public static final String RES_IS_EARN = "is_ean";
        public static final String RES_MFR_MODEL = "mfr_model";
        public static final String RES_GS = "gs1";
        public static final String RES_SUB_GROUP = "subgroup";


        public static final String RES_RATING = "rating";
        public static final String RES_IS_CONSUMR_APPROVED = "is_consumr_approved";
        public static final String RES_RANK_IN_CATEGORY = "rank_in_category";
        public static final String RES_CONSUMR_SAY = "consumrs_say";
        public static final String RES_TEXT = "text";
        public static final String RES_COLOR = "color";
        public static final String RES_FRIEND_RATING = "friend_ratings";
        public static final String RES_NOTIFICATION = "notifications";
        public static final String RES_AWARD = "awards";
        public static final String RES_CURRENT_USER_BOOKMARKED = "current_user_bookmarked";
        public static final String RES_COMPARISON = "comparisons";
        public static final String RES_CATEGORY = "category";
        public static final String RES_BRAND = "brand";
        public static final String RES_BARCODE = "barcodes";
        public static final String RES_SCANNED_BARCODE_ID = "scanned_barcode_id";
        public static final String RES_COUNT = "count";

        public static final String RES_TYPE = "type";
        public static final String RES_ROWS = "rows";
        public static final String RES_METRIC = "metric";
        public static final String RES_TITLE = "title";
        public static final String RES_CURRENT = "current";
        public static final String RES_RANKINGS = "rankings";
        public static final String RES_RANK = "rank";
        public static final String RES_VALUE = "value";
        public static final String RES_SNAPSHOT_CODE = "snapshot_code";


        public static final String RES_PAGINATION = "pagination";
        public static final String RES_PREVIOUS = "previous";
        public static final String RES_NEXT = "next";
        public static final String RES_PER_PAGE = "per_page";
        public static final String RES_PAGES = "pages";

        public static final String RES_CHILDREN = "children";

        public static final String RES_REASON = "reason";
        public static final String RES_IS_FRIEND = "is_friend";

        public static final String RES_SUCCESS = "success";

        public static final String RES_PRODUCT = "product";
        public static final String RES_PRODUCTS = "products";
        public static final String RES_NOTIFICATION_PRICE_DROP = "notification_price_drop";
        public static final String RES_NOTIFICATION_NEW_PRODUCT_IN_CATEGORY = "notification_new_product_in_category";

        public static final String RES_MESSAGE = "message";
        public static final String RES_NOTIFIED_AT = "notified_at";
        public static final String RES_IS_CONSUMED = "is_consumed";
        public static final String RES_IS_READ = "is_read";
        public static final String RES_SOURCE = "source";
        public static final String RES_TARGET = "target";
        public static final String RES_IMAGE_URL = "image_url";
        public static final String RES_APP_URL = "app_url";
        public static final String RES_IS_ICON = "is_icon";


        public static final String RES_FRIEND_COUNT = "friend_count";
        public static final String RES_KUDOS_RECEIVED_COUNT = "kudos_received_count";
        public static final String RES_HOMETOWN = "hometown";
        public static final String RES_GENDER = "gender";
        public static final String RES_USER_TYPE = "user_type";
        public static final String RES_AGE_RANGE = "age_range";
        public static final String RES_BLOG = "blog";
        public static final String RES_SOCIAL = "social";
        public static final String RES_FACEBOOK = "facebook";
        public static final String RES_TWITTER = "twitter";
        public static final String RES_HAS_INCLUDES = "has_includes";
        public static final String RES_RATINGS_BREAKDOWN = "ratings_breakdown";
        public static final String RES_FOLLOWED_BY_CURRENT_USER = "followed_by_current_user";

        public static final String RES_ACTION = "action";
        public static final String RES_TOTAL = "total";
        public static final String RES_COUNTS = "counts";
        public static final String RES_ACTED = "acted";

        public static final String RES_KUDOS = "kudos";

        public static final String RES_PRODUCT_ID = "product_id";
        public static final String RES_SUBMITTED_AT = "submitted_at";
        public static final String RES_BODY = "body";
        public static final String RES_IS_EXTERNAL = "is_external";
        public static final String RES_BLOG_POST_URL = "blog_post_url";
        public static final String RES_IS_FIRST = "is_first";
        public static final String RES_USER_IS_EXPERT = "user_is_expert";
        public static final String RES_FEATURED = "featured";
        public static final String RES_ACHIEVED = "achieved";
        public static final String RES_PENDING = "pending";

        public static final String RES_TOTAL_PRODUCTS = "total_products";
        public static final String RES_RECALL= "recall";
        public static final String RES_METER_TEXT= "meter_text";

        public static final String RES_PRICING= "pricing";
        public static final String RES_SUMMARY= "summary";

        public static final String RES_AVERAGE_PRICE= "average_price";
        public static final String RES_PRICE= "price";
        public static final String RES_CURRENT_USER_REVIEW= "current_user_review";
        public static final String RES_SENT= "sent";


        //ebay
        public static final String RES_CATEGORIES= "categories";
        public static final String RES_ITEMS= "items";
        public static final String RES_ITEM= "item";
        public static final String RES_OFFER_URL= "offerURL";
        public static final String RES_BASE_PRICE= "basePrice";
        public static final String RES_STORE= "store";
        public static final String RES_OFFER= "offer";
        public static final String RES_LOCATION= "location";
        public static final String RES_DISTANCE= "distance";
        public static final String RES_UNITS= "units";
        public static final String RES_LATITUDE= "latitude";
        public static final String RES_LONGITUDE= "longitude";
    }


    public static class SERVER_PATH {
        //
        public static final String EMAIL_INVITE= "app/email_invite";
        //
        public static final String USER_LOGIN = "account/login";
        public static final String USER_REGISTER = "account/register";
        public static final String USER_LOGOUT = "account/logout";
        public static final String USER_VERIFY = "account/verify";
        public static final String USER_LINK= "account/link";
        //
        public static final String USER_RECENTLY_VIEWED = "users/%s/recently_viewed";
        public static final String USER_FOLLOWING = "users/%s/following";
        public static final String USER_FOLLOWER= "users/%s/followers";
        public static final String USER_FRIEND= "users/%s/friend"; //used for add or delete friend
        public static final String USER_RECOMMENDATION = "users/recommendations";
        public static final String USER_BY_ID = "users/%s";
        public static final String USER_EXPERTISE = "users/%s/expertises";
        public static final String USER_KUDO = "users/%s/kudos";
        public static final String USER_REVIEW = "users/%s/reviews";
        //
        public static final String CATEGORIES_ROOTS = "categories/roots";
        public static final String CATEGORIES_MOST_POPULAR = "categories/most_popular";
        public static final String CATEGORIES_BY_ID = "categories/%s"; //used for get all categories from parent id
        public static final String CATEGORIES_MOST_POPULAR_BY_ID = "categories/%s/popular_products";
        public static final String CATEGORIES_RECOMMENDATIONS = "categories/%s/recommendations";

        //
        public static final String LEADER_BOARD = "leaderboards";

        //
        public static final String SCANS = "scans";
        public static final String SCAN_HIDE_BY_PRODUCT_ID = "scans/%s/hide";
        //
        public static final String BOOKMARKS = "bookmarks";
        public static final String BOOK_MARK_ALL = "bookmarks/all"; //used for delete book mark
        public static final String BOOK_MARK_BY_PRODUCT_ID = "bookmarks/%s"; //used for delete book mark
        //
        public static final String NOTIFICATIONS = "notifications";
        //
        public static final String PRODUCT_MOST_POPULAR = "products/most_popular";
        public static final String PRODUCT_SEARCH = "products/search";
        public static final String PRODUCT_BY_ID = "products/%s";
        public static final String PRODUCT_RECOMMEND = "products/%s/recommendations";
        public static final String PRODUCT_INFO = "products/%s/info";
        public static final String PRODUCT_OFFER_BY_ID = "products/%s/offers";
        public static final String PRODUCT_REVIEW = "products/%s/reviews"; // used for get all/add a review
        public static final String PRODUCT_LOOKUP = "products/lookup";// for scan product

        //
        public static final String QUICK_RATE_PRODUCT ="quickrate/%s/rate";
        public static final String SKIP_RATE_PRODUCT ="quickrate/%s/skip";

        //
        public static final String REVIEW_KUDO_BY_ID = "reviews/%s/kudos"; // used for both add or remove kudo of specific review

        //
        public static final String FORGOT_PASSWORD = "/forgot_password";
    }

    public static class SERVER_API_KEY {
        public static final String CONSUMER_KEY = "XmLAhJyGAFuq9Ws1ocjUThDyhsU6lLVRrLxQ3Tkb";
        public static final String CONSUMER_SERECT = "z4nWSIlwjhLIrrRyu0q3hGvpirUnEMzSvK6Il1XQ";
        public static String TOKEN = null;
        public static String TOKEN_SECRECT = null;
    }
    public static class WEB_BASE_URL {
        public static final String STAGING = "https://staging.consumr.com";
        public static final String DEV = "http://www.consumr.dev";
        public static final String OFFICAL = "https://www.consumr.com";
    }
    public static class ORDER_TYPE {
        public static final String VIEW_COUNT = "view_count";
        public static final String EXPERT_PICKS = "expert_picks";
        public static final String RATING = "rating";
    }
    public static class SOCIAL_FRIEND_TYPE {
        public static final int TWITTER = 0;
        public static final int FACEBOOK = 1;
        public static final int CONTACT = 2;
    }

}
