package com.au.beero.beero.ui.fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.au.beero.beero.R;
import com.au.beero.beero.manager.BeeroLocationManager;
import com.au.beero.beero.model.LosingDeal;
import com.au.beero.beero.ui.activity.MainActivity;
import com.au.beero.beero.ui.adapter.MyInfoWindowAdapter;
import com.au.beero.beero.ui.base.BaseFragment;
import com.au.beero.beero.ui.stack.StackFragment;
import com.au.beero.beero.utility.Utility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuc.phan on 8/14/2015.
 */
public class MapFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private final float ZOOM_LEVEL = 15f;
    private List<LosingDeal> mLosingDeal;
    private String mBrandName = "";

    private static final String ARG_LOS = "losing_deal";
    private static final String ARG_NAME = "brand_name";

    public static MapFragment makeInstance(String title, List<LosingDeal> losingDeals) {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARG_LOS, (ArrayList) losingDeals);
        bundle.putString(ARG_NAME, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mLosingDeal = bundle.getParcelableArrayList(ARG_LOS);
            mBrandName = bundle.getString(ARG_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment_layout, container, false);
        mMapView = (MapView) v.findViewById(R.id.google_map);
        mMapView.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(mActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().show();
        ((MainActivity) getActivity()).displayBackIcon(true, (StackFragment) getParentFragment(),
                mBrandName, "", false);
        mMapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
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
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.setMyLocationEnabled(true);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        Location myLocation = BeeroLocationManager.makeInstance().getCurrentLocation();
        if (myLocation != null) {
            LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(myLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location)));
            builder.include(myLatLng);
        }
        googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter(mActivity));
        if (mLosingDeal != null) {
            for (int i = 0; i < mLosingDeal.size(); i++) {
                LosingDeal deal = mLosingDeal.get(i);
                LatLng latLng = new LatLng(Double.valueOf(deal.getLat()), Double.valueOf(deal.getLng()));
                builder.include(latLng);
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng).title(deal.getStoreName())
                        .snippet(deal.getPricePerLitre()).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));
            }
        }
        LatLngBounds bounds = builder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, Utility.getScreenWidth(mActivity), Utility.getScreenHeight(mActivity), 50));

    }

    @Override
    public void onMapLoaded() {

    }
}
