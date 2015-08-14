package com.au.beero.beero.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.au.beero.beero.model.SearchResult;
import com.au.beero.beero.model.response.ResponseSearch;
import com.au.beero.beero.request.SearchRequest;
import com.au.beero.beero.ui.activity.MainActivity;
import com.au.beero.beero.ui.adapter.ProductAdapter;
import com.au.beero.beero.ui.base.BaseFragment;
import com.au.beero.beero.ui.stack.StackFragment;
import com.au.beero.beero.ui.widget.ActionItem;
import com.au.beero.beero.ui.widget.QuickAction;
import com.au.beero.beero.utility.ApiUtility;
import com.au.beero.beero.utility.Utility;
import com.framework.network.request.AbstractHttpRequest;
import com.framework.network.task.IDataEventHandler;
import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jupiter.at@gmail.com on 8/12/2015.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener, ItemTouchListenerAdapter.RecyclerViewOnItemClickListener {
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
    private List<SearchResult> searchResults;
    private ProductAdapter mBrandAdapter;
    private Animation bounceAnimation;
    RelativeLayout mProductListContainer;

    private static final String KEY_BRANDS = "brands";
    private static final String KEY_CASES = "case";
    private static final String KEY_SIX_PACKS = "six";
    private static final String KEY_CANS = "cans";
    private static final String KEY_BOTTLE = "bottles";
    private static final String KEY_BOTH = "any";
    private String mBrandStr = "";
    private String mPackage = KEY_CASES;
    private String mContainer = KEY_BOTH;
    private static List<Brand> mBrandsList = null;
    private boolean isLoaded = false;
    private TextView mRefreshBtn;
    private TextView mAddBtn;


    public static Fragment makeInstance(List<Brand> brands, String brandStr) {
        mBrandsList = brands;
        SearchFragment fragment = new SearchFragment();
        Bundle b = new Bundle();
        b.putString(KEY_BRANDS, brandStr);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBrandStr = getArguments().getString(KEY_BRANDS, "");
        }
        if (mBrandsList != null) {
            searchResults = new ArrayList<>(mBrandsList.size());
            for (int i = 0; i < mBrandsList.size(); i++) {
                SearchResult item = new SearchResult();
                item.setId(mBrandsList.get(i).getId());
                item.setBrandName(mBrandsList.get(i).getName());
                searchResults.add(i, item);
            }
        }

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
        mProductListview.addOnItemTouchListener(new ItemTouchListenerAdapter(mProductListview.mRecyclerView, this));
        mProductListview.addItemDividerDecoration(mActivity);
        mRefreshBtn = (TextView) view.findViewById(R.id.refresh);
        mAddBtn = (TextView) view.findViewById(R.id.add_beer);

        mPackageTxt.setOnClickListener(this);
        mContainerTxt.setOnClickListener(this);
        mRefreshBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);

        initRotateAnimation();
        initQuickAction();

        if (!isLoaded) {
            search(mBrandStr, mPackage, mContainer);
        } else {
            loadResult();
        }

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
                mPackageAction.show(v);
                mPackageAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_BOTTOM);
                break;
            case R.id.container_condition:
                mContainerAction.show(v);
                mPackageAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_BOTTOM);
                break;
            case R.id.refresh:
                mProductListContainer.setVisibility(View.GONE);
                search(mBrandStr, mPackage, mContainer);
                break;
            case R.id.add_beer:
                backToPrevious();
                break;
            default:
                break;
        }
    }

    private void initQuickAction() {
        ActionItem addItem = new ActionItem(ID_CASES, getString(R.string.cases), null);
        ActionItem acceptItem = new ActionItem(ID_SIX_PACKS, getString(R.string.six_pack), null);

        mPackageAction = new QuickAction(mActivity, QuickAction.VERTICAL);
        mPackageAction.addActionItem(addItem);
        mPackageAction.addActionItem(acceptItem);
        mPackageAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                switch (pos) {
                    case 1:
                        mPackage = KEY_SIX_PACKS;
                        break;
                    case 0:
                        mPackage = KEY_CASES;
                        break;
                }
                search(mBrandStr,mPackage,mContainer);
            }
        });
        //
        ActionItem cansItem = new ActionItem(ID_CANS, getString(R.string.cans), null);
        ActionItem bottleItem = new ActionItem(ID_BOTTLE, getString(R.string.bottles), null);
        ActionItem bothItem = new ActionItem(ID_BOTH, getString(R.string.both), null);

        mContainerAction = new QuickAction(mActivity, QuickAction.VERTICAL);
        mContainerAction.addActionItem(cansItem);
        mContainerAction.addActionItem(bottleItem);
        mContainerAction.addActionItem(bothItem);
        mContainerAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                switch (pos) {
                    case 0:
                        mContainer = KEY_CANS;
                        break;
                    case 1:
                        mContainer = KEY_BOTTLE;
                        break;
                    case 2:
                        mContainer = KEY_BOTH;
                        break;
                }
                search(mBrandStr, mPackage, mContainer);
            }
        });
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
        mSearchIcon.animate().cancel();
    }

    private void search(String brands, String packageString, String container) {
        startAnimation();
        if (mBrandStr != null && !mBrandStr.isEmpty()) {
            ApiUtility.search(mActivity, new IDataEventHandler<ResponseSearch>() {
                @Override
                public void onNotifyData(ResponseSearch data, AbstractHttpRequest request) {
                    stopAnimation();
                    isLoaded = true;
                    if (data == null) {

                    } else {
                        int size1 = searchResults.size();
                        int size2 = data.getSearchResults().size();
                        for (int i = 0; i < size1; i++) {
                            for (int j = 0; j < size2; j++) {
                                SearchResult result = data.getSearchResults().get(j);
                                if (searchResults.get(i).getId().equals(result.getId())) {
                                    searchResults.get(i).setWiningDeal(result.getWiningDeal());
                                    searchResults.get(i).setLosingDeals(result.getLosingDeals());
                                }
                            }
                        }
                        loadResult();
                        initBounceAnimation();
                    }
                }
            }, new SearchRequest(brands, packageString, container));
        }
    }

    private void loadResult() {
        mBrandAdapter = new ProductAdapter(mActivity, searchResults);
        mProductListview.setAdapter(mBrandAdapter);
        setHeight(searchResults.size());
    }

    private void backToPrevious() {
        StackFragment stack = ((StackFragment) ((MainActivity) mActivity).getCurrentStackFragment());
        stack.backToPrevious();

    }


    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int i) {
        showToast("pos " + i);
    }

    @Override
    public void onItemLongClick(RecyclerView recyclerView, View view, int i) {

    }
}
