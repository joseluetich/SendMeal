package com.dam.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.icu.number.NumberFormatter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.dam.sendmeal.model.CuentaBancaria;
import com.dam.sendmeal.model.Tarjeta;
import com.dam.sendmeal.model.Usuario;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Formatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    TextInputLayout nameTextField, emailTextField, passwordTextField, repeatedPasswordTextField,
            cardNumberTextField, ccvTextField, cbuTextField, aliasCbuTextField;
    RadioGroup cardsRadioGroup;
    RadioButton creditRadioButton, debitRadioButton;
    Spinner monthSpinner, yearSpinner;
    SwitchMaterial addInitialAmountSwitch;
    Slider initialCreditAmountSlider;
    CheckBox termsAndConditionsCheckBox;
    Button registerButton;
    TextView errorInitialAmount, errorDueDate, initialCreditTextView, initialCreditAmountTextView;


    String email, password, repeatedPassword, cardType, cardNumber, cardCCV; //guarda el contenido de inputContrasena e inputcontrasenaRepetida


    Boolean passwordMatch = true;

    TextView monto;
    TextView creditoInicial;
    TextView errorCargaInicial;

    String mesSeleccionado;
    String anoSeleccionado;

    final String DEBIT_TYPE = "debit";
    final String CREDIT_TYPE = "credit";

    int idCards = 0; //falta asignar un id para guardar en la clase tarjeta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtengo las entidades por id
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
        addInitialAmountSwitch = findViewById(R.id.addInitialAmountSwitch);
        initialCreditTextView = findViewById(R.id.initialCreditTextView);
        initialCreditAmountTextView = findViewById(R.id.initialCreditAmountTextView);
        initialCreditAmountSlider = findViewById(R.id.initialCreditAmountSlider);
        termsAndConditionsCheckBox = findViewById(R.id.termsAndConditionsCheckBox);
        registerButton = findViewById(R.id.registerButton);

//        monto = findViewById(R.id.monto);
//        creditoInicial = findViewById(R.id.creditoInicial);
//        emailTextField = findViewById(R.id.emailTextField);
//        cbuTextField = findViewById(R.id.cbuTextField);
//        aliasCbuTextField = findViewById(R.id.aliasCbuTextField);
//        errorInitialAmount = findViewById(R.id.errorInitialAmount);
        errorDueDate = findViewById(R.id.errorDueDate);


        monthSpinner.setEnabled(false);
        yearSpinner.setEnabled(false);

        mandatoryFieldValidation(emailTextField);
        mandatoryFieldValidation(passwordTextField);
        mandatoryFieldValidation(repeatedPasswordTextField);
        mandatoryFieldValidation(cardNumberTextField);
        mandatoryFieldValidation(ccvTextField);

        Objects.requireNonNull(emailTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!TextUtils.isEmpty(emailTextField.getEditText().getText())) {
                        if (!emailValidation()) {
                            emailTextField.setHelperTextEnabled(false);
                            emailTextField.setError(getString(R.string.invalidEmail));
                        } else {
                            email = Objects.requireNonNull(repeatedPasswordTextField.getEditText()).getText().toString();
                        }
                    }
                }
            }
        });

        Objects.requireNonNull(passwordTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!TextUtils.isEmpty(passwordTextField.getEditText().getText())) {
                        password = passwordTextField.getEditText().getText().toString();
                        if (!TextUtils.isEmpty(Objects.requireNonNull(repeatedPasswordTextField.getEditText()).getText())) {
                            if (!passwordMatchValidation()) {
                                repeatedPasswordTextField.setHelperTextEnabled(false);
                                repeatedPasswordTextField.setError(" ");
                                passwordTextField.setHelperTextEnabled(false);
                                passwordTextField.setError(getString(R.string.errorPasswordMatch));
                            } else {
                                repeatedPasswordTextField.setHelperTextEnabled(true);
                                repeatedPasswordTextField.setHelperText(getString(R.string.obligatoryFieldHelper));
                                repeatedPasswordTextField.setError(null);
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
                    if (!TextUtils.isEmpty(repeatedPasswordTextField.getEditText().getText())) {
                        repeatedPassword = repeatedPasswordTextField.getEditText().getText().toString();
                        if (!passwordMatchValidation()) {
                            repeatedPasswordTextField.setHelperTextEnabled(false);
                            repeatedPasswordTextField.setError(" ");
                            passwordTextField.setHelperTextEnabled(false);
                            passwordTextField.setError(getString(R.string.errorPasswordMatch));
                        } else {
                            passwordTextField.setHelperTextEnabled(true);
                            passwordTextField.setHelperText(getString(R.string.obligatoryFieldHelper));
                            passwordTextField.setError(null);
                        }
                    }
                }
            }
        });

        Objects.requireNonNull(ccvTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!TextUtils.isEmpty(ccvTextField.getEditText().getText())) {
                        if (ccvTextField.getEditText().getText().length() < 3) {
                            ccvTextField.setHelperTextEnabled(false);
                            ccvTextField.setError(getString(R.string.errorCCV));
                        } else {
                            cardCCV = Objects.requireNonNull(repeatedPasswordTextField.getEditText()).getText().toString();
                        }
                    }
                }
            }
        });


        cardsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) { //listener radiogroup tipo de tarjeta
                if (id == R.id.creditRadioButton) {
                    cardType = CREDIT_TYPE;
                } else {
                    cardType = DEBIT_TYPE;
                }
//                { //si esta marcado credito
//                    creditRadioButton.setChecked(true); //queda marcado credito
//                    debito.setChecked(false); //y desmarcado debito
//                } //de esta manera me aseguro que una opcion se elija si o si
//                else { //si no
//                    credito.setChecked(false); //queda desmarcado credito
//                    debito.setChecked(true); //y marcado debito
//                }
            }
        });

        Objects.requireNonNull(cardNumberTextField.getEditText()).addTextChangedListener(new TextWatcher() { //listener numero de tarjeta
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
////                numeroTarjeta = cardNumberTextField.getText().toString(); //almacena el string del campo numero de tarjeta
//                if(!TextUtils.isEmpty(cardNumberTextField.getEditText().getText())){
//                    ccvTextField.setEnabled(true);
//                    monthSpinner.setEnabled(true);
//                    yearSpinner.setEnabled(true);
//                }else {
//                    ccvTextField.setEnabled(false);
//                    monthSpinner.setEnabled(false);
//                    yearSpinner.setEnabled(false);
//                }

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
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


//                if(!numeroTarjeta.isEmpty()) { //si el campo no esta vacio
//                    ccvTextField.setEnabled(true); //se habilita el ccv el mes y el anio
//                    spinnerMes.setEnabled(true);
//                    spinnerAno.setEnabled(true);
//                } else { //si el campo esta vacio
//                    ccvTextField.setEnabled(false); //permanecen deshabilitados el ccv el mes y el anio
//                    spinnerAno.setEnabled(false);
//                    spinnerMes.setEnabled(false);
//                }
//                String errorNumeroTarjeta = null; //verifica que el campo obligatorio sea completado
//                if (TextUtils.isEmpty(cardNumberTextField.getText())) {
//                    errorNumeroTarjeta = getString(R.string.campoObligatorio);
//                }
//                toggleTextInputLayoutError(layoutNumeroTarjeta, errorNumeroTarjeta);
//            }
//        });

//
//
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mesSeleccionado = monthSpinner.getSelectedItem().toString();
                if(!mesSeleccionado.equals(getString(R.string.mes))) {
                    ((TextView)monthSpinner.getSelectedView()).setError(null);
                    errorDueDate.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                anoSeleccionado = yearSpinner.getSelectedItem().toString();
                if(!anoSeleccionado.equals(getString(R.string.ano))) {
                    ((TextView)yearSpinner.getSelectedView()).setError(null);
                    errorDueDate.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//
//        montoCredito.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //listener de la seekbar
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                monto.setText(String.valueOf(i)); //le asigno al texto de encima de la barra el valor que marca la barra
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) { }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) { }
//        });


        addInitialAmountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) { //listener del switch
                if(b) { //si esta activado
                    initialCreditTextView.setVisibility(View.VISIBLE); //muestro el texto del monto
                    initialCreditAmountSlider.setVisibility(View.VISIBLE); //y muestro la seekbar
                    initialCreditAmountTextView.setVisibility(View.VISIBLE);
                }
                else { //si esta desactivado
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
           }
       });
//
        termsAndConditionsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) { //listener del checkbox terminos
                if(termsAndConditionsCheckBox.isChecked()) { //si esta marcado
                    registerButton.setEnabled(true); //puedo habilitar el boton
                }
                else {
                    registerButton.setEnabled(false);
                }
            }
        });
//
        registrar.setOnClickListener(new View.OnClickListener() { //listener del boton registrar
            @Override
            public void onClick(View view) {
                if(cumpleRequisitos()) { //si cumple con todos los requisitos
                    //creo la tarjeta registrada
                    String ccv = ccvTextField.getText().toString();
                    Calendar vencimiento = Calendar.getInstance();
                    int numeroMesSeleccionado = transformarMes(mesSeleccionado);
                    vencimiento.set(Calendar.YEAR, Integer.parseInt(anoSeleccionado)); //no deberia hacer dos veces lo mismo
                    vencimiento.set(Calendar.MONTH, numeroMesSeleccionado);
                    Date fechaVencimiento = vencimiento.getTime();
                    boolean esCredito = credito.isChecked();
                    Tarjeta tarjeta = new Tarjeta(numeroTarjeta, ccv, fechaVencimiento, esCredito);
                    //creo la cuenta bancaria registrada
                    String cbu = cbuTextField.getText().toString();
                    String aliasCbu = inputAliasCbu.getText().toString();
                    CuentaBancaria cuenta = new CuentaBancaria(cbu, aliasCbu);
                    //creo al usuario registrado
                    String nombre = nameTextField.getEditText().getText().toString();
                    String clave = passwordTextField.getText().toString();
                    String mail = emailTextField.getText().toString();
                    Double credito = Double.valueOf(monto.getText().toString());
                    Usuario user = new Usuario(idTarjetas+1, nombre, clave, mail, credito, tarjeta, cuenta);
                    Toast aviso = Toast.makeText(MainActivity.this, "Registro exitoso", Toast.LENGTH_LONG); //aviso al usuario
                    aviso.show();
                }
            }
        });
    }
//
    public boolean cumpleRequisitos() {
        boolean check = true;

        if(!camposObligatoriosCompletos()) {
            check = false;
        }
        if(!emailTextField.getText().toString().isEmpty() && !ingresoEmailValido()) { //primero m fijo si ya no tiene la etiqueta de
            check = false; //campo obligatorio, si se cumple la primera condicion recien dp ve si el email es valido o no
        }
        if(!passwordRepeatedTextField.getText().toString().isEmpty() && !contrasenasCoinciden()){ //primero me fijo si no tiene la etiqueta
            check = false; //de campo obligatorio, valido antes si el campo no es nulo
        }
        if(!montoValido()) { //comprueba que si el switch esta activado, el monto no sea nulo
            check = false;
        }
        if(mesSeleccionado.equals(getString(R.string.mes))) {
            ((TextView) spinnerMes.getSelectedView()).setError("Error message");
            errorFechaVencimiento.setVisibility(View.VISIBLE);
            check = false;
        }
        if(anoSeleccionado.equals(getString(R.string.ano))) {
            ((TextView) spinnerAno.getSelectedView()).setError("Error message");
            errorFechaVencimiento.setVisibility(View.VISIBLE);
            check = false;
        }
        if(!mesSeleccionado.equals(getString(R.string.mes)) && !anoSeleccionado.equals(getString(R.string.ano)) && !vencimientoValido(anoSeleccionado,mesSeleccionado)) {
            ((TextView) spinnerMes.getSelectedView()).setError("Error message");
            ((TextView) spinnerAno.getSelectedView()).setError("Error message");
            errorFechaVencimiento.setVisibility(View.VISIBLE);
            check = false;
        }
        return check;
    }

    public boolean vencimientoValido(String ano, String mes) {
        boolean check = true;

        Calendar vencimiento = Calendar.getInstance();
        vencimiento.set(Calendar.YEAR, Integer.parseInt(ano)); //asigna el anio ingresado al calendar
        vencimiento.set(Calendar.MONTH, transformarMes(mes)); //asigna el mes ingresado al calendar

        Calendar hoy = Calendar.getInstance(); //obtiene fecha actual
        hoy.add(Calendar.MONTH, 3); //le suma tres meses

        if(!vencimiento.after(hoy)) { //analiza si el vencimiento ingresado es por lo menos tres meses despues de la fecha actual
            check = false;
        }
        return check;
    }

    public boolean camposObligatoriosCompletos() {
        boolean check = true;
        //email
        String errorEmail = null;
        if (TextUtils.isEmpty(emailTextField.getText())) {
            errorEmail = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutEmail, errorEmail);
        //contrasena
        String errorContrasena = null;
        if (TextUtils.isEmpty(passwordTextField.getText())) {
            errorContrasena = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutContrasena, errorContrasena);
        //contrasenaRepetida
        String errorContrasenaRepetida = null;
        if (TextUtils.isEmpty(passwordRepeatedTextField.getText())) {
            errorContrasenaRepetida = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutContrasenaRepetida, errorContrasenaRepetida);
        //numeroTarjeta
        String errorNumeroTarjeta = null;
        if (TextUtils.isEmpty(cardNumberTextField.getText())) {
            errorNumeroTarjeta = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutNumeroTarjeta, errorNumeroTarjeta);
        //ccv
        String errorCcv = null;
        if (TextUtils.isEmpty(ccvTextField.getText())) {
            errorCcv = getString(R.string.campoObligatorioCorto);
            check = false;
        }
        toggleTextInputLayoutError(layoutCcv, errorCcv);

        return check;
    }
//
//
//    public boolean contrasenasCoinciden() {
//        contrasena = passwordTextField.getText().toString(); //obtengo el string de inputContrasena
//        contrasenaRepetida = passwordRepeatedTextField.getText().toString(); //obtengo el string de inputContrasenaRepetida
//        String errorContrasena = null; //verifica que las contrasenias coincida
//        boolean check = true;
//        if (!contrasena.isEmpty() && !contrasenaRepetida.isEmpty() && !contrasena.equals(contrasenaRepetida)) {
//            errorContrasena = getString(R.string.errorContrasena);
//            check = false;
//        }
//        toggleTextInputLayoutError(layoutContrasenaRepetida, errorContrasena);
//        return check;
//    }
//
//    public boolean montoValido() {
//        boolean check = true;
//        if(realizarCarga.isChecked() && monto.getText().toString().equals("0")){ //verifica que si el switch esta activo, hay monto mayor a 0
//            errorCargaInicial.setVisibility(View.VISIBLE); //se muestra el error
//            check = false;
//        }
//        else {
//            errorCargaInicial.setVisibility(View.GONE);
//        }
//        return check;
//    }
//
    public int transformarMes(String mesSeleccionado){
        switch (mesSeleccionado){
            case "Enero": return 0;
            case "Febrero": return 1;
            case "Marzo": return 2;
            case "Abril": return 3;
            case "Mayo": return 4;
            case "Junio": return 5;
            case "Julio": return 6;
            case "Agosto": return 7;
            case "Septiembre": return 8;
            case "Octubre": return 9;
            case "Noviembre": return 10;
            case "Diciembre": return 11;
        }
        return -1;
    }

    }

    public void mandatoryFieldValidation(final TextInputLayout field){
        Objects.requireNonNull(field.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

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

    public boolean emailValidation() {
        Pattern emailPattern = Pattern.compile(getString(R.string.entidadRegularEmail)); //indica como deberia ser el email
        email = Objects.requireNonNull(emailTextField.getEditText()).getText().toString();
        Matcher matcherEmail = emailPattern.matcher(email); //lo va a comparar con lo ingresado en email

        //analiza si el email ingresado tiene un arroba y tres letras detras
        return matcherEmail.find();
    }

    public boolean passwordMatchValidation() {
//        String password = passwordTextField.getText().toString(); //obtengo el string de inputContrasena
//        String repeatedPassword = passwordRepeatedTextField.getText().toString(); //obtengo el string de inputContrasenaRepetida
//        String errorContrasena = null; //verifica que las contrasenias coincida
//        boolean check = true;
        passwordMatch = (!password.isEmpty() && !repeatedPassword.isEmpty() && password.equals(repeatedPassword));
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