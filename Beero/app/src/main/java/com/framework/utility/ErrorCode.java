package com.framework.utility;

import android.content.Context;

import com.au.beero.beero.R;


public class ErrorCode {
    // -- Web service Error Code
    /**
     * NETWORK ERROR
     */
    public static final int E_NETWORK_ERROR = 101;
    /**
     * TIME_OUT
     */
    public static final int E_TIME_OUT = 102;
    /**
     * FILE_IO_ERROR
     */
    public static final int E_FILE_IO_ERROR = 104;
    /**
     * UNEXPECTED_ERROR
     */
    public static final int E_UNEXPECTED_ERROR = 999;

    public static final int SG_EGP_OK = 200;
    public static final int SC_SERVICE_UNAVAILABLE = 503;

    /**
     * Get error msg by result and error code
     */
    public static String getErrorMessage(Context context, int errorCode, String fileName) {
        String errorString = null;
        //If resultCode is error network or timeout or fileIo error
        String name = CommonMethod.getFileName(context, fileName);
        switch (errorCode) {
            case E_NETWORK_ERROR:
            case E_TIME_OUT:
            case E_UNEXPECTED_ERROR:
                errorString = context.getString(R.string.ERROR_NETWORK) + name;
                break;
            default:
                errorString = context.getString(R.string.ERROR_NETWORK) + name;
                break;
        }
        return errorString;

    }

}