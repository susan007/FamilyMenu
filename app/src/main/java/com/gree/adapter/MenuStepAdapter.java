package com.gree.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.okhttpandroid.R;


public class MenuStepAdapter extends SimpleAdapter {

	private List<? extends Map<String, ?>> mData;

	private int resource;
	private Context context;

	
	
	public MenuStepAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.resource=resource;
		this.context=context;
		this.mData=data;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(resource, null);
		}

		TextView tv = (TextView) convertView.findViewById(R.id.menu_finished_step_txt);
		tv.setText(mData.get(position).get("stepTxt").toString());
		
		ImageView iv = (ImageView) convertView.findViewById(R.id.menu_finished_step_img);
		/*
		 * 缺省的占位图片，一般可以设置成一个加载中的进度GIF图，placeholder显示加载时的图片,crossDade(时间)代表淡入淡出
		 */
		Glide.with(context).load(mData.get(position).get("stepImg").toString()).centerCrop()
				.placeholder(R.drawable.loading).crossFade().into(iv);

		return convertView;
	}

	@Override
	public int getCount() {
		// 假设加载的数据量很大
		return mData.size();
	}
}
