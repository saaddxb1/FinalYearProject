package com.example.finalyearproject.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.activities.ChatMessageActivity
import com.example.finalyearproject.activities.UserListActivity
import com.example.finalyearproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class userCustomAdapter(val context: UserListActivity, val mList: ArrayList<User>): RecyclerView.Adapter<userCustomAdapter.ViewHolder>() {
    private var filteredList: List<User> = mList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val usersList = filteredList[position]

        var senderID = FirebaseAuth.getInstance().uid
        var senderRoom = senderID + usersList.UniqueID

        Log.e("sender room in ? $senderRoom", "sender room in or no")
        FirebaseDatabase.getInstance()!!.reference.child("chats")
            .child(senderRoom!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.e("snapshot data is $snapshot", "it is $snapshot")
                   var lastMsg = snapshot.child("LastMessage").value
                    holder.messageOnApp.text = lastMsg.toString()

                if(lastMsg == null){
                    holder.messageOnApp.text = "Tap to chat"
                }
                else{
                    holder.messageOnApp.text = lastMsg.toString()
                }

//                if (snapshot.exists()) {
////                    var lastMsg = snapshot.child("LastMessage").getValue(String.javaClass)
//                    holder.messageOnApp.text = lastMsg.toString()
//                }`
//                else{
//
//                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        holder.name.text = usersList.name
        holder.itemView.setOnClickListener{
            var moveit = Intent(context, ChatMessageActivity::class.java)
//            moveit.putExtra("user",usersList as Parcelable)
            moveit.putExtra("name", usersList.name)
            moveit.putExtra("userid", usersList.UniqueID)
            moveit.putExtra("token", usersList.token)

            context.startActivity(moveit)
        }




    }

    fun filterList(filteredList: List<User>) {
        this.filteredList = filteredList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return filteredList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name = itemView.findViewById<TextView>(R.id.textViewCustomAdapter)
        var Image = itemView.findViewById<ImageView>(R.id.imageViewForUser)
        var messageOnApp = itemView.findViewById<TextView>(R.id.messageOnMessenger)



    }
}




