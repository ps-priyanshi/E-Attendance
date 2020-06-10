package com.srmbatch2.acms.e_attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.srmbatch2.acms.e_attendance.ModelClasses.Faculty;
import com.srmbatch2.acms.e_attendance.ModelClasses.RequestStatus;
import com.srmbatch2.acms.e_attendance.ModelClasses.Student;
import com.srmbatch2.acms.e_attendance.ModelClasses.Subject;
import com.srmbatch2.acms.e_attendance.ModelClasses.UserSubjects;

import java.util.ArrayList;
import java.util.HashSet;

public class EnrollActivity extends AppCompatActivity {

    private Spinner spinnerSubject, spinnerFaculty, spinnerBatch;
    private Toolbar toolbar;
    private TextView errorText;
    private ArrayList<String> arrayListSubject, arrayListFaculty, arrayListBatch;
    private ArrayAdapter<String> arrayAdapterSubject, arrayAdapterBatch, arrayAdapterFaculty;
    private DatabaseReference databaseReference, databaseReferenceUser, databaseReference1, databaseReferenceUserData;
    private Button submit;
    private String sub,fac, batch, nameUser, regNoUser,keyName,user, students;
    private Query  queryFac, queryNameSub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);


        submit = findViewById(R.id.EnrollBtn);
        errorText = findViewById(R.id.errorText);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        spinnerFaculty = findViewById(R.id.spinnerFaculty);
        spinnerBatch = findViewById(R.id.spinnerBatch);
        FirebaseApp.initializeApp(this);

        toolbar = findViewById(R.id.toolbarEnroll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("faculty");
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceUserData = FirebaseDatabase.getInstance().getReference("users").child(user).child("userEnrollmentRequests");

        arrayListSubject = new ArrayList<>();
        arrayListBatch = new ArrayList<>();
        arrayListFaculty = new ArrayList<>();
        arrayAdapterSubject = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayListSubject);
        arrayAdapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(arrayAdapterSubject);

        arrayAdapterFaculty = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayListFaculty);
        arrayAdapterFaculty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFaculty.setAdapter(arrayAdapterFaculty);


        arrayAdapterBatch = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayListBatch);
        arrayAdapterBatch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBatch.setAdapter(arrayAdapterBatch);




        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String id = dataSnapshot.getKey();
                //Log.i("info", "Key : " + id);


                queryNameSub = FirebaseDatabase.getInstance().getReference("faculty").child(id).orderByChild("type").equalTo("regular");

                queryNameSub.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot1, @Nullable String s) {

                        //Log.i("info", "ChildId " + dataSnapshot1.getKey());
                      //  Log.i("info", "Child " + dataSnapshot1.getValue().toString());
                        Subject subject = dataSnapshot1.getValue(Subject.class);
                       // Log.i("info", "Sub " + subject);
                        //String sub = subject.getNameSub();
                        String sub = subject.getNameSub();
                        //Log.i("info", "Sub " + sub);
                        arrayListSubject.add(sub);


                        HashSet<String> hashSet = new HashSet<>();
                        hashSet.addAll(arrayListSubject);
                        arrayListSubject.clear();
                        arrayListSubject.addAll(hashSet);
                        arrayAdapterSubject.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        arrayAdapterSubject.clear();
                        arrayAdapterSubject.notifyDataSetChanged();
                        arrayAdapterFaculty.clear();
                        arrayAdapterFaculty.notifyDataSetChanged();
                        arrayAdapterBatch.clear();
                        arrayAdapterBatch.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayAdapterSubject.clear();
                arrayAdapterSubject.notifyDataSetChanged();
                arrayAdapterFaculty.clear();
                arrayAdapterFaculty.notifyDataSetChanged();
                arrayAdapterBatch.clear();
                arrayAdapterBatch.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {

                sub = arrayListSubject.get(position);
                //Log.i("info","SubSpinner " + sub );

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        String id = dataSnapshot.getKey();
                        //Log.i("info", "KeySub : " + id);


                        queryNameSub = FirebaseDatabase.getInstance().getReference("faculty").child(id).orderByChild("nameSub").equalTo(sub);

                        queryNameSub.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot1, @Nullable String s) {

                                //Log.i("info", "ChildIdSub " + dataSnapshot1.getKey());
                               // Log.i("info", "ChildSub " + dataSnapshot1.getValue().toString());
                                Subject subject = dataSnapshot1.getValue(Subject.class);
                                //Log.i("info", "Sub " + subject);
                                String fac = subject.getFacultySub();
                                //Log.i("info", "Sub " + fac);
                                arrayListFaculty.add(fac);


                                HashSet<String> hashSet = new HashSet<>();
                                hashSet.addAll(arrayListFaculty);
                                arrayListFaculty.clear();
                                arrayListFaculty.addAll(hashSet);
                                arrayAdapterFaculty.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                arrayAdapterSubject.notifyDataSetChanged();
                                arrayAdapterFaculty.notifyDataSetChanged();
                                arrayAdapterBatch.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        arrayAdapterSubject.clear();
                        arrayAdapterSubject.notifyDataSetChanged();
                        arrayAdapterFaculty.clear();
                        arrayAdapterFaculty.notifyDataSetChanged();
                        arrayAdapterBatch.clear();
                        arrayAdapterBatch.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                arrayAdapterFaculty.clear();
                arrayAdapterFaculty.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                fac = arrayListFaculty.get(position);
                //Log.i("info","Fac " + fac );
                queryFac = FirebaseDatabase.getInstance().getReference().child("faculty").orderByChild("name").equalTo(fac);

                ValueEventListener eventListenerFac = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                String batch = snapshot.getValue(Faculty.class).toStringBatch();
                                arrayListBatch.add(batch);

                                HashSet<String> hashSet = new HashSet<>();
                                hashSet.addAll(arrayListBatch);
                                arrayListBatch.clear();
                                arrayListBatch.addAll(hashSet);
                                arrayAdapterBatch.notifyDataSetChanged();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                queryFac.addValueEventListener(eventListenerFac);
                arrayAdapterBatch.clear();
                arrayAdapterBatch.notifyDataSetChanged();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        spinnerBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                batch = arrayListBatch.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("users").child(user);

                databaseReferenceUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                         regNoUser = dataSnapshot.child("regNo").getValue().toString();
                         nameUser = dataSnapshot.child("name").getValue().toString();

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(EnrollActivity.this, "Failed to read value", Toast.LENGTH_LONG).show();
                    }
                });




                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {

                        final String id = dataSnapshot.getKey();
                        //Log.i("info", "KeySub : " + id);


                        Query query = FirebaseDatabase.getInstance().getReference("faculty").child(id).orderByChild("facultySub").equalTo(fac);

                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot1, @Nullable String s) {

                                //  Log.i("info", "ChildId " + id);
                                // Log.i("info", "ChildIdSub " + dataSnapshot1.getKey());

                                Query query2 = FirebaseDatabase.getInstance().getReference("faculty").child(id).orderByChild("nameSub").equalTo(sub);

                                query2.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull final DataSnapshot dataSnapshot1, @Nullable String s) {

                                        keyName = dataSnapshot1.getKey();
                                       // Log.i("info", "MainId " + keyName);
                                        //Log.i("info", "MainData " + dataSnapshot.getValue(Subject.class).toStringNameSub());
                                        //String key = snapshot.getKey();
                                        //Log.i("info","IDKey "+ key);

                                        Query query3 = FirebaseDatabase.getInstance().getReference("faculty").child(id).child(keyName).child("enrollmentRequests");

                                        query3.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if(dataSnapshot.hasChild(regNoUser))
                                                {
                                                    errorText.setText("Request Already Sent.");
                                                }
                                                else{

                                                    databaseReferenceUserData.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                            if(dataSnapshot.hasChild(keyName))
                                                            {
                                                                errorText.setText("Invalid Request.");
                                                            }
                                                            else {


                                                                Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
                                                                toast.cancel();
                                                                Toast.makeText(EnrollActivity.this, "Request Sent", Toast.LENGTH_SHORT).show();
                                                                databaseReference1 = FirebaseDatabase.getInstance().getReference();
                                                                RequestStatus requestStatus = new RequestStatus(nameUser,regNoUser,"0", fac, sub);
                                                                databaseReference.child(id).child(keyName).child("enrollmentRequests").child(regNoUser).setValue(requestStatus);
                                                                databaseReference1.child("users").child(user).child("userEnrollmentRequests").child(keyName).setValue(requestStatus);

                                                                Intent intent=new Intent(EnrollActivity.this,HomePage.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                        }



                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        arrayAdapterSubject.notifyDataSetChanged();
                                        arrayAdapterFaculty.notifyDataSetChanged();
                                        arrayAdapterBatch.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                arrayAdapterSubject.notifyDataSetChanged();
                                arrayAdapterFaculty.notifyDataSetChanged();
                                arrayAdapterBatch.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                        @Override
                        public void onChildChanged (@NonNull DataSnapshot
                        dataSnapshot, @Nullable String s){

                        }

                        @Override
                        public void onChildRemoved (@NonNull DataSnapshot dataSnapshot){

                        }

                        @Override
                        public void onChildMoved (@NonNull DataSnapshot
                        dataSnapshot, @Nullable String s){

                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError){

                        }

                });
            }


        });
    }
}
