package com.example.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView txSensor;
    private TextView txSensorProximity;
    private Sensor sensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txSensor = (TextView) findViewById(R.id.sensor_list);
        txSensorProximity = (TextView) findViewById(R.id.sensor_proximity);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();
        for(Sensor sensor:sensorList){
            sensorText.append(sensor.getName())
                    .append(System.getProperty("line.separator"));
        }
        txSensor.setText(sensorText);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(sensorProximity==null){
            txSensorProximity.setText("No sensor!");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sensorProximity!=null){
            sensorManager.registerListener(this,sensorProximity
                    ,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float value = sensorEvent.values[0];
        if(sensorType==Sensor.TYPE_PROXIMITY){
            txSensorProximity.setText("Proximity Sensor: "+value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
