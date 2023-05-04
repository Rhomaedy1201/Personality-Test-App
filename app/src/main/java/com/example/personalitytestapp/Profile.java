package com.example.personalitytestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    Button signout;
    private GoogleSignInClient mGoogleSigninClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        signout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
//                editor.clear();
//                editor.apply();

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, Login.class));
                finish();
            }
        });
    }


}