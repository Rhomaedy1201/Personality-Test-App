package com.example.personalitytestapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);

        signout = view.findViewById(R.id.logout);
        profileUser = view.findViewById(R.id.profile_img);
        nameUser = view.findViewById(R.id.name_profile);
        emailUser = view.findViewById(R.id.email_profile);
        progressBar = view.findViewById(R.id.progressBar_profile);
        progressBar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null){
            progressBar.setVisibility(View.GONE);
            String Name = firebaseUser.getDisplayName();
            String Email = firebaseUser.getEmail();
            Uri Photo = firebaseUser.getPhotoUrl();

            System.out.println(Photo);
            nameUser.setText(Name);
            emailUser.setText(Email);
            Picasso.get().load(Photo).into(profileUser);
        }else {
            progressBar.setVisibility(View.VISIBLE);
            System.out.println("Data Kosong");
        }

        // Initialize sign in client
        mGoogleSignInClient = GoogleSignIn.getClient(ProfileFragment.this.getContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void showDialog(){
        ConstraintLayout constraintLayout = view.findViewById(R.id.dialog_forLogoutLayout);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_logout, constraintLayout);
        Button btnLogout = view1.findViewById(R.id.keluar_logout);
        Button btnBatal = view1.findViewById(R.id.batal_logout);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view1);
        final AlertDialog alertDialog = builder.create();

        btnLogout.findViewById(R.id.keluar_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Check condition
                        if (task.isSuccessful()) {
                            // When task is successful sign out from firebase
                            mAuth.signOut();
                            // Display Toast
                            Toast.makeText(getContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                            // Finish activity
                            alertDialog.dismiss();
                            startActivity(new Intent(getActivity(), Login.class));
                            getActivity().finish();
                            getActivity().finish();
                        }else{
                            System.out.println("Salah");
                        }
                    }
                });

            }
        });
        btnBatal.findViewById(R.id.batal_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}