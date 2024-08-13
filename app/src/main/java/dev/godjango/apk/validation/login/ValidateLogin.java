package dev.godjango.apk.validation.login;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dev.godjango.apk.R;

public class ValidateLogin {

    public int validateLogin(String email, String password) {
        if (email.isEmpty() && password.isEmpty()) {
            return 5;
        }
        if (email.isEmpty()) {
            return password.length() < 8 ? 8 : 1;
        }
        if (password.isEmpty()) {
            return !email.endsWith("@gmail.com") ? 7 : 3;
        }
        if (!email.endsWith("@gmail.com") && password.length() < 8) {
            return 6;
        }
        if (!email.endsWith("@gmail.com")) {
            return 2;
        }
        if (password.length() < 8) {
            return 4;
        }
        return 0;
    }

    @SuppressLint("SetTextI18n")
    public void handleValidationResult(int validationResult, String email, String password,
                                       EditText emailEditText, TextView emailErrorTextView,
                                       EditText passwordEditText, TextView passwordErrorTextView,
                                       LoginManager loginManager) {
        switch (validationResult) {
            case 1:
                showError(emailEditText, emailErrorTextView, "Enter an email");
                return;
            case 2:
                showError(emailEditText, emailErrorTextView, "Enter a valid email");
                return;
            case 3:
                showError(passwordEditText, passwordErrorTextView, "Enter a password");
                return;
            case 4:
                showError(passwordEditText, passwordErrorTextView, "Enter a valid password");
                return;case 5:
                showError(emailEditText, emailErrorTextView, "Enter an email");
                showError(passwordEditText, passwordErrorTextView, "Enter a password");
                return;
            case 6:
                showError(emailEditText, emailErrorTextView, "Enter a valid email");
                showError(passwordEditText, passwordErrorTextView, "Enter a valid password");
                return;
            case 7:
                showError(emailEditText, emailErrorTextView, "Enter a valid email");
                showError(passwordEditText, passwordErrorTextView, "Enter a password");
                return;
            case 8:
                showError(emailEditText, emailErrorTextView, "Enter an email");
                showError(passwordEditText, passwordErrorTextView, "Enter a valid password");
                return;
            default:
                loginManager.loginUser(email, password);
        }
    }

    private void showError(EditText editText, TextView errorTextView, String errorMessage) {
        editText.setBackgroundResource(R.drawable.error_bg);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(errorMessage);
    }
}
