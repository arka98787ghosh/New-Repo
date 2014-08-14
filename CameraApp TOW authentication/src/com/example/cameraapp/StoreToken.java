package com.example.cameraapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class StoreToken {
	Context mContext;
	SharedPreferences mSharedPreferences;
	public StoreToken(Context context){
		mContext = context;
		mSharedPreferences = mContext.getSharedPreferences("StoreToken",Context.MODE_PRIVATE);
	}
	
	public void storeToken(String authToken){
		Editor editor = mSharedPreferences.edit();
		editor.putString("authToken", authToken);
		editor.commit();
	}
	
	public String getToken(){
		return mSharedPreferences.getString("authToken", null);
	}
	
	public void deleteToken(){
		Editor editor = mSharedPreferences.edit();
		editor.putString("authToken", null);
		editor.commit();
	}
}
