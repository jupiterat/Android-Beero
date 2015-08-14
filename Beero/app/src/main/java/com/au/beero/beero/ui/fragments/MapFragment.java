package com.au.beero.beero.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.au.beero.beero.R;
import com.au.beero.beero.ui.adapter.MyInfoWindowAdapter;
import com.au.beero.beero.ui.base.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by thuc.phan on 8/14/2015.
 */
public class MapFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private final float ZOOM_LEVEL = 11.5f;

    public static MapFragment makeInstance() {
        return new MapFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mGoogleMap = mMapView.getMap();
        mMapView.getMapAsync(this);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.setMyLocationEnabled(true);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        mGoogleMap.setOnMapLongClickListener(this);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), ZOOM_LEVEL));
        googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter(mActivity));
    }
/*
    @Override
    public void onMapClick(LatLng latLng) {

        Marker newMarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .snippet(latLng.toString()));
        newMarker.setTitle(newMarker.getId());
    }*/

    @Override
    public void onMapLongClick(LatLng latLng) {
        Marker newMarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .snippet(latLng.toString()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        newMarker.setTitle(newMarker.getId());
    }
}
