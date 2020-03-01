package com.example.demo.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

public class ProductAdapter extends RecyclerView.ViewHolder{
    View view;
    public ProductAdapter(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setDetails(String name, String location , String price){
        TextView name_field, price_field, location_field;
        name_field = (TextView) view.findViewById(R.id.product_name);
        price_field = (TextView) view.findViewById(R.id.product_price);
        location_field = (TextView) view.findViewById(R.id.product_location);

        name_field.setText(name);
        price_field.setText("USh "+price);
        location_field.setText(location);

    }
}
