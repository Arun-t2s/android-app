package com.foxysy.foxysy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.foxysy.api.ApiConnection;
import com.foxysy.model.EventDetails;
import com.foxysy.utils.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

    ApiConnection apiconnection;
    ProgressDialog pDialog;

    public static String message, address_one, address_two, address_three,
            postal_code, lat, longi, phone;
    String response;

    TextView txt_title, txt_content;
    RelativeLayout top_bar;

    ArrayList<EventDetails> eventdetail_ar = new ArrayList<EventDetails>();
    public static ArrayList<Bitmap> img_ar = new ArrayList<Bitmap>();

    public static ArrayList<String> mon_ar = new ArrayList<String>();
    public static ArrayList<String> tue_ar = new ArrayList<String>();
    public static ArrayList<String> wed_ar = new ArrayList<String>();
    public static ArrayList<String> thu_ar = new ArrayList<String>();
    public static ArrayList<String> fri_ar = new ArrayList<String>();
    public static ArrayList<String> sat_ar = new ArrayList<String>();
    public static ArrayList<String> sun_ar = new ArrayList<String>();

    public static ArrayList<String> background_header_ar = new ArrayList<String>();
    public static ArrayList<String> background_footer_ar = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tab);
        apiconnection = new ApiConnection();

        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_content = (TextView) findViewById(R.id.txt_content);
        top_bar = (RelativeLayout) findViewById(R.id.top_bar);


        pDialog = ProgressDialog.show(HomeActivity.this, "", "Please Wait...");

        new Thread() {
            public void run() {

                try {
                    sleep(1500);

                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
                pDialog.dismiss();
            }
        }.start();


        if (Utility.isNetworkconn(HomeActivity.this)) {

            txt_title.setText(Splash.title);
            txt_title.setTextColor(Color.parseColor("#000000"));
            if (Splash.content.equalsIgnoreCase(" ")) {
                txt_content.setText("No Information Available");
            } else {

                txt_content.setText(Html.fromHtml(Splash.content));
                // txt_content.setTextColor(Color.parseColor(color_header_str));
            }

            int h = top_bar.getHeight();
            ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
            mDrawable.getPaint().setShader(
                    new LinearGradient(0, 0, 0, h, Color
                            .parseColor(Splash.background_header_ar.get(0)), Color
                            .parseColor(Splash.background_header_ar.get(1)),
                            Shader.TileMode.REPEAT));
            top_bar.setBackgroundDrawable(mDrawable);
            new GetDetails().execute();

        } else {

            openDialog();
            Toast.makeText(HomeActivity.this, "No Internet Connection",
                    Toast.LENGTH_SHORT).show();

        }

    }

    private class GetDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            response = apiconnection.getAllDetails();
//			System.out.println("HomeActivity:" + response);
            img_ar.clear();
            mon_ar.clear();
            tue_ar.clear();
            wed_ar.clear();
            thu_ar.clear();
            fri_ar.clear();
            sat_ar.clear();
            sun_ar.clear();
            message = "";
            background_header_ar.clear();
            address_one = "";
            address_two = "";
            address_three = "";
            postal_code = "";
            lat = "";
            longi = "";
            phone = "";

            EventDetails event_detail = new EventDetails();

            try {
                JSONObject jsonobject = new JSONObject(response);
                // event_detail.setHostname(jsonobject.optString("host").toString());
                message = jsonobject.optString("host").toString();

//				JSONObject jsonstyle = jsonobject.optJSONObject("style");
//				JSONObject jsonheader = jsonstyle.optJSONObject("header");
//				JSONObject jsonfooter = jsonstyle.optJSONObject("footer");
//				color_header_str = jsonheader.optString("color").toString();
//				// event_detail.setColor_header_str(jsonheader.optString("color").toString());
//				event_detail.setColor_footer_str(jsonfooter.optString("color")
//						.toString());
//				JSONArray jsonbackground_header = jsonheader
//						.optJSONArray("background");
//				JSONArray jsonbackground_footer = jsonfooter
//						.optJSONArray("background");
//				int lengthbackground_header = jsonbackground_header.length();
//				int lengthbackground_footer = jsonbackground_footer.length();
//
//				for (int i = 0; i < lengthbackground_header; i++) {
//					background_header_ar.add(jsonbackground_header.getString(i)
//							.toString());
//					// event_detail.setBackground_header_ar(background_header_ar);
//				}
//
//				for (int i = 0; i < lengthbackground_footer; i++) {
//					background_footer_ar.add(jsonbackground_footer.getString(i)
//							.toString());
//					// event_detail.setBackground_footer_ar(background_footer_ar);
//				}

                JSONObject jsonpages = jsonobject.optJSONObject("pages");
                JSONObject jsonhome = jsonpages.optJSONObject("home");

//				event_detail.setTitle(jsonhome.optString("title").toString());
//				event_detail.setContent(jsonhome.optString("content")
//						.toString());

                JSONObject jsongallery = jsonpages.optJSONObject("gallery");
                JSONArray jsonimages = jsongallery.optJSONArray("images");

                int lengthJsongalleryimages = jsonimages.length();

                for (int i = 0; i < lengthJsongalleryimages; i++) {
                    img_ar.add(getBitmapFromURL(jsonimages.getString(i)));
                    // System.out.println("IMGAINHOMEACTIVITY:"+img_ar);
                    // event_detail.setImages_ar(getBitmapFromURL(jsonimages.getString(i)));

                }


                JSONObject jsoncontact = jsonpages.optJSONObject("contact");
                JSONObject jsonopentimes = jsoncontact
                        .optJSONObject("opentimes");
                JSONObject jsonaddress = jsoncontact.optJSONObject("address");


                address_one = jsonaddress.optString("address1").toString();
                address_two = jsonaddress.optString("address2").toString();
                address_three = jsonaddress.optString("address3").toString();
                postal_code = jsonaddress.optString("postcode").toString();
                lat = jsonaddress.optString("lat").toString();
                longi = jsonaddress.optString("long").toString();
                phone = jsonaddress.optString("phone").toString();

                JSONArray jsonmonday = jsonopentimes.optJSONArray("Monday");
                JSONArray jsonTuesday = jsonopentimes.optJSONArray("Tuesday");
                JSONArray jsonWednesday = jsonopentimes
                        .optJSONArray("Wednesday");
                JSONArray jsonThursday = jsonopentimes.optJSONArray("Thursday");
                JSONArray jsonFriday = jsonopentimes.optJSONArray("Friday");
                JSONArray jsonSaturday = jsonopentimes.optJSONArray("Saturday");
                JSONArray jsonSunday = jsonopentimes.optJSONArray("Sunday");

                int lengthJsonArr_mon = jsonmonday.length();
                int lengthJsonArr_tue = jsonTuesday.length();
                int lengthJsonArr_wed = jsonWednesday.length();
                int lengthJsonArr_thu = jsonThursday.length();
                int lengthJsonArr_fri = jsonFriday.length();
                int lengthJsonArr_sat = jsonSaturday.length();
                int lengthJsonArr_sun = jsonSunday.length();

                for (int i = 0; i < lengthJsonArr_mon; i++) {
                    mon_ar.add(jsonmonday.getString(i));
                    System.out.println("MONDAY:" + mon_ar);
                    // event_detail.setMon_ar(mon_ar);
                    // mon = jsonmonday.getString(i);

                }

                for (int i = 0; i < lengthJsonArr_tue; i++) {
                    tue_ar.add(jsonTuesday.getString(i));
                    // event_detail.setMon_ar(tue_ar);
                    // tue = jsonTuesday.getString(i);

                }

                for (int i = 0; i < lengthJsonArr_wed; i++) {
                    wed_ar.add(jsonWednesday.getString(i));
                    // event_detail.setMon_ar(wed_ar);
                    // wed = jsonWednesday.getString(i);

                }
                for (int i = 0; i < lengthJsonArr_thu; i++) {
                    thu_ar.add(jsonThursday.getString(i));
                    // event_detail.setMon_ar(thu_ar);
                    // thu = jsonThursday.getString(i);

                }

                for (int i = 0; i < lengthJsonArr_fri; i++) {
                    fri_ar.add(jsonFriday.getString(i));
                    // event_detail.setMon_ar(fri_ar);
                    // fri = jsonFriday.getString(i);

                }

                for (int i = 0; i < lengthJsonArr_sat; i++) {
                    sat_ar.add(jsonSaturday.getString(i));
                    // event_detail.setMon_ar(sat_ar);
                    // sat = jsonSaturday.getString(i);

                }
                for (int i = 0; i < lengthJsonArr_sun; i++) {
                    sun_ar.add(jsonSunday.getString(i));
                    // event_detail.setMon_ar(sun_ar);
                    // sun = jsonSunday.getString(i);

                }
                eventdetail_ar.add(event_detail);

            } catch (Exception e) {
                e.printStackTrace();
            }
//			System.out.println("SPLASH SCREEN STATUS:" + title + "," + content);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);


        }

    }

	/*
     * @Override protected void onStart() { // TODO Auto-generated method stub
	 * super.onStart();
	 *
	 * EventDetails event=new EventDetails();
	 *
	 * txt_title.setText(event.getTitle());
	 *
	 * String content=event.getContent(); if(content.equalsIgnoreCase("")){
	 * txt_content.setText("No Information Available"); }else{
	 * txt_content.setText(content); } }
	 */

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

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        System.out.println("ONSTART CALLED IN HOME aCTIVIY");
    }

    private void openDialog() {

        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCancelable(false);
        dialog.show();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

//				finish();
                dialog.dismiss();
            }
        });

    }

    public void showProgress(Context context, String view) {

        final Dialog opendialog = new Dialog(context);
        opendialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        opendialog.setContentView(R.layout.custom_progressbar);
        opendialog.setCancelable(false);

        opendialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        ProgressBar progressbar = (ProgressBar) opendialog
                .findViewById(R.id.progressbar);

        // progressbar.setVisibility(View.INVISIBLE);

        if (view.equalsIgnoreCase("show")) {
            System.out.println("progressdialogshow");
            opendialog.show();

            // progressbar.setVisibility(View.VISIBLE);
        } else if (view.equalsIgnoreCase("dismiss")) {
            System.out.println("progressdialogdismiss");

            opendialog.dismiss();

            // progressbar.setVisibility(View.INVISIBLE);
        } else {
            opendialog.dismiss();
        }
    }

}
