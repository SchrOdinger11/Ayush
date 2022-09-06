package com.aniket.ayush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AppointmentData extends AppCompatActivity {

    TextView name, no, msg, symptoms;
    Appointment appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_data);

        name = findViewById(R.id.text_name);
        no = findViewById(R.id.text_no);
        msg = findViewById(R.id.text_msg);
        symptoms = findViewById(R.id.text_symp);

        Intent i = getIntent();
        if (i != null) {
            appointment = (Appointment) i.getSerializableExtra("key");

            name.setText(appointment.getPatient_name());
            no.setText(appointment.getPatient_number());
            msg.setText(appointment.getMessage());
            symptoms.setText(appointment.getPatient_symptoms());
//            Toast.makeText(this, hospital.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}