package com.aniket.ayush;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class DashBoard extends AppCompatActivity {

    Button logout;
    FirebaseAuth mFirebaseAuth;
    String getPinCode;

    ImageView img;
    // request code
    private final int PICK_IMAGE_REQUEST = 71;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    String services_api="https://sihmvp1.herokuapp.com/services/";
    TextView textViewSelectServices;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"dermatology","immunology","gastroenterology","hepatology","pharma","hiv","endocrinology",
    "asthma","cardiology","neurology","otolaryngology","paralysis","general","microbiology","pulmonology","pediatric",
    "proctology","orthopedy","ent","urology"};


    private EditText hospitalName, opening_time, closing_time;
    private Button  buttonGetDetails;
    Button uploadFile;
    Button uploadRegCert;
    Button uploadwardCert;
    private EditText affiliation, reg_num;
    private EditText address, email;
    private EditText phoneNumber;
    private EditText facilities;

    private EditText branch;
    private EditText website,specialites;
    private EditText no_of_doc;
    private EditText total_staff;
    private TextView lat;private TextView lng;
    StringBuilder stringBuilder;
    Bundle extras;

    private Button btnChoose, btnUpload, submit;

    TextView file_path;

    private Uri filePath;



    String pinCode, lati, longi;


    // creating a variable for request queue.
    private RequestQueue mRequestQueue;

    private String url = "https://sihmvp1.herokuapp.com/hospital";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        textViewSelectServices=(TextView)findViewById(R.id.textView);
        selectedLanguage = new boolean[langArray.length];
//        logout = findViewById(R.id.buttonFinal);
        mFirebaseAuth = FirebaseAuth.getInstance();


        //pinCode=pinCodeEdt.getText().toString();
        // getPinCode=pinCodeEdt.getText().toString();
        //buttonGetDetails = findViewById(R.id.buttonGetDetails);



        hospitalName=findViewById(R.id.registerFullName);
        opening_time = findViewById(R.id.opening_time);
        closing_time = findViewById(R.id.closing_time);


        uploadFile = (Button) findViewById(R.id.licenseCertiifcation);
        uploadwardCert= (Button) findViewById(R.id.wardReport);
        uploadRegCert = (Button) findViewById(R.id.registerationCertiifcation);
        submit = findViewById(R.id.registerButton);
        //file_path = (TextView)findViewById(R.id.file_path);
        email = findViewById(R.id.registerEmail);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        reg_num=findViewById(R.id.registerPhoneNumber);
        phoneNumber=findViewById(R.id.primaryContact);
        address=findViewById(R.id.address);
        specialites=findViewById(R.id.specialities);
        facilities=findViewById(R.id.facility);
        branch=findViewById(R.id.branch);
        website=findViewById(R.id.url);
        no_of_doc=findViewById(R.id.doctor_experts);
        total_staff=findViewById(R.id.doctor_experts);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();




        
//        Bundle extras = getIntent().getExtras();
//        lati = extras.getString("lat");
//        longi = extras.getString("long");
//
//        lat.setText(lati);
//        lng.setText(longi);
//
        extras = getIntent().getExtras();

//
//        lat.setText(lati);
//        lng.setText(longi);

        // initializing our request que variable with request
        // queue and passing our context to it.
        mRequestQueue = Volley.newRequestQueue(this);

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        uploadwardCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        uploadRegCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });



    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                uploadFile();
                uploadImage();
                add(view);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(DashBoard.this, MainActivity.class));
//                finish();
//            }
//        });




        textViewSelectServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                         stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                        textViewSelectServices.setText(stringBuilder.toString());
                    }
                });



                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            textViewSelectServices.setText("");
                        }
                    }
                });









                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });

                builder.show();









            }
        });




     



























            }

    public void add(View view) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);

//        EditText name = findViewById(R.id.names);
//        //EditText zipcode = findViewById(R.id.pinCode);
//        EditText state = findViewById(R.id.state);
//        EditText city = findViewById(R.id.city);
//        EditText registration_number = findViewById(R.id.reg2);
//        EditText phone_number = findViewById(R.id.phone);
//        EditText address1 = findViewById(R.id.adress);
//        EditText address2 = findViewById(R.id.adress2);


        lati = extras.getString("lat");
        longi = extras.getString("long");


//        JSONObject location = new JSONObject();
//        double coordinates[] = new double[2];
//        coordinates[0] = 19.0; //default values...change later to marker values
//        coordinates[1] = 72.0;
//        location.put("type","Point");
//        location.put("coordinates",coordinates);

        String id = mFirebaseAuth.getCurrentUser().getUid();

        services_api=services_api+id;












//
//        hospitalName=findViewById(R.id.registerFullName);
//        opening_time = findViewById(R.id.opening_time);
//        closing_time = findViewById(R.id.closing_time);
//
//
//        uploadFile = (Button) findViewById(R.id.licenseCertiifcation);
//        uploadwardCert= (Button) findViewById(R.id.wardReport);
//        uploadRegCert = (Button) findViewById(R.id.registerationCertiifcation);
//        submit = findViewById(R.id.registerButton);
//        //file_path = (TextView)findViewById(R.id.file_path);
//        email = findViewById(R.id.registerEmail);
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
//        reg_num=findViewById(R.id.registerPhoneNumber);
//        phoneNumber=findViewById(R.id.primaryContact);
//        address=findViewById(R.id.address);
//        specialites=findViewById(R.id.specialities);
//        facilities=findViewById(R.id.facility);
//        branch=findViewById(R.id.branch);
//        no_of_doc=findViewById(R.id.doctor_experts);
//        total_staff=findViewById(R.id.doctor_experts);



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hospitalID",id);
        jsonObject.put("name", hospitalName.getText().toString());
        //jsonObject.put("zipcode", Long.parseLong(zipcode.getText().toString()));
        jsonObject.put("opening_time", opening_time.getText().toString());
        jsonObject.put("closing_time", closing_time.getText().toString());
        jsonObject.put("registration_number", Long.parseLong(reg_num.getText().toString()));
        jsonObject.put("phone_number", Long.parseLong(phoneNumber.getText().toString()));
        jsonObject.put("address", address.getText().toString()) ;
        jsonObject.put("type", "hospital"); //type
        jsonObject.put("documentID", id);
        jsonObject.put("specialities", specialites.getText().toString());
        jsonObject.put("latitude", Double.parseDouble(lati));
        jsonObject.put("longitude", Double.parseDouble(longi));
        jsonObject.put("url", website.getText().toString());
        jsonObject.put("email", email.getText().toString());

        JSONObject jsonObjectservices = new JSONObject();
        jsonObjectservices.put("hospitalID",id);




        for(String s:langArray){
            if(stringBuilder.toString().contains(s)) {
                jsonObjectservices.put(s,true);
            }
        }


//        Background: There are approximately 4000 Ayush hospitals across India distributed under different council and hospitals
//        of the government of India. Objective: Ayush hospitals finder application using google Map API
//        which shows the location and nearby Ayush hospital with opening time and closing timing and by
//        integrating various *bio-medical data sources,*
//        containing information relevant to the hospital demographics, their inpatient procedure rates, Outpatient department etc



       // BSONTypeError: Argument passed in must be a string of 12 bytes or a string of 24 hex characters or an integer


        final String requestBody = jsonObject.toString();

        final String requestBodyServices=jsonObjectservices.toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                startActivity(new Intent(DashBoard.this, HospitalMainActivity.class));
                System.out.println("naya wala successful");
                finish();
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





        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, services_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                startActivity(new Intent(DashBoard.this, HospitalMainActivity.class));
                System.out.println("naya wala successful");
                finish();
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
                    return requestBodyServices == null ? null : requestBodyServices.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBodyServices, "utf-8");
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




        queue.add(stringRequest2);








    }



    private void chooseFile() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);
    }


    public void startMap(View view) {

        Intent i=new Intent(getApplicationContext(), MapsActivityHospital.class);
        //Toast.makeText(getApplicationContext(),pinCode,Toast.LENGTH_SHORT).show();
        //i.putExtra("message_key",pinCode);
        startActivityForResult(i,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
           // file_path.setText(filePath.getPath());
        }






//        protected void onActivityResult(int requestCode,
//        int resultCode,
//        Intent data)
//        {
//
//            super.onActivityResult(requestCode,
//                    resultCode,
//                    data);
//
//            // checking request code and result code
//            // if request code is PICK_IMAGE_REQUEST and
//            // resultCode is RESULT_OK
//            // then set image in the image view
//            if (requestCode == PICK_IMAGE_REQUEST
//                    && resultCode == RESULT_OK
//                    && data != null
//                    && data.getData() != null) {
//
//                // Get the Uri of data
//                filePath = data.getData();
//                try {
//
//                    // Setting image on image view using Bitmap
//                    Bitmap bitmap = MediaStore
//                            .Images
//                            .Media
//                            .getBitmap(
//                                    getContentResolver(),
//                                    filePath);
//                    img.setImageBitmap(bitmap);
//                }
//
//                catch (IOException e) {
//                    // Log the exception
//                    e.printStackTrace();
//                }
//            }
//        }
//



        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                if(resultCode == RESULT_OK) {

                    String userlat = data.getStringExtra("Latttitude");
                    String userlng=data.getStringExtra("Longitude");
                    Toast.makeText(getApplicationContext(),userlat+" "+userlng,Toast.LENGTH_SHORT).show();
                    lat.setText("hello");
                    lng.setText("hello");
                }
            }

    }



    private void uploadFile() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

            StorageReference ref = storageReference.child("Documents/"+user_id+"/"+ "file.pdf");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(DashBoard.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DashBoard.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }





    // initialing on click listener for our button.

//        buttonGetDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pinCode = pinCodeEdt.getText().toString();
//
//                if (TextUtils.isEmpty(pinCode)) {
//
//                } else {
//
//                    getDataFromPinCode(pinCode);
//                }
//            }
//        });








//
//    private void getDataFromPinCode(String pinCode) {
//
//
//        mRequestQueue.getCache().clear();
//
//        String url = "https://www.postalpincode.in/api/pincode/" + pinCode;
//
//        // below line is use to initialize our request queue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//
//
//        // in below line we are creating a
//        // object request using volley.
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                // inside this method we will get two methods
//                // such as on response method
//                // inside on response method we are extracting
//                // data from the json format.
//                try {
//                    // we are getting data of post office
//                    // in the form of JSON file.
//                    JSONArray postOfficeArray = response.getJSONArray("PostOffice");
//                    if (response.getString("Status").equals("Error")) {
//                        // validating if the response status is success or failure.
//                        // in this method the response status is having error and
//                        // we are setting text to TextView as invalid pincode.
//                        city.setText("Pin code is not valid.");
//                        state.setText("Pin code is not valid.");
//                    } else {
//                        // if the status is success we are calling this method
//                        // in which we are getting data from post office object
//                        // here we are calling first object of our json array.
//                        JSONObject obj = postOfficeArray.getJSONObject(0);
//
//                        // inside our json array we are getting district name,
//                        // state and country from our data.
//                        String district = obj.getString("District");
//                        String state1 = obj.getString("State");
//                        String country = obj.getString("Country");
//
//                        // after getting all data we are setting this data in
//                        // our text view on below line.
//                        city.setText(district);
//                        state.setText(state1);
//
//                    }
//                } catch (JSONException e) {
//                    // if we gets any error then it
//                    // will be printed in log cat.
//                    e.printStackTrace();
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // below method is called if we get
//                // any error while fetching data from API.
//                // below line is use to display an error message.
//                Log.i("info",error.toString());
//                Toast.makeText(getApplicationContext(), "Pin code is not valid.", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//        });
//        // below line is use for adding object
//        // request to our request queue.
//        queue.add(objectRequest);
//    }

    public void imageUpload(View view) {
SelectImage();

    }

    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }


    private void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                   // progressDialog.dismiss();
                                    Toast
                                            .makeText(getApplicationContext(),
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(getApplicationContext(),
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }

    }

}
