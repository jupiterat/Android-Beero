package com.au.beero.beero.model.response;

import com.au.beero.beero.model.Brand;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by shintabmt@gmai.com on 8/12/2015.
 */
public class BrandResponse {
    List<Brand> brands;

    public BrandResponse() {
    }

    public BrandResponse(JSONObject jsonObject) {
        Iterator<?> keys = jsonObject.keys();
        List<Brand> brands = new ArrayList<>();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            try {
                if (jsonObject.get(key) instanceof JSONObject) {
                    JSONObject object = (JSONObject) jsonObject.get(key);
                    Brand brand = new Brand(object);
                    brand.setId(key);
                    brands.add(brand);
                }
            } catch (Exception e) {

            }
        }
        setBrands(brands);
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
