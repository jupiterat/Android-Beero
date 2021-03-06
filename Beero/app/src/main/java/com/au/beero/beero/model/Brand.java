package com.au.beero.beero.model;

import com.au.beero.beero.utility.Constants;

import org.json.JSONObject;

/**
 * Created by thuc.phan on 8/12/2015.
 */
public class Brand extends BaseModel implements Comparable<Brand>{
    private String name;
    private String position;
    private boolean isSelected = false;



    private String url = "";

    public Brand() {

    }

    public Brand(JSONObject jsonObject) {
        super(jsonObject);
        try {
            setName(jsonObject.getString(Constants.SERVER_RES_KEY.RES_NAME));
        }catch (Exception e){

        }
        try {
            setPosition(jsonObject.getString(Constants.SERVER_RES_KEY.RES_POSITION));
        }catch (Exception e){

        }
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int compareTo(Brand brand) {
        int current = 0;
        int newId = 0;
        try {
            current = Integer.parseInt(this.getPosition());
            newId = Integer.parseInt(brand.getPosition());
        } catch(NumberFormatException ex) {

        }
        if(current > newId) {
            return 1;
        }
        if(current < newId) {
            return -1;
        }
        return 0;
    }
}
