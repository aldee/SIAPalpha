package com.parama.android.siapkontraktor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class UserFunctions {
	
	private static final String loginURL = "http://siap-kontraktor.com/android/api/login.php";
	private JSONParser jParser;
	
	public UserFunctions() {
		jParser = new JSONParser();
	}
	
	public JSONObject loginUser(String hostname, String database, String username, String password, DefaultHttpClient httpClient) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "login"));
		params.add(new BasicNameValuePair("hostname", hostname));
		params.add(new BasicNameValuePair("database", database));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jParser.makeHttpRequest(loginURL, "POST", params, httpClient);
		return json;
	}

}
