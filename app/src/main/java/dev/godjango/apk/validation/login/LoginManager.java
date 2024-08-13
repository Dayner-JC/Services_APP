package dev.godjango.apk.validation.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import dev.godjango.apk.ui.activities.LoginActivity;
import dev.godjango.apk.ui.activities.NavigationActivity;

public class LoginManager {

    private final LoginActivity loginActivity;

        public LoginManager(LoginActivity loginActivity) {
            this.loginActivity = loginActivity;
        }

        public void loginUser(String email, String password) {
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
                            saveLoginStateAndStartNavigation(token);
                        } catch (JSONException e) {
                            showLoginErrorToast();
                        }
                    },
                    error -> showLoginErrorToast());

            RequestQueue queue = Volley.newRequestQueue(loginActivity);
            queue.add(jsonObjectRequest);
        }

        private void saveLoginStateAndStartNavigation(String token) {
            SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged_in", true);
            editor.putString("token", token);
            editor.apply();

            startNavigationActivity();
        }

        private void startNavigationActivity() {
            Intent intent = new Intent(loginActivity, NavigationActivity.class);
            loginActivity.startActivity(intent);
            loginActivity.finish();
        }

        private void showLoginErrorToast() {
            Toast.makeText(loginActivity, "Login failed. Please check your credentials and try again.", Toast.LENGTH_SHORT).show();
        }

}
