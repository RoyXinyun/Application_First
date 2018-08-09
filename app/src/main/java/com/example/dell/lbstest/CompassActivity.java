package com.example.dell.lbstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.List;

public class CompassActivity extends AppCompatActivity {
        ImageView imageView;
        float currentDegree = 0f;
        SensorManager mSensorManager;
        private Sensor accelerometer;
        private Sensor magnetic;
        private float[] accelerometerValues = new float[3];
        private float[] magneticFieldValues = new float[3];
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_compass);
            imageView = (ImageView)findViewById(R.id.image_pass);
            mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(sensorListener,accelerometer,SensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener(sensorListener,magnetic,SensorManager.SENSOR_DELAY_GAME);
            calculateOrientation();
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(sensorListener);
    }

        private float calculateOrientation(){
            float[] values = new float[3];
            float[] R = new float[9];
            SensorManager.getRotationMatrix(R, null, accelerometerValues,
                    magneticFieldValues);
            SensorManager.getOrientation(R, values);
            values[0] = (float) Math.toDegrees(values[0]);
            return values[0];
        }

    SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = sensorEvent.values;
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldValues = sensorEvent.values;
            }
            float degree = calculateOrientation();
            RotateAnimation ra = new RotateAnimation(currentDegree,-degree, Animation.RELATIVE_TO_PARENT,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            ra.setDuration(200);
            imageView.startAnimation(ra);
            currentDegree=-degree;
            }
            @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    }