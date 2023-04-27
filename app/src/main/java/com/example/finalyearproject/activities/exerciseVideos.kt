package com.example.finalyearproject.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.adapter.VideosAdapter
import com.example.finalyearproject.model.Video
import kotlinx.android.synthetic.main.activity_exercise_videos.*

class exerciseVideos : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_videos)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        /**
         * Step-1  : XML -> Add Recycler View
         * Step-2  : XML -> Create custom layout for recyclerView Row
         * Step-3  : Data Class -> Create a data class
         * Step-4  : CustomAdapter Class : RecyclerView.Adapter<CustomAdapter.ViewHolder>
         * Step-5  : Implement in MainActivity or in RecyclerView Activity.
         * */

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.RecycleView)


        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        var data = ArrayList<Video>()
//        var stringArrayList: ArrayList<String> = arrayListOf<String>("Ajay","Vijay","Prakash")




        data.add(Video("android.resource://" + packageName + "/" + R.raw.crunch,"Crunches"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.squat,"Squat"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.sideplank,"Side-Plank-Left"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.plank,"Plank"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.sideplankwomen,"Side-Plank-Right"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.plankwomen,"Plank Addition"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.highknees,"High Knees"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.jumpingjacks,"Jumping Jacks"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.lunge,"Lunge"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.pushup,"Push Up"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.pushuprotation,"Push Up + Rotation"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.stepup,"Step-Up"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.wallsit,"Wall Sit"))
        data.add(Video("android.resource://" + packageName + "/" + R.raw.triceps,"Triceps"))


        // This will pass the ArrayList to our Adapter
        var adapterr = VideosAdapter(this,data)
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapterr





    }
}