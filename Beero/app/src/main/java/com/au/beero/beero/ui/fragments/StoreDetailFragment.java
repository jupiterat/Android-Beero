package com.au.beero.beero.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.au.beero.beero.R;
import com.au.beero.beero.model.Store;
import com.au.beero.beero.ui.base.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by thuc.phan on 8/15/2015.
 */
public class StoreDetailFragment extends BaseFragment implements OnMapReadyCallback {

    private MapView mMapView;
    private Store mStore;
    private final float ZOOM_LEVEL = 15f;

    public static StoreDetailFragment makeInstance() {
        return new StoreDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.store_detail_fragment_layout, container, false);
        mMapView = (MapView) v.findViewById(R.id.store_detail_map);
        mMapView.getMapAsync(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL));
    }
}
