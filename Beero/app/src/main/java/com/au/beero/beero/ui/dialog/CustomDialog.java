/**
 * Copyright (C) 2014 Orgit - All Rights Reserved
 */
package com.au.beero.beero.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

import com.au.beero.beero.utility.Utility;

/**
 * This class is base for all dialogs
 */
public abstract class CustomDialog extends Dialog {
	protected LayoutInflater mInflater;
	protected Context mContext;

	public CustomDialog(Context context) {
		super(context);
		mContext = context;
		setCanceledOnTouchOutside(true);

		mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public CustomDialog(Context context, int theme) {
		super(context, theme);
        mContext = context;
		setCanceledOnTouchOutside(true);
		mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	protected void onStart() {
		super.onStart();
		setupDialogSize();
		setupAnimation();
	}

	/** setup dialog size **/
	protected void setupDialogSize() {
		int screenW = Utility.getScreenWidth(getContext());
		int screenH = Utility.getScreenHeight(getContext());
		int width = screenW < screenH ? screenW : screenH;
        int height = screenW < screenH ? (int)(screenH * 0.8) : WindowManager.LayoutParams.WRAP_CONTENT;
		width *= 0.9;
//        height *= 0.95;
		if (Utility.isLarge(getContext())) {
			width *= 0.8;
//            height *= 0.8;
		} else if (Utility.isXLarge(getContext())) {
			width *= 0.75;
//            height *= 0.75;
		} else if(Utility.isNormal(getContext())) {
//            height *= 0.95;
        }
		setDialogWidth(width);
	}

	protected void setupAnimation() {

	}

	/** notify to know activity is resumed **/
	public void onResumeActivity() {
	}
	/**
	 * Callback when activity is paused.
	 */
	public void onPauseActivity() {
	}
	/**
	 * Callback when activity is destroyed.
	 */
	public void onDestroyActivity() {
	}
	/**
	 * catch configuration changed.
	 *
	 * @param newConfig
	 */
	public void onConfigurationChanged(Configuration newConfig) {
		updateOrientation(newConfig.orientation);
	}
	/**
	 * when orientation changes, update layout if have changes..
	 * 
	 * @param orientation
	 */
	public void updateOrientation(int orientation) {
	}
	/**
	 * setup dialog width
	 * 
	 * @param width
	 */
	protected void setDialogWidth(int width) {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = width;
		getWindow().setAttributes(params);
	}
	/**
	 * setup dialog height
	 * 
	 */
	protected void setDialogHeight(int height) {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = height;
		getWindow().setAttributes(params);
	}
	protected void showToast(String msg) {
		if (null != msg) {
			Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
		}
	}
}
