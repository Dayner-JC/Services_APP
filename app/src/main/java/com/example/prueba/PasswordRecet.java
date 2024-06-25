/*package com.example.prueba;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PasswordRecet extends AppCompatActivity {
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        email = findViewById(R.id.email);
        Button resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String email_text = email.getText().toString();

        if (email_text.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://127.0.0.1/auth/users/reset_password/";

        JSONObject resetData = new JSONObject();
        try {
            resetData.put("email", email_text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, resetData, response -> {
                    // Manejar la respuesta exitosa
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    finish();
                }, error -> {
                    // Manejar el error
                    Toast.makeText(this, "Failed to send reset email. Please try again.", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
*/
