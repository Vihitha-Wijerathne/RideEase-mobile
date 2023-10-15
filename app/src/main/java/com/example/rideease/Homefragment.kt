package com.example.rideease

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.rideease.DayPass.DaypassSelection

class Homefragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)

        val pay = view.findViewById<Button>(R.id.payment)
        val payr = view.findViewById<Button>(R.id.paymentr)

        pay.setOnClickListener {
            val intent = Intent(activity, AddCardDetails::class.java)
            startActivity(intent)
        }

        val dayPassButton: Button = view.findViewById(R.id.dayPassButton)
        dayPassButton.setOnClickListener {
            // Navigate to DaypassSelectionActivity
            val intent = Intent(activity, DaypassSelection::class.java)
            startActivity(intent)
        }
        
        payr.setOnClickListener {
            val intent = Intent(activity, CreditCard::class.java)
            startActivity(intent)
        }
        return view
    }

}

