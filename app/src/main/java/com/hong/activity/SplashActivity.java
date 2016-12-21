package com.hong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hong.adapter.CardFragmentPagerAdapter;
import com.hong.adapter.CardPagerAdapter;
import com.hong.bean.CardItem;
import com.hong.tools.ShadowTransformer;
import com.inthecheesefactory.lab.designlibrary.R;

/**
 * Created by susan on 16-12-21.
 */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private Button startBtn;

    private Boolean mShowingFragments = false;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        initView();
        initListener();
    }

    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        startBtn = (Button) findViewById(R.id.start_btn);
//        startBtn.setVisibility(View.INVISIBLE);

        mCardAdapter = new CardPagerAdapter();
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));
        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(viewPager, mFragmentCardAdapter);
    }

    public void initListener() {
        startBtn.setOnClickListener(this);

        mCardAdapter.addCardItem(new CardItem(R.string.tea, R.string.tea_detail,R.drawable.splash_tea));
        mCardAdapter.addCardItem(new CardItem(R.string.meat, R.string.meat_detail,R.drawable.splash_drag));
        mCardAdapter.addCardItem(new CardItem(R.string.fruit, R.string.fruit_detail,R.drawable.splash_fruit));
        mCardAdapter.addCardItem(new CardItem(R.string.tiaoliao, R.string.tiaoliao_detail,R.drawable.splash_tiaoliao));

        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);

        mCardShadowTransformer.enableScaling(true);
        mFragmentCardShadowTransformer.enableScaling(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                Intent intent = new Intent(SplashActivity.this, MenuSearchActivity.class);
                startActivity(intent);
                finish();
                break;
            default:

                break;
        }
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
