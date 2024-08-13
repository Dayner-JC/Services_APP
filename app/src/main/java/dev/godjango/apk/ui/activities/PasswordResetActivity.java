package dev.godjango.apk.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dev.godjango.apk.R;
import dev.godjango.apk.utils.SystemBarsUtil;
import dev.godjango.apk.validation.resetPassword.RequestPasswordReset;

public class PasswordResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);
        SystemBarsUtil.styleSystemBars(this, R.color.white);

        EditText email = findViewById(R.id.email);
        Button resetButton = findViewById(R.id.resetButton);
        ImageButton backButton = findViewById(R.id.Button_back);
        TextView errorText = findViewById(R.id.email_error_text);
        RequestPasswordReset requestPasswordReset = new RequestPasswordReset(this, email, errorText);

        errorText.setVisibility(View.GONE);
        resetButton.setOnClickListener(v -> requestPasswordReset.resetPassword());
        backButton.setOnClickListener(v -> finish());
    }

}


