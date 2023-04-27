package com.example.finalyearproject.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.activities.ChatMessageActivity
import com.example.finalyearproject.model.User

class SearchAdapter(val context: SearchView.OnQueryTextListener, val mList: ArrayList<User>): RecyclerView.Adapter<userCustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userCustomAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_user, parent, false)
        return userCustomAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: userCustomAdapter.ViewHolder, position: Int) {
        val usersList = mList[position]
        holder.name.text = usersList.name
        holder.itemView.setOnClickListener{


        }

    }



    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name = itemView.findViewById<TextView>(R.id.textViewCustomAdapter)
        var Image = itemView.findViewById<ImageView>(R.id.imageViewForUser)

    }
}