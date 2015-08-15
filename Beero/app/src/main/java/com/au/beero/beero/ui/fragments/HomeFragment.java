package com.au.beero.beero.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.au.beero.beero.R;
import com.au.beero.beero.manager.BeeroLocationManager;
import com.au.beero.beero.model.Brand;
import com.au.beero.beero.model.response.ResponseBrand;
import com.au.beero.beero.request.BrandRequest;
import com.au.beero.beero.ui.activity.MainActivity;
import com.au.beero.beero.ui.base.BaseFragment;
import com.au.beero.beero.ui.dialog.ConfirmDialog;
import com.au.beero.beero.ui.stack.StackFragment;
import com.au.beero.beero.utility.ApiUtility;
import com.framework.network.request.AbstractHttpRequest;
import com.framework.network.task.IDataEventHandler;
import com.framework.utility.PauseHandler;

import java.util.List;

/**
 * Created by jupiter.at@gmail.com on 8/12/2015.
 */
public class HomeFragment extends BaseFragment {
    final static int BEGIN_TRANSACTION = 1;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private ImageView mLogoIcon;
    private RelativeLayout mSorryContaner;
    private ConfirmDialog mDialog;
    TransactionHanlder handler = new TransactionHanlder();

    public static final int REQUEST_CODE_OPEN_GPS_SETTINGS = 0x48;

    public static Fragment makeInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCurrentPosition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.splash_activity_layout, null);
        mLogoIcon = (ImageView) v.findViewById(R.id.imgLogo);
        mSorryContaner = (RelativeLayout) v.findViewById(R.id.sorry_container);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().hide();
        handler.setActivity(getActivity());
        handler.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.setActivity(null);
    }

    private void loadBrands() {
        ApiUtility.loadBrands(mActivity, new IDataEventHandler<ResponseBrand>() {
            @Override
            public void onNotifyData(ResponseBrand data, AbstractHttpRequest request) {
                if (!isAdded()) {
                    return;
                }
                List<Brand> brandList = null;
                /*if (data != null) {
                    brandList = data.getBrands();
                }*/
                handler.sendMessage(handler.obtainMessage(BEGIN_TRANSACTION, data));
//                gotoBrandScreen(brandList);
            }
        }, new BrandRequest());
    }

    private void getCurrentPosition() {
        //support position
        boolean isEnable = BeeroLocationManager.makeInstance().isEnableLocationServices();
        if (isEnable) {
            if (BeeroLocationManager.makeInstance().isSupportArea()) {
                loadBrands();
            } else {
                gotoNotSupported();
            }
        } else {
            mDialog = new ConfirmDialog(mActivity, getString(R.string.allow_location_services_waring), getString(R.string.allow), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToGpsSettingScreen();
                    mDialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    gotoNotSupported();
                }
            });
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    private void gotoBrandScreen(List<Brand> brandList) {
        StackFragment stack = ((StackFragment) ((MainActivity) mActivity).getCurrentStackFragment());
        stack.popFromStack();
        Fragment brandFrag = BrandFragment.makeInstance(brandList);
        stack.addFragmentToStack(brandFrag);
    }

    private void gotoNotSupported() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLogoIcon != null && mLogoIcon.getVisibility() != View.GONE) {
                    mLogoIcon.setVisibility(View.GONE);
                }
                if (mSorryContaner != null && mSorryContaner.getVisibility() != View.VISIBLE) {
                    mSorryContaner.setVisibility(View.VISIBLE);
                }
            }
        }, 3000);

    }

    private void goToGpsSettingScreen() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        getParentFragment().startActivityForResult(intent, REQUEST_CODE_OPEN_GPS_SETTINGS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        System.out.println("REQUEST CODE: " + requestCode + " RESULT CODE:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_GPS_SETTINGS) {
            Location location = BeeroLocationManager.makeInstance().getCurrentLocation();
            if (location != null && BeeroLocationManager.makeInstance().isSupportArea()) {
                loadBrands();
            } else {
                gotoNotSupported();
            }
        }
    }

    private class TransactionHanlder extends PauseHandler {
        protected Activity activity;

        final void setActivity(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected boolean storeMessage(Message message) {
            return true;
        }

        @Override
        protected void processMessage(Message message) {
            final Activity activity = this.activity;
            if (activity != null) {
                switch (message.what) {

                    case BEGIN_TRANSACTION:
                        ResponseBrand obj = (ResponseBrand) message.obj;
                        if (obj != null) {
                            gotoBrandScreen(obj.getBrands());
                        }
                        break;
                }
            }
        }
    }
}
