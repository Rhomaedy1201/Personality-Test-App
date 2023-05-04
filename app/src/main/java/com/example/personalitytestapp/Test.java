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
    RadioButton sangatSuka, suka, netral, tidakSuka, sangatTidakSuka;
    int no = 0;
    public static int hasil, hasil_sangatSuka, hasil_suka, hasil_netral, hasil_tidakSuka, hasil_sangatTidakSuka;

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
        "Sangat Suka",
        "Suka",
        "Netral",
        "Tidak Suka",
        "Sangat Tidak Suka",
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

        sangatSuka = findViewById(R.id.rb_sangatSuka);
        suka = findViewById(R.id.rb_suka);
        netral = findViewById(R.id.rb_netral);
        tidakSuka = findViewById(R.id.rb_tidakSuka);
        sangatTidakSuka = findViewById(R.id.rb_sangatTidakSuka);

        question.setText(question_title[no]);
        sangatSuka.setText(answer[0]);
        suka.setText(answer[1]);
        netral.setText(answer[2]);
        tidakSuka.setText(answer[3]);
        sangatTidakSuka.setText(answer[4]);

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
    }

    private void back() {
    }

    private void tabFinish() {
        Intent intent = new Intent(getApplicationContext(), ResultTest.class);
        startActivity(intent);
    }

    public void next(View view){
        if (sangatSuka.isChecked() || suka.isChecked() || netral.isChecked() || tidakSuka.isChecked() || sangatTidakSuka.isChecked()){
            RadioButton jawaban_user = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            String ambil_jawaban_user = jawaban_user.getText().toString();

            // mengecek jawaban yang dipilih dan menambah nilai 1 pada salah satu pilihan
            if (ambil_jawaban_user.equalsIgnoreCase(answer[0])){
                hasil_sangatSuka++;
            }else if (ambil_jawaban_user.equalsIgnoreCase(answer[1])){
                hasil_suka++;
            }else if (ambil_jawaban_user.equalsIgnoreCase(answer[2])){
                hasil_netral++;
            }else if (ambil_jawaban_user.equalsIgnoreCase(answer[3])){
                hasil_tidakSuka++;
            }else if (ambil_jawaban_user.equalsIgnoreCase(answer[4])){
                hasil_sangatTidakSuka++;
            }
            rg.check(0);
            no++;

            // cek button finish jika udah terakhir maka enable
            if(no >= 9){
                finishTest = findViewById(R.id.btn_finish);
                finishTest.setEnabled(true);
            }

            // mengecek nilai jawaban dari soal
            System.out.println("Sangat Suka = " + hasil_sangatSuka);
            System.out.println("Suka = " + hasil_suka);
            System.out.println("Netral = " + hasil_netral);
            System.out.println("tidak Suka = " + hasil_tidakSuka);
            System.out.println("Sangat tidak suka = " + hasil_sangatTidakSuka);

            System.out.println("pilihan terbanyak adalah = " + Collections.max(Arrays.asList(hasil_sangatSuka, hasil_suka, hasil_netral, hasil_tidakSuka, hasil_sangatTidakSuka)));

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

                if (no == 9){
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