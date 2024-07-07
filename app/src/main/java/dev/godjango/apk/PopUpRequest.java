package dev.godjango.apk;

import static android.app.Activity.RESULT_OK;

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

public class PopUpRequest extends DialogFragment {
    private Dialog firstDialog;
    private Bundle cardData;

    public static PopUpRequest newInstance(String name, String lastName, String email, String countryNumber, String phoneNumberText, String country, double pay, double total_to_pay) {
        PopUpRequest fragment = new PopUpRequest();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("lastName", lastName);
        args.putString("email", email);
        args.putString("country", country);
        args.putString("countryNumber", countryNumber);
        args.putString("phoneNumber", phoneNumberText);
        args.putDouble("total_to_pay", total_to_pay);
        args.putDouble("pay", pay);

        fragment.setArguments(args);
        return fragment;
    }

    public void setCardData(Bundle cardData) {
        this.cardData = cardData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.popup_request, container, false);
    }

    public void setFirstDialog(Dialog firstDialog) {
        this.firstDialog = firstDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = UnitConverter.dpToPx(this.requireContext(), 370);
            int height = UnitConverter.dpToPx(this.requireContext(),493);

            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
            dialog.getWindow().setGravity(17);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        if (dialog == null) {
            throw new AssertionError();
        }

        TextView field_name = dialog.findViewById(R.id.Campo_Name);
        TextView text_payment_info = dialog.findViewById(R.id.Text_Payment_Info);
        TextView text_total_date = dialog.findViewById(R.id.Text_Total_Date);

        if (getArguments() != null) {
            String name = getArguments().getString("name");
            String lastName = getArguments().getString("lastName");
            String email = getArguments().getString("email");
            String countryNumber = getArguments().getString("countryNumber");
            String country = getArguments().getString("country");
            String phoneNumber = getArguments().getString("phoneNumber");
            double pay = getArguments().getDouble("pay");
            double total_to_pay = getArguments().getDouble("total_to_pay");
            double PaymentDue = total_to_pay - pay;
            String Pay = "PayPal";
            String pay_text = pay +"0 $";
            String total_pay_text = total_to_pay + "0 $";
            String PaymentDue_text = PaymentDue + "0 $";

            String allInfo = String.format("%s\n%s\n%s\n%s\n%s %s\n%s", name, lastName, email, Pay, countryNumber, phoneNumber, country);
            String PagoInfo = String.format("%s\n%s", pay_text, total_pay_text);
            String PaymentDue_string = String.format("%s", PaymentDue_text);

            field_name.setText(allInfo);
            text_payment_info.setText(PagoInfo);
            text_total_date.setText(PaymentDue_string);
        }

        Button button_accept = dialog.findViewById(R.id.Button_Accept_Request);
        button_accept.setOnClickListener(this::onViewCreated);
    }

    public void onViewCreated(View v) {
        dismiss();
        if (this.firstDialog != null) {
            this.firstDialog.dismiss();
        }
        if (cardData != null) {
            Intent data = new Intent();
            data.putExtra("newCardData", cardData);
            requireActivity().setResult(RESULT_OK, data);
            requireActivity().finish();
        }
    }
}
