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
    private Context mContext;
    public MyInfoWindowAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View myContentsView = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window_item_layout, null);
        TextView name = (TextView) myContentsView.findViewById(R.id.info_brand_name);
        TextView price = (TextView) myContentsView.findViewById(R.id.info_brand_price);
        if (marker.getTitle() != null){
            name.setText(marker.getTitle());
        } else {
            return null;
        }
        if (marker.getSnippet() != null){
            price.setText(marker.getSnippet());
        } else {
            return null;
        }
        return myContentsView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
