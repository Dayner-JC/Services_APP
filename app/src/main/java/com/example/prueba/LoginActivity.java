package com.example.prueba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private TextView emailError;
    private EditText password;
    private TextView passwordError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setSystemBarsColor(R.color.status_bar);
        if (Build.VERSION.SDK_INT >= 0) {
            setTransparentNavigationBarWithDarkIcons();
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        emailError = findViewById(R.id.email_error_text);
        passwordError = findViewById(R.id.password_error_text);
        TextView forget = findViewById(R.id.text_forget);
        final ImageButton togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);
        Button login = findViewById(R.id.loginButton);
        final PasswordToggleTransformationMethod transformationMethod = new PasswordToggleTransformationMethod();
        password.setTransformationMethod(transformationMethod);

        togglePasswordVisibility.setOnClickListener(view -> LoginActivity.this.passwordToggle(transformationMethod, togglePasswordVisibility));
        emailError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);
        login.setOnClickListener(LoginActivity.this::Verificacion);

        /*forget.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
            startActivity(intent);
        });*/
    }

    public void passwordToggle(PasswordToggleTransformationMethod transformationMethod, ImageButton togglePasswordVisibility) {
        transformationMethod.togglePasswordVisibility();
        if (transformationMethod.isPasswordVisible) {
            togglePasswordVisibility.setImageResource(R.drawable.ic_hide);
        } else {
            togglePasswordVisibility.setImageResource(R.drawable.ic_view);
        }
        this.password.setText(this.password.getText());
        this.password.setSelection(Objects.requireNonNull(this.password.getText()).length());
    }

    @SuppressLint("SetTextI18n")
    public void Verificacion(View v) {
        String email_text = this.email.getText().toString();
        String password_text = this.password.getText().toString();
        int resultado = verifiedLogin(email_text, password_text);

        switch (resultado) {
            case 1:
                this.email.setBackgroundResource(R.drawable.error_bg);
                this.emailError.setVisibility(View.VISIBLE);
                this.emailError.setText("Enter a email");
                this.password.setBackgroundResource(R.drawable.edit_text_background);
                this.passwordError.setVisibility(View.GONE);
                return;
            case 2:
                this.email.setBackgroundResource(R.drawable.error_bg);
                this.emailError.setVisibility(View.VISIBLE);
                this.emailError.setText("Enter a valid email");
                this.password.setBackgroundResource(R.drawable.edit_text_background);
                this.passwordError.setVisibility(View.GONE);
                return;
            case 3:
                this.email.setBackgroundResource(R.drawable.edit_text_background);
                this.emailError.setVisibility(View.GONE);
                this.password.setBackgroundResource(R.drawable.error_bg);
                this.passwordError.setVisibility(View.VISIBLE);
                this.passwordError.setText("Enter a password");
                return;
            case 4:
                this.email.setBackgroundResource(R.drawable.edit_text_background);
                this.emailError.setVisibility(View.GONE);
                this.password.setBackgroundResource(R.drawable.error_bg);
                this.passwordError.setVisibility(View.VISIBLE);
                this.passwordError.setText("Enter a valid password");
                return;
            case 5:
                this.email.setBackgroundResource(R.drawable.error_bg);
                this.emailError.setVisibility(View.VISIBLE);
                this.emailError.setText("Enter a email");
                this.password.setBackgroundResource(R.drawable.error_bg);
                this.passwordError.setVisibility(View.VISIBLE);
                this.passwordError.setText("Enter a password");
                return;
            case 6:
                this.email.setBackgroundResource(R.drawable.error_bg);
                this.emailError.setVisibility(View.VISIBLE);
                this.emailError.setText("Enter a valid email");
                this.password.setBackgroundResource(R.drawable.error_bg);
                this.passwordError.setVisibility(View.VISIBLE);
                this.passwordError.setText("Enter a valid password");
                return;
            case 7:
                this.email.setBackgroundResource(R.drawable.error_bg);
                this.emailError.setVisibility(View.VISIBLE);
                this.emailError.setText("Enter a valid email");
                this.password.setBackgroundResource(R.drawable.error_bg);
                this.passwordError.setVisibility(View.VISIBLE);
                this.passwordError.setText("Enter a password");
                return;
            case 8:
                this.email.setBackgroundResource(R.drawable.error_bg);
                this.emailError.setVisibility(View.VISIBLE);
                this.emailError.setText("Enter a email");
                this.password.setBackgroundResource(R.drawable.error_bg);
                this.passwordError.setVisibility(View.VISIBLE);
                this.passwordError.setText("Enter a valid password");
                return;
            default:
                this.email.setBackgroundResource(R.drawable.edit_text_background);
                this.password.setBackgroundResource(R.drawable.edit_text_background);
                loginUser(email_text, password_text);
        }
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, loginData, response -> {
                    try {
                        String token = response.getString("token");
                        // Guardar el token y proceder a la siguiente actividad
                        Intent intent = new Intent(this, NavigationActivity.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // Manejar el error
                    Toast.makeText(this, "Login failed. Please check your credentials and try again.", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public int verifiedLogin(String email, String password) {
        if (email.isEmpty() && password.isEmpty()) {
            return 5;
        }
        if (email.isEmpty()) {
            return password.length() < 8 ? 8 : 1;
        }
        if (password.isEmpty()) {
            if (!email.endsWith("@gmail.com")) {
                return 7;
            }
            return 3;
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

    public void Guest(View view) {
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
        finish();
    }
}

