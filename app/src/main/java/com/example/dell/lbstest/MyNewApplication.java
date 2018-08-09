package com.example.dell.lbstest;

import android.app.Application;
import android.content.Context;

import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;

public class MyNewApplication extends Application {
    CRPBleClient crpBleClient;
    CRPBleDevice crpBleDevice;
    CRPBleConnection crpBleConnection;
    public static final String DEVICE_MACADDR = "DA:92:80:F8:63:98";

    @Override
    public void onCreate() {
        super.onCreate();
        crpBleClient = CRPBleClient.create(this);
        crpBleDevice = crpBleClient.getBleDevice(DEVICE_MACADDR);
        crpBleConnection = crpBleDevice.connect();
    }

    public static CRPBleClient getBLEClient(Context context){
        MyNewApplication myApplication = (MyNewApplication) context.getApplicationContext();
        return myApplication.crpBleClient;
    }

    public static CRPBleDevice getBLEDevice(Context context){
        MyNewApplication myApplication = (MyNewApplication) context.getApplicationContext();
        return myApplication.crpBleDevice;
    }

    public static CRPBleConnection getBLEConnection(Context context){
        MyNewApplication myApplication = (MyNewApplication) context.getApplicationContext();
        return myApplication.crpBleConnection;
    }
}
