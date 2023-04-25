package com.example.personalitytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ResultTest extends AppCompatActivity {

    private Button backHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_test);
        backHome = findViewById(R.id.btn_backHome);
        backHome.setOnClickListener(v -> {
            tabBackHome();
        });
    }

    private void tabBackHome() {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }

}