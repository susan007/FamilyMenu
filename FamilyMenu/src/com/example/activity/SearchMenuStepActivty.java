package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.okhttpandroid.R;
import com.gree.adapter.MenuStepAdapter;
import com.gree.bean.menu_search_byname.MStep;
import com.gree.bean.menu_search_byname.MenuDigital;

public class SearchMenuStepActivty extends Activity{
	
	private Intent intent;
	private String position;
	
	private MenuDigital md;
	
	//完成图
	private ImageView menuFinishedImg;
	//标题
	private TextView menuTitle;
	//描述
	private TextView menuDescribe;
	//食材清单列表
	private TextView menuMaterialList;
	//步骤
	private ListView stepListView;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_digital);
		
		initView();
		initListener();
	}
	
	public void initView(){
		intent=SearchMenuStepActivty.this.getIntent();
		md=(MenuDigital) intent.getSerializableExtra("MD");  
		
		
		menuFinishedImg=(ImageView) findViewById(R.id.menu_finished_img);
		menuTitle=(TextView) findViewById(R.id.menu_title);
		menuDescribe=(TextView) findViewById(R.id.menu_describe);
		menuMaterialList=(TextView) findViewById(R.id.menu_material_list);
		stepListView=(ListView) findViewById(R.id.menu_step_listView);
		
		
	}

	public void initListener(){
		/*
		 * 缺省的占位图片，一般可以设置成一个加载中的进度GIF图，placeholder显示加载时的图片,crossDade(时间)代表淡入淡出
		 */
		Glide.with(SearchMenuStepActivty.this).load(md.getAlbums().get(0).toString())  
				.centerCrop().placeholder(R.drawable.loading).crossFade()
				.into(menuFinishedImg);
		
		menuTitle.setText(md.getTitle());
		menuDescribe.setText(md.getImtro());
		menuMaterialList.setText(md.getIngredients()+";"+md.getBurden());
		
		setStepListView();
	}
	/**
	 * 设置步骤列表
	 */
	private void setStepListView() {
		MenuStepAdapter adapter=new MenuStepAdapter(SearchMenuStepActivty.this, 
				getListViewData(), 
				R.layout.activity_menu_digital_item, 
				new String[]{"stepImg","stepTxt"}, 
				new int[]{R.id.menu_finished_step_img,R.id.menu_finished_step_txt});
		
		stepListView.setAdapter(adapter);
		
	}
	
	private List<Map<String, String>> getListViewData(){
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		List<MStep> stepList=md.getSteps();
		for (int i = 0; i < stepList.size(); i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("stepImg", stepList.get(i).getImg());
			Log.e("MenuStepActivity", stepList.get(i).getImg());
			map.put("stepTxt", stepList.get(i).getStep());
			Log.e("MenuStepActivity", stepList.get(i).getStep());
			list.add(map);
		}
		
		return list;
	}
	
	
}
