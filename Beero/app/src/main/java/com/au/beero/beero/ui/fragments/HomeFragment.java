package com.au.beero.beero.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.au.beero.beero.R;
import com.au.beero.beero.ui.base.BaseFragment;

/**
 * Created by jupiter.at@gmail.com on 8/12/2015.
 */
public class HomeFragment extends BaseFragment {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private ImageView mLogoIcon;
    private RelativeLayout mSorryContaner;

    public static Fragment makeInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        showSplash();
    }

    private void showSplash(){
        new Handler().postDelayed(new Runnable() {
			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                if (false) {
                    gotoSearchScreen();
                } else {
                    gotoNotSupported();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void gotoSearchScreen() {

    }

    private void gotoNotSupported() {
        mLogoIcon.setVisibility(View.GONE);
        mSorryContaner.setVisibility(View.VISIBLE);
    }
}
