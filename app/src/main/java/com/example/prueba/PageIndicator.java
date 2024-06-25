package com.example.prueba;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PageIndicator extends RelativeLayout {
    private int count;
    private int selectedPosition;

    public PageIndicator(Context context) {
        super(context);
        init();
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    public void setCount(int count) {
        this.count = count;
        createIndicators();
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position % getChildCount();
        updateIndicators();
    }

    private void createIndicators() {
        removeAllViews();
        for (int i = 0; i < this.count; i++) {
            ImageView indicator = new ImageView(getContext());
            indicator.setId(View.generateViewId());
            indicator.setImageResource(R.drawable.custom_indicator_drawable_unselected);
            int sizePx = (int) (6 * getResources().getDisplayMetrics().density);
            int marginPx = (int) (3 * getResources().getDisplayMetrics().density);
            LayoutParams params = new LayoutParams(sizePx, sizePx);
            params.setMargins(marginPx, 0, marginPx, 0);
            if (i == 0) {
                params.addRule(20);
            } else {
                params.addRule(1, getChildAt(i - 1).getId());
            }
            addView(indicator, params);
        }
    }

    private void updateIndicators() {
        for (int i = 0; i < getChildCount(); i++) {
            ImageView indicator = (ImageView) getChildAt(i);
            if (i == this.selectedPosition) {
                indicator.setImageResource(R.drawable.custom_indicator_drawable);
            } else {
                indicator.setImageResource(R.drawable.custom_indicator_drawable_unselected);
            }
        }
    }
}
