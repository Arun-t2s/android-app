package com.foxysy.foxysy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.foxysy.adapter.CustomAdapter;
import com.foxysy.api.ApiConnection;
import com.foxysy.model.EventDetails;
import com.foxysy.utils.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GalleryActivity extends Activity {

	ApiConnection apiconnection;
	ProgressDialog mDialog;
	String response;
	ArrayList<Bitmap> img_ar;

	CustomAdapter adapter;
	GridView gridView;
	Gallery gallery;
	RelativeLayout top_bar;
	private int selectedImagePosition = 0;
	ArrayList<EventDetails> eventdetails = new ArrayList<EventDetails>();
	EventDetails events;
	ImageView left_arrow_imageview, right_arrow_imageview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_tab);
		
		top_bar=(RelativeLayout)findViewById(R.id.top_bar);
		int h = top_bar.getHeight();
		ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
		mDrawable.getPaint().setShader(
				new LinearGradient(0, 0, 0, h, Color.parseColor(Splash.background_header_ar.get(0)),
						Color.parseColor(Splash.background_header_ar.get(1)), Shader.TileMode.REPEAT));
		top_bar.setBackgroundDrawable(mDrawable);

		gallery = (Gallery) findViewById(R.id.gallery);
		left_arrow_imageview = (ImageView) findViewById(R.id.left_arrow_imageview);
		right_arrow_imageview = (ImageView) findViewById(R.id.right_arrow_imageview);
		img_ar = HomeActivity.img_ar;
		System.out.println("gALLERY:" + img_ar.size());

		adapter = new CustomAdapter(GalleryActivity.this, img_ar);
		gallery.setAdapter(adapter);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectedImagePosition = position;

				if (selectedImagePosition > 0
						&& selectedImagePosition < img_ar.size() - 1) {

					left_arrow_imageview.setVisibility(View.VISIBLE);
					right_arrow_imageview.setVisibility(View.VISIBLE);

				} else if (selectedImagePosition == 0) {

					left_arrow_imageview.setVisibility(View.INVISIBLE);
				} else if (selectedImagePosition == img_ar.size() - 1) {

					right_arrow_imageview.setVisibility(View.INVISIBLE);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		left_arrow_imageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedImagePosition > 0) {
					--selectedImagePosition;

				}
				
				gallery.setSelection(selectedImagePosition, false);

			}
		});

		right_arrow_imageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedImagePosition < img_ar.size() - 1) {
					++selectedImagePosition;

				}
				
				gallery.setSelection(selectedImagePosition, false);

			}
		});

	}

	private class GetDetails extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {

//			mDialog = new ProgressDialog(GalleryActivity.this);
//			mDialog.setMessage("Please wait...");
//			mDialog.setCancelable(false);
//			mDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			response = apiconnection.getAllDetails();
			System.out.println("HomeActivity:" + response);

			try {
				JSONObject jsonobject = new JSONObject(response);

				JSONObject jsonpages = jsonobject.optJSONObject("pages");
				JSONObject jsongallery = jsonpages.optJSONObject("gallery");
				JSONArray jsonimages = jsongallery.optJSONArray("images");

				int lengthJsonArr = jsonimages.length();
				System.out.println("LENGTHOFJSONingallery:" + lengthJsonArr);

				for (int i = 0; i < lengthJsonArr; i++) {
					img_ar.add(getBitmapFromURL(jsonimages.getString(i)));
				}
				System.out.println("IMAGE_URL:" + img_ar);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
//			if (mDialog != null)
//				mDialog.dismiss();

			// adapter=new CustomAdapter(GalleryActivity.this, img_ar);
			// gridView.setAdapter(adapter);
			// mDialog.dismiss();

		}

	}

	public Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);

			Bitmap bm = Bitmap.createScaledBitmap(myBitmap, 400, 250, true);

			return bm;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
