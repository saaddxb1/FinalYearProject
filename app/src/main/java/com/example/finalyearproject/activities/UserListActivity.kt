package com.example.finalyearproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.HorizontalScrollView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.adapter.userCustomAdapter
import com.example.finalyearproject.databinding.ActivityMainBinding
import com.example.finalyearproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class UserListActivity : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null
    lateinit var binding: ActivityMainBinding


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        auth = FirebaseAuth.getInstance()


        db = FirebaseDatabase.getInstance().getReference()
        var currentUser = FirebaseAuth.getInstance().currentUser?.uid
        // getting the recyclerview by its id
        val RecycleViewChat = findViewById<RecyclerView>(R.id.RecycleViewChat)

//        val RecycleViewSearch = findViewById<RecyclerView>(R.id.recycleViewForSearch)

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            var map: HashMap<String, Any?> = HashMap()
            map.put("token", it)

            db.ref.child("UserSection").child("$currentUser").updateChildren(map)
        }


        // this creates a vertical layout Manager
        RecycleViewChat.layoutManager = LinearLayoutManager(this)

//        RecycleViewSearch.layoutManager = LinearLayoutManager(this)


        // ArrayList of class ItemsViewModel
        var data = ArrayList<User>()


        val searchView = findViewById<SearchView>(R.id.searchView)

        // This will pass the ArrayList to our Adapter
        var adapterr = userCustomAdapter(this, data)


        RecycleViewChat.adapter = adapterr



        // getting data from UserSection of firebase realtime database
        db.child("UserSection").addValueEventListener(object : ValueEventListener {
            // when there is a change in data it will run the onDataChange function and will add the other
            // existing users on firebase (repeats the values again so we clear the list)

            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    data.clear()
                    for (x in snapshot.children) {

                        var detailsOfUser = x.getValue(User::class.java)

                        if (detailsOfUser != null) {

                            if (auth.uid != detailsOfUser.UniqueID)
//                            data.add(db.child("UserSection").child("${auth.uid}").child("name").get())
                                data.add(detailsOfUser)

                            adapterr.notifyDataSetChanged()

                        }
                    }


                }


            }

            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this,"Error is", Toast.LENGTH_LONG).show()

            }


        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = ArrayList<User>()
                if (!newText.isNullOrEmpty()) {
                    for (user in data) {
                        if (user.name?.contains(newText, ignoreCase = true) == true) {
                            filteredList.add(user)
                        }
                    }
                }
                else {
                    filteredList.addAll(data)
                }
                adapterr.filterList(filteredList)
                return true
            }
                    })



    }}


