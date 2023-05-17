package com.example.personalitytestapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.Arrays;
import java.util.List;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_test);

        count1 = findViewById(R.id.tv_count_realistis);
        count2 = findViewById(R.id.tv_count_penyelidikan);

        progressBar_realistis = findViewById(R.id.pb_realistis);
        progressBar_penyelidikan = findViewById(R.id.pb_penyelidikan);

        progressBar_realistis.setProgress(Test.pilihan_iya);
        progressBar_penyelidikan.setProgress(Test.pilihan_tidak);

        progressBar_realistis.setMax(10);
        progressBar_penyelidikan.setMax(10);

//        int[] a = {Test.pilihan_iya, Test.pilihan_tidak};
//        int max = Arrays.stream(a).max().getAsInt();

        count1.setText("" + Test.pilihan_iya);
        count2.setText("" + Test.pilihan_tidak);
//        count6.setText("" + max);

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