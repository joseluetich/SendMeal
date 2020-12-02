package com.dam.sendmeal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.sendmeal.R;
import com.dam.sendmeal.model.Address;
import com.dam.sendmeal.model.Order;
import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.repository.OrderRepository;
import com.dam.sendmeal.repository.PlateRepository;
import com.dam.sendmeal.utils.NotificationPublisher;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewOrderActivity extends AppCompatActivity implements OrderRepository.OnResultCallback{

    Toolbar newOrderToolbar;
    TextInputLayout emailOrderTextField, streetTextField, numberTextField, floorTextField, apartmentTextField;
    RadioGroup deliverRadioGroup;
    RadioButton shippingRadioButton, takeAwayRadioButton;
    Address address;
    Button addPlateButton, confirmOrderButton;
    Order order;
    ArrayList<Plate> orderPlates = new ArrayList<>();
    RecyclerView orderPlatesListRecyclerView;
    RecyclerView.Adapter orderPlatesListAdapter;
    //CardView orderDescriptionCardView;
    ConstraintLayout orderDescriptionConstraintLayout;
    RecyclerView.LayoutManager orderPlatesListLayoutManager;
    TextView orderPriceTextView, platesQuantityTextView, platesListTextView;
    OrderRepository repository;

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
        //orderDescriptionCardView = findViewById(R.id.orderDescriptionCardView);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);
        floorTextField = findViewById(R.id.floorTextField);
        apartmentTextField = findViewById(R.id.apartmentTextField);
        platesListTextView = findViewById(R.id.platesListTextView);
        //orderListCardView = findViewById(R.id.orderDescriptionCardView);
        orderDescriptionConstraintLayout = findViewById(R.id.orderDescriptionConstraintLayout);

        order = new Order();
        address = new Address();
        order.setAddress(address);

        repository = new OrderRepository(this.getApplication(), this);

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

        Objects.requireNonNull(floorTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String floor = floorTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(floor)) {
                        order.getAddress().setFloor(Integer.valueOf(floor));
                    } else {
                        order.getAddress().setFloor(null);
                    }
                }

            }
        });

        Objects.requireNonNull(apartmentTextField.getEditText()).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String apartment = apartmentTextField.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(apartment)) {
                        order.getAddress().setApartment(apartment);
                    } else {
                        order.getAddress().setApartment(null);
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

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm() && orderPlates.size() >= 1){
                    new SimpleAsyncTask(confirmOrderButton).execute();
                    Intent intent = new Intent(NewOrderActivity.this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        Double totalPrice = 0.0;
        Integer totalPlates = 0;

        if(resultCode==RESULT_OK) {
            if (requestCode == 2) {
                ArrayList<String> selectedPlates = data.getStringArrayListExtra("PLATE");
                if(selectedPlates != null) {
                    for (String title : selectedPlates) {
                        //  repository.searchAll(); si agrego esto, deberia poner algo en on result, pero no quiero
                        //  que se cambie para el onresult del insert
                        for (Plate plate : Plate.getListPlates()) { //TODO cambiar Plate.getlistplates() por busqueda en bdd
                            if (title.toLowerCase().equals(plate.getTitle().toLowerCase()) && plate.getQuantity()>0) {
                                if(!orderPlates.contains(plate)){
                                    orderPlates.add(plate);
                                    System.out.println("add: "+plate);
                                }
                            }
                        }
                    }

                    orderPlatesListRecyclerView = findViewById(R.id.orderPlatesRecyclerView);
                    orderPlatesListRecyclerView.setHasFixedSize(true);

                    orderPlatesListLayoutManager = new LinearLayoutManager(this);
                    orderPlatesListRecyclerView.setLayoutManager(orderPlatesListLayoutManager);

                    System.out.println("orderplates: "+orderPlates);
                    orderPlatesListAdapter = new OrderPlatesListAdapter(orderPlates);
                    orderPlatesListRecyclerView.setAdapter(orderPlatesListAdapter);

                    orderDescriptionConstraintLayout.setVisibility(View.VISIBLE);
                    confirmOrderButton.setVisibility(View.VISIBLE);
                    platesListTextView.setVisibility(View.VISIBLE);

                    ArrayList<Plate> nullPlates = new ArrayList<>();
                    for(Plate plate : orderPlates) {
                        if(plate.getQuantity().equals(0)) {
                            nullPlates.add(plate);
                        }
                        totalPlates += plate.getQuantity();
                        totalPrice += plate.getPrice()*plate.getQuantity();
                    }
                    orderPlates.removeAll(nullPlates);
                    if(orderPlates.size()==0) {
                        confirmOrderButton.setVisibility(View.INVISIBLE);
                        platesListTextView.setVisibility(View.INVISIBLE);
                        orderDescriptionConstraintLayout.setVisibility(View.INVISIBLE);
                        addPlateButton.setText(R.string.addPlate);
                    }
                    else {
                        addPlateButton.setText(R.string.editPlate);
                    }

                    order.setPlates(orderPlates);

                    String price = totalPrice.toString();
                    orderPriceTextView.setText("$ "+price);
                    String quantity = Integer.toString(totalPlates);
                    platesQuantityTextView.setText(quantity);

                    repository.insert(order);
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

    @Override
    public void onResult(List result) {

    }

    @Override
    public void onInsert() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NewOrderActivity.this, "Orden agregada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class SimpleAsyncTask extends AsyncTask <Void, Void, String>{

        private WeakReference<Button> mButton;

        SimpleAsyncTask(Button button) {
            mButton = new WeakReference<>(button);
        }

        @Override
        protected String doInBackground(Void... voids) {

            final int SLEEP_TIME = 5000;


            // Sleep for the random amount of time
            try {
                Log.println(Log.INFO, "G", "Going to sleep for " + SLEEP_TIME + " milliseconds!");
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Return a String result
            Log.println(Log.INFO, "G", "Awake at last after sleeping for " + SLEEP_TIME + " milliseconds!");
            return null;
        }

        protected void onPostExecute(String result) {
            Log.println(Log.INFO, "G","Awaking");
            NotificationPublisher notificationPublisher = NotificationPublisher.getInstance();
            Intent intent = new Intent();
            notificationPublisher.onReceive(mButton.get().getContext(),intent);
        }
    }

}