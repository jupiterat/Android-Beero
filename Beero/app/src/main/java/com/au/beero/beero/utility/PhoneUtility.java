package com.au.beero.beero.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.View;

import com.au.beero.beero.R;
import com.au.beero.beero.ui.dialog.ConfirmDialog;

/**
 * Created by jupiter.at@gmail.com on 9/17/2015.
 */
public class PhoneUtility {
    private static Context mContext;
    private ConfirmDialog dialog;

    public static PhoneUtility makeInstance(Context ctx) {
        mContext = ctx;
        return new PhoneUtility();
    }

    public void call(String phone) {
        if (phone.isEmpty()) {
            dialog = new ConfirmDialog(mContext, mContext.getResources().getString(R.string.no_phone_available), "OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    return;
                }
            });
            dialog.hideCancelButton(true);
            dialog.show();
            return;
        }
        if (((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getPhoneType() == TelephonyManager.PHONE_TYPE_NONE
                || ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number() == null) {
            dialog = new ConfirmDialog(mContext, mContext.getResources().getString(R.string.no_phone_call_available), "OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    return;
                }
            });
            dialog.hideCancelButton(true);
            dialog.show();
        }

        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.replace(" ", "")));
        try {
            mContext.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            dialog = new ConfirmDialog(mContext, mContext.getResources().getString(R.string.no_phone_call_available), "OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    return;
                }
            });
            dialog.hideCancelButton(true);
            dialog.show();
        }
    }
}
