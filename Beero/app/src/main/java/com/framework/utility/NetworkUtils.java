package com.framework.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * @author "LiemVo"
 *
 */
public class NetworkUtils {
	/**
	 * Check network is connecting or not
	 */
	public static boolean isConnecting(Context con) {
		boolean isConnecting = false;
		if (con == null) {
			return false;
		}
		final ConnectivityManager cm = (ConnectivityManager) con
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			final NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				isConnecting = info.isConnectedOrConnecting();
			}
		}
		return isConnecting;
	}
}
