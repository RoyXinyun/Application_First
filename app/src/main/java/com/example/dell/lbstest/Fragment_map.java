package com.example.dell.lbstest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

public class Fragment_map extends Fragment {
    private TextView text_map;
    private TextView text_compass;

    private String longtitude;
    private String latitude;
    private String country;
    private String city;
    private String province;

    TextView text_country;
    TextView text_province;
    TextView text_city;
    TextView text_longtitude;
    TextView text_latitude;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map,container,false);
        text_map = (TextView)view.findViewById(R.id.text_map);
        text_compass = (TextView)view.findViewById(R.id.text_compass);
        text_country = (TextView)view.findViewById(R.id.country);
        text_province = (TextView)view.findViewById(R.id.province);
        text_city = (TextView)view.findViewById(R.id.city);
        text_longtitude = (TextView)view.findViewById(R.id.longtitude);
        text_latitude = (TextView)view.findViewById(R.id.latitude);
        text_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivityForResult(intent,1);
            }
        });
        text_compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CompassActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
