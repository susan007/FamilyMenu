package com.inthecheesefactory.lab.designlibrary;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
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

import com.fragment.FragmentMenuLabel;
import com.fragment.FragmentMenuName;
import com.fragment.FragmentMenuSort;
import com.fragment.FragmentSetting;
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
    private String path = "http://www.cnblogs.com/hongdiandian/";
    //给toolBar设置监听
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_settings:
                    //进入我们的官网
                    Intent intent = new Intent();
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
//    private FloatingActionButton fabBtn;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    private String tag = "hong";


    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_lab);
        initToolbar();
        initInstances();
        initListener();
        if (savedInstanceState == null) showMenuName();
    }

    /**
     * 设置默认fragment页面
     */
    private void showMenuName() {
        selectDrawerItem(navigation.getMenu().getItem(0));
        //设置侧滑栏打开
//        drawerLayout.openDrawer(GravityCompat.START);
    }

    /**
     * 设置toolBar
     */
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

    /**
     * 初始化数据
     */
    private void initInstances() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(CodeLabActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        fabBtn = (FloatingActionButton) findViewById(R.id.fabBtn);
        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);
        ctl = (CollapsingToolbarLayout) findViewById(R.id.title);
        ctl.setTitle("佳肴菜名");//佳肴菜名 佳肴分类  佳肴标签
        navigation = (NavigationView) findViewById(R.id.navigation);
        menuResultRecView = (RecyclerView) findViewById(R.id.menu_result_recyclerview);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        fragmentManager = getSupportFragmentManager();
    }

    public void initListener() {
        //侧边栏  实现相应的事件
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    /**
     * 侧滑栏
     * @param item
     */
    public void selectDrawerItem(MenuItem item) {
        int itemId = item.getItemId();
        Class fragmentClass;
        switch (itemId) {
            case R.id.menu_name:
                Toast.makeText(this,"正在加载，请耐心等待",Toast.LENGTH_SHORT).show();
                fragmentClass = FragmentMenuName.class;
                ctl.setTitle("佳肴菜名");//佳肴菜名 佳肴分类  佳肴标签
                break;
            case R.id.menu_label:
                Toast.makeText(this,"正在加载，请耐心等待",Toast.LENGTH_SHORT).show();
                fragmentClass = FragmentMenuLabel.class;
                ctl.setTitle("佳肴标签");//佳肴菜名 佳肴分类  佳肴标签
                break;
            case R.id.menu_setting:
                fragmentClass = FragmentSetting.class;
                ctl.setTitle("佳肴设置");//佳肴菜名 佳肴分类  佳肴标签
                break;
            default:
                fragmentClass = FragmentMenuName.class;
                ctl.setTitle("佳肴菜名");//佳肴菜名 佳肴分类  佳肴标签
                break;
        }
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
//                setToolbarElevation(specialToolbarBehaviour);
        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();//关闭侧边栏
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

}
