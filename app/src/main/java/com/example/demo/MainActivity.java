package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText  mTextUsername;
    EditText  mTextPassword;
    Button    mBottonLOgin;
    TextView  mTextViewRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextUsername = ( EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText)  findViewById(R.id.edittext_password);
        mBottonLOgin  = (Button) findViewById(R.id.button_login);
        mTextViewRegister =(TextView)findViewById(R.id.edittextview_register);
        mTextViewRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent;
                Intent:registerIntent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(registerIntent);
            }
        });
    }
}
