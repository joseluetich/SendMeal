package com.dam.sendmeal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.sendmeal.model.Plate;

import java.util.ArrayList;
import java.util.List;

public class PlatesListAdapter extends RecyclerView.Adapter<PlatesListAdapter.PlatesViewHolder> {

    private List<Plate> plates;
    Activity platesListActivity;
    ArrayList<String> selectedPlates = new ArrayList<>();

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
        holder.orderButton.setTag(position);

        Plate plate = plates.get(position);

        holder.plateImageView.setImageResource(R.drawable.pizza_image);
        holder.plateTitleString.setText(plate.getTitle().toUpperCase());
        holder.platePriceDouble.setText(plate.getStringPrice());


    }

    @Override
    public int getItemCount() {
        return plates.size();
    }

    public class PlatesViewHolder extends RecyclerView.ViewHolder {
        CardView plateCardView;
        TextView plateTitleString;
        TextView platePriceDouble;
        ImageView plateImageView;
        Button orderButton;

        public PlatesViewHolder(@NonNull View itemView, final Activity platesListActivity) {
            super(itemView);
            plateCardView = itemView.findViewById(R.id.plateCardView);
            plateImageView = itemView.findViewById(R.id.plateImageView);
            plateTitleString = itemView.findViewById(R.id.plateTitleTextView);
            platePriceDouble = itemView.findViewById(R.id.platePriceTextView);
            orderButton = itemView.findViewById(R.id.orderButton);


            if (platesListActivity.getIntent().getStringExtra("from").equals("NewOrderActivity")) {
                orderButton.setVisibility(View.VISIBLE);
            }
            else {
                orderButton.setVisibility(View.GONE);
            }

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(Plate plate: Plate.getListPlates()) {
                        if(plate.getTitle().toLowerCase().equals(plateTitleString.getText().toString().toLowerCase())) {
                            selectedPlates.add(plate.getTitle());
                        }
                    }
                }
            });
        }
    }

}
