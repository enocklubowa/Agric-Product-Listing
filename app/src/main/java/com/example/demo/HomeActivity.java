package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);

        navigation = findViewById(R.id.navigation);
        //BottomNavigationViewHelper.disableShiftMode(navigation);
        displaySelectedScreen(R.id.navigation_1);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ViewAnimation.fadeOutIn(findViewById(R.id.demo_frame));
                displaySelectedScreen(item.getItemId());
                return true;
            }
        });

    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment=null;


        switch (itemId){
            case R.id.navigation_1:
                fragment = new ProductListFragment();
                break;
            case R.id.navigation_2:
                fragment= new MessagesFragment();
                break;
            case R.id.navigation_3:
                fragment= new CropInfoFragment();
                break;

            case  R.id.navigation_4:
                fragment= new ProfileFragment();
                break;

        }


        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.demo_frame, fragment);
            ft.commit();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickAction(View view) {
        startActivity(new Intent(this, AddProductActivity.class));
    }

    private String pest_name, symptom, control;
    public void pestDetails(View view){
        Intent i = new Intent(this,PestDetails.class);
        switch (view.getId()){
            case R.id.white_grub:
                pest_name = "White Grub";
                symptom = "Yellowing and wilting of shoots\nLarge holes in Rhizomes";
                control = "Apply neem cake @ 40 kg/ha, entromophagous fungus plus cow dung";
                i.putExtra("PEST_NAME", pest_name);
                i.putExtra("SYMPTOM", symptom);
                i.putExtra("CONTROL", control);
                i.putExtra("CATEGORY","Pest");
                i.putExtra("IMAGE","white_grub");
                startActivity(i);
                break;
            case R.id.shoot_borer:
                pest_name = "Shoot borer";
                symptom = "Yellowing and wilting of shoots\nLarge holes in Rhizomes";
                control = " Spraying with Nimbicidine 25ml/L";
                i.putExtra("PEST_NAME", pest_name);
                i.putExtra("SYMPTOM", symptom);
                i.putExtra("CONTROL", control);
                i.putExtra("CATEGORY","Pest");
                i.putExtra("IMAGE","shoot_borer");
                startActivity(i);
                break;
            case R.id.rhizome_scale:
                pest_name = "Rhizome scale";
                symptom = "Light brown to grey appear on Rhizomes";
                control = "Treat the rhizomes with quinalphos 0.075% for 20-30 minutes";
                i.putExtra("PEST_NAME", pest_name);
                i.putExtra("SYMPTOM", symptom);
                i.putExtra("CONTROL", control);
                i.putExtra("CATEGORY","Pest");
                i.putExtra("IMAGE","rhizome_scale");
                startActivity(i);
                break;
            case R.id.bacteria_wilt:
                pest_name = "Bacterial wilt (Ralstonia solanacear)";
                symptom = "Leaf margins turn bronze and curl backward";
                control = "Treat the seeds with Streptocyclin (20g/100litres of water)\nDrench the soil with copper oxychloride 0.2%";
                i.putExtra("PEST_NAME", pest_name);
                i.putExtra("SYMPTOM", symptom);
                i.putExtra("CONTROL", control);
                i.putExtra("CATEGORY","Disease");
                i.putExtra("IMAGE","bacteria_wilt");
                startActivity(i);
                break;
            case R.id.soft_rot:
                pest_name = "Soft rot (Pythium aphanidrematum)";
                symptom = "Yellowing of the leaves and Rotten Rhizome";
                control = "Treat the Rhizome with Bordeaux mixture (1%) and with Trichoderma@8-10gm/litre of water";
                i.putExtra("PEST_NAME", pest_name);
                i.putExtra("SYMPTOM", symptom);
                i.putExtra("CONTROL", control);
                i.putExtra("CATEGORY","Disease");
                i.putExtra("IMAGE","soft_rot");
                startActivity(i);
                break;
            case R.id.dry_rot:
                pest_name = "Dry rot (fusarium and pratylenchus complex)";
                symptom = " Brownish ring on the cut Rhizome\nstunted growth of the plant and yellowing of leaves";
                control = "Mix the soil with mustard oil cake (40kg/ha) followed by hot water treatment at 50 degrees Celsius followed with Bordeaux mixture 1%";
                i.putExtra("PEST_NAME", pest_name);
                i.putExtra("SYMPTOM", symptom);
                i.putExtra("CONTROL", control);
                i.putExtra("CATEGORY","Disease");
                i.putExtra("CATEGORY","Disease");
                i.putExtra("IMAGE","dry_rot");
                startActivity(i);
                break;
            case R.id.leaf_spot:
                pest_name = "Leaf spot (blight)";
                symptom = " Small spindle and oval spots appear on leaves";
                control = "Spray Bordeaux mixture (1%)   3-4 times at 15 days’ intervals";
                i.putExtra("PEST_NAME", pest_name);
                i.putExtra("SYMPTOM", symptom);
                i.putExtra("CONTROL", control);
                i.putExtra("CATEGORY","Disease");
                i.putExtra("IMAGE","leaf_spot");
                startActivity(i);
                break;

        }
    }
}
