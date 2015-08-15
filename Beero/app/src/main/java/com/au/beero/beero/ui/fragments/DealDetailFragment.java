package com.au.beero.beero.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.au.beero.beero.R;
import com.au.beero.beero.manager.VolleySingleton;
import com.au.beero.beero.model.SearchResult;
import com.au.beero.beero.model.Store;
import com.au.beero.beero.model.WiningDeal;
import com.au.beero.beero.ui.activity.MainActivity;
import com.au.beero.beero.ui.base.BaseFragment;
import com.au.beero.beero.ui.stack.StackFragment;
import com.au.beero.beero.utility.Constants;

/**
 * Created by jupiter.at@gmail.com on 8/15/2015.
 */
public class DealDetailFragment extends BaseFragment implements View.OnClickListener {

    private TextView mVerifiedStock;
    private TextView mStoreName;
    private TextView mStoreAdd;
    private TextView mStoreDistance;
    private TextView mStoreOpening;
    private TextView mDealPrice;
    private TextView mDealPriceSmall;
    private TextView mDealPackage;
    private TextView mLoosingTxt;
    private TextView mStoreClosed;
    private NetworkImageView mProductImg;
    private static SearchResult mSearchResult;
    private FrameLayout mExclusiveContainer;

    public static Fragment makeInstance(SearchResult searchResult) {
        mSearchResult = searchResult;
        return new DealDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_detail_layout, null);
        mVerifiedStock = (TextView) view.findViewById(R.id.verified_txt);
        mStoreName = (TextView) view.findViewById(R.id.store_name);
        mStoreClosed = (TextView) view.findViewById(R.id.store_closed);
        mStoreAdd = (TextView) view.findViewById(R.id.store_address);
        mStoreDistance = (TextView) view.findViewById(R.id.store_distance);
        mStoreOpening = (TextView) view.findViewById(R.id.store_opening);
        mProductImg = (NetworkImageView) view.findViewById(R.id.product_img);
        mDealPrice = (TextView) view.findViewById(R.id.product_price_big);
        mDealPriceSmall = (TextView) view.findViewById(R.id.product_price_small);
        mDealPackage = (TextView) view.findViewById(R.id.product_container);
        mLoosingTxt = (TextView) view.findViewById(R.id.show_loosing);
        mExclusiveContainer = (FrameLayout) view.findViewById(R.id.exclusive_container);
        loadData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().show();
        ((MainActivity) getActivity()).displayBackIcon(true, (StackFragment) getParentFragment(),
                mSearchResult.getBrandName(), "", false);
    }

    private void loadData() {
        Store store = mSearchResult.getWiningDeal().getStore();
        mStoreOpening.setText(store.getStoreState());
        if (store.getStoreState().equalsIgnoreCase("Closed")) {
            mStoreOpening.setVisibility(View.GONE);
            mStoreClosed.setVisibility(View.VISIBLE);
        }
        WiningDeal winingDeal = mSearchResult.getWiningDeal();
        mStoreName.setText(store.getName());
        mStoreAdd.setText(store.getAddress());
        mStoreDistance.setText(String.format(getString(R.string.distance_drive_format), winingDeal.getBeautifiedDriveDistance()));
        ImageLoader loader = VolleySingleton.getInstance().getImageLoader();
        mProductImg.setImageUrl(winingDeal.getUrl(), loader);
        mProductImg.setErrorImageResId(R.drawable.brand_0);
        mProductImg.setDefaultImageResId(R.drawable.brand_0);
        String container = String.format(getString(R.string.container_format), winingDeal.getQty(), winingDeal.getContainerSize(), winingDeal.getContainerType());
        mDealPackage.setText(container);

        int bigPrice = (int) Float.parseFloat(winingDeal.getPrice());
        String bigPriceStr = String.valueOf(bigPrice);
        mDealPrice.setText(bigPriceStr);
        float smallPrice = Float.parseFloat(winingDeal.getPrice()) - bigPrice;
        mDealPriceSmall.setText(String.format("%2d", (int) (smallPrice * 100)));
        if (winingDeal.isExclusive()) {
            mExclusiveContainer.setVisibility(View.VISIBLE);
        } else {
            mExclusiveContainer.setVisibility(View.GONE);
        }
        int losingSize = mSearchResult.getLosingDeals() != null ? mSearchResult.getLosingDeals().size() : 0;
        mLoosingTxt.setText(String.format(getString(R.string.best_deals), losingSize));
        mLoosingTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.show_loosing:
                if (mSearchResult.getLosingDeals() != null) {
                    MapFragment searchFrag = MapFragment.makeInstance(mSearchResult.getBrandName(), mSearchResult.getLosingDeals());
                    ((StackFragment) ((MainActivity) getActivity()).getCurrentStackFragment())
                            .addFragmentToStack(searchFrag);
                }
                break;
        }
    }
}
