package dev.godjango.apk.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dev.godjango.apk.R;
import dev.godjango.apk.utils.PasswordVisibility;
import dev.godjango.apk.utils.SystemBarsUtil;
import dev.godjango.apk.validation.login.LoginManager;
import dev.godjango.apk.validation.login.ValidateLogin;
import dev.godjango.apk.validation.login.ValidateLoginWithGoogle;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private TextView emailErrorTextView;
    private EditText passwordEditText;
    private TextView passwordErrorTextView;
    private ImageButton passwordVisibilityButton;
    private PasswordVisibility passwordVisibility;
    private ValidateLogin loginValidator;
    private ValidateLoginWithGoogle googleLoginHandler;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SystemBarsUtil.styleSystemBars(this, R.color.status_bar);
        SystemBarsUtil.disableBackButton(this);

        loginValidator = new ValidateLogin();
        googleLoginHandler = new ValidateLoginWithGoogle(this);
        loginManager = new LoginManager(this);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        emailErrorTextView = findViewById(R.id.email_error_text);
        passwordErrorTextView = findViewById(R.id.password_error_text);
        passwordVisibilityButton = findViewById(R.id.togglePasswordVisibility);

        passwordVisibility = new PasswordVisibility();
        passwordEditText.setTransformationMethod(passwordVisibility);

        emailErrorTextView.setVisibility(View.GONE);
        passwordErrorTextView.setVisibility(View.GONE);
    }

    private void setupListeners() {
        passwordVisibilityButton.setOnClickListener(view ->
                PasswordVisibility.Visibility(passwordVisibility, passwordVisibilityButton, passwordEditText));

        Button loginButton = findViewById(R.id.loginButton);
        Button googleButton = findViewById(R.id.google);

        loginButton.setOnClickListener(this::onLoginButtonClicked);
        googleButton.setOnClickListener(this::onGoogleButtonClicked);

        TextView forgetTextView = findViewById(R.id.text_forget);
        forgetTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
            startActivity(intent);
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailErrorTextView.setVisibility(View.GONE);
                emailEditText.setBackgroundResource(R.drawable.edit_text_background);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordErrorTextView.setVisibility(View.GONE);
                passwordEditText.setBackgroundResource(R.drawable.edit_text_background);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void onLoginButtonClicked(View v) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        int validationResult = loginValidator.validateLogin(email, password);
        loginValidator.handleValidationResult(validationResult, email, password,
                emailEditText, emailErrorTextView,
                passwordEditText, passwordErrorTextView,
                loginManager);
    }

    private void onGoogleButtonClicked(View v) {
        googleLoginHandler.onGoogleButtonClicked();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleLoginHandler.handleActivityResult(requestCode, data);
    }

    public void onGuestButtonClicked(View view) {
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
        finish();
    }
}
