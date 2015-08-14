package com.au.beero.beero.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.au.beero.beero.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by thuc.phan on 8/14/2015.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View myContentsView;

    public MyInfoWindowAdapter(Context context) {
        myContentsView = LayoutInflater.from(context).inflate(R.layout.custom_info_window_item_layout, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView view = (TextView) myContentsView.findViewById(R.id.name);
        view.setText("YOLO");
        return myContentsView;
    }
}
