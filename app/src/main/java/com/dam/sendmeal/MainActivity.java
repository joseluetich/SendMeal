package com.dam.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.sendmeal.model.CuentaBancaria;
import com.dam.sendmeal.model.Tarjeta;
import com.dam.sendmeal.model.Usuario;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText inputNombre;
    EditText inputContrasena;
    EditText inputcontrasenaRepetida;
    String contrasena, contrasenaRepetida; //guarda el contenido de inputContrasena e inputcontrasenaRepetida
    EditText inputNumeroTarjeta;
    String numeroTarjeta; //guarda el contenido de inputNumeroTarjeta
    String email;
    EditText inputCcv;
    EditText inputMes;
    EditText inputAno;
    RadioGroup tarjetas;
    RadioButton credito;
    RadioButton debito;
    SeekBar montoCredito;
    TextView monto;
    Switch realizarCarga;
    TextView creditoInicial;
    CheckBox terminos;
    Button registrar;
    EditText inputEmail;
    EditText inputCbu;
    EditText inputAliasCbu;
    TextInputLayout layoutEmail;
    TextInputLayout layoutContrasena;
    TextInputLayout layoutContrasenaRepetida;
    TextInputLayout layoutNumeroTarjeta;
    TextInputLayout layoutCcv;
    TextInputLayout layoutMes;
    TextInputLayout layoutAno;

    int idTarjetas=0; //falta asignar un id para guardar en la clase tarjeta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtengo las entidades por id
        inputNombre = findViewById(R.id.nombre);
        inputContrasena = findViewById(R.id.contrasena);
        inputcontrasenaRepetida = findViewById(R.id.contrasenaRepetida);
        //errorContrasena = findViewById(R.id.errorContrasena);
        inputNumeroTarjeta = findViewById(R.id.numeroTarjeta);
        inputCcv = findViewById(R.id.ccv);
        inputMes = findViewById(R.id.mes);
        inputAno = findViewById(R.id.ano);
        tarjetas = findViewById(R.id.tarjetas);
        credito = findViewById(R.id.credito);
        debito = findViewById(R.id.debito);
        montoCredito = findViewById(R.id.montoCredito);
        monto = findViewById(R.id.monto);
        realizarCarga = findViewById(R.id.realizarCarga);
        creditoInicial = findViewById(R.id.creditoInicial);
        terminos = findViewById(R.id.terminos);
        registrar = findViewById(R.id.registrar);
        inputEmail = findViewById(R.id.email);
        inputCbu = findViewById(R.id.cbu);
        inputAliasCbu = findViewById(R.id.aliasCbu);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutContrasena = findViewById(R.id.layoutContrasena);
        layoutContrasenaRepetida = findViewById(R.id.layoutContrasenaRepetida);
        layoutNumeroTarjeta = findViewById(R.id.layoutNumeroTarjeta);
        layoutCcv = findViewById(R.id.layoutCcv);
        layoutMes = findViewById(R.id.layoutMes);
        layoutAno = findViewById(R.id.layoutAno);

        contrasena=""; //para que no ocurra errror en el .isEmpty() de la linea 101
        contrasenaRepetida="";

        inputEmail.addTextChangedListener(new TextWatcher() { //si no ingreso el email la primera vez, reconoce que se agrega texto para sacar el error
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String errorEmail = null;
                if (TextUtils.isEmpty(inputEmail.getText())) {
                    errorEmail = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutEmail, errorEmail);
            }
        });

        inputContrasena.addTextChangedListener(new TextWatcher() { //listener de la contrasena
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) { //por si lo ultimo en modificarse fue contrasena
                String campoObligatorio = null; //verifica si todavia no se escribio nada
                if (TextUtils.isEmpty(inputContrasena.getText())) { //si ya se escribio dejara de mostrar el error
                    campoObligatorio = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutContrasena, campoObligatorio);
            }
        });

        inputcontrasenaRepetida.addTextChangedListener(new TextWatcher() { //listener de repetir contrasena
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) { //por si lo ultimo en modificarse fue repetir contrasena
                String campoObligatorio = null; //se verifica que se hayan completado caracteres para sacar el error
                if (TextUtils.isEmpty(inputcontrasenaRepetida.getText())) {
                    campoObligatorio = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutContrasenaRepetida, campoObligatorio);
            }
        });

        tarjetas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) { //listener radiogroup tipo de tarjeta
                if(id == R.id.credito) { //si esta marcado credito
                    credito.setChecked(true); //queda marcado credito
                    debito.setChecked(false); //y desmarcado debito
                } //de esta manera me aseguro que una opcion se elija si o si
                else { //si no
                    credito.setChecked(false); //queda desmarcado credito
                    debito.setChecked(true); //y marcado debito
                }
            }
        });

        inputNumeroTarjeta.addTextChangedListener(new TextWatcher() { //listener numero de tarjeta
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                numeroTarjeta = inputNumeroTarjeta.getText().toString(); //almacena el string del campo numero de tarjeta
                if(!numeroTarjeta.isEmpty()) { //si el campo no esta vacio
                    inputCcv.setEnabled(true); //se habilita el ccv el mes y el anio
                    inputAno.setEnabled(true);
                    inputMes.setEnabled(true);
                }
                else { //si el campo esta vacio
                    inputCcv.setEnabled(false); //permanecen deshabilitados el ccv el mes y el anio
                    inputAno.setEnabled(false);
                    inputMes.setEnabled(false);
                }

                String errorNumeroTarjeta = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(inputNumeroTarjeta.getText())) {
                    errorNumeroTarjeta = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutNumeroTarjeta, errorNumeroTarjeta);
            }
        });

        inputCcv.addTextChangedListener(new TextWatcher() { //listener Ccv
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String errorCcv = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(inputCcv.getText())) {
                    errorCcv = getString(R.string.campoObligatorioCorto);
                }
                toggleTextInputLayoutError(layoutCcv, errorCcv);
            }
        });

        inputMes.addTextChangedListener(new TextWatcher() { //listener Ccv
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String errorMes = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(inputMes.getText())) {
                    errorMes = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutMes, errorMes);
            }
        });

        inputAno.addTextChangedListener(new TextWatcher() { //listener Ccv
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String errorAno = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(inputAno.getText())) {
                    errorAno = getString(R.string.campoObligatorioCorto);
                }
                toggleTextInputLayoutError(layoutAno, errorAno);
            }
        });

        montoCredito.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //listener de la seekbar
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                monto.setText(String.valueOf(i)); //le asigno al texto de encima de la barra el valor que marca la barra
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        realizarCarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) { //listener del switch
                if(b) { //si esta activado
                    monto.setVisibility(View.VISIBLE); //muestro el texto del monto
                    montoCredito.setVisibility(View.VISIBLE); //y muestro la seekbar
                }
                else { //si esta desactivado
                    monto.setVisibility(View.GONE); //desaparece el texto del monto
                    montoCredito.setVisibility(View.GONE); //y desapa
                    // rece la seekbar
                }
            }
        });

        terminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) { //listener del checkbox terminos
                if(terminos.isChecked()) { //si esta marcado
                    registrar.setEnabled(true); //puedo habilitar el boton
                }
                else
                    registrar.setEnabled(false);
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() { //listener del boton registrar
            @Override
            public void onClick(View view) {
                if(cumpleRequisitos()) { //si cumple con todos los requisitos
                    Toast aviso = Toast.makeText(MainActivity.this, "Registro exitoso", Toast.LENGTH_LONG); //aviso al usuario
                    aviso.show();
                    //creo la tarjeta registrada
                    String ccv = inputCcv.getText().toString();
                    Calendar vencimiento = Calendar.getInstance();
                    vencimiento.set(Calendar.YEAR, Integer.parseInt(inputAno.getText().toString())); //no deberia hacer dos veces lo mismo
                    vencimiento.set(Calendar.MONTH, Integer.parseInt(inputMes.getText().toString()));
                    Date fechaVencimiento = vencimiento.getTime();
                    Boolean esCredito = credito.isChecked();
                    Tarjeta tarjeta = new Tarjeta(numeroTarjeta, ccv, fechaVencimiento, esCredito);
                    //creo la cuenta bancaria registrada
                    String cbu = inputCbu.getText().toString();
                    String aliasCbu = inputAliasCbu.getText().toString();
                    CuentaBancaria cuenta = new CuentaBancaria(cbu, aliasCbu);
                    //creo al usuario registrado
                    String nombre = inputNombre.getText().toString();
                    String clave = inputContrasena.getText().toString();
                    String mail = inputEmail.getText().toString();
                    Double credito = Double.valueOf(monto.getText().toString());
                    Usuario user = new Usuario(idTarjetas+1, nombre, clave, mail, credito, tarjeta, cuenta);
                }
            }
        });
    }

    public boolean cumpleRequisitos() {
        boolean check = true;
        String ano = inputAno.getText().toString();
        String mes = inputMes.getText().toString();
        if(!camposObligatoriosCompletos()) {
            check = false;
        }
        if(!inputEmail.getText().toString().isEmpty() && !ingresoEmailValido()) { //primero m fijo si ya no tiene la etiqueta de
            check = false; //campo obligatorio, si se cumple la primera condicion recien dp ve si el email es valido o no
        }
        if(!inputcontrasenaRepetida.getText().toString().isEmpty() && !contrasenasCoinciden()){ //primero me fijo si no tiene la etiqueta
            check = false; //de campo obligatorio, valido antes si el campo no es nulo
        }
        //FALTA MODIFICAR ESOS ERRORES
        else if(realizarCarga.isChecked() && monto.getText().toString().equals("0")){ //verifica que si el switch esta activo, hay monto mayor a 0
            Toast aviso = Toast.makeText(MainActivity.this,"Monto invalido",Toast.LENGTH_LONG);
            aviso.show();
            check = false;
        }
        else if(!ano.isEmpty() && !mes.isEmpty() && !vencimientoValido(ano, mes)) { //analiza si el vencimiento ingresado es por lo menos tres meses despues de la fecha actual
            Toast aviso = Toast.makeText(MainActivity.this,"Vencimiento invalido",Toast.LENGTH_LONG);
            aviso.show();
            check = false;
        }
        return check;
    }

    public boolean vencimientoValido(String ano, String mes) {
        final Calendar vencimiento = Calendar.getInstance();
        vencimiento.set(Calendar.YEAR, Integer.parseInt(ano)); //asigna el anio ingresado al calendar
        vencimiento.set(Calendar.MONTH, Integer.parseInt(mes)); //asigna el mes ingresado al calendar

        Calendar hoy = Calendar.getInstance(); //obtiene fecha actual
        hoy.add(Calendar.MONTH, 3); //le suma tres meses

        //compara si el vencimiento es despues de tres meses a partir de hoy
        return vencimiento.after(hoy);
    }

    public boolean camposObligatoriosCompletos() {
        boolean check = true;
        //email
        String errorEmail = null;
        if (TextUtils.isEmpty(inputEmail.getText())) {
            errorEmail = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutEmail, errorEmail);
        //contrasena
        String errorContrasena = null;
        if (TextUtils.isEmpty(inputContrasena.getText())) {
            errorContrasena = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutContrasena, errorContrasena);
        //contrasenaRepetida
        String errorContrasenaRepetida = null;
        if (TextUtils.isEmpty(inputcontrasenaRepetida.getText())) {
            errorContrasenaRepetida = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutContrasenaRepetida, errorContrasenaRepetida);
        //numeroTarjeta
        String errorNumeroTarjeta = null;
        if (TextUtils.isEmpty(inputNumeroTarjeta.getText())) {
            errorNumeroTarjeta = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutNumeroTarjeta, errorNumeroTarjeta);
        //ccv
        String errorCcv = null;
        if (TextUtils.isEmpty(inputCcv.getText())) {
            errorCcv = getString(R.string.campoObligatorioCorto);
            check = false;
        }
        toggleTextInputLayoutError(layoutCcv, errorCcv);
        //mes
        String errorMes = null;
        if (TextUtils.isEmpty(inputMes.getText())) {
            errorMes = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutMes, errorMes);
        //anio
        String errorAno = null;
        if (TextUtils.isEmpty(inputAno.getText())) {
            errorAno = getString(R.string.campoObligatorioCorto);
            check = false;
        }
        toggleTextInputLayoutError(layoutAno, errorAno);
        return check;
    }

    public boolean ingresoEmailValido() {
        Pattern emailPattern = Pattern.compile(getString(R.string.entidadRegularEmail)); //indica como deberia ser el email
        email = inputEmail.getText().toString();
        Matcher matcherEmail = emailPattern.matcher(email); //lo va a comparar con lo ingresado en email
        String errorPatronEmail = null;
        boolean check = true;
        if(!email.isEmpty() && !matcherEmail.find()) { //analiza si el email ingresado tiene un arroba y tres letras detras
            errorPatronEmail = getString(R.string.emailInvalido);
            check = false;
        }
        toggleTextInputLayoutError(layoutEmail, errorPatronEmail);
        return check;
    }

    public boolean contrasenasCoinciden() {
        contrasena = inputContrasena.getText().toString(); //obtengo el string de inputContrasena
        contrasenaRepetida = inputcontrasenaRepetida.getText().toString(); //obtengo el string de inputContrasenaRepetida
        String errorContrasena = null; //verifica que las contrasenias coincida
        boolean check = true;
        if (!contrasena.isEmpty() && !contrasenaRepetida.isEmpty() && !contrasena.equals(contrasenaRepetida)) {
            errorContrasena = getString(R.string.errorContrasena);
            check = false;
        }
        toggleTextInputLayoutError(layoutContrasenaRepetida, errorContrasena);
        return check;
    }

    private static void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout, String msg) {
        textInputLayout.setError(msg);
        if (msg == null) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setErrorEnabled(true);
        }
    }
}