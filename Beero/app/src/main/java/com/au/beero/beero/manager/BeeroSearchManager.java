package com.au.beero.beero.manager;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import com.au.beero.beero.utility.Constants;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by shintabmt@gmai.com on 8/13/2015.
 */
public class BeeroSearchManager {
    private static final String UTF8_CHARSET = "UTF-8";
    private static final String BEERO_SECRECT_KEY = "21cda18e56dae228eb5b367f5a44a8c3e702cb36bf999879d8e7c3dbf1dafc06ef3f8c4641a9dd53db5a59197de69ff50a936b5b250bf5ded9499b07569240e2";
  /*  double LOCATIONMANAGER_DEFAULT_LOCATION_LATITUDE = -33.731628;
    double LOCATIONMANAGER_DEFAULT_LOCATION_LONGITUDE = 151.216935;*/

    private static BeeroSearchManager sInstance;
    private Context mContext;

    public static void initialize(Context context) {
        if (sInstance == null) {
            sInstance = new BeeroSearchManager();
            sInstance.mContext = context;
        }
    }

    public static BeeroSearchManager makeInstance() {
        return sInstance;
    }

    public String getTargetUrl(String brands, String packageString, String container) {
        String targetUrl = String.format("%s%s", Constants.SERVER_API_PATH, Constants.SERVER_PATH.SEARCH);
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("os", Constants.DEVICE_TYPE));
        params.add(new BasicNameValuePair("os_version", "" + android.os.Build.VERSION.SDK_INT));
        params.add(new BasicNameValuePair("app_id", "" + Settings.Secure.getString(mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID)));
        params.add(new BasicNameValuePair("lat", String.format("%.6f", getLatitude())));
        params.add(new BasicNameValuePair("lng", String.format("%.6f", getLongitude())));
        params.add(new BasicNameValuePair("brands", brands));
        params.add(new BasicNameValuePair("package", packageString));
        params.add(new BasicNameValuePair("container", container));
        params.add(new BasicNameValuePair("user_time", timestamp()));
        params.add(new BasicNameValuePair("signature", signature()));
        String paramString = URLEncodedUtils.format(params, "utf-8");
        targetUrl += "?" + paramString;
        return targetUrl;
    }

    /**
     * Generate a ISO-8601 format timestamp as required by Amazon.
     *
     * @return ISO-8601 format timestamp.
     */
    private String timestamp() {
        String timestamp = null;
        Calendar cal = Calendar.getInstance();
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
        timestamp = dfm.format(cal.getTime());
        return timestamp;
    }

    private String signature() {
        String signature = String.format("%s_%.6f_%.6f_%s", Settings.Secure.getString(mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID), getLatitude(), getLongitude(), BEERO_SECRECT_KEY);
        try {
            return SHA1(signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double getLatitude() {
        return BeeroLocationManager.makeInstance().getLatitude();
    }

    private double getLongitude() {
        return BeeroLocationManager.makeInstance().getLongitude();
    }

    private String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}
