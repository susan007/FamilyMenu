package com.hong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inthecheesefactory.lab.designlibrary.R;

import java.util.List;
import java.util.Map;

/**
 * Created by susan on 16-4-21.
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder>{

    private List<? extends Map<String, ?>> mData;

    private Context mContext;

    public MyRecycleViewAdapter( Context context , List<? extends Map<String, ?>> data)
    {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_menu_search_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i )
    {
        // 给ViewHolder设置元素

        viewHolder.menuTitle.setText("");
        viewHolder.menuTag.setText("");
//        viewHolder.menuImg.setImageDrawable(0);
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return 0;
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView menuTitle;
        public TextView menuTag;
        public ImageView menuImg;

        public ViewHolder( View v )
        {
            super(v);
            menuTag = (TextView) v.findViewById(R.id.menu_finished_tags);
            menuTitle = (TextView) v.findViewById(R.id.menu_finished_title);
            menuImg = (ImageView) v.findViewById(R.id.menu_finished_img);
        }
    }
}