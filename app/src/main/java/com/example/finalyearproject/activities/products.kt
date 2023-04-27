package com.example.finalyearproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.finalyearproject.CalorieSurplus
import com.example.finalyearproject.R
import com.example.finalyearproject.WelcomeMenu.CalorieDeficit
import com.example.finalyearproject.highprotein
import kotlinx.android.synthetic.main.activity_products.*

class products : AppCompatActivity() {

    lateinit var thinkingGif: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
        thinkingGif = findViewById(R.id.thinkin)
        Glide.with(this).load(R.drawable.whattoeat).into(thinkingGif)
        //Calorie Deficit
        buttonLeft.setOnClickListener{
            var Intent = Intent(this, CalorieDeficit::class.java)
            startActivity(Intent)
        }

        //Calorie Surplus
        buttonMiddle.setOnClickListener{
            var Intent = Intent(this, CalorieSurplus::class.java)
            startActivity(Intent)
        }
        // High Protein
        highProtein.setOnClickListener {
            var Intent = Intent(this, highprotein::class.java)
            startActivity(Intent)
        }



    }
}