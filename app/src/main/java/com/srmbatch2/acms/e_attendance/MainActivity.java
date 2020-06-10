package com.srmbatch2.acms.e_attendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.srmbatch2.acms.e_attendance.ModelClasses.NetworkCheck;

public class MainActivity extends AppCompatActivity {

    private EditText email, pass;
    private Button login, signUpButton;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private String ssid;

    @Override
    public void onStart() {
        super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
                finish();
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailText);
        pass  = findViewById(R.id.passwordText);
        login = findViewById(R.id.loginButton);
        signUpButton= findViewById(R.id.signUpButton);


        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email2 = email.getText().toString().trim();
                String pass2 = pass.getText().toString().trim();

                if(email2.length()==0){
                    email.setError("Registration Number is not entered");
                    email.requestFocus();
                }

                else if(!Patterns.EMAIL_ADDRESS.matcher(email2).matches()){
                    email.setError("Please enter a valid email");
                    email.requestFocus();
                }

                else if(pass2.length()==0 || pass2.length()<8){
                    pass.setError("Password can not be less than 8 characters");
                    pass.requestFocus();
                }

                else {

                    mAuth.signInWithEmailAndPassword(email2, pass2).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                               // Toast.makeText(MainActivity.this, "Authentication Successful", Toast.LENGTH_LONG).show();


                                Intent intentFingerprint = new Intent(MainActivity.this, HomePage.class);
                                startActivity(intentFingerprint);
                                finish();


                            } else {

                                Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                           



                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentSignUp = new Intent(MainActivity.this, signUpPage.class);
                startActivity((intentSignUp));
                finish();

            }
        });
    }
}
