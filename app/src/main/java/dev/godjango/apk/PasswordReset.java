package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class PasswordReset extends AppCompatActivity {
    private EditText email;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);

        email = findViewById(R.id.email);
        Button resetButton = findViewById(R.id.resetButton);
        errorText = findViewById(R.id.email_error_text);

        errorText.setVisibility(View.GONE);
        resetButton.setOnClickListener(v -> resetPassword());
    }

    @SuppressLint("SetTextI18n")
    private void resetPassword() {
        String email_text = email.getText().toString();

        if (email_text.isEmpty()) {
            errorText.setText("Enter a email");
            errorText.setVisibility(View.VISIBLE);
            email.setBackgroundResource(R.drawable.error_bg);
            return;
        }
        if(!email_text.endsWith("@gmail.com")){
            errorText.setText("Enter a valid email");
            errorText.setVisibility(View.VISIBLE);
            email.setBackgroundResource(R.drawable.error_bg);
            return;
        }

        email.setBackgroundResource(R.drawable.edit_text_background);
        errorText.setVisibility(View.GONE);

        String url = "http://192.168.43.9:3000/auth/users/reset_password/";

        JSONObject resetData = new JSONObject();
        try {
            resetData.put("email", email_text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, resetData, response -> {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    finish();
                }, error -> {
                    Toast.makeText(this, "Failed to send reset email. Please try again.", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}


