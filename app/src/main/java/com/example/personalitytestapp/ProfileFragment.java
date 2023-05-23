package com.example.personalitytestapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    View view;
    Button signout;
    private GoogleSignInClient mGoogleSigninClient;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    ImageView profileUser;
    TextView nameUser, emailUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);

        signout = view.findViewById(R.id.logout);
        profileUser = view.findViewById(R.id.profile_img);
        nameUser = view.findViewById(R.id.name_profile);
        emailUser = view.findViewById(R.id.email_profile);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null){
            String Name = firebaseUser.getDisplayName();
            String Email = firebaseUser.getEmail();
            Uri Photo = firebaseUser.getPhotoUrl();

            nameUser.setText(Name);
            emailUser.setText(Email);
            Picasso.get().load(Photo).into(profileUser);
        }else {
            System.out.println("Data Kosong");
        }

        // Initialize sign in client
        mGoogleSignInClient = GoogleSignIn.getClient(ProfileFragment.this.getContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);

        signout.setOnClickListener(view -> {
            // Sign out from google
            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Check condition
                    if (task.isSuccessful()) {
                        // When task is successful sign out from firebase
                        mAuth.signOut();
                        // Display Toast
                        Toast.makeText(getContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                        // Finish activity
                        getActivity().finish();
                    }else{
                        System.out.println("Salah");
                    }
                }
            });
        });

        return view;
    }
}