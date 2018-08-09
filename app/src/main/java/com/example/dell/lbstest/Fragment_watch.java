package com.example.dell.lbstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.bean.CRPHeartRateInfo;
import com.crrepa.ble.conn.bean.CRPMovementHeartRateInfo;
import com.crrepa.ble.conn.bean.CRPSleepInfo;
import com.crrepa.ble.conn.bean.CRPStepInfo;
import com.crrepa.ble.conn.listener.CRPBloodOxygenChangeListener;
import com.crrepa.ble.conn.listener.CRPBloodPressureChangeListener;
import com.crrepa.ble.conn.listener.CRPHeartRateChangeListener;
import com.crrepa.ble.conn.listener.CRPSleepChangeListener;
import com.crrepa.ble.conn.listener.CRPStepChangeListener;
import com.crrepa.ble.conn.listener.CRPWeatherChangeListener;
import com.example.dell.lbstest.R;

import java.util.List;

import static com.example.dell.lbstest.MyNewApplication.DEVICE_MACADDR;

public class Fragment_watch extends Fragment {
     TextView todayStep;
     TextView todayDistance;
     TextView todayHeat;
     TextView todaySleep;
     TextView yesterdaySleep;
     TextView text_heart;
     TextView text_heartDynamic;
     TextView todayHeart;
     TextView yesterdayHeart;
     TextView moveHeart;
     TextView textV_hp;
     TextView text_lp;
     TextView text_oxy;
     CardView cardView;
     TextView yesterdayStep;
     TextView yesterdayDistance;
     TextView goalStep;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.watch,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        todayStep = (TextView)view.findViewById(R.id.today_step);
        todayDistance = (TextView)view.findViewById(R.id.today_distance);
        todayHeat = (TextView)view.findViewById(R.id.today_heat);
        todaySleep = (TextView)view.findViewById(R.id.today_sleep);
        yesterdaySleep = (TextView)view.findViewById(R.id.yesterday_sleep);
        text_heartDynamic = (TextView)view.findViewById(R.id.heartDynamicRate);
        todayHeart = (TextView)view.findViewById(R.id.today_heartRate);
        yesterdayHeart = (TextView)view.findViewById(R.id.yesterday_heartRate);
        moveHeart = (TextView)view.findViewById(R.id.move_heartRate);
        text_lp = (TextView)view.findViewById(R.id.low_pressure);
        textV_hp = (TextView)view.findViewById(R.id.high_pressure);
        text_oxy = (TextView)view.findViewById(R.id.text_oxygen);
        cardView = (CardView)view.findViewById(R.id.step_cardView);
        yesterdayStep = (TextView)view.findViewById(R.id.yesterday_step);
        yesterdayDistance = (TextView)view.findViewById(R.id.yesterday_distance);
        goalStep = (TextView)view.findViewById(R.id.goal_step);
    }
}
