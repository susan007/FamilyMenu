package com.hong.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hong.adapter.CardAdapter;
import com.inthecheesefactory.lab.designlibrary.R;

/**
 * Created by susan on 16-12-20.
 */

public class SplashFragment extends Fragment {

    private View view;
    private CardView cardview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.splash_cardview,container,false);

        initView();
        initListener();

        return view;
    }

    public void initView(){
        cardview=(CardView)view.findViewById(R.id.cardview);

    }

    public void initListener(){
        cardview.setMaxCardElevation(cardview.getCardElevation()* CardAdapter.MAX_ELEVATION_FACTOR);
    }

    public CardView getCardview(){
        return cardview;
    }
}
