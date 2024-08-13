package dev.godjango.apk.utils;

import android.view.View;
import android.view.Window;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;

public class SystemBarsUtil {

    public static void styleSystemBars(AppCompatActivity activity, @ColorRes int statusBarColor) {
        disableBackButton(activity);
        setSystemBarsColor(activity, statusBarColor);
        setTransparentNavigationBarWithDarkIcons(activity);
    }

    public static void disableBackButton(AppCompatActivity activity) {
        activity.getOnBackPressedDispatcher().addCallback(activity, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });
    }

    public static void setSystemBarsColor(AppCompatActivity activity, @ColorRes int colorResId) {
        Window window = activity.getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        window.setStatusBarColor(ContextCompat.getColor(activity, colorResId));
        window.setNavigationBarColor(0);
    }

    public static void setTransparentNavigationBarWithDarkIcons(AppCompatActivity activity) {
        Window window = activity.getWindow();
        window.setNavigationBarColor(0);
        View decorView = window.getDecorView();
        int flags = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(flags | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }
}
