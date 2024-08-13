package dev.godjango.apk.validation.resetPassword;

import android.annotation.SuppressLint;
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

public class RequestPasswordReset {
    private final EditText email;
    private final TextView errorText;
    private final AppCompatActivity activity;

    public RequestPasswordReset(AppCompatActivity activity, EditText email, TextView errorText){
        this.activity = activity;
        this.email = email;
        this.errorText = errorText;
    }

    @SuppressLint("SetTextI18n")
    public void resetPassword() {
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
                    Toast.makeText(activity, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }, error -> Toast.makeText(activity, "Failed to send reset email. Please try again.", Toast.LENGTH_SHORT).show());

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(jsonObjectRequest);
    }

}
