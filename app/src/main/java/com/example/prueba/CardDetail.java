package com.example.prueba;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Collections;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @noinspection deprecation
 */
public class CardDetail extends AppCompatActivity {
    private Dialog dialog;
    private boolean isExpanded = false;
    private ScrollView scrollView;
    private TextView textDescriptionContent;
    private TextView textReadMore;
    private String precioFinal, precio;
    private TextView countryError;
    private EditText password;
    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText amount;
    private EditText phoneNumber;
    private double precioDouble, amountEntered;
    private CountryData countryData;
    private Map<String, List<String>> countryToPhoneCodesMap;
    private EditText country_number;
    private Spinner country;
    private CountryAdapter countryAdapter;
    private List<String> countryNames;
    private boolean ignoreTextWatcher = false;
    private boolean manualInput = false;
    boolean isUpdatingSpinner = false;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);
        countryData = new CountryData();
        countryToPhoneCodesMap = countryData.getCountryToPhoneCodesMap();

        RecyclerView cards = findViewById(R.id.Cards_Container);
        TextView textTitle = findViewById(R.id.Title);
        TextView textCategoria = findViewById(R.id.Categoria);
        TextView textPrecio = findViewById(R.id.Precio);
        TextView textTiempo = findViewById(R.id.Tiempo);
        textDescriptionContent = findViewById(R.id.text_description_content);
        textReadMore = findViewById(R.id.text_read_more);
        scrollView = findViewById(R.id.ScrollView);
        ImageView topImage = findViewById(R.id.Image);
        ImageButton backButton = findViewById(R.id.Button_back);
        Button requestButton = findViewById(R.id.Button_request);

        setSystemBarsColor(R.color.status_bar);
        if (Build.VERSION.SDK_INT >= 0) {
            setTransparentNavigationBarWithDarkIcons();
        }

        Intent intent = getIntent();
        int imageId = 0;
        String titulo = "";
        String categoria = "";
        precio = "";
        String tiempo = "";
        if (intent != null) {
            titulo = intent.getStringExtra("Titulo");
            categoria = intent.getStringExtra("Categoria");
            precio = intent.getStringExtra("Precio");
            tiempo = intent.getStringExtra("Tiempo");
            imageId = intent.getIntExtra("Image", 0);
        }

        assert precio != null;
        String precioSinSigno = precio.substring(1);
        String precioText = "$ " + precioSinSigno;
        precioDouble = Double.parseDouble(precioSinSigno);

        double precioMitadDouble = precioDouble / 2.0;

        precioFinal = String.format("%.2f", precioMitadDouble);

        List<CardReferData> datos = new ArrayList<>();
        datos.add(new CardReferData("Logo", imageId, true));
        datos.add(new CardReferData("Manual", imageId, false));
        datos.add(new CardReferData("Manual", imageId, false));
        CardReferAdapter adapter = new CardReferAdapter(datos, this, categoria, precio, tiempo, imageId);
        cards.setAdapter(adapter);
        cards.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        textTitle.setText(titulo);
        textCategoria.setText(categoria);
        textPrecio.setText(precioText);
        textTiempo.setText(tiempo);
        topImage.setImageResource(imageId);

        Resources resources = getResources();
        int endOffset = resources.getDimensionPixelSize(R.dimen.end_offset);
        cards.addItemDecoration(new EndOffsetItemDecoration(endOffset));
        scrollView.setVerticalScrollBarEnabled(false);

        backButton.setOnClickListener(this::onBackButtonClicked);
        textReadMore.setOnClickListener(this::onReadMoreClicked);
        requestButton.setOnClickListener(this::ShowDialog);
    }

    private void onBackButtonClicked(View view) {
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void onReadMoreClicked(View view) {
        isExpanded = !isExpanded;
        if (isExpanded) {
            textDescriptionContent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            scrollView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            textReadMore.setText("Read less...");
            scrollView.setVerticalScrollBarEnabled(true);
        } else {
            textDescriptionContent.getLayoutParams().height = dpToPx(74);
            scrollView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            textReadMore.setText("Read more...");
            scrollView.setVerticalScrollBarEnabled(false);
            scrollView.scrollTo(0, 0);
        }
        textDescriptionContent.requestLayout();
    }

    private void ShowDialog(View v) {
        showRegister();
    }

    @SuppressLint("SetTextI18n")
    private void showRegister() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_register);
        dialog.setCancelable(true);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(650));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setGravity(80);

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
        phoneNumber = dialog.findViewById(R.id.Phone_Number);
        country = dialog.findViewById(R.id.Country);
        country_number = dialog.findViewById(R.id.Country_Number);
        Button request_service = dialog.findViewById(R.id.button_request_2);

        nameError.setVisibility(View.GONE);
        lastNameError.setVisibility(View.GONE);
        emailError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);
        amountError.setVisibility(View.GONE);

        phoneNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        country_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        country_number.setSelection(country_number.getText().length());

        countryError.setVisibility(View.GONE);

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(6);
        amount.setFilters(filters);

        countryNames = new ArrayList<>();
        countryData = new CountryData();
        countryToPhoneCodesMap = countryData.getCountryToPhoneCodesMap();
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
                isUpdatingSpinner = true;

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
                isUpdatingSpinner = false;
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
                }

                String selectedCountry = parent.getItemAtPosition(position).toString();
                if (!selectedCountry.equals("Country not found")) {
                    List<String> possibleCodes = countryToPhoneCodesMap.get(selectedCountry);
                    if (possibleCodes != null && possibleCodes.size() > 1 && !manualInput) {
                        showCountryCodeDialog(possibleCodes, selectedCountry);
                    } else if (possibleCodes != null && possibleCodes.size() == 1) {
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
            private boolean isErrorDisplayed = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String amountText = editable.toString();
                String precioSinSigno = precio.substring(1);
                precioDouble = Double.parseDouble(precioSinSigno);
                double mitadPrecioOriginal = precioDouble / 2;

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
                        amountEntered = Double.parseDouble(amountText.substring(1));
                        if (amountEntered < mitadPrecioOriginal || amountEntered > precioDouble) {
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

        PasswordToggleTransformationMethod transformationMethod = new PasswordToggleTransformationMethod();
        passwordVisible.setOnClickListener(view -> togglePasswordVisibility(transformationMethod, passwordVisible, password));
        password.setTransformationMethod(transformationMethod);

        precioFinal = precioFinal.replace(',', '.');
        amount.setText("$" + precioFinal);

        request_service.setOnClickListener(view -> validateAndSubmit(name, lastName, email, password));
    }

    private void configureSpinner() {
        List<String> countryList = countryData.getCountryNames();
        countryNames.clear();
        countryNames.addAll(countryList);
        Collections.sort(countryNames);

        if (!countryNames.contains("Country not found")) {
            countryNames.add("Country not found");
        }

        countryAdapter = new CountryAdapter(this, countryNames);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryAdapter);

        int position = countryAdapter.getPosition("United States");
        if (position >= 0) {
            country.setSelection(position);
        }
    }

    private void showCountryCodeDialog(List<String> possibleCodes, String countryName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona su Código de País para " + countryName);

        String[] codesArray = possibleCodes.toArray(new String[0]);
        builder.setItems(codesArray, (dialog, which) -> {
            String selectedCode = codesArray[which];
            country_number.setText(selectedCode);
        });

        builder.show();
    }

    @SuppressLint("SetTextI18n")
    private void validateAndSubmit(EditText name, EditText lastName, EditText email, EditText password) {

        TextView nameError = dialog.findViewById(R.id.name_error_text);
        TextView lastNameError = dialog.findViewById(R.id.last_name_error_text);
        TextView emailError = dialog.findViewById(R.id.email_error_text);
        TextView passwordError = dialog.findViewById(R.id.password_error_text);
        TextView amountError = dialog.findViewById(R.id.Amount_Error);

        nameError.setVisibility(View.GONE);
        lastNameError.setVisibility(View.GONE);
        emailError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);
        amountError.setVisibility(View.GONE);

        boolean isNameValid = validateNameAndLastName(name, lastName, nameError, lastNameError);
        boolean isEmailValid = validateEmail(email, emailError);
        boolean isPasswordValid = validatePassword(password, passwordError);
        boolean isAmountValid = validateAmount(amountError);
        boolean isCountryNumberValid = validateCountryNumber(countryError);

        if (!isNameValid || !isEmailValid || !isPasswordValid || !isAmountValid || !isCountryNumberValid) {
            return;
        }

        showPopUp();
    }

    @SuppressLint("SetTextI18n")
    private boolean validateNameAndLastName(EditText name, EditText lastName, TextView nameError, TextView lastNameError) {
        String nameText = name.getText().toString();
        String lastNameText = lastName.getText().toString();
        boolean isValid = true;

        if (nameText.isEmpty()){

            name.setBackgroundResource(R.drawable.error_bg);
            nameError.setText("Enter a name");
            nameError.setVisibility(View.VISIBLE);
            isValid = false;

        }else if (!nameText.matches("[a-zA-Z]+")) {

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
    private boolean validateEmail(EditText email, TextView emailError) {
        String emailText = email.getText().toString();
        boolean isValid = true;

        if(emailText.isEmpty()){

            email.setBackgroundResource(R.drawable.error_bg);
            emailError.setText("Enter a email");
            emailError.setVisibility(View.VISIBLE);
            isValid = false;

        }else if (!emailText.endsWith("@gmail.com")) {
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
    private boolean validatePassword(EditText password, TextView passwordError) {
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

    @SuppressLint("SetTextI18n")
    private boolean validateAmount(TextView amountError) {
        return amountError.getVisibility() != View.VISIBLE;
    }

    private boolean validateCountryNumber(TextView countryError) {
        return countryError.getVisibility() != View.VISIBLE;
    }

    private void togglePasswordVisibility(PasswordToggleTransformationMethod transformationMethod, ImageButton passwordVisible, EditText password) {
        transformationMethod.togglePasswordVisibility();
        if (transformationMethod.isPasswordVisible) {
            passwordVisible.setImageResource(R.drawable.ic_hide);
        } else {
            passwordVisible.setImageResource(R.drawable.ic_view);
        }
        password.setText(password.getText());
        password.setSelection(Objects.requireNonNull(password.getText()).length());
    }

    private void showPopUp() {
        String nameText = name.getText().toString();
        String lastNameText = lastName.getText().toString();
        String emailText = email.getText().toString();
        String countryNumberText = country_number.getText().toString();
        String countryText = country.getSelectedItem().toString();
        String phoneNumberText = phoneNumber.getText().toString();

        PopUp popUp = PopUp.newInstance(nameText, lastNameText, emailText, countryNumberText,phoneNumberText, countryText, amountEntered, precioDouble);
        popUp.show(getSupportFragmentManager(), "popup_window");
        popUp.setCancelable(false);
        popUp.setPrimerDialog(this.dialog);
    }

    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    private void setSystemBarsColor(int colorResId) {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
        window.setNavigationBarColor(0);
    }

    private void setTransparentNavigationBarWithDarkIcons() {
        Window window = getWindow();
        window.setNavigationBarColor(0);
        View decorView = window.getDecorView();
        int flags = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(flags | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }
}
