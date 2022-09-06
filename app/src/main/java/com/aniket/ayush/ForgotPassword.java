package com.aniket.ayush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    TextView tvSignIn;
    FloatingActionButton resetPass;
    EditText emailId;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.f_email);
        tvSignIn = findViewById(R.id.f_signIn);
        resetPass = findViewById(R.id.btn_forgotPasspwd);

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = emailId.getText().toString();

                if(isValidEmail(mail)){
                    mFirebaseAuth.sendPasswordResetEmail(mail)
                            .addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(ForgotPassword.this,"Reset Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(ForgotPassword.this,"Check Your Email!",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
                else{
                    emailId.setError("Please check email id");
                    emailId.requestFocus();
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPassword.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}