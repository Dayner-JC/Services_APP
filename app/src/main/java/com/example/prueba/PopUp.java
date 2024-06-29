package com.example.prueba;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import java.util.Objects;

public class PopUp extends DialogFragment {
    private Dialog primerDialog;

    public static PopUp newInstance(String name, String lastName, String email, String countryNumber, String phoneNumberText, String country, double pagado,double total_pagar) {
        PopUp fragment = new PopUp();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("lastName", lastName);
        args.putString("email", email);
        args.putString("country", country);
        args.putString("countryNumber", countryNumber);
        args.putString("phoneNumber", phoneNumberText);
        args.putDouble("pagado", pagado);
        args.putDouble("total_pagar", total_pagar);
        fragment.setArguments(args);
        return fragment;
    }

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
            int width = dpToPx(370);
            int height = dpToPx(493);
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
            dialog.getWindow().setGravity(17);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        if (dialog == null) {
            throw new AssertionError();
        }

        TextView campo_name = dialog.findViewById(R.id.Campo_Name);
        TextView text_payment_info = dialog.findViewById(R.id.Text_Payment_Info);
        TextView text_total_date = dialog.findViewById(R.id.Text_Total_Date);

        if (getArguments() != null) {
            String name = getArguments().getString("name");
            String lastName = getArguments().getString("lastName");
            String email = getArguments().getString("email");
            String countryNumber = getArguments().getString("countryNumber");
            String country = getArguments().getString("country");
            String phoneNumber = getArguments().getString("phoneNumber");
            double pagado = getArguments().getDouble("pagado");
            double total_pagar = getArguments().getDouble("total_pagar");
            double falta_pagar = total_pagar - pagado;
            String pay = "PayPal";
            String pagado_text = pagado +"0 $";
            String total_pagar_text = total_pagar + "0 $";
            String falta_pagar_text = falta_pagar + "0 $";

            String allInfo = String.format("%s\n%s\n%s\n%s\n%s %s\n%s", name, lastName, email,pay, countryNumber, phoneNumber, country);
            String PagoInfo = String.format("%s\n%s", pagado_text, total_pagar_text);
            String falta_paga = String.format("%s", falta_pagar_text);

            campo_name.setText(allInfo);
            text_payment_info.setText(PagoInfo);
            text_total_date.setText(falta_paga);
        }

        Button button_accept = dialog.findViewById(R.id.Button_Accept_Request);
        button_accept.setOnClickListener(PopUp.this::onViewCreated);
    }

    public void onViewCreated(View v) {
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
