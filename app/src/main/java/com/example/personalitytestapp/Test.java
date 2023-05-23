package com.example.personalitytestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test extends AppCompatActivity {

    private Button finishTest, btnNext, btnBack;
    TextView question, noSoal, jmlSoal;
    RadioGroup rg;
    RadioButton iya, tidak;
    int no = 0;
    public static int hasil, pilihan_iya, pilihan_tidak;
    public static int pilih_introvert, pilih_extrovert;
    RadioButton jawaban_user;
    String ambil_jawaban_user;
    public static String hasil_kepribadaian;

    //    question title
    String[] question_title = QuestionTest.question_test;
    //    answer
    String[] answer = new String[]{
            "Iya",
            "Tidak",
    };
    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        question = findViewById(R.id.tvQuestion);
        rg = findViewById(R.id.rgAnswer);
        noSoal = findViewById(R.id.no_soal);
        jmlSoal = findViewById(R.id.jumlah_soal);

        noSoal.setText("" + (no + 1));
        jmlSoal.setText("/" + question_title.length);

        iya = findViewById(R.id.rb_iya);
        tidak = findViewById(R.id.rb_tidak);


        question.setText(question_title[0]);

        iya.setText(answer[0]);
        tidak.setText(answer[1]);

        rg.check(0);

        if (no == 0) {
            btnBack = findViewById(R.id.btn_back);
            btnBack.setEnabled(false);
        }
        if (no <= question_title.length) {
            finishTest = findViewById(R.id.btn_finish);
            finishTest.setEnabled(false);
        }

        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        finishTest = findViewById(R.id.btn_finish);
        finishTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultTest.class);
                startActivity(intent);
                next();
                updateResultTest(hasil_kepribadaian);
                finish();
                finish();
                finish();
                System.out.println("Kepribadian anda adalah : " + hasil_kepribadaian);
            }
        });


        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            back();
        });

    }


    public void back() {
        iya = findViewById(R.id.rb_iya);
        tidak = findViewById(R.id.rb_tidak);
        no--;
        System.out.println("" + no);
        System.out.println("" + ambil_jawaban_user);
        if (ambil_jawaban_user == "Iya") {
//            iya.setChecked(true);
            System.out.println("Iyaaaaaa");
        } else {
//            iya.setChecked(false);
//            tidak.setChecked(true);
            System.out.println("Tidakkkkk");
        }
        rg.check(0);
        if (no <= question_title.length) {
            // mengecek soal selanjutnya sesuai dengan nomer
            question.setText(question_title[no]);
            noSoal.setText("" + (no + 1));

            if (no <= 39) {
                finishTest = findViewById(R.id.btn_finish);
                finishTest.setEnabled(false);
            }

            // pengecekan tombol kembali/back
            if (no == 0) {
                btnBack = findViewById(R.id.btn_back);
                btnBack.setEnabled(false);
            } else {
                btnBack = findViewById(R.id.btn_back);
                btnBack.setEnabled(true);
            }

            if (no == question_title.length) {
                btnNext = findViewById(R.id.btn_next);
                btnNext.setEnabled(false);
            } else {
                btnNext = findViewById(R.id.btn_next);
                btnNext.setEnabled(true);
            }
        } else {
            finishTest.setEnabled(true);
        }
    }

    public void next() {
        if (iya.isChecked() || tidak.isChecked()) {
            jawaban_user = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            ambil_jawaban_user = jawaban_user.getText().toString();

            // mengecek jawaban yang dipilih dan menambah nilai 1 pada salah satu pilihan
            if ((no % 2) == 0) {
                // Genap & Introvert
                if (ambil_jawaban_user.equalsIgnoreCase(answer[0])) {
                    pilih_introvert = pilih_introvert + 1;
                } else if (ambil_jawaban_user.equalsIgnoreCase(answer[1])) {
                    pilih_extrovert = pilih_extrovert + 1;
                }
            }
            else {
                // Ganjil % Extrovert
                if (ambil_jawaban_user.equalsIgnoreCase(answer[0])) {
                    pilih_extrovert = pilih_extrovert +1;
                } else if (ambil_jawaban_user.equalsIgnoreCase(answer[1])) {
                    pilih_introvert = pilih_introvert +1;
                }
            }

            rg.check(0);
            if (no >= 39){
            }else{
                no++;
            }

            System.out.println(no);
            if (Integer.valueOf(pilih_introvert) > Integer.valueOf(pilih_extrovert)){
                hasil_kepribadaian = "introvert";
            }else {
                hasil_kepribadaian = "extrovert";

            }
            System.out.println("KEP : " + hasil_kepribadaian);

            // mengecek nilai jawaban dari soal
            System.out.println("Introvert = " + pilih_introvert);
            System.out.println("Extrovert = " + pilih_extrovert);

            System.out.println("pilihan terbanyak adalah = " + Collections.max(Arrays.asList(pilih_introvert, pilih_extrovert)));

            if (no <= question_title.length) {
                // mengecek soal selanjutnya sesuai dengan nomer
                question.setText(question_title[no]);
                noSoal.setText("" + (no + 1));

                // cek button finish jika udah terakhir maka enable
                if (no >= 39) {
                    finishTest = findViewById(R.id.btn_finish);
                    finishTest.setEnabled(true);
                }

                // pengecekan tombol kembali/back
                if (no == 0) {
                    btnBack = findViewById(R.id.btn_back);
                    btnBack.setEnabled(false);
                } else {
                    btnBack = findViewById(R.id.btn_back);
                    btnBack.setEnabled(true);
                }

                if (no >= 39) {
                    btnNext = findViewById(R.id.btn_next);
                    btnNext.setEnabled(false);
                } else {
                    btnNext = findViewById(R.id.btn_next);
                    btnNext.setEnabled(true);
                }
            } else {
                finishTest.setEnabled(true);
            }
        } else {
            Toast.makeText(this, "Kamu jawab dulu", Toast.LENGTH_LONG).show();
        }
    }

    private void updateResultTest(String kepribadianAnda){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String user_uid = firebaseUser.getUid();

        Date date = new Date();
        CharSequence dt_now  = DateFormat.format("dd", date.getTime());
        CharSequence dt_now2  = DateFormat.format("yyyy-MM-", date.getTime());
        String tanggal  = String.valueOf(DateFormat.format("yyyy-MM-dd", date.getTime()));
        int tgl = Integer.parseInt(String.valueOf(dt_now));
        int exp = tgl + 5;
        String tglExpired = String.valueOf(dt_now2)+String.valueOf(exp);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, DataApi.api_put_result_test + user_uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Update Data Result Test Gagal!");
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("value_introvert", String.valueOf(pilih_introvert));
                params.put("value_extrovert", String.valueOf(pilih_extrovert));
                params.put("personality", kepribadianAnda);
                params.put("date", String.valueOf(tanggal));
                params.put("date_expired", tglExpired);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
