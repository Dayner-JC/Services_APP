package dev.godjango.apk.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dev.godjango.apk.R;
import dev.godjango.apk.utils.SystemBarsUtil;
import dev.godjango.apk.validation.resetPassword.ConfirmPasswordReset;

public class PasswordResetConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset_confirm);
        SystemBarsUtil.styleSystemBars(this, R.color.white);

        EditText newPasswordEditText = findViewById(R.id.password);
        Button confirmButton = findViewById(R.id.resetButton);
        TextView passwordError = findViewById(R.id.password_error);
        passwordError.setVisibility(View.GONE);

        Intent intent = getIntent();

        ConfirmPasswordReset confirmPasswordReset = new ConfirmPasswordReset(this, newPasswordEditText, passwordError,intent);

        confirmButton.setOnClickListener(v -> confirmPasswordReset.confirmPasswordReset());
    }

}
