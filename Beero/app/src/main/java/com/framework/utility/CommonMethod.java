package com.framework.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.au.beero.beero.R;
import com.framework.exception.NoNetworkException;
import com.framework.exception.NotFoundException;
import com.framework.exception.UnexpectedException;


public class CommonMethod {
	public static final int BUFFER_SIZE = 1024;
	public static final String UTF8 = "UTF-8";
	public static final String TAG = CommonMethod.class.getSimpleName();
	public static void openUrl(Activity activity, String url){
		
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		activity.startActivity(browserIntent);
	}
	
	/**
	 * Check ULR is valid or Not
	 */
	public static boolean isValidURL(String url) {
		boolean isValid = false;
		try {
			URL u = new URL(url);
			if (u != null) {
				isValid = true;
			}
		} catch (MalformedURLException e) {
			// is not valid
		}
		return isValid;
	}

	public static String getFileName(Context context, String fileName) {
		if (context == null || TextUtils.isEmpty(fileName)) {
			return "";
		}
		String name = "";
		if (!TextUtils.isEmpty(fileName)) {
			name = context.getString(R.string.file_name_string);
			name = NetworkConstant.SPACE_CHARACTER + name;
		}
		return name;
	}
	
	/**
	 * get header for this app.
	 * 
	 * @return
	 */
	public static Header[] getHeader() {
		Header[] headers = {
				new BasicHeader(NetworkConstant.CONTENT_TYPE,
						NetworkConstant.XML_CONTENT_TYPE) };
		return headers;
	}
	
	
	/**
	 * create list of params based on an object that keeps information for
	 * requesting.
	 * 
	 * @param requestObject
	 *            keeps information for requesting.
	 * @return list of params.
	 */
	public static List<NameValuePair> getParams(Object requestObject) {
		if (requestObject == null) {
			return null;
		}
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Class<?> objectClass = requestObject.getClass();
		while (objectClass != null) {
			Field[] fields = objectClass.getDeclaredFields();
			for (Field field : fields) {
				try {
					boolean isAccess = field.isAccessible();
					if (!isAccess) {
						field.setAccessible(true);
					}
					if (!TextUtils.equals(field.getName(), "serialVersionUID")) {
						Object value = field.get(requestObject);
						if (value != null) {
							String valueStr = value.toString();
							if (!TextUtils.isEmpty(valueStr)) {
								params.add(new BasicNameValuePair(field
										.getName(), valueStr));
							}
						}
					}

					field.setAccessible(isAccess);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			objectClass = objectClass.getSuperclass();
		}

		return params;
	}
	
	public static MultipartEntity createMultipartEntity(Object requestObject){
		if (requestObject == null) {
			return null;
		}
		MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"Boundary+***************", null);
		Class<?> objectClass = requestObject.getClass();
		ArrayList<Class<?>> arrayClass = new ArrayList<Class<?>>();
		
		while (objectClass != null) {
			arrayClass.add(objectClass);
			
			objectClass = objectClass.getSuperclass();
		}
		
		for(int i = arrayClass.size() -1; i >=0; i--){
			objectClass = arrayClass.get(i);
			
			Field[] fields = objectClass.getDeclaredFields();
			for (int j = fields.length -1; j >=0; j--) {
				Field field = fields[j];
				try {
					boolean isAccess = field.isAccessible();
					if (!isAccess) {
						field.setAccessible(true);
					}
					if(TextUtils.equals(field.getName(), "photo")){
						Object value = field.get(requestObject);
						if(value != null)
							reqEntity.addPart(field.getName(), (ByteArrayBody)value);
					/*} else if(requestObject instanceof GameCreateObject && TextUtils.equals(field.getName(), "players")){
						String players = ((GameCreateObject) requestObject).getPlayers();
						
						if(players != null){
							players = players.trim();
							
							String []playerIds = players.split(",");
							if(playerIds != null){
								for (int pIndex = 0; pIndex<playerIds.length; pIndex++){
									reqEntity.addPart("players[]", new StringBody(playerIds[pIndex]));
								}
							}
						}*/
					}else if (!TextUtils.equals(field.getName(), "serialVersionUID")) {
						Object value = field.get(requestObject);
						if (value != null) {
							String valueStr = value.toString();
							if (!TextUtils.isEmpty(valueStr)) {
								reqEntity.addPart(new FormBodyPart(field.getName(), new StringBody(valueStr)));
							}
						}
					}

					field.setAccessible(isAccess);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
		return reqEntity;
	}
	
	/**
	 * Convert InputStream to String.
	 * 
	 * @param is
	 *            the stream wants to convert data to string.
	 * @return the string is converted from specified stream
	 * @throws UnexpectedException
	 *             throw when there is any exception in read stream
	 */
	public static String convertStreamToString(InputStream is)
			throws UnexpectedException {
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[NetworkConstant.BUFFER_SIZE];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						NetworkConstant.UTF8));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} catch (IOException e) {
				throw new UnexpectedException();
			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					throw new UnexpectedException();
				}
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * execute an asyntask. From android 3.0, we have to start asyntask by
	 * calling executeOnExcecutor. If we call execute, list of asyntasks will be
	 * run sequentially
	 */
	@SuppressLint("NewApi")
	public static <T> void executeAsyTask(AsyncTask<T, ?, ?> task, T... params) {
		if (Build.VERSION.SDK_INT >= 11) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		} else {
			task.execute(params);
		}
	}
	
	public static void saveProfileItem(String referenceName, String value, String key, Context context){
		Context applicationContext = context.getApplicationContext();
		SharedPreferences sharePre = applicationContext.getSharedPreferences(referenceName, Context.MODE_PRIVATE);
		Editor edit = sharePre.edit();
		edit.putString(applicationContext.getPackageName() + key, value!=null?value:"");
		edit.commit();
	}
	
	public static String getValueOfKeyLogin(String referenceName,Context context, String key) {
		if (context == null) return "";
		Context applicationContext = context.getApplicationContext();
		SharedPreferences sharePre = applicationContext.getSharedPreferences(referenceName, Context.MODE_PRIVATE);
		return sharePre.getString(applicationContext.getPackageName() + key, "");
	}
	
	
	public static String encodeParamater(String paramater) {

		try {
			return URLEncoder.encode(paramater, CommonMethod.UTF8);
		} catch (Exception e) {
		}
		return paramater;
	}
	
	/**
	 * Cache image from server to internal memory.
	 * 
	 * @param context
	 *            the current context.
	 * @param url
	 *            url of image wants to cache
	 * @return the path of image if it is cached successfully
	 * @throws ConnectException
	 *             throw when there is any network connect exception
	 * @throws NotFoundException
	 * @throws NoNetworkException
	 * @throws
	 */
	public static String cachePublicImage(final Context context,
			final String url, String userId, String password)
			throws NoNetworkException, NotFoundException, ConnectException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
		// CommonUtils.logInfo(TAG, "method [savePrivateImage] start");
		if (!CommonMethod.isValidURL(url)) {
			return null;
		}
		InputStream is = null;
		FileOutputStream fos = null;
		File imageFile = null;
		String filePath = "";
		File file = new File(getCachDir(context));
		if (file != null && !file.exists()) {
			file.mkdirs();
		}

		// Convert specific link to path to save in external memory
		filePath = generatePublicLocalPath(url, context);

		if (filePath == null) {
			return null;
		}
		try {
			imageFile = new File(filePath);
			// If image file doesn't exist in SDCard, load it from server and
			// write to SDCard
			if ((imageFile != null && !imageFile.exists())
					|| imageFile.length() == 0) {
				/**
				 * When image url starts with https, use DefaultHttpClient to
				 * get
				 */
				// is = ServerConnectionUtils.getStream(url);
				NetworkInfo networkInfo = ((ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE))
						.getActiveNetworkInfo();
				if (networkInfo != null) {
					// if (url.startsWith("https")) {
					// // is = ServerConnectionUtils.getStreamHttps(url);
					// is = ServerConnectionUtils.performGet(url, userId,
					// password, null);
					// } else if (url.startsWith("http")) {
					// is = ServerConnectionUtils.getStreamHttp(url);
					// }
					Log.d(TAG, "start load url = " + url);
					HttpResponse httpResponse = ServerConnectionUtils
							.performGet(url, userId, password, null);
					if (httpResponse != null) {
						is = httpResponse.getEntity().getContent();
					}

				} else {
					throw new NoNetworkException();
				}
				if (is == null) {

					throw new NotFoundException();

				}
				// Write by output stream:
				fos = new FileOutputStream(imageFile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int byteRead = 0;
				boolean isCanWrite = false;
				while (byteRead >= 0) {
					byteRead = is.read(buffer, 0, BUFFER_SIZE);
					if (byteRead > 0) {
						fos.write(buffer, 0, byteRead);
						isCanWrite = true;
					}
				}
				if (!isCanWrite) {
					fos.close();
					fos = null;
					deleteFile(filePath);
					filePath = null;
				}
				Log.d(TAG, "finish load successful url = " + url);
			}
			// }
		} catch (IOException e) {
			throw new ConnectException();
		} catch (OutOfMemoryError e) {
			System.gc();
			Runtime.getRuntime().gc();
			return null;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				// Unimportant exception
				Log.e(TAG, e.getMessage());
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// Unimportant exception
				Log.d(TAG, e.getMessage());
			}
		}
		// CommonUtils.logInfo(TAG, "method [savePrivateImage] end");
		return filePath;
	}
	
	public static String generatePublicLocalPath(String url, Context context) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		return getCachDir(context) + "/" + url.hashCode();
	}
	
	/**
	 * Delete inputed file.
	 * 
	 * @param path
	 *            String
	 */
	public static void deleteFile(String path) {
		if (TextUtils.isEmpty(path) == false) {
			File file = new File(path);
			if (file.exists()) {
				if (!file.delete()) {
					Log.e(TAG, "[deleteFile] Can't delete file!");
				}
			}
		}
	}
	public static String getCachDir(Context context) {
		File cacheDir;
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), context.getString(R.string.app_name));
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();

		if (cacheDir != null)
			return cacheDir.getAbsolutePath();
		else
			return "/";
	}
}
