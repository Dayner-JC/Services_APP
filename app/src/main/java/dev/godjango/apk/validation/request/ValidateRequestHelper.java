package dev.godjango.apk.validation.request;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dev.godjango.apk.R;

public class ValidateRequestHelper {

    @SuppressLint("SetTextI18n")
    public static boolean validateNameAndLastName(EditText name, EditText lastName, TextView nameError, TextView lastNameError) {
        String nameText = name.getText().toString();
        String lastNameText = lastName.getText().toString();
        boolean isValid = true;

        if (nameText.isEmpty()) {
            name.setBackgroundResource(R.drawable.error_bg);
            nameError.setText("Enter a name");
            nameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (!nameText.matches("[a-zA-Z]+")) {
            name.setBackgroundResource(R.drawable.error_bg);
            nameError.setText("Enter a valid name");
            nameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            name.setBackgroundResource(R.drawable.edit_text_background);
            nameError.setVisibility(View.GONE);
        }

        if (lastNameText.isEmpty()) {
            lastName.setBackgroundResource(R.drawable.error_bg);
            lastNameError.setText("Enter a last name");
            lastNameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (!lastNameText.matches("[a-zA-Z]+")) {
            lastName.setBackgroundResource(R.drawable.error_bg);
            lastNameError.setText("Enter a valid last name");
            lastNameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            lastName.setBackgroundResource(R.drawable.edit_text_background);
            lastNameError.setVisibility(View.GONE);
        }

        return isValid;
    }

    @SuppressLint("SetTextI18n")
    public static boolean validateEmail(EditText email, TextView emailError) {
        String emailText = email.getText().toString();
        boolean isValid = true;

        if (emailText.isEmpty()) {
            email.setBackgroundResource(R.drawable.error_bg);
            emailError.setText("Enter a email");
            emailError.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (!emailText.endsWith("@gmail.com")) {
            email.setBackgroundResource(R.drawable.error_bg);
            emailError.setText("Enter a valid email");
            emailError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            email.setBackgroundResource(R.drawable.edit_text_background);
            emailError.setVisibility(View.GONE);
        }

        return isValid;
    }

    @SuppressLint("SetTextI18n")
    public static boolean validatePassword(EditText password, TextView passwordError) {
        String passwordText = password.getText().toString();
        boolean isValid = true;

        if (passwordText.isEmpty()) {
            password.setBackgroundResource(R.drawable.error_bg);
            passwordError.setText("Enter a password");
            passwordError.setVisibility(View.VISIBLE);
            isValid = false;
        } else if (passwordText.length() < 8 || passwordText.length() > 16) {
            password.setBackgroundResource(R.drawable.error_bg);
            passwordError.setText("Enter a valid password");
            passwordError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            password.setBackgroundResource(R.drawable.edit_text_background);
            passwordError.setVisibility(View.GONE);
        }
        return isValid;
    }

    public static boolean validateAmount(TextView amountError) {
        return amountError.getVisibility() != View.VISIBLE;
    }

    public static boolean validateCountryNumber(TextView countryError,EditText countryNumber) {
        return countryError.getVisibility() != View.VISIBLE;
    }

    @SuppressLint("SetTextI18n")
    public static boolean validatePhoneNumber(EditText phone, TextView phoneError){
        if(phone.getText().toString().isEmpty()){
            phoneError.setText("Enter a phone number");
            phoneError.setVisibility(View.VISIBLE);
            phone.setBackgroundResource(R.drawable.error_bg);
            return false;
        }else if(phone.getText().toString().length() < 8 || !phone.getText().toString().matches("\\d+")){
            phoneError.setText("Enter a valid phone number");
            phoneError.setVisibility(View.VISIBLE);
            phone.setBackgroundResource(R.drawable.error_bg);
            return false;
        } else {
            phoneError.setVisibility(View.GONE);
            phone.setBackgroundResource(R.drawable.edit_text_background);
            return true;
        }
    }
}
