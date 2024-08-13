package dev.godjango.apk.listeners;

import static dev.godjango.apk.validation.request.RequestManager.showCountryCodeDialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import java.util.List;
import java.util.Map;

public class CountryItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private final EditText countryNumber;
    private final Map<String, List<String>> countryToPhoneCodesMap;
    private boolean initialSelection = true;
    private boolean manualInput;

    public CountryItemSelectedListener(EditText countryNumber, Map<String, List<String>> countryToPhoneCodesMap, boolean manualInput) {
        this.countryNumber = countryNumber;
        this.countryToPhoneCodesMap = countryToPhoneCodesMap;
        this.manualInput = manualInput;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (initialSelection) {
            initialSelection = false;
            return;
        }
        String selectedCountry = parent.getItemAtPosition(position).toString();
        if (!selectedCountry.equals("Country not found")) {
            List<String> possibleCodes = countryToPhoneCodesMap.get(selectedCountry);
            if (possibleCodes != null && possibleCodes.size() > 1 && !manualInput) {
                showCountryCodeDialog(possibleCodes, selectedCountry);
            } else if (possibleCodes != null && possibleCodes.size() == 1) {
                int cursorPosition = countryNumber.getSelectionStart();
                boolean ignoreTextWatcher = true;
                countryNumber.setText(possibleCodes.get(0));
                countryNumber.setSelection(Math.min(cursorPosition, countryNumber.getText().length()));
            }
        }

        manualInput = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


}

