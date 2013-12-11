package com.example.getcmykimage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import magick.ColorspaceType;
import magick.ImageInfo;
import magick.MagickImage;
import magick.util.MagickBitmap;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;


/**
 * Get images with CMYK colorspace (Images not supported by Android)
 * @author Mario Velasco Casqueo "@MVelascoC"
 * Date: 11/12/2013
 */
public class GetCMYKImage extends Activity {

	private static final String URL = "http://i.imgur.com/MPSIT.jpg";
	private static final String SAVING_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/newImage.jpg"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bitmap bitmap = getCMYKImageFromURL(URL);
		ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView1.setImageBitmap(bitmap);
	}


	/**
	 * Get a drawable object of an image through a path to that image.
	 * @param path Could be an internal or external path (SD card)
	 * @return drawable image to display (Bitmap)
	 */
	public Bitmap getCMYKImageFromPath(String path) {
		Bitmap bitmap = null;
		try {
			ImageInfo info = new ImageInfo(path); // where the CMYK image is
			MagickImage imageCMYK = new MagickImage(info);
			Log.d("DEBUG", "ColorSpace BEFORE => " + imageCMYK.getColorspace());
			boolean status = imageCMYK.transformRgbImage(ColorspaceType.CMYKColorspace);
			Log.d("DEBUG", "ColorSpace AFTER => " + imageCMYK.getColorspace() + ", success = " + status);

			bitmap = MagickBitmap.ToBitmap(imageCMYK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}


	/**
	 * Get a drawable object of an image through a URL
	 * @param urlString URL of the file to be downloaded
	 * @return drawable image to display (Bitmap)
	 */
	public Bitmap getCMYKImageFromURL(String urlString) {
		downloadFile(urlString, SAVING_PATH); 
		Bitmap bitmap = getCMYKImageFromPath(SAVING_PATH);
		return bitmap;
	}


	
	/**
	 * Download file from URL and save it in local
	 * @param urlString URL of the file to be downloaded
	 * @param savingPath where the file will be saved, internal or external path
	 */
	private void downloadFile (String urlString, String savingPath) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.connect();
			File file = new File(savingPath); // save file here
			FileOutputStream fileOutput = new FileOutputStream(file);
			InputStream inputStream = urlConnection.getInputStream();
			byte[] buffer = new byte[1024];
			int bufferLength = 0; //used to store a temporary size of the buffer
			while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
				fileOutput.write(buffer, 0, bufferLength);
			}
			fileOutput.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
