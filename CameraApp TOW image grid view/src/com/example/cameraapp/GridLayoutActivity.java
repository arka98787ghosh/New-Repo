package com.example.cameraapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

public class GridLayoutActivity extends Activity {

	private GridLayout gridLayout;
	private ScrollView sV;
	public static Bitmap bm;
	private Context mContext;
	int height;
	int width;
	int index = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mContext = this;
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		sV = new ScrollView(this);
		sV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		gridLayout = new GridLayout(GridLayoutActivity.this);
		gridLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		gridLayout.setVerticalScrollBarEnabled(true);
		// gridLayout.
		gridLayout.setColumnCount(3);
		JSONObject jsonReceived = null;
		int count = 0;
		try {
			jsonReceived = GetRequest.getRequest();
			count = jsonReceived.length();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final JSONObject saveJson = jsonReceived;

		gridLayout.setRowCount(count / 3 + count % 3);
		// gridLayout.canScrollVertically();
		// String image_url = "http://api.androidhive.info/images/sample.jpg";
		ImageView[] iV = new ImageView[count];
		for (int i = 0; i < count; i++) {
			try {
				if (jsonReceived.getString("" + i) != null) {
					String imageId = jsonReceived.getString("" + i);
					iV[i] = new ImageView(this);
					iV[i].setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					Drawable drawable = LoadImageFromWebOperations("http://192.168.1.36:9000/assets/images/ThumbnailImages/"
							+ imageId + ".png");
					iV[i].setImageDrawable(drawable);
					iV[i].setId(i);
					gridLayout.addView(iV[i]);
				} else
					break;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sV.addView(gridLayout);
		index = 0;
		setContentView(sV);
		
		for (int i = 0; i < count; i++) {
			iV[i].setOnClickListener(new View.OnClickListener() {

				// int pos = i;

				public void onClick(View v) {
					// TODO Auto-generated method stub

					//Toast.makeText(getBaseContext(), "Clicked"+v.getId(),
							//Toast.LENGTH_SHORT).show();
					try {
						String imageId = saveJson.getString(""+v.getId());
						Drawable drawable =
								 LoadFullImageFromWebOperations("http://192.168.1.36:9000/assets/images/FullImages/"
								 + imageId + ".png");
						sV.removeAllViews();
						ImageView showImage = new ImageView(mContext);
						showImage.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						showImage.setImageDrawable(drawable);
						sV.addView(showImage);
						index = 1;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// v.getId();
					
				}
			});
		}

		// iV.setImageURI(Uri
		// .parse("http://api.androidhive.info/images/sample.jpg"));
		// iV.setImageBitmap(bm);
		// Drawable drawable =
		// LoadImageFromWebOperations("http://api.androidhive.info/images/sample.jpg");
		// Drawable drawable =
		// LoadImageFromWebOperations("http://192.168.1.36:9000/assets/images/FullImages/0beb60e4-4e47-44b3-8c53-03aefa88f84c.png");
		// Drawable drawable =
		// LoadImageFromWebOperations("file://192.168.1.36:9000/home/arka/22797291-759e-4635-8e04-64337fa70fc8.png");

		// TODO Auto-generated method stub
	}

	private Drawable LoadImageFromWebOperations(String url) {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			// GridLayoutActivity gLA = new GridLayoutActivity();

			// return gLA.resize(d);

			Bitmap b = ((BitmapDrawable) d).getBitmap();
			Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width / 3,
					width / 3, false);
			bitmapRecycle(b);
			return new BitmapDrawable(getResources(), bitmapResized);
		} catch (Exception e) {
			Log.i("in grid layout activity", "Exc=" + e);
			return null;
		}
	}

	private Drawable LoadFullImageFromWebOperations(String url) {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			// GridLayoutActivity gLA = new GridLayoutActivity();

			// return gLA.resize(d);
			/*
			Bitmap b = ((BitmapDrawable) d).getBitmap();
			Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height,
					false);
			bitmapRecycle(b);
			*/
			//return new BitmapDrawable(getResources(), bitmapResized);
			return d;
		} catch (Exception e) {
			Log.i("in grid layout activity", "Exc=" + e);
			return null;
		}
	}

	/*
	 * private Drawable resize(Drawable image) { Bitmap b = ((BitmapDrawable)
	 * image).getBitmap(); Bitmap bitmapResized = Bitmap.createScaledBitmap(b,
	 * 50, 50, false); // bitmapRecycle(b); return new
	 * BitmapDrawable(getResources(), bitmapResized); }
	 */
	public static void bitmapRecycle(Bitmap bitmap) {
		bitmap.recycle();
		bitmap = null;
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		if(index == 0)
			super.onBackPressed();
		else
			;
		sV.removeAllViews();
		sV.addView(gridLayout);
		index = 0;
		setContentView(sV);
	}
	
}
