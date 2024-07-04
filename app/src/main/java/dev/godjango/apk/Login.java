package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

/** */
public class Login extends AppCompatActivity {

    private EditText emailEditText;
    private TextView emailErrorTextView;
    private EditText passwordEditText;
    private TextView passwordErrorTextView;
    private ImageButton passwordVisibilityButton;
    private PasswordVisibility passwordVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SystemBarsUtil.styleSystemBars(this, R.color.status_bar);

        SystemBarsUtil.disableBackButton(this);

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
        passwordVisibilityButton.setOnClickListener(view -> PasswordVisibility.Visibility(passwordVisibility, passwordVisibilityButton, passwordEditText));

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this::onLoginButtonClicked);

        /*TextView forgetTextView = findViewById(R.id.text_forget);
        forgetTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, PasswordResetActivity.class);
            startActivity(intent);
        });*/

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailErrorTextView.setVisibility(View.GONE);
                emailEditText.setBackgroundResource(R.drawable.edit_text_background);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordErrorTextView.setVisibility(View.GONE);
                passwordEditText.setBackgroundResource(R.drawable.edit_text_background);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void onLoginButtonClicked(View v) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        int validationResult = validateLogin(email, password);
        LoginValidation(validationResult, email, password);
    }

    private int validateLogin(String email, String password) {
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
    private void LoginValidation(int validationResult, String email, String password) {
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
                loginUser(email, password);
        }
    }

    private void showError(EditText editText, TextView errorTextView, String errorMessage) {
        editText.setBackgroundResource(R.drawable.error_bg);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(errorMessage);
    }

    private void loginUser(String email, String password) {
        String url = "http://127.0.0.1/auth/jwt/create/";

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                loginData,
                response -> {
                    try {
                        String token = response.getString("token");
                        startNavigationActivity(token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showLoginErrorToast();
                    }
                },
                error -> showLoginErrorToast());

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void startNavigationActivity(String token) {
        Intent intent = new Intent(this, Navigation.class);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }

    private void showLoginErrorToast() {
        Toast.makeText(this, "Login failed. Please check your credentials and try again.", Toast.LENGTH_SHORT).show();
    }

    public void onGuestButtonClicked(View view) {
        Intent intent = new Intent(this, Navigation.class);
        startActivity(intent);
        finish();
    }

}
