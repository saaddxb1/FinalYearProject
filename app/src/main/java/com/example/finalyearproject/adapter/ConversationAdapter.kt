package com.infini8ai.infini8ai.Adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.finalyearproject.R
import com.example.finalyearproject.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.receivemessage.view.*
import kotlinx.android.synthetic.main.sentmessage.view.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class ConversationAdapter(var context: Context, messages: ArrayList<Message>) :
    RecyclerView.Adapter<ViewHolder>() {
    var messages: ArrayList<Message>
    val ITEM_SENT = 1
    val ITEM_RECIEVE = 2


    init {
        this.messages = messages

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT) {
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.sentmessage, parent, false)
            SentViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.receivemessage, parent, false)
            RecieverViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message: Message = messages[position]
        Log.e("TAG", "Message Sender ID: "+message.senderid )
        Log.e("TAG", "Current Logged In USER ID: "+FirebaseAuth.getInstance().currentUser?.uid )
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(message.senderid)) {
            return ITEM_SENT
        } else {
            return ITEM_RECIEVE
        }
    }



    override fun getItemCount(): Int {
        return messages.size
    }

     class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            var SentMessage = itemView.findViewById<TextView>(R.id.sentMessage)
            var timeOfSentMessage = itemView.findViewById<TextView>(R.id.sentMessageDate)

        }
    }

     class RecieverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            var ReceiveMessage = itemView.findViewById<TextView>(R.id.receiveMessage)
            var timeOfReceiveMessage = itemView.findViewById<TextView>(R.id.receiveMessageDate)
        }
    }

    class saadViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        init{
            // var saad = itemView.findviewbyID .......
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // holder
        val message = messages[position]


        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolders = holder as SentViewHolder
            viewHolders.itemView.sentMessage.text=message.message
            viewHolders.itemView.sentMessageDate.text = message.dateUser
//            viewHolders.itemView.email.text = message.senderid
            Log.e("TAG", "sender: "+message.message)

        } else {
            val viewHolder = holder as RecieverViewHolder
            viewHolder.itemView.receiveMessage.text=message.message
            viewHolder.itemView.receiveMessageDate.text = message.dateUser


        }
    }
}