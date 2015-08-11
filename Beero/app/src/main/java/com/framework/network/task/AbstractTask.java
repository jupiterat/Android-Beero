package com.framework.network.task;

import java.util.concurrent.atomic.AtomicBoolean;

import com.framework.network.request.AbstractHttpRequest;

import android.content.Context;
import android.os.AsyncTask;


public abstract class AbstractTask<TParams, TProgress, TResult> extends
		AsyncTask<TParams, TProgress, TResult> {

	/** use to notify the result to UI. */
	protected IDataEventHandler<TResult> mDataEventHandler;
	/** Interface of global information */
	protected Context mContext;
	/**
	 * The object store information to request to server and create objects from
	 * the response
	 */
	protected AbstractHttpRequest<TParams, TResult> mRequest = null;

	public AbstractTask(Context context,
			IDataEventHandler<TResult> dataEventHandler,
			AbstractHttpRequest<TParams, TResult> request) {
		mDataEventHandler = dataEventHandler;
		mContext = context;
		mRequest = request;
	}

	/**
	 * true: if want to cancel this thread
	 */
	protected final AtomicBoolean mShouldCancel = new AtomicBoolean(false);

	/**
	 * Check this thread is cancel or not
	 * 
	 * @return true if the thread is canceled
	 */
	public boolean getShouldCancel() {
		return mShouldCancel.get();
	}

	/**
	 * Used to cancel the thread.
	 */
	public void setShouldCancel(boolean shouldCancel) {
		this.mShouldCancel.set(shouldCancel);
	}

	@Override
	protected void onPostExecute(TResult result) {
		if (mDataEventHandler != null) {
			if (mRequest != null) {
				mDataEventHandler.onNotifyData(result, mRequest);
			}
		}
		super.onPostExecute(result);
	}
}
