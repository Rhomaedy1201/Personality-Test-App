package com.example.personalitytestapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResultTestFragment extends Fragment {

    View view;
    PieChart pieChart;
    RequestQueue requestQueue;
    private FirebaseAuth mAuth;
    TextView personality, personality2, descPersonality;
    ProgressBar progressBar;
    Date date;
    String tanggall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_result_test, container, false);
        pieChart = view.findViewById(R.id.pieChart_view);
        personality = view.findViewById(R.id.personalityType);
        personality2 = view.findViewById(R.id.personalityFrag);
        descPersonality = view.findViewById(R.id.descPersonalityFrag);

        progressBar = view.findViewById(R.id.progressBar_resultTestFrag);

        requestQueue = Volley.newRequestQueue(this.getContext());
        showPieChart();

//        if (tanggal.equalsIgnoreCase("2023-05-24")){
//            System.out.println("Delete Hasil Test");
//        }else {
//            System.out.println("Hasil Test TETAP");
//        }

        return view;
    }


    private void showPieChart(){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "";

        // Get api
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, DataApi.api_result_test + firebaseUser.getUid(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.setVisibility(View.VISIBLE);
                            // get json
                            JSONObject employee = response.getJSONObject("data");
                            if (employee.getString("google_id").equalsIgnoreCase("kosong") || employee.getString("date").equalsIgnoreCase("null")){
                                System.out.println("data kosong");
                            }else{
                                progressBar.setVisibility(View.GONE);

                                //get date
                                date = new Date();
                                CharSequence dt_now  = DateFormat.format("dd", date.getTime());
                                int tanggal = Integer.valueOf((String) dt_now);
                                String dFirst = String.valueOf(employee.getString("date_expired").charAt(employee.getString("date_expired").length() - 2));
                                String dLast = String.valueOf(employee.getString("date_expired").charAt(employee.getString("date_expired").length() - 1));
                                int dt = Integer.parseInt(dFirst+dLast);
                                System.out.println(dt);

                                // Delete data result text
                                if (tanggal >= dt){
                                    System.out.println("Delete Hasil Test");
                                    DeleteResultTest();
                                }else {
                                    System.out.println("Hasil Test TETAP");
                                }

                                // Pie Chart
                                personality.setText(employee.getString("personality"));
                                personality2.setText(employee.getString("personality"));

                                if (employee.getString("personality").equalsIgnoreCase("introvert")){
                                    descPersonality.setText(PersonalityDescription.introvert);
                                }else {
                                    descPersonality.setText(PersonalityDescription.extrovert);
                                }
                                int introvert = (Integer.valueOf(employee.getString("value_introvert")) * 100) / 40;
                                int extrovert = (Integer.valueOf(employee.getString("value_extrovert")) * 100) / 40;

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

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        requestQueue.add(objectRequest);

    }

    private void DeleteResultTest(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.DELETE, DataApi.api_result_test + firebaseUser.getUid(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.setVisibility(View.VISIBLE);
//                            JSONObject employee = response.getJSONObject("data");
                            System.out.println(response.getString("status"));
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                            Toast.makeText(getContext(), response.getString("status"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        requestQueue.add(objectRequest);
    }
}