package com.example.finalyearproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CalorieSurplus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie_surplus)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
        //asd
    }
}