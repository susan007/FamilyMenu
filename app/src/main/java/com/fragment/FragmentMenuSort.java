package com.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghong.tool.MenuTool;
import com.ghong.tool.ParseJsonobject;
import com.hong.adapter.MenuSortAdapter;
import com.hong.adapter.SimpleAdapter;
import com.hong.adapter.SimpleViewHolder;
import com.hong.bean.bySort.MenuSortDegital;
import com.hong.bean.bySort.MenuSortDegitalStep;
import com.hong.bean.bySort.MenuSortResult;
import com.inthecheesefactory.lab.designlibrary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by susan on 16-5-31.
 * 按照菜系分类查找菜名
 * 备注：本来左侧侧滑栏有四个分类 这个属于按照分类搜索菜谱 和按照标签搜索重复了 所以去掉这个类
 * 这个类使用了一个RecycleView  却没显示 至今原因不明  后续再看看
 *
 */
public class FragmentMenuSort extends Fragment {

    private TextView txt;
    private RecyclerView recycleView;
    private View view;
    private LinearLayoutManager llm;
    private SimpleAdapter adapter;
    //标签文字数据
    private ArrayList<String> sortTxtlist;

    private ParseJsonobject pjb;

    //实体
    private List<MenuSortDegital> msd;
    private List<MenuSortDegitalStep> msds;

    private List<String> titleList;


    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //这个没有执行
                    adapter=new SimpleAdapter<String>(getContext(),R.layout.fragment_menu_sort_item,sortTxtlist) {
                        @Override
                        protected void convert(SimpleViewHolder holder, String item) {
                            holder.setText(R.id.menu_sort_txt,item);
                            txt.setText("我爱吃糖");
                        }
                    };
//
                    recycleView.setAdapter(adapter);
                    recycleView.setLayoutManager(llm);
                    break;
                default:

                    break;
            }
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_sort, container, false);
        initView();
        initListener();

        return view;
    }

    public void initView() {
        pjb = new ParseJsonobject();
        msd = new ArrayList<MenuSortDegital>();
        msds = new ArrayList<MenuSortDegitalStep>();
        sortTxtlist = new ArrayList<String>();
        titleList = new ArrayList<String>();

        txt=(TextView)view.findViewById(R.id.txt);

        recycleView = (RecyclerView) view.findViewById(R.id.menu_sort_recycleview);
        llm = new LinearLayoutManager(getContext());
    }

    public void initListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //给list中添加或得到的标签的数据
                startCheckMenu();

            }
        }).start();

    }

    /**
     * 获取网络数据  得到各种分类
     */
    public void startCheckMenu() {
        //解析得到的数据
        Log.e("dian", MenuTool.getRequest2());
        if (!TextUtils.isEmpty(MenuTool.getRequest2())) {
            //解析标签
            MenuSortResult msr = pjb.parseMenuSortResult(MenuTool.getRequest2());

            if (msr.getResultcode() != null && msr.getResultcode().equals("200")) {
                //得到分类总结果类  第一个list
                msd = msr.getResult();
                for (MenuSortDegital i : msd) {
                    //得到第二个list
                    msds = i.getList();
                    for (MenuSortDegitalStep j : msds) {
                        Log.e("dian", j.getName());
                        //得到所有的菜系
                        sortTxtlist.add(j.getName());
                    }
                }
                Message messageOK = new Message();
                messageOK.what = 1;
                handler.sendMessage(messageOK);
            }
        }
    }
}
