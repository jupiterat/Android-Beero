package com.au.beero.beero.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.au.beero.beero.R;
import com.au.beero.beero.ui.base.BaseFragment;

/**
 * Created by jupiter.at@gmail.com on 8/15/2015.
 */
public class StoreDetailFragment extends BaseFragment {

    public static Fragment makeInstance() {
        return new StoreDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_detail_layout, null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
