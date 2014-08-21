package com.example.cameraapp;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Filter {

	//static Bitmap image;
	//static Bitmap toRotate;
	//static Bitmap resizedBitmap;
	static Bitmap filteredBitmap;
	static int id = 1;
	

	public static void onDrawing() {
		//FilterEffects.initialzeBitmap();
		switch (id) {
		case 1:
			filteredBitmap = FilterEffects.doNothing(TakePicture.bitmap);
			break;
		case 2:
			filteredBitmap = FilterEffects.doInvert(TakePicture.bitmap);
			break;
		case 3:
			filteredBitmap = FilterEffects.doGamma(TakePicture.bitmap,1,1,1);
			break;
		case 4:
			filteredBitmap = FilterEffects.doGreyscale(TakePicture.bitmap);
			break;
		case 5:
			filteredBitmap = FilterEffects.createContrast(TakePicture.bitmap, 50);
			break;
		case 6:
			filteredBitmap = FilterEffects.doBrightness(TakePicture.bitmap, 30);
			break;
		case 7:
			filteredBitmap = FilterEffects.createSepiaToningEffect(TakePicture.bitmap, 0, 1.0, 1.0, 1.0);	
			break;
		default:
			break;
		}
		//FilterEffects.recyleBitmap();
		/*
		 * 
		 * images[1] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth()); Log.i("a", "d"); //
		 * images[0].setImageBitmap(mmodFilterImage); Log.i("a", "d");
		 * 
		 * images[2] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[1].setImageBitmap(mmodFilterImage);
		 * 
		 * images[3] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[2].setImageBitmap(mmodFilterImage);
		 * 
		 * images[4] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[3].setImageBitmap(mmodFilterImage);
		 * 
		 * images[5] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[4].setImageBitmap(mmodFilterImage);
		 * 
		 * images[6] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[5].setImageBitmap(mmodFilterImage);
		 */

	}

	public static Bitmap bitmapRotate(Bitmap original) {
		
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		original = Bitmap.createBitmap(original, 0, 0, original.getWidth(),
				original.getHeight(), matrix, true);
		return original;
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		//
		
		int width = bm.getWidth();

		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;

		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation

		Matrix matrix = new Matrix();

		// resize the bit map

		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap

		bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				false);
		return bm;

	}

	public static void bitmapRecycle(Bitmap bitmap) {
		bitmap.recycle();
		bitmap = null;
	}

}
