package com.example.finalyearproject.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.adapter.YogaAdapter
import com.example.finalyearproject.model.Yoga
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class yoga : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        auth = FirebaseAuth.getInstance()



        db = FirebaseDatabase.getInstance().getReference()
        // getting the recyclerview by its id
        val RecycleViewYoga = findViewById<RecyclerView>(R.id.recycleViewYoga)


        // this creates a vertical layout Manager
        RecycleViewYoga.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        var data = ArrayList<Yoga>()

        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.downwardfacingdog, "Downward Facing Dog"))
        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.thetree, "The Tree"))
        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.warriorone, "Warrior 1"))
        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.warriortwo, "Warrior 2"))
        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.sideangle, "Side Angle"))
        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.forwardbend, "Forward Bend"))
//        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.bridge, "Bridge"))
//        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.childspose, "Child's Pose"))
//        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.cobrapose, "Cobra Pose"))
//        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.bowpose, "Bow Pose"))
//        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.boatpose, "Boat Pose"))
//        data.add(Yoga("android.resource://" + packageName + "/" + R.raw.fishpose, "Fish Pose"))
//



//         This will pass the ArrayList to our Adapter
        var adapterr = YogaAdapter(this, data)

        RecycleViewYoga.adapter = adapterr

    }}





 