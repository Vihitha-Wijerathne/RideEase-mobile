package com.example.rideease

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.rideease.DayPass.DaypassSelect
import com.example.rideease.Route.AddRoute
import com.example.rideease.Route.Route


class Homefragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)

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

        return view
    }



}