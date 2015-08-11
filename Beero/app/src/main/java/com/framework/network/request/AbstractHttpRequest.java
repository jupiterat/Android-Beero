package com.framework.network.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Calendar;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.framework.exception.UnexpectedException;
import com.framework.network.ResponseError;
import com.framework.network.ResultCode;
import com.framework.utility.CommonMethod;
import com.framework.utility.NetworkConstant;

import android.text.TextUtils;



/**
 * The abstract class support method request to specifics url.
 * 
 * @param <TParams>
 *            Tparams of the url request.
 * @param <TResult>
 *            object return after request.
 */
public abstract class AbstractHttpRequest<TParams, TResult> {

	/** The variable keep method type of the request. */
	protected int mMethodType;
	/** The result code of the request */
	protected int mResultCode = ResultCode.ERROR;
	/** Response Error */
	protected ResponseError mResponseError;
	protected String mStatusCode = NetworkConstant.STATUS_CODE_1;
	protected TParams mRequestParams;
	private long mTimeCache = 0;
	private Header[] headers;
	
	public AbstractHttpRequest(TParams requestParams, long timeCache) {
		mRequestParams = requestParams;
		mTimeCache = timeCache;
	}

	/**
	 * Get the method type of the request
	 */
	public int getMethodType() {
		return mMethodType;
	}

	/**
	 * Get url of the request
	 * 
	 * @return String url
	 */
	public String getTargetUrl() {
		List<NameValuePair> params = createParams();
		if (params != null && params.size() > 0) {
			String paramsString = URLEncodedUtils.format(params, "UTF-8");
			if (!TextUtils.isEmpty(paramsString)) {
				return ("?" + paramsString);
			}
		}
		return "";
	}


	
	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}
	
	public boolean isMultipart(Header[] headers){
		boolean isMultipart = false;
		if(headers != null){
			for(int i = 0;i<headers.length;i++){
				if(headers[i].getName().equalsIgnoreCase(NetworkConstant.CONTENT_TYPE) && headers[i].getValue().startsWith(NetworkConstant.MULTIPART_CONTENT_TYPE)){
					isMultipart = true;
					break;
				}
			}
		}
		
		return isMultipart;
	}
	

	/**
	 * Creates list of parameters for the request.
	 * 
	 * @return list of parameters.
	 */
	public List<NameValuePair> createParams() {
		List<NameValuePair> params = null;
		params = CommonMethod.getParams(mRequestParams);
		return params;
	}
	
	public MultipartEntity createMultiPartEntry(){
		MultipartEntity multiPartEntry = CommonMethod.createMultipartEntity(mRequestParams);
		return multiPartEntry;
	}

	/**
	 * Parse InputStream response to specify object
	 * 
	 * @param isResponse
	 * @return
	 */
	public TResult inputStreamToData(InputStream isRespone)
			throws ConnectException, UnexpectedException,
			XmlPullParserException, IOException, JSONException {
		if (isRespone == null) {
			return null;
		}
		TResult result = null;

		String content = CommonMethod.convertStreamToString(isRespone);

		// insert to database.
		Calendar calendar = Calendar.getInstance();
		long currentTime = calendar.getTimeInMillis();
		// parse data.
		result = parseData(content, currentTime);
		isRespone.close();
		return result;
	}

	/**
	 * get key to save last time update for each API.
	 * 
	 * @return
	 */
	protected abstract String getKeySaveLastTimeUpdate();

	/**
	 * parse data.
	 * 
	 * @param content
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public TResult parseData(String content, long currentTime)
			throws JSONException, IOException {
		TResult result = null;
		try {
			// get result object.
			JSONObject json = new JSONObject(content);
			result = readJSON(json);
		} catch (JSONException ex) {
			// if it do not json object.
			// get json array
			try {
				JSONArray jsonArray = new JSONArray(content);
				result = readJSONArray(jsonArray);
			}catch (JSONException jsonEx){
				result = readString(content);
			}


		}

		// set last time update is current time.
		String key = getKeySaveLastTimeUpdate();
		return result;
	}

	protected TResult readJSONArray(JSONArray jsonArray) throws JSONException,
			IOException {
		return null;
	}
	protected TResult readString(String content){
		return null;
	}

	/**
	 * 
	 * @param parser
	 *            XmlPullPullParser
	 * @return T object
	 * @throws JSONException
	 * @throws IOException
	 */
	protected abstract TResult readJSON(JSONObject jsonObject)
			throws JSONException, IOException;

	/**
	 * @param mMethodType
	 *            the mMethodType to set
	 */
	public void setMethodType(int mMethodType) {
		this.mMethodType = mMethodType;
	}

	/**
	 * @return the mResultCode
	 */
	public int getResultCode() {
		return mResultCode;
	}

	/**
	 * @param mResultCode
	 *            the mResultCode to set
	 */
	public void setResultCode(int mResultCode) {
		this.mResultCode = mResultCode;
	}

	/**
	 * Set error information.
	 * 
	 * @param responseError
	 *            is error information.
	 */
	public void setResponseError(ResponseError responseError) {
		mResponseError = responseError;
	}

	public void setErrorMsgCode(int errorCode) {
		if (mResponseError == null) {
			mResponseError = new ResponseError();
		}
		mResponseError.setId("" + errorCode);
	}

	/**
	 * get status code.
	 * 
	 * @return
	 */
	public String getStatusCode() {
		return mStatusCode;
	}

	/**
	 * Get error information.
	 * 
	 * @return mResponseError.
	 */
	public ResponseError getResponseError() {
		return mResponseError;
	}

	public boolean isIgnoreNotFouncExeption() {
		return false;
	}

	public long getTimeCache() {
		return mTimeCache;
	}
}
