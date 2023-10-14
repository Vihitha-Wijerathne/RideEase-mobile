package com.example.rideease.DayPass

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.rideease.R

class DaypassSelection : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daypass_selection)

        val dayPassButton: Button = findViewById(R.id.dayPassButton)
        dayPassButton.setOnClickListener {
            // Navigate to Day Pass Selection Page
        }
    }
}