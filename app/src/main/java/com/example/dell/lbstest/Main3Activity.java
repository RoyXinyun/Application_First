package com.example.dell.lbstest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;

public class Main3Activity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private Random random;
    private TextView textView;
    private CircleImageView imageView;
    private int[] images = {R.drawable.beijing,R.drawable.beijing2,R.drawable.changsha,R.drawable.chengdu,
                     R.drawable.chongqing,R.drawable.guangzhou,R.drawable.hangzhou,R.drawable.nanjing,
                     R.drawable.qingdao,R.drawable.shanghai,R.drawable.tianjin,R.drawable.shenyang,R.drawable.shenzhen,
                     R.drawable.wuhan,R.drawable.xian};
    private String[] names = {"中国  北京","中国  北京","中国  长沙","中国  成都","中国  重庆","中国  广州","中国  杭州","中国  南京"
                              ,"中国  青岛","中国  上海","中国  天津","中国  沈阳","中国  深圳","中国  武汉","中国  西安"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        imageView = (CircleImageView)findViewById(R.id.image);
        textView = (TextView)findViewById(R.id.textView);
        random = new Random();
        int index = random.nextInt(14);
        imageView.setImageResource(images[index]);
        textView.setText(names[index] + "    ");
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorListener,sensor,SENSOR_DELAY_NORMAL);
    }

    SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float xValue = Math.abs(sensorEvent.values[0]);
            float yValue = Math.abs(sensorEvent.values[1]);
            float zValue = Math.abs(sensorEvent.values[2]);
            if(xValue > 15 || yValue > 15 || zValue > 15){
                TipHelper.Vibrate(Main3Activity.this,500);
                int index = random.nextInt(14);
                imageView.setImageResource(images[index]);
                textView.setText(names[index] + "  ");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(sensorListener);
    }
}
