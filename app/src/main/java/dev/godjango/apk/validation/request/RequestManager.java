package dev.godjango.apk.validation.request;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import dev.godjango.apk.R;
import dev.godjango.apk.adapters.CountryAdapter;
import dev.godjango.apk.listeners.AmountTextWatcher;
import dev.godjango.apk.listeners.CountryItemSelectedListener;
import dev.godjango.apk.listeners.CountryTextWatcher;
import dev.godjango.apk.models.CountryData;
import dev.godjango.apk.ui.dialogs.PopUpRequestDialog;
import dev.godjango.apk.utils.DateUtil;
import dev.godjango.apk.utils.PasswordVisibility;
import dev.godjango.apk.utils.UnitConverter;

/** @noinspection all*/
public class RequestManager {

    private static final CountryData countryData = new CountryData();
    private static final Map<String, List<String>> countryToPhoneCodesMap = countryData.getCountryToPhoneCodesMap();
    private static final List<String> countryNames = new ArrayList<>();
    static String title;
    static String category;
    static AppCompatActivity activity;
    private static Dialog dialog;
    private static EditText password;
    private static EditText name;
    private static EditText lastName;
    private static EditText email;
    private static EditText amount;
    private static EditText phoneNumber;
    static EditText country_number;
    static Spinner country;
    private static TextView countryError,textAlert;
    private static PasswordVisibility passwordVisibility;
    static CountryAdapter countryAdapter;
    static boolean ignoreTextWatcher = false;
    static boolean manualInput = false;
    private static double amountEntered;
    static String price;
    private static double priceDouble;
    static Bundle cardData;
    private static PopUpRequestDialog popUpRequestDialog;

    public RequestManager(Bundle cardData, AppCompatActivity activity, String price, String title, String category) {
        RequestManager.activity = activity;
        RequestManager.cardData = cardData;
        RequestManager.title = title;
        RequestManager.category = category;
        if(!price.equals("To Quote")) {
            RequestManager.price = price.replace(" ", "");
            priceDouble = Double.parseDouble(RequestManager.price.substring(1));
        }
        else{
            RequestManager.price = price;
        }
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

        textAlert = dialog.findViewById(R.id.Text_Alert);
        ImageButton passwordVisible = dialog.findViewById(R.id.Button_Password);
        password = dialog.findViewById(R.id.Password);
        name = dialog.findViewById(R.id.Name);
        lastName = dialog.findViewById(R.id.Last_Name);
        email = dialog.findViewById(R.id.Email);
        amount = dialog.findViewById(R.id.Amount);
        countryError = dialog.findViewById(R.id.PhoneError);
        TextView titleView = dialog.findViewById(R.id.Title);
        TextView categoryView = dialog.findViewById(R.id.Categoria);
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

        titleView.setText(title);
        categoryView.setText(category);

        String priceString = String.valueOf(price);
        if(!price.equals("To Quote")) {
            String priceWithOutDollar = priceString.replace("$", "");
            double halfOriginalPrice = Double.parseDouble(priceWithOutDollar) / 2;
            String finalPrice = String.format("$%.2f", halfOriginalPrice).replace(",", ".");
            amount.setText(finalPrice);
            amountEntered = halfOriginalPrice;
            priceService.setText(price.replace("$", "$ "));
        }
        else{
            amount.setText("To Quote");
            amount.setEnabled(false);
            priceService.setText("To Quote");
            textAlert.setText("Once you request the service, we'll send you over to Contact to sort out the payment.");
        }
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

        country_number.addTextChangedListener(new CountryTextWatcher(country_number, country, countryError, countryAdapter, countryToPhoneCodesMap,  ignoreTextWatcher,  manualInput));

        country.setOnItemSelectedListener(new CountryItemSelectedListener(country_number,countryToPhoneCodesMap, manualInput));

        amount.addTextChangedListener(new AmountTextWatcher(price,amount, amountError));

        passwordVisibility = new PasswordVisibility();
        password.setTransformationMethod(passwordVisibility);
        passwordVisible.setOnClickListener(view -> PasswordVisibility.Visibility(passwordVisibility, passwordVisible, password));

        request_service.setOnClickListener(view -> {
            if (country_number.getText().toString().isEmpty()) {
                countryError.setText("Enter a country number");
                country_number.setBackgroundResource(R.drawable.error_bg);
                countryError.setVisibility(View.VISIBLE);
            }

            boolean isValid = ValidateRequestManager.validateAllFields(name, lastName,  email,  password,
                     country_number,  phoneNumber, dialog);

            if(isValid) {
                NetworkHelper.registerUser(
                        activity,
                        name.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        country_number.getText().toString(),
                        phoneNumber.getText().toString(),
                        country.getSelectedItem().toString()
                );
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

    public static void showCountryCodeDialog(List<String> possibleCodes, String countryName) {
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
        popUpRequestDialog = PopUpRequestDialog.newInstance(nameText, lastNameText, emailText, countryNumberText, phoneNumberText, countryText, amountEntered, priceDouble);
        popUpRequestDialog.setCancelable(false);
        popUpRequestDialog.setFirstDialog(dialog);
        popUpRequestDialog.show(activity.getSupportFragmentManager(), "PopUpRequestDialog");

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

       popUpRequestDialog.setCardData(mergedData);

    }

}
