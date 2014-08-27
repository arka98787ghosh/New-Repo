package com.example.cameraapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ToSaveCropped {
	public static Bitmap croppedBitmap;
	
	Bitmap mImage = Filter.filteredBitmap;

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
		
		canvas.drawBitmap(mImage, -MaskActivity.x_memory,
				-MaskActivity.y_memory,null);
	}

}
