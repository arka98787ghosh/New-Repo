package com.example.cameraapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class SaveFile extends Activity{
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	static File mediaStorageDir;
	File pictureFile;
	
	public void save(){
		 
		 byte[] data;
		 data = MaskActivity.bitData;
		 
		 pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
         if (pictureFile == null){
            
             return;
         }

         try {
             FileOutputStream fos = new FileOutputStream(pictureFile);
             fos.write(data);
             fos.close();
             UploadImage.file = pictureFile;
             //UploadImage.fileName = 
         } catch (FileNotFoundException e) {
             Log.d("In PictureCalback", "File not found: " + e.getMessage());
         } catch (IOException e) {
             Log.d("In PictureCalback", "Error accessing file: " + e.getMessage());
         }
	}
	
	
	/** Create a file Uri for saving an image or video */
    @SuppressWarnings("unused")
	private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    @SuppressLint("SimpleDateFormat")
	private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }
}
