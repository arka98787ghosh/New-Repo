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
import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

public class UploadImage extends Activity{

	int serverResponseCode = 0;
	//ProgressDialog dialog = null;
	//static String fileName;
	//static byte[] data;
	static String url;
	static File file;
	//static String authToken;
	//Context mContext = this;
	//static Long id;
	public boolean executeMultipartPost() throws Exception {

		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			//ByteArrayOutputStream bos = new ByteArrayOutputStream();
			//ByteArrayOutputStream jsdh = new ByteArrayOutputStream();
			//byte[] data = bos.toByteArray();
			//mContext = this;
			//StoreToken st = new StoreToken(mContext);
			//authToken = st.getToken();
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
			//StringEntity jsonString = new StringEntity(MainActivity.authToken);
			//jsonString.setContentType("application/json;charset=UTF-8");
			Log.i("in upload image showing authtoken",""+MainActivity.authToken);
			postRequest.addHeader("authToken", MainActivity.authToken);
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
			Log.i("inside upload image",""+response.getStatusLine().getStatusCode());
			//redirect user to login page on the basis of response received.
			if(response.getStatusLine().getStatusCode() == 401)
				return true;
			else
				return false;

		} catch (Exception e) {

			// handle exception here
			e.printStackTrace();

			 Log.i(e.getClass().getName(), e.getMessage());
			 return false;
		}

	}
}