package com.aniket.ayush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class UserForm extends AppCompatActivity {


    String hospitalid;
    EditText name, message, symptoms, number;
    FirebaseAuth mFirebaseAuth;
    String url = "https://sihmvp1.herokuapp.com/user/inquiry";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        Intent i=getIntent();
        hospitalid = i.getExtras().getString("hospitalId");

        name = findViewById(R.id.editTextTextPersonName2);
        number = findViewById(R.id.editTextPhone);
        message = findViewById(R.id.editTextTextPersonName3);
        symptoms = findViewById(R.id.editTextTextPersonName4);
//        mFirebaseAuth = FirebaseAuth.getInstance();


    }

    public void add() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);


//        JSONObject location = new JSONObject();
//        double coordinates[] = new double[2];
//        coordinates[0] = 19.0; //default values...change later to marker values
//        coordinates[1] = 72.0;
//        location.put("type","Point");
//        location.put("coordinates",coordinates);

        //String id = mFirebaseAuth.getCurrentUser().getUid();

//        services_api = services_api + id;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("patient_name", name.getText().toString());
        jsonObject.put("patient_number", Long.parseLong(number.getText().toString()));
        //jsonObject.put("zipcode", Long.parseLong(zipcode.getText().toString()));
        jsonObject.put("message", message.getText().toString());
        jsonObject.put("patient_symptoms", symptoms.getText().toString());
        jsonObject.put("hospitalID", hospitalid);






//        Background: There are approximately 4000 Ayush hospitals across India distributed under different council and hospitals
//        of the government of India. Objective: Ayush hospitals finder application using google Map API
//        which shows the location and nearby Ayush hospital with opening time and closing timing and by
//        integrating various *bio-medical data sources,*
//        containing information relevant to the hospital demographics, their inpatient procedure rates, Outpatient department etc



        // BSONTypeError: Argument passed in must be a string of 12 bytes or a string of 24 hex characters or an integer


        final String requestBody = jsonObject.toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.i("VOLLEY", response);
//                startActivity(new Intent(UserForm.this, HospitalMainActivity.class));
//                System.out.println("naya wala successful");
//                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("naya wala error");
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        queue.add(stringRequest);


    }

    public void submitForm(View view) throws JSONException {
        add();
        Toast.makeText(getApplicationContext(), "UserActivity", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(UserForm.this, UserMainActivity.class));
        finish();

    }
}