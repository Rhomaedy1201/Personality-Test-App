package com.example.personalitytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login_google);

        login.setOnClickListener(v -> {
            LoginGoogle();
        });
    }

    private void LoginGoogle() {
        Intent log = new Intent(getApplicationContext(), Home.class);
        startActivity(log);
    }
}