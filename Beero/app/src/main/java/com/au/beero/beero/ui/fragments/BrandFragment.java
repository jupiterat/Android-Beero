package com.au.beero.beero.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.au.beero.beero.R;
import com.au.beero.beero.model.Brand;
import com.au.beero.beero.ui.activity.MainActivity;
import com.au.beero.beero.ui.adapter.BrandAdapter;
import com.au.beero.beero.ui.base.BaseFragment;
import com.au.beero.beero.ui.stack.StackFragment;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jupiter.at@gmail.com on 8/12/15.
 */
public class BrandFragment extends BaseFragment {
    private TextView mDoneBtn;
    private UltimateRecyclerView mBrandListview;
    private BrandAdapter mBrandAdapter;
    private List<Brand> mBrandList;

    public static Fragment makeInstance() {
        return new BrandFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brand_layout, null);
        mDoneBtn = (TextView) view.findViewById(R.id.done_select_brand);
        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("clicked");
            }
        });
        mBrandListview = (UltimateRecyclerView)view.findViewById(R.id.brand_list);
        mBrandListview.setLayoutManager(new LinearLayoutManager(mActivity));
        mBrandListview.addItemDividerDecoration(mActivity);
        mBrandList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Brand br = new Brand();
            br.setName("Beer band" + i);
            if(i % 2 == 0) {
                br.setIsSelected(true);
            }
            mBrandList.add(br);
        }



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String title = getString(R.string.brand_title);
        ((MainActivity) getActivity()).displayBackIcon(true, (StackFragment) getParentFragment(),
                title, "", false);
        mBrandAdapter = new BrandAdapter(mActivity,mBrandList);
        mBrandListview.setAdapter(mBrandAdapter);
    }
}
