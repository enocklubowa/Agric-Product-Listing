package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText email_field, password_field;
    private String email, password;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_field = findViewById(R.id.email);
        password_field = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this){
            @Override
            public void onBackPressed() {
                progressDialog.cancel();
            }
        };
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating...");
    }

    public void signIn(View v){
        if(email_field.getText()!=null &&password_field.getText()!=null){
            progressDialog.show();
            email = email_field.getText().toString().trim();
            password = password_field.getText().toString().trim();
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.e("Login status","Successful");
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();
                            }
                            else {
                                Log.e("Login status","Unsuccessful");
                                progressDialog.cancel();
                                Toast.makeText(MainActivity.this, "Could not sign in\nPlease try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
        else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToSignup(View view){
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

}
