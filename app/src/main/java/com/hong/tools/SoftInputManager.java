package com.hong.tools;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * 打开或者关闭软键盘
 * Created by susan on 16-4-21.
 */
public  class SoftInputManager {

    public static void openSoftManager(Context context){
            InputMethodManager imm= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0,InputMethodManager.RESULT_SHOWN);
    }

    public static void closeSoftManager(Context  context){
            InputMethodManager imm=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
