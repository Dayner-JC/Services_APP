package dev.godjango.apk.validation.request;

import static dev.godjango.apk.validation.request.ValidateRequestHelper.validateAmount;
import static dev.godjango.apk.validation.request.ValidateRequestHelper.validateCountryNumber;
import static dev.godjango.apk.validation.request.ValidateRequestHelper.validateEmail;
import static dev.godjango.apk.validation.request.ValidateRequestHelper.validateNameAndLastName;
import static dev.godjango.apk.validation.request.ValidateRequestHelper.validatePassword;
import static dev.godjango.apk.validation.request.ValidateRequestHelper.validatePhoneNumber;

import android.app.Dialog;
import android.widget.EditText;
import android.widget.TextView;

import dev.godjango.apk.R;

public class ValidateRequestManager {

    public static boolean validateAllFields(EditText name, EditText lastName, EditText email, EditText password,
                                            EditText countryNumber, EditText phoneNumber, Dialog dialog) {
        boolean isValid = true;

        TextView nameError = dialog.findViewById(R.id.name_error_text);
        TextView lastNameError = dialog.findViewById(R.id.last_name_error_text);
        TextView emailError = dialog.findViewById(R.id.email_error_text);
        TextView passwordError = dialog.findViewById(R.id.password_error_text);
        TextView amountError = dialog.findViewById(R.id.Amount_Error);
        TextView phoneError = dialog.findViewById(R.id.PhoneNumberError);
        TextView countryError = dialog.findViewById(R.id.PhoneError);

        isValid &= validateNameAndLastName(name, lastName, nameError, lastNameError);
        isValid &= validateEmail(email, emailError);
        isValid &= validatePassword(password, passwordError);
        isValid &= validateAmount(amountError);
        isValid &= validateCountryNumber(countryError, countryNumber);
        isValid &= validatePhoneNumber(phoneNumber, phoneError);

        return isValid;
    }

}

