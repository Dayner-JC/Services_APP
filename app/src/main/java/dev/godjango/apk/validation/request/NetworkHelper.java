package dev.godjango.apk.validation.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkHelper {

    public static void registerUser(AppCompatActivity activity, String name, String lastName, String email, String password,
                                    String countryNumber, String phoneNumber, String country) {
        String url = "http://192.168.43.9:3000/auth/users/";

        JSONObject userData = new JSONObject();
        try {
            userData.put("name", name);
            userData.put("lastName", lastName);
            userData.put("email", email);
            userData.put("password", password);
            userData.put("countryNumber", countryNumber);
            userData.put("phoneNumber", phoneNumber);
            userData.put("country", country);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Error preparing the data", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userData,
                response -> {

                    Toast.makeText(activity, "Request successful", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = activity.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("is_logged_in", true);
                    editor.apply();

                }, error -> Toast.makeText(activity, "Error requesting the service", Toast.LENGTH_SHORT).show());

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(jsonObjectRequest);
    }
}

