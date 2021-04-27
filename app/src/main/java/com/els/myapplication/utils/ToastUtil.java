package com.els.myapplication.utils;

import android.widget.Toast;

import com.els.myapplication.App;

public class ToastUtil {
    private static Toast mShortToast;
    private static Toast mLongToast;

    public static void showToast( String message) {
        if (mShortToast == null) {
            mShortToast = Toast.makeText(App.context, message, Toast.LENGTH_SHORT);
        }
        mShortToast.setText(message);
        mShortToast.show();

    }
}
