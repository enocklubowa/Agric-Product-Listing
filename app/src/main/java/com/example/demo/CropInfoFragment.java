package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class CropInfoFragment extends Fragment {
    private ProgressBar loading_page;
    private String pest_name, symptom, control;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crop_info, container, false);

        return root;
    }

    public void pestDetails(View view){
        Intent i = new Intent(getContext(),PestDetails.class);
        switch (view.getId()){
            case R.id.white_grub:
                pest_name = "White Grub";
                symptom = "Yellowing and wilting of shoots\nLarge holes in Rhizomes";
                control = "Apply neem cake @ 40 kg/ha, entromophagous fungus plus cow dung";
                i.putExtra("PEST_NAME", pest_name);
                i.putExtra("SYMPTOM", symptom);
                i.putExtra("CONTROL", control);
                i.putExtra("CATEGORY","Pest");
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
                startActivity(i);
                break;

        }
    }
}
