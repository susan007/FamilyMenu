package com.gree.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.okhttpandroid.R;

public class MenuResultAdapter extends SimpleAdapter {

	private int resource;
	private Context context;
	private List<? extends Map<String, ?>> mData;

	public MenuResultAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
		this.resource = resource;
		this.mData = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(resource, null);
		}

		TextView title = (TextView) convertView
				.findViewById(R.id.menu_finished_title);
		title.setText(mData.get(position).get("title").toString());  

		TextView tag = (TextView) convertView
				.findViewById(R.id.menu_finished_tags);
		tag.setText(mData.get(position).get("tag").toString());

		ImageView iv = (ImageView) convertView
				.findViewById(R.id.menu_finished_img);
		/*
		 * 缺省的占位图片，一般可以设置成一个加载中的进度GIF图，placeholder显示加载时的图片,crossDade(时间)代表淡入淡出
		 */
		Glide.with(context).load(mData.get(position).get("img").toString())
				.centerCrop().placeholder(R.drawable.loading).crossFade()
				.into(iv);

		return convertView;
	}

	@Override
	public int getCount() {
		// 假设加载的数据量很大
		return mData.size();
	}
}
