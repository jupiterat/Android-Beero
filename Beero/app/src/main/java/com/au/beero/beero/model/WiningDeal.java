package com.au.beero.beero.model;

import com.au.beero.beero.utility.Constants;

import org.json.JSONObject;

/**
 * Created by thuc.phan on 8/12/2015.
 */
public class WiningDeal {
    String brandName;
    String qty;
    String containerType;
    String containerSize;
    String price;
    String drivingDistance;
    String drivingTime;
    boolean isExclusive;
    Store store;
    String url;

    public WiningDeal() {
    }

    public WiningDeal(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        } else {
            try {
                setBrandName(jsonObject.getString(Constants.SERVER_RES_KEY.RES_BRAND_NAME));
            } catch (Exception e) {

            }
            try {
                setQty(jsonObject.getString(Constants.SERVER_RES_KEY.RES_QTY));
            } catch (Exception e) {

            }
            try {
                setContainerType(jsonObject.getString(Constants.SERVER_RES_KEY.RES_CONTAINER_TYPE));
            } catch (Exception e) {

            }
            try {
                setContainerSize(jsonObject.getString(Constants.SERVER_RES_KEY.RES_CONTAINER_SIZE));
            } catch (Exception e) {

            }
            try {
                setPrice(jsonObject.getString(Constants.SERVER_RES_KEY.RES_PRICE));
            } catch (Exception e) {

            }
            try {
                setDrivingDistance(jsonObject.getString(Constants.SERVER_RES_KEY.RES_DRIVING_DISTANCE));
            } catch (Exception e) {

            }
            try {
                setDrivingTime(jsonObject.getString(Constants.SERVER_RES_KEY.RES_DRIVING_TIME));
            } catch (Exception e) {

            }
            try {

                boolean exclusive = false;
                if (jsonObject.getString(Constants.SERVER_RES_KEY.RES_IS_EXCLUSIVE).equals("1")) {
                    exclusive = true;
                }
                setIsExclusive(exclusive);
            } catch (Exception e) {

            }
            try {
                setStore(new Store(jsonObject.getJSONObject(Constants.SERVER_RES_KEY.RES_STORE_DETAILS)));
            } catch (Exception e) {

            }
        }
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(String containerSize) {
        this.containerSize = containerSize;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDrivingDistance() {
        return drivingDistance;
    }

    public void setDrivingDistance(String drivingDistance) {
        this.drivingDistance = drivingDistance;
    }

    public String getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(String drivingTime) {
        this.drivingTime = drivingTime;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(boolean isExclusive) {
        this.isExclusive = isExclusive;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
