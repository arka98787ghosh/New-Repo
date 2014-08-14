package com.example.cameraapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class TakePicture extends Activity {

	//static boolean isGallery = false;
	static boolean isPic = false;
	private Camera mCamera;
	private CameraPreview mPreview;
	static byte[] bitmapData;
	static Bitmap bitmap;
	private Button captureButton;
	static Bitmap mMask;
	// private Button closeButton;
	public static final int MEDIA_TYPE_IMAGE = 1;
	static int height;
	static int width;
	static int wMask;
	float widthMaskConst = 0.93125f;
	static Bitmap bitmapThumbnail;
	private ProgressDialog mProgressDialog;
	//private Button galleryButton;
	//private Button logoutButton;
	private Context mContext;
	private static int RESULT_LOAD_IMAGE = 1;
	static int count = 0;
	static int counter = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Crashlytics.start(this);
		setContentView(R.layout.takepicture);
		//galleryButton = (Button) findViewById(R.id.gallery_button);
		//logoutButton = (Button) findViewById(R.id.logout_button);
		Display display = getWindowManager().getDefaultDisplay();
		Point point = new Point();
		point.x = display.getWidth();
		point.y = display.getHeight();
		width = point.x;
		height = point.y;
		FilterEffects.initialzeBitmap();
		wMask = (int) (width * widthMaskConst);
		Drawable myMask = getResources().getDrawable(R.drawable.mask);
		mMask = ((BitmapDrawable) myMask).getBitmap();
		mContext = this;
		final StoreToken st = new StoreToken(mContext);

		captureButton = (Button) findViewById(R.id.camera_button);
		// closeButton = (Button) findViewById(R.id.close_button);
		/*
		 * mCamera = getCameraInstance(); // Create our Preview view and set it
		 * as the content of our activity. if (mCamera != null)
		 * Log.i("in onresume " + ++count,
		 * " aslkdfkasdhfkjaskdjfkjkasjdfkjkajsdkjfkjlsdjfjkjlasjdfjkjlsd");
		 * mPreview = new CameraPreview(mContext, mCamera); FrameLayout preview
		 * = (FrameLayout) findViewById(R.id.camera_preview);
		 * preview.addView(mPreview); mCamera.startPreview();
		
		galleryButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// isGallery = true;
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		 */
		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// get an image from the camera
				// mCamera.stopPreview();
				// progressBar.setVisibility(View.VISIBLE);
				 mProgressDialog = ProgressDialog.show(mContext, "Processing",
				 "Please Wait ...", true);
				 isPic = true;
				Drawable myMask = getResources().getDrawable(R.drawable.mask);
				mMask = ((BitmapDrawable) myMask).getBitmap();
				mCamera.takePicture(null, null, mPicture);
				// isGallery = false;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// start another activity
				Intent launchactivity = new Intent(TakePicture.this,
						MaskActivity.class);
				startActivity(launchactivity);
				//bitmapRecycle(bitmap);
				finish();

			}
		});
		
	}
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	  } 
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // action with ID action_refresh was selected
	    case R.id.open_phone_gallery:
	    	Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, RESULT_LOAD_IMAGE);
	      break;
	    // action with ID action_settings was selected
	    case R.id.open_server_gallery:
	      Toast.makeText(this, "should load gallery from server", Toast.LENGTH_SHORT)
	          .show();
	      break;
	    case R.id.logout:
	    	StoreToken st = new StoreToken(mContext);
	    	st.deleteToken();
	    	finish();
	    default:
	      break;
	    }

	    return true;
	  }

	PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			bitmapData = data;
			Log.i("" + data[0], "" + data[1]);
			//bitmapRecycle(bitmap);
			//bitmap = Bitmap.createBitmap(TakePicture.width, TakePicture.height,
					//Bitmap.Config.RGB_565);
			bitmap = Filter.getResizedBitmap(Filter.bitmapRotate(BitmapFactory
					.decodeByteArray(data, 0, data.length)), height, width);
			bitmapThumbnail = Filter.getResizedBitmap(bitmap, 100, 100);
			// camera.startPreview();
		}
	};

	public static byte[] getbitmapData() {
		return bitmapData;
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera(); // release the camera immediately on pause event
		// mProgressDialog.dismiss();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Create an instance of Camera
		mCamera = getCameraInstance();
		// Create our Preview view and set it as the content of our activity.
		if (mCamera != null)
			Log.i("in onresume " + ++count,
					" aslkdfkasdhfkjaskdjfkjkasjdfkjkajsdkjfkjlsdjfjkjlasjdfjkjlsd");
		mPreview = new CameraPreview(mContext, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
		mCamera.startPreview();
	}

	private void releaseCamera() {
		if (mCamera != null) {
			Log.i("in release camera", "alksjdflkas");
			mCamera.release(); // release the camera for other applications
			mCamera = null;
		}
	}

	// to check whether camera is available or not?
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {

			// the phone has camera

			return true;
		} else {

			// the phone doesn't have a camera

			return false;
		}
	}

	// accessing the camera

	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			Log.i("camera working", "in get camera insance");
			c = Camera.open();
		} catch (Exception e) {
			// camera not available or in use by some other application
			Log.i("sorry camera not available ",
					"this idiotta is not relesing camera properly");
		}
		return c;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaColumns.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			// ImageView imageView = (ImageView) findViewById(R.id.imgView);
			// imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			bitmap = Filter.getResizedBitmap(
					BitmapFactory.decodeFile(picturePath), height, width);
			bitmapThumbnail = Filter.getResizedBitmap(bitmap, 100, 100);
			Intent launchactivity = new Intent(TakePicture.this,
					MaskActivity.class);
			startActivity(launchactivity);
			//bitmapRecycle(bitmap);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}
	
	public static void bitmapRecycle(Bitmap bitmap) {
		bitmap.recycle();
		bitmap = null;
	}
	
	@Override
    protected void onDestroy() {
    super.onDestroy();
    //if(mProgressDialog.isShowing())	
    //mProgressDialog.dismiss();
    unbindDrawables(findViewById(R.id.activity_main));
    System.gc();
    }

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
    
    public static void logoutAction(){
    	
    }

}