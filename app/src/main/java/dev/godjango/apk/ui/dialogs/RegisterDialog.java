package dev.godjango.apk.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Objects;

import dev.godjango.apk.R;
import dev.godjango.apk.utils.PasswordVisibility;

public class RegisterDialog extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button_accept_request = view.findViewById(R.id.Button_Accept_Request);
        final ImageButton password_visible = view.findViewById(R.id.Button_Password);
        final EditText password_edit_text = view.findViewById(R.id.Password);
        final PasswordVisibility transformationMethod = new PasswordVisibility();
        password_edit_text.setTransformationMethod(transformationMethod);
        PasswordVisibility PasswordVisibility = new PasswordVisibility();
        password_visible.setOnClickListener(view2 -> RegisterDialog.onViewCreated(PasswordVisibility, password_visible, password_edit_text));
        button_accept_request.setOnClickListener(RegisterDialog.this::AcceptRequest);
    }

    public static void onViewCreated(PasswordVisibility transformationMethod, ImageButton password_visible, EditText password_edit_text) {
        transformationMethod.togglePasswordVisibility();
        if (transformationMethod.isPasswordVisible) {
            password_visible.setImageResource(R.drawable.ic_hide);
        } else {
            password_visible.setImageResource(R.drawable.ic_view);
        }
        password_edit_text.setText(password_edit_text.getText());
        password_edit_text.setSelection(Objects.requireNonNull(password_edit_text.getText()).length());
    }

    public void AcceptRequest(View v) {
        dismiss();
    }
}
