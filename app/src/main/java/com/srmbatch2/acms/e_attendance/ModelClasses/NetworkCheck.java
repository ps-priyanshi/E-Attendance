package com.srmbatch2.acms.e_attendance.ModelClasses;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkCheck {

    public boolean isConnectedTo(String ssid, Context context) {
        boolean retVal = false;
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        if (wifiInfo != null) {
            String currentConnectedSSID = wifiInfo.getSSID();
            if (currentConnectedSSID != null && ssid.equals(currentConnectedSSID)) {
                retVal = true;
            }
        }
        return retVal;
    }
}
