package com.foxysy.foxysy;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.foxysy.api.ApiConnection;
import com.foxysy.model.EventDetails;
import com.foxysy.utils.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by dev on 5/13/2015.
 */
public class Splash extends Activity {
    private static int SPLASH_TIME_OUT = 4000;
    ApiConnection apiconnection;
    ImageView splash_img;
    public static String response, message, title, content, color_header_str;
    public static int gallary_int,contact_int;
    String gallary_api,contact_api;
    public static ArrayList<String> background_header_ar = new ArrayList<String>();
    public static ArrayList<String> background_footer_ar = new ArrayList<String>();
    public static final String MY_PRS_NAME = "MyPrefsFile";
    SharedPreferences sha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        splash_img = (ImageView) findViewById(R.id.pizza);
        apiconnection = new ApiConnection();



        if (Utility.isNetworkconn(Splash.this)) {
            new GetDetails().execute();

        } else {

            Toast.makeText(Splash.this, "No Internet Connection",
                    Toast.LENGTH_SHORT).show();

        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                System.out.println("splash");
                if (Utility.isNetworkconn(Splash.this)) {
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);

                    finish();
                }else{
                    openDialog();
                }
                }
            }

            ,SPLASH_TIME_OUT);
            System.out.println("splashout");
        }

        private class GetDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            response = apiconnection.getAllDetails();
            System.out.println("HomeActivity:" + response);
            message = "";
            background_header_ar.clear();


            EventDetails event_detail = new EventDetails();

            try {
                JSONObject jsonobject = new JSONObject(response);
                // event_detail.setHostname(jsonobject.optString("host").toString());
                message = jsonobject.optString("host").toString();
                gallary_api = jsonobject.optString("gallery").toString();
                gallary_int = Integer.parseInt(gallary_api);

                contact_api = jsonobject.optString("contact").toString();
                contact_int = Integer.parseInt(contact_api);

                SharedPreferences.Editor editor = getSharedPreferences(MY_PRS_NAME, MODE_PRIVATE).edit();

                editor.putInt("gallary",gallary_int);
                editor.putInt("contact",contact_int);
                editor.commit();

                System.out.println("gallary"+gallary_int+"contact"+contact_api);
                JSONObject jsonstyle = jsonobject.optJSONObject("style");
                JSONObject jsonheader = jsonstyle.optJSONObject("header");
                JSONObject jsonfooter = jsonstyle.optJSONObject("footer");
                color_header_str = jsonheader.optString("color").toString();
                // event_detail.setColor_header_str(jsonheader.optString("color").toString());
                event_detail.setColor_footer_str(jsonfooter.optString("color")
                        .toString());
                JSONArray jsonbackground_header = jsonheader
                        .optJSONArray("background");
                JSONArray jsonbackground_footer = jsonfooter
                        .optJSONArray("background");
                int lengthbackground_header = jsonbackground_header.length();
                int lengthbackground_footer = jsonbackground_footer.length();

                for (int i = 0; i < lengthbackground_header; i++) {
                    background_header_ar.add(jsonbackground_header.getString(i)
                            .toString());
                    // event_detail.setBackground_header_ar(background_header_ar);
                }

                for (int i = 0; i < lengthbackground_footer; i++) {
                    background_footer_ar.add(jsonbackground_footer.getString(i)
                            .toString());
                    // event_detail.setBackground_footer_ar(background_footer_ar);
                }

                JSONObject jsonpages = jsonobject.optJSONObject("pages");
                JSONObject jsonhome = jsonpages.optJSONObject("home");

                event_detail.setTitle(jsonhome.optString("title").toString());
                event_detail.setContent(jsonhome.optString("content")
                        .toString());


                title = jsonhome.optString("title").toString();
                content = jsonhome.optString("content").toString();


            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("SPLASH SCREEN STATUS:" + title + "," + content);

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);


        }

    }
    private void openDialog() {

        final Dialog dialog = new Dialog(Splash.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCancelable(false);
        dialog.show();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

				finish();
                dialog.dismiss();
            }
        });

    }

}
