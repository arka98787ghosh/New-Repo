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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText firstName;
	private EditText lastName;
	private EditText emailId;
	private EditText password;
	private EditText confirmPassword;
	private TextView passwordStatus;
	private Button register;
	private Button goToLoginPage;
	private Context mContext;
	public static String message;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    // TODO Auto-generated method stub
	    mContext = this;
	    firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		emailId = (EditText) findViewById(R.id.emailId);
		password = (EditText) findViewById(R.id.password);
		confirmPassword = (EditText) findViewById(R.id.confirmPassword);
		register = (Button) findViewById(R.id.register);
		goToLoginPage = (Button) findViewById(R.id.goToLoginPage);
		passwordStatus = (TextView) findViewById(R.id.passwordStatus);
		confirmPassword.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(password.getText().toString().equals(confirmPassword.getText().toString()) && password.getText().toString().length()>0)
					passwordStatus.setText("Password matching");
				else
					passwordStatus.setText("Password doesn't match each other, check it!");
			}
			
		});
		
		password.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(password.getText().toString().equals(confirmPassword.getText().toString()) && password.getText().toString().length()>0)
					passwordStatus.setText("Password matching");
				else
					passwordStatus.setText("Password doesn't match each other, check it!");
			}
			
		});
		//Toast.makeText(mContext, "Hi "+firstName.getText().toString(), Toast.LENGTH_SHORT).show();
		
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(password.getText().toString().equals(confirmPassword.getText().toString()) && password.getText().toString().length()>0){
					PostRequest probj = new PostRequest();
					PostRequest.emailId = emailId.getText().toString();
					PostRequest.password = password.getText().toString();
					//figure out how to get ip programmatically
					PostRequest.url =  getResources().getString(R.string.urlRegister);
					PostRequest.id = 2;
					try {
						if(probj.postRequest()){
							Toast.makeText(mContext, "you have been succesfully registered", Toast.LENGTH_SHORT).show();
							Intent launchactivity = new Intent(MainActivity.this,
									LoginActivity.class);
							startActivity(launchactivity);
							finish();
						}	
						else
							Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
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
				}else{
					Toast.makeText(mContext, "Check the entered passwords" , Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		goToLoginPage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent launchactivity = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(launchactivity);
				finish();
			}
		});
		
		//after registration you have to start the login page activity
	}

}
