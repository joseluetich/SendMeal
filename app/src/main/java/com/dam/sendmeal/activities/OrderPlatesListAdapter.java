package com.dam.sendmeal.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.sendmeal.R;
import com.dam.sendmeal.model.Plate;

import java.util.List;

public class OrderPlatesListAdapter extends RecyclerView.Adapter<OrderPlatesListAdapter.OrderPlatesViewHolder> {

    private List<Plate> orderPlates;

    public OrderPlatesListAdapter(List<Plate> listPlates) {
        this.orderPlates = listPlates;
    }

    @NonNull
    @Override
    public OrderPlatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //se ejecuta una vez por cada fila
        //obtenemos una vista y la inflamos con el layout

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_plate_row, parent, false);
        OrderPlatesViewHolder orderPlatesHolder = new OrderPlatesViewHolder(v);
        return orderPlatesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPlatesViewHolder holder, int position) {
        holder.orderPlateTitleString.setTag(position);
        holder.orderPlatePriceDouble.setTag(position);
        holder.quantityTextView.setTag(position);

        Plate plate = orderPlates.get(position);

        holder.orderPlateTitleString.setText(plate.getTitle().toUpperCase());
        String totalPrice = Double.toString(plate.getPrice() * plate.getQuantity());
        holder.orderPlatePriceDouble.setText("$ " + totalPrice);
        holder.quantityTextView.setText(plate.getQuantity()+"x");
    }

    @Override
    public int getItemCount() {
        return orderPlates.size();
    }

    public class OrderPlatesViewHolder extends RecyclerView.ViewHolder {
        TextView orderPlateTitleString, orderPlatePriceDouble, quantityTextView;
        CardView orderPlateCardView;

        public OrderPlatesViewHolder(@NonNull View itemView) {
            super(itemView);
            //orderPlateCardView = itemView.findViewById(R.id.orderPlateCardView);
            orderPlateTitleString = itemView.findViewById(R.id.orderPlateTitleTextView);
            orderPlatePriceDouble = itemView.findViewById(R.id.orderPlatePriceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
        }
    }

}
