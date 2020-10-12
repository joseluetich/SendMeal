package com.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dam.sendmeal.model.Address;
import com.dam.sendmeal.model.Order;
import com.dam.sendmeal.model.Plate;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewOrderActivity extends AppCompatActivity {

    Toolbar newOrderToolbar;
    TextInputLayout emailOrderTextField, streetTextField, numberTextField;
    RadioGroup deliverRadioGroup;
    RadioButton shippingRadioButton, takeAwayRadioButton;
    Address address;
    Button addPlateButton, confirmOrderButton;
    Order order;
    ArrayList<Plate> orderPlates = new ArrayList<>();
    RecyclerView orderPlatesListRecyclerView;
    RecyclerView.Adapter orderPlatesListAdapter;
    RecyclerView.LayoutManager orderPlatesListLayoutManager;
    TextView orderPriceTextView, platesQuantityTextView;//, orderPriceDescriptionTextView, orderDescriptionTextView;
    CardView orderDescriptionCardView;
    Double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        newOrderToolbar = findViewById(R.id.newOrderToolbar);
        setSupportActionBar(newOrderToolbar);//configuro la toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        emailOrderTextField = findViewById(R.id.emailOrderTextField);
        streetTextField = findViewById(R.id.streetTextField);
        numberTextField = findViewById(R.id.numberTextField);
        deliverRadioGroup = findViewById(R.id.deliverRadioGroup);
        shippingRadioButton = findViewById(R.id.shippingRadioButton);
        takeAwayRadioButton = findViewById(R.id.takeAwayRadioButton);
        addPlateButton = findViewById(R.id.addPlateButton);
        orderPriceTextView = findViewById(R.id.orderPriceTextView);
        platesQuantityTextView = findViewById(R.id.platesQuantityTextView);
        orderDescriptionCardView = findViewById(R.id.orderDescriptionCardView);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);

        address = new Address();
        order = new Order();
        order.setAddress(address);

        mandatoryFieldValidation(emailOrderTextField);
        mandatoryFieldValidation(streetTextField);
        mandatoryFieldValidation(numberTextField);

        Objects.requireNonNull(emailOrderTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String email = emailOrderTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(email)) {
                        if (!emailValidation(email)) {
                            emailOrderTextField.setHelperTextEnabled(false);
                            emailOrderTextField.setError(getString(R.string.invalidEmail));
                        }
                        order.setEmail(email);
                    } else {
                        order.setEmail(null);
                    }
                    if (validateForm()) {
                        addPlateButton.setEnabled(true);
                    }
                    else {
                        addPlateButton.setEnabled(false);
                    }
                }
            }
        });

        Objects.requireNonNull(streetTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String street = streetTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(street)) {
                        order.getAddress().setStreet(street);
                    } else {
                        order.getAddress().setStreet(null);
                    }
                    if (validateForm()) {
                        addPlateButton.setEnabled(true);
                    }
                    else {
                        addPlateButton.setEnabled(false);
                    }
                }

            }
        });

        Objects.requireNonNull(numberTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String number = numberTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(number)) {
                        order.getAddress().setNumber(Integer.valueOf(number));
                    } else {
                        order.getAddress().setNumber(null);
                    }
                    if (validateForm()) {
                        addPlateButton.setEnabled(true);
                    }
                    else {
                        addPlateButton.setEnabled(false);
                    }
                }
            }
        });

        deliverRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) { //listener radiogroup
                if (id == R.id.shippingRadioButton) {
                    order.setToShip(true);
                } else {
                    order.setToShip(false);
                }
            }
        });

        addPlateButton.setOnClickListener(new View.OnClickListener() { //listener del boton registrar
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewOrderActivity.this, PlatesListActivity.class).putExtra("from","NewOrderActivity");
                startActivityForResult(intent, 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(resultCode==RESULT_OK) {
            if (requestCode == 2) {
                ArrayList<String> selectedPlates = data.getStringArrayListExtra("PLATE");
                if(selectedPlates != null) {
                    for (String title : selectedPlates) {
                        for (Plate plate : Plate.getListPlates()) {
                            if (title.toLowerCase().equals(plate.getTitle().toLowerCase())) {
                                orderPlates.add(plate);
                                totalPrice += plate.getPrice();
                            }
                        }
                    }
                    orderPlatesListRecyclerView = findViewById(R.id.orderPlatesRecyclerView);
                    orderPlatesListRecyclerView.setHasFixedSize(true);

                    orderPlatesListLayoutManager = new LinearLayoutManager(this);
                    orderPlatesListRecyclerView.setLayoutManager(orderPlatesListLayoutManager);

                    orderPlatesListAdapter = new OrderPlatesListAdapter(orderPlates);
                    orderPlatesListRecyclerView.setAdapter(orderPlatesListAdapter);

                    orderDescriptionCardView.setVisibility(View.VISIBLE);
                    confirmOrderButton.setVisibility(View.VISIBLE);

                    String price = totalPrice.toString();
                    String quantity = Integer.toString(orderPlates.size());
                    orderPriceTextView.setText("$ "+price);
                    platesQuantityTextView.setText(quantity);
                }
            }
        }
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

    public boolean validateForm() {

        boolean formValid = true;

       /*verificar que no haya errores*/
        if (emailOrderTextField.getError() != null &&
                !Objects.requireNonNull(emailOrderTextField.getError()).toString().isEmpty()) {
            formValid = false;
        }

        if (streetTextField.getError() != null &&
                !Objects.requireNonNull(streetTextField.getError()).toString().isEmpty()) {
            formValid = false;
        }

        if (numberTextField.getError() != null &&
                !Objects.requireNonNull(numberTextField.getError()).toString().isEmpty()) {
            formValid = false;
        }

        /*verificar que los campos no esten vacios al inicio*/
        if(Objects.requireNonNull(emailOrderTextField.getEditText()).getText().toString().isEmpty()) {
            formValid = false;
        }
        if(Objects.requireNonNull(streetTextField.getEditText()).getText().toString().isEmpty()) {
            formValid = false;
        }
        if(Objects.requireNonNull(numberTextField.getEditText()).getText().toString().isEmpty()) {
            formValid = false;
        }

        return formValid;
    }

}