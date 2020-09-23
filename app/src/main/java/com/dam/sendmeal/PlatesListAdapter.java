package com.dam.sendmeal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.sendmeal.model.Plato;

import java.util.List;

public class PlatesListAdapter extends RecyclerView.Adapter<PlatesViewHolder> {

    private List<Plato> plates;

    public PlatesListAdapter(List<Plato> listPlates) {
        plates = listPlates;
    }

    @NonNull
    @Override
    public PlatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //se ejecuta una vez por cada fila
        //obtenemos una vista y la inflamos con el layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plate_row, parent, false);
        PlatesViewHolder platesHolder = new PlatesViewHolder(v);
        return platesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatesViewHolder holder, int position) {
        holder.plateImageView.setTag(position);
        holder.plateTitleString.setTag(position);
        holder.platePriceDouble.setTag(position);
        Plato plate = plates.get(position);
        //holder.plateImageView.setImageResource(plate.getImage());

        holder.plateImageView.setImageResource(R.drawable.pizza_image);
        holder.plateTitleString.setText(plate.getTitle().toUpperCase());
        holder.platePriceDouble.setText(plate.getStringPrice());
    }

    @Override
    public int getItemCount() {
        return plates.size();
    }
}
