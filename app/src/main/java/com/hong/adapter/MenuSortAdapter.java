package com.hong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.inthecheesefactory.lab.designlibrary.R;

import java.util.ArrayList;

/**
 * @author deman.lu
 * @version on 2016-03-10 14:19
 */
public class MenuSortAdapter extends RecyclerView.Adapter<MenuSortAdapter.RecycleViewHolder> {

    private Context context;
    //这个是得到的标签文字数据
    private ArrayList<String> mDatas;
    private int[] imgIDs = {R.drawable.menu_chuangyi, R.drawable.menu_family,
            R.drawable.menu_fast, R.drawable.menu_nood, R.drawable.menu_seasoning,
            R.drawable.menu_soup, R.drawable.menu_sucai, R.drawable.menu_sweet,R.drawable.menu_sweet};

    public MenuSortAdapter(Context context, ArrayList<String> mStrings) {
        this.context = context;
        this.mDatas = mStrings;
    }

    /**
     * 布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecycleViewHolder holder = new RecycleViewHolder(LayoutInflater.from(context)
//                .inflate(R.layout.fragment_menu_sort_item, parent, false));

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_menu_sort_item,parent,false);
        RecycleViewHolder vh = new RecycleViewHolder(view);

        return vh;
    }

    /**
     * 数据处理
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.menuSortTxt.setText(mDatas.get(position));
        //设置标签背景图  暂时按照固定八个标签来设置的。。。
//        holder.menuSortImg.setImageResource(imgIDs[position]);
    }

    /**
     * 得到item的size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView menuSortTxt;
//        ImageView menuSortImg;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            menuSortTxt = (TextView) itemView.findViewById(R.id.menu_sort_txt);
//            menuSortImg = (ImageView) itemView.findViewById(R.id.menu_sort_img);
        }
    }
}