package com.example.demo.ViewHolder;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.example.demo.ChatActivity;
import com.example.demo.ProductDetailsActivity;
import com.example.demo.R;
import com.example.demo.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends RecyclerView.ViewHolder{
    public View view;
    private TextView name_field, price_field, location_field;
    private ImageButton callButton, chatButton;
    private String sellerPhone;
    private static final int REQUEST_PHONE_CALL = 1;


    public ProductAdapter(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setDetails(String name, String location , String price, final String imageUrl, final String sellerId, final String description){
        getSellerPhone(sellerId);
        name_field = (TextView) view.findViewById(R.id.product_name);
        price_field = (TextView) view.findViewById(R.id.product_price);
        location_field = (TextView) view.findViewById(R.id.product_location);
        callButton = (ImageButton) view.findViewById(R.id.call_button);
        chatButton = (ImageButton) view.findViewById(R.id.chat_button);
        LoaderImageView image = (LoaderImageView) view.findViewById(R.id.product_cover_image);
        LinearLayout clickable_view = (LinearLayout) view.findViewById(R.id.lyt_parent);

        clickable_view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToProductDetailsActivity(sellerId, imageUrl, description);
                    }
                }
        );

        callButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.CALL_PHONE}
                            ,REQUEST_PHONE_CALL);

                        }
                        else {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sellerPhone));
                            view.getContext().startActivity(intent);
                        }

                    }
                }
        );

        chatButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openChatActivity(sellerId);
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

    private void getSellerPhone(String sellerId){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(sellerId);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user != null){
                            setUserPhone(user.getPhone());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    private void setUserPhone(String phone) {
        sellerPhone = phone;
    }

    public void openChatActivity(String sellerId){
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle extras = new Bundle();
        extras.putString("SELLER_ID", sellerId);
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }
}
