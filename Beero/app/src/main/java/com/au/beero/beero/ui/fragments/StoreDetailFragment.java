package com.au.beero.beero.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.au.beero.beero.R;
import com.au.beero.beero.manager.VolleySingleton;
import com.au.beero.beero.model.OpenTime;
import com.au.beero.beero.model.Store;
import com.au.beero.beero.ui.activity.MainActivity;
import com.au.beero.beero.ui.base.BaseFragment;
import com.au.beero.beero.ui.dialog.CatalogDialog;
import com.au.beero.beero.ui.stack.StackFragment;
import com.au.beero.beero.utility.Constants;
import com.au.beero.beero.utility.PhoneUtility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by thuc.phan on 8/15/2015.
 */
public class StoreDetailFragment extends BaseFragment implements OnMapReadyCallback, View.OnClickListener {

    private MapView mMapView;
    private static Store mStore;
    private static String mTitle = "";
    private final float ZOOM_LEVEL = 15f;
    private TextView mStoreName;
    private TextView mStoreAdd;
    private TextView mStoreMember;
    private TextView mStorePhone;
    private TextView mStoreOpening;
    private TextView mStoreClose;
    private TextView mWelcome;
    private TextView mRemainingTime;
    private NetworkImageView mStoreBanner;
    private NetworkImageView mStoreCata;
    private LinearLayout mOpeningHours;
    private ImageView mZoomCatalog;

    private LinearLayout mCatalogContainer;

    public static StoreDetailFragment makeInstance(String title, Store store) {
        mStore = store;
        mTitle = title;
        return new StoreDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_detail_fragment_layout, container, false);
        mMapView = (MapView) view.findViewById(R.id.store_detail_map);
        mStoreName = (TextView) view.findViewById(R.id.store_name);
        mStoreAdd = (TextView) view.findViewById(R.id.store_address);
        mRemainingTime = (TextView) view.findViewById(R.id.time_to_close);
        mStoreMember = (TextView) view.findViewById(R.id.store_member_since);
        mStorePhone = (TextView) view.findViewById(R.id.store_phone);
        mStoreOpening = (TextView) view.findViewById(R.id.store_opening);
        mStoreClose = (TextView) view.findViewById(R.id.store_closed);
        mStoreBanner = (NetworkImageView) view.findViewById(R.id.banner);
        mStoreCata = (NetworkImageView) view.findViewById(R.id.catalog);
        mOpeningHours = (LinearLayout) view.findViewById(R.id.opening_hours);
        mWelcome = (TextView) view.findViewById(R.id.welcome_msg);
        mCatalogContainer = (LinearLayout) view.findViewById(R.id.catalog_container);
        mZoomCatalog = (ImageView) view.findViewById(R.id.catalog_zoom);
        mZoomCatalog.setOnClickListener(this);
        mStorePhone.setOnClickListener(this);

        mMapView.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(mActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        loadData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().show();
        mMapView.onResume();
        ((MainActivity) getActivity()).displayBackIcon(true, (StackFragment) getParentFragment(),
                mTitle, "", false);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(false);
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setAllGesturesEnabled(false);
        LatLng latLng = new LatLng(Double.valueOf(mStore.getLat()), Double.valueOf(mStore.getLng()));
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                loadMap(String.valueOf(marker.getPosition().latitude), String.valueOf(marker.getPosition().longitude));
                return false;
            }
        });
        mMapView.getMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                loadMap(mStore.getLat(), mStore.getLng());
            }
        });
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL));
    }

    @Override
    public void onClick(View v) {
        if (v == mZoomCatalog){
            if (mStore.isHasCatalog()){
                CatalogDialog dialog = new CatalogDialog(mActivity, mStore.getId());
                dialog.show();
            }
        } else if(v.getId() == R.id.store_phone) {
            String phone = "";
            if(mStore.getPhone() != null) {
                phone = mStore.getPhone();
            }
            PhoneUtility.makeInstance(getActivity()).call(phone);
        }
    }

    private void loadData() {
        mStoreName.setText(mStore.getName());
        mStoreAdd.setText(mStore.getAddress());
        mStoreMember.setVisibility(View.VISIBLE);
        /*if (mStore.isMember()) {
            mStoreMember.setVisibility(View.VISIBLE);
        } else {
            mStoreMember.setVisibility(View.GONE);
        }*/

        if (mStore.getPhone()!=null){
            mStorePhone.setText(mStore.getPhone());
        }
        ImageLoader loader = VolleySingleton.getInstance().getImageLoader();

        mStoreBanner.setDefaultImageResId(R.drawable.store_placeholder);
        mStoreBanner.setErrorImageResId(R.drawable.store_placeholder);
//        mStoreBanner.setImageUrl("http://beero.com.au/stores/1/files/cover.jpg", loader);
        if (mStore.isHasCover()) {
            mStoreBanner.setVisibility(View.VISIBLE);
//            mStoreBanner.setImageUrl("", loader);
            mStoreBanner.setImageUrl(String.format(getString(R.string.cover_url), mStore.getId().toString()), loader);
        } else {
            mStoreBanner.setVisibility(View.GONE);
        }

        mStoreCata.setDefaultImageResId(R.drawable.catalog_placeholder);
        mStoreCata.setErrorImageResId(R.drawable.catalog_placeholder);
//        mStoreCata.setImageUrl("http://beero.com.au/stores/1/files/catalog.png", loader);
        if (mStore.isHasCatalog()) {
            mCatalogContainer.setVisibility(View.VISIBLE);
            mStoreCata.setImageUrl(String.format(getString(R.string.catalog_url), mStore.getId().toString()), loader);
        } else {
            mCatalogContainer.setVisibility(View.GONE);
        }
        if (mStore.isHasMgr()) {

        } else {

        }
//        if (mStore.getMgrWelcome()!=null){
//            mWelcome.setText(mStore.getMgrWelcome());
//        }
        if (mStore.getMessage() != null &&! mStore.getMessage().isEmpty()){
            mWelcome.setText(mStore.getMessage());
            mWelcome.setVisibility(View.VISIBLE);
        } else {
            mWelcome.setVisibility(View.GONE);
        }
        addOpeningHours();
        mStoreOpening.setText(mStore.getBeautifiedLabelForOpenTimeToday());
        if (mStore.getBeautifiedLabelForOpenTimeToday().equalsIgnoreCase("Closed")) {
            mStoreClose.setVisibility(View.VISIBLE);
            mStoreOpening.setVisibility(View.GONE);
        }
        long remainingTime = mStore.getRemainingTime();
        if (remainingTime != -1) {
            mRemainingTime.setVisibility(View.VISIBLE);
            CountDownTimer timer = new CountDownTimer(remainingTime * 60 * 1000, Constants.COUNT_DOWN_UNIT) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(!isAdded()) {
                        return;
                    }
                    long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
//                    long diffInHours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                    String time = String.format(getString(R.string.time_remaining_format), "" + diffInMinutes);
                    mRemainingTime.setText(time);
                }

                @Override
                public void onFinish() {
                    mRemainingTime.setVisibility(View.GONE);
                }
            };
            timer.start();
        } else {
            mRemainingTime.setVisibility(View.GONE);
        }
    }

    private void addOpeningHours() {
        if (mStore.getOpenHours() != null) {
            List<OpenTime> openTimes = mStore.getOpenHours();
            Collections.sort(openTimes);
            LayoutInflater li = LayoutInflater.from(mActivity);
            for (OpenTime openTime : openTimes) {
                View v = li.inflate(R.layout.opening_hours_layout, null);
                TextView day = (TextView) v.findViewById(R.id.day);
                TextView time = (TextView) v.findViewById(R.id.time);
                day.setText(openTime.getDay());
                time.setText(openTime.getOpenTime() + " to " + openTime.getCloseTime());
                mOpeningHours.addView(v);
            }
        }

    }

    /**
     * load map on google map app
     * @param lat
     * @param lng
     */
    private void loadMap(String lat, String lng) {
        String uriStr = "geo:" + lat + "," + lng + "?z=15";
        Uri gmmIntentUri = Uri.parse(uriStr);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
