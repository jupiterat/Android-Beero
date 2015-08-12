package com.au.beero.beero.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.au.beero.beero.R;
import com.au.beero.beero.ui.base.BaseFragment;

import garin.artemiy.quickaction.library.QuickAction;

/**
 * Created by jupiter.at@gmail.com on 8/12/2015.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {
    private TextView mPackageTxt;
    private TextView mContainerTxt;
    private QuickAction quickAction;
    public static Fragment makeInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_layout, null);
        mPackageTxt = (TextView) view.findViewById(R.id.package_condition);
        mContainerTxt = (TextView) view.findViewById(R.id.container_condition);
        mPackageTxt.setOnClickListener(this);
        mContainerTxt.setOnClickListener(this);
        RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.custom_condition_layout, null);
        quickAction = new QuickAction(mActivity, -1,item,item );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.package_condition:
                quickAction.show(v);
                break;
            case R.id.container_condition:
                break;
            default:
                break;
        }
    }

}
