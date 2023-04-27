package com.example.finalyearproject.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.finalyearproject.R
import com.example.finalyearproject.model.HeartSensorData
import com.example.finalyearproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_stepsensor.*
import java.text.SimpleDateFormat
import java.util.*


class stepsensor : AppCompatActivity(), SensorEventListener {

    lateinit var textsensor : TextView
    private var sensorManager: SensorManager? = null
    private var running = false
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var resetButton: Button
    private lateinit var btnsupload: Button
    private lateinit var heartButton: Button
    lateinit var gifis: ImageView
    private var stepsInTotal = 0f
    private var previousSteps = 0f
    private val REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 101



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        var heartSensorData: ArrayList<Float> = ArrayList()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stepsensor)
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }



         gifis=findViewById(R.id.move);
        Glide.with(this)
            .load(R.drawable.scoreis)
            .into(gifis);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
// ask for permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), REQUEST_ACTIVITY_RECOGNITION_PERMISSION)
                }
            }
        }

        textsensor = findViewById(R.id.heartData)
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

        resetButton = findViewById(R.id.reset_steps)
        btnsupload = findViewById(R.id.btnsupload)
//        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
//
//        val currentDateAndTime: String = simpleDateFormat.format(Date())
//
//
//        Log.e("TAG", "onCreate: "+currentDateAndTime )

        btnsupload.setOnClickListener {
            var getname: String = ""
            db = FirebaseDatabase.getInstance().getReference()
            var currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()

            db.child("UserSection").child(currentUser).addValueEventListener(object : ValueEventListener {
                // when there is a change in data it will run the onDataChange function and will add the other
                // existing users on firebase (repeats the values again so we clear the list)

                @SuppressLint("SuspiciousIndentation")
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                            var detailsOfUser = snapshot.getValue(User::class.java)
                            var name = detailsOfUser?.name
                            var uidUser = detailsOfUser?.UniqueID
//
                            Log.e("name is is is ", "$name")
                            var stepsTotal =  theSteps.text.toString()
//
                            Log.e("name of user","$name")
                            var data = com.example.finalyearproject.model.Sensor(stepsTotal,uidUser,name)
                            db.child("Step Sensor").child(currentUser).setValue(data)
                            Toast.makeText(this@stepsensor,"Data has been sent!", Toast.LENGTH_LONG).show()

                        }
                    }


                override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this,"Error is", Toast.LENGTH_LONG).show()

                }

            })

//            var stepsTotal =  tv_stepsTaken.text.toString()
//            var sharedPreferences = getSharedPreferences("getName", MODE_PRIVATE)
//            var sharedPreferencesSaad = getSharedPreferences("saadwaqqas", MODE_PRIVATE)
//            var name = sharedPreferencesSaad.getString("nameUser","")
////            var name = sharedPreferences.getString("name","")
//            Log.e("name of user","$name")
//            var uid = sharedPreferences.getString("uniqueID","")
//            Log.e("it is called ","it is : ${name}, ${uid}")
//            var data = com.example.finalyearproject.model.Sensor(stepsTotal,currentUser,name)
//            db.child("Step Sensor").child(currentUser).setValue(data)
//            Toast.makeText(this,"Data has been sent!", Toast.LENGTH_LONG).show()

        }

//        heartButton.setOnClickListener {
//
//            var changePhase = Intent(this,HeartRate::class.java)
//            startActivity(changePhase)
//
//        }

        Leaderboards.setOnClickListener {
            var changePhase = Intent(this,LeaderBoards::class.java)
            startActivity(changePhase)
        }


//
        loadData()

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager


        resetButton.setOnClickListener {
            previousSteps = stepsInTotal
            theSteps.text = 0.toString()
            saveData()
//            saveData()

        }



    }

    override fun onResume() {
        super.onResume()

        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "No Detection of Sensor", Toast.LENGTH_LONG).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)



        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        Log.e("Steps : $p0[0]", "$p0[1]")
        if (running == true) {
            if (p0 != null) {
                Log.e("Steps : $p0[0]", "$p0[1]")
                stepsInTotal = p0.values[0]
            }
            val presentSteps = stepsInTotal.toInt() - previousSteps.toInt()
            theSteps.text = "$presentSteps"

            progressBar.apply {
                setProgressWithAnimation(presentSteps.toFloat())
            }
        }

    }


    fun saveData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putFloat("steps", previousSteps)
        editor.apply()
    }

    fun loadData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val savedNumber: Float = sharedPreferences.getFloat("steps", 0f)
        previousSteps = savedNumber
        Log.e("TAG", "previousTotalSteps: $previousSteps")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


}