package com.aniket.ayush;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class HospitalList extends AppCompatActivity {

    LinearLayout recyclerView;
    List<Hospital> hospitals;
    private static String JSON_URL = "https://sihmvp1.herokuapp.com/admin/hospitals";
//    Adapter3 adapter;
    boolean isLoading = false;
    CardView one, two, three, four, five;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        RecyclerView courseRV = findViewById(R.id.idRVCourse);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Hospital> hospitals = (ArrayList<Hospital>) args.getSerializable("ARRAYLIST");

//        System.out.println("naya list hai "+object.size());

        Adapter3 courseAdapter = new Adapter3(this, hospitals);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);
//        recyclerView = findViewById(R.id.recycle);
//
//        one =findViewById(R.id.cardView1);
//        two =findViewById(R.id.cardView2);
//        three =findViewById(R.id.cardView3);
//        four =findViewById(R.id.cardView4);
//        five =findViewById(R.id.cardView5);
//
//
//        one.setOnClickListener(new View.OnClickListener() {
//                                   @Override
//                                   public void onClick(View view) {
//                                       final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                                               HospitalList.this, R.style.BottomSheetDialogTheme);
//                                       View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                                               .inflate(R.layout.layout_bottom_sheet,
//                                                       (LinearLayout) findViewById(R.id.bottomSheetContainer));
//                                       bottomSheetView.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
//                                           @Override
//                                           public void onClick(View view) {
//                                               Toast.makeText(HospitalList.this, "Shared!!!", Toast.LENGTH_SHORT).show();
//                                               bottomSheetDialog.dismiss();
//                                           }
//                                       });
//
//                                       // Following three methods have
//                                       // been implemented in this class.
//                                   }
//                               });
//
//
//        two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                            HospitalList.this, R.style.BottomSheetDialogTheme);
//                    View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                            .inflate(R.layout.layout_bottom_sheet,
//                                    (LinearLayout) findViewById(R.id.bottomSheetContainer));
//                    bottomSheetView.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Toast.makeText(HospitalList.this, "Shared!!!", Toast.LENGTH_SHORT).show();
//                            bottomSheetDialog.dismiss();
//                        }
//                    });
//            }
//        });
//
//        three.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                        HospitalList.this, R.style.BottomSheetDialogTheme);
//                View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                        .inflate(R.layout.layout_bottom_sheet,
//                                (LinearLayout) findViewById(R.id.bottomSheetContainer));
//                bottomSheetView.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(HospitalList.this, "Shared!!!", Toast.LENGTH_SHORT).show();
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//            }
//        });

//    private void extractHospitals() {
//
//        hospitals.clear();
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject hospitalObject = response.getJSONObject(i);
//
//                        Hospital hospital = new Hospital();
//                        hospital.setName(hospitalObject.getString("name"));
//                        hospital.setId(hospitalObject.getString("hospitalID"));
//                        hospital.setAddress(hospitalObject.getString("address"));
//                        hospital.setCity(hospitalObject.getString("city"));
//                        hospital.setState(hospitalObject.getString("state"));
//                        hospital.setC_time(hospitalObject.getString("opening_time"));
//                        hospital.setO_time(hospitalObject.getString("closing_time"));
//
//                        hospital.setReg_number(hospitalObject.getString("registration_number"));
//                        hospital.setDocumentId(hospitalObject.getString("documentID"));
//                        hospitals.add(hospital);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(HospitalList.this, e.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
////                System.out.println("yaha pe print hoga " + hospitals.size());
////                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                adapter = new Adapter3(getApplicationContext(), hospitals, queue) {
//                    @Override
//                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//                    }
//
//                    @Override
//                    public int getItemCount() {
//                        return 0;
//                    }
//                };
//                recyclerView.setAdapter(adapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("tag", "onErrorResponse: " + error.getMessage());
//            }
//        });
//
//        queue.add(jsonArrayRequest);
    }

//    public  void go(View v){
//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                HospitalList.this, R.style.BottomSheetDialogTheme);
//        View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                .inflate(R.layout.layout_bottom_sheet,
//                        (LinearLayout) findViewById(R.id.bottomSheetContainer));
//        bottomSheetView.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(HospitalList.this, "Shared!!!", Toast.LENGTH_SHORT).show();
//                bottomSheetDialog.dismiss();
//            }
//        });
//    }

}

