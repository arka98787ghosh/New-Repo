package com.example.cameraapp;

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MaskActivity extends Activity {

	private Button saveButton;
	@SuppressWarnings("unused")
	private RelativeLayout loadingPanel;
	private ProgressDialog mProgressDialog;
	// private ProgressBar progressBar;
	// private Button effectsButton;
	// public Bitmap croppedBitmap;
	public static byte[] bitData;
	public int finalx, finaly;

	public static float x, y;
	static float x_memory = 100;
	static float x_memory_old = 100;
	static float y_memory = 300;
	static float y_memory_old = 300;
	static float width_memory = 400;
	static float height_memory = 400;
	// static Bitmap resizableMask;
	static int margin = 100;
	static int showMargin = margin / 4;
	static int minMaskWidth = 200;
	static int offset = 1;
	static Button backButton;
	static boolean imageSaveState;
	static int count = 0;
	// float heightMaskConst = 0.51056f;

	ImageView filter1;
	ImageView filter2;
	ImageView filter3;
	ImageView filter4;
	ImageView filter5;
	ImageView filter6;
	ImageView filter7;
	Filter filter = new Filter();

	// int hMask;
	Context mContext;
	Runnable runnable;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mask_activity);
		Filter.id = 1;
		filter1 = (ImageView) findViewById(R.id.filter1);
		filter2 = (ImageView) findViewById(R.id.filter2);
		filter3 = (ImageView) findViewById(R.id.filter3);
		filter4 = (ImageView) findViewById(R.id.filter4);
		filter5 = (ImageView) findViewById(R.id.filter5);
		filter6 = (ImageView) findViewById(R.id.filter6);
		filter7 = (ImageView) findViewById(R.id.filter7);
		// backButton = (Button) findViewById(R.id.back_button);
		loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
		new Filter();
		Filter.onDrawing();
		mContext = this;
		// progressBar = (ProgressBar) findViewById(R.id.progressBar);
		/*
		 * try { Thread.sleep(1000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		// Log.i("x", "y");

		// Log.i("wMask", "" + TakePicture.wMask);
		final LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		final DrawingView dv = new DrawingView(this);
		ll.addView(dv);

		saveButton = (Button) findViewById(R.id.save_button);
		/*
		 * backButton.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * } });
		 */
		// onBackPressed();

		saveButton.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("WrongCall")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mProgressDialog = ProgressDialog.show(mContext, "Processing",
						"Please Wait ...", true);
				// /*
				new Thread(new Runnable() {
					@Override
					public void run() {

						ToSaveCropped tsc = new ToSaveCropped();
						tsc.onDraw();
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						ToSaveCropped.croppedBitmap.compress(
								Bitmap.CompressFormat.PNG, 100, stream);
						bitData = stream.toByteArray();
						// UploadImage.data = bitData;
						// UploadImage.fileName = IdGen.getId();
						UploadImage.url = getResources().getString(
								R.string.urlSaveFile);

						UploadImage uI = new UploadImage();
						SaveFile sv = new SaveFile();
						sv.save();

						// dv.bitmapRecycle(croppedBitmap);

						try {
							if (uI.executeMultipartPost()) {

								Intent launchactivity = new Intent(
										MaskActivity.this, LoginActivity.class);
								startActivity(launchactivity);
								// Toast.makeText(getBaseContext(),
								// "You have been logged out",
								// Toast.LENGTH_SHORT)
								// .show();
								finish();
							} else {

								MediaStore.Images.Media.insertImage(
										getContentResolver(),
										ToSaveCropped.croppedBitmap, "", "");
								// Toast.makeText(getBaseContext(),
								// "Image saved",
								// Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (imageSaveState)
									Toast.makeText(getBaseContext(),
											"You have been logged out",
											Toast.LENGTH_SHORT).show();
								else
									Toast.makeText(mContext, "Image Saved",
											Toast.LENGTH_SHORT).show();
								mProgressDialog.dismiss();
							}
						});
					}
				}).start();
				// */
			}
		});

		findViewById(R.id.horizontalScrollView);

		filter1.setImageBitmap(FilterEffectsSmall
				.doNothing(TakePicture.bitmapThumbnail));
		filter2.setImageBitmap(FilterEffectsSmall
				.doInvert(TakePicture.bitmapThumbnail));
		filter3.setImageBitmap(FilterEffectsSmall.doGamma(
				TakePicture.bitmapThumbnail, 1, 1, 1));
		filter4.setImageBitmap(FilterEffectsSmall
				.doGreyscale(TakePicture.bitmapThumbnail));
		filter5.setImageBitmap(FilterEffectsSmall.createContrast(
				TakePicture.bitmapThumbnail, 50));
		filter6.setImageBitmap(FilterEffectsSmall.doBrightness(
				TakePicture.bitmapThumbnail, 30));
		filter7.setImageBitmap(FilterEffectsSmall.createSepiaToningEffect(
				TakePicture.bitmapThumbnail, 0, 1.0, 1.0, 1.0));

		filter1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mProgressDialog = ProgressDialog.show(mContext, "Processing",
						"Please Wait ...", true);
				count = 0;
				Filter.id = 1;

				// Filter.onDrawing();
				ll.removeAllViews();
				DrawingView ndv = new DrawingView(getBaseContext());
				ll.addView(ndv);

				new Thread(new Runnable() {
					@Override
					public void run() {
						// do the thing that takes a long time

						Filter.onDrawing();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mProgressDialog.dismiss();
							}
						});
					}
				}).start();
			}
		});

		filter2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub // mProgressDialog = new

				mProgressDialog = ProgressDialog.show(mContext, "Processing",
						"Please Wait ...", true);
				count = 0;
				Filter.id = 2;

				ll.removeAllViews();
				DrawingView ndv = new DrawingView(getBaseContext());
				ll.addView(ndv);

				new Thread(new Runnable() {
					@Override
					public void run() {
						// do the thing that takes a long time

						Filter.onDrawing();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mProgressDialog.dismiss();
							}
						});
					}
				}).start();
			}
		});

		filter3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				// loadingPanel.setVisibility(View.VISIBLE);
				mProgressDialog = ProgressDialog.show(mContext, "Processing",
						"Please Wait ...", true);
				count = 0;
				Filter.id = 3;

				// Filter.onDrawing();
				ll.removeAllViews();
				DrawingView ndv = new DrawingView(getBaseContext());
				ll.addView(ndv);

				new Thread(new Runnable() {
					@Override
					public void run() {
						// do the thing that takes a long time

						Filter.onDrawing();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mProgressDialog.dismiss();
							}
						});
					}
				}).start();
			}
		});

		filter4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// loadingPanel.setVisibility(View.VISIBLE);

				mProgressDialog = ProgressDialog.show(mContext, "Processing",
						"Please Wait ...", true);
				count = 0;
				Filter.id = 4;

				ll.removeAllViews();
				DrawingView ndv = new DrawingView(getBaseContext());
				ll.addView(ndv);

				new Thread(new Runnable() {
					@Override
					public void run() {
						// do the thing that takes a long time

						Filter.onDrawing();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mProgressDialog.dismiss();
							}
						});
					}
				}).start();
			}
		});

		filter5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// loadingPanel.setVisibility(View.VISIBLE);

				mProgressDialog = ProgressDialog.show(mContext, "Processing",
						"Please Wait ...", true);
				count = 0;
				Filter.id = 5;

				ll.removeAllViews();
				DrawingView ndv = new DrawingView(getBaseContext());
				ll.addView(ndv);

				new Thread(new Runnable() {
					@Override
					public void run() {
						// do the thing that takes a long time

						Filter.onDrawing();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mProgressDialog.dismiss();
							}
						});
					}
				}).start();
			}
		});

		filter6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mProgressDialog = ProgressDialog.show(mContext, "Processing",
						"Please Wait ...", true);
				count = 0;
				Filter.id = 6;

				// Filter.onDrawing();
				ll.removeAllViews();
				DrawingView ndv = new DrawingView(getBaseContext());
				ll.addView(ndv);

				new Thread(new Runnable() {
					@Override
					public void run() {
						// do the thing that takes a long time

						Filter.onDrawing();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mProgressDialog.dismiss();
							}
						});
					}
				}).start();
				// loadingPanel.setVisibility(View.GONE);
			}
		});

		filter7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mProgressDialog = ProgressDialog.show(mContext, "Processing",
						"Please Wait ...", true);
				count = 0;
				Filter.id = 7;

				// Filter.onDrawing();
				ll.removeAllViews();
				DrawingView ndv = new DrawingView(getBaseContext());
				ll.addView(ndv);

				new Thread(new Runnable() {
					@Override
					public void run() {
						// do the thing that takes a long time

						Filter.onDrawing();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mProgressDialog.dismiss();
							}
						});
					}
				}).start();
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		count = 0;
		FilterEffects.recyleBitmap();
		FilterEffectsSmall.recycleBitmap();
		// if (croppedBitmap != null)
		// Filter.bitmapRecycle(croppedBitmap);
		if (DrawingView.resizableMask != null)
			Filter.bitmapRecycle(DrawingView.resizableMask);
		Intent launchactivity = new Intent(MaskActivity.this, TakePicture.class);
		startActivity(launchactivity);
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		filter1.setImageBitmap(null);
		filter2.setImageBitmap(null);
		filter3.setImageBitmap(null);
		filter4.setImageBitmap(null);
		filter5.setImageBitmap(null);
		filter6.setImageBitmap(null);
		filter7.setImageBitmap(null);
	}

}