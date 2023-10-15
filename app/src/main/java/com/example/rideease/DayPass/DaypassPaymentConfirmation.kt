package com.example.rideease.DayPass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.rideease.R

class DaypassPaymentConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daypass_payment_confirmation)


        val homeButton: Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            // Navigate back to the homepage
        }
    }
}