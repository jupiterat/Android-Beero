
package com.framework.utility;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * For accessing the Device information.
 * 
 * @author jupiter.at@gmail.com
 */
public class DeviceUtil {
    /**
     * get device udid information.
     * <p>
     * refer to this page:
     * http://android-coding.blogspot.jp/2011/06/get-unique-device
     * -id-imei-meid-or-esn.html
     * </p>
     * 
     * @param context
     * @return udid of device (IMEI or something else).
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (deviceId == null || deviceId.equals("0000000000000")) {
            // get from xml first.
            deviceId = getDeviceIdFromXml(context);
            if (deviceId != null && !deviceId.equals("")) {
                return deviceId;
            }
            // android ID - unreliable
            String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            String serial = null;

            try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String)get.invoke(c, "ro.serialno");
            } catch (Exception ignored) {
            }
            if (serial == null || serial.equals("")) {
                serial = UUID.randomUUID().toString();
            }
            String longId = androidId + serial;
            deviceId = encodeMD5(longId);
            saveDeviceId(context, deviceId);
        }
        return deviceId;
    }

    /**
     * this method will generate the special id with WIFI MAC Address and
     * Bluetooth MAC Address.
     * 
     * @param context
     * @return
     */
    public static String getDeviceIDWithMACAddress(Context context, String saltCode) {
        if (saltCode == null) {
            saltCode = "_JonnyKenAndRuby_";
        }
        TelephonyManager telephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        try {
            // DEVICE_ID // Requires READ_PHONE_STATE
            if (deviceId == null) {
                deviceId = "";
            }
            // ANDROID_ID
            String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            if (androidId == null) {
                androidId = "";
            }
            // WIFI MAC ADDRESS: android.permission.ACCESS_WIFI_STATE
            WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            String wifiMAC = wm.getConnectionInfo().getMacAddress();

            // Bluetooth MAC address android.permission.BLUETOOTH required
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            String bluetoothMAC = bluetoothAdapter.getAddress();

            // sum
            deviceId = deviceId + saltCode + androidId + saltCode + wifiMAC + saltCode
                    + bluetoothMAC;
            deviceId = encodeMD5(deviceId);
        } catch (Exception e) {
        }
        return deviceId;
    }
    
    /**
	 * help us convert to MD5 hash value.
	 * @param text
	 * @return
	 */
	public static String encodeMD5(String text) {
    	MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(text.getBytes(), 0, text.length());
			byte p_md5Data[] = m.digest();
			
			String m_szUniqueID = new String();
			for (int i=0;i<p_md5Data.length;i++) {
				int b =  (0xFF & p_md5Data[i]);
				// if it is a single digit, make sure it have 0 in front (proper padding)
				if (b <= 0xF) m_szUniqueID+="0";
				// add number to string
				m_szUniqueID += Integer.toHexString(b); 
			}
			m_szUniqueID = m_szUniqueID.toUpperCase(Locale.getDefault());
			return m_szUniqueID;
		} catch (Exception e) {
			Log.printStackTrace(e);
			try {
				return UUID.nameUUIDFromBytes(text.getBytes("utf8")).toString();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		} 
		return text;
	}

    /**
     * save device Id to xml file.
     * 
     * @param context
     * @param deviceId
     */
    private static void saveDeviceId(Context context, String deviceId) {
        SharedPreferences share = context.getSharedPreferences("device_id.xml",
                Context.MODE_PRIVATE);
        share.edit().putString("device_id", deviceId).commit();
    }

    /**
     * get device Id from xml file.
     * 
     * @param context
     * @return
     */
    private static String getDeviceIdFromXml(Context context) {
        SharedPreferences share = context.getSharedPreferences("device_id.xml",
                Context.MODE_PRIVATE);
        return share.getString("device_id", null);
    }

    /**
     * get screen width
     * 
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int screenW = outMetrics.widthPixels;

        return screenW;
    }

    /**
     * get screen height
     * 
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int screenH = outMetrics.heightPixels;

        return screenH;
    }

    /**
     * convert dp values to px
     * 
     * @param context
     * @param dp
     * @return
     */
    public static int convertDp2Px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);
    }

    /**
     * convert px values to dp
     * 
     * @param context
     * @param px
     * @return
     */
    public static int convertPx2Dp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)((px / displayMetrics.density) + 0.5);
    }

    /**
     * show device information.
     * 
     * @param tagOfLogCat this is for export to logcat.
     * @param context
     */
    public static void showDeviceInfo(String tagOfLogCat, Context context) {
        if (tagOfLogCat == null || tagOfLogCat.equals("")) {
            tagOfLogCat = "IVC";
        }
        Log.i(tagOfLogCat, "===Start show device information===");
        // resolution.
        int width = DeviceUtil.getScreenWidth(context);
        int height = DeviceUtil.getScreenHeight(context);
        Log.i(tagOfLogCat, "screenW=" + width);
        Log.i(tagOfLogCat, "screenH=" + height);
        // check density.
        Log.i(tagOfLogCat, "density=" + context.getResources().getDisplayMetrics().density);
        Log.i(tagOfLogCat, "scaledDensity="
                + context.getResources().getDisplayMetrics().scaledDensity);
        Log.i(tagOfLogCat, "screenLayoutType=" + getScreenLayoutTypeText(context));

        Log.i(tagOfLogCat, "densityDpi=" + getDensityDpi(context));
        Log.i(tagOfLogCat, "densityType=" + getDensityDpiText(context));
        // check device.
        Log.i(tagOfLogCat, "MANUFACTURER=" + Build.MANUFACTURER);
        Log.i(tagOfLogCat, "DEVICE=" + Build.DEVICE);
        Log.i(tagOfLogCat, "MODEL=" + Build.MODEL);
        Log.i(tagOfLogCat, "PRODUCT=" + Build.PRODUCT);
        Log.i(tagOfLogCat, "DISPLAY=" + Build.DISPLAY);
        Log.i(tagOfLogCat, "BRAND=" + Build.BRAND);
        Log.i(tagOfLogCat, "CPU_ABI=" + Build.CPU_ABI);
        Log.i(tagOfLogCat, "BOARD=" + Build.BOARD);
        Log.i(tagOfLogCat, "SDK INT=" + Build.VERSION.SDK_INT);
        Log.i(tagOfLogCat, "SDK RELEASE=" + Build.VERSION.RELEASE);
        Log.i(tagOfLogCat, "SDK CODENAME=" + Build.VERSION.CODENAME);
        Log.i(tagOfLogCat, "SDK INCREMENTAL=" + Build.VERSION.INCREMENTAL);
        Log.i(tagOfLogCat, "===End show device information===");
    }

    public static int getDensityDpi(Context context) {
    	return context.getResources().getDisplayMetrics().densityDpi;
    }
    
    public static String getDensityDpiText(Context context) {
    	int density = getDensityDpi(context);
    	String text = "DENSITY_MEDIUM";
    	switch (density) {
		case DisplayMetrics.DENSITY_HIGH:
			text = "DisplayMetrics.DENSITY_HIGH";
			break;
		case DisplayMetrics.DENSITY_LOW:
			text = "DisplayMetrics.DENSITY_LOW";
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			text = "DisplayMetrics.DENSITY_MEDIUM";
			break;
		case DisplayMetrics.DENSITY_TV:
			text = "DisplayMetrics.DENSITY_TV";
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			text = "DisplayMetrics.DENSITY_XHIGH";
			break;
		case DisplayMetrics.DENSITY_XXHIGH:
			text = "DisplayMetrics.DENSITY_XXHIGH";
			break;
		default:
			break;
		}
    	return text;
    }
    /**
     * Returns the language code for this Locale or the empty string if no
     * language was set
     * 
     * @param context
     * @return
     */
    public static String getLanguageCode() {
        return Locale.getDefault().getLanguage().toLowerCase(Locale.getDefault());
    }

    /**
     * get screen size type
     * @param context
     * @return
     */
    public static int getScreenLayoutType(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);
    }

    /**
     * get screen size as text.
     * @param context
     * @return
     */
    public static String getScreenLayoutTypeText(Context context) {
        int screenType = getScreenLayoutType(context);
        String screenTypeS = "SCREENLAYOUT_SIZE_UNDEFINED";
        switch (screenType) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                screenTypeS = "SCREENLAYOUT_SIZE_LARGE";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                screenTypeS = "SCREENLAYOUT_SIZE_NORMAL";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                screenTypeS = "SCREENLAYOUT_SIZE_SMALL";
                break;
            case 4:
                screenTypeS = "SCREENLAYOUT_SIZE_XLARGE";
                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:
                screenTypeS = "SCREENLAYOUT_SIZE_UNDEFINED";
                break;
            default:
                break;
        }
        return screenTypeS;
    }

    /**
     * check this device is tablet or not.
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return isXLarge(context) | isLarge(context);
    }

    /**
     * check device is large screen
     * @param context
     * @return
     */
    public static boolean isLarge(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * check device is Xlarge screen.
     * @param context
     * @return
     */
    public static boolean isXLarge(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4; // 4
                                                                                                                     // is
                                                                                                                     // xlarge
    }

    /**
     * check support honey com (sdk_int >= 11) tablet
     * @return
     */
    public static boolean isSupportedHoneycombTablet(Context context) {
        return hasHoneycomb() && isTablet(context);
    }

    /**
     * check support froyo
     * @return
     */
    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed
        // behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * check support honey com (sdk_int >= 11)
     * @return
     */
    public static boolean hasHoneycomb() {
        // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
        return Build.VERSION.SDK_INT >= 11;
    }

    /**
     * check support honey com (sdk_int >= 12)
     * @return
     */
    public static boolean hasHoneycombMR1() {
        // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
        return Build.VERSION.SDK_INT >= 12;
    }

    /**
     * check support Jelly bean (sdk_int >= 16)
     * @return
     */
    public static boolean hasJellyBean() {
        // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
        return Build.VERSION.SDK_INT >= 16;
    }
}
