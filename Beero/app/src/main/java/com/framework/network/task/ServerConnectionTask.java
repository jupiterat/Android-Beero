package com.framework.network.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

import com.framework.exception.UnexpectedException;
import com.framework.network.ResultCode;
import com.framework.network.request.AbstractHttpRequest;
import com.framework.utility.CommonMethod;
import com.framework.utility.ErrorCode;
import com.framework.utility.NetworkUtils;
import com.framework.utility.ServerConnectionUtils;


public class ServerConnectionTask<TParams, TProgress, TResult> extends
		AbstractTask<TParams, TProgress, TResult> {
	private static final String TAG = "ServerConnectionTask";

	public ServerConnectionTask(Context context,
			IDataEventHandler<TResult> dataEventHandler,
			AbstractHttpRequest<TParams, TResult> request) {
		super(context, dataEventHandler, request);
	}

	/**
	 * @throws OAuthCommunicationException 
	 * @throws OAuthExpectationFailedException 
	 * @throws OAuthMessageSignerException 
	 * @throws com.framework.exception.NotFoundException 
	 * @throws com.vad.exception.NotFoundException
	 *             Execute the connection to get data from sever
	 * 
	 * @throws
	 */
	protected HttpResponse executeConnection() throws ConnectException,
			ConnectTimeoutException, NotFoundException, HttpResponseException,
			 com.framework.exception.NotFoundException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		if (mRequest == null) {
			return null;
		}
		HttpResponse response = null;
		if(mRequest.isMultipart(mRequest.getHeaders())){
			response = ServerConnectionUtils.performPost( mRequest.getTargetUrl(),
				mRequest.getHeaders(), mRequest.createMultiPartEntry(),
				mRequest.getMethodType());
		} else {
			response = ServerConnectionUtils.performRequest( mRequest.getTargetUrl(),
				mRequest.getHeaders(), mRequest.createParams(),
				mRequest.getMethodType());
		}
		return response;

	}

	/**
	 * Execute loading data from server
	 * @throws OAuthCommunicationException 
	 * @throws OAuthExpectationFailedException 
	 * @throws OAuthMessageSignerException 
	 */
	public TResult doGetData() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		if (mRequest == null) {
			return null;
		}
		TResult result = null;
		String urlRequest = mRequest.getTargetUrl();
		mRequest.setResultCode(ResultCode.ERROR);

		// LogUtils.logDebug(TAG, "load data from server");
		HttpResponse response = null;
		InputStream inputStream = null;
		// Check network before start download
		if (!NetworkUtils.isConnecting(mContext)) {
			mRequest.setErrorMsgCode(ErrorCode.E_NETWORK_ERROR);
			return null;
		}
		if (!CommonMethod.isValidURL(urlRequest)) {
			mRequest.setErrorMsgCode(ErrorCode.E_UNEXPECTED_ERROR);
			return null;
		}
		try {
			response = executeConnection();
			if (response == null) {
				mRequest.setResultCode(ResultCode.NO_DATA);
				return null;
			}
			inputStream = getStreamFromResponse(response);
			Header headerStatus = response.getFirstHeader("Status");
			if (headerStatus != null) {
				int headerStatusCode = Integer
						.parseInt(headerStatus.getValue());
				if (headerStatusCode == HttpStatus.SC_SERVICE_UNAVAILABLE) {
					mRequest.setResultCode(ResultCode.ERROR);
					return null;
				}
			}
			if (!getShouldCancel() && inputStream != null) {
				result = mRequest.inputStreamToData(inputStream);

				if (mRequest.getResponseError() == null) {
					if (result == null) {
						mRequest.setResultCode(ResultCode.NO_DATA);
					} else {
						mRequest.setResultCode(ResultCode.HAS_DATA);
					}
				}
			}
		} catch (ConnectException e) {
			mRequest.setErrorMsgCode(ErrorCode.E_NETWORK_ERROR);
		} catch (ConnectTimeoutException e) {
			mRequest.setErrorMsgCode(ErrorCode.E_TIME_OUT);
		} catch (NotFoundException e) {
			mRequest.setErrorMsgCode(ErrorCode.E_UNEXPECTED_ERROR);
		} catch (IllegalStateException e) {
			mRequest.setErrorMsgCode(ErrorCode.E_UNEXPECTED_ERROR);
		} catch (HttpResponseException e) {
			if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND
					&& mRequest.isIgnoreNotFouncExeption()) {
				// return no data for this case.
				mRequest.setResultCode(ResultCode.NO_DATA);
			} else if (e.getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE) {
				mRequest.setErrorMsgCode(ErrorCode.SC_SERVICE_UNAVAILABLE);
			} else {
				mRequest.setErrorMsgCode(ErrorCode.E_NETWORK_ERROR);
			}
		} catch (IOException e) {
			mRequest.setErrorMsgCode(ErrorCode.E_FILE_IO_ERROR);
		} catch (UnexpectedException e) {
			mRequest.setErrorMsgCode(ErrorCode.E_UNEXPECTED_ERROR);
		} catch (XmlPullParserException e) {
			if (mRequest.getResponseError() == null) {
				mRequest.setErrorMsgCode(ErrorCode.E_UNEXPECTED_ERROR);
			}
		} catch (JSONException e) {
			if (mRequest.getResponseError() == null) {
				mRequest.setErrorMsgCode(ErrorCode.E_UNEXPECTED_ERROR);
			}
		} catch (com.framework.exception.NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			} catch (IOException e) {
			}
		}
		return result;
	}

	public InputStream getStreamFromResponse(HttpResponse response)
			throws IllegalStateException, IOException {
		return response.getEntity().getContent();
	}

	@Override
	protected TResult doInBackground(TParams... params) {
		TResult result = null;
		try {
			result = doGetData();
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
