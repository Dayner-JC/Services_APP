package dev.godjango.apk.listeners;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import dev.godjango.apk.R;
import dev.godjango.apk.adapters.CountryAdapter;

public class CountryTextWatcher implements TextWatcher {

    private final EditText countryNumber;
    private final Spinner country;
    private final TextView countryError;
    private final CountryAdapter countryAdapter;
    private final Map<String, List<String>> countryToPhoneCodesMap;
    private boolean ignoreTextWatcher;
    private boolean manualInput;
    private int cursorPosition = 0;

    public CountryTextWatcher(EditText countryNumber, Spinner country, TextView countryError, CountryAdapter countryAdapter, Map<String, List<String>> countryToPhoneCodesMap, boolean ignoreTextWatcher, boolean manualInput) {
        this.countryNumber = countryNumber;
        this.country = country;
        this.countryError = countryError;
        this.countryAdapter = countryAdapter;
        this.countryToPhoneCodesMap = countryToPhoneCodesMap;
        this.ignoreTextWatcher = ignoreTextWatcher;
        this.manualInput = manualInput;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        cursorPosition = countryNumber.getSelectionStart();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (ignoreTextWatcher) {
            ignoreTextWatcher = false;
            return;
        }

        countryNumber.removeTextChangedListener(this);

        String countryCode = s.toString();
        if (countryCode.isEmpty()) {
            countryNumber.setSelection(countryNumber.getText().length());
        } else if (!countryCode.startsWith("+")) {
            countryCode = "+" + countryCode;
            countryNumber.setText(countryCode);
            countryNumber.setSelection(countryNumber.getText().length());
        }

        String countryName = findCountryNameByCode(countryCode);

        if (countryName != null) {
            int spinnerPosition = countryAdapter.getPosition(countryName);
            if (country.getSelectedItemPosition() != spinnerPosition) {
                country.setSelection(spinnerPosition);
            }
            countryNumber.addTextChangedListener(this);
            countryError.setVisibility(View.GONE);
            countryNumber.setBackgroundResource(R.drawable.edit_text_background);
        } else {
            int notFoundPosition = countryAdapter.getPosition("Country not found");
            country.setSelection(notFoundPosition);
            countryNumber.addTextChangedListener(this);
            countryError.setVisibility(View.VISIBLE);
            countryNumber.setBackgroundResource(R.drawable.error_bg);
            countryError.setText("Enter a valid country number");
        }

        if (countryNumber.getText().toString().isEmpty()) {
            countryNumber.setBackgroundResource(R.drawable.error_bg);
            countryError.setText("Enter a country number");
            countryError.setVisibility(View.VISIBLE);
        }

        manualInput = true;
    }

    @Override
    public void afterTextChanged(Editable s) {
        countryNumber.removeTextChangedListener(this);

        String countryCode = s.toString();
        StringBuilder modifiedCountryCode = new StringBuilder();
        int addedChars = 0;

        if (!countryCode.isEmpty()) {
            for (int i = 0; i < countryCode.length(); i++) {
                char c = countryCode.charAt(i);
                if (i == 0 && c != '+') {
                    modifiedCountryCode.append('+');
                    addedChars++;
                    if (countryCode.length() == 1) {
                        cursorPosition = 1;
                    }
                }
                modifiedCountryCode.append(c);
                if (c == '-') {
                    addedChars++;
                }
            }
            if (addedChars == 0) {
                cursorPosition = countryNumber.getSelectionStart();
            } else {
                cursorPosition += addedChars;
            }
        }

        countryNumber.setText(modifiedCountryCode.toString());
        countryNumber.setSelection(Math.max(0, Math.min(cursorPosition, countryNumber.getText().length())));
        countryNumber.addTextChangedListener(this);
    }

    private String findCountryNameByCode(String countryCode) {
        for (Map.Entry<String, List<String>> entry : countryToPhoneCodesMap.entrySet()) {
            if (entry.getValue().contains(countryCode)) {
                return entry.getKey();
            }
        }
        return null;
    }
}

