package com.hong.adapter;

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
import com.inthecheesefactory.lab.designlibrary.R;


public class MenuLabelAdapter extends SimpleAdapter {

    private int resource;
    private Context context;
    private List<? extends Map<String, ?>> mData;

    public MenuLabelAdapter(Context context,
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
                .findViewById(R.id.menu_sort_txt);
        title.setText(mData.get(position).get("title").toString());


        return convertView;
    }

    @Override
    public int getCount() {
        // 假设加载的数据量很大
        return mData.size();
    }
}
