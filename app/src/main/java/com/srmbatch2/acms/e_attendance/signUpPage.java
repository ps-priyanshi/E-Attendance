package com.srmbatch2.acms.e_attendance;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.srmbatch2.acms.e_attendance.ModelClasses.User;

public class signUpPage extends AppCompatActivity {

    private EditText name, regNo, pass, email;
    private Button signUp;
    private TextView loginText;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        regNo = findViewById(R.id.registrationNumberTextSign);
        name  = findViewById(R.id.nameTextSign);
        email = findViewById(R.id.emailTextSign);
        pass  = findViewById(R.id.passwordTextSign);
        signUp = findViewById(R.id.signUpButtonSign);
        loginText = findViewById(R.id.loginLabelSign);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name1 = name.getText().toString().trim();
                final String email1 = email.getText().toString().trim();
                final String regNo1 = regNo.getText().toString().trim();
                final String pass1 = pass.getText().toString().trim();

                if(name1.length()==0){
                    name.setError("Name is not entered");
                    name.requestFocus();
                }

                else if(regNo1.length()==0){
                    regNo.setError("Registration Number is not entered");
                    regNo.requestFocus();
                }

                else if(email1.length()==0){
                    email.setError("Email is not entered");
                    email.requestFocus();
                }

                else if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                    email.setError("Please enter a valid email");
                    email.requestFocus();
                }

                else if(pass1.length()==0 || pass1.length()<8){
                    pass.setError("Password can not be less than 8 characters");
                    pass.requestFocus();
                }
                else {


                    mAuth.createUserWithEmailAndPassword(email1,pass1).addOnCompleteListener(signUpPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                //Toast.makeText(signUpPage.this, "Creation Successful!", Toast.LENGTH_LONG).show();
                                FirebaseUser user = task.getResult().getUser();
                                User user1 = new User(name1,regNo1, email1, pass1);

                                databaseReference.child("users").child(user.getUid()).setValue(user1);

                                Intent intentSignLogin = new Intent(signUpPage.this, MainActivity.class);
                                startActivity(intentSignLogin);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(signUpPage.this, "User Creation Failed!", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    }

                }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intentLogin = new Intent(signUpPage.this, MainActivity.class);
               startActivity(intentLogin);
               finish();
            }
        });

    }
}
