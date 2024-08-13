package dev.godjango.apk.utils;

import android.graphics.Rect;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import dev.godjango.apk.R;

public class PasswordVisibility implements TransformationMethod {
    public boolean isPasswordVisible;

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

    public static void Visibility(PasswordVisibility passwordVisivility,
                                  ImageView passwordVisibilityButton,
                                  EditText passwordEditText) {
        passwordVisivility.togglePasswordVisibility();
        if (passwordVisivility.isPasswordVisible) {
            passwordVisibilityButton.setImageResource(R.drawable.ic_hide);
        } else {
            passwordVisibilityButton.setImageResource(R.drawable.ic_view);
        }
        passwordEditText.setText(passwordEditText.getText());
        passwordEditText.setSelection(passwordEditText.getText().length());
    }
}
