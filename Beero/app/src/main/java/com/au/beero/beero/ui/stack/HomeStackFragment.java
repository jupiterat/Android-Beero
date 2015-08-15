package com.au.beero.beero.ui.stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.au.beero.beero.manager.BeeroLocationManager;
import com.au.beero.beero.model.LosingDeal;
import com.au.beero.beero.ui.fragments.HomeFragment;
import com.au.beero.beero.ui.fragments.MapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jupiter.at@gmail.com
 */
public class HomeStackFragment extends StackFragment {
    int mStackLevel = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mStackLevel = savedInstanceState.getInt("level");
        }
    }

    @Override
    protected Fragment addInitFragment() {
        /*ArrayList<LosingDeal> losingDeals = new ArrayList<>();
        LosingDeal deal = new LosingDeal();
        deal.setLat(String.valueOf(BeeroLocationManager.LOCATIONMANAGER_DEFAULT_LOCATION_LATITUDE));
        deal.setLng(String.valueOf(BeeroLocationManager.LOCATIONMANAGER_DEFAULT_LOCATION_LONGITUDE));
        deal.setPricePerLitre("$223.3");
        deal.setStoreName("InfoNam");
        losingDeals.add(deal);
        LosingDeal deal2 = new LosingDeal();
        deal2.setLat(String.valueOf(BeeroLocationManager.LOCATIONMANAGER_DEFAULT_LOCATION_LATITUDE - 0.01));
        deal2.setLng(String.valueOf(BeeroLocationManager.LOCATIONMANAGER_DEFAULT_LOCATION_LONGITUDE - 0.01));
        deal2.setPricePerLitre("$21.3");
        deal2.setStoreName("InfoNam2");
        losingDeals.add(deal2);
        return MapFragment.makeInstance(losingDeals);*/
        return HomeFragment.makeInstance();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", mStackLevel);
    }

}
