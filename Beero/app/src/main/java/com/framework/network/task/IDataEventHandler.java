package com.framework.network.task;

import com.framework.network.request.AbstractHttpRequest;

/**
 * Used to notify to UI result form background
 * @param <T> type of data want to return
 */
public interface IDataEventHandler<TResult> {
	/**
	 * result code: success, fail or network error
	 */
	public void onNotifyData(TResult data, AbstractHttpRequest request);
}
