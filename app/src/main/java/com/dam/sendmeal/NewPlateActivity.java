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
import android.widget.Toast;

import com.dam.sendmeal.model.Plato;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPlateActivity extends AppCompatActivity {

    Toolbar newPlateToolbar;
    EditText titleEditText, descriptionEditText, priceEditText, caloriesEditText;
    TextInputLayout titleTextInputLayout, descriptionTextInputLayout, priceTextInputLayout, caloriesTextInputLayout;
    Button savePlateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plate);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        caloriesEditText = findViewById(R.id.caloriesEditText);
        titleTextInputLayout = findViewById(R.id.titleTextInputLayout);
        descriptionTextInputLayout = findViewById(R.id.descriptionTextInputLayout);
        priceTextInputLayout = findViewById(R.id.priceTextInputLayout);
        caloriesTextInputLayout = findViewById(R.id.caloriesTextInputLayout);
        savePlateButton = findViewById(R.id.savePlateButton);

        newPlateToolbar = findViewById(R.id.newPlateToolbar);
        setSupportActionBar(newPlateToolbar);

        titleEditText.addTextChangedListener(new TextWatcher() { //listener titulo
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String titleError = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(titleEditText.getText())) {
                    titleError = getString(R.string.errorObligatoryField);
                }
                toggleTextInputLayoutError(titleTextInputLayout, titleError);
            }
        });

        /*inputDescripcion.addTextChangedListener(new TextWatcher() { //listener descripcion
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
        });*/

        priceEditText.addTextChangedListener(new TextWatcher() { //listener precio
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String priceError = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(priceEditText.getText())) {
                    priceError = getString(R.string.errorObligatoryField);
                }
                toggleTextInputLayoutError(priceTextInputLayout, priceError);
            }
        });

        caloriesEditText.addTextChangedListener(new TextWatcher() { //listener calorias
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                String caloriesError = null; //verifica que el campo obligatorio sea completado
                if (TextUtils.isEmpty(caloriesEditText.getText())) {
                    caloriesError = getString(R.string.errorObligatoryField);
                }
                toggleTextInputLayoutError(caloriesTextInputLayout, caloriesError);
            }
        });

        savePlateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(satisfyRequeriments()) {
                    //creo la tarjeta registrada
                    String title = titleEditText.getText().toString();
                    String description = descriptionEditText.getText().toString();
                    double price = Double.parseDouble(priceEditText.getText().toString());
                    int calories = Integer.parseInt(caloriesEditText.getText().toString());

                    Plato plato = new Plato(title, description, price, calories);
                    plato.addToPlates();

                    Toast aviso2 = Toast.makeText(NewPlateActivity.this, "Nuevo plato creado.", Toast.LENGTH_LONG); //aviso al usuario
                    aviso2.show();
                }
            }
        });
    }

    public boolean satisfyRequeriments() {
        boolean check = true;

        if(!requiredFieldsComplete()) {
            check = false;
        }
        if(!priceEditText.getText().toString().isEmpty() && !enteredValidPrice()) { //primero m fijo si ya no tiene la etiqueta de
            check = false; //campo obligatorio, si se cumple la primera condicion recien dp ve si el precio es valido o no
        }
        return check;
    }

    public boolean requiredFieldsComplete() {
        boolean check = true;
        //titulo
        String titleError = null;
        if (TextUtils.isEmpty(titleEditText.getText())) {
            titleError = getString(R.string.errorObligatoryField);
            check = false;
        }
        toggleTextInputLayoutError(titleTextInputLayout, titleError);
        //descripcion
        /*String descriptionError = null;
        if (TextUtils.isEmpty(descriptionEditText.getText())) {
            descriptionError = getString(R.string.errorObligatoryField);
            check = false;
        }
        toggleTextInputLayoutError(descriptionTextInputLayout, descriptionError);*/
        //precio
        String priceError = null;
        if (TextUtils.isEmpty(priceEditText.getText())) {
            priceError = getString(R.string.errorObligatoryField);
            check = false;
        }
        toggleTextInputLayoutError(priceTextInputLayout, priceError);
        //calorias
        String caloriesError = null;
        if (TextUtils.isEmpty(caloriesEditText.getText())) {
            caloriesError = getString(R.string.errorObligatoryField);
            check = false;
        }
        toggleTextInputLayoutError(caloriesTextInputLayout, caloriesError);

        return check;
    }

    public boolean enteredValidPrice() {
        Pattern pricePattern = Pattern.compile(getString(R.string.entidadRegularPrecio)); //indica como deberia ser el precio
        String price = priceEditText.getText().toString();
        Matcher priceMatcher = pricePattern.matcher(price); //lo va a comparar con lo ingresado en precio
        String pricePatternError = null;
        boolean check = true;
        if(!price.isEmpty() && !priceMatcher.find()) { //analiza si el precio ingresado tiene hasta dos decimales
            pricePatternError = getString(R.string.precioInvalido);
            check = false;
        }
        toggleTextInputLayoutError(priceTextInputLayout, pricePatternError);
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