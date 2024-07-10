package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class PasswordResetConfirm extends AppCompatActivity {
    private EditText newPasswordEditText;
    private TextView passwordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset_confirm);
        newPasswordEditText = findViewById(R.id.password);
        Button confirmButton = findViewById(R.id.resetButton);
        passwordError = findViewById(R.id.password_error);
        passwordError.setVisibility(View.GONE);
        confirmButton.setOnClickListener(v -> confirmPasswordReset());
    }

    @SuppressLint("SetTextI18n")
    private void confirmPasswordReset() {

        Intent intent = getIntent();
        Uri data = intent.getData();
        String token;
        if (data != null) {
            token = data.getQueryParameter("token");
        }else{
            Toast.makeText(this, "Token not found", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(this, "Password update", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(this, Login.class);
                    startActivity(loginIntent);
                    finish();
                },
                error -> {
                    Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show();
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
