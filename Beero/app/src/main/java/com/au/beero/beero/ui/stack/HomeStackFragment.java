package com.au.beero.beero.ui.stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.au.beero.beero.ui.fragments.HomeFragment;

/**
 * @author jupiter.at@gmail.com
 */
public class HomeStackFragment extends StackFragment {
    int mStackLevel = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mStackLevel = savedInstanceState.getInt("level");
        }
    }

    @Override
    protected Fragment addInitFragment() {
        return HomeFragment.makeInstance();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", mStackLevel);
    }

}
