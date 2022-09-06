package com.aniket.ayush;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.aniket.ayush.databinding.ActivityMapsHospitalBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;

public class MapsActivityHospital extends FragmentActivity implements OnMapReadyCallback {
    int pincode;
    LatLng draggedpos;double lattitudefinal;double longitudefinal;
    private GoogleMap mMap;
    private TextView displaytext;
    private ActivityMapsHospitalBinding binding;
    private String maps_api_key="AIzaSyC0yYPby4IwxwZ5CMaWxUZf7NfF9S4owbw";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    boolean  locationPermissionGranted=false;
    String pin;
    int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=100;
    Location lastKnownLocation;




    private EditText address;



    //Location defaultLocation = new Location("").setLatitude(-34).setLongitude(51);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsHospitalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Construct a PlacesClient
        Places.initialize(getApplicationContext(), getString(R.string.maps_api_key));
        placesClient = Places.createClient(this);



       // displaytext=(TextView)findViewById(R.id.textView4);
        address=(EditText)findViewById(R.id.editTextTextPersonName);

        address=findViewById(R.id.editTextTextPersonName);
        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        Intent i=getIntent();
//         pin=i.getStringExtra("message_key");
       // displaytext=findViewById(R.id.textView3);
       // displaytext.setText("hello");


    }
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
       // mMap.setOnMarkerDragListener( this);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {
                // TODO Auto-generated method stub
                // Here your code

            }

            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
                // TODO Auto-generated method stub
//                                         Toast.makeText(getApplicationContext(), "Dragging",
//                                         Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // TODO Auto-generated method stub

                draggedpos = marker.getPosition();
                lattitudefinal=draggedpos.latitude;longitudefinal=draggedpos.longitude;
                String txt=lattitudefinal+" "+longitudefinal;

                //displaytext.setText(txt);




            }



        });





    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lattitudefinal,
                                longitudefinal))
                        .title("Marker")
                        .draggable(true)
                        .snippet("Your Location")
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }



    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {


                                lattitudefinal=lastKnownLocation.getLatitude();
                                        longitudefinal=lastKnownLocation.getLongitude();

//                                lastKnownLocation.getLatitude(),
//                                        lastKnownLocation.getLongitude())

                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(lattitudefinal,
                                                longitudefinal))
                                        .title("Marker")
                                        .draggable(true)
                                        .snippet("Your Location ")
                                        .icon(BitmapDescriptorFactory
                                                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));




                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), 25));





























              }
                        } else {
                            Log.i("info", "Current location is null. Using defaults.");
                            Log.e("info", "Exception: %s", task.getException());
                            LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    public void sendLatLngBack(View view) {

       // Toast.makeText(getApplicationContext(),pin,Toast.LENGTH_SHORT).show();
//Log.i("info",pin);


getLocation();
//        Intent intent = new Intent(this,DashBoard.class);
//        intent.putExtra("Latttitude", String.valueOf(lattitudefinal));
//        intent.putExtra("Longitude", String.valueOf(longitudefinal));
//        setResult(RESULT_OK, intent);
//        startActivity(intent);
//        finish();



//class a-i class b -finish() startActivity(i) -> class a

    }
    public void goBack(View view) {

        Intent intent = new Intent(this, DashBoard.class);
        Bundle extras = new Bundle();
        extras.putString("lat", String.valueOf(lattitudefinal));
        extras.putString("long", String.valueOf(longitudefinal));
        intent.putExtras(extras);
        startActivity(intent);
        ////finish();



    }

    void getLocation(){
        String adress=address.getText().toString();




        List<Address> addressList=null;
        MarkerOptions usermarkerOptions=new MarkerOptions();
        if(!TextUtils.isEmpty(adress))
        {
            Geocoder geocoder=new Geocoder(this);
            try{
                addressList=geocoder.getFromLocationName(adress,3);

                if(addressList!=null)
                {
                    for(int i=0;i<addressList.size();i++)

                    {
                        Address userAddress=addressList.get(i);
                        LatLng latLng=new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                        usermarkerOptions.position(latLng);
                        usermarkerOptions.title(adress);
                        usermarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                        mMap.addMarker(usermarkerOptions);






                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latLng.latitude,
                                        latLng.longitude))
                                .title("Marker")
                                .draggable(true)
                                .snippet("Your Location")
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                        mMap.getUiSettings().setMyLocationButtonEnabled(true);

                    }
                }
                else{
                    Toast.makeText(this,"Location not found",Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
        else{
            Toast.makeText(this,"please write location",Toast.LENGTH_LONG).show();
        }















    }
}

