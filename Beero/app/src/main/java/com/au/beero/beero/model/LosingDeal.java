package com.au.beero.beero.model;

import com.au.beero.beero.utility.Constants;

import org.json.JSONObject;

/**
 * Created by thuc.phan on 8/12/2015.
 */
public class LosingDeal {
    private String lat;
    private String lng;
    private String pricePerLitre;
    private String storeName;

    public LosingDeal() {
    }
    public LosingDeal(JSONObject jsonObject) {
        if (jsonObject == null){
            return;
        } else {
            try {
                setLat(jsonObject.getString(Constants.SERVER_RES_KEY.RES_LAT));
            }catch (Exception e){

            }
            try {
                setLng(jsonObject.getString(Constants.SERVER_RES_KEY.RES_LNG));
            }catch (Exception e){

            }
            try {
                setPricePerLitre(jsonObject.getString(Constants.SERVER_RES_KEY.RES_PRICE_PER_LITRE));
            }catch (Exception e){

            }
            try {
                setStoreName(jsonObject.getString(Constants.SERVER_RES_KEY.RES_STORE_NAME   ));
            }catch (Exception e){

            }
        }
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPricePerLitre() {
        return pricePerLitre;
    }

    public void setPricePerLitre(String pricePerLitre) {
        this.pricePerLitre = pricePerLitre;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
