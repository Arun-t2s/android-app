package com.foxysy.foxysy;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.foxysy.api.ApiConnection;
import com.foxysy.model.EventDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;;

public class ContactActivity extends Activity {


	TextView txt_monstart, txt_monclose, txt_tuestart, txt_tueclose,
			txt_wedstart, txt_wedclose, txt_thustart, txt_thuclose,
			txt_fristart, txt_friclose, txt_satstart, txt_satclose,
			txt_sunstart, txt_sunclose, txt_address, txt_addressone,
			txt_postcode, txt_phone;

	// latitude and longitude
	double latit ;
	double longi ;
	ImageView map_trans;

	Fragment map;
	private GoogleMap googleMap;

	ArrayList<String> mon_ar = new ArrayList<String>();
	ArrayList<String> tue_ar = new ArrayList<String>();
	ArrayList<String> wed_ar = new ArrayList<String>();
	ArrayList<String> thu_ar = new ArrayList<String>();
	ArrayList<String> fri_ar = new ArrayList<String>();
	ArrayList<String> sat_ar = new ArrayList<String>();
	ArrayList<String> sun_ar = new ArrayList<String>();
	EventDetails event;

	ArrayList<EventDetails> arr;
	HomeActivity con;

	ScrollView scrollview_contact;
	RelativeLayout top_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.contact_tab);

		latit=Double.parseDouble(HomeActivity.lat);
		longi=Double.parseDouble(HomeActivity.longi);
		System.out.println("LAT:"+latit+"LONG:"+longi);
		try {
			// Loading map
			initilizeMap();
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.getUiSettings().setZoomControlsEnabled(false);

			// Enable / Disable Compass icon
			googleMap.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			// Enable / Disable zooming functionality
			googleMap.getUiSettings().setZoomGesturesEnabled(true);


			CameraPosition cameraPosition = new CameraPosition.Builder().target(
					new LatLng(latit, longi)).zoom(13).build();

			MarkerOptions marker = new MarkerOptions().position(new LatLng(latit, longi)).title("Hello Maps ");
			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map32));

// adding marker
			googleMap.addMarker(marker);
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


		} catch (Exception e) {
			e.printStackTrace();
		}

		top_bar = (RelativeLayout) findViewById(R.id.top_bar);
		int h = top_bar.getHeight();
		ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
		mDrawable.getPaint().setShader(
				new LinearGradient(0, 0, 0, h, Color
						.parseColor(Splash.background_header_ar.get(0)),
						Color.parseColor(Splash.background_header_ar
								.get(1)), Shader.TileMode.REPEAT));
		top_bar.setBackgroundDrawable(mDrawable);
		txt_monstart = (TextView) findViewById(R.id.txt_monstart);
		txt_monclose = (TextView) findViewById(R.id.txt_monclose);
		txt_tuestart = (TextView) findViewById(R.id.txt_tuestart);
		txt_tueclose = (TextView) findViewById(R.id.txt_tueclose);

		txt_wedstart = (TextView) findViewById(R.id.txt_wedstart);
		txt_wedclose = (TextView) findViewById(R.id.txt_wedclose);
		txt_thustart = (TextView) findViewById(R.id.txt_thustart);
		txt_thuclose = (TextView) findViewById(R.id.txt_thuclose);

		txt_fristart = (TextView) findViewById(R.id.txt_fristart);
		txt_friclose = (TextView) findViewById(R.id.txt_friclose);
		txt_satstart = (TextView) findViewById(R.id.txt_satstart);
		txt_satclose = (TextView) findViewById(R.id.txt_satclose);
		txt_sunstart = (TextView) findViewById(R.id.txt_sunstart);
		txt_sunclose = (TextView) findViewById(R.id.txt_sunclose);

		txt_address = (TextView) findViewById(R.id.txt_address);
		txt_addressone = (TextView) findViewById(R.id.txt_addressone);
		txt_postcode = (TextView) findViewById(R.id.txt_postcode);
		txt_phone = (TextView) findViewById(R.id.txt_phone);
		scrollview_contact=(ScrollView)findViewById(R.id.scrollview_contact);
		map_trans = (ImageView)findViewById(R.id.mapimag);
		scrollview_contact.requestDisallowInterceptTouchEvent(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			scrollview_contact.setOverScrollMode(View.OVER_SCROLL_NEVER);
	    }
		//scrollview_contact.setOverScrollMode(View.OVER_SCROLL_NEVER);

		txt_address.setText(HomeActivity.address_one + " "
				+ HomeActivity.address_two);
		txt_addressone.setText(HomeActivity.address_three);
		txt_postcode.setText(HomeActivity.postal_code);

		txt_phone.setText(HomeActivity.phone);
		txt_phone.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		txt_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
					try {
						String phonenumber = txt_phone.getText().toString();
						Intent intent = new Intent(Intent.ACTION_DIAL);
						System.out.println("phonenumber:" + phonenumber);
						intent.setData(Uri.parse("tel:01782367477"));
						startActivity(intent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(ContactActivity.this, "Device Doesn't support to make call", Toast.LENGTH_SHORT).show();
				}


			}
		});


		map_trans.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
					case MotionEvent.ACTION_DOWN:
						// Disallow ScrollView to intercept touch events.
						scrollview_contact.requestDisallowInterceptTouchEvent(true);
						// Disable touch on transparent view
						return false;

					case MotionEvent.ACTION_UP:
						// Allow ScrollView to intercept touch events.
						scrollview_contact.requestDisallowInterceptTouchEvent(false);
						return true;

					case MotionEvent.ACTION_MOVE:
						scrollview_contact.requestDisallowInterceptTouchEvent(true);
						return false;

					default:
						return true;
				}
			}
		});



		this.mon_ar = HomeActivity.mon_ar;
		this.tue_ar = HomeActivity.tue_ar;
		this.wed_ar = HomeActivity.wed_ar;
		this.thu_ar = HomeActivity.thu_ar;
		this.fri_ar = HomeActivity.fri_ar;
		this.sat_ar = HomeActivity.sat_ar;
		this.sun_ar = HomeActivity.sun_ar;

		txt_monstart.setText(mon_ar.get(0));
		txt_monclose.setText(mon_ar.get(1));
		txt_tuestart.setText(tue_ar.get(0));
		txt_tueclose.setText(tue_ar.get(1));
		txt_wedstart.setText(wed_ar.get(0));
		txt_wedclose.setText(wed_ar.get(1));
		txt_thustart.setText(thu_ar.get(0));
		txt_thuclose.setText(thu_ar.get(1));
		txt_fristart.setText(fri_ar.get(0));
		txt_friclose.setText(fri_ar.get(1));
		txt_satstart.setText(sat_ar.get(0));
		txt_satclose.setText(sat_ar.get(1));
		txt_sunstart.setText(sun_ar.get(0));
		txt_sunclose.setText(sun_ar.get(1));

		// apiconnection = new ApiConnection();

		/*
		 * if (Utility.isNetworkconn(ContactActivity.this)) { new
		 * GetDetails().execute(); } else {
		 * 
		 * Toast.makeText(ContactActivity.this, "No Internet Connection",
		 * Toast.LENGTH_SHORT).show(); //
		 * Utility.showforgetMsg(HomeActivity.this,"No Internet Connection"); }
		 */
	}




	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}


	

}
