package com.example.cameraapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class UploadImage extends Activity{

	int serverResponseCode = 0;
	//ProgressDialog dialog = null;
	static String fileName;
	static byte[] data;
	static String url;
	static File file;
	public void executeMultipartPost() throws Exception {

		try {

			//ByteArrayOutputStream bos = new ByteArrayOutputStream();
			//ByteArrayOutputStream jsdh = new ByteArrayOutputStream();
			//byte[] data = bos.toByteArray();

			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpPost postRequest = new HttpPost(url);
			
			//JSONObject json = new JSONObject();
			//json.put("picture", data);
			//StringEntity jsonString = new StringEntity(json.toString());
			//jsonString.setContentType("application/json;charset=UTF-8");
			//postRequest.setEntity(jsonString);
			//ByteArrayBody bab = new ByteArrayBody(data, fileName);
			ContentBody cbFile = new FileBody(file);

			MultipartEntity reqEntity = new MultipartEntity(

			HttpMultipartMode.BROWSER_COMPATIBLE);

			reqEntity.addPart("picture", cbFile);

			postRequest.setEntity(reqEntity);
			int timeoutConnection = 60000;
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			int timeoutSocket = 60000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);

			HttpResponse response = httpClient.execute(postRequest);

			BufferedReader reader = new BufferedReader(new InputStreamReader(

			response.getEntity().getContent(), "UTF-8"));

			String sResponse;

			StringBuilder s = new StringBuilder();
			
			while ((sResponse = reader.readLine()) != null) {

				s = s.append(sResponse);

			}
			Log.i("inside upload image",""+s.toString());
			//System.out.println("Response: " + s);

		} catch (Exception e) {

			// handle exception here
			e.printStackTrace();

			 Log.i(e.getClass().getName(), e.getMessage());

		}

	}
}