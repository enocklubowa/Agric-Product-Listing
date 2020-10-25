package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.example.demo.utils.ViewAnimation;
import com.example.demo.utils.Tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    private View parent_view;

    private ImageButton bt_toggle_reviews, bt_toggle_warranty, bt_toggle_description;
    private View lyt_expand_reviews;
    private NestedScrollView nested_scroll_view;
    private String sellerId;
    private String product_name;
    private String price;
    private String location;
    private String imageUrl;
    private String description;
    private TextView description_field, location_field, price_field, name_field;
    private ImageView product_image;
    private LinearLayout description_layout;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        parent_view = findViewById(R.id.parent_view);
        initToolbar();
        getIntentExtras();
        initComponent();
        loadImage();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fashion");
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        // nested scrollview
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);

        product_image = findViewById(R.id.product_image);

        description_field = findViewById(R.id.description);
        description_field.setText(description);

        location_field = findViewById(R.id.location);
        location_field.setText(location);

        name_field = findViewById(R.id.product_name);
        name_field.setText(product_name);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        price_field = findViewById(R.id.price);
        String shillings_format = "UGX "+price;
        price_field.setText(shillings_format);

        // section reviews
        bt_toggle_reviews = (ImageButton) findViewById(R.id.bt_toggle_reviews);
        lyt_expand_reviews = (View) findViewById(R.id.lyt_expand_reviews);
        bt_toggle_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_reviews);
            }
        });



        // section description
        bt_toggle_description = (ImageButton) findViewById(R.id.bt_toggle_description);
        description_layout = findViewById(R.id.description_layout);
        bt_toggle_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, description_layout);
            }
        });

        // expand first description
        toggleArrow(bt_toggle_description);
        description_layout.setVisibility(View.VISIBLE);

        ExtendedFloatingActionButton message_button = findViewById(R.id.fab);
        if (userId.equals(sellerId)){
            message_button.setVisibility(View.GONE);
        }
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChatActivity();
            }
        });

    }

    private void toggleSection(View bt, final View lyt) {
        boolean show = toggleArrow(bt);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    private void getIntentExtras(){
        Bundle extras = getIntent().getExtras();
        sellerId = extras.getString("USER_ID");
        product_name = extras.getString("PRODUCT_NAME");
        price = extras.getString("PRICE");
        location = extras.getString("LOCATION");
        imageUrl = extras.getString("IMAGE_URL");
        description = extras.getString("DESCRIPTION");

    }

    private void loadImage(){
        if(imageUrl != null){
            Picasso.get()
                    .load(imageUrl)
                    .resize(100, 100)
                    .centerCrop()
                    .into(product_image);
        }
    }

    public void openChatActivity(){
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle extras = new Bundle();
        extras.putString("SELLER_ID", sellerId);
        intent.putExtras(extras);
        startActivity(intent);
    }
}