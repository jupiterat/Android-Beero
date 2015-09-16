package com.au.beero.beero.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.marshalchen.ultimaterecyclerview.SwipeableUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.swipelistview.BaseSwipeListViewListener;
import com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    private SwipeableUltimateRecyclerview mProductListview;
    private List<SearchResult> searchResults;
    private ProductAdapter mBrandAdapter;
    private Animation bounceAnimation;
    private RelativeLayout mProductListContainer;
    private RelativeLayout mPackageSelection;
    private RelativeLayout mContainerSelection;
    private LinearLayout mPackageGroup;
    private LinearLayout mContainerGroup;

    private static final String KEY_BRANDS_ALL = "brands_all";
    private static final String KEY_BRANDS = "brands";
    private static final String KEY_CASES = "case";
    private static final String KEY_SIX_PACKS = "six";
    private static final String KEY_CANS = "cans";
    private static final String KEY_BOTTLE = "bottles";
    private static final String KEY_BOTH = "any";
    private String mBrandStr = "";
    private String mBrandAllStr = "";
    private String mPackage = KEY_CASES;
    private String mContainer = KEY_BOTH;
    private static List<Brand> mBrandsList = null;
    private static List<Brand> mBrandsListAll = null;
    private boolean isLoaded = false;
    private TextView mRefreshBtn;
    private TextView mAddBtn;
    private TextView mFindingStatus;
    private ImageView mPackageArrowDown;
    private ImageView mContainerArrowDown;
    private Animation animFadein;
    private TextView mCaseBtn;
    private TextView mSixPackBtn;
    private TextView mCansBtn;
    private TextView mBottleBtn;
    private TextView mBothBtn;
    private TextView mMyBeerBtn;
    private TextView mAllBeerBtn;
    private boolean isAllSelected = false;//default is all_beer not selected

    public static Fragment makeInstance(List<Brand> brandsAll, List<Brand> brands, String brandStr, String brandAllStr) {
        mBrandsList = brands;
        mBrandsListAll = brandsAll;
        SearchFragment fragment = new SearchFragment();
        Bundle b = new Bundle();
        b.putString(KEY_BRANDS, brandStr);
        b.putString(KEY_BRANDS_ALL,brandAllStr);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBrandStr = getArguments().getString(KEY_BRANDS, "");
            mBrandAllStr = getArguments().getString(KEY_BRANDS_ALL, "");
            if (!mBrandStr.isEmpty()) {
                mBrandStr = mBrandStr.replace(Utility.BRAND_SEPERTOR, Utility.SEARCH_SEPERTOR);
            }
            if (!mBrandAllStr.isEmpty()) {
                mBrandAllStr = mBrandAllStr.replace(Utility.BRAND_SEPERTOR, Utility.SEARCH_SEPERTOR);
            }
        }

//        if (mBrandsList != null) {
//            searchResults = new ArrayList<>(mBrandsList.size());
//            for (int i = 0; i < mBrandsList.size(); i++) {
//                SearchResult item = new SearchResult();
//                item.setId(mBrandsList.get(i).getId());
//                item.setBrandName(mBrandsList.get(i).getName());
//                searchResults.add(i, item);
//            }
//            Collections.sort(searchResults);
//        }
        searchResults = createSearchResult(mBrandsList);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_layout, null);
        view.requestFocus();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isQuickActionVisible()) {
                    hideQuickAction();
                    return true;
                }
                return false;
            }
        });
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (isQuickActionVisible()) {
                            hideQuickAction();
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });
        mPackageTxt = (TextView) view.findViewById(R.id.package_condition);
        mContainerTxt = (TextView) view.findViewById(R.id.container_condition);
        mSearchIcon = (ImageView) view.findViewById(R.id.icon_search);
        mProductListview = (SwipeableUltimateRecyclerview) view.findViewById(R.id.product_list);
        mProductListContainer = (RelativeLayout) view.findViewById(R.id.product_list_container);
        mProductListview.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mProductListview.addOnItemTouchListener(new ItemTouchListenerAdapter(mProductListview.mRecyclerView, this));
        mProductListview.addItemDividerDecoration(mActivity);
        (((SwipeListView) mProductListview.mRecyclerView)).setOffsetLeft(getFrontShowWidth());

        mProductListview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isQuickActionVisible()) {
                    hideQuickAction();
                    return true;
                }
                return false;
            }
        });
        mProductListview.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
            }

            @Override
            public void onStartClose(int position, boolean right) {
            }

            @Override
            public void onClickFrontView(int i) {
                if (isQuickActionVisible()) {
                    hideQuickAction();
                } else {
                    if (searchResults.get(i).getWiningDeal() != null) {
                        StackFragment stack = ((StackFragment) ((MainActivity) mActivity).getCurrentStackFragment());
                        Fragment brandFrag = DealDetailFragment.makeInstance(searchResults.get(i));
                        stack.addFragmentToStack(brandFrag);
                    }
                }
            }

            @Override
            public void onClickBackView(int position) {
                if (isQuickActionVisible()) {
                    hideQuickAction();
                } else {
                    for (Brand brand : mBrandsList) {
                        if (brand.getId().equals(mBrandAdapter.getProducts().get(position).getId())) {
                            brand.setIsSelected(false);
                            break;
                        }
                    }

                    mBrandAdapter.getProducts().remove(position);
                    mBrandAdapter.notifyDataSetChanged();
                    setHeight(mBrandAdapter.getProducts().size());
                    List<Brand> brands = new ArrayList<Brand>();
                    for (SearchResult item : mBrandAdapter.getProducts()) {
                        Brand brand = new Brand();
                        brand.setId(item.getId());
                        brand.setName(item.getBrandName());
                        brands.add(brand);
                    }
                    String[] ids = Utility.createIds(brands);
                    if (ids != null) {
                        Utility.saveSelectedIds(mActivity, ids[0], Utility.PREFS_KEY, ids[1], Utility.PREFS_VALUE);
                    } else {
                        Utility.saveSelectedIds(mActivity, "", Utility.PREFS_KEY, "", Utility.PREFS_VALUE);
                    }
                    if (mBrandAdapter.getProducts() != null && mBrandAdapter.getProducts().size() > 0) {
                        if (mProductListContainer.getVisibility() != View.VISIBLE) {
                            mProductListContainer.setVisibility(View.VISIBLE);
                        }
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mProductListContainer.getVisibility() == View.VISIBLE) {
                                    mProductListContainer.setVisibility(View.GONE);
                                }
                            }
                        }, 1000);

                    }
                }
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {

            }
        });

        mRefreshBtn = (TextView) view.findViewById(R.id.refresh);
        mAddBtn = (TextView) view.findViewById(R.id.add_beer);
        mFindingStatus = (TextView) view.findViewById(R.id.finding_status);
        mPackageSelection = (RelativeLayout) view.findViewById(R.id.package_condition_selection);
        mContainerSelection = (RelativeLayout) view.findViewById(R.id.container_condition_selection);
        mPackageArrowDown = (ImageView) view.findViewById(R.id.arrow_down_package);
        mContainerArrowDown = (ImageView) view.findViewById(R.id.arrow_down_container);
        mCaseBtn = (TextView) view.findViewById(R.id.case_btn);
        mSixPackBtn = (TextView) view.findViewById(R.id.six_pack_btn);
        mCansBtn = (TextView) view.findViewById(R.id.cans_btn);
        mBottleBtn = (TextView) view.findViewById(R.id.bottle_btn);
        mBothBtn = (TextView) view.findViewById(R.id.both_btn);
        mPackageGroup = (LinearLayout) view.findViewById(R.id.package_condition_group);
        mContainerGroup = (LinearLayout) view.findViewById(R.id.container_condition_group);
        mMyBeerBtn = (TextView) view.findViewById(R.id.my_beer);
        mAllBeerBtn = (TextView) view.findViewById(R.id.all_beer);


        mPackageTxt.setOnClickListener(this);
        mContainerTxt.setOnClickListener(this);
        mRefreshBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);

        mCaseBtn.setOnClickListener(this);
        mSixPackBtn.setOnClickListener(this);
        mCansBtn.setOnClickListener(this);
        mBottleBtn.setOnClickListener(this);
        mBothBtn.setOnClickListener(this);
        mPackageGroup.setOnClickListener(this);
        mContainerGroup.setOnClickListener(this);
        mMyBeerBtn.setOnClickListener(this);
        mAllBeerBtn.setOnClickListener(this);
        mMyBeerBtn.setSelected(!isAllSelected);
        mAllBeerBtn.setSelected(isAllSelected);
        //

        animFadein = AnimationUtils.loadAnimation(getActivity(),
                R.anim.fade_in);
        initRotateAnimation();
//        initQuickAction();

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
        int mode = isAllSelected ? SwipeListView.SWIPE_MODE_NONE : SwipeListView.SWIPE_MODE_LEFT;
        (((SwipeListView) mProductListview.mRecyclerView)).setSwipeMode(mode);
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
            case R.id.package_condition_group:
                showQuickAction(true);
                break;
            case R.id.container_condition:
            case R.id.container_condition_group:
                showQuickAction(false);
                break;
            case R.id.refresh:
                if (isQuickActionVisible()) {
                    hideQuickAction();
                } else {
                    search(mBrandStr, mPackage, mContainer);
                }
                break;
            case R.id.add_beer:
                if (isQuickActionVisible()) {
                    hideQuickAction();
                } else {
                    backToPrevious();
                }
                break;
            case R.id.case_btn:
                mPackage = KEY_CASES;
                mPackageTxt.setText(getString(R.string.cases));
                search(mBrandStr, mPackage, mContainer);
                mPackageSelection.setVisibility(View.GONE);
                stopAnimation(mPackageArrowDown);
                mPackageArrowDown.setVisibility(View.INVISIBLE);
                break;
            case R.id.six_pack_btn:
                mPackage = KEY_SIX_PACKS;
                mPackageTxt.setText(getString(R.string.six_pack));
                search(mBrandStr, mPackage, mContainer);
                mPackageSelection.setVisibility(View.GONE);
                stopAnimation(mPackageArrowDown);
                mPackageArrowDown.setVisibility(View.INVISIBLE);
                break;
            case R.id.cans_btn:
                mContainer = KEY_CANS;
                mContainerTxt.setText(getString(R.string.cans));
                search(mBrandStr, mPackage, mContainer);
                mContainerSelection.setVisibility(View.GONE);
                stopAnimation(mContainerArrowDown);
                mContainerArrowDown.setVisibility(View.INVISIBLE);
                break;
            case R.id.bottle_btn:
                mContainer = KEY_BOTTLE;
                mContainerTxt.setText(getString(R.string.bottles));
                search(mBrandStr, mPackage, mContainer);
                mContainerSelection.setVisibility(View.GONE);
                stopAnimation(mContainerArrowDown);
                mContainerArrowDown.setVisibility(View.INVISIBLE);
                break;
            case R.id.both_btn:
                mContainer = KEY_BOTH;
                mContainerTxt.setText(getString(R.string.both));
                search(mBrandStr, mPackage, mContainer);
                mContainerSelection.setVisibility(View.GONE);
                stopAnimation(mContainerArrowDown);
                mContainerArrowDown.setVisibility(View.INVISIBLE);
                break;
            case R.id.my_beer:
                isAllSelected = false;
                mMyBeerBtn.setSelected(!isAllSelected);
                mAllBeerBtn.setSelected(isAllSelected);
                searchResults = createSearchResult(mBrandsList);
                (((SwipeListView) mProductListview.mRecyclerView)).setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
                search(mBrandStr, mPackage, mContainer);
                break;
            case R.id.all_beer:
                isAllSelected = true;
                mMyBeerBtn.setSelected(!isAllSelected);
                mAllBeerBtn.setSelected(isAllSelected);
                searchResults = createSearchResult(mBrandsListAll);
                (((SwipeListView) mProductListview.mRecyclerView)).setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
                search(mBrandAllStr, mPackage, mContainer);
                break;
            default:
                break;
        }
    }

    /**
     * create search result list
     * @param list
     * @return
     */
    private List<SearchResult> createSearchResult(List<Brand> list) {
        if (list != null) {
            List<SearchResult> searchResults = new ArrayList<>(list.size());
            for (int i = 0; i < list.size(); i++) {
                SearchResult item = new SearchResult();
                item.setId(list.get(i).getId());
                item.setBrandName(list.get(i).getName());
                searchResults.add(i, item);
            }
            Collections.sort(searchResults);
            return  searchResults;
        }
        return null;
    }

    /**
     * check search condition is showing or not
     * @return
     */
    private boolean isQuickActionVisible() {
        if (mPackageSelection.getVisibility() == View.VISIBLE || mContainerSelection.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    /**
     * hide search condition
     */
    private void hideQuickAction() {
        if (mPackageSelection.getVisibility() == View.VISIBLE) {
            mPackageSelection.setVisibility(View.GONE);
            stopAnimation(mPackageArrowDown);
            mPackageArrowDown.setVisibility(View.INVISIBLE);
        }
        if (mContainerSelection.getVisibility() == View.VISIBLE) {
            mContainerSelection.setVisibility(View.GONE);
            stopAnimation(mContainerArrowDown);
            mContainerArrowDown.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * show search condition
     * @param left: package condition
     */
    private void showQuickAction(boolean left) {
        if (left) {
            if (mPackageSelection.getVisibility() != View.VISIBLE) {
                mPackageSelection.setVisibility(View.VISIBLE);
                mPackageArrowDown.setVisibility(View.VISIBLE);
                //display with animation
                if (mContainerSelection.getVisibility() != View.VISIBLE) {
                    int itemHeight = getResources().getDimensionPixelSize(R.dimen.com_50dp);
                    bounceFromBottom(mPackageSelection, itemHeight * 2);
                    mPackageArrowDown.startAnimation(animFadein);
                }
            } else {
                mPackageSelection.setVisibility(View.GONE);
                stopAnimation(mPackageArrowDown);
                mPackageArrowDown.setVisibility(View.INVISIBLE);

            }
            if (mContainerSelection.getVisibility() != View.GONE) {
                mContainerSelection.setVisibility(View.GONE);
                stopAnimation(mContainerArrowDown);
                mContainerArrowDown.setVisibility(View.INVISIBLE);
            }

        } else {
            if (mContainerSelection.getVisibility() != View.VISIBLE) {
                mContainerSelection.setVisibility(View.VISIBLE);
                mContainerArrowDown.setVisibility(View.VISIBLE);
                //display with animation
                if (mPackageSelection.getVisibility() != View.VISIBLE) {
                    int itemHeight = getResources().getDimensionPixelSize(R.dimen.com_50dp);
                    bounceFromBottom(mContainerSelection, itemHeight * 3);
                    mContainerArrowDown.startAnimation(animFadein);
                }

            } else {
                mContainerSelection.setVisibility(View.GONE);
                //must call before hide
                stopAnimation(mContainerArrowDown);
                mContainerArrowDown.setVisibility(View.INVISIBLE);
            }
            if (mPackageSelection.getVisibility() != View.GONE) {
                mPackageSelection.setVisibility(View.GONE);
                stopAnimation(mPackageArrowDown);
                mPackageArrowDown.setVisibility(View.INVISIBLE);
            }
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
                String searchTitle = getString(R.string.cases);
                if (mPackage.equals(KEY_SIX_PACKS)) {
                    searchTitle = getString(R.string.six_pack);
                }
                mPackageTxt.setText(searchTitle);
                search(mBrandStr, mPackage, mContainer);
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
                String searchTitle = getString(R.string.both);
                if (mContainer.equals(KEY_CANS)) {
                    searchTitle = getString(R.string.cans);
                } else if (mContainer.equals(KEY_BOTTLE)) {
                    searchTitle = getString(R.string.bottles);
                }
                mContainerTxt.setText(searchTitle);
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
        rotateAnimation.setDuration(2000);

    }

    private void setHeight(int size) {
        int height = 0;
        if (size > 0) {
            int screenHeight = Utility.getScreenHeight(mActivity);
            int header = getResources().getDimensionPixelSize(R.dimen.com_50dp);
            int listHeight = size * getResources().getDimensionPixelSize(R.dimen.com_120dp);
            int functionHeight = getResources().getDimensionPixelSize(R.dimen.com_60dp);


            if ((listHeight + functionHeight + (header * 2)) < screenHeight) {
                height = listHeight + functionHeight;
            } else {
                height = ViewGroup.LayoutParams.MATCH_PARENT;
            }
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

    private void stopAnimation(View v) {
        v.animate().cancel();
        v.clearAnimation();
    }

    private void search(String brands, String packageString, String container) {
        mProductListContainer.setVisibility(View.GONE);
        mFindingStatus.setText(getString(R.string.finding));
        mSearchIcon.setAnimation(rotateAnimation);
        startAnimation();
        if (mBrandStr != null && !mBrandStr.isEmpty()) {
//            final long start = System.currentTimeMillis();
            final Date dateStart = new Date();
            ApiUtility.search(mActivity, new IDataEventHandler<ResponseSearch>() {
                @Override
                public void onNotifyData(final ResponseSearch data, AbstractHttpRequest request) {
                    Date dateEnd = new Date();
                    long duration = dateEnd.getTime() - dateStart.getTime();
//                    long duration = System.currentTimeMillis() - start;
                    System.out.println("duration: " + duration);
                    long waiting = (duration > 2000) ? 0 : (2000 - duration);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopAnimation(mSearchIcon);
                            isLoaded = true;
                            if (data == null) {
                                mFindingStatus.setText(getString(R.string.no_deal));
                            } else {
                                int size1 = searchResults.size();
                                int size2 = data.getSearchResults().size();
                                for (int i = 0; i < size1; i++) {
                                    for (int j = 0; j < size2; j++) {
                                        SearchResult result = data.getSearchResults().get(j);
                                        if (searchResults.get(i).getId().equals(result.getId())) {
                                            searchResults.get(i).setWiningDeal(result.getWiningDeal());
                                            searchResults.get(i).setLosingDeals(result.getLosingDeals());
                                            break;
                                        }
                                    }
                                }
                                loadResult();
                                initAnimation();
                                mFindingStatus.setText("");

                            }
                        }
                    }, waiting);
                }
            }, new SearchRequest(brands, packageString, container));
        }
    }

    private void loadResult() {
//        if (mBrandAdapter == null) {
            mBrandAdapter = new ProductAdapter(mActivity, searchResults);
            mProductListview.setAdapter(mBrandAdapter);
//        } else {
//            mBrandAdapter.notifyDataSetChanged();
//        }

        setHeight(searchResults.size());
        if (mProductListContainer != null && mProductListContainer.getVisibility() != View.VISIBLE) {
            mProductListContainer.setVisibility(View.VISIBLE);
        }
    }

    private void backToPrevious() {
        StackFragment stack = ((StackFragment) ((MainActivity) mActivity).getCurrentStackFragment());
        stack.backToPrevious();

    }


    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int i) {
        if(isAllSelected) {
            if (searchResults.get(i).getWiningDeal() != null) {
                StackFragment stack = ((StackFragment) ((MainActivity) mActivity).getCurrentStackFragment());
                Fragment brandFrag = DealDetailFragment.makeInstance(searchResults.get(i));
                stack.addFragmentToStack(brandFrag);
            }
        }
    }

    @Override
    public void onItemLongClick(RecyclerView recyclerView, View view, int i) {

    }

    private void initAnimation() {
        float yDelta = getScreenHeight();
        if (mProductListContainer.getHeight() <= -1) {//in case MATCH_PARENT
            yDelta = mProductListContainer.getHeight();
        }
        final Animation animation = new TranslateAnimation(0, 0, -yDelta, 0);
        animation.setDuration(1300);
        animation.setInterpolator(new BounceInterpolator());
        mProductListContainer.startAnimation(animation);
    }

    private void bounceFromBottom(View v, int height) {
        final Animation animation = new TranslateAnimation(0, 0, height, 0);
        animation.setDuration(500);
        animation.setInterpolator(new BounceInterpolator());
        v.startAnimation(animation);
    }

    private int getFrontShowWidth() {
        int behindWidth = getResources().getDimensionPixelSize(R.dimen.com_100dp);
        int screenWidth = getScreenWidth();
        return screenWidth - behindWidth;
    }

    private float getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return (float) displaymetrics.heightPixels;
    }

    private int getScreenWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }
}
