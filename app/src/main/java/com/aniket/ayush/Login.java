package com.aniket.ayush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private static String JSON_URL = "https://sihmvp1.herokuapp.com/hospital/";
    EditText mail, password;
    Spinner spinner;
    TextView signUp, forgotPassword, hospitalRegistration;
    FirebaseAuth mFirebaseAuth;
    Button btnSignIn;
    ProgressDialog pd;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toast.makeText(getApplicationContext(),"new act",Toast.LENGTH_SHORT).show();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mail = (EditText) findViewById(R.id.username_email);
//        hospitalRegistration  = (TextView) findViewById(R.id.signup_hospital);
        password = (EditText) findViewById(R.id.password);
        spinner = (Spinner) findViewById(R.id.spinner);
        signUp = (TextView) findViewById(R.id.register_button);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        pd = new ProgressDialog(this);
        pd.setMessage("Sign in ....");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.UserType, R.layout.spinner_item2);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
//                Toast.makeText(getApplicationContext(), "Idharr", Toast.LENGTH_SHORT).show();
//                if( mFirebaseUser != null ){
////                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
////                    password.requestFocus();
////                    emailId.requestFocus();
//                    Intent i = new Intent(LoginActivity.this, DashBoard.class);
//                    startActivity(i);
//                    finish();
//                }
//                else{
////                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
//                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                String item = spinner.getSelectedItem().toString();
                if(item==null)item = "User";
                String finalItem = item;
                String email = mail.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    mail.setError("Please enter email id");
                    mail.requestFocus();
                    pd.dismiss();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                    pd.dismiss();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(Login.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else  if(isValidEmail(email)){
                    mFirebaseAuth.signInWithEmailAndPassword(email, finalItem+pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Login.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                            else{
                                password.getText().clear();
                                mail.getText().clear();
                                if(finalItem.equals("Admin")){
                                    startActivity(new Intent(Login.this, AdminDashboard.class));
                                }else if(finalItem.equals("Hospital")){
                                    Intent intToHome = new Intent(Login.this, HospitalMainActivity.class);
                                    startActivity(intToHome);
                                }
                                else{
                                    Intent intToHome = new Intent(Login.this, UserMainActivity.class);
                                    startActivity(intToHome);
                                }
                                finish();
                                pd.dismiss();
                            }
                        }
                    });
                }
                else{
                    mail.setError("Please Check Your Email Id");
                    mail.requestFocus();
                    pd.dismiss();

                }

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ResetPass = new Intent(Login.this, ForgotPassword.class);
                startActivity(ResetPass);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, HospitalRegistration.class));
            }
        });

//        hospitalRegistration.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Login.this, HospitalRegistration.class));
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if(currentUser != null) {
            if(currentUser.getEmail().toString().toLowerCase().equals("admin@gmail.com")) {
                startActivity(new Intent(Login.this, AdminDashboard.class));
            }
            else {
                RequestQueue queue = Volley.newRequestQueue(this);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL + currentUser.getUid(), null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try {
                            JSONObject object = response.getJSONObject(0);
                            if (object.getString("type").equals("hospital")) {
                                Intent intToHome = new Intent(Login.this, HospitalMainActivity.class);
                                startActivity(intToHome);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("tag", "onErrorResponse: " + error.getMessage());
                    }
                });

                queue.add(jsonArrayRequest);
            }
            finish();
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    }
