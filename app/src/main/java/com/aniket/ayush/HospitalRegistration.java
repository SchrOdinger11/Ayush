package com.aniket.ayush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HospitalRegistration extends AppCompatActivity {

    EditText mail, password, name;
    Spinner spinner;
    TextView signin;
    FloatingActionButton signup;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_hospital_registration);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mail = (EditText) findViewById(R.id.h_email);
        password = (EditText) findViewById(R.id.h_pass);
        name = (EditText) findViewById(R.id.h_name);
//        spinner = (Spinner) findViewById(R.id.spinner2);
        signin = (TextView) findViewById(R.id.h_signIn);
        signup = (FloatingActionButton) findViewById(R.id.h_signUp);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalRegistration.this, MainActivity.class));
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mail.getText().toString();
                String pwd = password.getText().toString();
                String nam = name.getText().toString();
                if(email.isEmpty()){
                    mail.setError("Please enter email id");
                    mail.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(nam.isEmpty()){
                    name.setError("Please enter your Name");
                    name.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty() && nam.isEmpty()){
                    Toast.makeText(HospitalRegistration.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty() && nam.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, "Hospital"+pwd).addOnCompleteListener(HospitalRegistration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(HospitalRegistration.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(HospitalRegistration.this, DashBoard.class));
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(HospitalRegistration.this,"Error Occurred!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}