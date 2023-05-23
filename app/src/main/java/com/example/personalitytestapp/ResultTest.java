package com.example.personalitytestapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.common.util.ArrayUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultTest extends AppCompatActivity {

    PieChart pieChart;
    TextView personality1, personality2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_test);

        personality1 = findViewById(R.id.typePersonality1);
        personality2 = findViewById(R.id.typePersonality2);

        personality1.setText(Test.hasil_kepribadaian);
        personality2.setText(Test.hasil_kepribadaian);

        pieChart = findViewById(R.id.pieChart_view);
        showPieChart();
    }

    private void tabBackHome() {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }

    private void showPieChart(){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "";

        int introvert = (Test.pilih_introvert * 100) / 40;
        int extrovert = (Test.pilih_extrovert * 100) / 40;



        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Introvert", introvert);
        typeAmountMap.put("Extrovert", extrovert);

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#009CD7"));
        colors.add(Color.parseColor("#FF7F50"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(15f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setValueFormatter(new MyValueFormatter(new DecimalFormat("###,###,###"), pieChart));
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

}