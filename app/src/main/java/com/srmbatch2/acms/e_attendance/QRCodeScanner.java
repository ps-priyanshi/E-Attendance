package com.srmbatch2.acms.e_attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.srmbatch2.acms.e_attendance.ModelClasses.Attendance;
import com.srmbatch2.acms.e_attendance.ModelClasses.Student;
import com.srmbatch2.acms.e_attendance.ModelClasses.Subject;
import com.srmbatch2.acms.e_attendance.ModelClasses.UserSubjects;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

public class QRCodeScanner extends MyBaseActivity {

    private ImageView qrCode;
    private Button scan;
    private Toolbar toolbar;
    private String sub, fac, subId, keyName, user, regNoUser, nameUser, date, time, date1, startTime, endTime;
    private DatabaseReference databaseReference, databaseReferenceUserData, databaseReferenceUser;
    public static String keyNameClassesConducted, verified = "no", valid = "no";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        FirebaseApp.initializeApp(QRCodeScanner.this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        qrCode = findViewById(R.id.qrCode);
        scan = findViewById(R.id.scan);

        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("users");
        databaseReference = FirebaseDatabase.getInstance().getReference("faculty");
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceUserData = FirebaseDatabase.getInstance().getReference("users").child(user);

        databaseReferenceUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                regNoUser = dataSnapshot.child("regNo").getValue().toString();
                nameUser = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        sub = HomePage.sub;
        fac = HomePage.fac;
        subId = HomePage.subId;
        //Log.i("info","Sub " + sub);
        //Log.i("info","Fac " + fac);

        Query query = FirebaseDatabase.getInstance().getReference("faculty").orderByChild("name").equalTo(fac);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                final String id = dataSnapshot.getKey();
                Log.i("info","id time " +id);

                Query query1 = FirebaseDatabase.getInstance().getReference("faculty").child(id).child("attendance").child(subId)
                        .child(date1).child("validity");
                query1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        startTime = dataSnapshot.child("startTime").getValue().toString();
                        endTime = dataSnapshot.child("endTime").getValue().toString();
                        Log.i("info","start time " +startTime);
                        Log.i("info","end time " +endTime);

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


        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        //time = Calendar.getInstance().getTime();
        Log.i("info","Time " + time);
        Log.i("info","Date " + date);
        Log.i("info","Date1 " + date1);



        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (verified == "no") {
                    Intent intent = new Intent(QRCodeScanner.this, FingerPrintAuth.class);
                    startActivity(intent);
                    finish();
                }

                */

                IntentIntegrator intentIntegrator = new IntentIntegrator(QRCodeScanner.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });

      /*  if (verified == "yes") {

            IntentIntegrator intentIntegrator = new IntentIntegrator(QRCodeScanner.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            intentIntegrator.setCameraId(0);
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.setPrompt("Scanning");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setBarcodeImageEnabled(true);
            intentIntegrator.initiateScan();
        }

       */
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String contents = result.getContents();
       // String c[] = contents.;
        String c1 = contents.substring(0,6);
        String c2 = contents.substring(7,16);
        String c3 = contents.substring(17,29);
        String decryptContents1 = decryptor(c1);
        String decryptContents2 = decryptor(c2);
        String decryptContents3 = decryptor(c3);
        //String string = decryptContents.toString();
        Log.i("info", "Content " + contents);
        //Log.i("info", "c " + c);
        Log.i("info", "C1 " + c1);
        Log.i("info", "C2 " + c2);
        Log.i("info", "C3 " + c3);
        Log.i("info", "DecryptContent1 " + decryptContents1);
        Log.i("info", "DecryptContent2 " + decryptContents2);
        Log.i("info", "DecryptContent3 " + decryptContents3);
        Log.i("info", "Valid " + valid);
        Log.i("info", "subId " + subId);
        Log.i("info", "startTime mid " + Long.parseLong(startTime.substring(3,5)));
        Log.i("info", "endTime mid " + Long.parseLong(endTime.substring(3,5)));
        Log.i("info", "time mid " + Long.parseLong(time.substring(3,5)));

        if (decryptContents2.contains(subId)  && decryptContents3.contains(date1) && Long.parseLong(time.substring(3,5))>=Long.parseLong(startTime.substring(3,5)) && Long.parseLong(time.substring(3,5))<=Long.parseLong(endTime.substring(3,5))) {
            valid = "yes";
        }

        Log.i("info", "Valid " + valid);
        if (result != null && valid == "yes") {

            // Log.i("info","Verified "+ verified);
            new AlertDialog.Builder(QRCodeScanner.this)
                    .setTitle("Scan Result")
                    //.setMessage(result.getContents())
                    .setMessage("Click on Submit to mark attendance.")
                    .setPositiveButton("Submit Attendance", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {


                            databaseReference.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {

                                    final String id = dataSnapshot.getKey();
                                    //Log.i("info", "KeySub : " + id);


                                    Query query = FirebaseDatabase.getInstance().getReference("faculty").child(id).orderByChild("facultySub").equalTo(fac);

                                    query.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(@NonNull final DataSnapshot dataSnapshot1, @Nullable String s) {

                                            // Log.i("info", "ChildId " + id);
                                            // Log.i("info", "ChildIdSub " + dataSnapshot1.getKey());


                                            Query query2 = FirebaseDatabase.getInstance().getReference("faculty").child(id).orderByChild("nameSub").equalTo(sub);

                                            query2.addChildEventListener(new ChildEventListener() {
                                                @Override
                                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot1, @Nullable String s) {

                                                    keyName = dataSnapshot1.getKey();
                                                    keyNameClassesConducted = dataSnapshot1.child("classesConducted").getValue().toString();

                                                    final Long keyNameClassesCon = Long.parseLong(keyNameClassesConducted);
                                                    //Log.i("info", "Keyname " + keyName);
                                                    Log.i("atten", "Keynameclass " + keyNameClassesCon);


                                                    Query q = FirebaseDatabase.getInstance().getReference("faculty").child(id).child("attendance").child(keyName).child(date1).orderByKey();

                                                    q.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if (dataSnapshot.hasChild(regNoUser)) {
                                                                dialog.dismiss();
                                                                Toast.makeText(QRCodeScanner.this, "Attendance for " + sub + " is already marked!!", Toast.LENGTH_LONG).show();
                                                                Intent intent = new Intent(QRCodeScanner.this, HomePage.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {

                                                                Query query = FirebaseDatabase.getInstance().getReference("faculty").child(id).child(keyName).orderByChild("regNoStudent").equalTo(regNoUser);


                                                                query.addChildEventListener(new ChildEventListener() {
                                                                    @Override
                                                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                                        //String keyStudent = dataSnapshot.getKey();

                                                                        String attendance = dataSnapshot.getValue(Student.class).toStringattendedClass();

                                                                        Long count = Long.parseLong(attendance);
                                                                        count = count + 1;
                                                                        //Log.i("info", "FacultyAtten " + count);
                                                                        final String c = count.toString();
                                                                        Log.i("atten","Before c "+ Long.parseLong(c));
                                                                        databaseReference.child(id).child(keyName).child(regNoUser).child("attendedClass").setValue(c);

                                                                        Attendance object = new Attendance(nameUser, date1, time, sub);
                                                                        databaseReference.child(id).child("attendance").child(keyName).child(date1).child(regNoUser).setValue(object);

                                                                        Query query1 = FirebaseDatabase.getInstance().getReference("users").child(user).orderByChild("subjectUser").equalTo(sub);

                                                                        query1.addChildEventListener(new ChildEventListener() {
                                                                            @Override
                                                                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                                                String key = dataSnapshot.getKey();
                                                                                String attendance = dataSnapshot.getValue(UserSubjects.class).toStringAttendanceUser();
                                                                                // Log.i("atten", "Attendance before " + attendance);
                                                                                Long count = Long.parseLong(attendance);
                                                                                count = count + 1;
                                                                                Double currentAttendance = ((Double.parseDouble(c) / Double.parseDouble(keyNameClassesConducted)) * 100);
                                                                                String currAtten = currentAttendance.toString();
                                                                                Log.i("atten","After keynamecon "+ keyNameClassesCon);
                                                                                Log.i("atten","After c "+ Long.parseLong(c));
                                                                                Log.i("atten","Attendance after "+ currentAttendance);
                                                                                databaseReferenceUser.child(user).child(key).child("attendanceUser").setValue(currAtten.substring(0,5));


                                                                                dialog.dismiss();
                                                                                Toast.makeText(QRCodeScanner.this, "Attendance Marked Successfully!", Toast.LENGTH_LONG).show();
                                                                                Intent intent = new Intent(QRCodeScanner.this, HomePage.class);
                                                                                startActivity(intent);
                                                                                finish();

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
                          /*  ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData data = ClipData.newPlainText("result", result.getContents());
                            clipboardManager.setPrimaryClip(data); */
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                            Intent intent = new Intent(QRCodeScanner.this, HomePage.class);
                            startActivity(intent);
                            finish();
                        }
                    }).create().show();

            verified = "no";
            valid = "no";

        } else {
            valid = "no";
            verified = "no";
            Toast.makeText(this, "Invalid QR Code! Timeout!", Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    public String decryptor(String codes){
        int len = codes.length();
        int rowSize = (int) floor(sqrt(len));
        int columnSize = (int) ceil(sqrt(len));
        if (columnSize * rowSize < len) {
            if (min(columnSize, rowSize) == columnSize)
                columnSize+=1;
            else
                rowSize+=1;
        }

        String[][] cipherTextMatrix = new String[rowSize][columnSize];
        String[][] reverseMatrix = new String[rowSize][columnSize];

        for (int i=0;i<rowSize;i++)
            for (int j = 0; j < columnSize; j++)
                cipherTextMatrix[i][j]=reverseMatrix[i][j]=" ";

        int l = 0;
        String plaintext = "";
        try {
            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < columnSize; j++) {
                    cipherTextMatrix[i][j] = String.valueOf(codes.charAt(l));
                    l++;
                }
            }


            for (int x = 0; x < rowSize; x++) {
                for (int y = 0, z = columnSize - 1; y < columnSize && z >= 0; y++, z--) {
                    reverseMatrix[x][y] = cipherTextMatrix[x][z];
                }
            }

            int k = 0;
            l = 0;
            while (k < rowSize && l < columnSize) {
                for (int i = l; i < columnSize; ++i)
                    plaintext += reverseMatrix[k][i];

                k++;

                for (int i = k; i < rowSize; ++i)
                    plaintext += reverseMatrix[i][columnSize - 1];

                columnSize--;


                if (k < rowSize) {
                    for (int i = columnSize - 1; i >= l; --i)
                        plaintext += reverseMatrix[rowSize - 1][i];

                    rowSize--;
                }

                if (l < columnSize) {
                    for (int i = rowSize - 1; i >= k; --i)
                        plaintext += reverseMatrix[i][l];

                    l++;
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {

        }
        Log.i("info","Plaintext " +plaintext);

        return plaintext;
    }


}


