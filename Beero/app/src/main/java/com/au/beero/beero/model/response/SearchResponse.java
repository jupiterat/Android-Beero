package com.au.beero.beero.model.response;

import com.au.beero.beero.model.LosingDeal;
import com.au.beero.beero.model.WiningDeal;
import com.au.beero.beero.utility.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuc.phan on 8/12/2015.
 */
public class SearchResponse {
    String id;
    WiningDeal winingDeal;
    List<LosingDeal> losingDeals;

    public SearchResponse() {
    }

    public SearchResponse(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        } else {
            try {
                setId(jsonObject.getString(Constants.SERVER_RES_KEY.RES_ID));
            } catch (Exception e) {

            }
            try {
                setWiningDeal(new WiningDeal(jsonObject.getJSONObject(Constants.SERVER_RES_KEY.RES_WINNING_DEAL)));
            } catch (Exception e) {

            }
            try {
            } catch (Exception e) {

            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WiningDeal getWiningDeal() {
        return winingDeal;
    }

    public void setWiningDeal(WiningDeal winingDeal) {
        this.winingDeal = winingDeal;
    }

    public List<LosingDeal> getLosingDeals() {
        return losingDeals;
    }

    public void setLosingDeals(List<LosingDeal> losingDeals) {
        this.losingDeals = losingDeals;
    }
}
