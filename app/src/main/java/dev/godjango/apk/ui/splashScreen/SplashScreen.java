package dev.godjango.apk.ui.splashScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import dev.godjango.apk.ui.activities.LoginActivity;
import dev.godjango.apk.ui.activities.NavigationActivity;
import dev.godjango.apk.R;
import dev.godjango.apk.utils.SystemBarsUtil;

/** */
@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private boolean isLoggedIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(4866);
        setContentView(R.layout.splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        SystemBarsUtil.setSystemBarsColor(this, R.color.status_bar);
        SystemBarsUtil.disableBackButton(this);
        new Handler().postDelayed(SplashScreen.this::Next, 1500);
    }

    public void Next() {
        Intent mainIntent;
       //if (isLoggedIn) {
            mainIntent = new Intent(this, NavigationActivity.class);
        //}else {
            mainIntent = new Intent(this, LoginActivity.class);
        //}
        startActivity(mainIntent);
        finish();

    }

}