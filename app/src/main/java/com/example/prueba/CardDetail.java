package com.example.prueba;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** @noinspection deprecation*/
public class CardDetail extends AppCompatActivity {
    private Dialog dialog;
    private boolean isExpanded = false;
    private ScrollView scrollView;
    private TextView textDescriptionContent;
    private TextView textReadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);

        // Inicialización de componentes
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

        // Configuración de la barra de estado y de navegación
        setSystemBarsColor(R.color.status_bar);
        if (Build.VERSION.SDK_INT >= 0) {
            setTransparentNavigationBarWithDarkIcons();
        }

        // Obtener datos del Intent
        Intent intent = getIntent();
        int imageId = 0;
        String titulo = "";
        String categoria = "";
        String precio = "";
        String tiempo = "";
        if (intent != null) {
            titulo = intent.getStringExtra("Titulo");
            categoria = intent.getStringExtra("Categoria");
            precio = intent.getStringExtra("Precio");
            tiempo = intent.getStringExtra("Tiempo");
            imageId = intent.getIntExtra("Image", 0);
        }

        // Configurar RecyclerView
        List<CardReferData> datos = new ArrayList<>();
        datos.add(new CardReferData("Logo", imageId, true));
        datos.add(new CardReferData("Manual", imageId, false));
        datos.add(new CardReferData("Manual", imageId, false));
        CardReferAdapter adapter = new CardReferAdapter(datos, this, categoria, precio, tiempo, imageId);
        cards.setAdapter(adapter);
        cards.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        // Configurar textos
        textTitle.setText(titulo);
        textCategoria.setText(categoria);
        textPrecio.setText(precio);
        textTiempo.setText(tiempo);
        topImage.setImageResource(imageId);

        // Configurar decorador de items en el RecyclerView
        Resources resources = getResources();
        int endOffset = resources.getDimensionPixelSize(R.dimen.end_offset);
        cards.addItemDecoration(new EndOffsetItemDecoration(endOffset));
        scrollView.setVerticalScrollBarEnabled(false);

        // Configurar listeners
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
        EditText password = dialog.findViewById(R.id.Password);
        EditText name = dialog.findViewById(R.id.Name);
        EditText lastName = dialog.findViewById(R.id.Last_Name);
        EditText email = dialog.findViewById(R.id.Email);
        EditText country_number = dialog.findViewById(R.id.Country_Number);
        PasswordToggleTransformationMethod transformationMethod = new PasswordToggleTransformationMethod();

        TextView nameError = dialog.findViewById(R.id.name_error_text);
        TextView lastNameError = dialog.findViewById(R.id.last_name_error_text);
        TextView emailError = dialog.findViewById(R.id.email_error_text);
        TextView passwordError = dialog.findViewById(R.id.password_error_text);

        nameError.setVisibility(View.GONE);
        lastNameError.setVisibility(View.GONE);
        emailError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);

        country_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        country_number.setSelection(country_number.getText().length());
        country_number.addTextChangedListener(new TextWatcher() {
            private boolean isDeleting = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isDeleting = count > after;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isDeleting) {
                    if (!s.toString().startsWith("+")) {
                        country_number.setText("+" + s.toString().replace("+", ""));
                        country_number.setSelection(country_number.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 4) {
                    s.delete(4, s.length());
                }

                if (s.length() == 1) {
                    country_number.setHint("   +1");
                }
            }
        });

        country_number.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String text = country_number.getText().toString();
                if (!text.startsWith("+")) {
                    if (text.length() >= 3) {
                        text = "+" + text.substring(1);
                    } else {
                        text = "+" + text;
                    }
                    country_number.setText(text);
                }
            }
        });

        passwordVisible.setOnClickListener(view -> togglePasswordVisibility(transformationMethod, passwordVisible, password));
        password.setTransformationMethod(transformationMethod);

        Button request_service = dialog.findViewById(R.id.button_request_2);
        request_service.setOnClickListener(view -> validateAndSubmit(name, lastName, email, password));
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

    private void validateAndSubmit(EditText name, EditText lastName, EditText email, EditText password) {
        String nameText = name.getText().toString();
        String lastNameText = lastName.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        TextView nameError = dialog.findViewById(R.id.name_error_text);
        TextView lastNameError = dialog.findViewById(R.id.last_name_error_text);
        TextView emailError = dialog.findViewById(R.id.email_error_text);
        TextView passwordError = dialog.findViewById(R.id.password_error_text);

        int nameErrorFlag = verificarNombreApellido(nameText, lastNameText);
        switch (nameErrorFlag) {
            case 1:
                setErrorBackground(name, nameError, lastName, lastNameError, true, true);
                break;
            case 2:
                setErrorBackground(name, nameError, lastName, lastNameError, true, false);
                break;
            case 3:
                setErrorBackground(name, nameError, lastName, lastNameError, false, true);
                break;
            default:
                setErrorBackground(name, nameError, lastName, lastNameError, false, false);
        }

        int emailPasswordErrorFlag = verificarEmailPassword(emailText, passwordText);
        switch (emailPasswordErrorFlag) {
            case 1:
                setEmailPasswordError(email, emailError, password, passwordError, "Enter an email", false);
                break;
            case 2:
                setEmailPasswordError(email, emailError, password, passwordError, "Enter a valid email", false);
                break;
            case 3:
                setEmailPasswordError(email, emailError, password, passwordError, null, true);
                break;
            case 4:
                setEmailPasswordError(email, emailError, password, passwordError, "Enter a valid password", true);
                break;
            case 5:
                setEmailPasswordError(email, emailError, password, passwordError, "Enter an email", "Enter a password");
                break;
            case 6:
                setEmailPasswordError(email, emailError, password, passwordError, "Enter a valid email", "Enter a valid password");
                break;
            case 7:
                setEmailPasswordError(email, emailError, password, passwordError, "Enter a valid email", "Enter a password");
                break;
            case 8:
                setEmailPasswordError(email, emailError, password, passwordError, "Enter an email", "Enter a valid password");
                break;
            default:
                email.setBackgroundResource(R.drawable.edit_text_background);
                password.setBackgroundResource(R.drawable.edit_text_background);
                showPopUp();
        }
    }

    private void setErrorBackground(EditText name, TextView nameError, EditText lastName, TextView lastNameError, boolean nameErrorFlag, boolean lastNameErrorFlag) {
        if (nameErrorFlag) {
            name.setBackgroundResource(R.drawable.error_bg);
            nameError.setVisibility(View.VISIBLE);
        } else {
            name.setBackgroundResource(R.drawable.edit_text_background);
            nameError.setVisibility(View.GONE);
        }
        if (lastNameErrorFlag) {
            lastName.setBackgroundResource(R.drawable.error_bg);
            lastNameError.setVisibility(View.VISIBLE);
        } else {
            lastName.setBackgroundResource(R.drawable.edit_text_background);
            lastNameError.setVisibility(View.GONE);
        }
    }

    private void setEmailPasswordError(EditText email, TextView emailError, EditText password, TextView passwordError, String emailErrorMessage, boolean isPasswordError) {
        email.setBackgroundResource(R.drawable.error_bg);
        emailError.setVisibility(View.VISIBLE);
        emailError.setText(emailErrorMessage);
        password.setBackgroundResource(isPasswordError ? R.drawable.error_bg : R.drawable.edit_text_background);
        passwordError.setVisibility(isPasswordError ? View.VISIBLE : View.GONE);
    }

    private void setEmailPasswordError(EditText email, TextView emailError, EditText password, TextView passwordError, String emailErrorMessage, String passwordErrorMessage) {
        email.setBackgroundResource(R.drawable.error_bg);
        emailError.setVisibility(View.VISIBLE);
        emailError.setText(emailErrorMessage);
        password.setBackgroundResource(R.drawable.error_bg);
        passwordError.setVisibility(View.VISIBLE);
        passwordError.setText(passwordErrorMessage);
    }

    private void showPopUp() {
        PopUp popUp = new PopUp();
        popUp.setCancelable(false);
        popUp.show(getSupportFragmentManager(), "popup_window");
        popUp.setPrimerDialog(this.dialog);
    }

    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
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

    public int verificarNombreApellido(String nombre, String apellido) {
        if (nombre.isEmpty() && apellido.isEmpty()) {
            return 1;
        }
        if (nombre.isEmpty()) {
            return 2;
        }
        if (apellido.isEmpty()) {
            return 3;
        }
        return 0;
    }

    public int verificarEmailPassword(String email, String password) {
        if (email.isEmpty() && password.isEmpty()) {
            return 5;
        }
        if (email.isEmpty()) {
            return password.length() < 8 ? 8 : 1;
        }
        if (password.isEmpty()) {
            if (!email.endsWith("@gmail.com")) {
                return 7;
            }
            return 3;
        }
        if (!email.endsWith("@gmail.com") && password.length() < 8) {
            return 6;
        }
        if (!email.endsWith("@gmail.com")) {
            return 2;
        }
        if (password.length() < 8) {
            return 4;
        }
        return 0;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}
