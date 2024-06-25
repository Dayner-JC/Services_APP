package com.example.prueba;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;

public class WorkDetail extends AppCompatActivity {
    private boolean isExpanded = false;
    private View scrollView;
    private TextView textDescriptionContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_detail);
        setSystemBarsColor(R.color.status_bar);
        if (Build.VERSION.SDK_INT >= 0) {
            setTransparentNavigationBarWithDarkIcons();
        }
        ImageButton button_back = findViewById(R.id.Work_button_back);
        final TextView text_read_more = findViewById(R.id.text_read_more);
        this.textDescriptionContent = findViewById(R.id.text_description_content);
        this.scrollView = findViewById(R.id.ScrollView);
        ImageButton button_delete = findViewById(R.id.Work_button_delete);
        button_delete.setOnClickListener(WorkDetail.this::DeleteService);
        text_read_more.setOnClickListener(view -> WorkDetail.this.ReadMore(text_read_more));
        button_back.setOnClickListener(WorkDetail.this::Back);
    }

    public void DeleteService(View v) {
        showPopUp();
    }

    @SuppressLint("SetTextI18n")
    public void ReadMore(TextView text_read_more) {
        this.isExpanded = !this.isExpanded;
        if (this.isExpanded) {
            this.textDescriptionContent.getLayoutParams().height = -2;
            this.scrollView.getLayoutParams().height = -2;
            text_read_more.setText("Read less...");
            this.scrollView.setVerticalScrollBarEnabled(true);
        } else {
            this.textDescriptionContent.getLayoutParams().height = dpToPx();
            this.scrollView.getLayoutParams().height = -2;
            text_read_more.setText("Read more...");
            this.scrollView.setVerticalScrollBarEnabled(false);
            this.scrollView.scrollTo(0, 0);
        }
        this.textDescriptionContent.requestLayout();
    }

    public void Back(View view) {
        finish();
    }

    private void showPopUp() {
        PopUpDeleteWork popUp = new PopUpDeleteWork();
        popUp.setCancelable(false);
        popUp.show(getSupportFragmentManager(), "popup_window");
    }

    private int dpToPx() {
        float scale = getResources().getDisplayMetrics().density;
        return (int) ((74 * scale) + 0.5f);
    }

    private void setSystemBarsColor(int colorResId) {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
        window.setNavigationBarColor(0);
    }

    private void setTransparentNavigationBarWithDarkIcons() {
        Window window = getWindow();
        window.setNavigationBarColor(0);
        View decorView = window.getDecorView();
        int flags = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(flags | 16);
    }
}