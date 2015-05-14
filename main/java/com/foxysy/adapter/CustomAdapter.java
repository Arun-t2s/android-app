package com.foxysy.adapter;

import java.util.ArrayList;

import com.foxysy.foxysy.R;
import com.foxysy.model.EventDetails;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	Context context;
	ArrayList<Bitmap> img_ar;
	
	ArrayList<EventDetails> events;

	public CustomAdapter(Context context, ArrayList<Bitmap> img_ar) {
		this.context = context;
		this.img_ar = img_ar;

	}

	/*public CustomAdapter(Context context,ArrayList<EventDetails> eventdetails) {
		this.context = context;
		this.events = eventdetails;
	}
*/
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_ar.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return img_ar.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	

	public class ListHolder {
		TextView presenter_name, presenter_desc, btn_readmore;
		// ImageView presenter_img;
		ImageView imgIcon;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ListHolder holder;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.listview_data, null);
			holder = new ListHolder();

			

			holder.imgIcon = (ImageView) convertView.findViewById(R.id.img_gallery);

			convertView.setTag(holder);

		} else {
			holder = (ListHolder) convertView.getTag();
		}
		
		holder.imgIcon.setImageBitmap(img_ar.get(position));
		
		/*
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.listview_data, null);
		}

		imgIcon = (ImageView) convertView.findViewById(R.id.img_gallery);

		imgIcon.setImageResource(events.get(position).getImages_ar());*/

		return convertView;
	}

}
