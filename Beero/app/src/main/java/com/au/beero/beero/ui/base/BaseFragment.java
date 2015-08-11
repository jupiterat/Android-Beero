/**
 * jupiter.at@gmail.com
 */
package com.au.beero.beero.ui.base;

import android.app.Activity;
import android.support.v4.app.Fragment;


/**
 * base fragment for all fragments in this application
 */
public class BaseFragment extends Fragment {
    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void showToast(String msg) {
        if (mActivity != null) {
            ((BaseActivity) mActivity).showToast(msg);
        }
    }

    protected void showDialog(String msg) {
        if (mActivity != null) {
            ((BaseActivity) mActivity).showDialog(msg);
        }
    }

    protected void showDialog(String msg, String cancelText) {
        if (mActivity != null) {
            ((BaseActivity) mActivity).showDialog(msg, cancelText);
        }
    }

    protected void showProgress() {
        if (mActivity != null) {
            ((BaseFragmentActivity) mActivity).showProgress();
        }
    }

    protected void showProgress(int resId) {
        if (mActivity != null) {
            ((BaseFragmentActivity) mActivity).showProgress(resId);
        }
    }

    protected void dismissProgress() {
        if (mActivity != null) {
            ((BaseFragmentActivity) mActivity).dismissProgress();
        }
    }

}
