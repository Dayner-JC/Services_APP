package com.example.prueba;

import android.graphics.Rect;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;

public class PasswordToggleTransformationMethod implements TransformationMethod {
    boolean isPasswordVisible;

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return this.isPasswordVisible ? source : new PasswordTransformationMethod().getTransformation(source, view);
    }

    @Override
    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {
    }

    public void togglePasswordVisibility() {
        this.isPasswordVisible = !this.isPasswordVisible;
    }
}
