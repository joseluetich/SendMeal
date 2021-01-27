package com.dam.sendmeal.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.sendmeal.R;
import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.repository.PlateRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PlatesListAdapter extends RecyclerView.Adapter<PlatesListAdapter.PlatesViewHolder> {

    private List<Plate> plates;
    Activity platesListActivity;
    ArrayList<String> selectedPlates;
    StorageReference gsReference;


    public PlatesListAdapter(List<Plate> listPlates, Activity activity, ArrayList<String> selectedPlates) {
        this.plates = listPlates;
        this.platesListActivity = activity;
        this.selectedPlates = selectedPlates;
    }

    @NonNull
    @Override
    public PlatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //se ejecuta una vez por cada fila
        //obtenemos una vista y la inflamos con el layout

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plate_row, parent, false);
        PlatesViewHolder platesHolder = new PlatesViewHolder(v,platesListActivity);
        return platesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatesViewHolder holder, int position) {
        holder.plateImageView.setTag(position);
        holder.plateTitleString.setTag(position);
        holder.platePriceDouble.setTag(position);
        //holder.orderButton.setTag(position);
        holder.minusImageView.setTag(position);
        holder.plusImageView.setTag(position);
        holder.numberPickerTextView.setTag(position);
        holder.numberPickerConstraintLayout.setTag(position);

        Plate plate = plates.get(position);

        //holder.plateImageView.setImageResource(plate.getPhoto());
        holder.plateTitleString.setText(plate.getTitle().toUpperCase());
        holder.platePriceDouble.setText(plate.getStringPrice());
        String quantity = plate.getQuantity().toString();
        holder.numberPickerTextView.setText(quantity);

    }

    @Override
    public int getItemCount() {
        return plates.size();
    }

    public class PlatesViewHolder extends RecyclerView.ViewHolder implements PlateRepository.OnResultCallback {
        CardView plateCardView;
        TextView plateTitleString, platePriceDouble, numberPickerTextView;
        ImageView plateImageView, minusImageView, plusImageView;
        ConstraintLayout numberPickerConstraintLayout;
        PlateRepository plateRepository;

        public PlatesViewHolder(@NonNull View itemView, final Activity platesListActivity) {
            super(itemView);
            plateCardView = itemView.findViewById(R.id.plateCardView);
            plateImageView = itemView.findViewById(R.id.plateImageView);
            plateTitleString = itemView.findViewById(R.id.plateTitleTextView);
            platePriceDouble = itemView.findViewById(R.id.platePriceTextView);
            minusImageView = itemView.findViewById(R.id.minusImageView);
            plusImageView = itemView.findViewById(R.id.plusImageView);
            numberPickerTextView = itemView.findViewById(R.id.numberPickerTextView);
            numberPickerConstraintLayout = itemView.findViewById(R.id.numberPickerConstraintLayout);
            plateRepository = new PlateRepository(platesListActivity.getApplication(), this);


            for(Plate plate: plates) {
                uploadPhoto(plate);
            }

            if (platesListActivity.getIntent().getStringExtra("from").equals("NewOrderActivity")) {
                numberPickerConstraintLayout.setVisibility(View.VISIBLE);
            }
            else {
                numberPickerConstraintLayout.setVisibility(View.INVISIBLE);
                plateRepository.searchAll();
            }

            minusImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = Integer.parseInt(numberPickerTextView.getText().toString());
                    if(num>0) {
                        num--;
                    }
                    numberPickerTextView.setText(num+"");
                    for(Plate plate: plates) {
                        if(plate.getTitle().toLowerCase().equals(plateTitleString.getText().toString().toLowerCase())) {
                            selectedPlates.remove(plate.getTitle());
                            plate.setQuantity(num);
                            if(plate.getQuantity()==0){
                                platePriceDouble.setText(plate.getStringPrice());
                            }
                            else {
                                String totalPrice = Double.toString(plate.getPrice() * plate.getQuantity());
                                platePriceDouble.setText("$ " + totalPrice);
                            }
                            plateRepository.update(plate);
                        }
                    }
                }
            });

            plusImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = Integer.parseInt(numberPickerTextView.getText().toString());
                    num++;
                    numberPickerTextView.setText(num+"");
                    for(Plate plate: plates) {
                        if(plate.getTitle().toLowerCase().equals(plateTitleString.getText().toString().toLowerCase())) {
                            selectedPlates.add(plate.getTitle());
                            plate.setQuantity(num);
                            String totalPrice = Double.toString(plate.getPrice()*plate.getQuantity());
                            platePriceDouble.setText("$ "+totalPrice);
                        }
                        plateRepository.update(plate);
                    }
                }
            });

        }

        @Override
        public void onResultPlate(List<Plate> result) {
            for(Plate plate: result){
                plate.setQuantity(0);
                plateRepository.update(plate);
            }
        }

        @Override
        public void onInsert() {

        }

        public void uploadPhoto(Plate plate) {
            // Creamos una referencia al storage con la Uri de la img("images/"+plate.getTitle()+".jpg")
            gsReference = FirebaseStorage.getInstance().getReference("images/"+plate.getTitle()+".jpg");

            final long THREE_MEGABYTE = 3 * 1024 * 1024;
            gsReference.getBytes(THREE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Exito
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    DisplayMetrics dm = new DisplayMetrics();
                    platesListActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);

                    plateImageView.setMinimumHeight(dm.heightPixels);
                    plateImageView.setMinimumWidth(dm.widthPixels);
                    plateImageView.setImageBitmap(bm);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(platesListActivity, "Error al cargar la imagen", Toast.LENGTH_LONG).show();
                }
            });

        }

    }

}
