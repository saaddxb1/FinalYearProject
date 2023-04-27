package com.example.finalyearproject.WelcomeMenu

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalyearproject.R
import kotlinx.android.synthetic.main.activity_support_center_email.*

class SupportCenterEmail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_center_email)
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
        buttonParhKaro.setOnClickListener {

            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts("mailto", "saadasif1122@hotmail.com", null)
            )

            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")

            startActivity(Intent.createChooser(emailIntent, "Send email."))


        }

    }
}