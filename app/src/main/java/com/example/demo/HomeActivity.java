package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomeActivity extends AppCompatActivity {
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragment = new ProductListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }



    public void clickAction(View view) {
        startActivity(new Intent(this, AddProductActivity.class));
    }



    public void openScreen(View v){

        switch(v.getId()){
            case R.id.home_button:
                fragment = new ProductListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                break;

            case R.id.profile_button:
                fragment = new ProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                break;


        }
    }
}
