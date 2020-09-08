package com.dam.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.sendmeal.model.CuentaBancaria;
import com.dam.sendmeal.model.Plato;
import com.dam.sendmeal.model.Tarjeta;
import com.dam.sendmeal.model.Usuario;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class NuevoPlatoActivity extends AppCompatActivity {

    Toolbar toolbarNuevoPlato;
    EditText inputId;
    EditText inputTitulo;
    EditText inputDescripcion;
    EditText inputPrecio;
    EditText inputCalorias;
    TextInputLayout layoutInputId;
    TextInputLayout layoutInputTitulo;
    TextInputLayout layoutInputDescripcion;
    TextInputLayout layoutInputPrecio;
    TextInputLayout layoutInputCalorias;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_plato);

        inputId = findViewById(R.id.idPlato);
        inputTitulo = findViewById(R.id.titulo);
        inputDescripcion = findViewById(R.id.descripcion);
        inputPrecio = findViewById(R.id.precio);
        inputCalorias = findViewById(R.id.calorias);
        layoutInputId = findViewById(R.id.layoutIdPlato);
        layoutInputTitulo = findViewById(R.id.layoutTitulo);
        layoutInputDescripcion = findViewById(R.id.layoutDescripcion);
        layoutInputPrecio = findViewById(R.id.layoutPrecio);
        layoutInputCalorias = findViewById(R.id.layoutCalorias);

        guardar = findViewById(R.id.guardarPlato);

        toolbarNuevoPlato = findViewById(R.id.toolbarNuevoPlato);
        setSupportActionBar(toolbarNuevoPlato);

        inputId.addTextChangedListener(new TextWatcher() { //listener id
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String errorId = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(inputId.getText())) {
                    errorId = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutInputId, errorId);
            }
        });

        inputTitulo.addTextChangedListener(new TextWatcher() { //listener titulo
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String errorTitulo = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(inputTitulo.getText())) {
                    errorTitulo = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutInputTitulo, errorTitulo);
            }
        });

        inputDescripcion.addTextChangedListener(new TextWatcher() { //listener descripcion
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String errorDescripcion = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(inputDescripcion.getText())) {
                    errorDescripcion = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutInputDescripcion, errorDescripcion);
            }
        });

        inputPrecio.addTextChangedListener(new TextWatcher() { //listener precio
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String errorPrecio = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(inputPrecio.getText())) {
                    errorPrecio = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutInputPrecio, errorPrecio);
            }
        });

        inputCalorias.addTextChangedListener(new TextWatcher() { //listener calorias
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String errorCalorias = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(inputCalorias.getText())) {
                    errorCalorias = getString(R.string.campoObligatorio);
                }
                toggleTextInputLayoutError(layoutInputCalorias, errorCalorias);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cumpleRequisitos()) {
                    //creo la tarjeta registrada
                    Integer id = Integer.valueOf(inputId.getText().toString());
                    String titulo = inputTitulo.getText().toString();
                    String descripcion = inputDescripcion.getText().toString();
                    Double precio = Double.valueOf(inputPrecio.getText().toString());
                    Integer calorias = Integer.valueOf(inputCalorias.getText().toString());

                    Plato plato = new Plato(id, titulo, descripcion, precio, calorias);

                    Toast aviso = Toast.makeText(NuevoPlatoActivity.this, "Nuevo plato creado", Toast.LENGTH_LONG); //aviso al usuario
                    aviso.show();
                }
            }
        });
    }

    public boolean cumpleRequisitos() {
        boolean check = true;

        if(!camposObligatoriosCompletos()) {
            check = false;
        }
        /*if(!inputEmail.getText().toString().isEmpty() && !ingresoEmailValido()) { //primero m fijo si ya no tiene la etiqueta de
            check = false; //campo obligatorio, si se cumple la primera condicion recien dp ve si el email es valido o no
        }
        if(!inputcontrasenaRepetida.getText().toString().isEmpty() && !contrasenasCoinciden()){ //primero me fijo si no tiene la etiqueta
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
        }*/
        return check;
    }

    public boolean camposObligatoriosCompletos() {
        boolean check = true;
        //id
        String errorId = null;
        if (TextUtils.isEmpty(inputId.getText())) {
            errorId = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutInputId, errorId);
        //titulo
        String errorTitulo = null;
        if (TextUtils.isEmpty(inputTitulo.getText())) {
            errorTitulo = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutInputTitulo, errorTitulo);
        //descripcion
        String errorDescripcion = null;
        if (TextUtils.isEmpty(inputDescripcion.getText())) {
            errorDescripcion = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutInputDescripcion, errorDescripcion);
        //precio
        String errorPrecio = null;
        if (TextUtils.isEmpty(inputPrecio.getText())) {
            errorPrecio = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutInputPrecio, errorPrecio);
        //calorias
        String errorCalorias = null;
        if (TextUtils.isEmpty(inputCalorias.getText())) {
            errorCalorias = getString(R.string.campoObligatorio);
            check = false;
        }
        toggleTextInputLayoutError(layoutInputCalorias, errorCalorias);

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