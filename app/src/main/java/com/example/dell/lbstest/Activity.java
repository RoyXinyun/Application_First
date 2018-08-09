package com.example.dell.lbstest;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.bean.CRPHeartRateInfo;
import com.crrepa.ble.conn.bean.CRPMovementHeartRateInfo;
import com.crrepa.ble.conn.bean.CRPSleepInfo;
import com.crrepa.ble.conn.bean.CRPStepInfo;
import com.crrepa.ble.conn.callback.CRPDeviceGoalStepCallback;
import com.crrepa.ble.conn.listener.CRPBloodOxygenChangeListener;
import com.crrepa.ble.conn.listener.CRPBloodPressureChangeListener;
import com.crrepa.ble.conn.listener.CRPHeartRateChangeListener;
import com.crrepa.ble.conn.listener.CRPSleepChangeListener;
import com.crrepa.ble.conn.listener.CRPStepChangeListener;
import com.crrepa.ble.conn.type.CRPDeviceLanguageType;
import com.crrepa.ble.conn.type.CRPDominantHandType;
import com.crrepa.ble.conn.type.CRPPastTimeType;
import com.crrepa.ble.conn.type.CRPTimeSystemType;
import com.crrepa.ble.conn.type.CRPWatchFacesType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.dell.lbstest.MyNewApplication.DEVICE_MACADDR;

public class Activity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment_map fragmentMap;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment_contact fragmentContact;
    private Fragment_watch fragmentWatch;
    private Fragment_set fragmentSet;

    private CRPBleClient crpBleClient;
    private CRPBleDevice crpBleDevice;
    private CRPBleConnection crpBleConnection;
    private BluetoothAdapter bluetoothAdapter;
    private LocationClient locationClient;

    private String longtitude;
    private String latitude;
    private String country;
    private String city;
    private String province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        fragmentWatch = new Fragment_watch();
        fragmentList.add(fragmentWatch);
        fragmentMap = new Fragment_map();
        fragmentList.add(fragmentMap);
        fragmentContact = new Fragment_contact();
        fragmentList.add(fragmentContact);
        fragmentSet = new Fragment_set();
        fragmentList.add(fragmentSet);

        tabLayout.addOnTabSelectedListener(listener);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),fragmentList));
        viewPager.setOffscreenPageLimit(3);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter != null){
            if(!bluetoothAdapter.isEnabled()){
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(intent);
                fragmentSet.switch_bluetooth.setChecked(true);
                Toast.makeText(this,"开启蓝牙",Toast.LENGTH_SHORT).show();
            }
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        }
        else
            Toast.makeText(this,"没有发现蓝牙设备",Toast.LENGTH_SHORT).show();

        crpBleClient = CRPBleClient.create(this);
        crpBleDevice = crpBleClient.getBleDevice(DEVICE_MACADDR);
        crpBleConnection = crpBleDevice.connect();
        crpBleConnection.sendWatchFaces(CRPWatchFacesType.MODEN_PORTAIT);
        crpBleConnection.sendTimeSystem(CRPTimeSystemType.TIME_SYSTEM_24);
        crpBleConnection.sendDominantHand(CRPDominantHandType.LEFT_HAND);
        crpBleConnection.sendDeviceLanguage(CRPDeviceLanguageType.LANGUAGE_ENGLISH);
        crpBleConnection.sendQuickView(true);
        crpBleConnection.sendSedentaryReminder(true);
        crpBleConnection.syncTime();
        crpBleConnection.setStepChangeListener(crpStepChangeListener);
        crpBleConnection.setSleepChangeListener(crpSleepChangeListener);
        crpBleConnection.setHeartRateChangeListener(crpHeartRateChangeListener);
        crpBleConnection.setBloodPressureChangeListener(crpBloodPressureChangeListener);
        crpBleConnection.setBloodOxygenChangeListener(crpBloodOxygenChangeListener);
        crpBleConnection.syncStep();
        crpBleConnection.syncPastStep(CRPPastTimeType.YESTERDAY_STEPS);
        crpBleConnection.syncSleep();
        crpBleConnection.queryGoalStep(new CRPDeviceGoalStepCallback() {
            @Override
            public void onGoalStep(int i) {
                fragmentWatch.goalStep.setText(i + "步");
            }
        });
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new LocationListener());
        initLocation();
        locationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
    }
    class LocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            longtitude = bdLocation.getLongitude() + "";
            latitude = bdLocation.getLatitude() + "";
            country = bdLocation.getCountry();
            province = bdLocation.getProvince();
            city = bdLocation.getCity();
        }
    }

    TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition(),true);
            crpBleConnection.syncStep();
            crpBleConnection.syncPastStep(CRPPastTimeType.YESTERDAY_STEPS);
            crpBleConnection.syncPastStep(CRPPastTimeType.DAY_BEFORE_YESTERDAY_STEPS);
            crpBleConnection.syncSleep();
            crpBleConnection.queryGoalStep(new CRPDeviceGoalStepCallback() {
                @Override
                public void onGoalStep(int i) {
                    fragmentWatch.goalStep.setText(i + "步");
                }
            });
            fragmentMap.text_longtitude.setText(longtitude);
            fragmentMap.text_latitude.setText(latitude);
            fragmentMap.text_country.setText(country);
            fragmentMap.text_province.setText(province);
            fragmentMap.text_city.setText(city);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    CRPStepChangeListener crpStepChangeListener = new CRPStepChangeListener() {
        @Override
        public void onStepChange(CRPStepInfo crpStepInfo) {
            fragmentWatch.todayStep.setText(crpStepInfo.getSteps() + "步");
            fragmentWatch.todayDistance.setText(crpStepInfo.getDistance() + "m");
            fragmentWatch.todayHeat.setText(crpStepInfo.getCalories()+ "J");
        }

        @Override
        public void onPastStepChange(int i, CRPStepInfo crpStepInfo) {
            if(i == CRPPastTimeType.DAY_BEFORE_YESTERDAY_STEPS){
                fragmentWatch.yesterdayStep.setText(crpStepInfo.getSteps() + "步");
            }
            else if(i == CRPPastTimeType.YESTERDAY_STEPS)
                fragmentWatch.yesterdayDistance.setText(crpStepInfo.getSteps() + "步");
        }
    };

    CRPSleepChangeListener crpSleepChangeListener = new CRPSleepChangeListener() {
        @Override
        public void onSleepChange(CRPSleepInfo crpSleepInfo) {
            fragmentWatch.todaySleep.setText(""+crpSleepInfo.getTotalTime());
        }

        @Override
        public void onPastSleepChange(int i, CRPSleepInfo crpSleepInfo) {
            fragmentWatch.yesterdaySleep.setText("" + crpSleepInfo.getTotalTime());
        }
    };

    CRPHeartRateChangeListener crpHeartRateChangeListener = new CRPHeartRateChangeListener() {
        @Override
        public void onMeasuring(int i) {
            fragmentWatch.text_heart.setText(" " + i);
        }

        @Override
        public void onOnceMeasureComplete(int i) {
            fragmentWatch.text_heart.setText(" " + i);
        }

        @Override
        public void onMeasureComplete(CRPHeartRateInfo crpHeartRateInfo) {
            fragmentWatch.yesterdayHeart.setText(" " + crpHeartRateInfo.getHeartRateType() + " " + crpHeartRateInfo.getMeasureData());
        }

        @Override
        public void on24HourMeasureResult(CRPHeartRateInfo crpHeartRateInfo) {
            fragmentWatch.todayHeart.setText(" " + crpHeartRateInfo.getHeartRateType() + " " + crpHeartRateInfo.getMeasureData());
        }

        @Override
        public void onMovementMeasureResult(List<CRPMovementHeartRateInfo> list) {
            for(CRPMovementHeartRateInfo info : list){
                fragmentWatch.moveHeart.setText(" " + info.getValidTime() + "\n");
            }
        }
    };

    CRPBloodPressureChangeListener crpBloodPressureChangeListener = new CRPBloodPressureChangeListener() {
        @Override
        public void onBloodPressureChange(int i, int i1) {
            fragmentWatch.textV_hp.setText("" + i);
            fragmentWatch.text_lp.setText("" + i1);
        }
    };

    CRPBloodOxygenChangeListener crpBloodOxygenChangeListener = new CRPBloodOxygenChangeListener() {
        @Override
        public void onBloodOxygenChange(int i) {
            fragmentWatch.text_oxy.setText("" + i);
        }
    };
}
