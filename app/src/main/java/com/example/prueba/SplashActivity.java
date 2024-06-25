package com.example.prueba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(4866);
        setContentView(R.layout.activity_splash);
        setSystemBarsColor(R.color.status_bar);
        new Handler().postDelayed(SplashActivity.this::m121lambda$onCreate$0$comexamplegodjangoserviceSplashActivity, 1500);
    }

    public void m121lambda$onCreate$0$comexamplegodjangoserviceSplashActivity() {
        Intent mainIntent = new Intent(this, LoginActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void setSystemBarsColor(int colorResId) {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
    }
}