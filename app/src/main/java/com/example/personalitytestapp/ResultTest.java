package com.example.personalitytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultTest extends AppCompatActivity {

    private Button backHome;
    private int currentProgress1 = 40;
    private int currentProgress2 = 30;
    private int currentProgress3 = 25;
    private int currentProgress4 = 12;
    private int currentProgress5 = 20;
    private int currentProgress6 = 1;
    private TextView count1;
    private TextView count2;
    private TextView count3;
    private TextView count4;
    private TextView count5;
    private TextView count6;
    private ProgressBar progressBar_realistis;
    private ProgressBar progressBar_penyelidikan;
    private ProgressBar progressBar_artistik;
    private ProgressBar progressBar_sosial;
    private ProgressBar progressBar_giat;
    private ProgressBar progressBar_konvensional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_test);

        count1 = findViewById(R.id.tv_count_realistis);
        count2 = findViewById(R.id.tv_count_penyelidikan);
        count3 = findViewById(R.id.tv_count_artistik);
        count4 = findViewById(R.id.tv_count_sosial);
        count5 = findViewById(R.id.tv_count_giat);
        count6 = findViewById(R.id.tv_count_konvensional);

        progressBar_realistis = findViewById(R.id.pb_realistis);
        progressBar_penyelidikan = findViewById(R.id.pb_penyelidikan);
        progressBar_artistik = findViewById(R.id.pb_artistik);
        progressBar_sosial = findViewById(R.id.pb_sosial);
        progressBar_giat = findViewById(R.id.pb_giat);
        progressBar_konvensional = findViewById(R.id.pb_konvensional);

        progressBar_realistis.setProgress(Test.hasil_sangatSuka);
        progressBar_penyelidikan.setProgress(Test.hasil_suka);
        progressBar_artistik.setProgress(Test.hasil_netral);
        progressBar_sosial.setProgress(Test.hasil_tidakSuka);
        progressBar_giat.setProgress(Test.hasil_sangatTidakSuka);
        progressBar_konvensional.setProgress(currentProgress6);

        progressBar_realistis.setMax(10);
        progressBar_penyelidikan.setMax(10);
        progressBar_artistik.setMax(10);
        progressBar_sosial.setMax(10);
        progressBar_giat.setMax(10);
        progressBar_konvensional.setMax(10);

        count1.setText("" + Test.hasil_sangatSuka);
        count2.setText("" + Test.hasil_suka);
        count3.setText("" + Test.hasil_netral);
        count4.setText("" + Test.hasil_tidakSuka);
        count5.setText("" + Test.hasil_sangatTidakSuka);
        count6.setText("" + currentProgress6);

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