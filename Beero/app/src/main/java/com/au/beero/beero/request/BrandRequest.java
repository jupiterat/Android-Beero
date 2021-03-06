package com.au.beero.beero.request;

import com.au.beero.beero.model.response.ResponseBrand;
import com.au.beero.beero.utility.Constants;
import com.framework.network.ResponseError;
import com.framework.network.request.AbstractHttpRequest;
import com.framework.utility.NetworkConstant;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by shintabmt@gmai.com on 8/12/2015.
 */
public class BrandRequest extends AbstractHttpRequest<Void, ResponseBrand> {
    public BrandRequest() {
        super(null, 0);
        setMethodType(NetworkConstant.GET_TYPE);
        Header[] headers = {new BasicHeader(NetworkConstant.CONTENT_TYPE, NetworkConstant.FORM_CONTENT_TYPE)};
        setHeaders(headers);
    }

    @Override
    public String getTargetUrl() {
        return String.format("%s%s", Constants.SERVER_API_PATH, Constants.SERVER_PATH.BRANDS);
    }

    @Override
    protected String getKeySaveLastTimeUpdate() {
        return null;
    }

    @Override
    protected ResponseBrand readJSON(JSONObject jsonObject) throws JSONException, IOException {
        if (jsonObject != null) {
            ResponseError respondError = new ResponseError();
            respondError.setStatusCode(jsonObject.getString(Constants.SERVER_RES_KEY.RES_STATUS));
            if (respondError.getStatusCode() != null && respondError.getStatusCode().equalsIgnoreCase(Constants.SERVER_RES_KEY.RES_OK)) {
                ResponseBrand responseBrand = new ResponseBrand(jsonObject.getJSONObject(Constants.SERVER_RES_KEY.RES_RESULTS));
                return responseBrand;
            } else {
                String error = jsonObject.getString(Constants.SERVER_RES_KEY.RES_REASON);
                respondError.setMessage(error);
                setResponseError(respondError);
            }
        }
        return null;
    }
}
