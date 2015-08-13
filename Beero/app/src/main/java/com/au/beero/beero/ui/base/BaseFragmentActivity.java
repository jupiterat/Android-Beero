/**
 * jupiter.at@gmail.com
 */
package com.au.beero.beero.ui.base;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.au.beero.beero.R;
import com.au.beero.beero.ui.stack.StackFragment;
import com.au.beero.beero.utility.Utility;

import java.lang.reflect.Field;

/**
 * This is a base class for activity contains Fragment transition
 */
public class BaseFragmentActivity extends BaseActivity {
    private static String TAG = BaseFragmentActivity.class.getSimpleName();
    private static String FRAGMENT_TAG = "fragment_tag";
    public FragmentManager fragmentManager;
    private SharedPreferences mSharedpreferences;
    private Editor mEditor;
    protected ProgressDialog mProgressDialog;
    /**
     * the flag to know that this activity has been destroyed.
     */
    protected boolean mIsDestroy = false;

    private ImageView mBackIcon;
    private TextView mTitle;
    private String mTitleStr = "";
    private boolean isAlwayDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "[BaseFragmentActivity] [onCreate]:");
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainView = inflater.inflate(R.layout.main_layout, null);
        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .addOnBackStackChangedListener(new OnBackStackChangedListener() {

                    @Override
                    public void onBackStackChanged() {
                        if (getSupportFragmentManager()
                                .getBackStackEntryCount() <= 0) {
                            finish();
                        }
                    }
                });
        setContentView(mainView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "[BaseFragmentActivity] [onStart]:");
        // Check network status run-time
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "[BaseFragmentActivity] [onStop]:");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "[BaseFragmentActivity] [onRestart]:");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "[BaseFragmentActivity] [onPause]:");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "[BaseFragmentActivity] [onDestroy]");
        mIsDestroy = true;
        if (mProgressDialog != null) {
            dismissProgress();
            mProgressDialog = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                    FRAGMENT_TAG);
            if (fragment != null && fragment instanceof StackFragment) {
                StackFragment stackFragment = (StackFragment) fragment;
                if (stackFragment.getStackCount() == 0) {
                    finish();
                } else if (stackFragment.backToPrevious()) {
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param value
     * @param stack
     * @param title
     * @param sub
     */
    public void displayBackIcon(boolean value, final StackFragment stack, String title, String sub, final boolean is_photo) {
        getActionBar().setDisplayShowCustomEnabled(value);
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(
                        android.R.color.transparent)));
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.action_bar_layout_detail);
        View actionBarView = getActionBar().getCustomView();
        mTitle = (TextView) actionBarView.findViewById(R.id.title_detail);
        RelativeLayout rlBack = (RelativeLayout) actionBarView.findViewById(R.id.rl_back);
        TextView tvSubTitle = (TextView) actionBarView.findViewById(R.id.sub_title);
        mBackIcon = (ImageView) actionBarView.findViewById(R.id.back_button);
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
            mTitle.setVisibility(View.GONE);
            mBackIcon.setVisibility(View.GONE);
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
                goBackPreviousScreen(stack);
            }
        });
    }

    public void disableBackIcon() {
        getActionBar().setDisplayShowCustomEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);
    }

    /**
     * show/hide back icon base on stack level
     *
     * @param stack
     */
    private void handleBackIcon(StackFragment stack, boolean is_photo) {
        if (stack != null) {
            if (stack.getStackLevel() == 1 && !setAlwaysDisplayed()) {
                mBackIcon.setVisibility(View.GONE);
                changeTitleParam(stack, is_photo);
            } else {
                mBackIcon.setVisibility(View.VISIBLE);
            }
        } else {
            mBackIcon.setVisibility(View.GONE);
            changeTitleParam(stack, is_photo);
        }
    }

    private void goBackPreviousScreen(final StackFragment stack) {
        if (stack != null && stack instanceof StackFragment) {
            Utility.hideInputKeyboard(getApplicationContext(), getCurrentFocus());
            if (stack.getStackLevel() == 1 && setAlwaysDisplayed()) {
                finish();
            } else {
                stack.backToPrevious();
            }
        } else {
            finish();
        }
    }

    /**
     * always show title or not
     */
    protected boolean setAlwaysDisplayed() {
        return isAlwayDisplayed;
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
     * add Fragment to stack
     *
     * @param newFragment
     */
    public void addFragmentToStack(Fragment newFragment) {

        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        fragmentManager
                .addOnBackStackChangedListener(new OnBackStackChangedListener() {

                    @Override
                    public void onBackStackChanged() {

                    }
                });
        FragmentTransaction ft = fragmentManager.beginTransaction();
        setupTransitionAnimation(ft);
        ft.replace(R.id.stack_fragment_container, newFragment, FRAGMENT_TAG);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * move to first fragment in stack
     */
    public void backToHomeFragment() {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount - 1; i++) {
            fragmentManager.popBackStack();
        }
    }

    /**
     * do back to previous screen.
     *
     * @return
     */
    public boolean backToPrevious() {
        // FragmentManager fm = getChildFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    public Fragment getCurrentStackFragment() {
        return fragmentManager.findFragmentById(R.id.stack_fragment_container);
    }

    public void popTheFirst() {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        if (backStackCount > 0) {
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0)
                    .getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void popAll() {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        if (backStackCount > 0) {
            fragmentManager.popBackStack(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * always show Overflow menu on action bar
     */
    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * setup animation of transition
     *
     * @param ft
     */
    protected void setupTransitionAnimation(FragmentTransaction ft) {
    }

    /**
     * get main preference
     *
     * @return
     */
    public SharedPreferences getMainPreference() {
        return mSharedpreferences;
    }

    /**
     * get main editor
     *
     * @return
     */
    public Editor getEditor() {
        return mEditor;
    }

    /**
     * return main data manager
     *
     * @return
     */
    // public MainManager getMainManager() {
    // return MainManager.getInstance(this);
    // }

    // public String getBaseFolder() {
    // return Environment.getExternalStorageDirectory() + File.separator +
    // getString(R.string.app_folder)
    // + File.separator;
    // }

    /**
     *
     * @param selectedPath
     *            : source file path
     * @param destPath
     *            : destination path
     * @param thumbPath
     *            : thumbnail path
     * @param type
     *            : file type
     */
    // public void writeFile(Context ctx, String selectedPath, String destPath,
    // String thumbPath, int type,
    // OnWriteFileSucess writeSuccessListener) {
    // FileInfo fileInfo = new FileInfo();
    // fileInfo.selectedPath = selectedPath;
    // fileInfo.destPath = destPath;
    // fileInfo.thumbPath = thumbPath;
    // fileInfo.type = type;
    // setOnWriteFileSuccess(writeSuccessListener);
    // new WritingFile(ctx).execute(fileInfo);
    // }

    // public interface RequestListener{
    // public void onInvalidToken();
    // }
    // public RequestListener getRequestListener() {
    // return requestListener;
    // }
    //
    // public void setRequestListener(RequestListener requestListener) {
    // this.requestListener = requestListener;
    // }

    /**
     * Show progress dialog to notify when the thread is running.
     */
    public void showProgress() {
        showProgress(0, null);
    }

    /**
     * Show progress dialog to notify when the thread is running.
     *
     * @param msgResId id of the message.
     */
    public void showProgress(int msgResId) {
        showProgress(msgResId, null);
    }

    public void showProgress(int msgResId,
                             DialogInterface.OnKeyListener keyListener) {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        if (msgResId != 0) {
            mProgressDialog.setMessage(getResources().getString(msgResId));
        } else {
            mProgressDialog.setMessage("Loading");
        }

        if (keyListener != null) {
            mProgressDialog.setOnKeyListener(keyListener);
        } else {
            mProgressDialog.setCancelable(false);
        }

        // check is showing confirm dialog or not.
        if (!mIsDestroy) {
            mProgressDialog.show();
        }
    }

    /**
     * Dismiss the progress dialog that are showing.
     */
    public void dismissProgress() {

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

    }
}
