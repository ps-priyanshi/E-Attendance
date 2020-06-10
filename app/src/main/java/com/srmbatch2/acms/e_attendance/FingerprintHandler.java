package com.srmbatch2.acms.e_attendance;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import androidx.core.content.ContextCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import android.widget.ImageView;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{

    private Context context;

    public FingerprintHandler(Context context) {

        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();

        fingerprintManager.authenticate(cryptoObject,cancellationSignal ,0,this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an error. " + errString,false);
    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Authentication Failed. ",false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error. " + helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        //this.update("You can now access the app",true);
        //QRCodeScanner.verified = "yes";
        Intent intent = new Intent(getContext(),QRCodeScanner.class);
        getContext().startActivity(intent);

    }

    private void update(String s, boolean b) {

        TextView heading2 = ((Activity)context).findViewById(R.id.heading2);
        ImageView fingerprintIcon = ((Activity)context).findViewById(R.id.fingerprintIcon);

        heading2.setText(s);

        if(b == false){

            heading2.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
        }
      /*  else {

            //heading2.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
            //fingerprintIcon.setImageResource(R.mipmap.task_done);
            QRCodeScanner.verified = "yes";
            Intent intent = new Intent(getContext(),QRCodeScanner.class);
            getContext().startActivity(intent);

        }

       */
    }
}
