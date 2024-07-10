package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import org.jetbrains.annotations.Nullable;
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
    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SystemBarsUtil.styleSystemBars(this, R.color.status_bar);
        SystemBarsUtil.disableBackButton(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

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
        Button google = findViewById(R.id.google);

        loginButton.setOnClickListener(this::onLoginButtonClicked);
        google.setOnClickListener(this::onGoogleButtonClicked);

        TextView forgetTextView = findViewById(R.id.text_forget);
        forgetTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, PasswordReset.class);
            startActivity(intent);
        });

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

    private void onGoogleButtonClicked(View v) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Unknown error authenticating with Firebase", Toast.LENGTH_SHORT).show();
            }
        }
    }

   private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        handleGoogleSignInSuccess();
                    } else {
                        Toast.makeText(this, "Unknown error authenticating with Firebase", Toast.LENGTH_SHORT).show();                    }
                });
    }

    private void handleGoogleSignInSuccess() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", true);
        editor.apply();

        Intent intent = new Intent(this, Navigation.class);
        startActivity(intent);
        finish();
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
                showError(emailEditText, emailErrorTextView, "Enter a email");
                return;
            case 2:
                showError(emailEditText, emailErrorTextView, "Enter a valid email");
                return;
            case 3:
                showError(passwordEditText, passwordErrorTextView, "Enter a password");
                return;
            case 4:
                showError(passwordEditText, passwordErrorTextView, "Enter a valid password");
                return;
            case 5:
                showError(emailEditText, emailErrorTextView, "Enter a email");
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
                showError(emailEditText, emailErrorTextView, "Enter a email");
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
        String url = "http://192.168.43.9:3000/auth/jwt/create/";

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("password", password);
        } catch (JSONException e) {
            showLoginErrorToast();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                loginData,
                response -> {
                    try {
                        String token = response.getString("token");

                        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("is_logged_in", true);
                        editor.apply();

                        startNavigationActivity(token);
                    } catch (JSONException e) {
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
