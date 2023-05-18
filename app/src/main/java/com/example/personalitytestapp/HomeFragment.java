package com.example.personalitytestapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {
    View view;
    Button test;
    TextView nameUser;
    ImageView profileUser;
    LinearLayout goProfile;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        test = view.findViewById(R.id.test_now);
        nameUser = view.findViewById(R.id.name);
        profileUser = view.findViewById(R.id.profile);
        goProfile = view.findViewById(R.id.toProfile);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("56816710394-pc0gv3u0at5vdkn762bh7umefj0te2jm.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this.getContext(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this.getContext());

        if (account != null){
            String Name = account.getDisplayName();
            Uri Photo = account.getPhotoUrl();

            nameUser.setText(Name);
            Picasso.get().load(Photo).into(profileUser);
        }else {
            System.out.println("Data Kosong");
        }

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Test.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ProfileFragment());
            }
        });
        return view;
    }

    private void MulaiTest() {
        Intent intent = new Intent(getActivity(), Test.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void loadFragment(Fragment fragment){
        // create a fragmentManager
        FragmentManager fm = getFragmentManager();
        //create FragmentTransaction to begin the transaction end replace the fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.relativelayout, fragment);
        fragmentTransaction.commit();
    }
}