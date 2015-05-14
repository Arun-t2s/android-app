package com.foxysy.foxysy;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.foxysy.utils.Utility;

public class MainActivity extends TabActivity implements OnTabChangeListener {

    //	private TabHost myTabHost;
    TabSpec spec;
    TabHost tabHost;
    public static final String MY_PRS_NAME = "MyPrefsFile";
    int gallary_ena, contact_ena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sample);

        Resources res = getResources(); // Resource object to get Drawables
        tabHost = getTabHost(); // The activity TabHost
        tabHost.setOnTabChangedListener(this);
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab
        SharedPreferences prefs = getSharedPreferences(MY_PRS_NAME, MODE_PRIVATE);
        gallary_ena = prefs.getInt("gallary", 0);
        contact_ena = prefs.getInt("contact", 0);

        intent = new Intent().setClass(this, HomeActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec = tabHost
                .newTabSpec("home")
                .setIndicator(
                        getTabIndicator(tabHost.getContext(), "Home",
                                R.drawable.home64)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, OrderNowActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec = tabHost
                .newTabSpec("Order")
                .setIndicator(
                        getTabIndicator(tabHost.getContext(), "Order Now",
                                R.drawable.ordernow64)).setContent(intent);
        tabHost.addTab(spec);

        if (gallary_ena == 1) {

            intent = new Intent().setClass(this, GalleryActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            spec = tabHost
                    .newTabSpec("Gallery")
                    .setIndicator(
                            getTabIndicator(tabHost.getContext(), "Gallery",
                                    R.drawable.gallery64)).setContent(intent);
            tabHost.addTab(spec);

        }

        if (contact_ena == 1) {
            intent = new Intent().setClass(this, ContactActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            spec = tabHost
                    .newTabSpec("Contact")
                    .setIndicator(
                            getTabIndicator(tabHost.getContext(), "Contact",
                                    R.drawable.contact64)).setContent(intent);
            tabHost.addTab(spec);
        }
        if (Utility.isNetworkconn(MainActivity.this)) {

            for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor(Splash.background_footer_ar.get(0)));
            }

            tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor(Splash.background_header_ar.get(1)));

        }
        tabHost.setCurrentTab(0);


    }

    @Override
    public void onTabChanged(String tabId) {
        // TODO Auto-generated method stub
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor(Splash.background_footer_ar.get(0)));
        }

        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor(Splash.background_header_ar.get(1)));
    }


    private void setNewTab(Context context, TabHost tabHost, String tag,
                           String title, int icon, int contentID) {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(getTabIndicator(tabHost.getContext(), title, icon));
        tabSpec.setContent(contentID);
        tabHost.addTab(tabSpec);
//		setTabColor(tabHost);
    }

    private View getTabIndicator(Context context, String title, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout,
                null);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(icon);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;

    }


}
