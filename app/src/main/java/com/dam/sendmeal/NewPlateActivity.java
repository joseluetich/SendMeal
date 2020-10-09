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

import com.dam.sendmeal.model.Plato;
import com.google.android.material.textfield.TextInputLayout;

import java.nio.DoubleBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPlateActivity extends AppCompatActivity {

    Toolbar newPlateToolbar;
    TextInputLayout titleTextField, descriptionTextField, priceTextField, caloriesTextField;
    Button savePlateButton;
    Plato plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plate);

        titleTextField = findViewById(R.id.titleTextInputLayout);
        descriptionTextField = findViewById(R.id.descriptionTextInputLayout);
        priceTextField = findViewById(R.id.priceTextInputLayout);
        caloriesTextField = findViewById(R.id.caloriesTextInputLayout);
        savePlateButton = findViewById(R.id.savePlateButton);

        plate = new Plato();

        newPlateToolbar = findViewById(R.id.newPlateToolbar);
        setSupportActionBar(newPlateToolbar);

        mandatoryFieldValidation(titleTextField);
        mandatoryFieldValidation(priceTextField);
        mandatoryFieldValidation(caloriesTextField);

        Objects.requireNonNull(titleTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String title = titleTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(title)) {
                        plate.setTitle(title);
                    } else {
                        plate.setTitle(null);
                    }
                }

            }
        });

        Objects.requireNonNull(descriptionTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String description = descriptionTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(description)) {
                        plate.setDescription(description);
                    } else {
                        plate.setDescription(null);
                    }
                }
            }
        });

        Objects.requireNonNull(priceTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String price = priceTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(price)) {
                        if(!enteredValidPrice()) {
                            priceTextField.setHelperTextEnabled(false);
                            priceTextField.setError(getString(R.string.precioInvalido));
                        }
                        else {
                            priceTextField.setHelperTextEnabled(true);
                            priceTextField.setError(null);
                        }
                        Double doublePrice = Double.valueOf(priceTextField.getEditText().getText().toString());
                        plate.setPrice(doublePrice);
                    } else {
                        plate.setPrice(null);
                    }
                }
            }
        });

        Objects.requireNonNull(caloriesTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String calories = caloriesTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(calories)) {
                        plate.setCalories(Integer.valueOf(calories));
                    } else {
                        plate.setCalories(null);
                    }
                }
            }
        });

        savePlateButton.setOnClickListener(new View.OnClickListener() { //listener del boton registrar
            @Override
            public void onClick(View view) {
                if (validateAllMandatoryFields() && !plate.getPrice().toString().isEmpty() && enteredValidPrice()) {
                    plate.addToPlates();
                    Toast aviso = Toast.makeText(NewPlateActivity.this, "Plato creado correctamente", Toast.LENGTH_LONG); //aviso al usuario
                    aviso.show();
                } else {
                    //Toast aviso = Toast.makeText(NewPlateActivity.this, "error", Toast.LENGTH_LONG); //aviso al usuario
                    //aviso.show();
                }

            }
        });
    }

    public boolean validateAllMandatoryFields() {
        boolean allCompleted = true;

        if (plate.getTitle() == null || plate.getTitle().isEmpty()) {
            titleTextField.setHelperTextEnabled(false);
            titleTextField.setError(getString(R.string.errorObligatoryField));
            allCompleted = false;
        }

        if (plate.getPrice() == null) {
            priceTextField.setHelperTextEnabled(false);
            priceTextField.setError(getString(R.string.errorObligatoryField));
            allCompleted = false;
        }

        if (plate.getCalories() == null || plate.getCalories().toString().isEmpty()) {
            caloriesTextField.setHelperTextEnabled(false);
            caloriesTextField.setError(getString(R.string.errorObligatoryField));
            allCompleted = false;
        }

        return allCompleted;
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

    public boolean enteredValidPrice() {
        Pattern pricePattern = Pattern.compile(getString(R.string.entidadRegularPrecio)); //indica como deberia ser el precio
        String price = priceTextField.getEditText().getText().toString();
        Matcher priceMatcher = pricePattern.matcher(price); //lo va a comparar con lo ingresado en precio
        String pricePatternError = null;
        boolean check = true;
        if(!price.isEmpty() && !priceMatcher.find()) { //analiza si el precio ingresado tiene hasta dos decimales
            Toast aviso2 = Toast.makeText(NewPlateActivity.this, price, Toast.LENGTH_SHORT); //aviso al usuario
            aviso2.show();
            priceTextField.setHelperTextEnabled(false);
            pricePatternError = getString(R.string.precioInvalido);
            check = false;
        }
        else {
            priceTextField.setHelperTextEnabled(true);
        }
        priceTextField.setError(pricePatternError);
        return check;
    }
}