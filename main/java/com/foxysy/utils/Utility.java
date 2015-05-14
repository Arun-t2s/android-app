package com.foxysy.utils;

import com.foxysy.foxysy.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

public class Utility {

	static Context context;
	static String dialog;

	public static boolean isNetworkconn(Context ctx) {
		context = ctx;

		ConnectivityManager connectivity = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}

		return false;
	}

	public static void showProgress(Context context, String view) {
		
		final Dialog opendialog = new Dialog(context);
		opendialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		opendialog.setContentView(R.layout.custom_progressbar);
		opendialog.setCancelable(false);
		opendialog.show();
		opendialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

		ProgressBar progressbar = (ProgressBar) opendialog
				.findViewById(R.id.progressbar);
		
		//progressbar.setVisibility(View.INVISIBLE);
		
		
		if (view.equalsIgnoreCase("show")) {
			System.out.println("progressdialogshow");
			//opendialog.show();
			
			progressbar.setVisibility(View.VISIBLE);
		} else{
			System.out.println("progressdialogdismiss");
			
			opendialog.dismiss();
			progressbar.setVisibility(View.INVISIBLE);
		}
	}
}
