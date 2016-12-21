package com.hong.bean;


public class CardItem {

    private int mTextResource;
    private int mTitleResource;
    private int menuImg;

    public CardItem(int title, int text,int img) {
        this.mTitleResource = title;
        this.mTextResource = text;
        this.menuImg=img;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }

    public int getMenuImg(){
        return menuImg;
    }
}
