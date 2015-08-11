/**
 * jupiter.at@gmail.com
 */
package com.au.beero.beero.ui.base;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.au.beero.beero.R;
import com.au.beero.beero.ui.stack.StackFragment;


/**
 * this is base activity for others extends. It contains common function for
 * all.
 */
public class BaseActivity extends FragmentActivity {
	private ImageView mBackIcon;
	private TextView mTitle;
	private String mTitleStr = "";
	private int mLogoId;
	protected Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		displayHomeIcon(false);
	}
	

	/**
	 * handle action bar home, up button
	 * 
	 * @param value
	 */
	public void displayHomeIcon(boolean value) {
		ActionBar bar = getActionBar();
		if (bar != null) {
			getActionBar().setDisplayUseLogoEnabled(false);// don't use app icon
			getActionBar().setDisplayShowHomeEnabled(true);// show Home icon or not
			getActionBar().setIcon(android.R.color.transparent);
			getActionBar().setDisplayHomeAsUpEnabled(value);// hide up button
			getActionBar().setHomeButtonEnabled(true);
		}
	}
	public void displayBackIcon(boolean value, final StackFragment stack, int resId, String sub, final boolean is_photo) {
		this.mLogoId = resId;
		displayBackIcon(value, stack, "", sub, is_photo);
	}

	/**
	 * 
	 * @param value
	 * @param stack
	 * @param title
	 * @param sub
	 */
	public void displayBackIcon(boolean value, final StackFragment stack, String title, String sub,
			final boolean is_photo) {
		getActionBar().setDisplayShowCustomEnabled(value);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.action_bar_layout_detail);
		View actionBarView = getActionBar().getCustomView();
		mTitle = (TextView) actionBarView.findViewById(R.id.title_detail);
		final RelativeLayout rlBack = (RelativeLayout) actionBarView.findViewById(R.id.rl_back);
		TextView tvSubTitle = (TextView) actionBarView.findViewById(R.id.sub_title);
		mBackIcon = (ImageView) actionBarView.findViewById(R.id.back_button);
		TextView mSelectedCounter = (TextView) actionBarView.findViewById(R.id.selected_counter);
		if (stack != null) {
			stack.setOnStackChangeListener(new StackFragment.onStackChangedListener() {

				@Override
				public void onChange(int level) {
					handleBackIcon(stack, is_photo);
				}
			});
		}
		handleBackIcon(stack, is_photo);
		if (title.equals("")) {
			if (mLogoId == 0){
				mTitle.setVisibility(View.GONE);
				mBackIcon.setVisibility(View.GONE);
			} else {
				mTitle.setVisibility(View.VISIBLE);
				mTitle.setText(title);
				mTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, mLogoId, 0);
				mLogoId = 0;
			}
		} else {
			mTitle.setVisibility(View.VISIBLE);
			mTitle.setText(title);
		}
		mTitleStr = title;
		mTitle.setText(title);
		if (sub.equals(""))
			tvSubTitle.setVisibility(View.GONE);
		else {
			tvSubTitle.setVisibility(View.VISIBLE);
			tvSubTitle.setText(sub);
		}

		rlBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (stack != null && stack instanceof StackFragment) {
//					rlBack.requestFocus();
//					Utils.hideInputKeyboard(getApplicationContext(), getCurrentFocus());
					stack.backToPrevious();
				} else {
					finish();
				}
			}
		});
	}

	public void disableBackIcon() {
		getActionBar().setDisplayShowCustomEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
	}

	/**
	 * change title param
	 * 
	 * @param is_photo
	 */
	private void changeTitleParam(StackFragment stack, boolean is_photo) {
		LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		if (is_photo || stack == null) {
			param.leftMargin = getResources().getDimensionPixelSize(R.dimen.com_15dp);
		} else {
			param.leftMargin = 0;
		}
		mTitle.setLayoutParams(param);
	}

	/**
	 * show/hide back icon base on stack level
	 * 
	 * @param stack
	 */
	private void handleBackIcon(StackFragment stack, boolean is_photo) {
		if (stack != null) {
			if (stack.getStackLevel() == 1) {
				mBackIcon.setVisibility(View.GONE);
				changeTitleParam(stack, is_photo);
			} else {
				mBackIcon.setVisibility(View.VISIBLE);
			}
		} else {
//			mBackIcon.setVisibility(View.GONE);
//			changeTitleParam(stack, is_photo);
		}
	}
	/**
	 * get title
	 * 
	 * @return
	 */
	public String getTitleStr() {
		return mTitleStr;
	}

	public void showDialog(String message) {
		new AlertDialog.Builder(this).setMessage(message)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				}).show();
	}
	
	public void showDialog(String message, String cancelText) {
		new AlertDialog.Builder(this).setMessage(message)
				.setPositiveButton(cancelText, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				}).show();
	}
	
	public void showToast(String msg) {
		if (null != msg && !msg.isEmpty()) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}
}
