package com.example.finalyearproject.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.example.finalyearproject.R
import com.example.finalyearproject.databinding.ActivityMainBinding
import com.example.finalyearproject.model.Message
import com.example.finalyearproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.infini8ai.infini8ai.Adapters.ConversationAdapter
import kotlinx.android.synthetic.main.layout_user.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

import java.text.SimpleDateFormat
import java.util.*

class ChatMessageActivity : AppCompatActivity() {

    var adapter: ConversationAdapter? = null
    var messages: ArrayList<Message>? = null
    var senderRoom: String? = null
    var recieverRoom: String? = null
    var database: FirebaseDatabase? = null
    var senderUid: String? = null
    lateinit var notificationsSharedPref: SharedPreferences
    lateinit var myEdit : SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_message)

//        var username = findViewById<TextView>(R.id.name)
//        var onlinestatus = findViewById<TextView>(R.id.status)
        messages = ArrayList()
        database = FirebaseDatabase.getInstance()
        val RecycleViewMessageArea = findViewById<RecyclerView>(R.id.RecycleViewChattingArea)
        var messageEditText = findViewById<EditText>(R.id.chatBox)
        var sendMessage = findViewById<ImageView>(R.id.sendIcon)
        val name = intent.getStringExtra("name")
        val Recieverid = intent.getStringExtra("userid")
        val senderEmail = FirebaseAuth.getInstance().currentUser?.email
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        var recieverToken = intent.getStringExtra("token")
        senderRoom = Recieverid + senderUid
        recieverRoom = senderUid + Recieverid

        supportActionBar?.title = name.toString()
//        supportActionBar?.hide()



        adapter = ConversationAdapter(this, messages!!)

        Toast.makeText(this, "$recieverToken", Toast.LENGTH_LONG).show()
//        senderUid = FirebaseAuth.getInstance().uid
//        senderRoom = senderUid + Recieverid
//        RecieverRoom = Recieverid + senderUid
//        adapter = ConversationAdapter(this, messages!!, senderRoom!!, RecieverRoom!!)
        RecycleViewMessageArea.layoutManager = LinearLayoutManager(this)
        RecycleViewMessageArea.adapter = adapter
//
//
//


        database!!.reference.child("chats")
            .child(senderRoom!!)
            .child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                        ArrayList<String> counterArray=new ArrayList<>();
                    messages!!.clear()
                    for (snapshot1 in snapshot.children) {
                        val message = snapshot1.getValue(Message::class.java)
//                        message!!.message=snapshot1.key
                        messages!!.add(message!!)
                        Log.e("TAG", "message: " + message.message)
                        Log.e("TAG", "whole message $message")

                    }
                    RecycleViewMessageArea.smoothScrollToPosition(adapter!!.itemCount)
                    adapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })




        sendMessage.setOnClickListener {

            val simpleDateFormat = SimpleDateFormat("hh:mm:ss")
            val currentDateAndTime: String = simpleDateFormat.format(Date())

            var messageEditTextDefined = messageEditText.text.toString()
            val date = Date()
            val message = Message(messageEditTextDefined, senderUid, currentDateAndTime)
            messageEditText.setText("")

            val randomkey = database!!.reference.push().key

            var hashMap: HashMap<String, Any?> = HashMap()

//            val Lastmessage: HashMap<String, Any?> = HashMap()
//            Lastmessage["lastmessage"] = message.message

            hashMap["LastMessage"] = message.message!!


            database!!.reference.child("chats")
                .child(senderRoom!!).updateChildren(hashMap)

            hashMap.put("LastMessage", message.message!!)

            database!!.reference.child("chats")
                .child(recieverRoom!!).updateChildren(hashMap)


            database!!.reference.child("UserSection").child("$senderUid")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            Log.e("getting name for notifi","yes")
                            val getName = snapshot.getValue(User::class.java)
                            var senderName = getName?.name
                            NotificationLogic(
                                senderName.toString(),
                                message.message.toString(),
                                recieverToken.toString(),

                                )
//                            Toast.makeText(this@ChatMessageActivity, "$senderName", Toast.LENGTH_SHORT).show()

                        }

                    }

                    override fun onCancelled(error: DatabaseError) {}
                })


//            var notificationsSharedPref = getSharedPreferences("nameForNotifications", MODE_PRIVATE)
//            var currentName = notificationsSharedPref.getString("nameNoti","")


            database!!.reference.child("chats")
                .child(senderRoom!!)
                .child("messages")
                .child(randomkey!!)
                .setValue(message).addOnSuccessListener {
                    database!!.reference.child("chats")
                        .child(recieverRoom!!)
                        .child("messages")
                        .child(randomkey!!)
                        .setValue(message).addOnSuccessListener {

                        }
//                }.addOnSuccessListener {
//                    NotificationLogic(
//                        senderEmail.toString(),
//                        message.message.toString(),
//                        recieverToken.toString(),
//
//                    )

                }




            //adding elements to the hashMap using
            // put() function


        }
//        val handler = Handler()
//        messageEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable) {
//                database!!.reference.child("presence").child(senderUid!!).setValue("typing...")
//                handler.removeCallbacksAndMessages(null)
//                handler.postDelayed(userStoppedTyping, 1000)
//            }
//
//            var userStoppedTyping =
//                Runnable {
//                    database!!.reference.child("presence").child(senderUid!!).setValue("Online")
//                    }
//        })

    }

    fun NotificationLogic(name: String, message: String, token: String) {

        Log.e("TAG", "name: " + name)
        Log.e("TAG", "message: " + message)
        var queue = Volley.newRequestQueue(this)
        var url = "https://fcm.googleapis.com/fcm/send"

        var save = JSONObject()
        save.put("title", name)
        save.put("body", message)
        var notification = JSONObject()
        notification.put("notification", save)
        notification.put("to", token)


        val request = object : JsonObjectRequest( //
            Method.POST, url, notification,
            Response.Listener { response ->
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show() // if api request is sent successfully
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "NotificationLogic: ${error.networkResponse}")
                Log.e("TAG", "NotificationLogic: ${error.message}")
                Log.e("TAG", "NotificationLogic: ${error.networkResponse}")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] =
                    "Key=AAAAKF2FMWY:APA91bF24ePDOIbpkEzoDBuhxM7IAOE8389V0V-U8yIz3cduofTvH4ZIKPV5fUWO8p64fIZ1V0ZNHOhlW8h0pXeBalthlrtOdvmIMfdl1KTiXA938BH-OzdBcPIomtsts3TnHGIfG5zx"

                return headers
            }
        }
        queue.add(request)


    }

//    override fun onResume() {
//        super.onResume()
//        val currentId = FirebaseAuth.getInstance().uid
//        database!!.reference.child("presence").child(currentId!!).setValue("Online")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        val currentId = FirebaseAuth.getInstance().uid
//        database!!.reference.child("presence").child(currentId!!).setValue("Offline")
//        }


}



































