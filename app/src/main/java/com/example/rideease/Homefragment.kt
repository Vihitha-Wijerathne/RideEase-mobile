package com.example.rideease

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.rideease.DayPass.DaypassSelect
import com.example.rideease.GetLoan.AddLoan
import com.example.rideease.Route.AddRoute

class Homefragment : Fragment() {
    private lateinit var loan: Button
    private lateinit var payr: Button
    private lateinit var pay: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)

        pay = view.findViewById(R.id.payment)
        payr = view.findViewById(R.id.paymentr)
        loan = view.findViewById(R.id.getloan_home)

        loan.setOnClickListener{
            val intent = Intent(activity, AddLoan::class.java)
            startActivity(intent)
        }

        pay.setOnClickListener {
            val intent = Intent(activity, AddCardDetails::class.java)
            startActivity(intent)
        }

        val dayPassButton: Button = view.findViewById(R.id.dayPassButton)
        dayPassButton.setOnClickListener {
            // Navigate to DaypassSelectionActivity
            val intent = Intent(activity, DaypassSelect::class.java)
            startActivity(intent)
        }

        val addRoute: Button = view.findViewById(R.id.addRouteButton)
        addRoute.setOnClickListener {
            // Navigate to DaypassSelectionActivity
            val intent = Intent(activity, AddRoute::class.java)
            startActivity(intent)
        }

        payr.setOnClickListener {
            val intent = Intent(activity, CreditCard::class.java)
            startActivity(intent)
        }

        return view
    }

}

