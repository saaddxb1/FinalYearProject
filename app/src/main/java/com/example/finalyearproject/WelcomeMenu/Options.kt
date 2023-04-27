package com.example.finalyearproject.WelcomeMenu
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.finalyearproject.R
import com.example.finalyearproject.activities.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_options.*


class Options : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth

    lateinit var logout: Button
    lateinit var bikeGif: ImageView
    private val REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 101


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
//// ask for permission
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), REQUEST_ACTIVITY_RECOGNITION_PERMISSION)
//            }
//        }

        bikeGif=findViewById(R.id.gifImage);
        Glide.with(this)
            .load(R.drawable.diet_exercise)
            .into(bikeGif);


        logout = findViewById(R.id.logOut)
        logout.setOnClickListener {

            showLogoutDialog()
        }
        var nameReceived = intent.getStringExtra("email")
        Log.e("TAG", "email:$nameReceived ", )
//        idtoaddname.setText("$nameReceived")


        messengerID.setOnClickListener{
            var changePhase = Intent(this, UserListActivity::class.java)
            startActivity(changePhase)
        }

        emailSupportID.setOnClickListener{
            var changePhase = Intent(this,SupportCenterEmail::class.java)
            startActivity(changePhase)
        }

        exerciseID.setOnClickListener{
            var changePhase = Intent(this, exerciseVideos::class.java)
            startActivity(changePhase)
        }

        Products.setOnClickListener{
            var changePhase = Intent(this, products::class.java)
            startActivity(changePhase)
        }

        yogaClick.setOnClickListener{
            var changePhase = Intent(this, yoga::class.java)
            startActivity(changePhase)

        }

        stepSensorID.setOnClickListener{




            var changePhase = Intent(this, stepsensor::class.java)
            startActivity(changePhase)
        }

    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this@Options, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custm_alert_logout, null)
        val btnOK = view.findViewById<Button>(R.id.btn_ok)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        builder.setView(view)
        btnOK.setOnClickListener {
            val sharedPreferences: SharedPreferences? = getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val myEdit = sharedPreferences!!.edit()
            myEdit.clear()
            myEdit.apply()
            this@Options.moveTaskToBack(true);
            this@Options.finish();
        }
        btnCancel.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

}