package com.au.beero.beero.task;

import android.content.Context;

import com.au.beero.beero.model.response.ResponseBrand;
import com.framework.network.request.AbstractHttpRequest;
import com.framework.network.task.IDataEventHandler;
import com.framework.network.task.ServerConnectionTask;

/**
 * Created by shintabmt@gmai.com on 8/12/2015.
 */
public class BrandTask extends ServerConnectionTask<Void, Void, ResponseBrand> {
    public BrandTask(Context context, IDataEventHandler<ResponseBrand> dataEventHandler, AbstractHttpRequest<Void, ResponseBrand> request) {
        super(context, dataEventHandler, request);
    }
}
