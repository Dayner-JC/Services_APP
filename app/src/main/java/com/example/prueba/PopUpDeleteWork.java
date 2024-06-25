package com.example.prueba;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import java.util.Objects;

public class PopUpDeleteWork extends DialogFragment {
    private static final int DEFAULT_DIALOG_WIDTH_DP = 300; // Define a default width in dp if needed

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.popup_delete_work, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = dpToPx(DEFAULT_DIALOG_WIDTH_DP);
            int height = dpToPx(260);
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
            dialog.getWindow().setGravity(17);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        Button btnCancel = requireView().findViewById(R.id.Button_Cancel_Delete);
        btnCancel.setOnClickListener(this::onCancelClicked);
    }

    private void onCancelClicked(View v) {
        dismiss();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
}
