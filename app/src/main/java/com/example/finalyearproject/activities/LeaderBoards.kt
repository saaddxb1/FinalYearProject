package com.example.finalyearproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.finalyearproject.R
import com.example.finalyearproject.model.Sensor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_leader_boards.*

class LeaderBoards : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_boards)
        var userSensorData: ArrayList<Int> = ArrayList()
        var duplicateOfSensorData: ArrayList<Int> = ArrayList()
        var userName: ArrayList<String> = ArrayList()
        var userUID: ArrayList<String> = ArrayList()

        var currentUser = FirebaseAuth.getInstance().currentUser?.uid

        db = FirebaseDatabase.getInstance().getReference()
        db.ref.child("Step Sensor")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userSensorData.clear()

                    for (snapshot1 in snapshot.children) {
                        val details = snapshot1.getValue(Sensor::class.java)
                        if (details != null) {
//                            userSensorData.add(details.steps!!.toInt())
                            userSensorData.add(details.steps!!.toInt())
                            userName.add(details!!.name!!.toString())
                            userUID.add(details!!.uniqueIDOfSteps!!.toString())
                            duplicateOfSensorData.add(details.steps!!.toInt())
                        }
                        // name = [ali, saad, jasim]
                        // a = [1,2,0]
                        // steps = [2,10,5]
                        // username[a[0]]
                        // userName[1]
                    }
                    Log.e("see it yourself","it is as follows $userSensorData")
                    var a = logic(userSensorData)

                    var sortedARray = userSensorData.sortedDescending().toMutableList()
                    if(!sortedARray.isEmpty())
                    {
//
                        var firstPerson = userName[0]
//                        var firstPOsName = userName[userSensorData.indexOf(sortedARray[0])]
                        var firstPOsName = userName[a[0]]
                        var firstSteps = duplicateOfSensorData[a[0]]
                        firstPos.text = "First Position : ${firstPOsName.toString()} Steps: $firstSteps "


                        if(sortedARray.size>1)
                        {

                            var new = db.child("Step Sensor").child("$currentUser")
                            var secondPerson = sortedARray[1]
//                            var secondPosName = userName[userSensorData.indexOf(sortedARray[1])]
                            var secondPosName = userName[a[1]]
                            var secondSteps = duplicateOfSensorData[a[1]]
                            secondPos.text = "Second Position : ${secondPosName.toString()} Steps: $secondSteps"
                        }
                        if(sortedARray.size>2)
                        {
                            var thirdPerson = sortedARray[2]
//                            var thirdPosName = userName[userSensorData.indexOf(sortedARray[1])]
                            var thirdPosName = userName[a[2]]
                            var thirdSteps = duplicateOfSensorData[a[2]]
                            thirdPos.text = "Third Position : ${thirdPosName.toString()} Steps: $thirdSteps"
                        }
                    }

                    Log.e("this is ","message is = ${sortedARray}")


                }
                override fun onCancelled(error: DatabaseError) {}
            })

    }

    fun logic(userSensorData: ArrayList<Int>): ArrayList<Int> {
        var emptyList: ArrayList<Int> = ArrayList()

        for(a in 0..userSensorData.size-1){
            if(a in emptyList){
                userSensorData[a] = -1
            }
            var isTemp = a
            for(j in 0..userSensorData.size-1){
                if(j in emptyList){

                }
                else{
                    if(userSensorData[j]>userSensorData[isTemp]){
                        isTemp = j
                    }
                }
            }
            emptyList.add(isTemp)


        }
        return emptyList

    }
}