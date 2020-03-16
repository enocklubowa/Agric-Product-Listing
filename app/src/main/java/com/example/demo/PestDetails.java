package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PestDetails extends AppCompatActivity {
    private String pest_name, symptom, control, category, image;
    private TextView name_field, symptom_field, control_field, category_field;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_details);
        name_field = findViewById(R.id.pest_name);
        symptom_field = findViewById(R.id.symptoms_text);
        control_field = findViewById(R.id.control_text);
        category_field = findViewById(R.id.category);
        imageView = findViewById(R.id.image_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );
        Intent i = getIntent();
        pest_name = i.getStringExtra("PEST_NAME");
        symptom = i.getStringExtra("SYMPTOM");
        control = i.getStringExtra("CONTROL");
        category = i.getStringExtra("CATEGORY");
        image = i.getStringExtra("IMAGE");
        name_field.setText(pest_name);
        symptom_field.setText(symptom);
        control_field.setText(control);
        category_field.setText(category);
        switch (image){
            case "white_grub":
                imageView.setImageResource(R.drawable.white_grub);
                break;
            case "shoot_borer":
                imageView.setImageResource(R.drawable.shoot_borer);
                break;
            case "rhizome_scale":
                imageView.setImageResource(R.drawable.rhizome_scale);
                break;
            case "bacteria_wilt":
                imageView.setImageResource(R.drawable.bacteria_wilt);
                break;
            case "soft_rot":
                imageView.setImageResource(R.drawable.soft_rot);
                break;
            case "dry_rot":
                imageView.setImageResource(R.drawable.dry_rot);
                break;
            case "leaf_spot":
                imageView.setImageResource(R.drawable.leaf_spot);
                break;
        }
    }
}
