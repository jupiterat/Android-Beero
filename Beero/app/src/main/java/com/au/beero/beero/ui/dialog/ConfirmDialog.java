/**
 * Copyright (C) 2014 Orgit - All Rights Reserved
 */
package com.au.beero.beero.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.au.beero.beero.R;

public class ConfirmDialog extends CustomDialog implements View.OnClickListener {

    private TextView mCancelTxt;
    private TextView mOKTxt;
    private TextView mTitleTxt;
    private View.OnClickListener listener;
    private View.OnClickListener cancelListener;
    private String mOkTitle = "Delete";
    private String mDialogTitle = "";
    private boolean hideCancelButton = false;

    // default value, should be skipped with this value
    private float mTitleSize = 0;
    private CharSequence mSpanedText;

    /**
     * @param context
     * @param title:      title of positive button
     * @param okListener: positive button click listener
     */
    public ConfirmDialog(Context context, String title, String okTitle, View.OnClickListener okListener) {
        super(context);
        Window w = this.getWindow();
        w.setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);
//        mContext = context;
        listener = okListener;
        if (!okTitle.isEmpty()) {
            mOkTitle = okTitle;
        }
        mDialogTitle = title;
    }

    public ConfirmDialog(Context context, CharSequence mSpanedText, String okTitle, View.OnClickListener okListener) {
        super(context);
        Window w = this.getWindow();
        w.setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);
        mContext = context;
        listener = okListener;
        if (!okTitle.isEmpty()) {
            mOkTitle = okTitle;
        }
        mDialogTitle = null;
        this.mSpanedText = mSpanedText;
    }

    public ConfirmDialog(Context context, String title, String okTitle, View.OnClickListener okListener, View.OnClickListener cancelListener) {
        super(context);
        Window w = this.getWindow();
        w.setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);
        mContext = context;
        listener = okListener;
        if (!okTitle.isEmpty()) {
            mOkTitle = okTitle;
        }
        this.cancelListener = cancelListener;
        mDialogTitle = title;
    }

    @Override
    @SuppressLint("InflateParams")
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.confirm_dialog_layout, null);
        mCancelTxt = (TextView) v.findViewById(R.id.cancel);
        mOKTxt = (TextView) v.findViewById(R.id.ok);
        mTitleTxt = (TextView) v.findViewById(R.id.title);
        mCancelTxt.setEnabled(true);
        if (cancelListener != null) {
            mCancelTxt.setOnClickListener(cancelListener);
        } else {
            if (hideCancelButton) {
                mCancelTxt.setVisibility(View.GONE);
                LayoutParams theLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                mOKTxt.setLayoutParams(theLayoutParam);
            } else {
                mCancelTxt.setOnClickListener(this);
            }
        }
        mOKTxt.setEnabled(true);
        mOKTxt.setText(mOkTitle);
        if (listener != null) {
            mOKTxt.setOnClickListener(listener);
        }
//		SpannableString spannablecontent=new SpannableString(mDialogTitle);
//		spannablecontent.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
//				0, spannablecontent.length(), 0);
        setTitle();
//		mTitleTxt.setText(mDialogTitle);
        setTitleSize(mTitleSize);
        setContentView(v);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                this.dismiss();
                break;

            default:
                break;
        }
    }

    /**
     * set title dialog
     *
     * @param str
     */
    private void setTitle(String str) {
        if (mTitleTxt != null) {
            mTitleTxt.setText(str);
        }
    }

    private void setTitle() {
        if (mTitleTxt != null) {
            if (mDialogTitle != null && !mDialogTitle.isEmpty()) {
                mTitleTxt.setText(mDialogTitle);
            } else {
                if (mSpanedText != null && mSpanedText.length() > 0) {
//                    String abc  = "<b>Bolded text</b>, <i>italic text</i>, even <u>underlined</u>!";
                    mTitleTxt.setText(mSpanedText);
                }
            }

        }
    }

    /**
     * set size of title dialog
     *
     * @param size
     */
    private void setTitleSize(float size) {
        if (mTitleTxt != null && size != 0) {
            mTitleTxt.setTextSize(size);
        }
    }

    public void hideCancelButton(boolean isHidden) {
        hideCancelButton = isHidden;
        if (mCancelTxt != null) {
            mCancelTxt.setVisibility(View.GONE);
        }
    }

    public void setDialogMessageSize(float size) {
        mTitleSize = size;
    }
}
