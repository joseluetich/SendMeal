package com.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText inputContrasena;
    EditText inputContrasena2;
    TextView errorContrasena;
    String contrasena, contrasena2;
    EditText inputNumeroTarjeta;
    String numeroTarjeta;
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
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputContrasena = findViewById(R.id.contrasena);
        inputContrasena2 = findViewById(R.id.contrasena2);
        errorContrasena = findViewById(R.id.errorContrasena);
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
        email = findViewById(R.id.email);

        contrasena="";
        contrasena2="";

        inputContrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                contrasena = inputContrasena.getText().toString();
            }
        });

        inputContrasena2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                contrasena2 = inputContrasena2.getText().toString();

                if(!contrasena.isEmpty() && contrasena.equals(contrasena2)){
                    errorContrasena.setVisibility(View.INVISIBLE);
                    if(terminos.isChecked()) {
                        registrar.setEnabled(true);
                        registrar.setText("SEhabilita");
                    }
                }
                else if(contrasena.isEmpty() || contrasena2.isEmpty()){
                    errorContrasena.setVisibility(View.INVISIBLE);
                }
                else {
                    errorContrasena.setVisibility(View.VISIBLE);
                    errorContrasena.setText(R.string.errorContrasena);
                }
            }
        });

        inputNumeroTarjeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                numeroTarjeta = inputNumeroTarjeta.getText().toString();

                if(!numeroTarjeta.isEmpty()){
                    inputCcv.setEnabled(true);
                    inputAno.setEnabled(true);
                    inputMes.setEnabled(true);
                }
                else {
                    inputCcv.setEnabled(false);
                    inputAno.setEnabled(false);
                    inputMes.setEnabled(false);
                }
            }
        });

        tarjetas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if(id == R.id.credito) {
                    credito.setChecked(true);
                    debito.setChecked(false);
                }
                else {
                    credito.setChecked(false);
                    debito.setChecked(true);
                }
            }
        });

        montoCredito.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                monto.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        realizarCarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    monto.setVisibility(View.VISIBLE);
                    montoCredito.setVisibility(View.VISIBLE);
                }
                else {
                    monto.setVisibility(View.GONE);
                    montoCredito.setVisibility(View.GONE);
                }
            }
        });

        terminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(terminos.isChecked()) {
                    registrar.setEnabled(true);
                }
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cumpleRequisitos()) {
                    Toast aviso = Toast.makeText(MainActivity.this, "Registro exitoso", Toast.LENGTH_LONG);
                    aviso.show();
                }
            }
        });
    }
    public boolean cumpleRequisitos() {
        Pattern emailPattern = Pattern.compile("@[a-z][a-z][a-z]");
        Matcher matcherEmail = emailPattern.matcher(email.getText().toString());

        if(email.getText().toString().isEmpty() || contrasena.isEmpty() || contrasena2.isEmpty() || inputNumeroTarjeta.getText().toString().isEmpty()
                || inputCcv.getText().toString().isEmpty() || inputMes.getText().toString().isEmpty() || inputAno.getText().toString().isEmpty()) {
            Toast aviso = Toast.makeText(MainActivity.this,"Campos obligatorios incompletos",Toast.LENGTH_LONG);
            aviso.show();
            return false;
        }
        else if(realizarCarga.isChecked() && monto.getText().toString().equals("0")){
            Toast aviso = Toast.makeText(MainActivity.this,"Monto invalido",Toast.LENGTH_LONG);
            aviso.show();
            return false;
        }
        else if(!matcherEmail.find()) {
            Toast aviso = Toast.makeText(MainActivity.this,"Email invalido",Toast.LENGTH_LONG);
            aviso.show();
            return false;
        }
        //else if()

        return true;
    }

}