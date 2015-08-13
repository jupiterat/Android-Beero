package com.au.beero.beero.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.au.beero.beero.R;
import com.au.beero.beero.model.Brand;
import com.au.beero.beero.ui.adapter.BrandAdapter;
import com.au.beero.beero.ui.adapter.ProductAdapter;
import com.au.beero.beero.ui.base.BaseFragment;
import com.au.beero.beero.ui.widget.ActionItem;
import com.au.beero.beero.ui.widget.QuickAction;
import com.au.beero.beero.utility.Utility;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jupiter.at@gmail.com on 8/12/2015.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {
    private TextView mPackageTxt;
    private TextView mContainerTxt;
    private static final int ID_CASES = 1;
    private static final int ID_SIX_PACKS = 2;
    private static final int ID_CANS = 3;
    private static final int ID_BOTTLE = 4;
    private static final int ID_BOTH = 5;
    private QuickAction mPackageAction;
    private QuickAction mContainerAction;
    private ImageView mSearchIcon;
    private RotateAnimation rotateAnimation;
    private UltimateRecyclerView mProductListview;
    private List<Brand> mBrandList;
    private ProductAdapter mBrandAdapter;
    private Animation bounceAnimation;
    RelativeLayout mProductListContainer;

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
        mSearchIcon = (ImageView) view.findViewById(R.id.icon_search);
        mProductListview = (UltimateRecyclerView) view.findViewById(R.id.product_list);
        mProductListContainer = (RelativeLayout) view.findViewById(R.id.product_list_container);
        mProductListview.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mProductListview.addItemDividerDecoration(mActivity);

        mBrandList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Brand br = new Brand();
            br.setName("Beer band" + i);
            if (i % 2 == 0) {
                br.setIsSelected(true);
            }
            mBrandList.add(br);
        }

        mPackageTxt.setOnClickListener(this);
        mContainerTxt.setOnClickListener(this);

        initRotateAnimation();
        initQuickAction();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().hide();
        searchBeer();
        mBrandAdapter = new ProductAdapter(mActivity, mBrandList);
        mProductListview.setAdapter(mBrandAdapter);
        setHeight(mBrandList.size());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initBounceAnimation();
            }
        }, 3000);


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
                mPackageAction.show(v);
                mPackageAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_BOTTOM);
                break;
            case R.id.container_condition:
                mContainerAction.show(v);
                mPackageAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_BOTTOM);
                break;
            default:
                break;
        }
    }

    private void searchBeer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 5000);
    }

    private void initQuickAction() {
        ActionItem addItem = new ActionItem(ID_CASES, getString(R.string.cases), null);
        ActionItem acceptItem = new ActionItem(ID_SIX_PACKS, getString(R.string.six_pack), null);

        mPackageAction = new QuickAction(mActivity, QuickAction.VERTICAL);
        mPackageAction.addActionItem(addItem);
        mPackageAction.addActionItem(acceptItem);
        //
        ActionItem cansItem = new ActionItem(ID_CANS, getString(R.string.cans), null);
        ActionItem bottleItem = new ActionItem(ID_BOTTLE, getString(R.string.bottles), null);
        ActionItem bothItem = new ActionItem(ID_BOTH, getString(R.string.both), null);

        mContainerAction = new QuickAction(mActivity, QuickAction.VERTICAL);
        mContainerAction.addActionItem(cansItem);
        mContainerAction.addActionItem(bottleItem);
        mContainerAction.addActionItem(bothItem);
    }

    private void initRotateAnimation() {
        rotateAnimation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
                .5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(1000);
        mSearchIcon.setAnimation(rotateAnimation);

    }

    private void setHeight(int size) {
        int screenHeight = Utility.getScreenHeight(mActivity);
        int header = getResources().getDimensionPixelSize(R.dimen.com_50dp);
        int listHeight = size * getResources().getDimensionPixelSize(R.dimen.com_100dp);
        int functionHeight = getResources().getDimensionPixelSize(R.dimen.com_60dp);

        int height = 0;
        if ((listHeight + functionHeight + (header * 2)) < screenHeight) {
            height = listHeight + functionHeight;
        } else {
            height = ViewGroup.LayoutParams.MATCH_PARENT;
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mProductListContainer.setLayoutParams(params);
    }

    private void initBounceAnimation() {
        mProductListContainer.setVisibility(View.VISIBLE);
        bounceAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.bounce);
        bounceAnimation.setInterpolator(new BounceInterpolator());
        mProductListContainer.startAnimation(bounceAnimation);
//        TranslateAnimation translation;
//        translation = new TranslateAnimation(0f, 0F, 0f, mProductListview.getHeight());
////        translation.setStartOffset(500);
//        translation.setDuration(2000);
//        translation.setFillAfter(true);
//        translation.setInterpolator(new BounceInterpolator());
//        mProductListview.startAnimation(translation);

    }

    private void startAnimation() {
        mSearchIcon.startAnimation(rotateAnimation);
    }

    private void stopAnimation() {
        mSearchIcon.clearAnimation();
    }

}
