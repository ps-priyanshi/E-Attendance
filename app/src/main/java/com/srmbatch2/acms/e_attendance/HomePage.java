package com.srmbatch2.acms.e_attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
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
import com.srmbatch2.acms.e_attendance.Adapters.HomePageAdapter;
import com.srmbatch2.acms.e_attendance.ModelClasses.UserSubjects;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements HomePageAdapter.OnClickListener {


    private TextView userName, userRegNo;
    private Toolbar toolbar;
    private DatabaseReference databaseReference, databaseReferenceFaculty, databaseReference1;
    private Button enroll, logout;
    private RecyclerView recyclerView;
    private ArrayList<UserSubjects> arrayList;
    private HomePageAdapter adapter;
    private String ssid;
    public static String sub, fac, subId, nameUser, regNoUser;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.enrollMenu) {
            Intent intent = new Intent(HomePage.this, EnrollActivity.class);
            startActivity(intent);
        } else if (id == R.id.requestsEnroll) {
            Intent intent = new Intent(HomePage.this, RequestsEnroll.class);
            startActivity(intent);
        } else if (id == R.id.logoutMenu) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(HomePage.this, MainActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //  enroll = findViewById(R.id.enrollBtn);
        // logout = findViewById(R.id.logoutBtn);
        userName = findViewById(R.id.userName);
        userRegNo = findViewById(R.id.userRegNo);
        FirebaseApp.initializeApp(this);
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView = findViewById(R.id.recyclerViewHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


      /*  if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("net","Permi : Not have permi");

        } else {
            Log.i("net","Permi : Have permi");
        }

         boolean val = isConnectedTo("Self-Service", HomePage.this);
         ssid= getWifiName(HomePage.this);
         Log.i("net","State "+ val);
         Log.i("net","ssid " + ssid); */


        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                nameUser = dataSnapshot.child("name").getValue().toString();
                userName.setText(nameUser);
                regNoUser = dataSnapshot.child("regNo").getValue().toString();
                userRegNo.setText(regNoUser);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(HomePage.this, "Failed to read value", Toast.LENGTH_LONG).show();
            }
        });


        arrayList = new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference("users").child(user).orderByChild("typeUser").equalTo("regularUser");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                UserSubjects subject = dataSnapshot.getValue(UserSubjects.class);
                arrayList.add(subject);

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

        adapter = new HomePageAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {

        UserSubjects subject = arrayList.get(position);
        sub = subject.getSubjectUser();
        fac = subject.getFacultyUser();
        subId = subject.getSubjectID();
        Intent intent = new Intent(HomePage.this, FingerPrintAuth.class);
        startActivity(intent);
    }
}

   /* public boolean isConnectedTo(String ssid, Context context) {
        boolean retVal = false;
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        if (wifiInfo != null) {
            String currentConnectedSSID = wifiInfo.getSSID();
           // ssid = currentConnectedSSID;
            if (currentConnectedSSID != null && ssid.equals(currentConnectedSSID)) {
                retVal = true;
            }
        }
        return retVal;
    }

    public String getWifiName(Context context) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }
    */
