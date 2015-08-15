package com.au.beero.beero.ui.fragments;

import android.content.SharedPreferences;
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
import com.au.beero.beero.utility.Utility;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

/**
 * Created by jupiter.at@gmail.com on 8/12/15.
 */
public class BrandFragment extends BaseFragment {
    private TextView mDoneBtn;
    private UltimateRecyclerView mBrandListview;
    private BrandAdapter mBrandAdapter;
    private static List<Brand> mBrandList;

    private String[] mSelectedIds = null;

    private String ids = "";

    public static Fragment makeInstance(List<Brand> brandList) {
        mBrandList = brandList;
        return new BrandFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brand_layout, null);
        mDoneBtn = (TextView) view.findViewById(R.id.done_select_brand);
        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDoneClick();
            }
        });
        mBrandListview = (UltimateRecyclerView)view.findViewById(R.id.brand_list);
        mBrandListview.setLayoutManager(new LinearLayoutManager(mActivity));
        mBrandListview.addItemDividerDecoration(mActivity);
        if(Utility.getPrefIds(mActivity).length() > 0) {
            mSelectedIds = Utility.getPrefIds(mActivity).split(Utility.BRAND_SEPERTOR);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().show();
        String title = getString(R.string.brand_title);
        ((MainActivity) getActivity()).displayBackIcon(true, (StackFragment) getParentFragment(),
                title, "", false);
        mBrandAdapter = new BrandAdapter(mActivity,mBrandList);
        mBrandListview.setAdapter(mBrandAdapter);
        if(mSelectedIds != null && mSelectedIds.length > 0) {
            for(Brand item : mBrandAdapter.getBrands()) {
                for(int i = 0; i < mSelectedIds.length; i++) {
                    if(item.getId().equals(mSelectedIds[i])) {
                        item.setIsSelected(true);
                    }
                }
            }
            mBrandAdapter.updateList(mBrandList);
        }
    }

    private void handleDoneClick() {
        if(mBrandAdapter != null && mBrandAdapter.getSelectedBrandsStr() != null && mBrandAdapter.getSelectedBrandsStr().size() > 0) {
            String ids = Utility.createIds(mBrandAdapter.getSelectedBrandsStr());
            Utility.saveSelectedIds(mActivity,ids);
            gotoSearch();
        } else {
            showDialog(getString(R.string.select_brand_warning));
        }
    }

    private void gotoSearch() {
        String ids = Utility.getPrefIds(mActivity);
        Fragment searchFrag = SearchFragment.makeInstance(mBrandAdapter.getSelectedBrands(),ids);
        ((StackFragment) ((MainActivity) getActivity()).getCurrentStackFragment())
                .addFragmentToStack(searchFrag);
    }


}
