package com.framework.utility;

import android.os.Debug;
import android.os.Debug.MemoryInfo;


/**
 * Custom log util to control when we show or not logcat.
 * @author jupiter.at@gmail.com
 *
 */
public class Log {
	public static boolean ENABLE_LOG = true;
	private static final String TAG = "IVC_LOG";
	// TODO When rerease, you have to set to 'false'.
	public static boolean LOG_V = ENABLE_LOG;
	/**
	 * flag true: show debug log. false: don't show debug log.
	 */
	public static boolean LOG_DEBUG = ENABLE_LOG;
	/**
	 * flag true: show info log. false: don't show info log.
	 */
	public static boolean LOG_INFO = ENABLE_LOG;
	/**
	 * flag true: show warning log. false: don't show warning log.
	 */
	public static boolean LOG_WARNING = false;
	/**
	 * flag true: show error log. false: don't show error log.
	 */
	public static boolean LOG_ERROR = true;

	/**
	 * Send a DEBUG log message.
	 * @param TAG TAG name
	 * @param message message content.
	 * @param e 
	 */
	public static void d(String TAG, String message, Exception e) {
		if (!LOG_DEBUG) {
			return;
		}
		if (message == null) {
			return;
		}
		android.util.Log.d(TAG, "" + message, e);
	}
	/**
	 * Send a DEBUG log message.
	 * @param TAG TAG name
	 * @param message message content.
	 */
	public static void d(String TAG, String message) {
		if (!LOG_DEBUG) {
			return;
		}
		if (message == null) {
			return;
		}
		android.util.Log.d(TAG, "" + message);
	}
	/**
	 * Send a WARNING log message.
	 * @param TAG TAG name
	 * @param message message content.
	 */
	public static void w(String TAG, String message) {
		if (!LOG_WARNING) {
			return;
		}
		if (message == null) {
			return;
		}
		android.util.Log.w(TAG, "" + message);
	}
	/**
	 * Send a ERROR log message.
	 * @param TAG TAG name
	 * @param message message content.
	 */
	public static void e(String TAG, String message) {
		if (!LOG_ERROR) {
			return;
		}
		if (message == null) {
			return;
		}
		android.util.Log.e(TAG, "" + message);
	}
	/**
	 * Send a INFO log message.
	 * @param TAG TAG name
	 * @param message message content.
	 */
	public static void i(String TAG, String message) {
		if (!LOG_INFO) {
			return;
		}
		if (message == null) {
			return;
		}
		android.util.Log.i(TAG, "" + message);
	}
	/**
	 * send a V log message
	 * @param tag
	 * @param string
	 */
	public static void v(String tag, String message) {
		if (!LOG_V) {
			return;
		}
		if (message == null) {
			return;
		}
		android.util.Log.v(tag, "" + message);
	}
	/**
	 * Show log error with exception
	 * @param tag
	 * @param string
	 * @param e
	 */
	public static void e(String tag, String message, Exception e) {
		if (!LOG_ERROR) {
			return;
		}
		if (message == null) {
			return;
		}
		android.util.Log.e(tag, "" + message, e);
	}
	/**
	 * enable logcat or not
	 * @param enabled
	 */
	public static void enableLogcat(boolean enabled) {
		LOG_DEBUG = enabled;
		LOG_ERROR = enabled;
		LOG_INFO = enabled;
		LOG_V = enabled;
		LOG_WARNING = enabled;
	}
	/**
	 * Do print stack trace of exception to logcat
	 * @param e
	 */
	public static void printStackTrace(String TAG, Exception e) {
		if (e == null) {
			return;
		}
		if (!LOG_DEBUG) {
			if (e.getMessage() != null && !e.getMessage().equals("")) {
				Log.e(TAG, e.getMessage());
			}
			return;
		}
		e.printStackTrace();
	}
	/**
	 * Do print stack trace of exception to logcat
	 * @param e
	 */
	public static void printStackTrace(Exception e) {
		printStackTrace(TAG, e);
	}
	/**
	 * Do print stack trace of exception to logcat
	 * @param e
	 */
	public static void printStackTrace(OutOfMemoryError e) {
		printStackTrace(TAG, e);
	}
	/**
	 * Do print stack trace of exception to logcat
	 * @param e
	 */
	public static void printStackTrace(String TAG, OutOfMemoryError e) {
		if (e == null) {
			return;
		}
		if (!LOG_DEBUG) {
			if (e.getMessage() != null && !e.getMessage().equals("")) {
				Log.e(TAG, e.getMessage());
			}
			return;
		}
		e.printStackTrace();
	}
	/**
	 * show runtime memory.
	 */
	public static void debugRuntimeMemory() {
		Log.d(TAG, "debugRuntimMemory memory information----------------------->START-1");
		Log.d(TAG, "Runtime.getRuntime().availableProcessors()=" + Runtime.getRuntime().availableProcessors());
		Log.d(TAG, "Runtime.getRuntime().maxMemory()=" 
				+ Runtime.getRuntime().maxMemory() + "Bytes=>"
				+ (Runtime.getRuntime().maxMemory() / 1024f) + "KB=>"
				+ (Runtime.getRuntime().maxMemory() / (1024f * 1024f)) + "MB");
        Log.d(TAG, "Runtime.getRuntime().totalMemory()=" 
		        + Runtime.getRuntime().totalMemory() + "Bytes=>"
				+ (Runtime.getRuntime().totalMemory() / 1024f) + "KB=>"
				+ (Runtime.getRuntime().totalMemory() / (1024f * 1024f)) + "MB");
        Log.d(TAG, "Runtime.getRuntime().freeMemory()=" 
		        + Runtime.getRuntime().freeMemory() + "Bytes=>"
				+ (Runtime.getRuntime().freeMemory() / 1024f) + "KB=>"
				+ (Runtime.getRuntime().freeMemory() / (1024f * 1024f)) + "MB");
        Log.d(TAG, "debugRuntimMemory memory information<-----------------------END-1");
	}
	/**
	 * this method is used to debug memory in using
	 */
	@SuppressWarnings("deprecation")
	public static void debugDavlkMemory() {
		Log.d(TAG, "debugDavlkMemory memory information----------------------->START-1");
		Log.d(TAG, "getGlobalAllocCount---->" + Debug.getGlobalAllocCount());
		Log.d(TAG, "getGlobalAllocSize---->" + Debug.getGlobalAllocSize());
		Log.d(TAG, "getGlobalExternalAllocCount---->" + Debug.getGlobalExternalAllocCount());
		Log.d(TAG, "getGlobalExternalAllocSize---->" + Debug.getGlobalExternalAllocSize());
		Log.d(TAG, "getGlobalExternalFreedCount---->" + Debug.getGlobalExternalFreedCount());
		Log.d(TAG, "getGlobalExternalFreedSize---->" + Debug.getGlobalExternalFreedSize());
		Log.d(TAG, "getGlobalFreedCount---->" + Debug.getGlobalFreedCount());
		Log.d(TAG, "getGlobalFreedSize---->" + Debug.getGlobalFreedSize());
		Log.d(TAG, "getGlobalGcInvocationCount---->" + Debug.getGlobalGcInvocationCount());
		Log.d(TAG, "getLoadedClassCount---->" + Debug.getLoadedClassCount());
		Log.d(TAG, "getNativeHeapAllocatedSize---->" + Debug.getNativeHeapAllocatedSize());
		Log.d(TAG, "getNativeHeapFreeSize---->" + Debug.getNativeHeapFreeSize());
		Log.d(TAG, "getNativeHeapSize---->" + Debug.getNativeHeapSize());
		Log.d(TAG, "getThreadAllocCount---->" + Debug.getThreadAllocCount());
		Log.d(TAG, "getThreadAllocSize---->" + Debug.getThreadAllocSize());
		Log.d(TAG, "getThreadExternalAllocCount---->" + Debug.getThreadExternalAllocCount());
		Log.d(TAG, "getThreadExternalAllocSize---->" + Debug.getThreadExternalAllocSize());
		Log.d(TAG, "getThreadGcInvocationCount---->" + Debug.getThreadGcInvocationCount());
		Log.d(TAG, "debugDavlkMemory memory information----------------------->START-2");
		MemoryInfo memoryInfo = new MemoryInfo();
		Debug.getMemoryInfo(memoryInfo);
		Log.d(TAG, "memoryInfo.dalvikPrivateDirty---->" + memoryInfo.dalvikPrivateDirty);
		Log.d(TAG, "memoryInfo.dalvikPss---->" + memoryInfo.dalvikPss);
		Log.d(TAG, "memoryInfo.dalvikSharedDirty---->" + memoryInfo.dalvikSharedDirty);
		Log.d(TAG, "memoryInfo.nativePrivateDirty---->" + memoryInfo.nativePrivateDirty);
		Log.d(TAG, "memoryInfo.nativePss---->" + memoryInfo.nativePss);
		Log.d(TAG, "memoryInfo.nativeSharedDirty---->" + memoryInfo.nativeSharedDirty);
		Log.d(TAG, "memoryInfo.otherPrivateDirty---->" + memoryInfo.otherPrivateDirty);
		Log.d(TAG, "memoryInfo.otherPss---->" + memoryInfo.otherPss);
		Log.d(TAG, "memoryInfo.otherSharedDirty---->" + memoryInfo.otherSharedDirty);
		Log.d(TAG, "memoryInfo.getTotalPrivateDirty---->" + memoryInfo.getTotalPrivateDirty());
		Log.d(TAG, "memoryInfo.getTotalPss---->" + memoryInfo.getTotalPss());
		Log.d(TAG, "memoryInfo.getTotalSharedDirty---->" + memoryInfo.getTotalSharedDirty());		
		Log.d(TAG, "debugDavlkMemory memory information----------------------->END");
	}
}
