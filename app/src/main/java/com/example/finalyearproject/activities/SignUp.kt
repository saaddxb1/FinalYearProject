package com.example.finalyearproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.finalyearproject.R
import com.example.finalyearproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class signUp : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    var isCheck = false
    var nameOfUsers: ArrayList<String> = ArrayList()
    var data: ArrayList<User> = ArrayList()
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myEdit :SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        auth = Firebase.auth





        ButtonForSignUp.setOnClickListener {

            var name = findViewById<EditText>(R.id.nameForSignUp)
            var email = findViewById<EditText>(R.id.emailEditTextSignUp)
            var password = findViewById<EditText>(R.id.passwordEditTextSignUp)

            var nameSignUp = name.text.toString()
            var emailSignUp = email.text.toString()
            var passwordSignUp = password.text.toString()

            if (emailSignUp.isNullOrEmpty()) {
                emailEditTextSignUp.error = "Should not be empty!(Email)"
            } else if (passwordSignUp.isNullOrEmpty()) {
                passwordEditTextSignUp.error = "Should not be empty!(Password)"
            } else if (nameSignUp.isNullOrEmpty()) {
                nameForSignUp.error = "Should not be empty!(Name)"
            }
            else if(nameSignUp.length>=15){
                nameForSignUp.error = "Name should be less than 15 characters"
            }

            else {
                db = FirebaseDatabase.getInstance().getReference()
                if(isEmailValid(emailSignUp)){
                    signUp(nameSignUp, emailSignUp, passwordSignUp)
                }
                else{
                    Toast.makeText(this,"Wrong email format!", Toast.LENGTH_LONG).show()
                }

            }
        }


    }



    fun isEmailValid(email: String): Boolean {
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        return EMAIL_REGEX.toRegex().matches(email);
    }


    fun signUp(nameSignUp: String, emailSignUp: String, passwordSignUp: String) {




//        db.child("UserSection").addValueEventListener(object : ValueEventListener {
//            // when there is a change in data it will run the onDataChange function and will add the other
//            // existing users on firebase (repeats the values again so we clear the list)
//
//            @SuppressLint("SuspiciousIndentation")
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                if (snapshot.exists()) {
//                    data.clear()
//                    for (x in snapshot.children) {
//                        var detailsOfUser = x.getValue(User::class.java)
//                        Log.e("whole message sign up","${detailsOfUser}")
//
//                        if (detailsOfUser != null) {
//                                data.add(detailsOfUser) // ask new has all things but we only take name in adapter
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
////                Toast.makeText(this,"Error is", Toast.LENGTH_LONG).show()
//
//            }
//
//
//        })

        auth.createUserWithEmailAndPassword(emailSignUp, passwordSignUp)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addToDatabase(nameSignUp, emailSignUp, auth.uid!!)

                    sharedPreferences = getSharedPreferences("getName", MODE_PRIVATE)
                    myEdit = sharedPreferences!!.edit()
                    myEdit.putString("name", nameSignUp)
                    myEdit.commit()

                    var transfer = Intent(this, signIn::class.java)
                    transfer.putExtra("name", nameSignUp)
                    startActivity(transfer)

                } else {
                    Toast.makeText(this, "Authentication error!", Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Error has occured ${it.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }


        nameForSignUp.setText("")
        emailEditTextSignUp.setText("")
        passwordEditTextSignUp.setText("")

    }

    fun addToDatabase(name: String, email: String, uid: String) {

        db = FirebaseDatabase.getInstance().getReference()
        db.child("UserSection").child(uid).setValue(User(name, email, uid))

    }

}






