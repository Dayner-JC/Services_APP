package dev.godjango.apk.validation.resetPassword;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import dev.godjango.apk.R;
import dev.godjango.apk.ui.activities.LoginActivity;

public class ConfirmPasswordReset {
    private final AppCompatActivity activity;
    private final EditText newPasswordEditText;
    private final TextView passwordError;
    private final Intent intent;

    public ConfirmPasswordReset(AppCompatActivity activity, EditText newPasswordEditText, TextView passwordError, Intent intent){
        this.activity = activity;
        this.newPasswordEditText = newPasswordEditText;
        this.passwordError = passwordError;
        this.intent = intent;
    }

    @SuppressLint("SetTextI18n")
    public void confirmPasswordReset() {

        Uri data = intent.getData();
        String token;
        if (data != null) {
            token = data.getQueryParameter("token");
        }else{
            Toast.makeText(activity, "Token not found", Toast.LENGTH_SHORT).show();
            return;
        }

        String newPassword = newPasswordEditText.getText().toString();
        if (newPassword.isEmpty()) {
            passwordError.setText("Enter a password");
            passwordError.setVisibility(View.VISIBLE);
            newPasswordEditText.setBackgroundResource(R.drawable.error_bg);
            return;
        }
        if (newPassword.length() < 8 || newPassword.length() >= 16) {
            passwordError.setText("Enter a valid password");
            passwordError.setVisibility(View.VISIBLE);
            newPasswordEditText.setBackgroundResource(R.drawable.error_bg);
            return;
        }
        newPasswordEditText.setBackgroundResource(R.drawable.edit_text_background);
        passwordError.setVisibility(View.GONE);

        String url = "http://192.168.43.9:3000/auth/users/reset_password_confirm/";
        JSONObject resetData = new JSONObject();
        try {
            resetData.put("token", token);
            resetData.put("new_password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, resetData,
                response -> {
                    Toast.makeText(activity, "Password update", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(loginIntent);
                    activity.finish();
                },
                error -> {
                    Toast.makeText(activity, "An error has occurred", Toast.LENGTH_SHORT).show();
                }
        );
        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(jsonObjectRequest);
    }

}
