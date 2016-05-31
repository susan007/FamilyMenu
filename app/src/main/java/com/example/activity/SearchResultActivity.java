package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.okhttpandroid.R;
import com.gree.adapter.MenuResultAdapter;
import com.gree.bean.menu_search_byname.MData;
import com.gree.bean.menu_search_byname.MStep;
import com.gree.bean.menu_search_byname.MenuDigital;
import com.gree.bean.menu_search_byname.MenuResult;
import com.gree.tools.MenuTool;
import com.gree.tools.ParseJsonobject;

public class SearchResultActivity extends Activity {

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
				Toast.makeText(SearchResultActivity.this, "查询错误，未找到该菜",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		initView();
		initListener();
	}

	public void initView() {
		menuInput = (EditText) findViewById(R.id.menu_check);
		checkBtn = (Button) findViewById(R.id.check_btn);
		menuListView = (ListView) findViewById(R.id.menu_list);
		titleList = new ArrayList<String>();
		tagList = new ArrayList<String>();
		imgList = new ArrayList<String>();

		pjb = new ParseJsonobject();
		mr = new MenuResult();
	}

	public void initListener() {
		checkBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 先把上次的ListView数据清空 不然查询到的数据会累加到列表
				titleList = new ArrayList<String>();
				tagList = new ArrayList<String>();
				imgList = new ArrayList<String>();

				startCheckMenu();
			}
		});

		menuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 根据id加载不同的做菜细节
				Intent intent = new Intent(SearchResultActivity.this,
						SearchMenuStepActivty.class);
				Bundle bundle = new Bundle();
				// 传一个对象过去？
				bundle.putSerializable("MD", mdg.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
			}

		});
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
					// get search result
					if (mr.getResultcode().equals("200")) {
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

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// String menu_name=menuInput.getText().toString().trim();
		//
		// Log.e(tag, menu_name);
		// if (!TextUtils.isEmpty(MenuTool.getRequest2())) {
		// Message messageOK=new Message();
		// messageOK.what=1;
		// messageOK.obj=MenuTool.getRequest2();
		// handler.sendMessage(messageOK);
		// }else{
		// Message messageEr=new Message();
		// messageEr.what=0;
		// handler.sendMessage(messageEr);
		// }
		//
		// }
		// }).start();

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// String menu_name=menuInput.getText().toString().trim();
		//
		// Log.e(tag, menu_name);
		// if (!TextUtils.isEmpty(MenuTool.getRequest3("1"))) {
		// Message messageOK=new Message();
		// messageOK.what=1;
		// messageOK.obj=MenuTool.getRequest3("1");
		// handler.sendMessage(messageOK);
		// }else{
		// Message messageEr=new Message();
		// messageEr.what=0;
		// handler.sendMessage(messageEr);
		// }
		//
		// }
		// }).start();
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
	 * 设置ListView
	 */
	public void setMenuListView() {
		mra = new MenuResultAdapter(SearchResultActivity.this, initData(),
				R.layout.activity_menu_search_list_item, new String[] {
						"title", "tag", "img" }, new int[] {
						R.id.menu_finished_title, R.id.menu_finished_tags,
						R.id.menu_finished_img });
		menuListView.setAdapter(mra);
	}

}
