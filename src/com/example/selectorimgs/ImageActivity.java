package com.example.selectorimgs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class ImageActivity extends Activity {

	private final static int REQUEST_IMG = 1;
	ImageView imageView;
	Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		imageView = (ImageView)findViewById(R.id.imageView1);
	}

	public void showGallery(View view) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_IMG);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("RESULT","On Activity Result");
		
		if (bitmap!=null) {
			bitmap.recycle();			
		}
		
		InputStream is = null;
		
		if (requestCode == REQUEST_IMG && resultCode == Activity.RESULT_OK) {
		
			try {
				is = getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(is);
				
				imageView.setImageBitmap(bitmap);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} else {
			Log.e("RESULT", "Se produjo un Error");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

}
