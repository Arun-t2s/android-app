package com.foxysy.foxysy;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.foxysy.utils.Utility;

import java.io.File;

public class OrderNowActivity extends Activity{
	
	WebView webView;
	ProgressDialog mDialog;
	RelativeLayout top_bar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ordernow_tab);
		webView = (WebView)findViewById(R.id.webView);



			mDialog = new ProgressDialog(OrderNowActivity.this);
			mDialog.setMessage("Please wait...");
			mDialog.setCancelable(false);
			mDialog.show();

		webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
		webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

		if ( !Utility.isNetworkconn(OrderNowActivity.this) ) { // loading offline
			System.out.println("no network");
			openDialog();
			webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK );
			webView.loadUrl("http://" + HomeActivity.message + "/our_menu.php");
		}else {
			System.out.println("network connected");
			webView.loadUrl("http://" + HomeActivity.message + "/our_menu.php");

		}
		webView.setWebViewClient(new WebViewClient() {

				public void onPageFinished(WebView view, String url) {
					mDialog.dismiss();

				}
			});



	}
	private void openDialog() {

		final Dialog dialog = new Dialog(OrderNowActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.alert_dialog);
		dialog.setCancelable(false);
		dialog.show();
		//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

		Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

		btn_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

//				finish();
				dialog.dismiss();
			}
		});

	}


	@Override
	protected void onDestroy()
	{
		// Clear the cache (this clears the WebViews cache for the entire application)
		//webView.clearCache(false);

		super.onDestroy();
	}

	@Override
	public File getCacheDir()
	{
		// NOTE: this method is used in Android 2.1

		return getApplicationContext().getCacheDir();
	}
}
