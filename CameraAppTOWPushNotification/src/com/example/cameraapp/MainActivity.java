package com.example.cameraapp;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private EditText firstName;
	private EditText lastName;
	private EditText emailId;
	private EditText password;
	private EditText confirmPassword;
	private TextView passwordStatus;
	private Button register;
	private Button goToLoginPage;
	private Context mContext;
	public static String messagse;
	static String regId;
	static String authToken;
	
	String SENDER_ID = "338282307442";
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    String regid;

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
		
		final StoreToken st = new StoreToken(mContext);
		
		if (checkPlayServices()) {
	        // If this check succeeds, proceed with normal processing.
	        // Otherwise, prompt user to get valid Play Services APK.
	       //mDisplay.setText("play services available");
	    	
	    	gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(mContext);
            
            if (regid.isEmpty()) {
            	Log.i("in main activity on create","reaching here");
                registerInBackground();
            }else{
            	Log.i("in main activity on create","no; reaching here");
            }
	    	
	    }
		
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
					PostRequest.registrationId = regId;
					//figure out how to get ip programatically
					PostRequest.url =  getResources().getString(R.string.urlRegister);
					
					JSONObject jsonReceived = new JSONObject();
					
					try {
						jsonReceived = PostRequest.postRequest();
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
					
					try {
						if(jsonReceived.getBoolean("state")){
							Toast.makeText(mContext, "you have been succesfully registered", Toast.LENGTH_SHORT).show();
							Intent launchactivity = new Intent(MainActivity.this,
									LoginActivity.class);
							startActivity(launchactivity);
							finish();
						}else{
							Toast.makeText(mContext, jsonReceived.getString("message"),
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					/*
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
					*/
				}else{
					Toast.makeText(mContext, "Check the entered passwords" , Toast.LENGTH_SHORT).show();
				}
				
			}
		});
			
		goToLoginPage.setOnClickListener(new View.OnClickListener() {
			
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				authToken = st.getToken();
				PostRequest.url = getResources().getString(R.string.urlTokenAuthenticate);
				Log.i("in go to login page",""+getResources().getString(R.string.urlTokenAuthenticate));
				try {
					if(authToken != null && PostRequest.tokenPostRequest()){
						//Toast.makeText(mContext, "should go to take picture screen", Toast.LENGTH_SHORT).show();
						Intent launchactivity = new Intent(MainActivity.this,
								TakePicture.class);
						startActivity(launchactivity);
						finish();
					}else{
						Intent launchactivity = new Intent(MainActivity.this,
								LoginActivity.class);
						startActivity(launchactivity);
						finish();
					}
				} catch (JSONException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    checkPlayServices();
	}
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i("In main activity checkPlayServices", "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i("In main activity getRegistrationId", "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i("In main activity getRegistrationId", "App version changed.");
	        return "";
	    }
	    regId = registrationId;
	    Log.i("in main activity get reg id",""+registrationId);
	    return registrationId;
	}
	
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	private void registerInBackground() {
	    new AsyncTask<Void, Void, String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(mContext);
	                }
	                regid = gcm.register(SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	                //sendRegistrationIdToBackend();
	                Log.i("in main activity asynctask",""+regid);
	                regId = regid;
	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the regID - no need to register again.
	                storeRegistrationId(mContext, regid);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }

	        @Override
	        protected void onPostExecute(String msg) {
	            //mDisplay.append(msg + "\n");
	        }

	    }.execute(null, null, null);
	    
	}
	
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i("In main activity store registration id", "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}

}
