package com.hong.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hong.activity.SearchByMenuNameStepActivty;
import com.hong.adapter.MenuLabelAdapter;
import com.hong.adapter.MenuResultAdapter;
import com.hong.bean.bySort.MenuSortDegital;
import com.hong.bean.bySort.MenuSortDegitalStep;
import com.hong.bean.bySort.MenuSortResult;
import com.hong.bean.byname.MData;
import com.hong.bean.byname.MStep;
import com.hong.bean.byname.MenuDigital;
import com.hong.bean.byname.MenuResult;
import com.hong.tools.MenuTool;
import com.hong.tools.ParseJsonobject;
import com.inthecheesefactory.lab.designlibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by susan on 16-5-31.
 */
public class FragmentMenuLabel extends Fragment {

    private ListView menuLabelList;
    private MenuLabelAdapter mla;
    private ParseJsonobject pjb;
    //实体
    private List<MenuSortDegital> msd;
    private List<MenuSortDegitalStep> msds;
    private List<String> sortTxtlist;
    // title的list
    private List<String> titleList;
    private List<String> stepLabelList;
    private View view;
    // tag的list
    private List<String> tagList;
    // 完成图的list
    private List<String> imgList;

    private MenuResultAdapter mra;

    // 菜谱搜索结果
    private MenuResult mr;
    // 具体菜谱数据
    private MData md;
    // 做菜的详细信息描述
    private List<MenuDigital> mdg;
    // 做菜的详细步骤
    private List<MStep> ms;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置列表
                    setMenuListView();
                    setMenuLabelClick();
                    Log.e("dian", msg.obj + "");
                    break;
                case 2:
                    // 设置结果列表
                    setMenuLabelResultList();
                    setMenuLabelStepClick();
                    break;
                case 0:
                    Toast.makeText(getContext(), "查询错误", Toast.LENGTH_SHORT).show();
                    break;
                default:

                    break;
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_label, container, false);
        initView();
        initListener();
        return view;
    }

    public void initView() {
        menuLabelList = (ListView) view.findViewById(R.id.menu_label_list);
        msd = new ArrayList<MenuSortDegital>();
        msds = new ArrayList<MenuSortDegitalStep>();
        pjb = new ParseJsonobject();

    }

    public void initListener() {
        //搜索都有什么菜系
        new Thread(new Runnable() {
            @Override
            public void run() {
                startCheckMenu();
            }
        }).start();

    }

    /**
     * 点击查询菜谱步骤
     */
    public void setMenuLabelStepClick() {
        //菜谱结果列表
        menuLabelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 根据id加载不同的做菜细节
                Intent intent = new Intent(getContext(),
                        SearchByMenuNameStepActivty.class);
                Bundle bundle = new Bundle();
                // 传一个对象过去？
                bundle.putSerializable("MD", mdg.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
    }

    /**
     * 设置具体菜系的ListView
     */
    public void setMenuLabelResultList() {
        mra = new MenuResultAdapter(getContext(),
                initMenuResult(),
                R.layout.activity_menu_search_list_item,
                new String[]{"title", "tag", "img"},
                new int[]{R.id.menu_finished_title, R.id.menu_finished_tags,
                        R.id.menu_finished_img});
        menuLabelList.setAdapter(mra);

    }

    /**
     * 设置具体菜系的菜的结果
     *
     * @return
     */
    public List<Map<String, Object>> initMenuResult() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < stepLabelList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", stepLabelList.get(i));
            map.put("tag", tagList.get(i));
            map.put("img", imgList.get(i));
            list.add(map);
        }
        // 绑定数据
        return list;
    }

    /**
     * 设置菜系的ListView
     */
    public void setMenuListView() {
        mla = new MenuLabelAdapter(getContext(),
                initData(),
                R.layout.fragment_menu_sort_item,
                new String[]{"title"},
                new int[]{R.id.menu_sort_txt});
        menuLabelList.setAdapter(mla);
    }

    public List<Map<String, Object>> initData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < titleList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", titleList.get(i));
            list.add(map);
        }
        // 绑定数据
        return list;
    }

    /**
     * 点击查询菜谱
     */
    public void setMenuLabelClick() {
        //列表点击事件
        menuLabelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //按照分类ID查询菜类
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //根据id请求具体菜谱  显示按照菜名的Fragment 更换Fragment...
                        startSearchMenuByTag(String.valueOf(position + 1));
                        Log.e("10001","position"+position);
                    }
                }).start();

            }
        });
    }

    /**
     * 获取标签数据  得到各种菜系分类
     */
    public void startCheckMenu() {
        //清空存放菜系名的列表
        titleList = new ArrayList<String>();
        //解析得到的数据
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
                        //得到所有的菜系
                        titleList.add(j.getName());
                    }
                }
                Message messageOK = new Message();
                messageOK.what = 1;
                handler.sendMessage(messageOK);
            } else {
                Message messageEr = new Message();
                messageEr.what = 0;
                handler.sendMessage(messageEr);
            }
        } else {
            Message messageEr = new Message();
            messageEr.what = 0;
            handler.sendMessage(messageEr);
        }
    }

    /**
     * 按照标签ID查询菜品
     */
    public void startSearchMenuByTag(String tagId) {
        //清空存放菜系名的列表
        stepLabelList = new ArrayList<String>();
        tagList = new ArrayList<String>();
        imgList = new ArrayList<String>();
        if (!TextUtils.isEmpty(MenuTool.getRequest3(tagId))) {
            // 解析json数据
            mr = pjb.parseMenuResult2(MenuTool.getRequest3(tagId));
            // get search result  空指针异常
            if (mr.getResultcode() != null && mr.getResultcode().equals("200")) {
                md = mr.getResult();
                if (!TextUtils.isEmpty(md.getPn())
                        && !TextUtils.isEmpty(md.getRn())
                        && !TextUtils.isEmpty(md.getTotalNum())
                        && md.getData() != null) {

                    // 获得所有菜谱的细节
                    mdg = md.getData();
                    for (MenuDigital i : mdg) {

                        if (!TextUtils.isEmpty(i.getTitle())
                                && !TextUtils.isEmpty(i.getBurden())
                                && !TextUtils.isEmpty(i.getId())
                                && !TextUtils.isEmpty(i.getImtro())
                                && !TextUtils.isEmpty(i
                                .getIngredients())
                                && !TextUtils.isEmpty(i.getTags())
                                && i.getAlbums() != null
                                && i.getSteps() != null) {

                            // 获取标题
                            stepLabelList.add(i.getTitle());
                            // 获取tag
                            tagList.add(i.getTags());
                            // 获取图片
                            imgList.add(i.getAlbums().get(0));

                            // 做菜的详细步骤
                            ms = i.getSteps();
                        } else {
                            Message messageEr = new Message();
                            messageEr.what = 0;
                            handler.sendMessage(messageEr);
                        }

                    }

                    Message messageOK = new Message();
                    messageOK.what = 2;
                    handler.sendMessage(messageOK);

                }
            } else {
                Message messageEr = new Message();
                messageEr.what = 0;
                handler.sendMessage(messageEr);
            }
        } else {
            Message messageEr = new Message();
            messageEr.what = 0;
            handler.sendMessage(messageEr);
        }

    }

    /**
     * 从fragment返回fragment
     */
    @Override
    public void onResume() {
        super.onResume();
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    setMenuListView();
                    setMenuLabelClick();
                    return true;
                }
                return false;
            }
        });
    }
}
