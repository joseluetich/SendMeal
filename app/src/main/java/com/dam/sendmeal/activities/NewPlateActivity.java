package com.dam.sendmeal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dam.sendmeal.R;
import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.repository.PlateRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPlateActivity extends AppCompatActivity implements PlateRepository.OnResultCallback {

    Toolbar newPlateToolbar;
    TextInputLayout titleTextField, descriptionTextField, priceTextField, caloriesTextField;
    Button savePlateButton, takePhotoButton, uploadPhotoButton;
    Plate plate;
    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;
    byte[] photo;
    StorageReference platosImagesRef, storageRef;
    FirebaseStorage storage;
    ImageView photoImageView;
    PlateRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plate);

        // Creamos una referencia a nuestro Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        titleTextField = findViewById(R.id.titleTextInputLayout);
        descriptionTextField = findViewById(R.id.descriptionTextInputLayout);
        priceTextField = findViewById(R.id.priceTextInputLayout);
        caloriesTextField = findViewById(R.id.caloriesTextInputLayout);
        savePlateButton = findViewById(R.id.savePlateButton);
        takePhotoButton = findViewById(R.id.takePhotoButton);
        uploadPhotoButton = findViewById(R.id.uploadPhotoButton);
        photoImageView = findViewById(R.id.photoImageView);

        plate = new Plate();
        plate.setQuantity(0);
        photoImageView.setVisibility(View.GONE);

        repository = new PlateRepository(this.getApplication(), this);

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
                        if(!enteredValidTitle()) {
                            titleTextField.setHelperTextEnabled(false);
                            titleTextField.setError(getString(R.string.invalidTitle));
                        }
                        else {
                            titleTextField.setHelperTextEnabled(true);
                            titleTextField.setError(null);
                        }
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
                            priceTextField.setError(getString(R.string.invalidPrice));
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
                if (validateForm()) {
                    plate.addToPlates();
                    savePhoto();

                    Toast aviso = Toast.makeText(NewPlateActivity.this, "Plato creado correctamente", Toast.LENGTH_LONG); //aviso al usuario
                    aviso.show();
                    repository.insert(plate);
                    finish();
                }
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camaraIntent, CAMARA_REQUEST);
            }
        });

        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galeriaIntent, GALERIA_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == CAMARA_REQUEST) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                photo = baos.toByteArray(); // Imagen en arreglo de bytes

                photoImageView.setVisibility(View.VISIBLE); //preview
                photoImageView.setImageBitmap(imageBitmap);

            }
            else if(requestCode == GALERIA_REQUEST) {
                Uri selectedImage = data.getData();
                InputStream is;
                try {
                    is = getContentResolver().openInputStream(selectedImage);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    photo = baos.toByteArray(); // Imagen en arreglo de bytes

                    photoImageView.setImageBitmap(bitmap);
                    photoImageView.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {}
            }
        }

    }

    public void savePhoto() {
        final Context context = this;

        // Creamos una referencia a 'images/plato_id.jpg'
        platosImagesRef = storageRef.child("images/"+plate.getTitle()+".jpg");

        // Cualquiera de los tres métodos tienen la misma implementación, se debe utilizar el que corresponda
        UploadTask uploadTask = platosImagesRef.putBytes(photo);
        // UploadTask uploadTask = platosImagesRef.putFile(file);
        // UploadTask uploadTask = platosImagesRef.putStream(stream);

        // Registramos un listener para saber el resultado de la operación
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {

            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continuamos con la tarea para obtener la URL
                return platosImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // URL de descarga del archivo
                    Uri downloadUri = task.getResult();
                    plate.setPhoto(downloadUri.toString());
                } else {
                    Toast.makeText(context, "Error al cargar la imagen", Toast.LENGTH_LONG).show();
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

    public boolean validateForm() {
        boolean validForm = true;
        if(!validateAllMandatoryFields()) {
            validForm = false;
        }
        else if(!enteredValidPrice()) {
            validForm = false;
        }
        else if(!enteredValidTitle()) {
            validForm = false;
        }
        return validForm;
    }
    public boolean enteredValidTitle() {
        boolean validTitle = true;
        String title = titleTextField.getEditText().getText().toString();
        for(Plate plate: Plate.getListPlates()) {
            if(plate.getTitle().toLowerCase().equals(title.toLowerCase())) {
                validTitle = false;
            }
        }
        if(!validTitle) {
            titleTextField.setHelperTextEnabled(false);
            titleTextField.setError(getString(R.string.invalidTitle));
        }
        else {
            titleTextField.setHelperTextEnabled(true);
            titleTextField.setError(null);
        }
        return validTitle;
    }
    public boolean enteredValidPrice() {
        Pattern pricePattern = Pattern.compile(getString(R.string.entidadRegularPrecio)); //indica como deberia ser el precio
        String price = priceTextField.getEditText().getText().toString();
        Matcher priceMatcher = pricePattern.matcher(price); //lo va a comparar con lo ingresado en precio
        String pricePatternError = null;
        boolean validPrice = true;
        if(!price.isEmpty() && !priceMatcher.find()) { //analiza si el precio ingresado tiene hasta dos decimales
            priceTextField.setHelperTextEnabled(false);
            pricePatternError = getString(R.string.invalidPrice);
            validPrice = false;
        }
        else {
            priceTextField.setHelperTextEnabled(true);
        }
        priceTextField.setError(pricePatternError);
        return validPrice;
    }

    @Override
    public void onResultPlate(List<Plate> result) {

    }

    @Override
    public void onInsert() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NewPlateActivity.this, "Plato agregado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}