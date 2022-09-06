package com.aniket.ayush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HospitalData extends AppCompatActivity {

    Hospital hospital;
    Button viewDoc;
    TextView name, specialities, address,email_id,mob_no, url;
    FirebaseStorage storage;
    StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_data);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        name = findViewById(R.id.hospital_name);
        specialities = findViewById(R.id.service_pro);
        email_id = findViewById(R.id.text_hc2);
        mob_no=findViewById(R.id.text_hc3);
        url = findViewById(R.id.text_hc4);

        address = findViewById(R.id.address_name);
        viewDoc = findViewById(R.id.button_1);
        Intent i = getIntent();
        if (i != null) {
            hospital = (Hospital) i.getSerializableExtra("key");
            email_id.setText(hospital.getEmail());
            name.setText(hospital.getName());
            specialities.setText(hospital.getSpecialities());
           mob_no.setText(hospital.getPh_numbers());
            url.setText(hospital.getUrl());
            address.setText(hospital.getAddress());

//            Toast.makeText(this, hospital.getName(), Toast.LENGTH_SHORT).show();
        }

        viewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageRef.child("Documents/"+hospital.getDocumentId()+"/"+"file.pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()));
                        startActivity(browserIntent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Toast.makeText(HospitalData.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}