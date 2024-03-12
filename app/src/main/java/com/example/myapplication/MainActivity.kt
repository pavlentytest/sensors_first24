package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    var lightsensor: Sensor? = null
    var pressuresensor: Sensor? = null
    var rotatesensor: Sensor? = null
    var tempsensor: Sensor? = null
    lateinit var lighttv: TextView
    lateinit var pressuretv: TextView
    lateinit var rotateiv: ImageView
    lateinit var temptv: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val list: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        println("Size=${list.size}")
        println(list.joinToString("\n"))

        lighttv = findViewById(R.id.lightvalue)
        pressuretv = findViewById(R.id.pressurevalue)
        rotateiv = findViewById(R.id.rotateImage)
        temptv = findViewById(R.id.tempvalue)

        lightsensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if(lightsensor != null) {
            sensorManager.registerListener(this,lightsensor,SensorManager.SENSOR_DELAY_GAME)
        }
        pressuresensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pressuresensor != null) {
            sensorManager.registerListener(this,pressuresensor,SensorManager.SENSOR_DELAY_GAME)
        }
        rotatesensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(rotatesensor != null) {
            sensorManager.registerListener(this,rotatesensor,SensorManager.SENSOR_DELAY_GAME)
        }
        tempsensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if(tempsensor != null) {
            sensorManager.registerListener(this,tempsensor,SensorManager.SENSOR_DELAY_GAME)
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(lightsensor == null) {
            lighttv.text = "No light sensor!"
        } else if(event!!.sensor.type == lightsensor?.type) {
            lighttv.text = "Light: ${event.values[0]}"
        }
        if(pressuresensor == null) {
            pressuretv.text = "No pressure sensor!"
        } else if(event!!.sensor.type == pressuresensor?.type) {
            pressuretv.text = "Pressure: ${event.values[0]}"
        }
        if(event!!.sensor.type == rotatesensor!!.type) {
            rotateiv.rotation = event.values[2]*10
        }
        if(tempsensor == null) {
            temptv.text = "No temp sensor!"
        } else if(event!!.sensor.type == tempsensor?.type) {
            temptv.text = "Temp: ${event.values[0]}"
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}