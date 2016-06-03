package com.hong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by leesure on 16-5-31.
 */
public class SimpleViewHolder extends RecyclerView.ViewHolder {

    private View convertView;
    private Context context;
    /**save view*/
    private SparseArray<View> views;


    public SimpleViewHolder(Context context,View itemview){
        super(itemview);
        this.context=context;
        this.views=new SparseArray<View>();
        this.convertView=itemview;
    }

    /**
     * to get the type of a view
     * @param viewId
     * @param <T>
     * @return view
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * set text of a textview or a button
     * @param viewId
     * @param str
     * @return SimpleViewHolder of Chaining
     */
    public SimpleViewHolder setText(int viewId, String str){
        View view=getView(viewId);
        if(view instanceof TextView){
            TextView textView=getView(viewId);
            textView.setText(str);
        }else {
            Button button=getView(viewId);
            button.setText(str);
        }
        return this;
    }

    /**
     * set color of a text or a button
     * @param viewId
     * @param color
     * @return SimpleViewHolder of Chaining
     */
    public SimpleViewHolder setTextColor(int viewId, int color){
        View view=getView(viewId);
        if(view instanceof TextView){
            TextView textView=getView(viewId);
            textView.setTextColor(color);
        }else {
            Button button=getView(viewId);
            button.setTextColor(color);
        }
        return this;
    }

    /**
     * set image of a imageview
     * @param viewId
     * @param imgId
     * @return SimpleViewHolder of Chaining
     */
    public SimpleViewHolder setImageResource(int viewId, int imgId){
        ImageView view=getView(viewId);
        view.setImageResource(imgId);
        return this;
    }

    /**
     * use Glide to get the img from network
     * @param viewId
     * @param url
     * @return
     */
    public SimpleViewHolder setImageUrl(int viewId,String url){
        ImageView view=getView(viewId);
        Glide.with(context).load(url).crossFade().into(view);
        return this;
    }

    /**
     * set the visibility of a view
     * @param viewId
     * @param bl
     * @return
     */
    public SimpleViewHolder setVisibility(int viewId,boolean bl){
        View view=getView(viewId);
        view.setVisibility(bl?View.VISIBLE:View.GONE);
        return this;
    }



}
