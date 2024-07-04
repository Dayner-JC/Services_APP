package dev.godjango.apk;

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
    private static final int DEFAULT_DIALOG_WIDTH_DP = 300;
    private int position;
    private OnDeleteWorkListener deleteWorkListener;

    public static PopUpDeleteWork newInstance(int position, OnDeleteWorkListener listener) {
        PopUpDeleteWork fragment = new PopUpDeleteWork();
        fragment.position = position;
        fragment.deleteWorkListener = listener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.popup_delete_work, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = UnitConverter.dpToPx(this.requireContext(),DEFAULT_DIALOG_WIDTH_DP);
            int height = UnitConverter.dpToPx(this.requireContext(),260);
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
            dialog.getWindow().setGravity(17);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        Button btnCancel = requireView().findViewById(R.id.Button_Cancel_Delete);
        Button btnAccept = requireView().findViewById(R.id.Button_Accept_Delete);
        btnCancel.setOnClickListener(this::onCancelClicked);
        btnAccept.setOnClickListener(this::onAcceptClicked);
    }

    private void onCancelClicked(View v) {
        dismiss();
    }

    private void onAcceptClicked(View v) {
        if (deleteWorkListener != null) {
            deleteWorkListener.onDeleteWork(position);
        }
        dismiss();
    }

    public interface OnDeleteWorkListener {
        void onDeleteWork(int position);
    }
}
