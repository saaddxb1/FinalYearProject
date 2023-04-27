package com.example.finalyearproject.WelcomeMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalyearproject.R

class CalorieDeficit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie_deficit)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
    }
}