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
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

public class GridLayoutActivity extends Activity {

	private GridLayout gridLayout;
	private ScrollView sV;
	public static Bitmap bm;
	private Context mContext;
	int height;
	int width;
	int index = 0;
	static int statusCode;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		try {
			mContext = this;
			GetRequest.url = getResources().getString(R.string.urlGetImageUrls);
			GetRequest.fullImageUrl = getResources().getString(
					R.string.urlGetFullImageUrl);
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			width = size.x;
			height = size.y;
			sV = new ScrollView(this);
			sV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			gridLayout = new GridLayout(GridLayoutActivity.this);
			gridLayout.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

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
			// String image_url =
			// "http://api.androidhive.info/images/sample.jpg";
			ImageView[] iV = new ImageView[count];
			for (int i = 0; i < count; i++) {
				try {
					if (jsonReceived.getString("" + i) != null) {
						String imageUrl = jsonReceived.getString("" + i);
						iV[i] = new ImageView(this);
						iV[i].setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						Drawable drawable = LoadImageFromWebOperations(imageUrl);
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

						// Toast.makeText(getBaseContext(), "Clicked"+v.getId(),
						// Toast.LENGTH_SHORT).show();

						try {
							String imageUrl = saveJson.getString("" + v.getId());
							// Drawable drawable =
							// LoadFullImageFromWebOperations("http://192.168.1.36:9000/assets/images/FullImages/"
							// + imageUrl + ".png");
							GetRequest.imageUrl = imageUrl;
							imageUrl = GetRequest.getFullImageUrlRequest();
							//Drawable drawable = LoadFullImageFromWebOperations(imageUrl);
							sV.removeAllViews();
							ImageView showImage = new ImageView(mContext);
							showImage.setLayoutParams(new LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT));
							showImage.setImageBitmap(LoadFullImageFromWebOperations(imageUrl));
							//showImage.setImageDrawable(drawable);
							sV.addView(showImage);
							index = 1;
						} catch (JSONException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							if (statusCode == 401) {
								// Log.i( "in if status code 401",
								// "reaching here");
								Toast.makeText(mContext,
										"You have been logged out",
										Toast.LENGTH_SHORT).show();
								Intent launchactivity = new Intent(
										GridLayoutActivity.this,
										LoginActivity.class);
								startActivity(launchactivity);
								finish();
							}
						}
					}
				});
			}

			// TODO Auto-generated method stub
		} catch (Exception e) {
			Log.i("in grid layout activity",
					"exception occured " + e.getMessage());
		} finally {
			if (statusCode == 401) {
				// Log.i( "in if status code 401", "reaching here");
				/*
				 * Toast.makeText(mContext, "You have been logged out",
				 * Toast.LENGTH_SHORT).show(); Intent launchactivity = new
				 * Intent( GridLayoutActivity.this, LoginActivity.class);
				 * startActivity(launchactivity);
				 */
				finish();
			}
		}
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
			// return d;
		} catch (Exception e) {
			Log.i("in grid layout activity", "Exc=" + e);
			return null;
		}
	}

	private Bitmap LoadFullImageFromWebOperations(String url) {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			InputStream is = (InputStream) new URL(url).getContent();
			// Drawable d = Drawable.createFromStream(is, "src name");
			// Drawable d = Drawable.createFromResourceStream(null, null, is,
			// "src name");
			// Drawable d = Drawable.createFromResourceStream(0, 0, is,
			// "src name", 0);
			// GridLayoutActivity gLA = new GridLayoutActivity();
			// Drawable d = new BitmapDrawable();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inTargetDensity = 0;
			Bitmap b = BitmapFactory.decodeStream(is, null, options);
			// return gLA.resize(d);
			/*
			 * Bitmap b = ((BitmapDrawable) d).getBitmap(); Bitmap bitmapResized
			 * = Bitmap.createScaledBitmap(b, width, height, false);
			 * bitmapRecycle(b);
			 */
			// return new BitmapDrawable(getResources(), bitmapResized);
			return b;
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
		// super.onBackPressed();
		if (index == 0)
			super.onBackPressed();
		else
			;
		sV.removeAllViews();
		sV.addView(gridLayout);
		index = 0;
		setContentView(sV);
	}

}
