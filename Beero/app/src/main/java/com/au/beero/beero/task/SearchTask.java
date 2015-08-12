package com.au.beero.beero.task;

import android.content.Context;

import com.au.beero.beero.model.response.ResponseSearch;
import com.framework.network.request.AbstractHttpRequest;
import com.framework.network.task.IDataEventHandler;
import com.framework.network.task.ServerConnectionTask;

/**
 * Created by shintabmt@gmai.com on 8/12/2015.
 */
public class SearchTask extends ServerConnectionTask<Void, Void, ResponseSearch> {
    public SearchTask(Context context, IDataEventHandler<ResponseSearch> dataEventHandler, AbstractHttpRequest<Void, ResponseSearch> request) {
        super(context, dataEventHandler, request);
    }
}
