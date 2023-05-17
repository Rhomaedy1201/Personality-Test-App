package com.example.personalitytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class Test extends AppCompatActivity {

    private Button finishTest, btnNext, btnBack;
    TextView question, noSoal, jmlSoal;
    RadioGroup rg;
    RadioButton iya, tidak;
    int no = 0;
    public static int hasil, pilihan_iya, pilihan_tidak;
    RadioButton jawaban_user;
    String ambil_jawaban_user;

//    question title
    String[] question_title = new String[]{
            "Uji kualitas suku cadang sebelum pengiriman",
        "Mempelajari struktur tubuh manusia",
        "Melakukan paduan suara musik",
        "Berikan bimbingan karir kepada orang-orang",
        "Menjual waralaba restoran kepada individu",
        "Hasilkan cek gaji bulanan untuk kantor",
        "Letakkan bata atau ubin",
        "Pelajari perilaku hewan",
        "Arahkan permainan",
        "Lakukan pekerjaan sukarela di organisasi nirlaba",
    };
//    answer
    String[] answer = new String[]{
        "Iya",
        "Tidak",
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        question = findViewById(R.id.tvQuestion);
        rg = findViewById(R.id.rgAnswer);
        noSoal = findViewById(R.id.no_soal);
        jmlSoal = findViewById(R.id.jumlah_soal);

        noSoal.setText("" + (no + 1 ));
        jmlSoal.setText("/" + question_title.length);

        iya = findViewById(R.id.rb_iya);
        tidak = findViewById(R.id.rb_tidak);

        question.setText(question_title[no]);
        iya.setText(answer[0]);
        tidak.setText(answer[1]);

        rg.check(0);

        if (no == 0){
            btnBack = findViewById(R.id.btn_back);
            btnBack.setEnabled(false);
        }
        if(no < 8){
            finishTest = findViewById(R.id.btn_finish);
            finishTest.setEnabled(false);
        }

        finishTest = findViewById(R.id.btn_finish);
        finishTest.setOnClickListener(v -> {
            tabFinish();
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
        if (ambil_jawaban_user == "Iya"){
//            iya.setChecked(true);
            System.out.println("Iyaaaaaa");
        }else {
//            iya.setChecked(false);
//            tidak.setChecked(true);
            System.out.println("Tidakkkkk");
        }
        rg.check(0);
        if(no <= question_title.length){
            // mengecek soal selanjutnya sesuai dengan nomer
            question.setText(question_title[no]);
            noSoal.setText(""+ (no + 1));

            // pengecekan tombol kembali/back
            if (no == 0){
                btnBack = findViewById(R.id.btn_back);
                btnBack.setEnabled(false);
            }else{
                btnBack = findViewById(R.id.btn_back);
                btnBack.setEnabled(true);
            }

            if (no == question_title.length){
                btnNext = findViewById(R.id.btn_next);
                btnNext.setEnabled(false);
            }else{
                btnNext = findViewById(R.id.btn_next);
                btnNext.setEnabled(true);
            }
        }else{
            finishTest.setEnabled(true);
        }
    }

    private void tabFinish() {
        Intent intent = new Intent(getApplicationContext(), ResultTest.class);
        startActivity(intent);
        finish();
    }

    public void next(View view){
        if (iya.isChecked() || tidak.isChecked()){
            jawaban_user = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            ambil_jawaban_user = jawaban_user.getText().toString();

            // mengecek jawaban yang dipilih dan menambah nilai 1 pada salah satu pilihan
            if (ambil_jawaban_user.equalsIgnoreCase(answer[0])){
                pilihan_iya++;
            }else if (ambil_jawaban_user.equalsIgnoreCase(answer[1])){
                pilihan_tidak++;
            }
            rg.check(0);
            no++;

            // cek button finish jika udah terakhir maka enable
            if(no >= 9){
                finishTest = findViewById(R.id.btn_finish);
                finishTest.setEnabled(true);
            }

            // mengecek nilai jawaban dari soal
            System.out.println("Sangat Suka = " + pilihan_iya);
            System.out.println("Suka = " + pilihan_tidak);

            System.out.println("pilihan terbanyak adalah = " + Collections.max(Arrays.asList(pilihan_iya,pilihan_tidak)));

            if(no <= question_title.length){
                // mengecek soal selanjutnya sesuai dengan nomer
                question.setText(question_title[no]);
                noSoal.setText(""+ (no + 1));

                // pengecekan tombol kembali/back
                if (no == 0){
                    btnBack = findViewById(R.id.btn_back);
                    btnBack.setEnabled(false);
                }else{
                    btnBack = findViewById(R.id.btn_back);
                    btnBack.setEnabled(true);
                }

                if (no == question_title.length){
                    btnNext = findViewById(R.id.btn_next);
                    btnNext.setEnabled(false);
                }else{
                    btnNext = findViewById(R.id.btn_next);
                    btnNext.setEnabled(true);
                }
            }else{
                finishTest.setEnabled(true);
            }
        }else{
            Toast.makeText(this, "Kamu jawab dulu", Toast.LENGTH_LONG).show();
        }
    }
}