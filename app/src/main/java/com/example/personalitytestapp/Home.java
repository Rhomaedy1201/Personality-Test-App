package com.example.personalitytestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {

    Button test;
    TextView nameUser;
    ImageView profileUser;
    private GoogleSignInClient mGoogleSignInClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        test = findViewById(R.id.test_now);
        nameUser =findViewById(R.id.name);
        profileUser = findViewById(R.id.profile);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");

        if (account != null){
            String Name = account.getDisplayName();
            Uri Photo = account.getPhotoUrl();

            nameUser.setText(Name);
            Picasso.get().load(Photo).into(profileUser);
        }else {
            System.out.println("Data Kosong");
        }

        test.setOnClickListener(v -> {
            MulaiTest();
        });

    }

    private void MulaiTest() {
        Intent intent = new Intent(getApplicationContext(), Test.class);
        startActivity(intent);
    }

    public void display_profile(View view){
        Intent profile = new Intent(getApplicationContext(), Profile.class);
        startActivity(profile);
    }
}