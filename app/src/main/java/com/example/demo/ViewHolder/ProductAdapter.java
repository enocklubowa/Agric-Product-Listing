package com.example.demo.ViewHolder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.example.demo.ProductDetailsActivity;
import com.example.demo.R;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends RecyclerView.ViewHolder{
    public View view;
    private TextView name_field, price_field, location_field;

    public ProductAdapter(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setDetails(String name, String location , String price, final String imageUrl, final String userId, final String description){
        name_field = (TextView) view.findViewById(R.id.product_name);
        price_field = (TextView) view.findViewById(R.id.product_price);
        location_field = (TextView) view.findViewById(R.id.product_location);
        LoaderImageView image = (LoaderImageView) view.findViewById(R.id.product_cover_image);
        LinearLayout clickable_view = (LinearLayout) view.findViewById(R.id.lyt_parent);

        clickable_view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToProductDetailsActivity(userId, imageUrl, description);
                    }
                }
        );

        name_field.setText(name);
        price_field.setText("USh "+price);
        location_field.setText(location);
        if(imageUrl != null){
            Picasso.get()
                    .load(imageUrl)
                    .resize(100, 100)
                    .centerCrop()
                    .into(image);
        }
        


    }

    private void goToProductDetailsActivity(String userId, String imageUrl, String description) {
        Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putString("USER_ID", userId);
        extras.putString("PRODUCT_NAME", name_field.getText().toString());
        extras.putString("PRICE", price_field.getText().toString());
        extras.putString("LOCATION", location_field.getText().toString());
        extras.putString("IMAGE_URL", imageUrl);
        extras.putString("DESCRIPTION", description);
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }
}
