package com.hong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.inthecheesefactory.lab.designlibrary.R;

/**
 * Created by susan on 16-6-2.
 */
public class SearchByLabelResultActivity extends Activity{

    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu_name);
        initView();
        initListener();

    }

    public void initView(){
        ll=(LinearLayout)findViewById(R.id.check_ll);

    }

    public void initListener(){
        ll.setVisibility(View.GONE);
    }
}
