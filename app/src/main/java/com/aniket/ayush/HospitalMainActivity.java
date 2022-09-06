package com.aniket.ayush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HospitalMainActivity extends AppCompatActivity {

    Button logout, fetch;
    RecyclerView recyclerView;
    ArrayList<Appointment> appointmentList;
    FirebaseAuth mFirebaseAuth;
    private static String JSON_URL = "https://sihmvp1.herokuapp.com/inquiry/";
    Adapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_main);

        logout = findViewById(R.id.hm_logout);
        fetch = findViewById(R.id.fetch_appointments);
        recyclerView = findViewById(R.id.appointments);
        appointmentList = new ArrayList<Appointment>();
        mFirebaseAuth = FirebaseAuth.getInstance();

        JSON_URL += mFirebaseAuth.getCurrentUser().getUid();
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extract_appointments();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HospitalMainActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    void extract_appointments(){

        appointmentList.clear();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject appointmentObject = response.getJSONObject(i);

                        Appointment appointment = new Appointment();
                        appointment.setMessage(appointmentObject.getString("message"));
                        appointment.setPatient_name(appointmentObject.getString("patient_name"));
                        appointment.setPatient_symptoms(appointmentObject.getString("patient_symptoms"));
                        appointment.setPatient_number(appointmentObject.getString("patient_number"));
                        appointment.setHospitalID(appointmentObject.getString("hospitalID"));
                        appointmentList.add(appointment);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

//                System.out.println("yaha pe print hoga " + hospitals.size());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter2(getApplicationContext(),appointmentList, queue);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }
}