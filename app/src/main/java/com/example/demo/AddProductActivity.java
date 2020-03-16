package com.example.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.demo.model.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;

public class AddProductActivity extends AppCompatActivity {
    private ImageButton bt_close;
    private static final int PICTURE_RESULT_1 = 42;
    private static final int PICTURE_RESULT_2 = 43;
    private Uri imageUri_1, imageUri_2;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String imageUrl_1, imageUrl_2;
    private AppCompatImageView image_1, image_2;
    private TextInputEditText product_name, product_location, product_description, product_price;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        image_1 = findViewById(R.id.image_1);
        image_2 = findViewById(R.id.image_2);
        bt_close = findViewById(R.id.bt_close);
        product_name = findViewById(R.id.product_name);
        product_location = findViewById(R.id.product_location);
        product_description = findViewById(R.id.product_description);
        product_price = findViewById(R.id.product_price);
        bt_close.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );

        progressDialog = new ProgressDialog(this){
            @Override
            public void onBackPressed() {
                progressDialog.cancel();
                progressDialog.dismiss();
            }
        };
        progressDialog.setCancelable(false);
    }

    public void uploadImage1(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_RESULT_1);

    }
    public void uploadImage2(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(intent.createChooser(intent, "insert Picture"), PICTURE_RESULT_2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICTURE_RESULT_1 && resultCode == RESULT_OK ){
            imageUri_1 = data.getData();
            try{
                final InputStream imageStream = getContentResolver().openInputStream(imageUri_1);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri_1);
                image_1.setImageBitmap(selectedImage);

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if(requestCode == PICTURE_RESULT_2 && resultCode == RESULT_OK){
            imageUri_2 = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri_2);
                image_2.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void saveDeal(View v){
        Log.e("Data to be submitted ",product_name.getText().toString()+product_description.getText().toString()
                +product_location.getText().toString()+product_price.getText().toString());
        if(product_name.getText() == null || product_description.getText() == null ||
                product_location.getText() == null || imageUri_1 == null || product_price.getText() == null){
            Toast.makeText(this, "Please provide all the required details", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setMessage("saving deal");
            progressDialog.show();
            storageReference = FirebaseStorage.getInstance().getReference().child("productImages").child(imageUri_1.getLastPathSegment());

            Task<UploadTask.TaskSnapshot> uploadTask = storageReference.putFile(imageUri_1);
            if(imageUrl_2 !=null){
                Task<UploadTask.TaskSnapshot> uploadTask_1 = storageReference.putFile(imageUri_2);
                uploadTask_1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            imageUrl_2 = downloadUri.toString();
                        }
                    }
                });

            }

            
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        progressDialog.cancel();
                        Uri downloadUri = task.getResult();
                        imageUrl_1 = downloadUri.toString();
                        databaseReference = FirebaseDatabase.getInstance().getReference()
                                .child("products");
                        databaseReference.push().setValue(new Product(
                                product_name.getText().toString(),
                                product_description.getText().toString(),
                                imageUrl_1,
                                imageUrl_2,
                                product_location.getText().toString(),
                                "Today",
                                product_price.getText().toString(),
                                FirebaseAuth.getInstance().getCurrentUser().getUid())
                        );
                        progressDialog.cancel();
                        progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(AddProductActivity.this, "Image added at "+imageUrl, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(AddProductActivity.this, "Upload error \nPlease try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }
}
