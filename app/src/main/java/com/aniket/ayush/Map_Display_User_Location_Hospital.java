package com.aniket.ayush;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map_Display_User_Location_Hospital extends AppCompatActivity {


    SupportMapFragment supportMapFragment;
    ImageButton locationsearch;
    Button userbook ;
    Button ListV;
    ImageButton SearchLocation;
    private static  final int RESULT_SPEECH=1;
    FusedLocationProviderClient client;

        EditText locationsearchfield ;
    ArrayList<LatLng> CoordinatesFromList1 = new ArrayList<>();
    ArrayList<Hospital> hospitalArrayList = new ArrayList<>();


    List<LatLng> hospitals=new ArrayList<>();
    //private static String JSON_URL = "https://sihmvp1.herokuapp.com/admin/hospitals";


    LatLng latLng;
    //19.17,72.87
int f=0;
    String symptom1;
    String symptom2;
    String symptom3;
    String distance;
    String flag;
    String global_hospital_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display_user_location_hospital);
        SearchLocation= (ImageButton) findViewById(R.id.speechBtn);
        locationsearchfield=(EditText)findViewById(R.id.locationsearch) ;

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(Map_Display_User_Location_Hospital.this);
        userbook = findViewById(R.id.userbook);

        ListV=findViewById(R.id.button6);
        //button
        locationsearch=(ImageButton) findViewById(R.id.searchbutton);
        ListV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Map_Display_User_Location_Hospital.this, HospitalList.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)hospitalArrayList);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });

        if (ActivityCompat.checkSelfPermission(Map_Display_User_Location_Hospital.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(Map_Display_User_Location_Hospital.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

//



//
//        Bundle extras=getIntent().getExtras();
//        symptom1=extras.getString("Spinner1");
//        symptom2=extras.getString("Spinner2");
//        symptom3=extras.getString("Spinner3");



        symptom1=getIntent().getExtras().getString("Spinner1");
        symptom2=getIntent().getExtras().getString("Spinner2");
        symptom3=getIntent().getExtras().getString("Spinner3");
        distance = getIntent().getExtras().getString("Spinner4");
        flag=getIntent().getExtras().getString("flag");


//        userbook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(global_hospital_id.equals("")){
//                    Toast.makeText(getApplicationContext(),"Select Hospital",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "ClickedUserbook  "+global_hospital_id, Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(getApplicationContext(), UserForm.class);
//                    i.putExtra("hospitalId", global_hospital_id);
//                    //startActivity(i);
//                }
//            }
//        });
        //Toast.makeText(getApplicationContext(), symptom1+" "+symptom2+" "+symptom3, Toast.LENGTH_SHORT).show();

    }


    private void getCurrentLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling


            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                //when success
                 //Toast.makeText(getApplicationContext(),location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_SHORT).show();
                if (location != null) {

                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {

                            ArrayList<LatLng> CoordinatesFromList = new ArrayList<>();
//                            CoordinatesFromList.add(new LatLng(19.178105, 72.876765));
//                            CoordinatesFromList.add(new LatLng(20.178105, 75.876765));
//                            CoordinatesFromList.add(new LatLng(21.009105, 75.876765));
                           // final ArrayList<LatLng>[] CoordinatesFromList1 = new ArrayList<LatLng>[1];
                            latLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());

//
//                            String Userlat = "19.24702301340382";
//                            String Userlong = "72.84990167187982";
                            //Toast.makeText(Map_Display_User_Location_Hospital.this, latLng.latitude+" "+latLng.longitude, Toast.LENGTH_SHORT).show();
                            String Userlat=String.valueOf(latLng.latitude);
                            String Userlong=String.valueOf(latLng.longitude);
                            String url;
                            List<String> hospitalName=new ArrayList<String>();
                            url = "https://sihmvp1.herokuapp.com" +
                                    "/hospital/" + Userlat + "/" + Userlong +"/"+ distance + "/"+symptom1+"%2C"+symptom2+"%2C"+symptom3;

// lat/long

                            if(flag.equals("true")) {
                                url = "https://sihmvp1.herokuapp.com/hospital/"+Userlat+"/"+Userlong + "/" + distance;

                            }
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



                            List<String> hospitalidlist=new ArrayList<String>();
                            HashMap<Marker,String> hospital_hash_map=new HashMap<Marker,String>();

                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {

                                    for (int i = flag.equals("true")?0:1; i < response.length(); i++) {
                                        try {

                                            JSONObject hospitalObject = response.getJSONObject(i);
                                            JSONObject location = (JSONObject) hospitalObject.get("location");
                                            String hospitalId_= (String) hospitalObject.get("hospitalID");
                                            hospitalidlist.add(hospitalId_);

                                            hospitalName.add(hospitalObject.getString("name"));
                                            JSONArray coordinates = (JSONArray) location.get("coordinates");
                                            LatLng hospitalcoordinates = new LatLng((double) coordinates.get(1),(double) coordinates.get(0));



                                            CoordinatesFromList.add(hospitalcoordinates);

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

                                            hospitalArrayList.add(hospital);




                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }


                                    }
                                    //CoordinatesFromList1[0] =CoordinatesFromList;

                                    CoordinatesFromList1 = CoordinatesFromList;



                                    //create marker option
                                    MarkerOptions options = new MarkerOptions().position(latLng)
                                            .title("I am here");


                                    //ADD MARKER ON MAP
                                    googleMap.addMarker(options);
                                    googleMap.addCircle(new CircleOptions()
                                            .center(latLng)
                                            .radius(Integer.parseInt(distance)) //500km
                                            .strokeColor(-65536)
                                            .fillColor(0x220000ff)
                                            .strokeWidth(5.0f));

                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    //zoom map
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));


                                //testing to put in loop


                                   // this is commented
//                                    InfoWindowAdapter markerInfoWindowAdapter = new InfoWindowAdapter(Map_Display_User_Location_Hospital.this);
//                                    googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
//                                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                                        @Override
//                                        public boolean onMarkerClick(@NonNull Marker marker) {
//
//                                            //Button userbook2=(Button) findViewById(R.id.userbook);
//
//                                            Marker temp=googleMap.addMarker(options);
//                                            temp.showInfoWindow();
//                                           // userbook2.setVisibility(View.VISIBLE);
//                                            return false;
//                                        }
//                                    });


//

                                    // Setting a custom info window adapter for the google map



                                    int n = CoordinatesFromList.size();

                                    int x = 0;

                                    for (int i = 0; i < n; i++) {

                                        String var = "Ayurveda";
                                        LatLng temp = CoordinatesFromList.get(i);

                                    if(f%4==0){
                                        var="Unnani";
                                        f++;
                                    }
                                    else if(f%4==1){
                                        var="Unnani Ayurveda";f++;

                                    }
                                    else if(f%4==2){
                                        var="Siddha Sowa";f++;

                                    }
                                    else{
                                        var="Homeopathy";f=0;

                                    }

                                        MarkerOptions options1 = new MarkerOptions().position(temp)
                                                .title(hospitalName.get(i) +"("+var+")").
                                                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));


                                        global_hospital_id=hospitalidlist.get(i);

                                        //zoom mapgoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 50));


                                        //ADD MARKER ON MAP
                                        Marker marker=googleMap.addMarker(options1);

                                        hospital_hash_map.put(marker,hospitalidlist.get(i));


                                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(@NonNull Marker marker) {
                                                global_hospital_id=hospital_hash_map.get(marker);

                                                userbook.setVisibility(View.VISIBLE);
                                                return false;
                                            }
                                        });



                                        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                            @Override
                                            public void onMapClick(@NonNull LatLng latLng) {
                                                userbook.setVisibility(View.GONE);
                                                Marker temp=googleMap.addMarker(options1);
                                               // temp.hideInfoWindow();
                                               // tempMarker.

                                            }
                                        });

                                        //CardView Here --opening /closing
                                        //number -- to display call
                                        //hospital registeration form
                                        //availibiltiy/price












                                    }
                                    SearchLocation.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            switch (v.getId())
                                            {

                                                case R.id.speechBtn:
                                                    //EditText addressFeld=(EditText) findViewById(R.id.locationsearch);


                                                    String address=locationsearchfield.getText().toString();
                                                    List<Address> addressList=null;
                                                    MarkerOptions usermarkerOptions=new MarkerOptions();
                                                    if(!TextUtils.isEmpty(address))
                                                    {
                                                        Geocoder geocoder=new Geocoder(getApplicationContext());
                                                        try{
                                                            addressList=geocoder.getFromLocationName(address,3);

                                                            if(addressList!=null)
                                                            {
                                                                for(int i=0;i<addressList.size();i++)

                                                                {
                                                                    Address userAddress=addressList.get(i);
                                                                    LatLng latLng=new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                                                                    usermarkerOptions.position(latLng);
                                                                    usermarkerOptions.title(address);
                                                                    usermarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                                                    googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                                                                    googleMap.addMarker(usermarkerOptions);

                                                                }
                                                            }
                                                            else{
                                                                Toast.makeText(getApplicationContext(),"Location not found",Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                        catch (Exception e)
                                                        {
                                                            e.printStackTrace();
                                                        }


                                                    }
                                                    else{
                                                        Toast.makeText(getApplicationContext(),"please write location",Toast.LENGTH_LONG).show();
                                                    }
                                                    break;



                                            }
                                        }
                                    });













                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),"Response not proper",Toast.LENGTH_SHORT).show();
                                    Log.d("tag", "onErrorResponse: " + error.getMessage());
                                }
                            });

                            queue.add(jsonArrayRequest);





                        }
                    });
                } else {
                    Toast.makeText(Map_Display_User_Location_Hospital.this, "Location not found", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }


    public void displayHospital(View view) {



        if(global_hospital_id.equals("")){
            Toast.makeText(getApplicationContext(),"Select Hospital",Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(getApplicationContext(), "ClickedUserbook  "+global_hospital_id, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Map_Display_User_Location_Hospital.this, UserForm.class);
            i.putExtra("hospitalId", global_hospital_id);
            startActivity(i);
        }



        // Toast.makeText(getApplicationContext(), latLng.latitude + " " + latLng.longitude, Toast.LENGTH_SHORT).show();


    }


    public void onClick(View view) {

Toast.makeText(getApplicationContext(),"Button pressed",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        try {
            startActivityForResult(intent, RESULT_SPEECH);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    locationsearchfield.setText(text.get(0));
                }
                break;
        }
    }


    public void onClick2(View view) {


    }
}
