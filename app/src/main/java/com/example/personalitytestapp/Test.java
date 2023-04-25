package com.example.personalitytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Test extends AppCompatActivity {

    private Button finishTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        finishTest = findViewById(R.id.btn_finish);
        finishTest.setOnClickListener(v -> {
            tabFinish();
        });
    }

    private void tabFinish() {
        Intent intent = new Intent(getApplicationContext(), ResultTest.class);
        startActivity(intent);
    }
}