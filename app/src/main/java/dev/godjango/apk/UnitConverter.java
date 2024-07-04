package dev.godjango.apk;

import android.content.Context;

public class UnitConverter {

    private UnitConverter() {
    }

    public static int dpToPx(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
