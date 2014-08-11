package com.example.cameraapp;

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.media.FaceDetector;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MaskActivity extends Activity {

	static int id = 0;
	private Button saveButton;
	private RelativeLayout loadingPanel;
	private ProgressDialog mProgressDialog;
	// private ProgressBar progressBar;
	// private Button effectsButton;
	public Bitmap croppedBitmap;
	public static byte[] bitData;
	public int finalx, finaly;
	public static float x, y;
	static float x_memory = 100;
	static float x_memory_old = 100;
	static float y_memory = 300;
	static float y_memory_old = 300;
	static float width_memory = 400;
	static float height_memory = 400;
	static Bitmap resizableMask;
	static int margin = 100;
	static int showMargin = margin / 4;
	static int minMaskWidth = 200;
	static int offset = 1;
	static Button backButton;
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
		Filter.id=1;
		filter1 = (ImageView) findViewById(R.id.filter1);
		filter2 = (ImageView) findViewById(R.id.filter2);
		filter3 = (ImageView) findViewById(R.id.filter3);
		filter4 = (ImageView) findViewById(R.id.filter4);
		filter5 = (ImageView) findViewById(R.id.filter5);
		filter6 = (ImageView) findViewById(R.id.filter6);
		filter7 = (ImageView) findViewById(R.id.filter7);
		//backButton = (Button) findViewById(R.id.back_button);
		loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
		final Filter filter = new Filter();
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
				// Toast.makeText(getBaseContext(), "reaching",
				// Toast.LENGTH_SHORT).show();

				class ToSaveCropped {

					class NewDrawingView extends View {
						// Bitmap bitmap;
						float x, y;
						//int id;

						public NewDrawingView(Context context) {
							super(context);
							// bitmap =
							// BitmapFactory.decodeResource(context.getResources(),
							// R.drawable.ic_launcher);
						}

						@Override
						public boolean onTouchEvent(MotionEvent event) {

							switch (event.getAction()) {
							case MotionEvent.ACTION_DOWN: {

							}
								break;

							case MotionEvent.ACTION_MOVE: {
								x = (int) event.getX();
								y = (int) event.getY();

								invalidate();
							}

								break;
							case MotionEvent.ACTION_UP:

								x = (int) event.getX();
								finalx = (int) x;
								y = (int) event.getY();
								finaly = (int) y;
								// System.out.println(".................." + x +
								// "......" + y);
								// // x=
								// 345
								// y=530
								invalidate();
								break;
							}
							return true;
						}

						// byte[] data = TakePicture.getbitmapData();
						// Bitmap mImage = BitmapFactory.decodeByteArray(data,
						// 0,
						// data.length);

						//Bitmap mImage = TakePicture.bitmap;

						// Drawable myMask = getResources().getDrawable(
						// R.drawable.mask);
						// Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();

						// Bitmap mmImage = filter.bitmapRotate(mImage);

						// Bitmap mmmImage = filter.getResizedBitmap(mmImage,
						// height, width);

						// Bitmap mmMask = filter.getResizedBitmap(mMask, wMask,
						// wMask);
						/*
						 * @Override public void onDraw(Canvas canvas) { //
						 * Log.i(""+data[0],""+data[1]);
						 * 
						 * Paint maskPaint = new Paint();
						 * maskPaint.setXfermode(new PorterDuffXfermode(
						 * PorterDuff.Mode.DST_IN));
						 * 
						 * Paint imagePaint = new Paint();
						 * imagePaint.setXfermode(new PorterDuffXfermode(
						 * PorterDuff.Mode.DST_OVER));
						 * 
						 * canvas.drawBitmap(TakePicture.mMask, x, y,
						 * maskPaint); canvas.drawBitmap(mImage, 0, 0,
						 * imagePaint);
						 * 
						 * }
						 */
					}

					NewDrawingView ndv = new NewDrawingView(getBaseContext());
					float x = finalx;
					float y = finaly;

					// private byte[] data = TakePicture.getbitmapData();
					//Filter.onDrawing();
					Bitmap mImage = Filter.filteredBitmap;

					Drawable myMask = getResources().getDrawable(
							R.drawable.mask);

					// Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();

					// Bitmap mmImage = dv.mmImage;

					// Bitmap mmmImage = filter.getResizedBitmap(ndv.mmImage,
					// height, width);

					// Bitmap mmMask =
					// filter.getResizedBitmap(TakePicture.mMask,
					// MaskActivity.width_memory,
					// MaskActivity.height_memory);

					public void onDraw() {

						Canvas canvas;
						int w = (int) MaskActivity.width_memory, h = (int) MaskActivity.height_memory;
						Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see
																		// other
																		// conf
																		// types
						croppedBitmap = Bitmap.createBitmap(w, h, conf); // this
																			// creates
																			// a
																			// MUTABLE
																			// bitmap
						canvas = new Canvas(croppedBitmap);

						Paint maskPaint = new Paint();
						maskPaint.setXfermode(new PorterDuffXfermode(
								PorterDuff.Mode.DST_IN));

						Paint imagePaint = new Paint();
						imagePaint.setXfermode(new PorterDuffXfermode(
								PorterDuff.Mode.DST_OVER));
						// Log.i(""+x,""+y);
						canvas.drawBitmap(TakePicture.mMask, 0, 0, maskPaint);
						canvas.drawBitmap(mImage, -MaskActivity.x_memory,
								-MaskActivity.y_memory, imagePaint);
					}

				}
				ToSaveCropped tsc = new ToSaveCropped();
				tsc.onDraw();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				croppedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				bitData = stream.toByteArray();
				//UploadImage.data = bitData;
				//UploadImage.fileName = IdGen.getId();
				UploadImage.url = getResources().getString(R.string.urlSaveFile);
				SaveFile sv = new SaveFile();
				sv.save();

				MediaStore.Images.Media.insertImage(getContentResolver(),
						croppedBitmap, "", "");
				
				UploadImage uI = new UploadImage();
				try {
					uI.executeMultipartPost();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Toast.makeText(getBaseContext(), "Image saved",
						Toast.LENGTH_SHORT).show();
				
				

				// dv.bitmapRecycle(croppedBitmap);

			}
		});

		HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
		
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
				/*
				 * // TODO Auto-generated method stub // mProgressDialog = new
				 * ProgressDialog(mContext); // /mProgressDialog.show(mContext,
				 * "Dialog Title", // "Loading ..."); Filter.id = 2;
				 * Filter.onDrawing(); ll.removeAllViews(); //
				 * Log.i("Dialog still Showing","All views removed");
				 * NewDrawingView ndv = new NewDrawingView(mContext);
				 * ll.addView(ndv); //
				 * Log.i("Dialog still Showing","new view added");
				 */

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
				// loadingPanel.setVisibility(View.GONE);
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

		filter5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// loadingPanel.setVisibility(View.VISIBLE);

				mProgressDialog = ProgressDialog.show(mContext, "Processing",
						"Please Wait ...", true);
				count = 0;
				Filter.id = 5;
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

		filter6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// loadingPanel.setVisibility(View.VISIBLE);

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
				// loadingPanel.setVisibility(View.VISIBLE);

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
				// loadingPanel.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		count = 0;
		FilterEffects.recyleBitmap();
		FilterEffectsSmall.recycleBitmap();
		if(croppedBitmap != null)
		Filter.bitmapRecycle(croppedBitmap);
		if(resizableMask != null)
		Filter.bitmapRecycle(resizableMask);
		Intent launchactivity = new Intent(MaskActivity.this,
				TakePicture.class);
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
    //unbindDrawables(findViewById(R.id.mask_activity));
    //System.gc();
    }
	/*
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
        view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
        ((ViewGroup) view).removeAllViews();
        }
    }
	*/
	class DrawingView extends View {
		// Bitmap bitmap;
		// int id = 9;
		float del_x = 0;
		float del_y = 0;
		float x, y;

		public DrawingView(Context context) {
			super(context);
			// bitmap = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.ic_launcher);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				x = event.getX();
				y = event.getY();
				float x_right = (MaskActivity.x_memory + MaskActivity.width_memory);
				float x_left = (MaskActivity.x_memory);
				float y_top = (MaskActivity.y_memory);
				float y_bottom = (MaskActivity.y_memory + MaskActivity.height_memory);
				if (x >= x_right && x <= (x_right + margin) && y >= y_bottom
						&& y <= (y_bottom + margin)) {

					id = 1;

				} else if (x >= x_right && x <= (x_right + margin)
						&& y <= y_top && y >= (y_top - margin)) {
					id = 2;
				}

				else if (x <= (x_left) && x >= (x_left - margin)
						&& y >= (y_bottom) && y <= (y_bottom + margin)) {

					id = 4;

				} else if (x <= (x_left) && x >= (x_left - margin)
						&& y <= (y_top) && y >= (y_top - margin)) {
					id = 5;
				} else if (x >= x_left && x <= x_right && y <= y_bottom
						&& y >= y_top) {

					id = 9;

				} else if (x <= x_left || x >= x_right || y <= y_top
						|| y >= y_bottom) {
					id = 0;
					del_x = 0;
					del_y = 0;
					Log.i("in else", "" + id);
				}
			}
				break;

			case MotionEvent.ACTION_MOVE: {
				if (id != 0) {
					if (id == 5) {
						if (MaskActivity.width_memory >= minMaskWidth) {
							MaskActivity.x_memory = (int) event.getX();
							del_x = (MaskActivity.x_memory_old - (int) event
									.getX());
						} else {
							MaskActivity.width_memory = minMaskWidth + offset;
						}

						if (MaskActivity.height_memory >= minMaskWidth) {
							MaskActivity.y_memory = (int) event.getY();
							del_y = (MaskActivity.y_memory_old - (int) event
									.getY());
						} else {
							MaskActivity.height_memory = minMaskWidth + offset;
						}

					}

					else if (id == 9) {

						del_x = 0;
						MaskActivity.x_memory = (int) event.getX()
								- MaskActivity.width_memory / 2;

						del_y = 0;
						MaskActivity.y_memory = (int) event.getY()
								- MaskActivity.height_memory / 2;

					} else if (id == 4) {

						if (MaskActivity.width_memory >= minMaskWidth) {
							MaskActivity.x_memory = (int) event.getX();
							del_x = (MaskActivity.x_memory_old - (int) event
									.getX());
						} else {
							MaskActivity.width_memory = minMaskWidth + offset;
						}

						if (MaskActivity.height_memory >= minMaskWidth) {
							MaskActivity.y_memory = MaskActivity.y_memory_old;
							del_y = ((int) event.getY()
									- MaskActivity.y_memory_old - MaskActivity.height_memory);
						} else {
							MaskActivity.height_memory = minMaskWidth + offset;
						}

					} else if (id == 1) {

						if (MaskActivity.width_memory >= minMaskWidth) {
							MaskActivity.x_memory = MaskActivity.x_memory_old;
							del_x = ((int) event.getX()
									- MaskActivity.x_memory_old - MaskActivity.width_memory);
						} else {
							MaskActivity.width_memory = minMaskWidth + offset;
						}

						if (MaskActivity.height_memory >= minMaskWidth) {
							MaskActivity.y_memory = MaskActivity.y_memory_old;
							del_y = ((int) event.getY()
									- MaskActivity.y_memory_old - MaskActivity.height_memory);
						} else {
							MaskActivity.height_memory = minMaskWidth + offset;
						}

					} else if (id == 2) {

						if (MaskActivity.width_memory >= minMaskWidth) {
							MaskActivity.x_memory = MaskActivity.x_memory_old;
							del_x = ((int) event.getX()
									- MaskActivity.x_memory_old - MaskActivity.width_memory);
						} else {
							MaskActivity.width_memory = minMaskWidth + offset;
						}

						if (MaskActivity.height_memory >= minMaskWidth) {
							MaskActivity.y_memory = (int) event.getY();
							del_y = (MaskActivity.y_memory_old - (int) event
									.getY());
						} else {
							MaskActivity.height_memory = minMaskWidth + offset;
						}

					} else {
					}
				}
				invalidate();
			}

				break;
			case MotionEvent.ACTION_UP:
				// System.out.println(".................." + x + "......" + y);
				// // x=
				// 345
				// y=530
				id = 9;
				id = 0;
				del_x = 0;
				del_y = 0;
				invalidate();
				break;
			}
			if (id != 0) {

				if (MaskActivity.width_memory >= minMaskWidth) {
					MaskActivity.x_memory_old = MaskActivity.x_memory;
					MaskActivity.width_memory += del_x;
				}

				if (MaskActivity.height_memory >= minMaskWidth) {
					MaskActivity.y_memory_old = MaskActivity.y_memory;
					MaskActivity.height_memory += del_y;
				}
				// Log.i("showing width and height when around minMaskWidth "+MaskActivity.width_memory,""+MaskActivity.height_memory);
			}
			return true;
		}

		// byte[] data = TakePicture.getbitmapData();
		Bitmap mImage = Filter.filteredBitmap;

		// Drawable myMask = getResources().getDrawable(R.drawable.mask);
		// Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();

		// Bitmap mmImage = filter.bitmapRotate(mImage);

		// Bitmap mmmImage = filter.getResizedBitmap(mmImage, height, width);

		// Bitmap mmMask = filter.getResizedBitmap(mMask, wMask, wMask);

		@Override
		public void onDraw(Canvas canvas) {
			// Log.i(""+data[0],""+data[1]);
			/*
			 * Paint maskPaint = new Paint(); maskPaint .setXfermode(new
			 * PorterDuffXfermode(PorterDuff.Mode.DST_IN));
			 * 
			 * Paint imagePaint = new Paint(); imagePaint.setXfermode(new
			 * PorterDuffXfermode( PorterDuff.Mode.DST_OVER));
			 * 
			 * canvas.drawBitmap(TakePicture.mMask, MaskActivity.x,
			 * MaskActivity.y, maskPaint); canvas.drawBitmap(mImage, 0, 0,
			 * imagePaint);
			 */
			PointF tmp_point = new PointF();
			Paint tmp_paint = new Paint();

			Paint rectPaint = new Paint();
			rectPaint
					.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));

			Paint maskPaint = new Paint();
			maskPaint
					.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

			Paint imagePaint = new Paint();
			imagePaint.setXfermode(new PorterDuffXfermode(
					PorterDuff.Mode.DST_OVER));

			if (id == 4) {
				MaskActivity.resizableMask = Filter.getResizedBitmap(
						(Bitmap.createBitmap(TakePicture.mMask)),
						(int) (MaskActivity.height_memory + del_y),
						(int) MaskActivity.width_memory);
				canvas.drawBitmap(MaskActivity.resizableMask,
						MaskActivity.x_memory - del_x, MaskActivity.y_memory,
						maskPaint);
				// lt
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory - showMargin,
						MaskActivity.x_memory, MaskActivity.y_memory, rectPaint);
				// rt
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						- showMargin, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory, rectPaint);
				// lb
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory,
						MaskActivity.x_memory, MaskActivity.y_memory
								+ MaskActivity.height_memory + showMargin,
						rectPaint);
				// rb
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						+ MaskActivity.height_memory, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory
								+ showMargin, rectPaint);
				canvas.drawBitmap(mImage, 0, 0, imagePaint);

			}

			else if (id == 5) {
				MaskActivity.resizableMask = Filter.getResizedBitmap(
						(Bitmap.createBitmap(TakePicture.mMask)),
						(int) MaskActivity.height_memory,
						(int) (MaskActivity.width_memory + del_x));
				// lt
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory - showMargin,
						MaskActivity.x_memory, MaskActivity.y_memory, rectPaint);
				// rt
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						- showMargin, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory, rectPaint);
				// lb
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory,
						MaskActivity.x_memory, MaskActivity.y_memory
								+ MaskActivity.height_memory + showMargin,
						rectPaint);
				// rb
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						+ MaskActivity.height_memory, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory
								+ showMargin, rectPaint);
				canvas.drawBitmap(MaskActivity.resizableMask,
						MaskActivity.x_memory, MaskActivity.y_memory + del_y,
						maskPaint);
				canvas.drawBitmap(mImage, 0, 0, imagePaint);
			} else if (id == 9) {
				MaskActivity.resizableMask = Filter.getResizedBitmap(
						(Bitmap.createBitmap(TakePicture.mMask)),
						(int) MaskActivity.height_memory,
						(int) MaskActivity.width_memory);
				// lt
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory - showMargin,
						MaskActivity.x_memory, MaskActivity.y_memory, rectPaint);
				// rt
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						- showMargin, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory, rectPaint);
				// lb
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory,
						MaskActivity.x_memory, MaskActivity.y_memory
								+ MaskActivity.height_memory + showMargin,
						rectPaint);
				// rb
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						+ MaskActivity.height_memory, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory
								+ showMargin, rectPaint);
				canvas.drawBitmap(MaskActivity.resizableMask,
						MaskActivity.x_memory, MaskActivity.y_memory, maskPaint);
				canvas.drawBitmap(mImage, 0, 0, imagePaint);
			} else if (id == 1) {
				MaskActivity.resizableMask = Filter.getResizedBitmap(
						(Bitmap.createBitmap(TakePicture.mMask)),
						(int) (MaskActivity.height_memory + del_y),
						(int) (MaskActivity.width_memory + del_x));
				// lt
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory - showMargin,
						MaskActivity.x_memory, MaskActivity.y_memory, rectPaint);
				// rt
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						- showMargin, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory, rectPaint);
				// lb
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory,
						MaskActivity.x_memory, MaskActivity.y_memory
								+ MaskActivity.height_memory + showMargin,
						rectPaint);
				// rb
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						+ MaskActivity.height_memory, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory
								+ showMargin, rectPaint);
				canvas.drawBitmap(MaskActivity.resizableMask,
						MaskActivity.x_memory, MaskActivity.y_memory, maskPaint);
				canvas.drawBitmap(mImage, 0, 0, imagePaint);
			} else if (id == 2) {
				MaskActivity.resizableMask = Filter.getResizedBitmap(
						(Bitmap.createBitmap(TakePicture.mMask)),
						(int) MaskActivity.height_memory,
						(int) (MaskActivity.width_memory + del_x));
				// lt
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory - showMargin,
						MaskActivity.x_memory, MaskActivity.y_memory, rectPaint);
				// rt
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						- showMargin, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory, rectPaint);
				// lb
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory,
						MaskActivity.x_memory, MaskActivity.y_memory
								+ MaskActivity.height_memory + showMargin,
						rectPaint);
				// rb
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						+ MaskActivity.height_memory, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory
								+ showMargin, rectPaint);
				canvas.drawBitmap(MaskActivity.resizableMask,
						MaskActivity.x_memory, MaskActivity.y_memory + del_y,
						maskPaint);
				canvas.drawBitmap(mImage, 0, 0, imagePaint);
			} else {
				MaskActivity.resizableMask = Filter.getResizedBitmap(
						(Bitmap.createBitmap(TakePicture.mMask)),
						(int) MaskActivity.height_memory,
						(int) MaskActivity.width_memory);
				// lt
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory - showMargin,
						MaskActivity.x_memory, MaskActivity.y_memory, rectPaint);
				// rt
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						- showMargin, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory, rectPaint);
				// lb
				canvas.drawRect(MaskActivity.x_memory - showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory,
						MaskActivity.x_memory, MaskActivity.y_memory
								+ MaskActivity.height_memory + showMargin,
						rectPaint);
				// rb
				canvas.drawRect(MaskActivity.x_memory
						+ MaskActivity.width_memory, MaskActivity.y_memory
						+ MaskActivity.height_memory, MaskActivity.x_memory
						+ MaskActivity.width_memory + showMargin,
						MaskActivity.y_memory + MaskActivity.height_memory
								+ showMargin, rectPaint);
				canvas.drawBitmap(MaskActivity.resizableMask,
						MaskActivity.x_memory, MaskActivity.y_memory, maskPaint);
				canvas.drawBitmap(mImage, 0, 0, imagePaint);
			}

			/*
			 * for (int i = 0; i < FaceDetection.face_count; i++) {
			 * FaceDetector.Face face = FaceDetection.faces[i];
			 * tmp_paint.setColor(Color.BLUE); tmp_paint.setAlpha(100); //
			 * tmp_paint // .setXfermode(new
			 * PorterDuffXfermode(PorterDuff.Mode.DARKEN));
			 * face.getMidPoint(tmp_point); // canvas.drawCircle(tmp_point.x,
			 * tmp_point.y, // face.eyesDistance(), tmp_paint);
			 * 
			 * canvas.drawRect(tmp_point.x - face.eyesDistance() , tmp_point.y -
			 * face.eyesDistance(), tmp_point.x + face.eyesDistance(),
			 * tmp_point.y + face.eyesDistance(),tmp_paint); }
			 */
			if (count == 0) {
				Log.i("reaching here","in on draw of askactivity");
				FaceDetection.updateImage(mImage);
				count++;
			}

			if(FaceDetection.face_count>0){
				for (int i = 0; i < FaceDetection.face_count; i++) {
					FaceDetector.Face face = FaceDetection.faces[i];
					tmp_paint.setColor(Color.BLUE);
					tmp_paint.setAlpha(100);
					// tmp_paint
					// .setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
					face.getMidPoint(tmp_point);
					// canvas.drawCircle(tmp_point.x, tmp_point.y,
					// face.eyesDistance(), tmp_paint);

					canvas.drawRect(tmp_point.x - face.eyesDistance(), tmp_point.y
							- face.eyesDistance(),
							tmp_point.x + face.eyesDistance(),
							tmp_point.y + face.eyesDistance(), tmp_paint);
				}
			}
		}
	}
}