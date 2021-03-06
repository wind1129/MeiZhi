package com.example.wind.meizhi.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.wind.meizhi.MeiZhiApp;

/**
 * Util to show hint such as snackBar or dialog.
 */
public class UI {

    private static Context context = MeiZhiApp.context;

    public static void showSnack(View rootView, int textId) {
        if (null != rootView) {
            Snackbar.make(rootView, textId, Snackbar.LENGTH_SHORT).show();
        }
    }

    public static void showSnackLong(View rootView, int textId) {
        if (null != rootView) {
            Snackbar.make(rootView, textId, Snackbar.LENGTH_LONG).show();
        }
    }
}
