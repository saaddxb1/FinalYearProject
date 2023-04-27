package com.example.finalyearproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class highprotein : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highprotein)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
    }
}