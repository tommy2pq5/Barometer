package com.example.masato.barometer

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlin.properties.Delegates
import java.nio.file.Files.size



class MainActivity : AppCompatActivity(),SensorEventListener {
    private var sensorManager: SensorManager by Delegates.notNull<SensorManager>()
    private var pressure: Sensor by Delegates.notNull<Sensor>()
    private var zeroing=1000.0f
    private var zeroflg=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zeroButton=findViewById<Button>(R.id.zeroing)

        zeroButton.setOnClickListener {
            zeroflg=true
        }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager;

        pressure = sensorManager.getDefaultSensor(
            Sensor.TYPE_PRESSURE
        )



    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("sensorChanged","sensorChanged")
        if(event?.sensor?.type==Sensor.TYPE_PRESSURE){
            val senval=event.values.get(0)
            val diffalt=((senval-zeroing))/120.162*1000
            findViewById<TextView>(R.id.baroText).text= diffalt?.toString()
            Log.d("sensorChanged", event?.values!![0].toString())

            if(zeroflg){
                zeroing=senval
                zeroflg=false
            }


        }
    }

    override fun onResume(){
        super.onResume()
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_UI)

    }

}
