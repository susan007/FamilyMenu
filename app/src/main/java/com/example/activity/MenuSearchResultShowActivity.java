package com.example.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.okhttpandroid.R;

public class MenuSearchResultShowActivity extends Activity{
	
	private ListView searchResultListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_search_list);
		
		
	}
	
	public void initView(){
		searchResultListView=(ListView) findViewById(R.id.menu_search_result_list);
	}
	
	public void initListener(){
		
		
	}

}
