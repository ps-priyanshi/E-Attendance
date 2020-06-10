package com.srmbatch2.acms.e_attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.srmbatch2.acms.e_attendance.Adapters.RequestsEnrollAdapter;
import com.srmbatch2.acms.e_attendance.ModelClasses.RequestStatus;
import com.srmbatch2.acms.e_attendance.ModelClasses.Student;
import com.srmbatch2.acms.e_attendance.ModelClasses.Subject;
import com.srmbatch2.acms.e_attendance.ModelClasses.UserSubjects;

import java.util.ArrayList;

import static com.srmbatch2.acms.e_attendance.HomePage.fac;
import static com.srmbatch2.acms.e_attendance.HomePage.regNoUser;
import static com.srmbatch2.acms.e_attendance.HomePage.sub;

public class RequestsEnroll extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private String username, regNo, keyName, students, user;
    private DatabaseReference databaseReference, databaseReference1, databaseReference2, removeValue;
    private ArrayList<RequestStatus> arrayList;
    private RequestsEnrollAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_refresh) {
            // Log.i("info","Refresh option selected from action bar");
            // swipeRefreshLayout.setRefreshing(true);
            // update();

            databaseReference = FirebaseDatabase.getInstance().getReference("faculty");
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    final String id = dataSnapshot.getKey();
                    Log.i("info", "id refresh " + id);

                    Query q = FirebaseDatabase.getInstance().getReference("faculty").child(id).orderByChild("type").equalTo("regular");
                    q.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            Log.i("info", "id2 refresh " + dataSnapshot.getKey());
                            final String id2 = dataSnapshot.getKey();

                            Query q1 = FirebaseDatabase.getInstance().getReference("faculty").child(id).child(id2).child("enrollmentRequests").orderByKey().equalTo(regNoUser);
                            q1.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    Log.i("info", "id3 refresh " + dataSnapshot.getKey());
                                    RequestStatus requestStatus = dataSnapshot.getValue(RequestStatus.class);

                                    if (requestStatus.getStatus().equals("1")) {

                                        Log.i("info", "yess add ");
                                        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user).child("userEnrollmentRequests")
                                                .child(id2).child("status");
                                        databaseReference.setValue("1");

                                    }

                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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





  /*

            Query query = FirebaseDatabase.getInstance().getReference("users").child(user).child("userEnrollmentRequests").orderByChild("status").equalTo("0");

            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user)
                            .child("userEnrollmentRequests").child(dataSnapshot.getKey()).child("status");
                    databaseReference.setValue("1");

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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


   */
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_enroll);

        swipeRefreshLayout = findViewById(R.id.refreshRequests);
        toolbar = findViewById(R.id.toolbarRequestsEnroll);
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView = findViewById(R.id.recyclerViewRequestsEnroll);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        username = HomePage.nameUser;
        regNo = HomePage.regNoUser;
        arrayList = new ArrayList<>();

        databaseReference1 = FirebaseDatabase.getInstance().getReference("faculty");
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user).child("userEnrollmentRequests");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Log.i("info","Child " + dataSnapshot.getKey());
                final String id = dataSnapshot.getKey();
                RequestStatus requestStatus = dataSnapshot.getValue(RequestStatus.class);
                arrayList.add(requestStatus);
                //Log.i("info", "Status before for " + requestStatus.getStudentName()+ " is " + requestStatus.getStatus());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new RequestsEnrollAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.i("info", "Refreshing data");
                update();
            }
        });


    }

    private void update() {


        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user).child("userEnrollmentRequests");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Log.i("info","Child Update" + dataSnapshot.getKey());
                final String id = dataSnapshot.getKey();
                RequestStatus requestStatus = dataSnapshot.getValue(RequestStatus.class);
                //arrayList.add(requestStatus);
              // Log.i("info", "Status After for " + requestStatus.getStudentName()+ " is " + requestStatus.getStatus());
                adapter.notifyDataSetChanged();

                final String subject = requestStatus.getStudentSubject();
                final String faculty = requestStatus.getStudentFaculty();

                if(requestStatus.getStatus().equals("1"))
                {
                    Log.i("info", " Status live " + requestStatus.getStatus());
                    databaseReference1.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {

                            final String id = dataSnapshot.getKey();
                            Log.i("info", "KeySub : " + id);


                            Query query = FirebaseDatabase.getInstance().getReference("faculty").child(id).orderByChild("facultySub").equalTo(faculty);

                            query.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot1, @Nullable String s) {

                                    //  Log.i("info", "ChildId " + id);
                                    // Log.i("info", "ChildIdSub " + dataSnapshot1.getKey());

                                    Query query2 = FirebaseDatabase.getInstance().getReference("faculty").child(id).orderByChild("nameSub").equalTo(subject);

                                    query2.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(@NonNull final DataSnapshot dataSnapshot1, @Nullable String s) {

                                            keyName = dataSnapshot1.getKey();
                                            Log.i("info", "MainId " + keyName);
                                            students = dataSnapshot1.getValue(Subject.class).toStringStudents();
                                            Log.i("info", "MainData " + dataSnapshot1.getValue(Subject.class).toStringNameSub());
                                            //String key = snapshot.getKey();
                                            //Log.i("info","IDKey "+ key);

                                             Student student = new Student(keyName, username, regNo, faculty, subject,"0", "regularStudent");
                                             databaseReference1.child(id).child(keyName).child(regNoUser).setValue(student);

                                             UserSubjects userSubjects = new UserSubjects(keyName, faculty, subject,"0", "regularUser");
                                             databaseReference2 = FirebaseDatabase.getInstance().getReference();
                                             databaseReference2.child("users").child(user).child(keyName).setValue(userSubjects);

                                            removeValue = FirebaseDatabase.getInstance().getReference("faculty").child(id).child(keyName).child("enrollmentRequests").child(regNo);
                                            Log.i("info", "Remove val "+ removeValue.toString());
                                            removeValue.removeValue();

                                            Long count = Long.parseLong(students);
                                            count = count + 1;
                                            Log.i("info","Children " + count);
                                            String c = count.toString();
                                            databaseReference1.child(id).child(keyName).child("students").setValue(c);

                                        }
                                        @Override
                                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        swipeRefreshLayout.setRefreshing(false);
        finish();
        startActivity(getIntent());
    }

}

