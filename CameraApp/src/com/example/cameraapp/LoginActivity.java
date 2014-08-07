package com.example.cameraapp;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText emailId;
	private EditText password;
	private Button loginButton;
	private Button registerButton;
	private Context mContext;
	public static String message;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		// TODO Auto-generated method stub

		mContext = this;
		emailId = (EditText) findViewById(R.id.emailId);
		password = (EditText) findViewById(R.id.password);
		loginButton = (Button) findViewById(R.id.loginButton);
		registerButton = (Button) findViewById(R.id.registerButton);
		
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PostRequest probj = new PostRequest();
				PostRequest.emailId = emailId.getText().toString();
				PostRequest.password = password.getText().toString();
				//PostRequest.password = password.getText().toString();
				//figure out how to get ip programmatically
				PostRequest.url = getResources().getString(R.string.urlLogin);
				PostRequest.id = 1;
				Log.i("email"+emailId,"password"+password);
				try {
					if (probj.postRequest()){
						Intent launchactivity = new Intent(LoginActivity.this,
								TakePicture.class);
						startActivity(launchactivity);
						finish();
					}
					else
						Toast.makeText(mContext, message,
								Toast.LENGTH_SHORT).show();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent launchactivity = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(launchactivity);
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent launchactivity = new Intent(LoginActivity.this,
				MainActivity.class);
		startActivity(launchactivity);
		this.finish();
	}
	
}

