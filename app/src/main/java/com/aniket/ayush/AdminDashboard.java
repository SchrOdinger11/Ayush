package com.aniket.ayush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Hospital> hospitals;
    private static String JSON_URL = "https://sihmvp1.herokuapp.com/admin/hospitals";
    Adapter adapter;

    Button logout, fetch, accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        recyclerView = findViewById(R.id.hospitalList);
        logout = findViewById(R.id.admin_logout);
        fetch = findViewById(R.id.fetch_requests);
        accept = findViewById(R.id.btn_accept);
        hospitals = new ArrayList<>();

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extractHospitals();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminDashboard.this, MainActivity.class));
                finish();
            }
        });


    }



    private void extractHospitals() {

        hospitals.clear();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject hospitalObject = response.getJSONObject(i);

                        Hospital hospital = new Hospital();
                        hospital.setName(hospitalObject.getString("name"));
                        hospital.setId(hospitalObject.getString("hospitalID"));
                        hospital.setAddress(hospitalObject.getString("address"));

                        hospital.setC_time(hospitalObject.getString("opening_time"));
                        hospital.setO_time(hospitalObject.getString("closing_time"));

                        hospital.setReg_number(hospitalObject.getString("registration_number"));
                        hospital.setDocumentId(hospitalObject.getString("documentID"));

                        hospital.setUrl(hospitalObject.getString("url"));
                        hospital.setSpecialities(hospitalObject.getString("specialities"));
                        hospital.setEmail(hospitalObject.getString("email"));

                        JSONArray t = (JSONArray) hospitalObject.get("phone_number");
                        String x = t.getString(0);
                        //Toast.makeText(AdminDashboard.this, x, Toast.LENGTH_SHORT).show();
                        hospital.setPh_numbers(x);
                        hospitals.add(hospital);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(AdminDashboard.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

//                System.out.println("yaha pe print hoga " + hospitals.size());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(),hospitals, queue);
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