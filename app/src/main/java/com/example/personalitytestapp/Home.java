package com.example.personalitytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        test = findViewById(R.id.test_now);

        test.setOnClickListener(v -> {
            MulaiTest();
        });
    }

    private void MulaiTest() {
        Intent intent = new Intent(getApplicationContext(), Test.class);
        startActivity(intent);
    }
}