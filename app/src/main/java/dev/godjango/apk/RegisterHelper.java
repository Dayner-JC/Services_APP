package dev.godjango.apk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.view.Gravity;

/** @noinspection all*/
public class RegisterHelper {

    private static final CountryData countryData = new CountryData();
    private static final Map<String, List<String>> countryToPhoneCodesMap = countryData.getCountryToPhoneCodesMap();
    private static final List<String> countryNames = new ArrayList<>();
    private static AppCompatActivity activity;
    private static Dialog dialog;
    private static EditText password;
    private static EditText name;
    private static EditText lastName;
    private static EditText email;
    private static EditText amount;
    private static EditText phoneNumber;
    private static EditText country_number;
    private static Spinner country;
    private static TextView countryError;
    private static PasswordVisibility passwordVisibility;
    private static CountryAdapter countryAdapter;
    private static boolean ignoreTextWatcher = false;
    private static boolean manualInput = false;
    private static double amountEntered;
    private static String price;
    private static double priceDouble;
    private static Bundle cardData;

    public RegisterHelper(Bundle cardData, AppCompatActivity activity, String price) {
        RegisterHelper.activity = activity;
        RegisterHelper.cardData = cardData;
        RegisterHelper.price = price.replace(" ", "");
        priceDouble = Double.parseDouble(RegisterHelper.price.substring(1));
        configureCountryData();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public static void showRegister() {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_register);
        dialog.setCancelable(true);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, UnitConverter.dpToPx(activity, 650));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        ImageButton passwordVisible = dialog.findViewById(R.id.Button_Password);
        password = dialog.findViewById(R.id.Password);
        name = dialog.findViewById(R.id.Name);
        lastName = dialog.findViewById(R.id.Last_Name);
        email = dialog.findViewById(R.id.Email);
        amount = dialog.findViewById(R.id.Amount);
        countryError = dialog.findViewById(R.id.PhoneError);
        TextView nameError = dialog.findViewById(R.id.name_error_text);
        TextView lastNameError = dialog.findViewById(R.id.last_name_error_text);
        TextView emailError = dialog.findViewById(R.id.email_error_text);
        TextView passwordError = dialog.findViewById(R.id.password_error_text);
        TextView amountError = dialog.findViewById(R.id.Amount_Error);
        TextView phoneError = dialog.findViewById(R.id.PhoneNumberError);
        TextView priceService = dialog.findViewById(R.id.Price);
        TextView timeForPay = dialog.findViewById(R.id.Time);
        phoneNumber = dialog.findViewById(R.id.Phone_Number);
        country = dialog.findViewById(R.id.Country);
        country_number = dialog.findViewById(R.id.Country_Number);
        Button request_service = dialog.findViewById(R.id.button_request_2);

        String priceString = String.valueOf(price);
        String priceWithOutDollar = priceString.replace("$", "");
        double halfOriginalPrice = Double.parseDouble(priceWithOutDollar) / 2;
        String finalPrice = String.format("$%.2f", halfOriginalPrice).replace(",", ".");
        amount.setText(finalPrice);
        amountEntered = halfOriginalPrice;

        priceService.setText(price.replace("$", "$ "));
        timeForPay.setText(cardData.getString("Time"));

        nameError.setVisibility(View.GONE);
        lastNameError.setVisibility(View.GONE);
        emailError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);
        amountError.setVisibility(View.GONE);
        phoneError.setVisibility(View.GONE);
        phoneNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        country_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        country_number.setSelection(country_number.getText().length());
        countryError.setVisibility(View.GONE);
        amount.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        configureSpinner();

        country_number.addTextChangedListener(new TextWatcher() {
            private int cursorPosition = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cursorPosition = country_number.getSelectionStart();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ignoreTextWatcher) {
                    ignoreTextWatcher = false;
                    return;
                }

                country_number.removeTextChangedListener(this);

                String countryCode = s.toString();
                if (countryCode.isEmpty()) {
                    country_number.setSelection(country_number.getText().length());
                } else if (!countryCode.startsWith("+")) {
                    countryCode = "+" + countryCode;
                    country_number.setText(countryCode);
                    country_number.setSelection(country_number.getText().length());
                }

                String countryName = findCountryNameByCode(countryCode);

                if (countryName != null) {
                    int spinnerPosition = countryAdapter.getPosition(countryName);
                    if (country.getSelectedItemPosition() != spinnerPosition) {
                        country.setSelection(spinnerPosition);
                    }
                    country_number.addTextChangedListener(this);
                    countryError.setVisibility(View.GONE);
                    country_number.setBackgroundResource(R.drawable.edit_text_background);
                } else {
                    int notFoundPosition = countryAdapter.getPosition("Country not found");
                    country.setSelection(notFoundPosition);
                    country_number.addTextChangedListener(this);
                    countryError.setVisibility(View.VISIBLE);
                    country_number.setBackgroundResource(R.drawable.error_bg);
                    countryError.setText("Enter a valid country number");
                }

                if (country_number.getText().toString().isEmpty()) {
                    country_number.setBackgroundResource(R.drawable.error_bg);
                    countryError.setText("Enter a country number");
                    countryError.setVisibility(View.VISIBLE);
                }

                manualInput = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                country_number.removeTextChangedListener(this);

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
                        cursorPosition = country_number.getSelectionStart();
                    } else {
                        cursorPosition += addedChars;
                    }
                }

                country_number.setText(modifiedCountryCode.toString());
                country_number.setSelection(Math.max(0, Math.min(cursorPosition, country_number.getText().length())));
                country_number.addTextChangedListener(this);
            }

            private String findCountryNameByCode(String countryCode) {
                for (Map.Entry<String, List<String>> entry : countryToPhoneCodesMap.entrySet()) {
                    if (entry.getValue().contains(countryCode)) {
                        return entry.getKey();
                    }
                }
                return null;
            }
        });

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean initialSelection = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initialSelection) {
                    initialSelection = false;
                    return;
                }String selectedCountry = parent.getItemAtPosition(position).toString();
                if (!selectedCountry.equals("Country not found")) {List<String> possibleCodes = countryToPhoneCodesMap.get(selectedCountry);
                    if (possibleCodes != null && possibleCodes.size() > 1 && !manualInput) {
                        showCountryCodeDialog(possibleCodes, selectedCountry);
                    } else if (possibleCodes != null&& possibleCodes.size() == 1) {
                        int cursorPosition = country_number.getSelectionStart();
                        ignoreTextWatcher = true;
                        country_number.setText(possibleCodes.get(0));
                        country_number.setSelection(Math.min(cursorPosition, country_number.getText().length()));
                    }
                }

                manualInput = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            private boolean isErrorDisplayed= false;

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
                double mitadPrecioOriginal = Double.parseDouble(priceWithOutDollar) / 2;

                boolean isValid = true;
                String errorMessage = "";

                if (amountText.isEmpty()) {
                    isValid = false;
                    errorMessage = "Amount cannot be empty";
                } else {
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
                        if (amountEntered < mitadPrecioOriginal || amountEntered > Double.parseDouble(priceString.substring(1))) {
                            isValid = false;
                            errorMessage = "Enter a valid amount";
                        }
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
        });

        passwordVisibility = new PasswordVisibility();
        password.setTransformationMethod(passwordVisibility);
        passwordVisible.setOnClickListener(view -> PasswordVisibility.Visibility(passwordVisibility, passwordVisible, password));

        request_service.setOnClickListener(view -> {
            if (country_number.getText().toString().isEmpty()) {
                countryError.setText("Enter a country number");
                country_number.setBackgroundResource(R.drawable.error_bg);
                countryError.setVisibility(View.VISIBLE);
            }

            boolean isValid = ValidateRegister.validateNameAndLastName(name, lastName, nameError, lastNameError);
            isValid &= ValidateRegister.validateEmail(email, emailError);
            isValid &= ValidateRegister.validatePassword(password, passwordError);
            isValid &= ValidateRegister.validateAmount(amountError);
            isValid &= ValidateRegister.validateCountryNumber(countryError, country_number);
            isValid &= ValidateRegister.validatePhoneNumber(phoneNumber, phoneError);

            if(isValid) {
               showPopUp(activity);
            }

        });
    }

    private static void configureCountryData() {
        List<String> countryList = countryData.getCountryNames();
        countryNames.clear();
        countryNames.addAll(countryList);
        Collections.sort(countryNames);

        if (!countryNames.contains("Country not found")) {
            countryNames.add("Country not found");
        }
    }

    private static void configureSpinner() {
        countryAdapter = new CountryAdapter(activity, countryNames);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryAdapter);

        int position = countryAdapter.getPosition("United States");
        if (position >= 0) {
            country.setSelection(position);
        }
    }

    private static void showCountryCodeDialog(List<String> possibleCodes, String countryName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select a country code for " + countryName);

        String[] codesArray = possibleCodes.toArray(new String[0]);
        builder.setItems(codesArray, (dialog, which) -> {
            String selectedCode = codesArray[which];
            country_number.setText(selectedCode);
        });

        builder.show();
    }

   private static void showPopUp(AppCompatActivity activity) {
        String nameText = name.getText().toString();
        String lastNameText = lastName.getText().toString();
        String emailText = email.getText().toString();
        String countryNumberText = country_number.getText().toString();
        String countryText = country.getSelectedItem().toString();
        String phoneNumberText = phoneNumber.getText().toString();

        PopUpRequest popUpRequest = PopUpRequest.newInstance(nameText, lastNameText, emailText, countryNumberText, phoneNumberText, countryText, amountEntered, priceDouble);
        popUpRequest.setCancelable(false);
        popUpRequest.setFirstDialog(dialog);
        popUpRequest.show(activity.getSupportFragmentManager(), "PopUpRequest");

        Bundle userData = new Bundle();
        userData.putString("Name", nameText);
        userData.putString("Last Name", lastNameText);
        userData.putString("Email", emailText);
        userData.putString("Country Number", countryNumberText);
        userData.putString("Phone Number", phoneNumberText);
        userData.putString("Country", countryText);
        userData.putString("Amount Paid", String.valueOf(amountEntered));
        userData.putString("Price Service", String.valueOf(priceDouble));

       String currentDate = DateUtil.getCurrentDateFormatted();
       userData.putString("Request Date", currentDate);

       Bundle mergedData = new Bundle();
       if (cardData != null) {
           mergedData.putAll(cardData);
       }
       mergedData.putAll(userData);

       popUpRequest.setCardData(mergedData);

    }
}
