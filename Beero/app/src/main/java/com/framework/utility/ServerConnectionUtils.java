package com.framework.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.framework.exception.NotFoundException;

/**
 * Define common method used to connect with server
 * 
 */
public class ServerConnectionUtils {
	public static DefaultHttpClient getHttpsClient() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme(NetworkConstant.CONNECT_SCHEME_HTTP,
				PlainSocketFactory.getSocketFactory(),
				NetworkConstant.CONNECT_PORT_80));
		schemeRegistry.register(new Scheme(
				NetworkConstant.CONNECT_SCHEME_HTTPS, PlainSocketFactory
						.getSocketFactory(), NetworkConstant.CONNECT_PORT_443));
		HttpParams params = new BasicHttpParams();
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
				params, schemeRegistry);

		DefaultHttpClient client = new DefaultHttpClient(cm, null);

		// Set value
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		client.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET, HTTP.UTF_8);
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT,
				NetworkConstant.CONNECT_TIME_OUT);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				NetworkConstant.SOCKET_TIME_OUT);
		return client;

	}

	public static HttpResponse getStreamHttps(String urlString)
			throws ConnectException, ConnectTimeoutException,
			NotFoundException, ConnectTimeoutException, HttpResponseException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		DefaultHttpClient client = getHttpsClient();
		HttpGet method = new HttpGet(urlString);
		HttpResponse response = execute(client, method);
		return response;
	}

	public static String getStringContentHttp(String urlString)
			throws ConnectException, ConnectTimeoutException {
		String response = "";
		InputStream resp = getStreamHttp(urlString);
		if (resp != null) {
			InputStreamReader is = new InputStreamReader(resp);
			BufferedReader br = new BufferedReader(is);
			String read;
			try {
				StringBuffer sb = new StringBuffer("");
				while ((read = br.readLine()) != null) {
					sb.append(read + "\n");
				}
				response = sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public static InputStream getStreamHttp(final String urlString)
			throws ConnectException, ConnectTimeoutException {
		InputStream in = null;
		URL url;
		URLConnection conn;
		try {
			url = new URL(urlString);
			conn = url.openConnection();
			conn.setDefaultUseCaches(true);
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(true);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod(NetworkConstant.GET);
			httpConn.setReadTimeout(NetworkConstant.CONNECT_TIME_OUT);
			httpConn.setConnectTimeout(NetworkConstant.CONNECT_TIME_OUT);
			httpConn.connect();

			int code = httpConn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (ConnectTimeoutException e) {
			throw e;
		} catch (IOException e) {
			throw new ConnectException();
		}
		return in;
	}

	public static HttpResponse performPost(
			String url, final Header[] headers,
			final MultipartEntity reqEntity, final int requestType)
			throws ConnectException, ConnectTimeoutException,
			NotFoundException, ConnectTimeoutException, HttpResponseException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		HttpResponse response = null;
		DefaultHttpClient client = getHttpsClient();
		if (requestType == NetworkConstant.POST_TYPE) {
			HttpPost method = new HttpPost(url);
			if (reqEntity != null ) {
				try {
					method.setEntity(reqEntity);
				} catch (Exception e) {
					// Unimportance exception
					e.getMessage();
				}
			}

			if (headers != null && headers.length > 0) {
				method.setHeaders(headers);
			}
			response = execute(client, method);
		} else if (requestType == NetworkConstant.GET_TYPE) {
			HttpGet method = new HttpGet(url);
			if (headers != null && headers.length > 0) {
				method.setHeaders(headers);
			}
			response = execute(client, method);
		} else if (requestType == NetworkConstant.PUT_TYPE) {
			// If request is "PUT"
			HttpPut method = new HttpPut(url);
			if (headers != null && headers.length > 0) {
				method.setHeaders(headers);
			}
			response = execute(client, method);
		} else if (requestType == NetworkConstant.DELETE_TYPE) {
			// If request is "DELETE"
			HttpDelete method = new HttpDelete(url);
			response = execute(client, method);
		}
		return response;
	}
	public static HttpResponse performRequest(
			String url, final Header[] headers,
			final List<NameValuePair> params, final int requestType)
			throws ConnectException, ConnectTimeoutException,
			NotFoundException, ConnectTimeoutException, HttpResponseException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		HttpResponse response = null;
		DefaultHttpClient client = getHttpsClient();
		if (requestType == NetworkConstant.POST_TYPE) {
			HttpPost method = new HttpPost(url);
			if (params != null && params.size() > 0) {
				try {
					method.setEntity(new UrlEncodedFormEntity(params));
				} catch (UnsupportedEncodingException e) {
					e.getMessage();
				}
			}

			if (headers != null && headers.length > 0) {
				method.setHeaders(headers);
			}
			response = execute(client, method);
		} else if (requestType == NetworkConstant.GET_TYPE) {
			HttpGet method = new HttpGet(url);
			
			if (headers != null && headers.length > 0) {
				method.setHeaders(headers);
			}
			response = execute(client, method);
		} else if (requestType == NetworkConstant.PUT_TYPE) {
			// If request is "PUT"
			HttpPut method = new HttpPut(url);
			if (headers != null && headers.length > 0) {
				method.setHeaders(headers);
			}
			response = execute(client, method);
		} else if (requestType == NetworkConstant.DELETE_TYPE) {
			// If request is "DELETE"
			HttpDelete method = new HttpDelete(url);
			response = execute(client, method);
		}
		return response;
	}
	private static HttpResponse execute(final DefaultHttpClient client,
			final HttpRequestBase method) throws ConnectException,
			ConnectTimeoutException, NotFoundException, HttpResponseException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		HttpResponse response = null;
		try {
			/*if (!method.getURI().toString().contains("ecs.amazonaws.com")&&!method.getURI().toString().contains("ebay")){
				OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Constants.SERVER_API_KEY.CONSUMER_KEY, Constants.SERVER_API_KEY.CONSUMER_SERECT);
				consumer.setTokenWithSecret(Constants.SERVER_API_KEY.TOKEN, Constants.SERVER_API_KEY.TOKEN_SECRECT);
				consumer.sign(method);
			}*/
//			OAuthConsumer consumer = new DefaultOAuthConsumer(
//					consumerKey, consumerSecret);

			response = client.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			/*
			 * remove due to hanlde for this server
			 */
			/*if (statusCode != HttpStatus.SC_OK) {
				throw new HttpResponseException(statusCode, null);
			}*/

		} catch (ConnectTimeoutException e) {
			throw e;
		} catch (HttpResponseException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			throw new ConnectException();

		}
		return response;
	}

	/**
	 * Perform an HTTP GET operation with user/pass and headers.
	 * 
	 * @param url
	 *            Specify URL.
	 * @param user
	 *            Specify user.
	 * @param pass
	 *            Specify password.
	 * @param header
	 *            Headers value.
	 * @param isImage
	 *            Will image download
	 * @return HttpResponse
	 * @throws ConnectException
	 *             When connect error is happened, the exceptions are thrown.
	 * @throws ConnectTimeoutException
	 * @throws NotFoundException
	 * @throws OAuthCommunicationException 
	 * @throws OAuthExpectationFailedException 
	 * @throws OAuthMessageSignerException 
	 */
	public static HttpResponse performGet(final String url, final String user,
			final String pass, final Header[] headers) throws ConnectException,
			ConnectTimeoutException, NotFoundException, HttpResponseException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		return performRequest( url, headers, null,
				NetworkConstant.GET_TYPE);
	}

}
