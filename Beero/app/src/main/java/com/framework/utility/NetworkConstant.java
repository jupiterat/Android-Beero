package com.framework.utility;

public class NetworkConstant {

	
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String STATUS_CODE_OK = "0";
	public static final String STATUS_CODE_1 = "1";
	public static final String STATUS_CODE_2 = "2";
	public static final String STATUS_CODE_3 = "3";
	public static final String STATUS_CODE_9 = "9";
	
	public static final String NEW_LINE_CHARACTER = "\r\n";
	public static final String SPACE_CHARACTER = " ";
	public static final String EMPTY_CHARACTER = "";
	public static final String STROKE_CHARACTER = "-";
	public static final String COMMA_CHARACTER = ",";
	public static final String HTML_TAG_CHARACTER = "\\<.*?\\>";
	public final static String DEFAULT_HTTP = "http://";
	
	
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String XML_CONTENT_TYPE = "application/xml";
	public static final String JSON_CONTENT_TYPE = "application/json";
	public static final String IMAGE_CONTENT_TYPE ="image/jpeg";
	public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
	public static final String MULTIPART_CONTENT_TYPE ="multipart/form-data; boundary=Boundary+***************";
	
	public static final int BUFFER_SIZE = 1024;
	public static final String UTF8 = "UTF-8";
	
	public final static String URL_VISUAL_DESIGN_DEFAULT = "visualdesign.ie";
	public final static String URL_VISUAL_DESIGN = DEFAULT_HTTP + "www."
			+ URL_VISUAL_DESIGN_DEFAULT;
	
	public static final int HTTP_PROXY_PORT = 8080;
	public static final int CONNECT_TIME_OUT = 10000;
	public static final int SOCKET_TIME_OUT = 20000;
	public static final int CONNECT_PORT_80 = 80;
	public static final int CONNECT_PORT_443 = 443;
	public static final String CONNECT_SCHEME_HTTP = "http";
	public static final String CONNECT_SCHEME_HTTPS = "https";
	public static final String GET = "GET";
	public static final String POST = "POST";
	
	
	public static final int POST_TYPE = 1;
	public static final int GET_TYPE = 2;
	public static final int PUT_TYPE = 3;
	public static final int DELETE_TYPE = 4;
	
	
}
