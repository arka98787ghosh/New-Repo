package com.example.cameraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.FaceDetector;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

class DrawingView extends View {
	static int id = 0;
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
			resizableMask = Filter.getResizedBitmap(
					(Bitmap.createBitmap(TakePicture.mMask)),
					(int) (MaskActivity.height_memory + del_y),
					(int) MaskActivity.width_memory);
			canvas.drawBitmap(resizableMask,
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
			resizableMask = Filter.getResizedBitmap(
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
			canvas.drawBitmap(resizableMask,
					MaskActivity.x_memory, MaskActivity.y_memory + del_y,
					maskPaint);
			canvas.drawBitmap(mImage, 0, 0, imagePaint);
		} else if (id == 9) {
			resizableMask = Filter.getResizedBitmap(
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
			canvas.drawBitmap(resizableMask,
					MaskActivity.x_memory, MaskActivity.y_memory, maskPaint);
			canvas.drawBitmap(mImage, 0, 0, imagePaint);
		} else if (id == 1) {
			resizableMask = Filter.getResizedBitmap(
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
			canvas.drawBitmap(resizableMask,
					MaskActivity.x_memory, MaskActivity.y_memory, maskPaint);
			canvas.drawBitmap(mImage, 0, 0, imagePaint);
		} else if (id == 2) {
			resizableMask = Filter.getResizedBitmap(
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
			canvas.drawBitmap(resizableMask,
					MaskActivity.x_memory, MaskActivity.y_memory + del_y,
					maskPaint);
			canvas.drawBitmap(mImage, 0, 0, imagePaint);
		} else {
			resizableMask = Filter.getResizedBitmap(
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
			canvas.drawBitmap(resizableMask,
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
			Log.i("reaching here", "in on draw of askactivity");
			FaceDetection.updateImage(mImage);
			count++;
		}

		if (FaceDetection.face_count > 0) {
			for (int i = 0; i < FaceDetection.face_count; i++) {
				FaceDetector.Face face = FaceDetection.faces[i];
				tmp_paint.setColor(Color.BLUE);
				tmp_paint.setAlpha(100);
				// tmp_paint
				// .setXfermode(new
				// PorterDuffXfermode(PorterDuff.Mode.DARKEN));
				face.getMidPoint(tmp_point);
				// canvas.drawCircle(tmp_point.x, tmp_point.y,
				// face.eyesDistance(), tmp_paint);

				canvas.drawRect(tmp_point.x - face.eyesDistance(),
						tmp_point.y - face.eyesDistance(), tmp_point.x
								+ face.eyesDistance(),
						tmp_point.y + face.eyesDistance(), tmp_paint);
			}
		}
	}
}