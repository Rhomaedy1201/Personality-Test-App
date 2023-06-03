package com.example.personalitytestapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    View view;
    Button test;
    TextView nameUser;
    ImageView profileUser;
    LinearLayout goProfile;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    private FirebaseAuth mAuth;

    private String cekResultTest;
    private static final String TAG = "GoogleActivity";
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        test = view.findViewById(R.id.test_now);
        nameUser = view.findViewById(R.id.name);
        profileUser = view.findViewById(R.id.profile);
        goProfile = view.findViewById(R.id.toProfile);
        progressBar = view.findViewById(R.id.progressBar_home);

//        progressBar.setVisibility(View.VISIBLE);

        requestQueue = Volley.newRequestQueue(this.getContext());
        getApiResultTest();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("56816710394-pc0gv3u0at5vdkn762bh7umefj0te2jm.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this.getContext(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this.getContext());

        if (account != null){
            progressBar.setVisibility(View.GONE);
            String Name = account.getDisplayName();
            Uri Photo = account.getPhotoUrl();

            nameUser.setText(Name);
            Picasso.get().load(Photo).into(profileUser);
        }else {
            progressBar.setVisibility(View.VISIBLE);
            System.out.println("Data Kosong");
        }

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekResultTest.equalsIgnoreCase("ada")){
                    showDialog();
                }else{
                    Intent intent = new Intent(getContext(), Test.class);
                    startActivity(intent);
                    HomeFragment.this.getActivity().finish();
                }
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

    private void loadFragment(Fragment fragment){
        // create a fragmentManager
        FragmentManager fm = getFragmentManager();
        //create FragmentTransaction to begin the transaction end replace the fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.relativelayout, fragment);
        fragmentTransaction.commit();
    }

    private void getApiResultTest(){
        // Get api
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, DataApi.api_result_test + firebaseUser.getUid(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // get json
                            JSONObject employee = response.getJSONObject("data");
                            if (employee.getString("google_id").equalsIgnoreCase("kosong") || employee.getString("date").equalsIgnoreCase("null")){
                                System.out.println("data kosong");
                                cekResultTest = "kosong";
                            }else{
                                cekResultTest = "ada";
                                //get date
                                Date date = new Date();
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
                                    postApiUidResultTest();
                                }else {
                                    System.out.println("Hasil Test TETAP");
                                }
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

    private void showDialog(){
        ConstraintLayout constraintLayout = view.findViewById(R.id.dialog_forHome);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_home_warning, constraintLayout);
        Button btnOke = view1.findViewById(R.id.oke);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view1);
        final AlertDialog alertDialog = builder.create();

        btnOke.findViewById(R.id.oke).setOnClickListener(new View.OnClickListener() {
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

    private void postApiUidResultTest(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DataApi.api_result_test, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "POS Data Result Test Gagal!");
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("google_id", firebaseUser.getUid());
                params.put("value_introvert", "");
                params.put("value_extrovert", "");
                params.put("personality", "");
                params.put("date", "");
                params.put("date_expire", "");
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