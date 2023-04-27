package com.example.finalyearproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.finalyearproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_forgot_password.*

class forgotPassword : AppCompatActivity() {
    lateinit var db: DatabaseReference
    var auth: FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        var em = findViewById<EditText>(R.id.emailAsk)
        auth = FirebaseAuth.getInstance()

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }



        emailForgot.setOnClickListener {
            var forgotEmailEditText = em.text.toString()
            if(forgotEmailEditText.isNullOrEmpty()){
                em.error = "Should not be empty!(Email)"
            }
            else {
                forgotPasswordFunction(forgotEmailEditText)
            }
            }
    }


    fun forgotPasswordFunction(forgotEmailEditText: String){

        auth!!.sendPasswordResetEmail(forgotEmailEditText)
            .addOnSuccessListener {
                Toast.makeText(this,"Email sent!", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener{
                Toast.makeText(this,"Error is ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

        emailAsk.setText("")

    }
}