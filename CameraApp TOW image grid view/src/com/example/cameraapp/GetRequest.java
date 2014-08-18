package com.example.cameraapp;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.net.ParseException;
import android.os.StrictMode;
import android.util.Log;

public class GetRequest {

	static String url = "http://192.168.1.36:9000/getImageIds";
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
		try {
			responseText = EntityUtils.toString(httpResponse.getEntity());
			Log.i("status line in post request "," "+httpResponse.getStatusLine());
		} catch (ParseException e) {
			e.printStackTrace();
			Log.i("Parse Exception", e + "");
		}
		
		JSONObject jsonResponse = new JSONObject(responseText);
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
		Log.i("in get request", responseText);
		return jsonResponse;
		//Log.i("response text", "" + responseText);
	}
}
