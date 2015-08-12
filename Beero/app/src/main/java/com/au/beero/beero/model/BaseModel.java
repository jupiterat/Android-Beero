package com.au.beero.beero.model;

import com.au.beero.beero.utility.Constants;

import org.json.JSONObject;

/**
 * Created by shintabmt@gmai.com on 8/12/2015.
 */
public class BaseModel {
    private String id;
    public BaseModel() {
    }
    public BaseModel(JSONObject jsonObject) {
        if (jsonObject == null){
            return;
        } else {
            try {
                setId(jsonObject.getString(Constants.SERVER_RES_KEY.RES_ID));
            }catch (Exception e){

            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
