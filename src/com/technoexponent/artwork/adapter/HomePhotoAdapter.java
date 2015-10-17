package com.technoexponent.artwork.adapter;



import com.squareup.picasso.Picasso;
import com.technoexponent.artwork.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class HomePhotoAdapter extends BaseAdapter{

	private Context context;
	Activity activity;
	LayoutInflater inflater;
	public HomePhotoAdapter(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity= activity;
		//		 this.activity =activity;
	}

	public int getCount() {
		return 51;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) 
		{
			convertView = inflater.inflate(R.layout.homegridview_listing, null);
		}

		
		ImageView home_img = (ImageView) convertView.findViewById(R.id.home_img);
//		imageView.setLayoutParams(new GridView.LayoutParams(size, size));
		int width= activity.getResources().getDisplayMetrics().widthPixels;
		Picasso
        .with(activity)
        .load(R.drawable.koala)
        .centerCrop().resize(width/2,width/2)       
        .into(home_img);


		return convertView;
	}





}