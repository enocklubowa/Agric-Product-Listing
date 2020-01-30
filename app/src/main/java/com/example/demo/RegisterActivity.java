package com.example.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText email_field, phone_field, name_field, password_field;
    private String email, phone, name, password;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email_field = findViewById(R.id.email);
        name_field = findViewById(R.id.name);
        phone_field = findViewById(R.id.phone);
        password_field = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this){
            @Override
            public void onBackPressed() {
                progressDialog.cancel();
            }
        };
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Creating user...");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                }
        );
    }

    public void signUp(View v){
        email = email_field.getText().toString().trim();
        phone = phone_field.getText().toString().trim();
        name = name_field.getText().toString().trim();
        password = password_field.getText().toString().trim();
        if(!email.equals("") && !phone.equals("") && !name.equals("") && !password.equals("")){
            progressDialog.show();
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.e("Sign up status","Successful");
                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                finish();
                            }
                            else {
                                Log.e("Sign up status","Unsuccessful");

                                progressDialog.cancel();
                                Toast.makeText(RegisterActivity.this, "Could not sign up\nPlease try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
        else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
