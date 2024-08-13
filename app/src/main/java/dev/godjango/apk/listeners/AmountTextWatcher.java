package dev.godjango.apk.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dev.godjango.apk.R;

public class AmountTextWatcher implements TextWatcher {

    private final EditText amount;
    private final TextView amountError;
    private boolean isErrorDisplayed = false;
    private final String price;

    public AmountTextWatcher(String price,EditText amount, TextView amountError) {
        this.amount = amount;
        this.amountError = amountError;
        this.price = price;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String amountText = amount.getText().toString();
        String priceString = String.valueOf(price);
        String priceWithOutDollar = priceString.replace("$", "");
        double halfOriginalPrice = Double.parseDouble(priceWithOutDollar) / 2;

        boolean isValid = true;
        String errorMessage = "";

        if (amountText.isEmpty()) {
            isValid = false;
            errorMessage = "Amount cannot be empty";
        } else {
            double amountEntered;
            if(!amountText.equals("To Quote")) {
                if (!amountText.startsWith("$")) {
                    amountText = "$" + amountText;
                    amount.setText(amountText);
                    amount.setSelection(amountText.length());
                }

                if (!amountText.endsWith(".00")) {
                    isValid = false;
                    errorMessage = "Amount must end with '.00'";
                } else {

                    String amountWithoutDollar = amountText.substring(1);
                    amountEntered = Double.parseDouble(amountWithoutDollar);
                    if (amountEntered < halfOriginalPrice || amountEntered > Double.parseDouble(priceString.substring(1))) {
                        isValid = false;
                        errorMessage = "Enter a valid amount";
                    }

                }
            }else{
                return;
            }
        }

        if (isValid) {
            if (isErrorDisplayed) {
                amount.setBackgroundResource(R.drawable.edit_text_background);
                amountError.setVisibility(View.GONE);
                isErrorDisplayed = false;
            }
        } else {
            if (!isErrorDisplayed) {
                amount.setBackgroundResource(R.drawable.error_bg);
                amountError.setText(errorMessage);
                amountError.setVisibility(View.VISIBLE);
                isErrorDisplayed = true;
            }
        }
    }

}


