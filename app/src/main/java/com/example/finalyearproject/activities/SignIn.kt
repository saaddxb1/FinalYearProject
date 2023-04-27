package com.example.finalyearproject.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalyearproject.R
import com.example.finalyearproject.WelcomeMenu.Options
import com.example.finalyearproject.model.Sensor
import com.example.finalyearproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_leader_boards.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class signIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var isUserLoggedIn: Boolean = false
    private lateinit var db: DatabaseReference
    var database: FirebaseDatabase? = null
    //    lateinit var iPref: PrefImpl
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myEdit :SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)



        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
        sharedPreferences = getSharedPreferences("ImpData", MODE_PRIVATE)
        myEdit = sharedPreferences!!.edit()


//        iPref = PrefImpl(this)
        checkLoginUser()

        auth = Firebase.auth
        textView.setOnClickListener {
            var intentForForgotPassword = Intent(this, forgotPassword::class.java)
            startActivity(intentForForgotPassword)
        }

        donthaveaccount.setOnClickListener {
            var intentForSignUp = Intent(this, signUp::class.java)
            startActivity(intentForSignUp)
        }

        buttonForSignIn.setOnClickListener {

            var email = findViewById<EditText>(R.id.emailEditText)
            var password = findViewById<EditText>(R.id.passwordEditText)
            var emailSignIn = email.text.toString()
            var passwordSignIn = password.text.toString()


            if (emailSignIn.isNullOrEmpty()) {
                emailEditText.error = "Should not be empty!(Email)"
            } else if (passwordSignIn.isNullOrEmpty()) {
                passwordEditText.error = "Should not be empty!(Password)"
            } else {
                signUserIn(emailSignIn, passwordSignIn)
            }
        }




    }

    private fun checkLoginUser() {

        val sh = getSharedPreferences("ImpData", MODE_PRIVATE)

        isUserLoggedIn = sh.getBoolean("userLoggedValue",false)

        Log.e("TAG", "isLoggin: "+isUserLoggedIn )
        if (isUserLoggedIn) {
            val intent = Intent(this@signIn, Options::class.java)
            startActivity(intent)
        }

//        val isUserLoggedIn: Boolean = iPref.bool("is_user_logged_in",false)
//        if (isUserLoggedIn) {
//            Log.e("TAG", "shared Bool: " + isUserLoggedIn)
//            val intent = Intent(this@signIn, Options::class.java)
//            startActivity(intent)
//
//        }
    }

    fun signUserIn(emailSignIn: String, passwordSignIn: String) {


        auth.signInWithEmailAndPassword(emailSignIn, passwordSignIn)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    sharedPreferences = getSharedPreferences("getName", MODE_PRIVATE)

                    var currentUserNow = FirebaseAuth.getInstance().currentUser?.uid


                    var goToOptions = Intent(this@signIn, Options::class.java)
                    var nameFromSignUp = intent.getStringExtra("name")
                    Log.e("name from sign up ","$nameFromSignUp")
                    goToOptions.putExtra("email", emailSignIn)
//                    iPref.put("is_user_logged_in", true)
                    myEdit.putBoolean("userLoggedValue", true)
                    myEdit.putString("name", nameFromSignUp)
                    myEdit.commit()
                    startActivity(goToOptions)


                } else {
                    Toast.makeText(this, "Error has occurred!", Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error is ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

        emailEditText.setText("")
        passwordEditText.setText("")


    }





}