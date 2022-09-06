package com.aniket.ayush;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static String JSON_URL = "https://sihmvp1.herokuapp.com/hospital/";
    EditText mail, password;
    Spinner spinner;
    Button register,sos;

    TextView signUp, forgotPassword, hospitalRegistration;
    FirebaseAuth mFirebaseAuth;
    FloatingActionButton btnSignIn;
    ProgressDialog pd;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sos=findViewById(R.id.button5);
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Message has been send!", Toast.LENGTH_SHORT).show();
            }
        });
        register = findViewById(R.id.button3);







    }
 public void go(View V){
     Intent i=new Intent(getApplicationContext(), Login.class);
        startActivity(i);
 }

    public void locateUser(View v) {

        Intent i = new Intent(MainActivity.this, UserMainActivity.class);
        startActivity(i);

    }

    public void startChatBot(View v) {
        Intent i = new Intent(MainActivity.this, chatBot.class);
        startActivity(i);

    }
}