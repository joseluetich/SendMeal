package com.dam.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.sendmeal.model.CuentaBancaria;
import com.dam.sendmeal.model.Tarjeta;
import com.dam.sendmeal.model.Usuario;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewAccountActivity extends AppCompatActivity {

    final String DEBIT_TYPE = "debit";
    final String CREDIT_TYPE = "credit";
    TextInputLayout nameTextField, emailTextField, passwordTextField, repeatedPasswordTextField,
            cardNumberTextField, ccvTextField, cbuTextField, aliasCbuTextField;
    RadioGroup cardsRadioGroup;
    RadioButton creditRadioButton, debitRadioButton;
    Spinner monthSpinner, yearSpinner;
    SwitchMaterial addInitialAmountSwitch;
    Slider initialCreditAmountSlider;
    CheckBox termsAndConditionsCheckBox;
    Button registerButton;
    TextView initialCreditTextView, initialCreditAmountTextView;
    Usuario user;
    Tarjeta card;
    CuentaBancaria bankAccount;
    Boolean passwordMatch = true;
    Toolbar toolbarRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        nameTextField = findViewById(R.id.nameTextField);
        emailTextField = findViewById(R.id.emailTextField);
        passwordTextField = findViewById(R.id.passwordTextField);
        repeatedPasswordTextField = findViewById(R.id.repeatedPasswordTextField);
        cardNumberTextField = findViewById(R.id.cardNumberTextField);
        ccvTextField = findViewById(R.id.ccvTextField);
        cardsRadioGroup = findViewById(R.id.cardsRadioGroup);
        creditRadioButton = findViewById(R.id.creditRadioButton);
        debitRadioButton = findViewById(R.id.debitRadioButton);
        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        cbuTextField = findViewById(R.id.cbuTextField);
        aliasCbuTextField = findViewById(R.id.aliasCbuTextField);
        addInitialAmountSwitch = findViewById(R.id.addInitialAmountSwitch);
        initialCreditTextView = findViewById(R.id.initialCreditTextView);
        initialCreditAmountTextView = findViewById(R.id.initialCreditAmountTextView);
        initialCreditAmountSlider = findViewById(R.id.initialCreditAmountSlider);
        termsAndConditionsCheckBox = findViewById(R.id.termsAndConditionsCheckBox);
        registerButton = findViewById(R.id.registerButton);

        user = new Usuario();
        card = new Tarjeta();
        bankAccount = new CuentaBancaria();

        user.setCuentaBancaria(bankAccount);
        user.setTarjeta(card);

        toolbarRegistro = findViewById(R.id.newAccountToolbar);
        setSupportActionBar(toolbarRegistro);//configuro la toolbar

        monthSpinner.setEnabled(false);
        yearSpinner.setEnabled(false);

        mandatoryFieldValidation(emailTextField);
        mandatoryFieldValidation(passwordTextField);
        mandatoryFieldValidation(repeatedPasswordTextField);
        mandatoryFieldValidation(cardNumberTextField);
        mandatoryFieldValidation(ccvTextField);

        Objects.requireNonNull(nameTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String name = nameTextField.getEditText().getText().toString();
                    user.setNombre(name);
                }
            }
        });

        Objects.requireNonNull(emailTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String email = emailTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(email)) {
                        if (!emailValidation(email)) {
                            emailTextField.setHelperTextEnabled(false);
                            emailTextField.setError(getString(R.string.invalidEmail));
                        }
                        user.setEmail(email);
                    } else {
                        user.setEmail(null);
                    }
                }
            }
        });

        Objects.requireNonNull(passwordTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String password = passwordTextField.getEditText().getText().toString();
                    String repeatedPassword = Objects.requireNonNull(repeatedPasswordTextField.getEditText()).getText().toString();
                    if (!TextUtils.isEmpty(password)) {
                        if (!TextUtils.isEmpty(repeatedPassword)) {
                            if (!passwordMatchValidation(password, repeatedPassword)) {
                                repeatedPasswordTextField.setHelperTextEnabled(false);
                                repeatedPasswordTextField.setError(" ");
                                passwordTextField.setHelperTextEnabled(false);
                                passwordTextField.setError(getString(R.string.errorPasswordMatch));
                            } else {
                                repeatedPasswordTextField.setHelperTextEnabled(true);
                                repeatedPasswordTextField.setHelperText(getString(R.string.obligatoryFieldHelper));
                                repeatedPasswordTextField.setError(null);
                                user.setClave(password);
                            }
                        }
                    }
                }
            }
        });

        Objects.requireNonNull(repeatedPasswordTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String password = passwordTextField.getEditText().getText().toString();
                    String repeatedPassword = repeatedPasswordTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(repeatedPassword)) {
                        if (!passwordMatchValidation(password, repeatedPassword)) {
                            repeatedPasswordTextField.setHelperTextEnabled(false);
                            repeatedPasswordTextField.setError(" ");
                            passwordTextField.setHelperTextEnabled(false);
                            passwordTextField.setError(getString(R.string.errorPasswordMatch));
                        } else {
                            passwordTextField.setHelperTextEnabled(true);
                            passwordTextField.setHelperText(getString(R.string.obligatoryFieldHelper));
                            passwordTextField.setError(null);
                            if (user.getClave() == null ||
                                    !user.getClave().equals(password)) user.setClave(password);
                        }
                    }
                }
            }
        });

        cardsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) { //listener radiogroup tipo de tarjeta
                if (id == R.id.creditRadioButton) {
                    user.getTarjeta().setEsCredito(true);
                } else {
                    user.getTarjeta().setEsCredito(false);
                }
            }
        });

        Objects.requireNonNull(cardNumberTextField.getEditText()).addTextChangedListener(new TextWatcher() {
            //listener numero de tarjeta

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000 0000 0000 0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(cardNumberTextField.getEditText().getText())) {
                    ccvTextField.setEnabled(true);
                    monthSpinner.setEnabled(true);
                    yearSpinner.setEnabled(true);
                } else {
                    ccvTextField.setEnabled(false);
                    monthSpinner.setEnabled(false);
                    yearSpinner.setEnabled(false);
                }
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }
        });

        Objects.requireNonNull(cardNumberTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String cardNumber = cardNumberTextField.getEditText().getText().toString();
                    user.getTarjeta().setNumero(cardNumber);
                }
            }
        });

        Objects.requireNonNull(ccvTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                String ccv = ccvTextField.getEditText().getText().toString();
                if (!hasFocus) {
                    if (!TextUtils.isEmpty(ccv)) {
                        if (ccv.length() < 3) {
                            ccvTextField.setHelperTextEnabled(false);
                            ccvTextField.setError(getString(R.string.errorCCV));
                        }
                    }
                    user.getTarjeta().setCcv(ccv);
                }
            }
        });


        monthSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String selectedMonth = monthSpinner.getSelectedItem().toString();
                    if (!selectedMonth.equals(getString(R.string.mes))) {
                        ((TextView) monthSpinner.getSelectedView()).setError(null);
                    } else {
                        ((TextView) monthSpinner.getSelectedView()).setError(" ");
                    }
                }
            }
        });


        yearSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String selectedYear = yearSpinner.getSelectedItem().toString();
                    if (!selectedYear.equals(getString(R.string.mes))) {
                        ((TextView) yearSpinner.getSelectedView()).setError(null);
                    } else {
                        ((TextView) yearSpinner.getSelectedView()).setError(" ");
                    }
                }
            }
        });


        addInitialAmountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) { //listener del switch
                if (b) { //si esta activado
                    initialCreditTextView.setVisibility(View.VISIBLE); //muestro el texto del monto
                    initialCreditAmountSlider.setVisibility(View.VISIBLE); //y muestro la seekbar
                    initialCreditAmountTextView.setVisibility(View.VISIBLE);
                } else { //si esta desactivado
                    initialCreditTextView.setVisibility(View.GONE); //desaparece el texto del monto
                    initialCreditAmountSlider.setVisibility(View.GONE); //y desaparece la seekbar
                    initialCreditAmountTextView.setVisibility(View.GONE);
                }
            }
        });

        initialCreditAmountSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat format = NumberFormat.getCurrencyInstance();
                format.setMaximumFractionDigits(0);
                format.setCurrency(Currency.getInstance("ARS"));
                return format.format(value);
            }
        });

        initialCreditAmountSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                NumberFormat format = NumberFormat.getCurrencyInstance();
                format.setMaximumFractionDigits(0);
                format.setCurrency(Currency.getInstance("ARS"));
                initialCreditAmountTextView.setText(format.format(slider.getValue()));
                user.setCredito((double) slider.getValue());
            }
        });
//
        termsAndConditionsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) { //listener del checkbox terminos
                if (termsAndConditionsCheckBox.isChecked()) { //si esta marcado
                    registerButton.setEnabled(true); //puedo habilitar el boton
                } else {
                    registerButton.setEnabled(false);
                }
            }
        });
//
        registerButton.setOnClickListener(new View.OnClickListener() { //listener del boton registrar
            @Override
            public void onClick(View view) {
                if (validateForm()) {

                    Calendar calendar = Calendar.getInstance();
                    int idSelectedMonth = transformarMes(monthSpinner.getSelectedItem().toString());
                    calendar.set(Calendar.YEAR, Integer.parseInt(yearSpinner.getSelectedItem().toString())); //no deberia hacer dos veces lo mismo
                    calendar.set(Calendar.MONTH, idSelectedMonth);
                    Date dueDate = calendar.getTime();
                    user.getTarjeta().setVencimiento(dueDate);

                    String accountCBU = Objects.requireNonNull(cbuTextField.getEditText()).getText().toString();
                    String accountAliasCBU = Objects.requireNonNull(aliasCbuTextField.getEditText()).getText().toString();
                    user.getCuentaBancaria().setCbu(accountCBU);
                    user.getCuentaBancaria().setAlias(accountAliasCBU);

                    String name = Objects.requireNonNull(nameTextField.getEditText()).getText().toString();
                    user.setNombre(name);


                    Toast aviso = Toast.makeText(NewAccountActivity.this, "Registro exitoso", Toast.LENGTH_LONG); //aviso al usuario
                    aviso.show();
                } else {
                    Toast aviso = Toast.makeText(NewAccountActivity.this, "ERROOOOR", Toast.LENGTH_LONG); //aviso al usuario
                    aviso.show();
                }

            }
        });
    }


    public boolean validateForm() {

        boolean formValid = true;

        if (!validateAllMandatoryFields()) {
            formValid = false;
        }
        if (emailTextField.getError() != null &&
                !Objects.requireNonNull(emailTextField.getError()).toString().isEmpty()) {
            formValid = false;
        }

        if (passwordTextField.getError() != null &&
                !Objects.requireNonNull(passwordTextField.getError()).toString().isEmpty()) {
            formValid = false;
        }

        if (repeatedPasswordTextField.getError() != null &&
                !Objects.requireNonNull(repeatedPasswordTextField.getError()).toString().isEmpty()) {
            formValid = false;
        }

        if (cardNumberTextField.getError() != null &&
                !Objects.requireNonNull(cardNumberTextField.getError()).toString().isEmpty()) {
            formValid = false;
        }

        if (ccvTextField.getError() != null &&
                !Objects.requireNonNull(ccvTextField.getError()).toString().isEmpty()) {
            formValid = false;
        }

        if (!monthSpinner.getSelectedItem().equals(getString(R.string.mes))
                && !yearSpinner.getSelectedItem().equals(getString(R.string.ano))) {
            if (!vencimientoValido(yearSpinner.getSelectedItem().toString(), monthSpinner.getSelectedItem().toString())) {
                ((TextView) monthSpinner.getSelectedView()).setError("Error message");
                ((TextView) yearSpinner.getSelectedView()).setError("Error message");
                formValid = false;
            }
        } else {
            if (monthSpinner.getSelectedItem().equals(getString(R.string.mes)))
                ((TextView) monthSpinner.getSelectedView()).setError("Error message");
            if (yearSpinner.getSelectedItem().equals(getString(R.string.ano)))
                ((TextView) yearSpinner.getSelectedView()).setError("Error message");
            formValid = false;
        }


        return formValid;
    }

    public boolean vencimientoValido(String ano, String mes) {
        boolean check = true;

        Calendar vencimiento = Calendar.getInstance();
        vencimiento.set(Calendar.YEAR, Integer.parseInt(ano)); //asigna el anio ingresado al calendar
        vencimiento.set(Calendar.MONTH, transformarMes(mes)); //asigna el mes ingresado al calendar

        Calendar hoy = Calendar.getInstance(); //obtiene fecha actual
        hoy.add(Calendar.MONTH, 3); //le suma tres meses

        if (!vencimiento.after(hoy)) { //analiza si el vencimiento ingresado es por lo menos tres meses despues de la fecha actual
            check = false;
        }
        return check;
    }

    public boolean validateAllMandatoryFields() {
        boolean allCompleted = true;

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            emailTextField.setHelperTextEnabled(false);
            emailTextField.setError(getString(R.string.errorObligatoryField));
            allCompleted = false;
        }

        if (user.getClave() == null || (user.getClave().isEmpty() &&
                Objects.requireNonNull(passwordTextField.getEditText()).getText().toString().isEmpty())) {
            passwordTextField.setHelperTextEnabled(false);
            passwordTextField.setError(getString(R.string.errorObligatoryField));
            allCompleted = false;
        }

        if (user.getClave() == null || (user.getClave().isEmpty() &&
                Objects.requireNonNull(repeatedPasswordTextField.getEditText()).getText().toString().isEmpty())) {
            repeatedPasswordTextField.setHelperTextEnabled(false);
            repeatedPasswordTextField.setError(getString(R.string.errorObligatoryField));
            allCompleted = false;
        }

        if (user.getTarjeta().getNumero() == null || user.getTarjeta().getNumero().isEmpty()) {
            cardNumberTextField.setHelperTextEnabled(false);
            cardNumberTextField.setError(getString(R.string.errorObligatoryField));
            allCompleted = false;
        }

        if (user.getTarjeta().getCcv() == null || user.getTarjeta().getCcv().isEmpty()) {
            ccvTextField.setHelperTextEnabled(false);
            ccvTextField.setError(getString(R.string.errorObligatoryField));
            allCompleted = false;
        }


        return allCompleted;
    }

    public int transformarMes(String mesSeleccionado) {
        switch (mesSeleccionado) {
            case "Enero":
                return 0;
            case "Febrero":
                return 1;
            case "Marzo":
                return 2;
            case "Abril":
                return 3;
            case "Mayo":
                return 4;
            case "Junio":
                return 5;
            case "Julio":
                return 6;
            case "Agosto":
                return 7;
            case "Septiembre":
                return 8;
            case "Octubre":
                return 9;
            case "Noviembre":
                return 10;
            case "Diciembre":
                return 11;
        }
        return -1;
    }


    public void mandatoryFieldValidation(final TextInputLayout field) {
        Objects.requireNonNull(field.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(field.getEditText().getText())) {
                    // Clear error text
                    field.setHelperTextEnabled(true);
                    field.setHelperText(getString(R.string.obligatoryFieldHelper));
                    field.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(field.getEditText().getText())) {
                    field.setHelperTextEnabled(false);
                    field.setError(getString(R.string.errorObligatoryField));
                }
            }
        });
    }

    public boolean emailValidation(String email) {
        Pattern emailPattern = Pattern.compile(getString(R.string.entidadRegularEmail)); //indica como deberia ser el email
        Matcher matcherEmail = emailPattern.matcher(email); //lo va a comparar con lo ingresado en email

        //analiza si el email ingresado tiene un arroba y tres letras detras
        return matcherEmail.find();
    }

    public boolean passwordMatchValidation(String password, String repeatedPassword) {
        passwordMatch = (password != null && repeatedPassword != null
                && !password.isEmpty() && !repeatedPassword.isEmpty() && password.equals(repeatedPassword));
        return passwordMatch;
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }

    private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }
        return formatted.toString();
    }

    private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
        boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
        for (int i = 0; i < s.length(); i++) { // check that every element is right
            if (i > 0 && (i + 1) % dividerModulo == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }
}