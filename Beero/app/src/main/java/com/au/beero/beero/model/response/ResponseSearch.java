package com.au.beero.beero.model.response;

import com.au.beero.beero.model.SearchResult;

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
                    SearchResult searchResult = null;
                    if (jsonObject.get(key) instanceof JSONObject) {
                        JSONObject object = (JSONObject) jsonObject.get(key);
                        searchResult = new SearchResult(object);
                    } else {
                        searchResult = new SearchResult();
                    }
                    searchResult.setId(key);
                    searchResults.add(searchResult);
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
