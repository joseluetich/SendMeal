package com.dam.sendmeal.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.sendmeal.R;
import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.viewModel.PlateViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlatesListAdapter extends RecyclerView.Adapter<PlatesListAdapter.PlatesViewHolder> {

    private final LayoutInflater mInflater;
    private List<Plate> plates;
    Activity platesListActivity;
    ArrayList<String> selectedPlates = new ArrayList<>();

    // TODO: Verify platesListAdapter constructor
    PlatesListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

//    public PlatesListAdapter(List<Plate> listPlates, Activity activity, ArrayList<String> selectedPlates) {
//        this.plates = listPlates;
//        this.platesListActivity = activity;
//        this.selectedPlates = selectedPlates;
//    }

    @NonNull
    @Override
    public PlatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //se ejecuta una vez por cada fila
        //obtenemos una vista y la inflamos con el layout

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plate_row, parent, false);
        PlatesViewHolder platesHolder = new PlatesViewHolder(v,(Activity) parent.getContext());
        return platesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatesViewHolder holder, int position) {
        if (plates != null) {
            Plate plate = plates.get(position);

            holder.plateImageView.setTag(position);
            holder.plateTitleString.setTag(position);
            holder.platePriceDouble.setTag(position);
            //holder.orderButton.setTag(position);
            holder.minusImageView.setTag(position);
            holder.plusImageView.setTag(position);
            holder.numberPickerTextView.setTag(position);
            holder.numberPickerConstraintLayout.setTag(position);

            holder.plateImageView.setImageResource(R.drawable.pizza_image);
            holder.plateTitleString.setText(plate.getTitle().toUpperCase());
            holder.platePriceDouble.setText(plate.getStringPrice());
            String quantity = plate.getQuantity() != null ? plate.getQuantity().toString() : "0" ;
            holder.numberPickerTextView.setText(quantity);

        } else {
            // Covers the case of data not being ready yet.
            holder.plateTitleString.setText("No Word");
        }
    }

    public void setPlates(List<Plate> plates){
        this.plates = plates;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (plates != null)
            return plates.size();
        else return 0;
    }

    public class PlatesViewHolder extends RecyclerView.ViewHolder {
        CardView plateCardView;
        TextView plateTitleString, platePriceDouble, numberPickerTextView;
        ImageView plateImageView, minusImageView, plusImageView;
        ConstraintLayout numberPickerConstraintLayout;
        //int number;

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

            if (platesListActivity.getIntent().getStringExtra("from").equals("NewOrderActivity")) {
                numberPickerConstraintLayout.setVisibility(View.VISIBLE);
            }
            else {
                numberPickerConstraintLayout.setVisibility(View.INVISIBLE);
                // TODO: Verify how is accessing to plates
                for(Plate plate: plates){
                    plate.setQuantity(0);
                }
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
                    }
                }
            });

        }
    }

}
