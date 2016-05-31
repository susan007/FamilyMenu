package com.inthecheesefactory.lab.designlibrary;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import android.widget.ListView;
import android.widget.Toast;

import com.ghong.tool.MenuTool;
import com.ghong.tool.ParseJsonobject;
import com.ghong.tool.SoftInputManager;
import com.hong.adapter.MenuResultAdapter;
import com.hong.bean.byname.MData;
import com.hong.bean.byname.MStep;
import com.hong.bean.byname.MenuDigital;
import com.hong.bean.byname.MenuResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeLabActivity extends AppCompatActivity {

    private LinearLayoutManager llm;
    private RecyclerView menuResultRecView;
    private String path="http://www.cnblogs.com/hongdiandian/";
    //给toolBar设置监听
    private Toolbar.OnMenuItemClickListener onMenuItemClick=new Toolbar.OnMenuItemClickListener(){
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_settings:
                    //进入我们的官网
                    Intent intent=new Intent();
                    intent.setData(Uri.parse(path));
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
            return false;
        }
    };
    private NavigationView navigation;
    private CollapsingToolbarLayout ctl;
    private TabLayout tabLayout;
    private Toolbar toolBar;
    private CoordinatorLayout rootLayout;
    private FloatingActionButton fabBtn;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

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

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置列表
                    setMenuListView();
                    Log.e(tag, msg.obj + "");
                    break;
                case 0:

                    Snackbar.make(rootLayout,"查询错误，未找到该菜",Snackbar.LENGTH_SHORT).setAction("Notice",new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            //可以添加事件动作
                            Log.e("CodeLabActivity","哈哈哈哈哈哈哈哈哈哈哈哈哈，我被点击了");
                        }
                    }).show();

                    break;

                default:
                    break;
            }
        };
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_lab);
        initToolbar();
        initInstances();
        initListener();

    }


    private void initToolbar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
//        //设置图标
//        toolBar.setLogo(R.drawable.tt);
//        //设置标题
//        toolBar.setTitle("二次元");
//        //设定左上角的按钮  但要在setSupportActionBar()之后用
//        toolBar.setNavigationIcon(0);
        setSupportActionBar(toolBar);
        toolBar.setOnMenuItemClickListener(onMenuItemClick);
    }
    private void initInstances() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(CodeLabActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabBtn=(FloatingActionButton)findViewById(R.id.fabBtn);
        rootLayout=(CoordinatorLayout)findViewById(R.id.rootLayout);

        ctl=(CollapsingToolbarLayout)findViewById(R.id.title);
        ctl.setTitle("美味佳肴");



        menuInput = (EditText) findViewById(R.id.menu_check);
        checkBtn = (Button) findViewById(R.id.check_btn);
        menuListView = (ListView) findViewById(R.id.menu_search_result_list);
        titleList = new ArrayList<String>();
        tagList = new ArrayList<String>();
        imgList = new ArrayList<String>();

        pjb = new ParseJsonobject();
        mr = new MenuResult();

        navigation=(NavigationView)findViewById(R.id.navigation);
        menuResultRecView=(RecyclerView)findViewById(R.id.menu_result_recyclerview);
    }

    public void initListener(){
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(CodeLabActivity.this, "我遭受了点击", Toast.LENGTH_SHORT).show();
                Snackbar.make(rootLayout, "I am Snackbar!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .show();
            }
        });

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 根据id加载不同的做菜细节
                Intent intent = new Intent(CodeLabActivity.this,
                        SearchMenuStepActivty.class);
                Bundle bundle = new Bundle();
                // 传一个对象过去？
                bundle.putSerializable("MD", mdg.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        checkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 先把上次的ListView数据清空 不然查询到的数据会累加到列表
                titleList = new ArrayList<String>();
                tagList = new ArrayList<String>();
                imgList = new ArrayList<String>();
                SoftInputManager.closeSoftManager(CodeLabActivity.this);
                startCheckMenu();
            }
        });

        //侧边栏  实现相应的事件
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.navItem1:

                        break;
                    case R.id.navItem2:
                        break;
                    case R.id.navItem3:
                        break;
                    case R.id.navItem4:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        //菜谱列表
        menuResultRecView.setHasFixedSize(true);
        llm=new LinearLayoutManager(CodeLabActivity.this);
        menuResultRecView.setLayoutManager(llm);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_code_lab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 设置ListView
     */
    public void setMenuListView() {
        mra = new MenuResultAdapter(CodeLabActivity.this, initData(),
                R.layout.activity_menu_search_list_item, new String[] {
                "title", "tag", "img" }, new int[] {
                R.id.menu_finished_title, R.id.menu_finished_tags,
                R.id.menu_finished_img });
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
            Log.e("Main", titleList.get(i));
            map.put("tag", tagList.get(i));
            Log.e("Main", tagList.get(i));
            map.put("img", imgList.get(i));
            Log.e("Main", imgList.get(i));

            list.add(map);
        }

        // 绑定数据
        return list;
    }




    /**
     * 开启一个线程开始查询菜
     */
    public void startCheckMenu() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String menu_name = menuInput.getText().toString().trim();
                Log.e(tag, menu_name);
                if (!TextUtils.isEmpty(MenuTool.getRequest1(menu_name))) {
                    // 解析json数据
                    mr = pjb.parseMenuResult2(MenuTool.getRequest1(menu_name));
                    // get search result  空指针异常
                    if (mr.getResultcode()!=null && mr.getResultcode().equals("200")) {
                        md = mr.getResult();
                        if (!TextUtils.isEmpty(md.getPn())
                                && !TextUtils.isEmpty(md.getRn())
                                && !TextUtils.isEmpty(md.getTotalNum())
                                && md.getData() != null) {

                            Log.e("MainActivity", md.getPn());
                            Log.e("MainActivity", md.getRn());
                            Log.e("MainActivity", md.getTotalNum());
                            Log.e("MainActivity", md.getData().toString());
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

                                    Log.e("MainActivity", i.getTitle());
                                    Log.e("MainActivity", i.getBurden());
                                    Log.e("MainActivity", i.getId());
                                    Log.e("MainActivity", i.getImtro());
                                    Log.e("MainActivity", i.getIngredients());
                                    Log.e("MainActivity", i.getTags());
                                    Log.e("MainActivity", i.getAlbums()
                                            .toString());
                                    Log.e("MainActivity", i.getSteps()
                                            .toString());
                                    // 获取标题
                                    titleList.add(i.getTitle());
                                    // 获取tag
                                    tagList.add(i.getTags());
                                    // 获取图片
                                    imgList.add(i.getAlbums().get(0));

                                    // 做菜的详细步骤
                                    ms = i.getSteps();
                                    for (MStep step : ms) {
                                        Log.d("MainActivity", step.getImg());
                                        Log.d("MainActivity", step.getStep());
                                    }

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
        }).start();

    }


}
