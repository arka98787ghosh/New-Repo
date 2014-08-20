package com.example.cameraapp;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
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

public class GetRequest {

	static String url;
	static String fullImageUrl;
	static String imageUrl;
	public static byte[] byteArray;

	@SuppressLint("NewApi")
	public static JSONObject getRequest() throws ClientProtocolException, IOException, JSONException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		DefaultHttpClient httpclient = new DefaultHttpClient(); 
		HttpGet httpGetRequest = new HttpGet(url);
		httpGetRequest.addHeader("authToken", MainActivity.authToken);
		HttpResponse httpResponse = httpclient.execute(httpGetRequest);
		String responseText = null;
		GridLayoutActivity.statusCode = httpResponse.getStatusLine().getStatusCode();
		try {
			responseText = EntityUtils.toString(httpResponse.getEntity());
			//Log.i("status line in post request "," "+httpResponse.getStatusLine());
		} catch (ParseException e) {
			e.printStackTrace();
			Log.i("Parse Exception", e + "");
		}
		
		JSONObject jsonResponse = new JSONObject(responseText);
		
		/*
		//JSONObject receivedJson = httpResponse.getEntity().
		//File sdCardDirectory = Environment.getExternalStorageDirectory();
		//File file = new File(sdCardDirectory , "image.png");
		
		//BufferedImage fullImage = ImageIO.read(file);
		//String responseText = null;
		try {
			//responseText = EntityUtils.toString(httpResponse.getEntity());
			
			//String filename = "MyApp/MediaTag/MediaTag-"+objectId+".png";
			//File file = new File(Environment.getExternalStorageDirectory(), filename);
			//FileOutputStream fos;

			//fos = new FileOutputStream(file);
			//httpResponse.getEntity().writeTo(fos);
			//byteArray = EntityUtils.toByteArray(httpResponse.getEntity());
			//GridLayoutActivity.bm = BitmapFactory.decodeByteArray(byteArray , 0, byteArray .length);
			//fos.write(mediaTagBuffer);
			//fos.flush();
			//fos.close();
			Log.i("status line in post request "," "+httpResponse.getStatusLine());
		} catch (ParseException e) {
			e.printStackTrace();
			Log.i("Parse Exception", e + "");
		}
		*/
		
		Log.i("in get request", responseText);
		return jsonResponse;
		//Log.i("response text", "" + responseText);
	}
	
	@SuppressLint("NewApi")
	public static String getFullImageUrlRequest() throws ClientProtocolException, IOException, JSONException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		JSONObject json = new JSONObject();
		json.put( "imageUrl", imageUrl);
		
		DefaultHttpClient httpclient = new DefaultHttpClient(); 
		HttpPost httpGetRequest = new HttpPost(fullImageUrl);
		httpGetRequest.addHeader("authToken", MainActivity.authToken);
		
		StringEntity jsonString = new StringEntity(json.toString());
		jsonString.setContentType("application/json;charset=UTF-8");
		httpGetRequest.setEntity(jsonString);
		
		HttpResponse httpResponse = httpclient.execute(httpGetRequest);
		//String responseText = null;
		
		try {
			//responseText = EntityUtils.toString(httpResponse.getEntity());
			GridLayoutActivity.statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.i("status line in post request "," "+GridLayoutActivity.statusCode);
			if(GridLayoutActivity.statusCode == 401){
				//GridLayoutActivity gLA = new GridLayoutActivity();
				Log.i("status line in post request "," "+GridLayoutActivity.statusCode);
				//GridLayoutActivity.statusCode = statusCode;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			Log.i("Parse Exception", e + "");
		}
		
		//JSONObject jsonResponse = new JSONObject(responseText);
		
		/*
		//JSONObject receivedJson = httpResponse.getEntity().
		//File sdCardDirectory = Environment.getExternalStorageDirectory();
		//File file = new File(sdCardDirectory , "image.png");
		
		//BufferedImage fullImage = ImageIO.read(file);
		//String responseText = null;
		try {
			//responseText = EntityUtils.toString(httpResponse.getEntity());
			
			//String filename = "MyApp/MediaTag/MediaTag-"+objectId+".png";
			//File file = new File(Environment.getExternalStorageDirectory(), filename);
			//FileOutputStream fos;

			//fos = new FileOutputStream(file);
			//httpResponse.getEntity().writeTo(fos);
			//byteArray = EntityUtils.toByteArray(httpResponse.getEntity());
			//GridLayoutActivity.bm = BitmapFactory.decodeByteArray(byteArray , 0, byteArray .length);
			//fos.write(mediaTagBuffer);
			//fos.flush();
			//fos.close();
			Log.i("status line in post request "," "+httpResponse.getStatusLine());
		} catch (ParseException e) {
			e.printStackTrace();
			Log.i("Parse Exception", e + "");
		}
		*/
		String responseText = EntityUtils.toString(httpResponse.getEntity());
		JSONObject jsonResponse = new JSONObject(responseText);
		Log.i("in get request", responseText);
		return jsonResponse.getString("fullImageUrl");
		//Log.i("response text", "" + responseText);
	}
}
