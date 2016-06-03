package com.hong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by leesure on 16-5-31.
 */
public abstract class SimpleAdapter<T> extends RecyclerView.Adapter {

    private Context mcontext;
    private int mlayoutResId;
    private List<T> mdata;
    private LayoutInflater mLayoutInflater;
    /**Item的clicklistener*/
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T item=mdata.get(position);
        convert((SimpleViewHolder)holder,item);
    }

    protected abstract void convert(SimpleViewHolder holder, T item);

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SimpleViewHolder vh=null;
        View itemView=mLayoutInflater.inflate(mlayoutResId,parent,false);
        vh=new SimpleViewHolder(mcontext,itemView);
        initItemClickListener(vh);
        return vh;
    }

    /**
     * 初始化Item的clicklistener
     * @param vh
     */
    private void initItemClickListener(final SimpleViewHolder vh) {
        if(vh!=null){
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onClick(v,vh.getLayoutPosition());
                }
            });
            vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onRecyclerViewItemClickListener.onLongClick(v,vh.getLayoutPosition());
                }
            });

        }

    }

    public SimpleAdapter(Context context, int layoutResId, List<T> data){
        this.mcontext=context;
        this.mlayoutResId=layoutResId;
        this.mdata=data;
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * 设置item的clicklistener
     * @param onRecyclerViewItemClickListener
     */
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        public void onClick(View view, int position);
        public boolean onLongClick(View view, int position);
    }



}
