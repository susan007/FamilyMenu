package com.hong.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hong.activity.SearchByMenuNameStepActivty;
import com.hong.adapter.MenuResultAdapter;
import com.hong.bean.byname.MData;
import com.hong.bean.byname.MStep;
import com.hong.bean.byname.MenuDigital;
import com.hong.bean.byname.MenuResult;
import com.hong.tools.MenuTool;
import com.hong.tools.ParseJsonobject;
import com.hong.tools.SoftInputManager;
import com.inthecheesefactory.lab.designlibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by susan on 16-5-31.
 * <p/>
 * 问题：布局里已经有了一个RecycleView为什么还要加一个ListView去实现？我脑子傻了
 */
public class FragmentMenuName extends Fragment {

    private LinearLayoutManager llm;
    private RecyclerView menuResultRecView;

    // 解析数据
    private ParseJsonobject pjb;
    // 菜谱搜索结果
    private MenuResult mr;
    // 具体菜谱数据
    private MData md;
    // 做菜的详细信息描述
    private List<MenuDigital> mdg;
    // 做菜的详细步骤
    private List<MStep> ms;

    // title的list
    private List<String> titleList;
    // tag的list
    private List<String> tagList;
    // 完成图的list
    private List<String> imgList;

    // 蔬菜列表
    private ListView menuListView;
    private MenuResultAdapter mra;

    private Button checkBtn;
    private EditText menuInput;
    private MenuTool mt;

    private String tag = "hong";

    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;

    private View view;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置列表
                    setMenuListView();
                    break;
                case 0:

//                    Snackbar.make(rootLayout,"查询错误，未找到该菜",Snackbar.LENGTH_SHORT).setAction("Notice",new View.OnClickListener(){
//                        @Override
//                        public void onClick(View v) {
//                            //可以添加事件动作
//                            Log.e("CodeLabActivity","哈哈哈哈哈哈哈哈哈哈哈哈哈，我被点击了");
//                        }
//                    }).show();
                    Toast.makeText(getContext(), "没有查询到该菜", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_name, container, false);
        initView();
        initListener();

        return view;
    }

    public void initView() {
        menuInput = (EditText) view.findViewById(R.id.menu_check);
        checkBtn = (Button) view.findViewById(R.id.check_btn);
        menuListView = (ListView) view.findViewById(R.id.menu_search_result_list);
        titleList = new ArrayList<String>();
        tagList = new ArrayList<String>();
        imgList = new ArrayList<String>();
        //解析json数据
        pjb = new ParseJsonobject();
        mr = new MenuResult();

        menuResultRecView = (RecyclerView) view.findViewById(R.id.menu_result_recyclerview);
    }

    public void initListener() {
        //菜谱结果列表
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
        //查询按钮
        checkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 先把上次的ListView数据清空 不然查询到的数据会累加到列表
                titleList = new ArrayList<String>();
                tagList = new ArrayList<String>();
                imgList = new ArrayList<String>();
                SoftInputManager.closeSoftManager(getContext());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startCheckMenu();

                    }
                }).start();

                //菜谱列表
                menuResultRecView.setHasFixedSize(true);
                llm = new LinearLayoutManager(getContext());
                menuResultRecView.setLayoutManager(llm);
            }
        });

    }

    /**
     * 设置ListView
     */
    public void setMenuListView() {
        mra = new MenuResultAdapter(getContext(), initData(),
                R.layout.activity_menu_search_list_item, new String[]{
                "title", "tag", "img"}, new int[]{
                R.id.menu_finished_title, R.id.menu_finished_tags,
                R.id.menu_finished_img});
        menuListView.setAdapter(mra);
    }

    /**
     * 初始化ListView里的数据
     *
     * @return
     */
    public List<Map<String, Object>> initData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < titleList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", titleList.get(i));
            map.put("tag", tagList.get(i));
            map.put("img", imgList.get(i));

            list.add(map);
        }
        // 绑定数据
        return list;
    }


    /**
     * 开启一个线程开始查询菜
     */
    public void startCheckMenu() {
        String menu_name = menuInput.getText().toString().trim();
        if (!TextUtils.isEmpty(MenuTool.getRequest1(menu_name))) {
            // 解析json数据
            mr = pjb.parseMenuResult2(MenuTool.getRequest1(menu_name));
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
                            titleList.add(i.getTitle());
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
                    messageOK.what = 1;
                    messageOK.obj = mr.getResultcode();
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

}
