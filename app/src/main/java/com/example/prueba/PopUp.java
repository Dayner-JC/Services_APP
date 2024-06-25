package com.example.prueba;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import java.util.Objects;

public class PopUp extends DialogFragment {
    static final boolean $assertionsDisabled = false;
    private Dialog primerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.popup_request, container, false);
    }

    public void setPrimerDialog(Dialog primerDialog) {
        this.primerDialog = primerDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = dpToPx(350);
            int height = dpToPx(450);
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
            dialog.getWindow().setGravity(17);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        if (dialog == null) {
            throw new AssertionError();
        }
        Button button_accept = dialog.findViewById(R.id.Button_Accept_Request);
        button_accept.setOnClickListener(PopUp.this::m118lambda$onStart$0$comexamplegodjangoservicePopUp);
    }

    public void m118lambda$onStart$0$comexamplegodjangoservicePopUp(View v) {
        dismiss();
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        startActivity(intent);
        if (this.primerDialog != null) {
            this.primerDialog.dismiss();
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
}
