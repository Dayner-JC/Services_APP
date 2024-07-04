package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

/** */
@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(4866);
        setContentView(R.layout.splash_screen);
        SystemBarsUtil.setSystemBarsColor(this, R.color.status_bar);
        SystemBarsUtil.disableBackButton(this);
        new Handler().postDelayed(SplashScreen.this::Next, 1500);
    }

    public void Next() {
        Intent mainIntent = new Intent(this, Login.class);
        startActivity(mainIntent);
        finish();
    }

}