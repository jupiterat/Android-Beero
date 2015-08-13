package com.au.beero.beero.utility;

import android.content.Context;

import com.au.beero.beero.model.response.ResponseBrand;
import com.au.beero.beero.model.response.ResponseSearch;
import com.au.beero.beero.request.BrandRequest;
import com.au.beero.beero.request.SearchRequest;
import com.au.beero.beero.task.BrandTask;
import com.au.beero.beero.task.SearchTask;
import com.framework.network.request.AbstractHttpRequest;
import com.framework.network.task.IDataEventHandler;
import com.framework.utility.CommonMethod;

/**
 * Created by jupiter.at@gmail.com on 8/13/2015.
 */
public class ApiUtility {
    public static void loadBrands(Context ctx, IDataEventHandler<ResponseBrand> callback,BrandRequest request) {
        BrandTask task = new BrandTask(ctx,callback,request);
        CommonMethod.executeAsyTask(task);
    }

    public static void search(Context ctx, IDataEventHandler<ResponseSearch> callback,SearchRequest request) {
        SearchTask task = new SearchTask(ctx, callback, request);
        CommonMethod.executeAsyTask(task);
    }
}
