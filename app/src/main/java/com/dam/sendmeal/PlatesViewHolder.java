package com.dam.sendmeal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PlatesViewHolder extends RecyclerView.ViewHolder {
    CardView plateCardView;
    TextView plateTitleString;
    TextView platePriceDouble;
    ImageView plateImageView;

    public PlatesViewHolder(@NonNull View itemView) {
        super(itemView);
        plateCardView = (CardView) itemView.findViewById(R.id.plateCardView);
        plateImageView = (ImageView) itemView.findViewById(R.id.plateImageView);
        plateTitleString = (TextView) itemView.findViewById(R.id.plateTitleTextView);
        platePriceDouble = (TextView) itemView.findViewById(R.id.platePriceTextView);

    }






}
