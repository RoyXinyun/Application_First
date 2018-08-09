package com.example.dell.lbstest;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;

import static android.content.Context.CAMERA_SERVICE;
import static com.example.dell.lbstest.MyNewApplication.DEVICE_MACADDR;

public class Fragment_set extends Fragment {
    Switch switch_bluetooth;
    Switch switch_camera;
    Switch switch_light;
    Boolean light = false;
    Switch switch_device;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private TextView text_yao;
    private CRPBleClient crpBleClient;
    private CRPBleDevice crpBleDevice;
    private CRPBleConnection crpBleConnection;
    private TextView text_chui;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set,container,false);
        iniView(view);
        switch_camera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
                switch_camera.setChecked(false);
            }
        });
        switch_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    light = !light;
                    CameraManager mCameraManager = (CameraManager)getContext().getSystemService(CAMERA_SERVICE);
                    String[] ids  = mCameraManager.getCameraIdList();//获取当前手机所有摄像头设备ID
                    for (String id : ids) {
                        CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                        Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE); //查询该摄像头组件是否包含闪光灯
                        Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                        if (flashAvailable != null && flashAvailable
                                && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                            mCameraManager.setTorchMode(id,light);//打开或关闭手电筒
                        }
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        switch_bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(bluetoothAdapter.isEnabled()){
                    bluetoothAdapter.disable();
                    switch_bluetooth.setChecked(false);
                }
                else
                {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(intent);
                    switch_bluetooth.setChecked(true);
                }
            }
        });
        text_yao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void iniView(View view) {
        switch_bluetooth = (Switch)view.findViewById(R.id.switch_bluetooth);
        switch_camera = (Switch)view.findViewById(R.id.switch_camera);
        switch_light = (Switch)view.findViewById(R.id.switch_light);
        switch_camera.setChecked(false);
        switch_light.setChecked(false);
        if(bluetoothAdapter.isEnabled())
            switch_bluetooth.setChecked(true);
        else
            switch_bluetooth.setChecked(false);
        text_yao = (TextView)view.findViewById(R.id.text_yao);
    }
}
