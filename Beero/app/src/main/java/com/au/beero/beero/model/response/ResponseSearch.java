package com.au.beero.beero.model.response;

import com.au.beero.beero.model.Brand;
import com.au.beero.beero.model.LosingDeal;
import com.au.beero.beero.model.SearchResult;
import com.au.beero.beero.model.WiningDeal;
import com.au.beero.beero.utility.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thuc.phan on 8/12/2015.
 */
public class ResponseSearch {
    List<SearchResult>searchResults;
    public ResponseSearch() {
    }

    public ResponseSearch(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        } else {
            Iterator<?> keys = jsonObject.keys();
            List<SearchResult> searchResults = new ArrayList<>();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                try {
                    if (jsonObject.get(key) instanceof JSONObject) {
                        JSONObject object = (JSONObject) jsonObject.get(key);
                        SearchResult searchResult = new SearchResult(object);
                        searchResult.setId(key);
                        searchResults.add(searchResult);
                    }
                } catch (Exception e) {

                }
            }
            setSearchResults(searchResults);
        }
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}
