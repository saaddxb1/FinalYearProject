package com.example.finalyearproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.finalyearproject.R
import com.example.finalyearproject.model.HeartSensorData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_heart_rate.*
import java.util.*

class HeartRate : AppCompatActivity() {

    lateinit var textsensor : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_rate)

        textsensor = findViewById(R.id.heartrate)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("HeartSensor")
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val heartSensorData = dataSnapshot.getValue(HeartSensorData::class.java)
                        if (heartSensorData != null) {

                            textsensor.setText(heartSensorData.heartData.toString())

                            println("Retrieved HeartSensor data: ${heartSensorData.heartData}")

                        } else {
                            println("HeartSensor data not found")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("Failed to read value from Firebase: ${error.message}")
                    }
                    })
            }
        }, 0, 5000) // Retrieve data every 5 seconds (5000 ms)

    }
}