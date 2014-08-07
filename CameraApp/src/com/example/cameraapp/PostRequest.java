package com.example.cameraapp;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.net.ParseException;
import android.os.StrictMode;
import android.util.Log;

public class PostRequest {

	static String emailId;
	static String password;
	static String url;
	static int id;

	@SuppressLint("NewApi")
	public boolean postRequest() throws JSONException, ClientProtocolException,
			IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		Log.i("in post request", "in post request");
		JSONObject json = new JSONObject();
		json.put("email", emailId);
		json.put("password", password);
		Log.i("email id", "" + emailId);
		Log.i("password", "" + password);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppostreq = new HttpPost(url);

		StringEntity jsonString = new StringEntity(json.toString());
		jsonString.setContentType("application/json;charset=UTF-8");
		httppostreq.setEntity(jsonString);
		HttpResponse httpresponse = httpclient.execute(httppostreq);
		String responseText = null;
		try {
			responseText = EntityUtils.toString(httpresponse.getEntity());
		} catch (ParseException e) {
			e.printStackTrace();
			Log.i("Parse Exception", e + "");
		}

		Log.i("response text", "" + responseText);
		JSONObject jsonResponse = new JSONObject(responseText);

		boolean state = jsonResponse.getBoolean("state");
		if (id == 1)
			LoginActivity.message = jsonResponse.getString("message");
		else if (id == 2)
			MainActivity.message = jsonResponse.getString("message");

		// Log.i("in postrequest "+state ,jsonResponse.getString("message"));
		return state;
	}
}
